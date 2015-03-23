package com.foal.yonder.jersey.resource;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.core.util.MultivaluedMapImpl;

/**
 * @author yonder
 * @date 2015-3-23
 */
public class ClientDemo {

	
	public static void main(String[] args) {
		try {
			Client client = Client.create();
			client.setConnectTimeout(2000);
			client.setReadTimeout(3000);
			WebResource resource = client.resource("http://localhost:8080/JerseyServerDemo/rs/jersey_path/postData");
			MultivaluedMap<String, String> queryParams = new MultivaluedMapImpl();
			queryParams.add("username", "yondeaar");
			JSONObject json = new JSONObject();
			json.put("username", "yonder");
			JSONObject response = resource.queryParams(queryParams).type(MediaType.APPLICATION_JSON).post(JSONObject.class, json);
			System.out.println(response);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
