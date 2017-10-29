package majestyUtility;

/*
[Number of Files]    [File Offset 1]    [File Name 1]   [File Offset 2]   [File Name 2]   ...N 번 만큼 반복 ... [ File Data ]
        4 byte                 4 byte         24 byte           4 byte           24 byte
*/

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Scanner;

public class ResourceExtractor {
	String fileLoc;

	int numberOfFiles; //
	ArrayList<Integer> fileoffset;
	ArrayList<String> name;

	RandomAccessFile raf;
	File file;
	FileInputStream fileInputStream;

	public ResourceExtractor(String fileLoc) {
		file = new File(fileLoc);

		try {
			fileInputStream = new FileInputStream(file);

			raf = new RandomAccessFile(file, "r");
			numberOfFiles = raf.read();
			Byte offset = raf.readByte();
			String fileName = raf.readLine();

			System.out.println(numberOfFiles);
			System.out.println(offset);
			System.out.println(fileName);

			offset = raf.readByte();
			fileName = raf.readLine();
			System.out.println(offset);
			System.out.println(fileName);
			offset = raf.readByte();
			fileName = raf.readLine();
			System.out.println(offset);
			System.out.println(fileName);
			offset = raf.readByte();
			fileName = raf.readLine();
			System.out.println(offset);
			System.out.println(fileName);

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
