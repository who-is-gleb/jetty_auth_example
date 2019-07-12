import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;


public class UserServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        response.setContentType("text/html");
        response.setStatus(HttpServletResponse.SC_OK);
        PrintWriter writer = response.getWriter();

        Map<String, String> existingUsers = new HashMap<>();
        existingUsers.put("Petr", String.valueOf(123));
        existingUsers.put("Andy", String.valueOf(456));
        existingUsers.put("Ivan", String.valueOf(789));

        String authName = request.getParameter("name");

        if (!existingUsers.containsKey(authName)) {
            writer.println("Here are all the users: ");
            for (String s : existingUsers.keySet()) {
                writer.println(s + "; ");
            }
        } else {
            writer.println("You are " + authName);
        }
    }
}