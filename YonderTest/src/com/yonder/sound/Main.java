package com.yonder.sound;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author cyd
 * @date 2015-4-1
 */
public class Main {
	
	
	public static void and(List<BufferedInputStream> inputs, BufferedOutputStream out) throws Exception {
		byte[] byt = new byte[1024];
		int len;
		for (BufferedInputStream input : inputs) {
			while ((len = input.read(byt)) != -1) {
				out.write(byt, 0, len);
				out.flush();

			}
			input.close();
		}
		out.close();
	}

	public static void main(String[] args) throws Exception {

		Random random = new Random();
		List<BufferedInputStream> inputs = new ArrayList<BufferedInputStream>();
		for (int i = 0; i < 100; i++) {
			int soundIndex = random.nextInt(83) + 2;
			System.out.println(soundIndex);
			inputs.add(new BufferedInputStream(new FileInputStream(new File("E:\\sound\\piano\\" + soundIndex + ".mp3"))));
		}
		BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(new File("E://sound/C.mp3")));
		
		and(inputs, out);
	}
}
