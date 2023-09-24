package pages;

import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import product.Product;
import server.Client;
import server.Response;

public class ProductsDialog extends JPanel {
    private JTextField NameField = new JTextField(20);
    private JTextField PriceField = new JTextField(20);
    private JTextField QuantityField = new JTextField(20);
    private JTextField TypeField = new JTextField(20);

    public ProductsDialog(Product product, boolean isUpdate) {
        setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.BOTH;
        constraints.insets = new Insets(5, 10, 5, 10);

        JLabel nameLabel = new JLabel("Product name:");
        constraints.gridx = 0;
        constraints.gridy = 0;
        add(nameLabel, constraints);

        // NameField.setColumns(20);
        NameField.setText(product.Name != null ? product.Name : "");
        constraints.gridx = 1;
        constraints.gridy = 0;
        add(NameField, constraints);

        JLabel priceLabel = new JLabel("Product price:");
        constraints.gridx = 0;
        constraints.gridy = 1;
        add(priceLabel, constraints);

        // PriceField.setColumns(20);
        PriceField.setText(product.Price != null ? product.Price.toString() : "");
        constraints.gridx = 1;
        constraints.gridy = 1;
        add(PriceField, constraints);

        JLabel quantityLabel = new JLabel("Product quantity:");
        constraints.gridx = 0;
        constraints.gridy = 2;
        add(quantityLabel, constraints);

        // QuantityField.setColumns(20);
        QuantityField.setText(product.Quantity != null ? product.Quantity.toString() : "");
        constraints.gridx = 1;
        constraints.gridy = 2;
        add(QuantityField, constraints);

        JLabel typeLabel = new JLabel("Product type:");
        constraints.gridx = 0;
        constraints.gridy = 3;
        add(typeLabel, constraints);

        // TypeField.setColumns(20);
        TypeField.setText(product.Type != null ? product.Type : "");
        constraints.gridx = 1;
        constraints.gridy = 3;
        add(TypeField, constraints);

        JButton submitButton = new JButton(!isUpdate ? "Create" : "Edit");
        constraints.gridx = 0;
        constraints.gridy = 4;
        add(submitButton, constraints);

        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                Product request = new Product();

                request.setRequestType("create");
                if (isUpdate) {
                    request.setId(product.getId());
                    request.setRequestType("update");
                }
                request.setName(NameField.getText());
                request.setPrice(Integer.parseInt(PriceField.getText()));
                request.setQuantity(Integer.parseInt(QuantityField.getText()));
                request.setType(TypeField.getText());

                try {
                    Client.sendRequest(request);
                    Response res = Client.getResponse();
                    if (res.message == "error") {
                        JOptionPane.showMessageDialog(submitButton, res.message + "\n" + res.object.toString());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                Container container = submitButton.getParent();
                while (!(container instanceof JDialog) && container != null) {
                    container = container.getParent();
                }

                if (container instanceof JDialog) {
                    JDialog dialog = (JDialog) container;
                    dialog.dispose();
                }
                Products.Refresh();
            }
        });
    }
}
