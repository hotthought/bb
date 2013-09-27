package com.enjoyor.common.util;
import java.util.Date;
import java.util.List;
import java.util.Map;

import java.util.Iterator;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

public class JsonUtil {
	
	/**
	 * 通过List生成JSON数据
	 * @param beanList 包含bean对象的集合
	 * @return 生成的JSON数据
	 */
	public static String getJsonFromList(List beanList, String dateFormat){
		JsonConfig jsonConfig = new JsonConfig(); 
		jsonConfig.registerJsonValueProcessor(java.util.Date.class,new JsonDateValueProcessor(dateFormat)); 
		JSONArray jsonArray = JSONArray.fromObject(beanList, jsonConfig);
		return jsonArray.toString();
	}
	
	/**
	 * 通过对象数组生成JSON数据
	 * @param beanArray 包含bean对象的集合
	 * @return 生成的JSON数据
	 */
	public static String getJsonFromArrayObj(Object[] beanArray){
		JSONArray jsonArray = JSONArray.fromObject(beanArray);
		return jsonArray.toString();
	}
	
	/**
	 * 通过bean生成JSON数据
	 * @param bean bean对象
	 * @return 生成的JSON数据
	 */
	public static String getJsonFromBean(Object bean, String dateFormat){
		JsonConfig jsonConfig = new JsonConfig(); 
		jsonConfig.registerJsonValueProcessor(java.util.Date.class,new JsonDateValueProcessor(dateFormat)); 
		
		JSONObject JsonObject = JSONObject.fromObject(bean, jsonConfig);
		return JsonObject.toString();
	}
	

}