import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.List;

public class MainApp {
    private InventoryManager manager = new InventoryManager();
    private DefaultListModel<String> listModel = new DefaultListModel<>();

    public void createGUI() {
        JFrame frame = new JFrame("Inventory Manager");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(450, 500);

        JTextField nameField = new JTextField(10);
        JTextField qtyField = new JTextField(5);
        JTextField priceField = new JTextField(5);
        JButton addBtn = new JButton("Add Item");
        JButton removeBtn = new JButton("Remove Selected");
        JButton saveBtn = new JButton("Save to File");

        JList<String> itemList = new JList<>(listModel);

        // 🔁 Load inventory from file on startup
        try {
            List<InventoryItem> loadedItems = InventoryFileHandler.load("inventory.txt");
            for (InventoryItem item : loadedItems) {
                manager.addItem(item);
                listModel.addElement(item.toString());
            }
        } catch (IOException ex) {
            System.out.println("No saved inventory found or file read error.");
        }

        // 🔲 Input Panel
        JPanel inputPanel = new JPanel();
        inputPanel.add(new JLabel("Name:"));
        inputPanel.add(nameField);
        inputPanel.add(new JLabel("Qty:"));
        inputPanel.add(qtyField);
        inputPanel.add(new JLabel("Price:"));
        inputPanel.add(priceField);
        inputPanel.add(addBtn);

        // 🔳 Bottom Button Panel
        JPanel bottomPanel = new JPanel();
        bottomPanel.add(removeBtn);
        bottomPanel.add(saveBtn);

        // 🖼️ Layout
        frame.add(inputPanel, BorderLayout.NORTH);
        frame.add(new JScrollPane(itemList), BorderLayout.CENTER);
        frame.add(bottomPanel, BorderLayout.SOUTH);

        // ➕ Add Item Event with Auto-Save
        addBtn.addActionListener(e -> {
            try {
                String name = nameField.getText().trim();
                int qty = Integer.parseInt(qtyField.getText().trim());
                double price = Double.parseDouble(priceField.getText().trim());

                InventoryItem item = new InventoryItem(name, qty, price);
                manager.addItem(item);
                listModel.addElement(item.toString());

                // Auto-save after adding
                InventoryFileHandler.save(manager.getItems(), "inventory.txt");

                nameField.setText(""); qtyField.setText(""); priceField.setText("");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "Invalid number format.");
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(frame, "Auto-save failed: " + ex.getMessage());
            }
        });

        // ❌ Remove Item Event
        removeBtn.addActionListener(e -> {
            int index = itemList.getSelectedIndex();
            if (index != -1) {
                String itemLine = listModel.get(index);
                String itemName = itemLine.split(" - ")[0];
                manager.removeItem(itemName);
                listModel.remove(index);
                try {
                    InventoryFileHandler.save(manager.getItems(), "inventory.txt");
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(frame, "Auto-save after removal failed: " + ex.getMessage());
                }
            }
        });

        // 💾 Manual Save Button
        saveBtn.addActionListener(e -> {
            try {
                InventoryFileHandler.save(manager.getItems(), "inventory.txt");
                JOptionPane.showMessageDialog(frame, "Inventory saved to inventory.txt");
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(frame, "Error saving file: " + ex.getMessage());
            }
        });

        // 🛑 Save on App Close
        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                try {
                    InventoryFileHandler.save(manager.getItems(), "inventory.txt");
                    System.out.println("Inventory saved on close.");
                } catch (IOException ex) {
                    System.err.println("Failed to save inventory on close: " + ex.getMessage());
                }
            }
        });

        frame.setVisible(true);
    }

    public static void main(String[] args) {
        new MainApp().createGUI();
    }
}
