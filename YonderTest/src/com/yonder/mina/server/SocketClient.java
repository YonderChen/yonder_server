package com.yonder.mina.server;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.net.Socket;

import org.apache.log4j.Logger;

public class SocketClient {
    
    private static final Logger logger = Logger.getLogger(SocketClient.class);

	public static void main(String[] args) {
		try {
			// 为了简单起见，所有的异常都直接往外抛
			String host = "127.0.0.1"; // 要连接的服务端IP地址
			int port = 1234; // 要连接的服务端对应的监听端口
			// 与服务端建立连接
			Socket client = new Socket(host, port);
			// 建立连接后就可以往服务端写数据了
			Writer writer = new OutputStreamWriter(client.getOutputStream());
			writer.write("H4sIAAAAAAAAAC2PS26FMAxF9+IxkZzEkIQddN4NOD+gggRROmifuvf6VW9mXZ9zZT/g6v2G+QF7X7b2lmEGV4MPmFFpDl6Rt17Fqkl5JM1TRkwUYABOqX+1+/37LDBrRJToKvys0AM0PiSG+77zjEKv5eqv1dX38rIGSP04uMnCSIMd4IRZinZui9g/q5gHJxlNyGOoKalQS1WkY5KjMCsmb/QUtbHOCpxWbq3sIsTSPrYiUT//vyIyNcbgVCFLiqotirNG5SIXU8k5EzT8DvC5LU3wiXJA9DmP1mZyIY3eM2djE/kyPdE/o3zjkzsBAAA=\r\n");
			writer.flush();
			writer.write("H4sIAAAAAAAAAC2PS26FMAxF9+IxkZzEkIQddN4NOD+gggRROmifuvf6VW9mXZ9zZT/g6v2G+QF7X7b2lmEGV4MPmFFpDl6Rt17Fqkl5JM1TRkwUYABOqX+1+/37LDBrRJToKvys0AM0PiSG+77zjEKv5eqv1dX38rIGSP04uMnCSIMd4IRZinZui9g/q5gHJxlNyGOoKalQS1WkY5KjMCsmb/QUtbHOCpxWbq3sIsTSPrYiUT//vyIyNcbgVCFLiqotirNG5SIXU8k5EzT8DvC5LU3wiXJA9DmP1mZyIY3eM2djE/kyPdE/o3zjkzsBAAA=\r\n");
			writer.flush();
			writer.write("H4sIAAAAAAAAAC2PS26FMAxF9+IxkZzEkIQddN4NOD+gggRROmifuvf6VW9mXZ9zZT/g6v2G+QF7X7b2lmEGV4MPmFFpDl6Rt17Fqkl5JM1TRkwUYABOqX+1+/37LDBrRJToKvys0AM0PiSG+77zjEKv5eqv1dX38rIGSP04uMnCSIMd4IRZinZui9g/q5gHJxlNyGOoKalQS1WkY5KjMCsmb/QUtbHOCpxWbq3sIsTSPrYiUT//vyIyNcbgVCFLiqotirNG5SIXU8k5EzT8DvC5LU3wiXJA9DmP1mZyIY3eM2djE/kyPdE/o3zjkzsBAAA=\r\n");
			writer.flush();
			writer.write("H4sIAAAAAAAAAC2PS26FMAxF9+IxkZzEkIQddN4NOD+gggRROmifuvf6VW9mXZ9zZT/g6v2G+QF7X7b2lmEGV4MPmFFpDl6Rt17Fqkl5JM1TRkwUYABOqX+1+/37LDBrRJToKvys0AM0PiSG+77zjEKv5eqv1dX38rIGSP04uMnCSIMd4IRZinZui9g/q5gHJxlNyGOoKalQS1WkY5KjMCsmb/QUtbHOCpxWbq3sIsTSPrYiUT//vyIyNcbgVCFLiqotirNG5SIXU8k5EzT8DvC5LU3wiXJA9DmP1mZyIY3eM2djE/kyPdE/o3zjkzsBAAA=\r\n");
			writer.flush();
			writer.write("H4sIAAAAAAAAAC2PS26FMAxF9+IxkZzEkIQddN4NOD+gggRROmifuvf6VW9mXZ9zZT/g6v2G+QF7X7b2lmEGV4MPmFFpDl6Rt17Fqkl5JM1TRkwUYABOqX+1+/37LDBrRJToKvys0AM0PiSG+77zjEKv5eqv1dX38rIGSP04uMnCSIMd4IRZinZui9g/q5gHJxlNyGOoKalQS1WkY5KjMCsmb/QUtbHOCpxWbq3sIsTSPrYiUT//vyIyNcbgVCFLiqotirNG5SIXU8k5EzT8DvC5LU3wiXJA9DmP1mZyIY3eM2djE/kyPdE/o3zjkzsBAAA=\r\n");
			writer.flush();
			writer.write("H4sIAAAAAAAAAC2PS26FMAxF9+IxkZzEkIQddN4NOD+gggRROmifuvf6VW9mXZ9zZT/g6v2G+QF7X7b2lmEGV4MPmFFpDl6Rt17Fqkl5JM1TRkwUYABOqX+1+/37LDBrRJToKvys0AM0PiSG+77zjEKv5eqv1dX38rIGSP04uMnCSIMd4IRZinZui9g/q5gHJxlNyGOoKalQS1WkY5KjMCsmb/QUtbHOCpxWbq3sIsTSPrYiUT//vyIyNcbgVCFLiqotirNG5SIXU8k5EzT8DvC5LU3wiXJA9DmP1mZyIY3eM2djE/kyPdE/o3zjkzsBAAA=\r\n");
			writer.flush();
			writer.write("H4sIAAAAAAAAAC2PS26FMAxF9+IxkZzEkIQddN4NOD+gggRROmifuvf6VW9mXZ9zZT/g6v2G+QF7X7b2lmEGV4MPmFFpDl6Rt17Fqkl5JM1TRkwUYABOqX+1+/37LDBrRJToKvys0AM0PiSG+77zjEKv5eqv1dX38rIGSP04uMnCSIMd4IRZinZui9g/q5gHJxlNyGOoKalQS1WkY5KjMCsmb/QUtbHOCpxWbq3sIsTSPrYiUT//vyIyNcbgVCFLiqotirNG5SIXU8k5EzT8DvC5LU3wiXJA9DmP1mZyIY3eM2djE/kyPdE/o3zjkzsBAAA=\r\n");
			writer.flush();
			writer.write("H4sIAAAAAAAAAC2PS26FMAxF9+IxkZzEkIQddN4NOD+gggRROmifuvf6VW9mXZ9zZT/g6v2G+QF7X7b2lmEGV4MPmFFpDl6Rt17Fqkl5JM1TRkwUYABOqX+1+/37LDBrRJToKvys0AM0PiSG+77zjEKv5eqv1dX38rIGSP04uMnCSIMd4IRZinZui9g/q5gHJxlNyGOoKalQS1WkY5KjMCsmb/QUtbHOCpxWbq3sIsTSPrYiUT//vyIyNcbgVCFLiqotirNG5SIXU8k5EzT8DvC5LU3wiXJA9DmP1mZyIY3eM2djE/kyPdE/o3zjkzsBAAA=\r\n");
			writer.flush();
			client.close();
			Thread.sleep(1000);
//			StringBuffer sb = new StringBuffer();
//			byte[] buf = new byte[8192];
//			int len;
//			int index = 0;
//			while ((len = client.getInputStream().read(buf)) != -1) {
//				String str = new String(buf, index, len);
//				index = len;
//				sb.append(str);
//				if (str.endsWith("\r\n") || str.endsWith("\n")) {
//				    logger.error("str:" + str);
//					buf = new byte[8192];
//					index = 0;
//				}
//			}
//			logger.error(sb.toString());
			 Thread.sleep(50000000);
			// client.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
