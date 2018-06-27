package cn.et.shiro.utils;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.mysql.jdbc.Connection;

public class JdbcUtils {

	private Connection conn = null;
	private PreparedStatement stmt = null;
	private ResultSet rs = null;
	private static String driver = "com.mysql.jdbc.Driver";
	private String url = "jdbc:mysql://localhost:3306/my";
	private String user = "root";
	private String password = "zyx";
	
	/**
	 * 加载数据库驱动，连接数据库
	 */
	public Connection getCon() {
		try {
			Class.forName(driver);
		}catch(ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		try {
			conn = (Connection)DriverManager.getConnection(url,user,password);
		}catch(SQLException e) {
			e.printStackTrace();
		}
		
		return conn;
	}
	
	/**
	 * 修改
	 */
	public int update(String sql,Object... obj){
		int count = 0;
		conn = getCon();
		
		try {
			stmt = conn.prepareStatement(sql);
			
			if(obj != null) {
				for(int i=0; i<obj.length; i++) {
					stmt.setObject(i+1, obj[i]);
				}
			}
			
			count = stmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(); //调用关闭
		}
		
		return count;
	}
	
	/**
	 * 查询
	 */
	public ResultSet Query(String sql,Object... obj) {
		conn = getCon();
		
		try {
			stmt = conn.prepareStatement(sql);
			
			if(obj != null) {
				for(int i=0; i<obj.length; i++) {
					stmt.setObject(i+1, obj[i]);
				}
			}
			
			rs = stmt.executeQuery();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			//close();
		}
		
		return rs;
	}
	
	/**
	 * 关闭
	 */
	public void close() {
		try {
			if(rs != null) {
				rs.close();
			}
		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			
			try {
				if(stmt != null) {
					stmt.close();
				}
			} catch(SQLException e) {
				e.printStackTrace();
			} finally {
				
				if(conn != null) {
					try {
						conn.close();
					} catch(SQLException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}
	
}
