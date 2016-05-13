import java.io.*;
import java.util.*;

public class InventoryFileHandler {

    // Builds a reliable absolute path
    private static String resolvePath(String fileName) {
        String path = System.getProperty("user.dir") + File.separator + fileName;
        System.out.println("Using file path: " + path); // Debug log
        return path;
    }

    public static void save(List<InventoryItem> items, String fileName) throws IOException {
        String path = resolvePath(fileName);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(path))) {
            for (InventoryItem item : items) {
                writer.write(item.getName() + "," + item.getQuantity() + "," + item.getPrice());
                writer.newLine();
            }
        }
    }

    public static List<InventoryItem> load(String fileName) throws IOException {
        String path = resolvePath(fileName);
        File file = new File(path);
        List<InventoryItem> items = new ArrayList<>();

        if (!file.exists()) return items;

        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    String name = parts[0];
                    int quantity = Integer.parseInt(parts[1]);
                    double price = Double.parseDouble(parts[2]);
                    items.add(new InventoryItem(name, quantity, price));
                }
            }
        }

        return items;
    }
}

