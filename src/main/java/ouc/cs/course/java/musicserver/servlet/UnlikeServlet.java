package ouc.cs.course.java.musicserver.servlet;

import ouc.cs.course.java.musicserver.model.Music;
import ouc.cs.course.java.musicserver.model.User;
import ouc.cs.course.java.musicserver.service.LikedMusicService;
import ouc.cs.course.java.musicserver.service.MusicService;
import ouc.cs.course.java.musicserver.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Writer;
import java.sql.SQLException;

@WebServlet("/unlike")
public class UnlikeServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        UserService userService = new UserService();
        String username = (String) request.getSession().getAttribute("username");
        User user = userService.getOne(username);

        MusicService musicService = new MusicService();
        String musicMd5Value = request.getParameter("music");
        Music music = null;
        try {
            music = musicService.getMusicByMd5Value(musicMd5Value);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        LikedMusicService likedMusicService = new LikedMusicService();
        likedMusicService.unlike(user, music);

        response.setStatus(200);
        Writer out = response.getWriter();
        out.write("success");
        out.flush();
        out.close();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setStatus(400);
    }
}
