import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class AddUserUI {

    public JFrame view;

    public JButton btnAddUser = new JButton("Add User");


    public JTextField txtUsername = new JTextField(20);
    public JTextField txtPassword = new JPasswordField(20);
    public JTextField txtUserType = new JTextField(20);

    public AddUserUI() {
        this.view = new JFrame();

        view.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        view.setTitle("Add User");
        view.setSize(600, 400);

        Container pane = view.getContentPane();
        pane.setLayout(new BoxLayout(pane, BoxLayout.PAGE_AXIS));

        JPanel line = new JPanel();
        line.add(new JLabel("Username"));
        line.add(txtUsername);
        pane.add(line);

        line = new JPanel();
        line.add(new JLabel("Password"));
        line.add(txtPassword);
        pane.add(line);

        line = new JPanel();
        line.add(new JLabel("User Type:"));
        line.add(txtUserType);
        pane.add(line);

        pane.add(btnAddUser);

        btnAddUser.addActionListener(new AddUserUI.AddUserActionListener());

    }

    public void run() {
        view.setVisible(true);
    }

    private class AddUserActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent a) {
            User user = new User();

            String username = txtUsername.getText();

            if (username.length() == 0) {
                JOptionPane.showMessageDialog(null, "Must enter non null username");
                return;
            }

            user.username = username;

            String pass = txtPassword.getText();

            if (pass.length() == 0) {
                JOptionPane.showMessageDialog(null, "Must enter non null password");
                return;
            }

            user.password = pass;

            String userType = txtUserType.getText();
            try {
                user.userType = Integer.parseInt(userType);
            } catch (NumberFormatException f) {
                JOptionPane.showMessageDialog(null, "Invalid User type");
                return;
            }

            try {
                Socket link = new Socket("localhost", 1004);
                Scanner input = new Scanner(link.getInputStream());
                PrintWriter output = new PrintWriter(link.getOutputStream(), true);

                output.println("PUT");
                output.println(user.username);
                output.println(user.password);
                output.println(user.userType);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
