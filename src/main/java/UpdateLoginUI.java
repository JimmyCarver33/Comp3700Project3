import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.PrintWriter;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

public class UpdateLoginUI {

    public JFrame view;

    public JButton btnUpdate = new JButton("Update User Info");

    public JTextField txtCurrentUser = new JTextField(20);
    public JTextField txtCurrentPass = new JTextField(20);
    public JTextField txtNewUser = new JTextField(20);
    public JTextField txtNewPass = new JTextField(20);

    Socket link;
    Scanner input;
    PrintWriter output;

    int accessToken;

    public UpdateLoginUI() {
        this.view = new JFrame();

        view.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        view.setTitle("Update Login Info");
        view.setSize(600, 400);
        view.getContentPane().setLayout(new BoxLayout(view.getContentPane(), BoxLayout.PAGE_AXIS));

        JPanel panelButtons = new JPanel(new FlowLayout());
        panelButtons.add(btnUpdate);
        view.getContentPane().add(panelButtons);

        JPanel line1 = new JPanel(new FlowLayout());
        line1.add(new JLabel("Current UserName: "));
        line1.add(txtCurrentUser);
        view.getContentPane().add(line1);

        JPanel line2 = new JPanel(new FlowLayout());
        line2.add(new JLabel("Current Password: "));
        line2.add(txtCurrentPass);
        view.getContentPane().add(line2);

        JPanel line3 = new JPanel(new FlowLayout());
        line3.add(new JLabel("New UserName: "));
        line3.add(txtNewUser);
        view.getContentPane().add(line3);

        JPanel line4 = new JPanel(new FlowLayout());
        line4.add(new JLabel("New Password: "));
        line4.add(txtNewPass);
        view.getContentPane().add(line4);


        btnUpdate.addActionListener(new UpdateLoginUI.UpdateButtonListener());
    }

    public void run() {
        view.setVisible(true);
    }

    class UpdateButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent actionEvent) {

            String currentUser = txtCurrentUser.getText();

            if (currentUser.length() == 0) {
                JOptionPane.showMessageDialog(null, "Must enter current username");
                return;
            }

            String currentPass = txtCurrentPass.getText();

            if (currentPass.length() == 0) {
                JOptionPane.showMessageDialog(null, "Must enter current password");
                return;
            }

            if (checkUser(currentUser, currentPass)) {
                String newUser = txtNewUser.getText();
                String newPass = txtNewPass.getText();

                if (newUser.length() == 0 || newPass.length() == 0)
                {
                    JOptionPane.showMessageDialog(null, "Must enter non null values");
                    return;
                }

                updateUser(newUser, newPass, currentUser, currentPass);
            }

        }
    }

    private static boolean updateUser(String newUserName, String newPassword, String currUserName, String currPassword) {
        Connection conn = null;
        try {
            String url = "jdbc:sqlite:C:/Users/Jimmy/Documents/Auburn Classes/COMP 3700 Software Modeling/Project3/store.db";
            conn = DriverManager.getConnection(url);

            String sql = "UPDATE Users SET Username = " + "\"" + newUserName + "\""
                    + ",  Password = " + "\"" + newPassword + "\""
                    + " WHERE Username = " + "\"" + currUserName + "\""
                    + " AND  Password = " + "\"" + currPassword + "\"";
            System.out.println(sql);
            Statement stmt = conn.createStatement();
            stmt.executeQuery(sql);
            conn.close();
            return true;


        } catch (Exception e) {

        }
        return false;
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

