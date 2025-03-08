/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package Controller;

/**
 *
 * @author dashcodes
 */

import javax.swing.*;
import javax.swing.table.*;
import java.awt.event.*;
import java.util.HashMap;

public class UIController {
    private final AdminRole adminController;
    private final HashMap<String, JFrame> frames;
    private JTable currentEmployeeTable;

    public UIController(AdminRole adminController) {
        this.adminController = adminController;
        this.frames = new HashMap<>();
    }

    // === Core UI Management ===
    public void registerFrame(String frameName, JFrame frame) {
        frames.put(frameName, frame);
    }

    public void showFrame(String frameName) {
        frames.values().forEach(f -> f.setVisible(false));
        frames.get(frameName).setVisible(true);
    }

    // === Table Management ===
    public void initializeEmployeeTable(JTable table) {
        this.currentEmployeeTable = table;
        DefaultTableModel model = new DefaultTableModel(adminController.getColumnNames(), 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return row == getRowCount() - 1;
            }
        };
        table.setModel(model);
        adminController.setupEditableTable(table, model);
    }

    // === Common Operations ===
    public void handleAddEmployee() {
        if (currentEmployeeTable != null) {
            DefaultTableModel model = (DefaultTableModel) currentEmployeeTable.getModel();
            model.addRow(new Object[12]);
            currentEmployeeTable.scrollRectToVisible(
                currentEmployeeTable.getCellRect(model.getRowCount()-1, 0, true)
            );
        }
    }

    public boolean handleSaveEmployee() {
        if (currentEmployeeTable != null) {
            return adminController.saveNewEmployeeFromTable(
                (DefaultTableModel) currentEmployeeTable.getModel()
            );
        }
        return false;
    }

    // === Navigation Handlers ===
    public ActionListener createNavigationListener(String targetFrame) {
        return e -> showFrame(targetFrame);
    }

    // === Dialog Management ===
    public void showConfirmationDialog(String message, Runnable onConfirm) {
        int result = JOptionPane.showConfirmDialog(
            null, 
            message,
            "Confirmation",
            JOptionPane.YES_NO_OPTION
        );
        
        if (result == JOptionPane.YES_OPTION) {
            onConfirm.run();
        }
    }

    // === Search/Sort Handlers ===
    public ActionListener createSearchHandler(JTextField searchField, JComboBox<String> searchType) {
        return e -> {
            adminController.searchEmployees(
                (String) searchType.getSelectedItem(),
                searchField.getText(),
                (DefaultTableModel) currentEmployeeTable.getModel()
            );
        };
    }
}

