# Shopping_Inventory_App
A simple, intuitive inventory management application built using **Java Swing** for GUI and basic file I/O for persistent storage. This project simulates a desktop inventory system, complete with auto-save and file loading, all managed in a beginner-friendly and retro Java style (like you'd code back in 2016).

---

## 🚀 Features

- 🖥️ **User-friendly GUI** using Java Swing
- 💾 **Auto-saves inventory** on every add/remove action
- 🧠 **Loads previous inventory** on startup (from `inventory.txt`)
- 📄 Saves as a plain CSV-style `.txt` file
- ⚙️ Works with both **Windows, Linux, and WSL**
- 📁 Auto-detects working directory using `System.getProperty("user.dir")`

---

## 🧰 Project Structure
ShoppingInventoryManager/
├── src/
│ ├── InventoryItem.java
│ ├── InventoryManager.java
│ ├── InventoryFileHandler.java ← handles file save/load
│ ├──MainApp.java ← main GUI logic
| └── inventory.txt ← saved inventory data (created automatically)

## 📦 How to Compile & Run (Cross-platform)

### ✅ Requirements:
- Java 8 or higher
- (Optional) WSL or Linux terminal

### 🔧 Compilation Steps:

```bash
# Go to source folder
cd ShoppingInventoryManager/src

# Compile
javac *.java

# Run the app
java MainApp
```

└── README.md
