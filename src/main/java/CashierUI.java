import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CashierUI {
    public JFrame view;

    public JButton btnUpdatePurchases = new JButton("Update Purchases");
    public JButton btnUpdateCustomers = new JButton("Update Customers");

    public CashierUI(User user) {
        this.view = new JFrame();

        view.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        view.setTitle("Store Management System - Cashier View");
        view.setSize(600, 400);
        view.getContentPane().setLayout(new BoxLayout(view.getContentPane(), BoxLayout.PAGE_AXIS));

        JLabel title = new JLabel("Store Management-Cashier");

        title.setFont (title.getFont ().deriveFont (24.0f));
        view.getContentPane().add(title);

        JPanel panelButtons = new JPanel(new FlowLayout());
        panelButtons.add(btnUpdatePurchases);
        panelButtons.add(btnUpdateCustomers);

        view.getContentPane().add(panelButtons);


        btnUpdatePurchases.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                UpdatePurchaseUI ap = new UpdatePurchaseUI();
                ap.run();
            }
        });

        btnUpdateCustomers.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                UpdateCustomerUI ui = new UpdateCustomerUI();
                ui.run();
            }
        });
    }
    public void run() {
        view.setVisible(true);
    }
}