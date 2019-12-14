import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeListener;

public class CustomerUI {

    public User user = null;

    public JFrame view;

    public JButton btnViewPurchases = new JButton("View Purchases");
    public JButton btnUpdatePurchases = new JButton("Update Purchases");
    public JButton btnSearchProducts = new JButton("Search for Product");

    public CustomerUI(User user) {

        this.user = user;

        this.view = new JFrame();

        view.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        view.setTitle("Store Management System");
        view.setSize(600, 400);
        view.getContentPane().setLayout(new BoxLayout(view.getContentPane(), BoxLayout.PAGE_AXIS));

        JLabel title = new JLabel("Customer View");

        title.setFont (title.getFont ().deriveFont (24.0f));
        title.setHorizontalAlignment(JLabel.CENTER);
        view.getContentPane().add(title);

        JPanel panelUser = new JPanel(new FlowLayout());

        view.getContentPane().add(panelUser);

        JPanel panelButtons = new JPanel(new FlowLayout());
        panelButtons.add(btnViewPurchases);
        panelButtons.add(btnUpdatePurchases);
        panelButtons.add(btnSearchProducts);

        view.getContentPane().add(panelButtons);


        btnViewPurchases.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {

            }
        });

        btnUpdatePurchases.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                UpdatePurchaseUI ui = new UpdatePurchaseUI();
                ui.run();
            }
        });

        btnSearchProducts.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                ProductSearchUI ui = new ProductSearchUI();
                ui.run();
            }
        });
    }

    public void run() {
        view.setVisible(true);
    }
}