import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.*;
import com.google.gson.Gson;
import java.util.Map;
import javax.servlet.http.HttpSession;



@SuppressWarnings("serial")
@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    public static Map<String, String> existingUsers = new HashMap<>();

    static {
        existingUsers.put("Petr", String.valueOf(123));
        existingUsers.put("Andy", String.valueOf(456));
        existingUsers.put("Ivan", String.valueOf(789));
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.setStatus(HttpServletResponse.SC_OK);
        PrintWriter writer = response.getWriter();

        String authName = request.getParameter("name");
        String authPass = request.getParameter("pass");

        // Check if password parameter exists
        if (!request.getParameterMap().containsKey("pass")) {
            response.sendError(403);
        }

        // Check if name parameter exists
        if (!request.getParameterMap().containsKey("name")) {
            response.sendError(403);
        }

        // Get a new session if the data matches
        if (matchesServerData(authName, authPass)) {
            HttpSession session = request.getSession();
            String jsonUser = new Gson().toJson(new User(authName, authPass, session.getId()));
            writer.println(jsonUser);
        } else {
            response.sendError(403);
        }
    }

    // Check if name and password match the database
    private boolean matchesServerData(String authName, String authPass) {
        return existingUsers.containsKey(authName) && (authPass.equals(existingUsers.get(authName)));
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.setStatus(HttpServletResponse.SC_OK);
        PrintWriter writer = response.getWriter();

        writer.println("You are using POST with JSON!");
        // Get request body json
        User loginInfo = new Gson().fromJson(request.getReader(), User.class);
        String jsonName = loginInfo.getName();
        String jsonPass = loginInfo.getPass();

        // Give session ID if data matches
        if (matchesServerDataPOST(jsonName, jsonPass)) {
            HttpSession session = request.getSession();
            session.setMaxInactiveInterval(30*60);
            String jsonUser = new Gson().toJson(new User(jsonName, jsonPass, session.getId()));
            writer.println(jsonUser);
        } else {
            response.sendError(403);
        }
    }

    // Check if name and password match the database
    private boolean matchesServerDataPOST(String jsonName, String jsonPass) {
        return existingUsers.containsKey(jsonName) && (jsonPass.equals(existingUsers.get(jsonName)));
    }
}
