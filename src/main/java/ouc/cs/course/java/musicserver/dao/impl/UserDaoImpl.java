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
	 *
	 * @param user
	 * @return
	 * @throws SQLException
	 */
	@Override
	public int insert(User user) throws SQLException {
		int autoKey = -1;

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
			System.out.println("insert user failed.");
		} finally {
			DatabaseUtil.close(resultSet, preparedStatement, connection);
		}

		return autoKey;
	}

	@Override
	public List<User> findAll() throws SQLException {

		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		User user = null;
		List<User> userList = new ArrayList<User>();
		String sql = "select name from user";
		try {
			conn = DatabaseUtil.getConnection();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next()) {
				user = new User();
				user.setName(rs.getString(1));
				userList.add(user);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new SQLException("query all data failed.");
		} finally {
			DatabaseUtil.close(rs, ps, conn);
		}
		return userList;

	}




	@Override
	public User findById(int id) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public User findByName(String name) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public User findOne(String name, String passMd5Value) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	/**
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
