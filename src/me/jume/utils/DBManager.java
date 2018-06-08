package me.jume.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;


public class DBManager {
	private static final String CLASSNAME;
	private static final String URL;
	private static final String USER;
	private static final String PASSWORD;
	static{
		ResourceBundle rsb = ResourceBundle.getBundle("db");
		// 注意调用ResourceBundle的getString方法时传递的参数必须是String类型的key
		// 【注意】实质上是db.properties文件中=左边的字符串。
		// 【还需要注意】db.properties中的key和value值都不需要使用
		CLASSNAME = rsb.getString("driver");
		URL = rsb.getString("url");
		USER = rsb.getString("user");
		PASSWORD = rsb.getString("pwd");
		try {
			// 1.加载驱动
			Class.forName(CLASSNAME);	// 加载驱动
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 获取连接
	 * @return	返回数据库连接
	 */
	public static Connection getConnection(){
		Connection con = null;
		try {
			con = DriverManager.getConnection(URL, USER, PASSWORD);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return con;
	}

	/**
	 * 关闭所有资源
	 * @param rs		结果集
	 * @param ps		预编译对象
	 * @param con		连接
	 */
	public static void closeAll(ResultSet rs, PreparedStatement ps, Connection con){
		try {
			if(rs != null) {rs.close();rs=null;}
			if(ps != null) {ps.close();ps=null;}
			if(con != null) {con.close();con=null;}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
