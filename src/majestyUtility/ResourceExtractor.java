package majestyUtility;

/*
[Number of Files]    [File Offset 1]    [File Name 1]   [File Offset 2]   [File Name 2]   ...N 번 만큼 반복 ... [ File Data ]
        4 byte                 4 byte         24 byte           4 byte           24 byte
        
        little endian 사용
*/

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.charset.StandardCharsets;

import majestyUtility.res.Converter;
import majestyUtility.res.FileStructure;

public class ResourceExtractor {
	String fileLoc;

	int numberOfFiles; // 파일 개수
	byte[] nof = new byte[4]; // 첫 4바이트는 파일의 개수

	RandomAccessFile raf;
	File file;
	FileStructure fileStructure;

	public ResourceExtractor(String fileLoc) {
		file = new File(fileLoc);
		fileStructure = new FileStructure();

		try {
			raf = new RandomAccessFile(file, "r");
			raf.readFully(nof);
			raf.readFully(fileStructure.getFileOffset());
			raf.readFully(fileStructure.getFileName());
			// 파일의 갯수만큼 읽고 파일 오프셋과 이름을 가져온다.

			numberOfFiles = Converter.byteArrayToInt(nof);
			fileStructure.setFileNames(new String(fileStructure.getFileName(), StandardCharsets.UTF_8).trim());
			fileStructure.setOffset(Converter.byteArrayToInt(fileStructure.getFileOffset()));

			System.out.println(numberOfFiles);
			System.out.println(fileStructure.getFileOffset());
			System.out.println(fileStructure.getFileNames());

			// 파일 개수와, 첫번째 파일 오프셋, 이름을 가져온다.
			int cnt = 1;
			while (cnt < numberOfFiles) { // 파일 개수만큼 반복
				raf.readFully(fileStructure.getFileOffset());
				raf.readFully(fileStructure.getFileName());
				fileStructure.setFileNames(new String(fileStructure.getFileName(), StandardCharsets.UTF_8).trim());
				fileStructure.setOffset(Converter.byteArrayToInt(fileStructure.getFileOffset()));

				System.out.println(fileStructure.getFileOffset());
				System.out.println(fileStructure.getFileNames());
				cnt++;
			}

			// 파일 위치로가서 파일 크기만큼 데이터를 읽는다.
			// raf.seek(pOffset);
			// fileSize = nOffset - pOffset; // 파일 크기 구하기
			// pOffset = nOffset; // 이전 오프셋은 다음 오프셋으로 , 다음 오프셋을 이용해 크기구하기
			//
			// fileData = new byte[fileSize]; // 파일크기만큼 배열을 만들고
			// raf.readFully(fileData); // 파일 크기 만큼 읽어온다.

			// raf.readFully(fileOffset);
			// nOffset = Converter.byteArrayToInt(fileOffset); // 다음 파일 오프셋

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
