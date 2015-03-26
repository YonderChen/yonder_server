package com.foal.yonder.jersey.resource;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUpload;
import org.apache.commons.fileupload.RequestContext;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.fileupload.servlet.ServletRequestContext;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
/**
 * 
 * @author yonder
 * @date 2015-3-23
 */
@SuppressWarnings("unchecked")
@Path("/jersey_path")
public class DemoResource
{
	/**
	 * 处理GET请求<br>
	 * <br>
	 * url : http://localhost:8080/JerseyServerDemo/rs/jersey_path/getHello<br>
	 * result : Hello Jersey<br>
	 */
	@GET
	@Path("/getHello")
	@Produces( { MediaType.TEXT_HTML })
	public String HelloResource()
	{
		return "Hello Jersey";
	}
	/**
	 * 处理GET请求，url路径作为参数<br>
	 * <br>
	 * url : http://localhost:8080/JerseyServerDemo/rs/jersey_path/user/yonder<br>
	 * result : {"username":"yonder","data":"this is a test getPathParamData by 'GET'"}<br>
	 */
	@GET
	@Path("/user/{username}")
	@Produces( { MediaType.TEXT_HTML })
	public String getPathParamData(@PathParam(value = "username") String username)
	{
		JsonObject retValue = new JsonObject();
		retValue.addProperty("username", username);
		retValue.addProperty("data", "this is a test getPathParamData by 'GET'");
		return retValue.toString();
	}
	/**
	 * 处理http的GET请求，url中?后面的参数对应QueryParam<br>
	 * <br>
	 * url : http://localhost:8080/JerseyServerDemo/rs/jersey_path/getQueryParamData?username=yonder<br>
	 * ret : {"username":"yonder","data":"this is a test getQueryParamData by 'GET'"}<br>
	 */
	@GET
	@Path("/getQueryParamData")
	@Produces( { MediaType.TEXT_HTML })
	public String getQueryParamData(@QueryParam(value = "username") String username)
	{
		JsonObject retValue = new JsonObject();
		retValue.addProperty("username", username);
		retValue.addProperty("data", "this is a test getQueryParamData by 'GET'");
		return retValue.toString();
	}
	/**
	 * 处理网页表单POST请求<br>
	 * <br>
	 * url : http://localhost:8080/JerseyServerDemo/rs/jersey_path/postFormData<br>
	 * param : username=yonder<br>
	 * ret : {"username":"yonder","data":"this is a test postFormData by 'GET'"}<br>
	 */
	@POST
	@Path("/postFormData")
	@Produces( { MediaType.TEXT_HTML })
	public String postFormData(@FormParam(value = "username") String username)
	{
		JsonObject retValue = new JsonObject();
		retValue.addProperty("username", username);
		retValue.addProperty("data", "this is a test postFormData by 'POST'");
		return retValue.toString();
	}
	/**
	 * 处理Jersey Client发送的POST请求<br>
	 * <br>
	 * 示例 : ClientDemo
	 * ret :　{"id":23,"param":{"username":"yonder"},"username":"yonder","data":"this is a test postData by 'POST'"}
	 */
	@POST
	@Path("/postData")
	@Produces( { MediaType.TEXT_HTML })
	public String postData(@QueryParam(value = "id") int id, String param)
	{
		JsonObject paramJson = new JsonParser().parse(param).getAsJsonObject();
		JsonObject retValue = new JsonObject();
		retValue.addProperty("id", id);
		retValue.addProperty("param", param);
		String username = paramJson.get("username") == null ? "" : paramJson.get("username").getAsString();
		retValue.addProperty("username", username);
		retValue.addProperty("data", "this is a test postData by 'POST'");
		return retValue.toString();
	}
	
	public static final String FileUploadPath = "E:/fileUploadDir/";
	
	/**
	 * 上传文件
	 * @param request
	 * @return
	 */
	@POST
	@Path("/uploadFile")
	@Produces(MediaType.TEXT_HTML)
	public String uploadStatePolicy(@Context HttpServletRequest request) {
		try {
			request.setCharacterEncoding("utf-8");
			RequestContext requestContext = new ServletRequestContext(request);

			if (FileUpload.isMultipartContent(requestContext)) {

				DiskFileItemFactory factory = new DiskFileItemFactory();
				File tmpDir = new File(FileUploadPath);
				if (!tmpDir.isDirectory()) {
					tmpDir.mkdir();
				}
				factory.setRepository(tmpDir);
				ServletFileUpload upload = new ServletFileUpload(factory);
				// upload.setHeaderEncoding("gbk");
				upload.setSizeMax(2000000);
				List items = new ArrayList();
				items = upload.parseRequest(request);

				Iterator<?> it = items.iterator();
				while (it.hasNext()) {
					FileItem fileItem = (FileItem) it.next();
					if (fileItem.isFormField()) {
						System.out.println(fileItem.getFieldName() + " " + fileItem.getName() + " " + new String(fileItem.getString()));
					} else {
						System.out.println(fileItem.getFieldName() + " " + fileItem.getName() + " " + fileItem.isInMemory() + " " + fileItem.getContentType() + " " + fileItem.getSize());

						if (fileItem.getName() != null && fileItem.getSize() != 0) {
							File fullFile = new File(fileItem.getName());
							File newFile = new File(FileUploadPath + fullFile.getName());
							fileItem.write(newFile);
						} else {
							System.out.println("文件没有选择 或 文件内容为空");
						}
					}

				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "上传成功";
	}
}
