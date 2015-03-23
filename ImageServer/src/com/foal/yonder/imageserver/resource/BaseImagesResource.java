package com.foal.yonder.imageserver.resource;

import java.io.File;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.apache.commons.lang.StringUtils;
import org.codehaus.jettison.json.JSONObject;

import com.foal.yonder.imageserver.util.B64;

@Path("/base_images")
public class BaseImagesResource
{
	/**
	 * 
	 * @param json.imgPath, json.imgStr,json.imgType,json.imgName
	 * @return 0:success, -1:fail格式不对
	 * @see eg. {"imgPath":"sfz","imgStr":"--","imgType":"jpg","imgName":"123",}
	 */
	@POST
	@Path("/saveImage")
	@Produces( { MediaType.APPLICATION_JSON })
	@SuppressWarnings("finally")
	public String saveImage(JSONObject json)
	{
		String imagesPath = getClass().getResource("").getPath();
		JSONObject retValue = new JSONObject();
		try
		{
			imagesPath = java.net.URLDecoder.decode(imagesPath, "UTF-8");
			imagesPath = imagesPath.split("ROOT")[0]+"images"+File.separator;
			String imgPath = json.optString("imgPath", "0");
			String imgStr = json.optString("imgStr", "0");
			String imgType = json.optString("imgType", "0");
			String imgName = json.optString("imgName", "0");
			String ret = "0";
			if(StringUtils.isBlank(imgPath)||"0".equals(imgPath))
			{
				ret = "-1";
			}
			if(StringUtils.isBlank(imgStr)||"0".equals(imgStr))
			{
				ret = "-1";
			}
			if(StringUtils.isBlank(imgType)||"0".equals(imgType))
			{
				ret = "-1";
			}
			if(StringUtils.isBlank(imgName)||"0".equals(imgName))
			{
				ret = "-1";
			}
			if(!"-1".equals(ret))
			{
				String photoPath = imagesPath + imgPath + File.separator + imgName + "." +imgType;
				File file = new File(photoPath);
				if(!file.getParentFile().exists())
				{
					file.getParentFile().mkdirs();
				}
				else if(file.exists())
				{
					file.delete();  
				}
				B64.GenerateImage(imgStr, photoPath);
				ret = "0";
				
			}
			retValue.put("ret", ret);
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
