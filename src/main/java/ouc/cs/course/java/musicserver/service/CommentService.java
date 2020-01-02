package ouc.cs.course.java.musicserver.service;

import ouc.cs.course.java.musicserver.dao.impl.CommentDaoImpl;
import ouc.cs.course.java.musicserver.model.MusicSheet;
import ouc.cs.course.java.musicserver.model.User;

import java.util.HashMap;

public class CommentService {
    CommentDaoImpl commentDao = new CommentDaoImpl();

    public void comment(User user, MusicSheet musicSheet, String content) {
        commentDao.insert(user, musicSheet, content);
    }

    public HashMap<Integer, String> getComments(MusicSheet musicSheet) {
        return commentDao.getComment(musicSheet);
    }
}
