package majestyUtility.res;

public class FileStructure {

	private int offset; // 파일 오프셋
	private String fileNames; // 파일 이름
	private int fileSize; // 파일 크기

	private byte[] fileOffset = new byte[4]; // 각 파일의 주소값
	private byte[] fileName = new byte[24]; // 파일 이름 저장할 바이트 배열
	private byte[] fileData; // 각 파일의 데이터 저장할 바이트 배열

	public int getOffset() {
		return offset;
	}

	public void setOffset(int offset) {
		this.offset = offset;
	}

	public String getFileNames() {
		return fileNames;
	}

	public void setFileNames(String fileNames) {
		this.fileNames = fileNames;
	}

	public int getFileSize() {
		return fileSize;
	}

	public void setFileSize(int fileSize) {
		this.fileSize = fileSize;
	}

	public byte[] getFileOffset() {
		return fileOffset;
	}

	public void setFileOffset(byte[] fileOffset) {
		this.fileOffset = fileOffset;
	}

	public byte[] getFileName() {
		return fileName;
	}

	public void setFileName(byte[] fileName) {
		this.fileName = fileName;
	}

	public byte[] getFileData(int fileSize) {
		fileData = new byte[fileSize];
		return fileData;
	}

	public void setFileData(byte[] fileData) {
		this.fileData = fileData;
	}

	public String getFileExtension(String fileName) {
		int indexOfPeriod = this.getFileNames().lastIndexOf('.');
		try {

			if (indexOfPeriod != -1) {
				String extension = fileName.substring(indexOfPeriod + 1);
				return extension;
			}
		} catch (Exception e) {
			System.out.println("확장자 가져오기 실패");
		}

		return "no extension";
	}

}
