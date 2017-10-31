package majestyUtility;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;

/*
[Number of Files]    [File Offset 1]    [File Name 1]   [File Offset 2]   [File Name 2]   ...N 번 만큼 반복 ... [ File Data ]
        4 byte                 4 byte         24 byte           4 byte           24 byte
        
        little endian 사용
*/

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;

import javax.imageio.ImageIO;

import majestyUtility.res.Converter;
import majestyUtility.res.FileStructure;

public class ResourceExtractor {
	String fileLoc;

	int numberOfFiles; // 파일 개수
	byte[] nof = new byte[4]; // 첫 4바이트는 파일의 개수

	RandomAccessFile raf;
	File file;
	FileStructure fileStructure;
	ArrayList<FileStructure> aList;

	public ResourceExtractor(String fileLoc) {
		file = new File(fileLoc);

		try {
			raf = new RandomAccessFile(file, "r");
			// 바이트 만큼 가져오기 위해 raf 객체 생성
			raf.readFully(nof);
			numberOfFiles = Converter.byteArrayToInt(nof);
			// 파일의 갯수를 가져온다.
			aList = new ArrayList<FileStructure>();

			int cnt = 0;
			while (cnt < numberOfFiles) { // 파일 개수만큼 반복
				if (cnt == numberOfFiles - 1) { // 마지막 파일 크기구하기
					fileStructure = new FileStructure();
					raf.readFully(fileStructure.getFileOffset());
					raf.readFully(fileStructure.getFileName());
					fileStructure.setFileNames(new String(fileStructure.getFileName(), StandardCharsets.UTF_8).trim());
					fileStructure.setOffset(Converter.byteArrayToInt(fileStructure.getFileOffset()));
					fileStructure.setFileSize((int) raf.length() - fileStructure.getOffset());

					aList.add(fileStructure);
				} else {
					fileStructure = new FileStructure();
					raf.readFully(fileStructure.getFileOffset());
					raf.readFully(fileStructure.getFileName());
					fileStructure.setFileNames(new String(fileStructure.getFileName(), StandardCharsets.UTF_8).trim());
					fileStructure.setOffset(Converter.byteArrayToInt(fileStructure.getFileOffset()));

					aList.add(fileStructure);
				}
				// if
				// (fileStructure.getFileExtension(fileStructure.getFileNames()).equals("bmp"))
				// {
				// System.out.println("1");
				// } else if
				// (fileStructure.getFileExtension(fileStructure.getFileNames()).equals("att"))
				// {
				// System.out.println("2");
				// } else if
				// (fileStructure.getFileExtension(fileStructure.getFileNames()).equals("cod"))
				// {
				// System.out.println("3");
				// }

				cnt++;
			}

			for (int i = 0; i < numberOfFiles; i++) {
				try {
					aList.get(i).setFileSize(aList.get(i + 1).getOffset() - aList.get(i).getOffset());
				} catch (IndexOutOfBoundsException e) {
					System.out.println("reached out of index");
				}
			} // 마지막 파일 외 나머지 파일 크기구하기.

			Iterator<FileStructure> fileIterator = aList.iterator();

			while (fileIterator.hasNext()) {
				fileStructure = fileIterator.next();
				raf.seek(fileStructure.getOffset());
				raf.readFully(fileStructure.getFileData(fileStructure.getFileSize()));

				if (fileStructure.getFileExtension(fileStructure.getFileNames()).equals("bmp")) {
					ByteBuffer buf = null;
					BufferedImage img = ImageIO
							.read(new ByteArrayInputStream(fileStructure.getFileData(fileStructure.getFileSize())));
					byte[] bytes = new byte[fileStructure.getFileSize()];
					bytes = fileStructure.getFileData(fileStructure.getFileSize());
					buf.get(bytes, 0, bytes.length);
					Files.write(Paths.get(fileStructure.getFileNames()), buf.array());
				} else {
					Files.write(Paths.get(fileStructure.getFileNames()),
							fileStructure.getFileData(fileStructure.getFileSize()));
				}
				// 각 파일 이름과 데이터만 입력하면 된다
				System.out.println(fileStructure.getFileNames() + " file2 size:" + fileStructure.getFileSize());
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		new ResourceExtractor("src\\majestyUtility\\res\\maps\\MapTile.dat");
	}

}
