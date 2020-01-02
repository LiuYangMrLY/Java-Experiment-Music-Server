package ouc.cs.course.java.musicserver.dao.impl;

import ouc.cs.course.java.musicserver.dao.CommentDao;
import ouc.cs.course.java.musicserver.model.MusicSheet;
import ouc.cs.course.java.musicserver.model.User;
import ouc.cs.course.java.musicserver.util.db.DatabaseUtil;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class CommentDaoImpl implements CommentDao {
    @Override
    public void insert(User user, MusicSheet musicSheet, String content) {
        if (user == null || musicSheet == null || content == null) {
            return;
        }

        String INSERT_COMMENT_CONTENT_INTO_COMMENT_TABLE_SQL = "INSERT INTO comment(user, music_sheet, content) VALUES (?, ?, ?)";

        try {
            Connection connection = DatabaseUtil.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(INSERT_COMMENT_CONTENT_INTO_COMMENT_TABLE_SQL);

            preparedStatement.setInt(1, user.getId());
            preparedStatement.setInt(2, musicSheet.getId());
            preparedStatement.setString(3, content);

            preparedStatement.execute();

            DatabaseUtil.close(null, preparedStatement, connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public HashMap<Integer, String> getComment(MusicSheet musicSheet) {
        HashMap<Integer, String> hashMap = new HashMap<>();

        String SELECT_MUSIC_SHEET_COMMENT_FROM_COMMENTS_TABLE_SQL = "SELECT user, content FROM comment WHERE music_sheet=?";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = DatabaseUtil.getConnection();
            preparedStatement = connection.prepareStatement(SELECT_MUSIC_SHEET_COMMENT_FROM_COMMENTS_TABLE_SQL);

            preparedStatement.setInt(1, musicSheet.getId());

            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                hashMap.put(resultSet.getInt(1), resultSet.getString(2));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DatabaseUtil.close(resultSet, preparedStatement, connection);
        }

        return hashMap;
    }

    public static void makePreparations() {
        String CREATE_COMMENT_TABLE_SQL = "CREATE TABLE IF NOT EXISTS comment(" +
                "user INT UNSIGNED NOT NULL," +
                "music_sheet INT UNSIGNED NOT NULL," +
                "content CHAR(255)" +
                ")";

        try {
            Connection connection = DatabaseUtil.getConnection();
            Statement statement = connection.createStatement();

            statement.executeUpdate(CREATE_COMMENT_TABLE_SQL);

            DatabaseUtil.close(null, statement, connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
