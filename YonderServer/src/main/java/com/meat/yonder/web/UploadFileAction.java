package com.meat.yonder.web;

import java.io.File;

import org.apache.struts2.convention.annotation.Action;

import com.meat.yonder.bean.AjaxBean;
import com.meat.yonder.config.Constant;
import com.meat.yonder.util.FileUtil;

public class UploadFileAction extends BaseAction {
	private static final long serialVersionUID = -5344633781867255449L;

	private File swfupload;
	private String swfuploadFileName;
	
	@Action("upload")
	public String upload() {
		String savePath = Constant.DATA_LOGO_SAVE_PATH_VALUE;
		String webUrl = Constant.DATA_LOGO_WEB_PATH_VALUE;
		String fileName = null;
		if (this.swfupload != null) {
			if (this.swfupload.length() != 0 && this.swfupload.length() <= Constant.FILE_MAX_SIZE) {
				try {
					fileName = FileUtil.uploadFile(this.getSwfupload(), savePath, this.swfuploadFileName);
				} catch (Exception e) {
					File file = new File(savePath + "/" + fileName);
					if (file.exists()) {
						file.delete();
					}
					ajaxBean = new AjaxBean(false, "上传失败,失败原因:" + e.getMessage());
					this.ajaxWrite(ajaxBean);
					return null;
				}
			} else {
				ajaxBean = new AjaxBean(false, "大小不能超过10M.");
				this.ajaxWrite(ajaxBean);
				return null;
			}
		}
		ajaxBean = new AjaxBean(true, "上传成功.");
		ajaxBean.setFilePath(savePath + "/" + fileName);
		ajaxBean.setWebUrl(webUrl + "/" + fileName);
		ajaxBean.setOldFileName(this.swfuploadFileName);
		ajaxBean.setNewFileName(fileName);
		this.ajaxWrite(ajaxBean);
		return null;
	}

	public File getSwfupload() {
		return swfupload;
	}

	public void setSwfupload(File swfupload) {
		this.swfupload = swfupload;
	}

	public String getSwfuploadFileName() {
		return swfuploadFileName;
	}

	public void setSwfuploadFileName(String swfuploadFileName) {
		this.swfuploadFileName = swfuploadFileName;
	}
	
}
