import java.io.PrintWriter;
import java.net.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Scanner;

public class LoginServer {
    public static void main(String[] args) {
        HashMap<Integer, String> loginUsers = new HashMap<>();

        int port = 1000;
        try {
            ServerSocket server = new ServerSocket(port);
            System.out.println("Server is listening at port = " + port);
            while (true) {
                Socket pipe = server.accept();
                PrintWriter out = new PrintWriter(pipe.getOutputStream(), true);
                Scanner in = new Scanner(pipe.getInputStream());

                String command = in.nextLine();
                if (command.equals("LOGIN")) {
                    String username = in.nextLine();
                    String password = in.nextLine();

                    System.out.println("Login with username = " + username + "\npassword = " + password);

                    if (checkUser(username, password)) {
                        int accessToken = username.hashCode();
                        out.println(accessToken);
                        loginUsers.put(accessToken, username);
                    } else {
                        out.println(0);
                        break;
                    }
                }

                if (command.equals("LOGOUT")) {
                    int token = in.nextInt();

                    System.out.println("Logout with token = " + token);

                    if (loginUsers.containsKey(token))
                        loginUsers.remove(token);
                    out.println(1);
                } else {
                    out.println(0);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static boolean checkUser(String username, String password) {
        Connection conn = null;
        try {
            String url = "jdbc:sqlite:C:/Users/Jimmy/Documents/Auburn Classes/COMP 3700 Software Modeling/Project3/store.db";
            conn = DriverManager.getConnection(url);

            String sql = "SELECT * FROM Users WHERE Username = " + "\"" + username + "\""
                    + " AND  Password = " + "\"" + password + "\"";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            if (rs.next()) {
                conn.close();
                return true;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

}