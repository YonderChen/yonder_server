package com.foal.yonder.jersey.resource;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.codehaus.jettison.json.JSONObject;
/**
 * 
 * @author yonder
 * @date 2015-3-23
 */
@Path("/jersey_path")
public class DemoResource
{
	@GET
	@Path("/getHello")
	@Produces( { MediaType.APPLICATION_JSON })
	public String HelloResource()
	{
		return "Hello Jersey";
	}
	/**
	 * 处理GET请求，url路径作为参数<br>
	 * <br>
	 * url : http://localhost:8080/JerseyServerDemo/rs/jersey_path/yonder<br>
	 * result : {"username":"yonder","data":"this is a test getPathParamData by 'GET'"}<br>
	 * 
	 * @param username
	 * @return
	 */
	@GET
	@Path("{username}")
	@Produces( { MediaType.APPLICATION_JSON })
	@SuppressWarnings("finally")
	public String getPathParamData(@PathParam(value = "username") String username)
	{
		JSONObject retValue = new JSONObject();
		try
		{
			retValue.put("username", username);
			retValue.put("data", "this is a test getPathParamData by 'GET'");
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			return retValue.toString();
		}
	}
	/**
	 * 处理http的GET请求，url中?后面的参数对应QueryParam<br>
	 * <br>
	 * url : http://localhost:8080/JerseyServerDemo/rs/jersey_path/getQueryParamData?username=yonder<br>
	 * ret : {"username":"yonder","data":"this is a test getQueryParamData by 'GET'"}<br>
	 * 
	 * @param username
	 * @return
	 */
	@GET
	@Path("getQueryParamData")
	@Produces( { MediaType.APPLICATION_JSON })
	@SuppressWarnings("finally")
	public String getQueryParamData(@QueryParam(value = "username") String username)
	{
		JSONObject retValue = new JSONObject();
		try
		{
			retValue.put("username", username);
			retValue.put("data", "this is a test getQueryParamData by 'GET'");
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			return retValue.toString();
		}
	}
	/**
	 * 处理网页表单POST请求<br>
	 * <br>
	 * url : http://localhost:8080/JerseyServerDemo/rs/jersey_path/postFormData<br>
	 * param : username=yonder<br>
	 * ret : {"username":"yonder","data":"this is a test postFormData by 'GET'"}<br>
	 * 
	 * @param json
	 * @return
	 */
	@POST
	@Path("/postFormData")
	@Produces( { MediaType.APPLICATION_JSON })
	@SuppressWarnings("finally")
	public String postFormData(@FormParam(value = "username") String username)
	{
		JSONObject retValue = new JSONObject();
		try
		{
			retValue.put("username", username);
			retValue.put("data", "this is a test postFormData by 'POST'");
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			return retValue.toString();
		}
	}
	/**
	 * 处理Jersey Client发送的POST请求<br>
	 * <br>
	 * 示例 : ClientDemo
	 * ret :　{"param":{"username":"yonder"},"username":"yonder","data":"this is a test postData by 'POST'"}
	 * @param json
	 * @return
	 */
	@POST
	@Path("/postData")
	@Produces( { MediaType.APPLICATION_JSON })
	@SuppressWarnings("finally")
	public String postData(JSONObject json)
	{
		JSONObject retValue = new JSONObject();
		try
		{
			retValue.put("param", json);
			String username = json.optString("username", "default_param");
			retValue.put("username", username);
			retValue.put("data", "this is a test postData by 'POST'");
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			return retValue.toString();
		}
	}
}
