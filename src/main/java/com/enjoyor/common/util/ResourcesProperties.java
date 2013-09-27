package com.enjoyor.common.util;

import java.util.Map;

/**
 * 
 * @date : 2012-12-7 下午2:05:07
 * 读取resources.properties里面的值并放入缓存中
 **/
public class ResourcesProperties {
	
	private Map<String, String> resourcesProperties;
	
	private Map<String, String> mapPropreties;
	
	private static ResourcesProperties rp = null;
	
	private ResourcesProperties(){
	}
	
	public synchronized static ResourcesProperties getInstance(){
		if(rp == null){
			rp = new ResourcesProperties();
		}
		return rp;
	}
	
	public Map<String, String> getResourcesPropreties(){
		if(resourcesProperties == null || resourcesProperties.isEmpty()){
			resourcesProperties = StringUtil.getAllPropertiesFromResources("resources.properties");
		}
		return resourcesProperties;
	}
	
	public Map<String, String> getMapProperties(){
		if(mapPropreties == null || mapPropreties.isEmpty()){
			mapPropreties = StringUtil.getAllPropertiesFromResources("map.properties");
		}
		return mapPropreties;
	}
	
}
