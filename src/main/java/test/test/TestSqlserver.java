package test.test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.enjoyor.common.util.SqlServerConn;

public class TestSqlserver {

	public static void main(String[] args) {
		String strConnection = "jdbc:sqlserver://localhost:1433; DatabaseName=dljbb";
		String user = "sa";
		String password = "Abc123";
		Connection conn = SqlServerConn.getConnection(strConnection,user,password);
		String sql = "SELECT * FROM [sys_user]";
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			  while (rs.next()) {
			    int id = rs.getInt("id");
			    String name = rs.getString("name");
			    System.out.println("id name ==="+id+name);
			      }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		SqlServerConn.closeConnection(conn);

	}

}
