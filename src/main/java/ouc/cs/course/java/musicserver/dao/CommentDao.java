package ouc.cs.course.java.musicserver.dao;

import ouc.cs.course.java.musicserver.model.MusicSheet;
import ouc.cs.course.java.musicserver.model.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public interface CommentDao {
    void insert(User user, MusicSheet musicSheet, String content);

    HashMap<Integer, String> getComment(MusicSheet musicSheet);
}
