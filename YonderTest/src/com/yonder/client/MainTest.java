package com.yonder.client;

import java.io.File;
import java.io.FileOutputStream;

import com.yonder.tools.MD5Tools;

/**
 * @author cyd
 * @date 2015-2-11
 */
public class MainTest {
	public static void main(String[] args) {
		test("eclipse.rar");
//		writeFile();
	}
	
	public static void test(String fileName) {
		File file1 = new File("f:\\" + fileName);
		File file2 = new File("C:\\Users\\Administrator\\Downloads\\" + fileName);
		try {
			System.out.println(MD5Tools.getFileMD5(file1));
			System.out.println(MD5Tools.getFileMD5(file2));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void writeFile() {
		try {
			File file = new File("F:\\line.txt");
			FileOutputStream fOut = new FileOutputStream(file);
			for (int i = 0; i < 300000; i++) {
				String line = i + "---------\r\n";
				fOut.write(line.getBytes());
			}
		} catch (Exception e) {
		}
	}
}
