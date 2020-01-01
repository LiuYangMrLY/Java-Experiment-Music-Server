package ouc.cs.course.java.musicserver.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import ouc.cs.course.java.musicserver.dao.MusicSheetDao;
import ouc.cs.course.java.musicserver.model.MusicSheet;
import ouc.cs.course.java.musicserver.util.db.DatabaseUtil;

public class MusicSheetDaoImpl implements MusicSheetDao {
	@Override
	public int insert(MusicSheet ms) throws SQLException {
		int autoIncKey = -1;

		String sql = "INSERT INTO musicsheet(uuid, name, creatorId, creator, dateCreated, picture) VALUES (?, ?, ?, ?, ?, ?)";
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {
			connection = DatabaseUtil.getConnection();
			preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

			preparedStatement.setString(1, ms.getUuid());
			preparedStatement.setString(2, ms.getName());
			preparedStatement.setString(3, ms.getCreatorId());
			preparedStatement.setString(4, ms.getCreator());
			preparedStatement.setString(5, ms.getDateCreated());
			preparedStatement.setString(6, ms.getPicture());
			
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
	public void update(MusicSheet ms) throws SQLException {
		String sql = "UPDATE musicsheet SET name=?, creatorId=?, creator=?, dateCreated=?, picture=? WHERE uuid=?";
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		try {
			connection = DatabaseUtil.getConnection();
			preparedStatement = connection.prepareStatement(sql);

			preparedStatement.setString(1, ms.getName());
			preparedStatement.setString(2, ms.getCreatorId());
			preparedStatement.setString(3, ms.getCreator());
			preparedStatement.setString(4, ms.getDateCreated());
			preparedStatement.setString(5, ms.getPicture());
			preparedStatement.setString(6, ms.getUuid());

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
		String sql = "DELETE FROM musicsheet WHERE id=?";
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
	public void deleteByUuid(String uuid) throws SQLException {
		String sql = "DELETE FROM musicsheet WHERE uuid=?";
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		try {
			connection = DatabaseUtil.getConnection();
			preparedStatement = connection.prepareStatement(sql);

			preparedStatement.setString(1, uuid);

			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new SQLException("delete data by uuid failed.");
		} finally {
			DatabaseUtil.close(null, preparedStatement, connection);
		}
	}

	@Override
	public MusicSheet findById(int id) throws SQLException {
		MusicSheet musicSheet = null;

		String sql = "SELECT uuid, name, creatorId, creator, dateCreated, picture FROM musicsheet WHERE id=?";
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {
			connection = DatabaseUtil.getConnection();
			preparedStatement = connection.prepareStatement(sql);

			preparedStatement.setInt(1, id);

			resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				musicSheet = new MusicSheet();
				musicSheet.setUuid(resultSet.getString(1));
				musicSheet.setName(resultSet.getString(2));
				musicSheet.setCreatorId(resultSet.getString(3));
				musicSheet.setCreator(resultSet.getString(4));
				musicSheet.setDateCreated(resultSet.getString(5));
				musicSheet.setPicture(resultSet.getString(6));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new SQLException("query by id failed.");
		} finally {
			DatabaseUtil.close(resultSet, preparedStatement, connection);
		}

		return musicSheet;
	}

	@Override
	public MusicSheet findByUuid(String uuid) throws SQLException {
		MusicSheet musicSheet = null;

		String sql = "SELECT id, uuid, name, creatorId, creator, dateCreated, picture FROM musicsheet WHERE uuid=?";
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {
			connection = DatabaseUtil.getConnection();
			preparedStatement = connection.prepareStatement(sql);

			preparedStatement.setString(1, uuid);

			resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				musicSheet = new MusicSheet();
				musicSheet.setId(resultSet.getInt(1));
				musicSheet.setUuid(resultSet.getString(2));
				musicSheet.setName(resultSet.getString(3));
				musicSheet.setCreatorId(resultSet.getString(4));
				musicSheet.setCreator(resultSet.getString(5));
				musicSheet.setDateCreated(resultSet.getString(6));
				musicSheet.setPicture(resultSet.getString(7));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new SQLException("MusicSheetDaoImpl: query by uuid failed.");
		} finally {
			DatabaseUtil.close(resultSet, preparedStatement, connection);
		}

		return musicSheet;
	}
	
	
	@Override
	public List<MusicSheet> findAll() throws SQLException {
		MusicSheet musicSheet;
		List<MusicSheet> musicSheetList = new ArrayList<>();

		String sql = "SELECT id, uuid, name, creatorId, creator, dateCreated, picture FROM musicsheet";
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {
			connection = DatabaseUtil.getConnection();
			preparedStatement = connection.prepareStatement(sql);

			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				musicSheet = new MusicSheet();
				musicSheet.setId(resultSet.getInt(1));
				musicSheet.setUuid(resultSet.getString(2));
				musicSheet.setName(resultSet.getString(3));
				musicSheet.setCreatorId(resultSet.getString(4));
				musicSheet.setCreator(resultSet.getString(5));
				musicSheet.setDateCreated(resultSet.getString(6));
				musicSheet.setPicture(resultSet.getString(7));
				musicSheetList.add(musicSheet);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new SQLException("query all data failed.");
		} finally {
			DatabaseUtil.close(resultSet, preparedStatement, connection);
		}

		return musicSheetList;
	}

	@Override
	public List<MusicSheet> findLatest(int num) throws SQLException {
		MusicSheet musicSheet;
		List<MusicSheet> musicSheetList = new ArrayList<>();

		String sql = "SELECT id, uuid, name, creatorId, creator, dateCreated, picture FROM musicsheet ORDER BY id DESC limit " + num;
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {
			connection = DatabaseUtil.getConnection();
			preparedStatement = connection.prepareStatement(sql);

			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				musicSheet = new MusicSheet();
				musicSheet.setId(resultSet.getInt(1));
				musicSheet.setUuid(resultSet.getString(2));
				musicSheet.setName(resultSet.getString(3));
				musicSheet.setCreatorId(resultSet.getString(4));
				musicSheet.setCreator(resultSet.getString(5));
				musicSheet.setDateCreated(resultSet.getString(6));
				musicSheet.setPicture(resultSet.getString(7));
				musicSheetList.add(musicSheet);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new SQLException("query latest data failed.");
		} finally {
			DatabaseUtil.close(resultSet, preparedStatement, connection);
		}

		return musicSheetList;
	}

	/**
	 * 准备阶段
	 * 数据库创建 musicsheet 表
	 */
	public static void makePreparations() {
		String CREATE_MUSIC_SHEET_TABLE_TABLE_SQL = "CREATE TABLE IF NOT EXISTS musicsheet(" +
				"id INT UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT," +
				"uuid CHAR(255) NOT NULL," +
				"name CHAR(255)," +
				"creatorId CHAR(255)," +
				"creator CHAR(255)," +
				"dateCreated CHAR(255)," +
				"picture CHAR(255)" +
				");";

		try {
			Connection connection = DatabaseUtil.getConnection();

			Statement statement = connection.createStatement();
			statement.executeUpdate(CREATE_MUSIC_SHEET_TABLE_TABLE_SQL);

			DatabaseUtil.close(null, statement, connection);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
