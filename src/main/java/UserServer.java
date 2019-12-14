import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

public class UserServer {

    static String dbfile = "jdbc:sqlite:C:/Users/Jimmy/Documents/Auburn Classes/COMP 3700 Software Modeling/Project3/store.db";

    public static void main(String[] args) {

        int port = 1004;

        if (args.length > 0) {
            System.out.println("Running arguments: ");
            for (String arg : args)
                System.out.println(arg);
            port = Integer.parseInt(args[0]);
            dbfile = args[1];
        }

        try {
            ServerSocket server = new ServerSocket(port);

            System.out.println("Server is listening at port = " + port);

            while (true) {
                Socket pipe = server.accept();
                PrintWriter out = new PrintWriter(pipe.getOutputStream(), true);
                Scanner in = new Scanner(pipe.getInputStream());

                String command = in.nextLine();

                if (command.equals("PUT")) {
                    String name = in.nextLine();
                    String pass = in.nextLine();
                    String type = in.nextLine();

                    System.out.println("PUT command with Username = " + name);

                    Connection conn = null;
                    try {
                        String url = dbfile;
                        conn = DriverManager.getConnection(url);

                        String sql = "INSERT INTO Users VALUES (\"" + name + "\"," + "\"" + pass + "\","
                                + type + ")";
                        System.out.println("SQL for PUT: " + sql);
                        Statement stmt = conn.createStatement();
                        stmt.execute(sql);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    conn.close();


                } else {
                    out.println(0);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
