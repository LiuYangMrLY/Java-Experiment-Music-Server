package ouc.cs.course.java.musicserver.servlet;

import ouc.cs.course.java.musicserver.service.UserService;
import ouc.cs.course.java.musicserver.util.md5.MD5Util;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public LoginServlet() {
		super();
	}

	/**
	 * 已登录重定向到 index.html
	 * 未登录获取登录页面 login.html
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		String username = (String) session.getAttribute("username");
		if (username != null) {
			response.sendRedirect("index/index.html");
		} else {
			response.sendRedirect("user/login.html");
		}
	}

	/**
	 * 登录接口
	 * form 表单 POST
	 * 验证用户的用户名和密码
	 * 合法 重定向到 index
	 * 非法 驻留在 login.html
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String username = request.getParameter("username");
		String password = request.getParameter("password");

		UserService userService = new UserService();
		if (userService.getOne(username, MD5Util.MD5Encode(password, "UTF-8")) != null) {
			request.getSession().setAttribute("username", username);
			response.sendRedirect("index/index.jsp");
		} else {
			response.sendRedirect("user/login.html");
		}
	}
}
