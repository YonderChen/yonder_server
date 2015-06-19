package com.yonder.sound;
/**
 * @author cyd
 * @date 2015-5-6
 */
public class MainUniteWavTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		SoundTools.uniteWavFile(new String[]{"E:\\sound\\result\\e4.mp3", "E:\\sound\\result\\e5.mp3"}, "E:\\sound\\result\\eUnitWav.mp3");
	}

}
