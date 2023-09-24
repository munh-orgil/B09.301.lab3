package pages;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Vector;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumnModel;

import javafx.scene.paint.Color;
import product.Product;
import server.Client;
import server.Response;

public class Products extends JPanel {
    final Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
    final int width = (int) screen.getWidth();
    final int height = (int) screen.getHeight();
    public static JFrame frame;

    public static void Refresh() {
        frame.setVisible(false);
        new Init();
    }

    public Products() {
        BoxLayout bl = new BoxLayout(this, BoxLayout.X_AXIS);
        setLayout(bl);
        Product request = new Product();
        request.RequestType = "list";
        try {
            Client.sendRequest(request);
        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
        }

        Response res = Client.getResponse();
        System.out.println(res.message);
        if (res.message.equals("error")) {
            return;
        }

        Vector<Product> list = Vector.class.cast(res.object);

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.BOTH;
        constraints.insets = new Insets(5, 10, 5, 10);

        JPanel controlPanel = new JPanel(new GridBagLayout());

        JButton addProduct = new JButton("Add product");
        addProduct.setPreferredSize(new Dimension(200, 80));
        constraints.gridx = 0;
        constraints.gridy = 0;
        controlPanel.add(addProduct, constraints);
        JButton editProduct = new JButton("Edit product");
        editProduct.setPreferredSize(new Dimension(200, 80));
        constraints.gridx = 0;
        constraints.gridy = 1;
        controlPanel.add(editProduct, constraints);
        JButton deleteProduct = new JButton("Delete product");
        deleteProduct.setPreferredSize(new Dimension(200, 80));
        constraints.gridx = 0;
        constraints.gridy = 2;
        controlPanel.add(deleteProduct, constraints);
        JButton refreshButton = new JButton("Refresh");
        refreshButton.setPreferredSize(new Dimension(200, 80));
        constraints.gridx = 0;
        constraints.gridy = 3;
        controlPanel.add(refreshButton, constraints);
        refreshButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Refresh();
            }
        });

        controlPanel.setPreferredSize(new Dimension(1, height));
        controlPanel.setMinimumSize(new Dimension(300, height));
        controlPanel.setMaximumSize(new Dimension(300, height));
        controlPanel.setPreferredSize(new Dimension(300, height));

        // addProduct.addActionListener(e -> {
        // JOptionPane.showOptionDialog(
        // this,
        // new ClassesDialog(new backend.models.Classes(), false),
        // "Add product",
        // JOptionPane.DEFAULT_OPTION,
        // JOptionPane.PLAIN_MESSAGE,
        // null,
        // new Object[] {},
        // null);
        // });

        add(controlPanel);

        if (list.size() == 0) {
            JPanel noProductPanel = new JPanel(new BorderLayout());
            JLabel noProductLabel = new JLabel("Product not found! 404");
            Font font = noProductLabel.getFont();
            noProductLabel.setFont(font.deriveFont(30f));
            noProductPanel.add(noProductLabel, BorderLayout.CENTER);
            noProductPanel
                    .setMaximumSize(new Dimension(width, (int) (height * 0.5)));
            add(noProductPanel);
        } else {
            JPanel productsPanel = new JPanel();
            BoxLayout productsLayout = new BoxLayout(productsPanel, BoxLayout.Y_AXIS);
            productsPanel.setLayout(productsLayout);

            Vector<String> titles = new Vector<String>();
            titles.add("Name");
            titles.add("Price");
            titles.add("Quantity");
            titles.add("Type");
            titles.add("Created at");
            Vector<Vector<Object>> tableContent = new Vector<>();
            for (Product p : list) {
                Vector<Object> row = new Vector<>();
                row.add(p.Name);
                row.add(p.Price);
                row.add(p.Quantity);
                row.add(p.Type);
                row.add(p.CreatedAt);

                tableContent.add(row);
            }
            JTable table = new JTable(tableContent, titles);
            table.setBounds(30, 40, 200, 300);
            table.setAlignmentY(TOP_ALIGNMENT);
            table.setRowHeight(30);
            table.getTableHeader().setPreferredSize(new Dimension(width, 40));
            TableColumnModel columnModel = table.getColumnModel();
            columnModel.getColumn(0).setPreferredWidth(200);
            columnModel.getColumn(1).setPreferredWidth(100);
            columnModel.getColumn(2).setPreferredWidth(100);
            columnModel.getColumn(3).setPreferredWidth(200);
            columnModel.getColumn(4).setPreferredWidth(200);

            DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
            centerRenderer.setHorizontalAlignment(JLabel.CENTER);

            columnModel.getColumn(0).setCellRenderer(centerRenderer);
            columnModel.getColumn(1).setCellRenderer(centerRenderer);
            columnModel.getColumn(2).setCellRenderer(centerRenderer);
            columnModel.getColumn(3).setCellRenderer(centerRenderer);
            columnModel.getColumn(4).setCellRenderer(centerRenderer);

            JScrollPane scrollPane = new JScrollPane(table);

            editProduct.addActionListener(e -> {
                JOptionPane.showOptionDialog(
                        this,
                        new ProductsDialog(list.get(table.getSelectedRow()), true),
                        "Edit product",
                        JOptionPane.DEFAULT_OPTION,
                        JOptionPane.PLAIN_MESSAGE,
                        null,
                        new Object[] {},
                        null);
            });
            deleteProduct.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent ae) {
                    int[] rows = table.getSelectedRows();
                    for (int row : rows) {
                        Product reqProduct = new Product();
                        reqProduct.RequestType = "delete";
                        reqProduct.Id = (Integer) list.get(row).getId();
                        try {
                            Client.sendRequest(reqProduct);
                        } catch (ClassNotFoundException | IOException e) {
                            e.printStackTrace();
                        }
                    }
                    Refresh();
                }
            });

            add(scrollPane);
        }

        addProduct.addActionListener(e -> {
            JOptionPane.showOptionDialog(
                    this,
                    new ProductsDialog(new Product(), false),
                    "Create product",
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.PLAIN_MESSAGE,
                    null,
                    new Object[] {},
                    null);
        });
    }

}
