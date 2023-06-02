package qss.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

import org.apache.commons.io.FileUtils;

public class FileUtil {
	@SuppressWarnings("unused")
	private int maxBufferSize = 1024 << 2;


	public String write(String filePath, InputStream is) throws Exception {

		try {
			mkDir(filePath);
			String myPath = getUniquePath(filePath);

			File targetFile = new File(myPath);
			FileUtils.copyInputStreamToFile(is, targetFile);

			return myPath;
		}
		catch(Exception ex) {
			ex.printStackTrace();
			ex.getMessage();
			return "";
		}
		finally {
			if(is != null) try { is.close(); } catch(Exception e) {}
		}
	}

	public String write(String filePath, byte[] fileData) throws Exception {
		FileOutputStream fos = null;

		try {
			mkDir(filePath);
			String myPath = getUniquePath(filePath);

			fos = new FileOutputStream(myPath);
			fos.write(fileData);

			return myPath;
		}
		catch(Exception ex) {
			ex.printStackTrace();
			ex.getMessage();
			return "";
		}
		finally {
			if (fos != null) try { fos.close(); } catch(Exception ignore) {}
		}
	}

	public static String getFileExt( final String fileName ) {
		int index = fileName.lastIndexOf(".");

		if (index == -1) {
			return "";
		}
		else {
			return fileName.substring(index+1, fileName.length());
		}
	}

	// 디렉토리 만드는 함수 상위 디렉토리가 없을시에 자동으로 생성(재귀함수)
	public boolean mkDir(String path) {
		File file = new File(path);

		if (!file.getParentFile().isDirectory()) {
			mkDir(file.getParent());
		}
		if (file.getName().indexOf('.') == -1) {
			file.mkdir();
		}
		return true;
	}

	public String getUniquePath(String path) {
		String uniquePath = "";
		File file = new File(path);

		if (file.isFile() && file.exists()) {
			int i = 1;

			while (true) {
				String fileNameNotInExt = file.getName().substring(0, file.getName().indexOf('.'));
				String fileExt = file.getName().substring(file.getName().indexOf('.') + 1);

				uniquePath = file.getParent()
						+ System.getProperty("file.separator")
						+ fileNameNotInExt + "(" + i + ")." + fileExt;

				File tempFile = new File(uniquePath);

				if (!tempFile.exists()) {
					break;
				}
				i++;
			}
		}
		else {
			uniquePath = path;
		}

		return uniquePath;
	}
}