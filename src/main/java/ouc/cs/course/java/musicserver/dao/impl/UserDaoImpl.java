package ouc.cs.course.java.musicserver.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import ouc.cs.course.java.musicserver.dao.UserDao;
import ouc.cs.course.java.musicserver.model.User;
import ouc.cs.course.java.musicserver.util.db.DatabaseUtil;

public class UserDaoImpl implements UserDao {
	/**
	 * 插入一个新建用户 user
	 * @param user 新建用户
	 * @return 新建用户的 id (返回 -1 为插入失败)
	 */
	@Override
	public int insert(User user) {
		int autoKey = -1;

		if (user.getName() == null || user.getPassMd5value() == null) {
			return autoKey;
		}

		String INSERT_USER_INTO_USER_TABLE_SQL = "INSERT INTO user(name, passmd5value) VALUES (?, ?)";
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {
			connection = DatabaseUtil.getConnection();
			preparedStatement = connection.prepareStatement(INSERT_USER_INTO_USER_TABLE_SQL, Statement.RETURN_GENERATED_KEYS);

			preparedStatement.setString(1, user.getName());
			preparedStatement.setString(1, user.getPassMd5value());

			preparedStatement.execute();

			resultSet = preparedStatement.getGeneratedKeys();
			if (resultSet.next()) {
				autoKey = resultSet.getInt(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DatabaseUtil.close(resultSet, preparedStatement, connection);
		}

		return autoKey;
	}

	/**
	 * 获取所有的用户 users(id, name) without passmd5value
	 * @return 所有用户 users
	 */
	@Override
	public List<User> findAll() {
		ArrayList<User> userList = new ArrayList<>();

		String SELECT_USER_FROM_USER_TABLE = "SELECT id, name FROM user";
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {
			connection = DatabaseUtil.getConnection();
			preparedStatement = connection.prepareStatement(SELECT_USER_FROM_USER_TABLE);

			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				userList.add(new User(
					resultSet.getInt(1),
					resultSet.getString(2)
				));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DatabaseUtil.close(resultSet, preparedStatement, connection);
		}

		return userList;
	}

	/**
	 * 通过 id 查找用户 user
	 * @param id 用户 id
	 * @return 用户 user
	 */
	@Override
	public User findById(int id) {
		User user = null;

		String SELECT_USER_FROM_USER_TABLE = "SELECT * FROM user WHERE id=?";
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {
			connection = DatabaseUtil.getConnection();
			preparedStatement = connection.prepareStatement(SELECT_USER_FROM_USER_TABLE);

			preparedStatement.setInt(1, id);

			resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				user = new User(
						resultSet.getInt(1),
						resultSet.getString(2),
						resultSet.getString(3)
				);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DatabaseUtil.close(resultSet, preparedStatement, connection);
		}

		return user;
	}

	/**
	 * 通过用户名查找用户 user
	 * @param name 用户名
	 * @return 用户 user
	 */
	@Override
	public User findByName(String name) {
		User user = null;

		if (name == null) {
			return null;
		}

		String SELECT_USER_FROM_USER_TABLE = "SELECT * FROM user WHERE name=?";
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {
			connection = DatabaseUtil.getConnection();
			preparedStatement = connection.prepareStatement(SELECT_USER_FROM_USER_TABLE);

			preparedStatement.setString(1, name);

			resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				user = new User(
						resultSet.getInt(1),
						resultSet.getString(2),
						resultSet.getString(3)
				);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DatabaseUtil.close(resultSet, preparedStatement, connection);
		}

		return user;
	}

	/**
	 * 通过用户名和密码查找user
	 * @param name 用户名
	 * @param passMd5Value 密码
	 * @return 用户 user
	 */
	@Override
	public User findOne(String name, String passMd5Value) {
		User user = null;

		if (name == null || passMd5Value == null) {
			return null;
		}

		String SELECT_USER_FROM_USER_TABLE = "SELECT * FROM user WHERE name=? AND passmd5value=?";
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {
			connection = DatabaseUtil.getConnection();
			preparedStatement = connection.prepareStatement(SELECT_USER_FROM_USER_TABLE);

			preparedStatement.setString(1, name);
			preparedStatement.setString(2, passMd5Value);

			resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				user = new User(
						resultSet.getInt(1),
						resultSet.getString(2),
						resultSet.getString(3)
				);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DatabaseUtil.close(resultSet, preparedStatement, connection);
		}

		return user;
	}

	/**
	 * 准备阶段
	 * 数据库创建 user 表
	 */
	public static void makePreparations() {
		String CREAT_USER_TABLE_SQL = "CREATE TABLE IF NOT EXISTS user(" +
				"id INT UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT," +
				"name CHAR(255)," +
				"passmd5value CHAR(255)" +
				");";

		try {
			Connection connection = DatabaseUtil.getConnection();

			Statement statement = connection.createStatement();
			statement.executeUpdate(CREAT_USER_TABLE_SQL);

			DatabaseUtil.close(null, statement, connection);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
