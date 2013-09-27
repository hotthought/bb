package com.enjoyor.common.util;

public abstract class Dialect {
	public static enum Type {
		MYSQL, ORACLE,sqlserver
	}

	public abstract String getLimitString(String sql, int skipResults, int maxResults);

}
