import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class LoginUI {
    public JFrame view;

    public JButton btnLogin = new JButton("Login");
    public JButton btnLogout = new JButton("Logout");
    public JButton btnChangeUser = new JButton("Change Login Info");

    public JTextField txtUsername = new JTextField(20);
    public JTextField txtPassword = new JPasswordField(20);
    public JTextField txtUserType = new JTextField(20);

    Socket link;
    Scanner input;
    PrintWriter output;

    int accessToken;

    public LoginUI() {
        this.view = new JFrame();

        view.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        view.setTitle("Login");
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

        pane.add(btnLogin);
        pane.add(btnLogout);
        pane.add(btnChangeUser);

        btnLogin.addActionListener(new LoginActionListener());

        btnLogout.addActionListener(new LogoutActionListener());
        btnChangeUser.addActionListener(new UserChangeActionListener());
    }

    public void run() {
        view.setVisible(true);
    }

    private class UserChangeActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            UpdateLoginUI ui = new UpdateLoginUI();
            ui.run();
        }
    }

    private class LogoutActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            try {
                link = new Socket("localhost", 1000);
                input = new Scanner(link.getInputStream());
                output = new PrintWriter(link.getOutputStream(), true);

                output.println("LOGOUT");
                output.println(accessToken);
                int res = input.nextInt();
                System.out.println("Sent LOGOUT " + accessToken + " received " + res);

                if (res == 0)
                    JOptionPane.showMessageDialog(null, "Invalid token for logout!");
                else
                    JOptionPane.showMessageDialog(null, "Logout successfully = ");
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    private class LoginActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            User user = new User();

            user.username = txtUsername.getText();
            user.password = txtPassword.getText();
            user.userType = Integer.parseInt(txtUserType.getText());

            if (user.username.length() == 0 || user.password.length() == 0) {
                JOptionPane.showMessageDialog(null, "Cannot have null username or password");
                return;
            }
            try {
                try {
                    link = new Socket("localhost", 1000);
                    input = new Scanner(link.getInputStream());
                    output = new PrintWriter(link.getOutputStream(), true);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                output.println("LOGIN");
                output.println(user.username);
                output.println(user.password);
                accessToken = input.nextInt();
                System.out.println("Sent " + user.username + "/" + user.password + " received " + accessToken);

                if (accessToken == 0)
                    JOptionPane.showMessageDialog(null, "Invalid username or password.");
                else
                    JOptionPane.showMessageDialog(null, "Login granted with access token = " + accessToken);
            } catch (Exception e) {
                e.printStackTrace();
            }

                System.out.println("User = " + user);
                if (user.userType == User.MANAGER) {
                    ManagerUI ui = new ManagerUI(user);
                    ui.run();
                }
                else if (user.userType == User.CASHIER) {
                    CashierUI ui = new CashierUI(user);
                    ui.run();
                }
                else if (user.userType == User.CUSTOMER) {
                    CustomerUI ui = new CustomerUI(user);
                    ui.run();
                }
                else if (user.userType == User.ADMIN) {
                    AdminUI ui = new AdminUI(user);
                    ui.run();
                }
                else {
                    JOptionPane.showMessageDialog(null, "Usertype NOT supported!");
                    view.setVisible(true);
                }

            }

        }


    }

