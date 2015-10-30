package com.yonder.mina.server;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.apache.mina.core.session.IoSession;
import org.apache.mina.handler.stream.StreamIoHandler;

/**
 * 
 * @author cyd
 * @date 2015-2-5
 */
public class MinaStreamIOHandler extends StreamIoHandler {

	public static final int BUFFER_SIZE = 1024*512;
	
	// 设定一个线程池
	// 参数说明：最少数量3，最大数量6 空闲时间 3秒
	private static ThreadPoolExecutor threadPool = new ThreadPoolExecutor(3, 6, 3, TimeUnit.SECONDS,
			// 缓冲队列为3
			new ArrayBlockingQueue<Runnable>(3),
			// 抛弃旧的任务
			new ThreadPoolExecutor.DiscardOldestPolicy());

	@Override
	public void sessionCreated(IoSession session) throws Exception {
		super.sessionCreated(session);
		System.out.println("sessionCreated:" + System.currentTimeMillis() + " - remote address:" + session.getRemoteAddress());
	}

	@Override
	public void sessionClosed(IoSession session) throws Exception {
		super.sessionClosed(session);
		System.out.println("sessionClosed:" + System.currentTimeMillis() + " - remote address:" + session.getRemoteAddress());
	}

	@Override
	public void sessionOpened(IoSession session) {
		System.out.println("sessionOpened start:" + System.currentTimeMillis() + " - remote address:" + session.getRemoteAddress());
		super.sessionOpened(session);
		System.out.println("sessionOpened end:" + System.currentTimeMillis() + " - remote address:" + session.getRemoteAddress());
	}

	protected void processStreamIo(final IoSession session, final InputStream in, final OutputStream out) {
		System.out.println("processStreamIo start:" + System.currentTimeMillis() + " - remote address:" + session.getRemoteAddress());
		// 将线程放入线程池 当连接很多时候可以通过线程池处理
		threadPool.execute(new Runnable() {
			
			public void run() {
		        System.out.println("server work start:" + System.currentTimeMillis());
		        try {   
		            byte[] inBuff = new byte[BUFFER_SIZE];
		            int pathLengh = in.read(inBuff);
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
		            FileInputStream fin = new FileInputStream(sendFile);  
		            System.out.println("write file start:" + System.currentTimeMillis());
		            byte[] bufferByte = new byte[BUFFER_SIZE];   
		            int dataLength = 0;
		            int len = 0;   
		            long lastPrintTime = 0;
		            while((len = fin.read(bufferByte)) != -1 ){   
		            	long nowTime = System.currentTimeMillis();
		            	if (nowTime - lastPrintTime > 1000) {
		            		System.out.println("write file to client...:" + System.currentTimeMillis());
		            		lastPrintTime = nowTime;
						}
		                out.write(bufferByte, 0, len);   
		                out.flush();
		                dataLength += len;
		            }   
		            fin.close();
		            System.out.println("dataLength:" + dataLength);
		            System.out.println("write file end:" + System.currentTimeMillis());
		        } catch (Exception e) {   
		            e.printStackTrace();   
		        }finally{   
		    		session.close(false).awaitUninterruptibly();
		            System.out.println("session close:" + System.currentTimeMillis());
		            System.out.println("server work end:" + System.currentTimeMillis());
		        }   
			}
		});
		// 直接启动线程 连接很少可以选用下面
		// new ServerWork(session, in, out).start();
		System.out.println("processStreamIo end:" + System.currentTimeMillis() + " - remote address:" + session.getRemoteAddress());
	}
}