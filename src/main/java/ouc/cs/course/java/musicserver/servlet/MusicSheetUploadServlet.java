package ouc.cs.course.java.musicserver.servlet;

import java.io.IOException;
import java.io.Writer;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.json.JSONObject;
import ouc.cs.course.java.musicserver.model.Music;
import ouc.cs.course.java.musicserver.model.MusicSheet;
import ouc.cs.course.java.musicserver.model.MusicSheetToMusic;
import ouc.cs.course.java.musicserver.service.MusicService;
import ouc.cs.course.java.musicserver.service.MusicSheetService;
import ouc.cs.course.java.musicserver.service.MusicSheetToMusicService;
import ouc.cs.course.java.musicserver.util.json.JsonReader;

@WebServlet("/sheetUpload")
public class MusicSheetUploadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public MusicSheetUploadServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html;charset=utf-8");
		response.setHeader("Access-Control-Allow-Origin", "*");  // 设置响应头允许AJAX跨域访问，星号表示所有的异域请求都可以接受
		response.setHeader("Access-Control-Allow-Methods", "GET, POST");

		JSONObject json = JsonReader.receivePost(request);

		System.out.println("[REQUEST JSON] " + json);
		MusicSheet musicSheet = (MusicSheet) JSONObject.toBean(json, MusicSheet.class);
		System.out.println("*************** Music sheet ***************");
		System.out.println("Sheet UUID: " + musicSheet.getUuid());
		System.out.println("Sheet Name: " + musicSheet.getName());
		System.out.println("Sheet Creator: " + musicSheet.getCreator());
		System.out.println("Sheet Created Date: " + musicSheet.getDateCreated());
		System.out.println("Sheet Picture: " + musicSheet.getPicture());
		System.out.println("Sheet Music Items: " + musicSheet.getMusicItems());

		JSONObject jsonObject = new JSONObject();

		if (musicSheet.getMusicItems() == null) {
			jsonObject.put("message", "Music items is null.");
		} else {
			System.out.println("Music: ");
			for (String key : musicSheet.getMusicItems().keySet()) {
				System.out.println(" ---- " + musicSheet.getMusicItems().get(key));
			}
			System.out.println("********************************************");

			MusicSheetService musicSheetService = new MusicSheetService();
			MusicService musicService = new MusicService();
			MusicSheetToMusicService musicSheetToMusicService = new MusicSheetToMusicService();

			Music music;
			MusicSheetToMusic musicSheetToMusic;
			int musicSheetId, musicId;
			boolean token = true;

			try {
				musicSheetId = musicSheetService.create(musicSheet);

				for (String key: musicSheet.getMusicItems().keySet()) {
					music = new Music(key, musicSheet.getMusicItems().get(key));
					musicId = musicService.create(music);

					musicSheetToMusic = new MusicSheetToMusic(musicSheetId, musicId);
					musicSheetToMusicService.create(musicSheetToMusic);
				}
			} catch (SQLException e) {
				e.printStackTrace();
				token = false;
			}

			if (token) {
				jsonObject.put("musicSheet", JSONObject.fromObject(musicSheet));
				jsonObject.put("message", "Upload musicsheet successfully.");
			} else {
				jsonObject.put("message", "Upload musicsheet failed.");
			}
		}

		Writer out = response.getWriter();
		out.write(jsonObject.toString());
		out.flush();
		out.close();
	}
}