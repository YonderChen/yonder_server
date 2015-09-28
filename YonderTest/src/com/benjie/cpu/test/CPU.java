package com.benjie.cpu.test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;

public class CPU {
	private static final int CPUTIME = 30;

	private static final int PERCENT = 100;

	private static final int FAULTLENGTH = 10;

	public static String getCpuInfo() {
		// 操作系统
		String osName = System.getProperty("os.name");
		if (osName.toLowerCase().startsWith("windows")) {
			return "cpuRatio:" + getCpuRatioForWindows();
		} else if (osName.toLowerCase().startsWith("linux")) {
			return getCpuInfoForLinux();
		} else {
			return "unknow system!";
		}
	}

	public static double getTotalThread() {
		// 获得线程总数
		ThreadGroup parentThread;
		for (parentThread = Thread.currentThread().getThreadGroup(); 
				parentThread.getParent() != null; 
				parentThread = parentThread.getParent())
			;
		int totalThread = parentThread.activeCount();

		return totalThread;
	}

	private static String getCpuInfoForLinux() {
		InputStream is = null;
		InputStreamReader isr = null;
		BufferedReader brStat = null;
		try {
			Process process = Runtime.getRuntime().exec("top -b -n 1");
			is = process.getInputStream();
			isr = new InputStreamReader(is);
			brStat = new BufferedReader(isr);

			String cpuInfo = null;
			while ((cpuInfo = brStat.readLine()) != null) {
				if (cpuInfo.startsWith("Cpu")) {
					break;
				}
			}
			if (cpuInfo != null && cpuInfo.startsWith("Cpu")) {
				return cpuInfo;
			}
		} catch (IOException ioe) {
			System.out.println(ioe.getMessage());
		} finally {
			freeResource(is, isr, brStat);
		}
		return "";
	}

	private static void freeResource(InputStream is, InputStreamReader isr,
			BufferedReader br) {
		try {
			if (is != null)
				is.close();
			if (isr != null)
				isr.close();
			if (br != null)
				br.close();
		} catch (IOException ioe) {
			System.out.println(ioe.getMessage());
		}
	}

	/**
	 * 获得CPU使用率.
	 * 
	 * @return 返回cpu使用率
	 */
	private static double getCpuRatioForWindows() {
		try {
			String procCmd = System.getenv("windir")
					+ "\\system32\\wbem\\wmic.exe process get Caption,CommandLine,"
					+ "KernelModeTime,ReadOperationCount,ThreadCount,UserModeTime,WriteOperationCount";
			// 取进程信息
			long[] c0 = readCpu(Runtime.getRuntime().exec(procCmd));
			Thread.sleep(CPUTIME);
			long[] c1 = readCpu(Runtime.getRuntime().exec(procCmd));
			if (c0 != null && c1 != null) {
				long idletime = c1[0] - c0[0];
				long busytime = c1[1] - c0[1];
				return Double.valueOf(
						PERCENT * (busytime) / (busytime + idletime))
						.doubleValue();
			} else {
				return 0.0;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			return 0.0;
		}
	}

	/**
	 * 
	 * 读取CPU信息.
	 * 
	 * @param proc
	 */
	private static long[] readCpu(final Process proc) {
		long[] retn = new long[2];
		try {
			proc.getOutputStream().close();
			InputStreamReader ir = new InputStreamReader(proc.getInputStream());
			LineNumberReader input = new LineNumberReader(ir);
			String line = input.readLine();
			if (line == null || line.length() < FAULTLENGTH) {
				return null;
			}
			int capidx = line.indexOf("Caption");
			int cmdidx = line.indexOf("CommandLine");
			int rocidx = line.indexOf("ReadOperationCount");
			int umtidx = line.indexOf("UserModeTime");
			int kmtidx = line.indexOf("KernelModeTime");
			int wocidx = line.indexOf("WriteOperationCount");
			long idletime = 0;
			long kneltime = 0;
			long usertime = 0;
			while ((line = input.readLine()) != null) {
				if (line.length() < wocidx) {
					continue;
				}
				String caption = substring(line, capidx, cmdidx - 1)
						.trim();
				String cmd = substring(line, cmdidx, kmtidx - 1).trim();
				if (cmd.indexOf("wmic.exe") >= 0) {
					continue;
				}
				if (caption.equals("System Idle Process")
						|| caption.equals("System")) {
					idletime += Long.valueOf(
							substring(line, kmtidx, rocidx - 1).trim())
							.longValue();
					idletime += Long.valueOf(
							substring(line, umtidx, wocidx - 1).trim())
							.longValue();
					continue;
				}

				kneltime += Long.valueOf(
						substring(line, kmtidx, rocidx - 1).trim())
						.longValue();
				usertime += Long.valueOf(
						substring(line, umtidx, wocidx - 1).trim())
						.longValue();
			}
			retn[0] = idletime;
			retn[1] = kneltime + usertime;
			return retn;
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			try {
				proc.getInputStream().close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	public static void main(String[] args) throws Exception {
//		for (int i = 0; i < 10; i++) {
//			new Thread(new Runnable() {
//				
//				@Override
//				public void run() {
//					while (true) {
//						try {
//							Thread.sleep(1000);
//						} catch (InterruptedException e) {
//							// TODO Auto-generated catch block
//							e.printStackTrace();
//						}
//					}
//				}
//			}).start();
//			
//		}
		System.out.println(CPU.getCpuInfo());
		System.out.println(CPU.getTotalThread());
	}
	

    private static String substring(String src, int start_idx, int end_idx){
        byte[] b = src.getBytes();
        String tgt = "";
        for(int i=start_idx; i<=end_idx; i++){
            tgt +=(char)b[i];
        }
        return tgt;
    }
}
