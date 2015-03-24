package com.foal.yonder.jersey.resource;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
/**
 * 
 * @author yonder
 * @date 2015-3-23
 */
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
}
