package ouc.cs.course.java.musicserver.servlet;

import com.alibaba.fastjson.JSONArray;
import net.sf.json.JSONObject;
import ouc.cs.course.java.musicserver.model.Music;
import ouc.cs.course.java.musicserver.model.User;
import ouc.cs.course.java.musicserver.service.LikedMusicService;
import ouc.cs.course.java.musicserver.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;

@WebServlet("/liked_music")
public class LikedMusicServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        UserService userService = new UserService();
        String username = (String) request.getSession().getAttribute("username");
        User user = userService.getOne(username);

        LikedMusicService likedMusicService = new LikedMusicService();
        ArrayList<Music> arrayList = (ArrayList<Music>) likedMusicService.getUserLikedMusic(user);

        JSONObject jsonObject = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        for (Music music: arrayList) {
            jsonArray.add(music.getMd5value());
        }
        jsonObject.put("music", jsonArray);

        response.setStatus(200);
        Writer out = response.getWriter();
        out.write(jsonObject.toString());
        out.flush();
        out.close();
    }
}
