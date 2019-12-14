import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class UpdateCustomerUI {

    public JFrame view;

    public JButton btnLoad = new JButton("Load Customer");
    public JButton btnSave = new JButton("Save Customer");

    public JTextField txtCustomerID = new JTextField(20);
    public JTextField txtlastName = new JTextField(20);
    public JTextField txtfirstName = new JTextField(20);
    public JTextField txtphoneNum = new JTextField(20);


    public UpdateCustomerUI() {
        this.view = new JFrame();

        view.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        view.setTitle("Update Customer Information");
        view.setSize(600, 400);
        view.getContentPane().setLayout(new BoxLayout(view.getContentPane(), BoxLayout.PAGE_AXIS));

        JPanel panelButtons = new JPanel(new FlowLayout());
        panelButtons.add(btnLoad);
        panelButtons.add(btnSave);
        view.getContentPane().add(panelButtons);

        JPanel line1 = new JPanel(new FlowLayout());
        line1.add(new JLabel("Customer ID "));
        line1.add(txtCustomerID);
        view.getContentPane().add(line1);

        JPanel line2 = new JPanel(new FlowLayout());
        line2.add(new JLabel("Last Name "));
        line2.add(txtlastName);
        view.getContentPane().add(line2);

        JPanel line3 = new JPanel(new FlowLayout());
        line3.add(new JLabel("First Name "));
        line3.add(txtfirstName);
        view.getContentPane().add(line3);

        JPanel line4 = new JPanel(new FlowLayout());
        line4.add(new JLabel("Phone Number "));
        line4.add(txtphoneNum);
        view.getContentPane().add(line4);


        btnLoad.addActionListener(new UpdateCustomerUI.LoadButtonListener());

        btnSave.addActionListener(new UpdateCustomerUI.SaveButtonListener());

    }

    public void run() {
        view.setVisible(true);
    }

    class LoadButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            Customer cust = new Customer();
            String id = txtCustomerID.getText();

            if (id.length() == 0) {
                JOptionPane.showMessageDialog(null, "Must enter non null customer ID");
                return;
            }

            try {
                cust.customerID = Integer.parseInt(id);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Invalid ID");
                return;
            }

            try {
                Socket link = new Socket("localhost", 1002);
                Scanner input = new Scanner(link.getInputStream());
                PrintWriter output = new PrintWriter(link.getOutputStream(), true);

                output.println("GET");
                output.println(cust.customerID);

                cust.lastName = input.nextLine();

                if (cust.lastName.equals("null")) {
                    JOptionPane.showMessageDialog(null, "This customer is not in the database");
                    return;
                }

                txtlastName.setText(cust.lastName);

                cust.firstName = input.nextLine();
                txtfirstName.setText(cust.firstName);

                cust.phoneNum = input.nextInt();
                txtphoneNum.setText(Integer.toString(cust.phoneNum));

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    class SaveButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            Customer cust = new Customer();
            String id = txtCustomerID.getText();

            if (id.length() == 0) {
                JOptionPane.showMessageDialog(null, "Must enter non null customer ID");
                return;
            }

            try {
                cust.customerID = Integer.parseInt(id);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Invalid ID");
                return;
            }

            String lastname = txtlastName.getText();
            if (lastname.length() == 0) {
                JOptionPane.showMessageDialog(null, "Must enter last name");
                return;
            }

            cust.lastName = lastname;

            String firstname = txtfirstName.getText();
            if (firstname.length() == 0) {
                JOptionPane.showMessageDialog(null, "Must enter first name");
                return;
            }

            cust.firstName = firstname;

            String phoneNumber = txtphoneNum.getText();
            try {
                cust.phoneNum = Integer.parseInt(phoneNumber);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Invalid Phone Number");
                return;
            }

            try {
                Socket link = new Socket("localhost", 1002);
                Scanner input = new Scanner(link.getInputStream());
                PrintWriter output = new PrintWriter(link.getOutputStream(), true);

                output.println("PUT");
                output.println(cust.customerID);
                output.println(cust.lastName);
                output.println(cust.firstName);
                output.println(cust.phoneNum);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
