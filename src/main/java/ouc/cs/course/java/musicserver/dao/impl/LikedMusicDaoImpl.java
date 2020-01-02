package ouc.cs.course.java.musicserver.dao.impl;

import ouc.cs.course.java.musicserver.dao.LikedMusicDao;
import ouc.cs.course.java.musicserver.model.Music;
import ouc.cs.course.java.musicserver.model.User;
import ouc.cs.course.java.musicserver.util.db.DatabaseUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LikedMusicDaoImpl implements LikedMusicDao {
    @Override
    public void insert(User user, Music music) {
        if (user == null || music == null) {
            return;
        }

        if (this.isLiked(user, music)) {
            return;
        }

        String INSERT_LIKED_MUSIC_INTO_LIKED_MUSIC_TABLE_SQL = "INSERT INTO liked_music(user, music) VALUES (?, ?)";

        try {
            Connection connection = DatabaseUtil.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(INSERT_LIKED_MUSIC_INTO_LIKED_MUSIC_TABLE_SQL);

            preparedStatement.setInt(1, user.getId());
            preparedStatement.setInt(2, music.getId());

            preparedStatement.execute();

            DatabaseUtil.close(null, preparedStatement, connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(User user, Music music) {
        if (user == null || music == null) {
            return;
        }

        String DELETE_LIKED_MUSIC_FROM_LIKED_MUSIC_TABLE = "DELETE FROM liked_music WHERE user=? AND music=?";

        try {
            Connection connection = DatabaseUtil.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(DELETE_LIKED_MUSIC_FROM_LIKED_MUSIC_TABLE);

            preparedStatement.setInt(1, user.getId());
            preparedStatement.setInt(2, music.getId());

            preparedStatement.execute();

            DatabaseUtil.close(null, preparedStatement, connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Music> findLikedMusic(User user) {
        ArrayList<Music> arrayList = new ArrayList<>();

        if (user == null) {
            return arrayList;
        }

        String SELECT_LIKED_MUSIC_FROM_LIKED_MUSIC_TABLE_SQL = "SELECT music FROM liked_music WHERE user=?";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = DatabaseUtil.getConnection();
            preparedStatement = connection.prepareStatement(SELECT_LIKED_MUSIC_FROM_LIKED_MUSIC_TABLE_SQL);

            preparedStatement.setInt(1, user.getId());

            resultSet = preparedStatement.executeQuery();
            MusicDaoImpl MusicDaoImpl = new MusicDaoImpl();
            while (resultSet.next()) {
                arrayList.add(MusicDaoImpl.findById(resultSet.getInt(1)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DatabaseUtil.close(resultSet, preparedStatement, connection);
        }

        return arrayList;
    }

    @Override
    public boolean isLiked(User user, Music music) {
        boolean flag = true;

        if (user == null || music == null) {
            return true;
        }

        String JUDGE_MUSIC_IS_LIKED_FROM_LIKED_MUSIC_TABLE_SQL = "SELECT * FROM liked_music WHERE user=? AND music=?";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = DatabaseUtil.getConnection();
            preparedStatement = connection.prepareStatement(JUDGE_MUSIC_IS_LIKED_FROM_LIKED_MUSIC_TABLE_SQL);

            preparedStatement.setInt(1, user.getId());
            preparedStatement.setInt(2, music.getId());

            resultSet = preparedStatement.executeQuery();
            if (!resultSet.next()) {
                flag = false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DatabaseUtil.close(resultSet, preparedStatement, connection);
        }

        return flag;
    }

    public static void makePreparations() {
        String CREATE_LIKED_MUSIC_TABLE_SQL = "CREATE TABLE IF NOT EXISTS liked_music(" +
                "user INT UNSIGNED NOT NULL," +
                "music INT UNSIGNED NOT NULL " +
                ")";

        try {
            Connection connection = DatabaseUtil.getConnection();
            Statement statement = connection.createStatement();

            statement.executeUpdate(CREATE_LIKED_MUSIC_TABLE_SQL);

            DatabaseUtil.close(null, statement, connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
