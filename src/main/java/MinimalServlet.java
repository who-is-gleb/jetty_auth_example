import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.servlet.FilterHolder;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;


public class MinimalServlet {
    public static void main(String[] args) throws Exception {
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);

        context.addServlet(new ServletHolder(new UserServlet()), "/user");
        context.addServlet(new ServletHolder(new LoginServlet()), "/login");

        context.addFilter(new FilterHolder(new AuthorizationFilter()), "/user", null);

        Server server = new Server(3000);
        server.setHandler(new HandlerList(context));

        server.start();
        server.join();
    }
}