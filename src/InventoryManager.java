import java.util.*;

public class InventoryManager {
    private List<InventoryItem> items = new ArrayList<>();

    public void addItem(InventoryItem item) {
        items.add(item);
    }

    public boolean removeItem(String name) {
        return items.removeIf(item -> item.getName().equalsIgnoreCase(name));
    }

    public List<InventoryItem> getItems() {
        return items;
    }
}
