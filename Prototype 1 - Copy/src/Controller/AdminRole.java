/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package Controller;

/**
 *
 * @author dashcodes
 */

import DatabaseConnector.DatabaseConnection;
import java.awt.Component;
import java.awt.Dimension;
import java.sql.*;
import java.util.EventObject;
import java.util.Vector;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

public class AdminRole {
    private final DatabaseConnection databaseConnection;
    private final DBQueries dbQueries;
    
    /**
     * Constructor initializes database connection and queries
     */
    public AdminRole() {
        databaseConnection = new DatabaseConnection();
        dbQueries = new DBQueries();
    }
    
    /**
     * Loads employee data from database into the table model
     * @param tableModel The DefaultTableModel to populate with employee data
     * @return boolean indicating success or failure
     */
    public boolean loadEmployeeData(DefaultTableModel tableModel) {
        try (Connection conn = databaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(dbQueries.getAdminAccess())) {
            
            // Clear existing rows
            tableModel.setRowCount(0);
        
            // Populate the table model with data
        while (rs.next()) {
            Object[] row = new Object[12]; // Adjust the size based on your columns
            row[0] = rs.getString("EID");
            String lastName = rs.getString("Last_Name");
            String firstName = rs.getString("First_Name");
            row[1] = lastName + ", " + firstName;
            row[2] = rs.getString("Birthday");
            row[3] = rs.getString("Address");
            row[4] = rs.getString("Phone_Number");
            row[5] = rs.getString("SSS_#");
            row[6] = rs.getString("PhilHealth_#");
            row[7] = rs.getString("TIN_#");
            row[8] = rs.getString("Pag-ibig_#");
            row[9] = rs.getString("Status");
            row[10] = rs.getString("Designation");
            row[11] = rs.getString("Half_Month_Rate");
            tableModel.addRow(row);
        }
        return true;
    } catch (SQLException e) {
        e.printStackTrace();
        return false;
        }
    }
    
    
    /**
     * Adds a new employee to the database
     * @param employee An array of employee data
     * @return boolean indicating success or failure
     */
    public boolean addEmployee(Object[] employee) {
        try (Connection conn = databaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(dbQueries.getInsertEmployee())) {
            
            stmt.setString(1, (String) employee[0]); // EID
            stmt.setString(2, (String) employee[1]); //FullName
            stmt.setString(3, (String) employee[2]); // Birthday
            stmt.setString(4, (String) employee[3]); // Address
            stmt.setString(5, (String) employee[4]); // Phone_Number
            stmt.setString(6, (String) employee[5]); // SSS_#
            stmt.setString(7, (String) employee[6]); // Philhealth_#
            stmt.setString(8, (String) employee[7]); // TIN_#
            stmt.setString(9, (String) employee[8]); // Pag-ibig_#
            stmt.setString(10, (String) employee[9]); // Status
            stmt.setString(11, (String) employee[10]); // Designation
            stmt.setDouble(12, (Double) employee[11]); // Half_Month_Rate
            
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            System.err.println("Error adding employee: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * Updates an existing employee in the database
     * @param employee An array of updated employee data
     * @return boolean indicating success or failure
     */
    public boolean updateEmployee(Object[] employee) {
        try (Connection conn = databaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(dbQueries.getUpdateEmployee())) {
            
            stmt.setString(1, (String) employee[1]); // Full Name
            stmt.setString(2, (String) employee[2]); // Birthday
            stmt.setString(3, (String) employee[3]); // Address
            stmt.setString(4, (String) employee[4]); // Phone_Number
            stmt.setString(5, (String) employee[5]); // SSS_#
            stmt.setString(6, (String) employee[6]); // Philhealth_#
            stmt.setString(7, (String) employee[7]); // TIN_#
            stmt.setString(8, (String) employee[8]); // Pag-ibig_#
            stmt.setString(9, (String) employee[9]); // Status
            stmt.setString(10, (String) employee[10]); // Designation
            stmt.setDouble(11, (Double) employee[11]); // Half_Month_Rate
            stmt.setString(12, (String) employee[0]); // EID (for WHERE clause)
            
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            System.err.println("Error updating employee: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * Deletes an employee from the database
     * @param eid Employee ID to delete
     * @return boolean indicating success or failure
     */
    public boolean deleteEmployee(String eid) {
        try (Connection conn = databaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(dbQueries.getDeleteEmployee())) {
            
            stmt.setString(1, eid);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            System.err.println("Error deleting employee: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * Checks if an employee ID already exists
     * @param eid Employee ID to check
     * @return boolean indicating if the EID exists
     */
    public boolean isEidExists(String eid) {
        try (Connection conn = databaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(dbQueries.getSearchByEID())) {
            
            stmt.setString(1, eid);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
            
        } catch (SQLException e) {
            System.err.println("Error checking EID: " + e.getMessage());
            e.printStackTrace();
        }
        
        return false;
    }
    
    /**
     * Retrieves employee data for a specific EID
     * @param eid Employee ID to retrieve
     * @return Object array containing employee data or null if not found
     */
    public Object[] getEmployeeById(String eid) {
        try (Connection conn = databaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(dbQueries.getGetEmployeeByEid())) {
            
            stmt.setString(1, eid);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                Object[] employee = new Object[12];
                employee[0] = rs.getString(dbQueries.getEid());
                String lastName = rs.getString("Last_Name");
                String firstName = rs.getString("First_Name");
                employee[1] = lastName + ", " + firstName;
                employee[2] = rs.getString(dbQueries.getBirthDay());
                employee[3] = rs.getString(dbQueries.getAddress());
                employee[4] = rs.getString(dbQueries.getPhoneNumber());
                employee[5] = rs.getString(dbQueries.getSSS());
                employee[6] = rs.getString(dbQueries.getPhilHealth());
                employee[7] = rs.getString(dbQueries.getTin());
                employee[8] = rs.getString(dbQueries.getPagIbig());
                employee[9] = rs.getString(dbQueries.getStatus());
                employee[10] = rs.getString(dbQueries.getDesignation());
                employee[11] = rs.getDouble("Half_Month_Rate");
                
                return employee;
            }
            
        } catch (SQLException e) {
            System.err.println("Error retrieving employee: " + e.getMessage());
            e.printStackTrace();
        }
        
        return null;
    }
    
    /**
     * Searches for employees based on a specific column and search term
     * @param columnName Column to search in
     * @param searchTerm Term to search for
     * @param tableModel The DefaultTableModel to populate with search results
     * @return boolean indicating success or failure
     */
    public boolean searchEmployees(String columnName, String searchTerm, DefaultTableModel tableModel) {
    String query = dbQueries.createSearchQuery(columnName);
    
    try (Connection conn = databaseConnection.getConnection();
         PreparedStatement stmt = conn.prepareStatement(query)) {
        
        stmt.setString(1, "%" + searchTerm + "%");
        ResultSet rs = stmt.executeQuery();
        
        // Clear existing rows
        tableModel.setRowCount(0);
        
        // Add data from ResultSet to the table model
        while (rs.next()) {
            Vector<Object> row = new Vector<>();
            row.add(rs.getString(dbQueries.getEid()));
            
            // Combine lastName and firstName
            String lastName = rs.getString(dbQueries.getLastName());
            String firstName = rs.getString(dbQueries.getFirstName());
            row.add(lastName + ", " + firstName);
            row.add(rs.getString(dbQueries.getBirthDay()));
            row.add(rs.getString(dbQueries.getAddress()));
            row.add(rs.getString(dbQueries.getPhoneNumber()));
            row.add(rs.getString(dbQueries.getSSS()));
            row.add(rs.getString(dbQueries.getPhilHealth()));
            row.add(rs.getString(dbQueries.getTin()));
            row.add(rs.getString(dbQueries.getPagIbig()));
            row.add(rs.getString(dbQueries.getStatus()));
            row.add(rs.getString(dbQueries.getDesignation()));
            
            try {
                double salary = rs.getDouble(dbQueries.getHalfMonthRate());
                row.add(salary);
            } catch (SQLException ex) {
                String salaryStr = rs.getString(dbQueries.getHalfMonthRate());
                if (salaryStr != null) {
                    salaryStr = salaryStr.replace(",", "");
                    try {
                        double salary = Double.parseDouble(salaryStr);
                        row.add(salary);
                    } catch (NumberFormatException e) {
                        row.add(salaryStr);
                    }
                } else {
                    row.add(0.0); // Default value if null
                }
            }
            
            tableModel.addRow(row);
        }
        
            return true;
        } catch (SQLException e) {
        System.err.println("Error searching employees: " + e.getMessage());
        e.printStackTrace();
        return false;
        }
    }
    
    /**
     * Sorts the employee data based on a specific column
     * @param columnName Column to sort by
     * @param ascending If true, sort in ascending order; otherwise descending
     * @param tableModel The DefaultTableModel to populate with sorted data
     * @return boolean indicating success or failure
     */
    public boolean sortEmployees(String columnName, boolean ascending, DefaultTableModel tableModel) {
    String query = dbQueries.createSortQuery(columnName, ascending);
    
    try (Connection conn = databaseConnection.getConnection();
         Statement stmt = conn.createStatement();
         ResultSet rs = stmt.executeQuery(query)) {
        
        // Clear existing rows
        tableModel.setRowCount(0);
        
        // Add data from ResultSet to the table model
        while (rs.next()) {
            Vector<Object> row = new Vector<>();
            row.add(rs.getString(dbQueries.getEid()));
            
            // Combine lastName and firstName
            String lastName = rs.getString(dbQueries.getLastName());
            String firstName = rs.getString(dbQueries.getFirstName());
            row.add(lastName + ", " + firstName);
            
            row.add(rs.getString(dbQueries.getBirthDay()));
            row.add(rs.getString(dbQueries.getAddress()));
            row.add(rs.getString(dbQueries.getPhoneNumber()));
            row.add(rs.getString(dbQueries.getSSS()));
            row.add(rs.getString(dbQueries.getPhilHealth()));
            row.add(rs.getString(dbQueries.getTin()));
            row.add(rs.getString(dbQueries.getPagIbig()));
            row.add(rs.getString(dbQueries.getStatus()));
            row.add(rs.getString(dbQueries.getDesignation()));
            
            // Fix for numeric value with commas - get as string and handle conversion
            try {
                // First try getting as double directly
                double salary = rs.getDouble("Half_Month_Rate");
                row.add(salary);
            } catch (SQLException ex) {
                // If that fails, get as string and clean it
                String salaryStr = rs.getString("Half_Month_Rate");
                if (salaryStr != null) {
                    // Remove commas and convert to double
                    salaryStr = salaryStr.replace(",", "");
                    try {
                        double salary = Double.parseDouble(salaryStr);
                        row.add(salary);
                    } catch (NumberFormatException e) {
                        // If all else fails, just add the string value
                        row.add(salaryStr);
                    }
                } else {
                    row.add(0.0); // Default value if null
                }
            }
            
            tableModel.addRow(row);
        }
        
        return true;
    } catch (SQLException e) {
        System.err.println("Error sorting employees: " + e.getMessage());
        e.printStackTrace();
        return false;
    }
}
    
    /**
     * Gets the column names for the employee table
     * @return Array of column names
     */
    public String[] getColumnNames() {
        return new String[]
        {"EID",
        "Last_Name",
        "First_Name",
        "Birthday",
        "Address", 
        "Phone_Number",
        "SSS #",
        "PhilHealth #",
        "TIN #",
        "Pag-ibig #", 
        "Status",
        "Designation",
        "Gross Salary"};
    }
    
    /**
     * Validate employee data before adding/updating
     * @param employee
     * @return
     */
    
    public String validateEmployeeData(Object[] employee) {
        if (employee[0] == null || employee[0].toString().trim().isEmpty()) {
            return "EID is required.";
        }
        if (employee[1] == null || employee[1].toString().trim().isEmpty()) {
            return "Last Name is required.";
        }
        if (employee[2] == null || employee[2].toString().trim().isEmpty()) {
            return "First Name is required.";
        }
        return null; // Return null if validation passes
    }

    // Show a confirmation dialog for deletion
    public boolean confirmDeletion(String employeeName) {
        int confirm = JOptionPane.showConfirmDialog(null,
                "Are you sure you want to delete employee " + employeeName + "?",
                "Confirm Deletion", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
        return confirm == JOptionPane.YES_OPTION;
    }

    // Show a success or error message
    public void showMessage(String message, String title, boolean isError) {
        JOptionPane.showMessageDialog(null, message, title,
                isError ? JOptionPane.ERROR_MESSAGE : JOptionPane.INFORMATION_MESSAGE);
    }
    
    /**
    * Saves a new employee from the last row of the table
    * @param tableModel The table model containing employee data
    * @return boolean indicating success or failure
    */
    public boolean saveNewEmployeeFromTable(DefaultTableModel tableModel) {
        int lastRow = tableModel.getRowCount() - 1;
    
        // Check if the row has data
        boolean hasData = false;
        for (int i = 0; i < tableModel.getColumnCount(); i++) {
        if (tableModel.getValueAt(lastRow, i) != null && 
            !tableModel.getValueAt(lastRow, i).toString().trim().isEmpty()) {
            hasData = true;
            break;
            }
        }
    
        if (!hasData) {
            showMessage("No new employee data to save.", "Information", false);
            return false;
        }
    
        // Collect data from the last row
        Object[] employee = new Object[12];
        for (int i = 0; i < tableModel.getColumnCount(); i++) {
            Object value = tableModel.getValueAt(lastRow, i);

            // Handle null values or convert to appropriate types
            if (value == null || value.toString().trim().isEmpty()) {
                if (i == 11) { // Gross Salary column
                    employee[i] = 0.0; // Default value for Double
                } else {
                    employee[i] = ""; // Default value for String
                }
            } else {
                if (i == 11 && !(value instanceof Double)) {
                    try {
                        employee[i] = Double.parseDouble(value.toString().trim());
                    } catch (NumberFormatException e) {
                        showMessage("Invalid Gross Salary. Please enter a valid number.", "Validation Error", true);
                        return false;
                    }
                } else {
                    employee[i] = value.toString().trim();
                }
            }
        }

        // Validate the data
        String validationError = validateEmployeeData(employee);
        if (validationError != null) {
            showMessage(validationError, "Validation Error", true);
            return false;
        }

        // Check if EID already exists
        if (isEidExists(employee[0].toString())) {
            showMessage("EID already exists. Please use a unique EID.", "Duplicate EID", true);
            return false;
        }

        // Add the employee to the database
        if (addEmployee(employee)) {
            showMessage("Employee added successfully.", "Success", false);

            // Clear the last row
            for (int i = 0; i < tableModel.getColumnCount(); i++) {
                tableModel.setValueAt(null, lastRow, i);
            }

            // Reload data to ensure it's properly formatted and sorted
            loadEmployeeData(tableModel);
                return true;
            } else {
            showMessage("Failed to add employee.", "Error", true);
            return false;
            }
        }
    
    /**
    * Sets up a table with the last row being editable for new employee entry
    * @param table The JTable to set up
    * @param tableModel The table model to use
    */
   public void setupEditableTable(JTable table, DefaultTableModel tableModel) {
       // Make only the last row editable
       table.setDefaultEditor(Object.class, new DefaultCellEditor(new JTextField()) {
           @Override
           public boolean isCellEditable(EventObject e) {
               int row = table.getSelectedRow();
               return row == table.getRowCount() - 1;
           }
       });

       // Set up table model with proper column types
       tableModel.setColumnIdentifiers(new String[] {
           "EID", "Full Name", "Birthday", "Address", "Phone Number",
           "SSS #", "PhilHealth #", "TIN #", "Pag-Ibig #", "Status", 
           "Designation", "Gross Salary"
       });

       // Set the salary column to display as Double
       table.getColumnModel().getColumn(11).setCellRenderer(new DefaultTableCellRenderer() {
           @Override
           public Component getTableCellRendererComponent(JTable table, Object value, 
                   boolean isSelected, boolean hasFocus, int row, int column) {
               if (value instanceof Double) {
                   setText(String.format("%.2f", (Double)value));
               } else if (value != null) {
                   setText(value.toString());
               } else {
                   setText("");
               }
               return this;
           }
       });
   }

   /**
    * Adds an empty row to the table model
    * @param tableModel The table model to add the row to
    */
   public void addEmptyRowToTable(DefaultTableModel tableModel) {
       Object[] emptyRow = new Object[12];
       tableModel.addRow(emptyRow);
   }

   /**
    * Shows a dialog for updating an employee
    * @param parent The parent frame
    * @param eid The employee ID to update
    * @param tableModel The table model to refresh after update
    */
   public void showUpdateDialog(JFrame parent, String eid, DefaultTableModel tableModel) {
       Object[] employee = getEmployeeById(eid);

       if (employee == null) {
           showMessage("Could not retrieve employee data.", "Error", true);
           return;
       }

       JDialog updateDialog = new JDialog(parent, "Update Employee", true);
       updateDialog.setSize(new Dimension(600, 500));
       updateDialog.setLocationRelativeTo(parent);

       // Create form panel
       JPanel formPanel = new JPanel();
       formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));

       // Add fields with pre-filled values
       JTextField eidField = new JTextField(employee[0].toString(), 10);
       eidField.setEditable(false); // EID should not be editable
       JTextField lastNameField = new JTextField(employee[1].toString(), 20);
       JTextField firstNameField = new JTextField(employee[2].toString(), 20);
       // Add other fields as needed

       // Add labels and fields to form panel
       formPanel.add(new JLabel("EID:"));
       formPanel.add(eidField);
       formPanel.add(new JLabel("Last Name:"));
       formPanel.add(lastNameField);
       formPanel.add(new JLabel("First Name:"));
       formPanel.add(firstNameField);
       // Add other fields as needed

       // Add buttons
       JButton updateButton = new JButton("Update");
       JButton cancelButton = new JButton("Cancel");

       // Add buttons to a button panel
       JPanel buttonPanel = new JPanel();
       buttonPanel.add(updateButton);
       buttonPanel.add(cancelButton);

       // Add form panel and button panel to dialog
       updateDialog.add(formPanel, "Center");
       updateDialog.add(buttonPanel, "South");

       // Add action listeners to buttons
       updateButton.addActionListener(e -> {
           Object[] updatedEmployee = new Object[12];
           updatedEmployee[0] = eid; // Keep original EID
           updatedEmployee[1] = lastNameField.getText().trim();
           updatedEmployee[2] = firstNameField.getText().trim();
           // ... (Fill in remaining fields)

           String validationError = validateEmployeeData(updatedEmployee);
           if (validationError != null) {
               showMessage(validationError, "Validation Error", true);
               return;
           }

           if (updateEmployee(updatedEmployee)) {
               showMessage("Employee updated successfully.", "Success", false);
               loadEmployeeData(tableModel);
               updateDialog.dispose();
           } else {
               showMessage("Failed to update employee.", "Error", true);
           }
       });

       cancelButton.addActionListener(e -> updateDialog.dispose());

       // Show dialog
       updateDialog.setVisible(true);
   }

   /**
    * Deletes an employee after confirmation
    * @param eid The employee ID to delete
    * @param tableModel The table model to refresh after deletion
    */
   public void deleteEmployeeWithConfirmation(String eid, DefaultTableModel tableModel) {
       // Find employee name from table model or database
       String employeeName = getEmployeeNameById(eid);

       if (confirmDeletion(employeeName)) {
           if (deleteEmployee(eid)) {
               showMessage("Employee deleted successfully.", "Success", false);
               loadEmployeeData(tableModel);
           } else {
               showMessage("Failed to delete employee.", "Error", true);
           }
       }
   }

   /**
    * Gets an employee's name by their ID
    * @param eid The employee ID
    * @return The employee's full name
    */
   private String getEmployeeNameById(String eid) {
       Object[] employee = getEmployeeById(eid);
       if (employee != null && employee.length >= 3) {
           return employee[1].toString() + " " + employee[2].toString();
       }
       return "Employee #" + eid;
   }

   /**
    * Shows a dialog for searching employees
    * @param parent The parent frame
    * @param tableModel The table model to display search results
    */
   public void showSearchDialog(JFrame parent, DefaultTableModel tableModel) {
       JDialog searchDialog = new JDialog(parent, "Search Employees", true);
       searchDialog.setSize(new Dimension(400, 200));
       searchDialog.setLocationRelativeTo(parent);

       // Create panel
       JPanel panel = new JPanel();
       panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

       // Add components
       JComboBox<String> columnComboBox = new JComboBox<>(new String[]{"EID"});
       JTextField searchField = new JTextField(20);

       // Add labels and components to panel
       panel.add(new JLabel("Search by:"));
       panel.add(columnComboBox);
       panel.add(new JLabel("Search term:"));
       panel.add(searchField);

       // Add buttons
       JButton searchButton = new JButton("Search");
       JButton cancelButton = new JButton("Cancel");

       // Add buttons to a button panel
       JPanel buttonPanel = new JPanel();
       buttonPanel.add(searchButton);
       buttonPanel.add(cancelButton);

       // Add panels to dialog
       searchDialog.add(panel, "Center");
       searchDialog.add(buttonPanel, "South");

       // Add action listeners to buttons
       searchButton.addActionListener(e -> {
           String column = (String) columnComboBox.getSelectedItem();
           String searchTerm = searchField.getText().trim();

           if (searchTerm.isEmpty()) {
               showMessage("Please enter a search term.", "Error", true);
               return;
           }

           String dbColumnName = column.replace(" ", "_");
           if (searchEmployees(dbColumnName, searchTerm, tableModel)) {
               searchDialog.dispose();
           } else {
               showMessage("Search failed.", "Error", true);
           }
       });

       cancelButton.addActionListener(e -> searchDialog.dispose());

       // Show dialog
       searchDialog.setVisible(true);
   }

    /**
     * Shows a dialog for sorting employees
     * @param parent The parent frame
     * @param tableModel The table model to sort
     */
    public void showSortDialog(JFrame parent, DefaultTableModel tableModel) {
        JDialog sortDialog = new JDialog(parent, "Sort Employees", true);
        sortDialog.setSize(new Dimension(400, 200));
        sortDialog.setLocationRelativeTo(parent);

        // Create panel
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        // Add components
        JComboBox<String> columnComboBox = new JComboBox<>(new String[]{
            "Status", "Designation"
        });
        JRadioButton ascendingRadio = new JRadioButton("Ascending", true);
        JRadioButton descendingRadio = new JRadioButton("Descending", false);

        // Group radio buttons
        ButtonGroup sortOrderGroup = new ButtonGroup();
        sortOrderGroup.add(ascendingRadio);
        sortOrderGroup.add(descendingRadio);

        // Add labels and components to panel
        panel.add(new JLabel("Sort by:"));
        panel.add(columnComboBox);
        panel.add(new JLabel("Sort order:"));
        panel.add(ascendingRadio);
        panel.add(descendingRadio);

        // Add buttons
        JButton sortButton = new JButton("Sort");
        JButton cancelButton = new JButton("Cancel");

        // Add buttons to a button panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(sortButton);
        buttonPanel.add(cancelButton);

        // Add panels to dialog
        sortDialog.add(panel, "Center");
        sortDialog.add(buttonPanel, "South");

        // Add action listeners to buttons
        sortButton.addActionListener(e -> {
            String column = (String) columnComboBox.getSelectedItem();
            boolean ascending = ascendingRadio.isSelected();

            String dbColumnName = column.replace(" ", "_");
            if (sortEmployees(dbColumnName, ascending, tableModel)) {
                sortDialog.dispose();
            } else {
                showMessage("Sort failed.", "Error", true);
            }
        });

        cancelButton.addActionListener(e -> sortDialog.dispose());

        // Show dialog
        sortDialog.setVisible(true);
    }
}