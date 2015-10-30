package com.yonder.mina.server;

import java.io.File;
import java.io.FileInputStream;

import org.apache.log4j.Logger;
import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;


/**
 * 
 * @author jackyli515
 */
public class SocketFileServerHandler extends IoHandlerAdapter {
	private static final Logger logger = Logger.getLogger(SocketFileServerHandler.class);
    public static final int BUFFER_SIZE = 1024*512;

	@Override
	public void messageReceived(IoSession session, Object message) {
		try {
        	//正常处理业务（）
			IoBuffer buffer = (IoBuffer) message;
			System.out.println(buffer);
            byte[] inBuff = new byte[buffer.limit()];
            buffer.get(inBuff);
            int pathLengh = inBuff.length;
        	if (pathLengh <= 0) {
				throw new RuntimeException();
			}
        	byte[] paramByte = new byte[pathLengh];
        	for (int i = 0; i < pathLengh; i++) {
				paramByte[i] = inBuff[i];
			}
            String paramStr = new String(paramByte, "utf-8");
            System.out.println("paramStr:" + paramStr);
            FileStreamParam param = FileStreamParam.getParam(paramStr, session);
            //此处路径如何动态设定。   
            String filePath = "Constants.ResourceFileDir" + param.getString("fileName");
            System.out.println("filePath:" + filePath);
            File sendFile = new File(filePath);
            FileInputStream fis = new FileInputStream(sendFile);  
            System.out.println("write file start:" + System.currentTimeMillis());
            byte[] bufferByte = new byte[BUFFER_SIZE];
            int dataLength = 0;
            int len = 0;   
            int i = 0;

			IoBuffer ioBuffer = IoBuffer.allocate(BUFFER_SIZE);
			ioBuffer.setAutoExpand(true);
            while((len = fis.read(bufferByte)) != -1 ){   
				ioBuffer.put(bufferByte, 0, len);
                dataLength += len;
                i++;
            }
			ioBuffer.flip(); 
        	session.write(ioBuffer); 
            fis.close();
            System.out.println("file length:" + dataLength);
            System.out.println("wirte count:" + i);
        } catch(Exception ex){  
        	ex.printStackTrace();
        	logger.error("error",ex);
        } finally {
        	
        }
	}

	@Override
	public void sessionOpened(IoSession session) throws Exception {
		super.sessionOpened(session);
	}

	@Override
	public void sessionCreated(IoSession session) throws Exception {
		super.sessionCreated(session);
	}

	@Override
	public void sessionClosed(IoSession session) throws Exception {
		System.out.println("close----------------------------------------------" + System.currentTimeMillis());
		super.sessionClosed(session);
	}

	@Override
	public void exceptionCaught(IoSession session, Throwable cause)
			throws Exception {
		super.exceptionCaught(session, cause);
		session.close(true);
	}

	@Override
	public void messageSent(IoSession session, Object message) throws Exception {
		super.messageSent(session, message);
		session.close(true);
	}

	@Override
	public void sessionIdle(IoSession session, IdleStatus status)
			throws Exception {
		super.sessionIdle(session, status);
		session.close(true);
	}

}
