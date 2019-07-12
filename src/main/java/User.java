import org.eclipse.jetty.server.session.Session;

public class User {
    String name;
    String pass;
    String session;

    User(String name, String pass, String session) {
        this.name = name;
        this.pass = pass;
        this.session = session;
    }

    User(String name, String pass) {
        this.name = name;
        this.pass = pass;
    }

    public String getName() {
        return this.name;
    }

    public String getPass() {
        return this.pass;
    }
}
