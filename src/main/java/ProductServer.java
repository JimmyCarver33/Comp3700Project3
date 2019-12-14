import java.io.PrintWriter;
import java.net.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Scanner;

public class ProductServer {
    static String dbfile = "jdbc:sqlite:C:/Users/Jimmy/Documents/Auburn Classes/COMP 3700 Software Modeling/Project3/store.db";

    public static void main(String[] args) {

        int port = 1001;

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
                if (command.equals("GET")) {
                    String str = in.nextLine();
                    System.out.println("GET product with id = " + str);
                    int productID = Integer.parseInt(str);

                    Connection conn = null;
                    try {
                        String url = dbfile;
                        conn = DriverManager.getConnection(url);

                        String sql = "SELECT * FROM Products WHERE ProductID = " + productID;
                        Statement stmt = conn.createStatement();
                        ResultSet rs = stmt.executeQuery(sql);

                        if (rs.next()) {
                            out.println(rs.getString("Name"));
                            out.println(rs.getDouble("Price"));
                            out.println(rs.getDouble("Quantity"));
                        }
                        else
                            out.println("null");

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    conn.close();
                }

                if (command.equals("PUT")) {
                    String id = in.nextLine();
                    String name = in.nextLine();
                    String price = in.nextLine();
                    String quantity = in.nextLine();

                    System.out.println("PUT command with ProductID = " + id);

                    Connection conn = null;
                    try {
                        String url = dbfile;
                        conn = DriverManager.getConnection(url);

                        String sql = "SELECT * FROM Products WHERE ProductID = " + id;
                        Statement stmt = conn.createStatement();
                        ResultSet rs = stmt.executeQuery(sql);

                        if (rs.next()) {
                            rs.close();
                            stmt.execute("DELETE FROM Products WHERE ProductID = " + id);
                        }

                        sql = "INSERT INTO Products VALUES (" + id + ",\"" + name + "\","
                                + quantity + "," + price + ")";
                        System.out.println("SQL for PUT: " + sql);
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