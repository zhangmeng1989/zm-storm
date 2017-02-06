package com.zm.strorm.es.test;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;

/**
 * Created by ZXL on 2016/12/23.
 */
public class ReadFromFile {
	public static String ReadTxtFile(String FileName) throws Exception {
		BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(FileName));
		ByteArrayOutputStream memStream = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int len = 0;
		while ((len = bufferedInputStream.read(buffer)) != -1) {
			memStream.write(buffer, 0, len);
		}
		byte[] data = memStream.toByteArray();
		bufferedInputStream.close();
		memStream.close();
		bufferedInputStream.close();
		return new String(data);
	}
}