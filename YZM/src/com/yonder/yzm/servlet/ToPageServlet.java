package com.yonder.yzm.servlet;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.FileUploadBase.SizeLimitExceededException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.lang3.StringUtils;

public class ToPageServlet extends HttpServlet {
	
	private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhhmmssSSS");

	/**
	 * 
	 */
	private static final long serialVersionUID = -1776793401143421843L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		super.doGet(req, resp);
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String remoteAddr = request.getRemoteAddr();
		remoteAddr = remoteAddr.replaceAll(":", "_");
		String path = "upload_pic/remoteAddr_" + remoteAddr + "/";
		String realPath = request.getSession().getServletContext().getRealPath(path);
		File dir = new File(realPath);
		if (!dir.isDirectory()) {
			dir.mkdirs();
		}
		Date now = new Date();
		DiskFileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload sfu = new ServletFileUpload(factory);
		sfu.setHeaderEncoding("UTF-8"); // 处理中文问题
		sfu.setSizeMax(4 * 1024 * 1024); // 限制文件大小

		String desc = "";
		List<String> piclist = new ArrayList<String>(); // 放上传的图片名
		try {
			List<FileItem> fileItems = sfu.parseRequest(request); // 解码请求
			// 得到所有表单元素
			for (FileItem fi : fileItems) {
				// 有可能是 文件，也可能是普通文字
				if (fi.isFormField()) { // 这个选项是 文字
					if (StringUtils.equals("desc", fi.getFieldName())) {
						desc = fi.getString("utf-8");
					}
					// System.out.println("表单值为："+fi.getString());
				} else {
					// 是文件
					String fileName = fi.getName();
					String fileSuffix = ResourceTools.getFileSuffix(fileName);
					if (!ResourceTools.checkSuffix(fileSuffix, ResourceTools.getImageSuffixs())) {
						request.setAttribute("msg", "抱歉，图片文件格式不规范，只支持这些图片格式：" + Arrays.toString(ResourceTools.getImageSuffixs()));
						request.getRequestDispatcher("error.jsp").forward(request, response);
						return;
					}
					//重命名图片
					String newName = sdf.format(now) + "_" + piclist.size() + fileSuffix;
					fi.write(new File(realPath, newName));
					piclist.add(path + newName); // 把图片放入集合
				}
			}

		} catch (SizeLimitExceededException e) {
			e.printStackTrace();
			ServletFileUpload tempSfu = new ServletFileUpload(factory);
			tempSfu.setHeaderEncoding("UTF-8"); // 处理中文问题
			try {
				tempSfu.parseRequest(request);
				request.setAttribute("msg", "抱歉，文件太大啦，小小服务器处理不过来！");
				request.getRequestDispatcher("error.jsp").forward(request, response);
			} catch (FileUploadException e1) {
				e1.printStackTrace();
			}
			return;
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("msg", "抱歉，服务器被外星人破坏啦！");
			request.getRequestDispatcher("error.jsp").forward(request, response);
			return;
		}

		// 去显示上传的文件
		request.setAttribute("pics", piclist);
		request.setAttribute("desc", desc);
		request.getRequestDispatcher("yzm.jsp").forward(request, response);
	}
}
