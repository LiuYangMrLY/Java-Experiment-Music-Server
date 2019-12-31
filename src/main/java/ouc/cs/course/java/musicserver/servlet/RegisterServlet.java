package ouc.cs.course.java.musicserver.servlet;

import ouc.cs.course.java.musicserver.model.User;
import ouc.cs.course.java.musicserver.service.UserService;
import ouc.cs.course.java.musicserver.util.md5.MD5Util;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Writer;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.sendRedirect("user/register.html");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        if (username == null || password == null) {
            response.setStatus(400);

            Writer out = response.getWriter();
            out.write("username or password is empty");
            out.flush();
            out.close();

            return;
        }

        UserService userService = new UserService();
        if (userService.getOne(username) != null) {
            response.setStatus(400);

            Writer out = response.getWriter();
            out.write("username has been used");
            out.flush();
            out.close();

            return;
        }

        User user = new User(username, MD5Util.MD5Encode(password, "UTF-8"));
        userService.create(user);

        request.getSession().setAttribute("username", username);
        response.sendRedirect("index/index.jsp");
    }
}
