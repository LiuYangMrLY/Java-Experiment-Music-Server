package ouc.cs.course.java.musicserver.service;

import ouc.cs.course.java.musicserver.dao.impl.LikedMusicDaoImpl;
import ouc.cs.course.java.musicserver.model.Music;
import ouc.cs.course.java.musicserver.model.User;

import java.util.List;

public class LikedMusicService {
    LikedMusicDaoImpl likedMusicDao = new LikedMusicDaoImpl();

    public LikedMusicService() {}

    public void like(User user, Music music) {
        likedMusicDao.insert(user, music);
    }

    public void unlike(User user, Music music) {
        likedMusicDao.delete(user, music);
    }

    public List<Music> getUserLikedMusic(User user) {
        return likedMusicDao.findLikedMusic(user);
    }
}
