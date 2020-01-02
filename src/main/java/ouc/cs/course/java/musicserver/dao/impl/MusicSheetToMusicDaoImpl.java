package ouc.cs.course.java.musicserver.dao.impl;

import ouc.cs.course.java.musicserver.dao.MusicSheetToMusicDao;
import ouc.cs.course.java.musicserver.model.MusicSheetToMusic;
import ouc.cs.course.java.musicserver.util.db.DatabaseUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MusicSheetToMusicDaoImpl implements MusicSheetToMusicDao {
	@Override
	public int insert(MusicSheetToMusic musicSheetToMusic) throws SQLException {
		int autoIncKey = -1;

		String sql = "INSERT INTO musicsheet_music(musicsheetId, musicId) VALUES (?, ?)";
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {
			connection = DatabaseUtil.getConnection();
			preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

			preparedStatement.setInt(1, musicSheetToMusic.getMusicSheetId());
			preparedStatement.setInt(2, musicSheetToMusic.getMusicId());

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
	public void delete(int id) throws SQLException {
		String sql = "DELETE FROM musicsheet_music WHERE id=?";
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
	public List<Integer> findByMusicSheetId(int musicsheetId) throws SQLException {
		List<Integer> musicIdList = new ArrayList<>();

		String sql = "SELECT musicId FROM musicsheet_music WHERE musicsheetId=?";
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {
			connection = DatabaseUtil.getConnection();
			preparedStatement = connection.prepareStatement(sql);

			preparedStatement.setInt(1, musicsheetId);

			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				musicIdList.add(resultSet.getInt(1));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new SQLException("query by musicsheetId failed.");
		} finally {
			DatabaseUtil.close(resultSet, preparedStatement, connection);
		}

		return musicIdList;
	}

	/**
	 * 准备阶段
	 * 数据库创建 musicsheet_music 表
	 */
	public static void makePreparations() {
		String CREATE_MUSIC_SHEET_MUSIC_TABLE_SQL = "CREATE TABLE IF NOT EXISTS musicsheet_music(" +
				"id INT UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT," +
				"musicsheetId INT UNSIGNED NOT NULL," +
				"musicId INT UNSIGNED NOT NULL" +
				")";

		try {
			Connection connection = DatabaseUtil.getConnection();

			Statement statement = connection.createStatement();
			statement.executeUpdate(CREATE_MUSIC_SHEET_MUSIC_TABLE_SQL);

			DatabaseUtil.close(null, statement, connection);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
