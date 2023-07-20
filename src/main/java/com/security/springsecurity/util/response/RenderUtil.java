package com.security.springsecurity.util.response;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.security.springsecurity.util.data.DataUtil;

public class RenderUtil {
	
	public static void renderJson(HttpServletResponse response, ResponseData< DataUtil >jsonObject) {

		try {
			String json = new Gson().toJson(jsonObject);
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write(json);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
