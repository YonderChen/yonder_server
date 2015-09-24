package com.yonder.game.tools;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import com.google.common.io.BaseEncoding;

public class CompressTools {

	public static String uncompress(String str) throws IOException {
		if (str == null || str.length() == 0) {
			return "";
		}
		byte[] t = BaseEncoding.base64().decode(str);

		ByteArrayOutputStream out = new ByteArrayOutputStream();
		ByteArrayInputStream in = new ByteArrayInputStream(t);
		GZIPInputStream gunzip = new GZIPInputStream(in);
		try {
			byte[] buffer = new byte[256];
			int n;
			while ((n = gunzip.read(buffer)) >= 0) {
				out.write(buffer, 0, n);
			}
		} finally {
			gunzip.close();
		}
		in.close();
		out.close();

		return out.toString("UTF-8");
	}

	public static String compress(String str) throws IOException {
		if (str == null || str.length() == 0) {
			return "";
		}

		byte[] tArray;
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		GZIPOutputStream gzip = new GZIPOutputStream(out);
		try {
			gzip.write(str.getBytes("UTF-8"));
			gzip.flush();
		} finally {
			gzip.close();
		}

		tArray = out.toByteArray();
		out.close();

		return BaseEncoding.base64().encode(tArray);
	}

}
