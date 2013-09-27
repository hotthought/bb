package com.enjoyor.common.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SqlServerConn {
	// 获取连接
	public static Connection getConnection(String strConnection,String user,String password) {
		//String dbDriver = Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");;
		//strConnection = "jdbc:jtds:sqlserver://localhost:1433/TheTest";
//		String user = "sa";
//		String password = "sa";
		Connection conn = null;
		try {
			
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		} catch (java.lang.ClassNotFoundException e) {
			System.err.println("DBconnection():" + e.getMessage());
		}
		// --------连接SQL数据库------------------
		try {
			conn = DriverManager.getConnection(strConnection, user, password);
		} catch (SQLException ex) {
			System.err.println("aq.executeQuery:" + ex.getMessage());
		}
		return conn;
	}

	public static void closeConnection(PreparedStatement ps, Connection conn,
			ResultSet rs) {
		try {
			if (rs != null) {
				rs.close();
			}
			if (ps != null) {
				ps.close();
			}
			if (conn != null) {
				conn.close();
			}
		} catch (SQLException sqlerror) {
			sqlerror.printStackTrace();
		}
	}

	public static void closeConnection(PreparedStatement ps, Connection conn) {
		try {
			if (ps != null) {
				ps.close();
			}
			if (conn != null) {
				conn.close();
			}
		} catch (SQLException sqlerror) {
			sqlerror.printStackTrace();
		}
	}

	public static void closeConnection(Connection conn) {
		try {
			if (conn != null) {
				conn.close();
			}
		} catch (SQLException sqlerror) {
			sqlerror.printStackTrace();
		}
	}

}
