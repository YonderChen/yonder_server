package com.foal.yonder.jersey.resource;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;

import com.google.gson.JsonObject;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.core.util.MultivaluedMapImpl;

/**
 * @author yonder
 * @date 2015-3-23
 */
public class ClientDemo {

	
	public static void main(String[] args) {
		Client client = Client.create();
		client.setConnectTimeout(2000);
		client.setReadTimeout(3000);
		WebResource resource = client.resource("http://localhost:8080/JerseyServerDemo/rs/jersey_path/postData");
		MultivaluedMap<String, String> queryParams = new MultivaluedMapImpl();
		queryParams.add("id", "23");
		JsonObject json = new JsonObject();
		json.addProperty("username", "yonder");
		String response = resource.queryParams(queryParams).type(MediaType.TEXT_HTML).post(String.class, json.toString());
		System.out.println(response);
	}
}
