import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

public class PurchaseServer {
    static String dbfile = "jdbc:sqlite:C:/Users/Jimmy/Documents/Auburn Classes/COMP 3700 Software Modeling/Project3/store.db";

    public static void main(String[] args) {

        int port = 1003;

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
                    System.out.println("GET purchase with id = " + str);
                    int productID = Integer.parseInt(str);

                    Connection conn = null;
                    try {
                        String url = dbfile;
                        conn = DriverManager.getConnection(url);

                        String sql = "SELECT * FROM Purchases WHERE PurchaseID = " + productID;
                        Statement stmt = conn.createStatement();
                        ResultSet rs = stmt.executeQuery(sql);

                        if (rs.next()) {
                            out.println(rs.getInt("CustomerID"));
                            out.println(rs.getInt("ProductID"));
                            out.println(rs.getInt("Quantity"));
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
                    String custID = in.nextLine();
                    String purchID = in.nextLine();
                    String quantity = in.nextLine();

                    System.out.println("PUT command with PurchaseID = " + id);

                    Connection conn = null;
                    try {
                        String url = dbfile;
                        conn = DriverManager.getConnection(url);

                        String sql = "SELECT * FROM Purchases WHERE PurchaseID = " + id;
                        Statement stmt = conn.createStatement();
                        ResultSet rs = stmt.executeQuery(sql);

                        if (rs.next()) {
                            rs.close();
                            stmt.execute("DELETE FROM Purchases WHERE PurchaseID = " + id);
                        }

                        sql = "INSERT INTO Purchases VALUES (" + id + "," + custID + ","
                                + purchID + "," + quantity + ")";
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
