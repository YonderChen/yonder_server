package com.yonder.sound;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @author cyd
 * @date 2015-4-1
 */
public class MainTest {
	
	public static void main(String[] args) throws IOException {

		BufferedInputStream in = new BufferedInputStream(new FileInputStream(new File("E:\\sound\\1.mp3")));
		BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream("E:\\sound\\111.mp3"));
		byte[] byt = new byte[1024];
		int len;
		int index = 0;
		while ((len = in.read(byt)) != -1) {
			if (index > 1024*50) {
				out.write(byt, 0, len);
				out.flush();
			}
			index += len;
			if (index > 1024*61) {
				break;
			}
		}
		in.close();
		out.close();
	}
}
