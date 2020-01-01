package ouc.cs.course.java.musicserver.dao.impl;

import ouc.cs.course.java.musicserver.dao.MusicDao;
import ouc.cs.course.java.musicserver.model.Music;
import ouc.cs.course.java.musicserver.util.db.DatabaseUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MusicDaoImpl implements MusicDao {
	@Override
	public int insert(Music music) throws SQLException {
		int autoIncKey = -1;

		if (music == null) {
			return autoIncKey;
		}

		String sql = "INSERT INTO music(md5value, name, singer) VALUES (?, ?, ?)";
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {
			connection = DatabaseUtil.getConnection();
			preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

			preparedStatement.setString(1, music.getMd5value());
			preparedStatement.setString(2, music.getName());
			preparedStatement.setString(3, music.getSinger());

			preparedStatement.executeUpdate();

			resultSet = preparedStatement.getGeneratedKeys();
			if (resultSet.next()) {
				autoIncKey = resultSet.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new SQLException("add data failed.");
		} finally {
			DatabaseUtil.close(resultSet, preparedStatement, connection);
		}

		return autoIncKey;
	}

	@Override
	public void update(Music music) throws SQLException {
		String sql = "UPDATE music SET name=?, singer=? WHERE md5value=?";
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		try {
			connection = DatabaseUtil.getConnection();
			preparedStatement = connection.prepareStatement(sql);

			preparedStatement.setString(1, music.getName());
			preparedStatement.setString(2, music.getSinger());
			preparedStatement.setString(3, music.getMd5value());

			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new SQLException("update data failed.");
		} finally {
			DatabaseUtil.close(null, preparedStatement, connection);
		}
	}

	@Override
	public void delete(int id) throws SQLException {
		String sql = "DELETE FROM music WHERE id=?";
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		try {
			connection = DatabaseUtil.getConnection();
			preparedStatement = connection.prepareStatement(sql);

			preparedStatement.setInt(1, id);

			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new SQLException("delete data failed.");
		} finally {
			DatabaseUtil.close(null, preparedStatement, connection);
		}
	}

	@Override
	public void deleteByMd5value(String md5value) throws SQLException {
		String sql = "DELETE FROM music WHERE md5value=?";
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		try {
			connection = DatabaseUtil.getConnection();
			preparedStatement = connection.prepareStatement(sql);

			preparedStatement.setString(1, md5value);

			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new SQLException("delete data by md5value failed.");
		} finally {
			DatabaseUtil.close(null, preparedStatement, connection);
		}
	}

	@Override
	public Music findById(int id) throws SQLException {
		Music music = null;

		String sql = "SELECT md5value, name, singer FROM music WHERE id=?";
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {
			connection = DatabaseUtil.getConnection();
			preparedStatement = connection.prepareStatement(sql);

			preparedStatement.setInt(1, id);

			resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				music = new Music();
				music.setMd5value(resultSet.getString(1));
				music.setName(resultSet.getString(2));
				music.setSinger(resultSet.getString(3));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new SQLException("query by id failed.");
		} finally {
			DatabaseUtil.close(resultSet, preparedStatement, connection);
		}

		return music;
	}

	@Override
	public Music findByMd5value(String md5value) throws SQLException {
		Music music = null;

		String sql = "SELECT id, md5value, name, singer FROM music WHERE md5value=?";
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {
			connection = DatabaseUtil.getConnection();
			preparedStatement = connection.prepareStatement(sql);

			preparedStatement.setString(1, md5value);

			resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				music = new Music();
				music.setId(resultSet.getInt(1));
				music.setMd5value(resultSet.getString(2));
				music.setName(resultSet.getString(3));
				music.setSinger(resultSet.getString(4));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new SQLException("query by md5value failed.");
		} finally {
			DatabaseUtil.close(resultSet, preparedStatement, connection);
		}

		return music;
	}

	@Override
	public List<Music> findAll() throws SQLException {
		Music music;
		List<Music> musicList = new ArrayList<>();

		String sql = "SELECT md5value, name, singer FROM music";
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {
			connection = DatabaseUtil.getConnection();
			preparedStatement = connection.prepareStatement(sql);

			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				music = new Music();
				music.setMd5value(resultSet.getString(1));
				music.setName(resultSet.getString(2));
				music.setSinger(resultSet.getString(3));
				musicList.add(music);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new SQLException("query all data failed.");
		} finally {
			DatabaseUtil.close(resultSet, preparedStatement, connection);
		}

		return musicList;
	}

	/**
	 * 准备阶段
	 * 数据库创建 music 表
	 */
	public static void makePreparations() {
		String CREATE_MUSIC_TABLE_SQL = "CREATE TABLE IF NOT EXISTS music(" +
				"id INT UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT," +
				"md5value CHAR(255) NOT NULL," +
				"name CHAR(255)," +
				"singer CHAR(255)" +
				")";

		try {
			Connection connection = DatabaseUtil.getConnection();

			Statement statement = connection.createStatement();
			statement.executeUpdate(CREATE_MUSIC_TABLE_SQL);

			DatabaseUtil.close(null, statement, connection);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
