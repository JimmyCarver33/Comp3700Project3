import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class UpdatePurchaseUI {
    public JFrame view;

    public JButton btnLoad = new JButton("Load Purchase");
    public JButton btnSave = new JButton("Save Purchase");

    public JTextField txtPurchaseID = new JTextField(20);
    public JTextField txtCustomerID = new JTextField(20);
    public JTextField txtProductID = new JTextField(20);
    public JTextField txtQuantity = new JTextField(20);

    public UpdatePurchaseUI()   {

        this.view = new JFrame();

        view.setTitle("Update Purchase Information");
        view.setSize(600, 400);
        view.getContentPane().setLayout(new BoxLayout(view.getContentPane(), BoxLayout.PAGE_AXIS));

        JPanel line1 = new JPanel(new FlowLayout());
        line1.add(new JLabel("PurchaseID "));
        line1.add(txtPurchaseID);
        view.getContentPane().add(line1);

        JPanel line2 = new JPanel(new FlowLayout());
        line2.add(new JLabel("CustomerID "));
        line2.add(txtCustomerID);
        view.getContentPane().add(line2);

        JPanel line3 = new JPanel(new FlowLayout());
        line3.add(new JLabel("ProductID "));
        line3.add(txtProductID);
        view.getContentPane().add(line3);

        JPanel line4 = new JPanel(new FlowLayout());
        line4.add(new JLabel("Quantity "));
        line4.add(txtQuantity);
        view.getContentPane().add(line4);

        JPanel panelButtons = new JPanel(new FlowLayout());
        panelButtons.add(btnLoad);
        panelButtons.add(btnSave);
        view.getContentPane().add(panelButtons);

        btnLoad.addActionListener(new UpdatePurchaseUI.LoadButtonListener());

        btnSave.addActionListener(new UpdatePurchaseUI.SaveButtonListener());

    }

    public void run() {
        view.setVisible(true);
    }

    class LoadButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            Purchase purchase = new Purchase();
            String id = txtPurchaseID.getText();

            if (id.length() == 0) {
                JOptionPane.showMessageDialog(null, "Must enter non null purchase ID");
                return;
            }

            try {
                purchase.purchaseID = Integer.parseInt(id);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Invalid ID");
                return;
            }

            try {
                Socket link = new Socket("localhost", 1003);
                Scanner input = new Scanner(link.getInputStream());
                PrintWriter output = new PrintWriter(link.getOutputStream(), true);

                output.println("GET");
                output.println(purchase.purchaseID);

                purchase.customerID = input.nextInt();

                //if (purchase.customerID.equals("null")) {
                    //JOptionPane.showMessageDialog(null, "This product is not in the database");
                    //return;
                //}

                txtCustomerID.setText(Integer.toString(purchase.customerID));

                purchase.productID = input.nextInt();
                txtProductID.setText(Integer.toString(purchase.productID));

                purchase.quantity = input.nextInt();
                txtQuantity.setText(Integer.toString(purchase.quantity));

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    class SaveButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            Purchase purchase = new Purchase();
            String id = txtPurchaseID.getText();

            if (id.length() == 0) {
                JOptionPane.showMessageDialog(null, "Must enter non null purchase ID");
                return;
            }

            try {
                purchase.purchaseID = Integer.parseInt(id);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Invalid ID");
                return;
            }

            String custID = txtCustomerID.getText();
            if (custID.length() == 0) {
                JOptionPane.showMessageDialog(null, "Must enter customer ID");
                return;
            }

            try {
                purchase.customerID = Integer.parseInt(custID);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Invalid Customer ID");
                return;
            }

            String prodID = txtProductID.getText();
            try {
                purchase.productID = Integer.parseInt(prodID);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Invalid ProductID");
                return;
            }

            String qty = txtQuantity.getText();
            try {
                purchase.quantity = Integer.parseInt(qty);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Invalid Quantity");
                return;
            }

            try {
                Socket link = new Socket("localhost", 1003);
                Scanner input = new Scanner(link.getInputStream());
                PrintWriter output = new PrintWriter(link.getOutputStream(), true);

                output.println("PUT");
                output.println(purchase.purchaseID);
                output.println(purchase.customerID);
                output.println(purchase.productID);
                output.println(purchase.quantity);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
