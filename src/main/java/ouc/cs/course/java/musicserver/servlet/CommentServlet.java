package ouc.cs.course.java.musicserver.servlet;

import com.alibaba.fastjson.JSONObject;
import ouc.cs.course.java.musicserver.dao.impl.MusicSheetDaoImpl;
import ouc.cs.course.java.musicserver.model.MusicSheet;
import ouc.cs.course.java.musicserver.model.User;
import ouc.cs.course.java.musicserver.service.CommentService;
import ouc.cs.course.java.musicserver.service.MusicSheetService;
import ouc.cs.course.java.musicserver.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;

@WebServlet("/comment")
public class CommentServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = (String) request.getSession().getAttribute("username");
        UserService userService = new UserService();
        User user = userService.getOne(username);

        String musicSheetMd5Value = request.getParameter("music_sheet");
        MusicSheetService musicSheetService = new MusicSheetService();
        MusicSheet musicSheet = musicSheetService.getMusicByUuid(musicSheetMd5Value);

        CommentService commentService = new CommentService();
        commentService.comment(user, musicSheet, request.getParameter("content"));

        response.setStatus(200);
        Writer out = response.getWriter();
        out.write("success");
        out.flush();
        out.close();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String musicSheetMd5Value = request.getParameter("music_sheet");
        MusicSheetService musicSheetService = new MusicSheetService();
        MusicSheet musicSheet = musicSheetService.getMusicByUuid(musicSheetMd5Value);

        CommentService commentService = new CommentService();
        HashMap<Integer, String> hashMap = commentService.getComments(musicSheet);

        HashMap<String, String> result = new HashMap<>();
        UserService userService = new UserService();
        for (Integer key: hashMap.keySet()) {
            result.put(userService.getOne(key).getName(), hashMap.get(key));
        }

        JSONObject jsonObject = new JSONObject();
        jsonObject.putAll(result);

        response.setStatus(200);
        Writer out = response.getWriter();
        out.write(jsonObject.toString());
        out.flush();
        out.close();
    }
}
