package com.benjie.log.fix;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;

public class FixTest {

	public static void main(String[] args) {
		System.out.println("aaaa\nadsfdsf");
	}
	
	public static void main1(String[] args) {
		try {
			String[] signStrs = {"gp_id_","op_time_","area_id_","mac_","plat_form_","channel_"};
			File sourceFile = new File("data.log");
			File dataFile = new File("dau.log");
			List<String> strList = FileUtils.readLines(sourceFile, "UTF-8");
			List<String> dataLines = new ArrayList<String>();
			for (String str : strList) {
				boolean isMatch = true;
				for (String signStr : signStrs) {
					int index = str.indexOf(signStr);
					if (index < 0) {
						isMatch = false;
						break;
					}
				}
				if (isMatch) {
					dataLines.add(str);
				}
			}
			if (dataLines.size() > 0) {
				FileUtils.writeLines(dataFile, dataLines);
			}
			System.out.println("write " + dataLines.size() + " line!");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
