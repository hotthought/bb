package com.enjoyor.common.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 
 * @date : 2012-8-28 下午2:46:43
 *
 **/
public class MyBatisSql {
	
	/** 
	 * 运行期 sql 
	 */  
    private String sql;  
      
    /** 
     * 参数 数组 
     */  
    private Object[] parameters;  
    
    public void setSql(String sql) {    
        this.sql = sql;    
    }    
    
    public String getSql() {    
        return sql;    
    }    
    
    public void setParameters(Object[] parameters) {    
        this.parameters = parameters;    
    }    
    
    public Object[] getParameters() {    
        return parameters;    
    }    
      
    @Override  
    public String toString() {  
        if(parameters == null || sql == null){  
            return "";  
        }  
        List<Object> parametersArray = Arrays.asList(parameters);  
        List<Object> list = new ArrayList<Object>(parametersArray);  
        while(sql.indexOf("?") != -1 && list.size() > 0 && parameters.length > 0){  
        	if(list.get(0) instanceof String){
        		sql = sql.replaceFirst("\\?", "'" + list.get(0) + "'");  
        	} else if(list.get(0) instanceof java.util.Date){
        		
        		sql = sql.replaceFirst("\\?", list.get(0).toString());  
        	} else {
        		sql = sql.replaceFirst("\\?", list.get(0).toString());  
        	}
            
            list.remove(0);  
        }  
        return sql.replaceAll("(\r?\n(\\s*\r?\n)+)", "\r\n");  
    }

}
