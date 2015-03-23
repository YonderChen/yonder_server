package com.foal.yonder.imageserver.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class B64
{
	// 加密
	public static String getBase64(String str)
	{
		byte[] b = null;
		String s = null;
		try
		{
			b = str.getBytes("utf-8");
		}
		catch(UnsupportedEncodingException e)
		{
			e.printStackTrace();
		}
		if(b != null)
		{
			s = new BASE64Encoder().encode(b);
		}
		return s;
	}

	// 解密
	public static String getFromBase64(String s)
	{
		byte[] b = null;
		String result = null;
		if(s != null)
		{
			BASE64Decoder decoder = new BASE64Decoder();
			try
			{
				b = decoder.decodeBuffer(s);
				result = new String(b, "utf-8");
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
		return result;
	}

	//手动实现base64编码/解码
	private static char[] base64EncodeChars = new char[] { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j',
			'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '+', '/' };

	private static byte[] base64DecodeChars = new byte[] { -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 62, -1,
			-1, -1, 63, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, -1, -1, -1, -1, -1, -1, -1, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, -1, -1, -1, -1, -1, -1, 26, 27, 28, 29, 30, 31, 32,
			33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, -1, -1, -1, -1, -1 };

	// 编码(encode)
	public static String encode(byte[] data)
	{
		StringBuffer buff = new StringBuffer();
		int len = data.length;
		int i = 0;
		int byte1, byte2, byte3;
		while(i < len)
		{
			byte1 = data[i++] & 0xff;
			if(i == len)
			{
				buff.append(base64EncodeChars[byte1 >>> 2]);
				buff.append(base64EncodeChars[(byte1 & 0x3) << 4]);
				buff.append("==");
				break;
			}
			byte2 = data[i++] & 0xff;
			if(i == len)
			{
				buff.append(base64EncodeChars[byte1 >>> 2]);
				buff.append(base64EncodeChars[((byte1 & 0x03) << 4) | ((byte2 & 0xf0) >>> 4)]);
				buff.append(base64EncodeChars[(byte2 & 0x0f) << 2]);
				buff.append("=");
				break;
			}
			byte3 = data[i++] & 0xff;
			buff.append(base64EncodeChars[byte1 >>> 2]);
			buff.append(base64EncodeChars[((byte1 & 0x03) << 4) | ((byte2 & 0xf0) >>> 4)]);
			buff.append(base64EncodeChars[((byte2 & 0x0f) << 2) | ((byte3 & 0xc0) >>> 6)]);
			buff.append(base64EncodeChars[byte3 & 0x3f]);
		}
		return buff.toString();
	}

	// 解码(decode)
	public static byte[] decode(String str) throws UnsupportedEncodingException
	{
		StringBuffer buff = new StringBuffer();
		byte[] data = str.getBytes("US-ASCII");
		int len = data.length;
		int i = 0;
		int byte1, byte2, byte3, byte4;
		while(i < len)
		{
			/* byte1 */
			do
			{
				byte1 = base64DecodeChars[data[i++]];
			}
			while(i < len && byte1 == -1);
			if(byte1 == -1)
				break;
			/* byte2 */
			do
			{
				byte2 = base64DecodeChars[data[i++]];
			}
			while(i < len && byte2 == -1);
			if(byte2 == -1)
				break;
			buff.append((char) ((byte1 << 2) | ((byte2 & 0x30) >>> 4)));
			/* byte3 */
			do
			{
				byte3 = data[i++];
				if(byte3 == 61)
					return buff.toString().getBytes("iso8859-1");
				byte3 = base64DecodeChars[byte3];
			}
			while(i < len && byte3 == -1);
			if(byte3 == -1)
				break;
			buff.append((char) (((byte2 & 0x0f) << 4) | ((byte3 & 0x3c) >>> 2)));
			/* byte4 */
			do
			{
				byte4 = data[i++];
				if(byte4 == 61)
					return buff.toString().getBytes("iso8859-1");
				byte4 = base64DecodeChars[byte4];
			}
			while(i < len && byte4 == -1);
			if(byte4 == -1)
				break;
			buff.append((char) (((byte3 & 0x03) << 6) | byte4));
		}
		return buff.toString().getBytes("iso8859-1");
	}
	
	/**
	 * 对字节数组字符串进行Base64解码并生成图片
	 * imgStr Base64码
	 * imgFilePath 图片文件保存地址
	 */
	public static boolean GenerateImage(String imgStr, String imgFilePath) 
	{
        if (imgStr == null) // 图像数据为空
            return false;
        BASE64Decoder decoder = new BASE64Decoder();
        try 
        {
            // Base64解码
            byte[] bytes = decoder.decodeBuffer(imgStr);
            for (int i = 0; i < bytes.length; ++i) 
            {
                if (bytes[i] < 0) 
                {
                	// 调整异常数据
                    bytes[i] += 256;
                }
            }
            // 生成jpeg图片
            OutputStream out = new FileOutputStream(imgFilePath);
            out.write(bytes);
            out.flush();
            out.close();
            return true;
        }
        catch (Exception e) 
        {
        	e.printStackTrace();
            return false;
        }
    }
	
	public static String GetImageStr(File imgFile) 
	{
		// 将图片文件转化为字节数组字符串，并对其进行Base64编码处理
		byte[] data = null;
		// 读取图片字节数组
		try 
		{
			InputStream in = new FileInputStream(imgFile);
			data = new byte[in.available()];
			in.read(data);
			in.close();
		} catch (IOException e) 
		{
			e.printStackTrace();
		}
	
		// 对字节数组Base64编码
		BASE64Encoder encoder = new BASE64Encoder();
		return encoder.encode(data);// 返回Base64编码过的字节数组字符串
	}

	public static String GetImageStr(String imgFilePath) 
	{
		// 将图片文件转化为字节数组字符串，并对其进行Base64编码处理
		byte[] data = null;
		// 读取图片字节数组
		try 
		{
			InputStream in = new FileInputStream(imgFilePath);
			data = new byte[in.available()];
			in.read(data);
			in.close();
		} catch (IOException e) 
		{
			e.printStackTrace();
		}
	
		// 对字节数组Base64编码
		BASE64Encoder encoder = new BASE64Encoder();
		return encoder.encode(data);// 返回Base64编码过的字节数组字符串
	}
}
