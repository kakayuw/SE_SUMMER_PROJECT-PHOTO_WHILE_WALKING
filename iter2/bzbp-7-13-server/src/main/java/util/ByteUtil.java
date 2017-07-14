package util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.StringBufferInputStream;

import javax.imageio.stream.FileImageInputStream;

public class ByteUtil {
	public static byte[] FileToString(File file) {
		byte[] ret = null;
		try {
			FileImageInputStream fiStream = new FileImageInputStream(file);
			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			byte[] buffer = new byte[1024];
			int n;
			while ((n = fiStream.read(buffer)) != -1) {
				outputStream.write(buffer, 0, n);
			}
			ret = outputStream.toByteArray();
			fiStream.close();
			outputStream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ret;
	}

	public static byte[] StringToByte(String str) {
		byte[] ret = null;
		try {
			StringBufferInputStream inputStream = new StringBufferInputStream(str);
			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			byte[] buffer = new byte[1024];
			int n;
			while ((n = inputStream.read(buffer)) != -1) {
				outputStream.write(buffer, 0, n);
			}
			ret = outputStream.toByteArray();
			inputStream.close();
			outputStream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ret;
	}
}
