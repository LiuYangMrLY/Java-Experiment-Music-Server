package ouc.cs.course.java.musicserver.util.db;

import ouc.cs.course.java.musicserver.dao.impl.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

/**
 * 数据库操作工具类
 */
public class DatabaseUtil {

	public static String URL;
	public static String USERNAME;
	public static String PASSWORD;
	public static String DRIVER;
	private static ResourceBundle resourceBundle = ResourceBundle.getBundle("ouc.cs.course.java.musicserver.util.db.db-config");

	private DatabaseUtil() {
	}

	static {
		URL = resourceBundle.getString("jdbc.url");
		USERNAME = resourceBundle.getString("jdbc.username");
		PASSWORD = resourceBundle.getString("jdbc.password");
		DRIVER = resourceBundle.getString("jdbc.driver");

		try {
			Class.forName(DRIVER);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		// 数据库表创建
		UserDaoImpl.makePreparations();
		MusicDaoImpl.makePreparations();
		MusicSheetToMusicDaoImpl.makePreparations();
		MusicSheetDaoImpl.makePreparations();
		LikedMusicDaoImpl.makePreparations();
	}

	/**
	 * 获取数据库连接的方法
	 * @return
	 */
	public static Connection getConnection() {
		Connection connection = null;

		try {
			connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Get Database Connection failed.");
		}

		return connection;
	}

	/**
	 * 关闭数据库连接
	 * @param resultSet
	 * @param statement
	 * @param connection
	 */
	public static void close(ResultSet resultSet, Statement statement, Connection connection) {
		try {
			if (resultSet != null) {
				resultSet.close();
			}
			if (statement != null) {
				statement.close();
			}
			if (connection != null) {
				connection.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
