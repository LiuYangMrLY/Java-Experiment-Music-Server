package ouc.cs.course.java.musicserver.dao;

import ouc.cs.course.java.musicserver.model.Music;
import ouc.cs.course.java.musicserver.model.User;

import java.util.List;

public interface LikedMusicDao {
    void insert(User user, Music music);

    void delete(User user, Music music);

    List<Music> findLikedMusic(User user);

    boolean isLiked(User user, Music music);
}
