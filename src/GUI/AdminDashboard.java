/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package GUI;


/**
 *
 * @author dashcoades
 */

import BusinessLogic.DashboardData;
import BusinessLogic.EmployeeDetails;
import Controller.RoleAuthenticator;
import Controller.AdminRole;
import java.sql.Connection;
import javax.swing.JOptionPane;
import java.awt.BorderLayout;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.Dimension;

public class AdminDashboard extends javax.swing.JFrame {
    // Core Components
    private RoleAuthenticator roleAuthenticator;
    private Connection connection;
    private String loggedInUsername;

    // Business Logic Components
    private EmployeeDetails employeeDetails;
    private DashboardData dashboardData;

    // Controller Components
    private AdminRole adminRole;
    private DefaultTableModel tableModel;
    
    /**
     * Constructor that accepts username to load user-specific data
     * @param username The username of the logged-in user
     * @param connection Database connection
     */
    public AdminDashboard(String username, Connection connection) {
        initComponents();
        
        // Initially save button is disabled to avoid conflicts
        saveButton.setEnabled(false);
        
        // Initialize AdminRole and tableModel
        adminRole = new AdminRole();
        tableModel = (DefaultTableModel) employeeTable.getModel();
        
        // Set column names
        String[] columnNames = {
            "EID", "Full Name", "Birthday", "Address", "Phone Number",
            "SSS #", "PhilHealth #", "TIN #", "Pag-Ibig #", "Status",
            "Designation", "Gross Salary"
        };
        tableModel.setColumnIdentifiers(columnNames);
        
        // Store the database connection and username
        this.connection = connection;
        this.loggedInUsername = username;
        
        // Load employee data and initialize dashboard
        initializeDashboard(username);
        
        // Load employee data into the table
        if (connection != null) {
            adminRole.loadEmployeeData(tableModel);
        } else {
            JOptionPane.showMessageDialog(this,
                "Database connection is not established.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    // Constructor that accepts user data
    public AdminDashboard(
        String firstName,
        String lastName,
        String designation,
        int eid,
        float basicPay,
        float totalAllowances,
        float totalDeductions,
        float netPay) 
    {
        initComponents();

        // Initialize AdminRole and tableModel
        adminRole = new AdminRole();
        tableModel = (DefaultTableModel) employeeTable.getModel();
        
        // Update UI with user data
        String fullName = firstName + " " + lastName;
        String designationWithEid = designation + " -- " + eid;
        this.fullName.setText(fullName);
        this.designationWithEid.setText(designationWithEid);
        
        // Load employee data into the table
        adminRole.loadEmployeeData(tableModel);
    }
    
    // Constructor for simpler initialization
    public AdminDashboard(String firstName, String lastName, String designation, int eid) {
        initComponents();
        
        // Initialize AdminRole and tableModel
        adminRole = new AdminRole();
        tableModel = (DefaultTableModel) employeeTable.getModel();
        
        // Update UI with user data
        String fullName = firstName + " " + lastName;
        String designationWithEid = designation + " -- " + eid;
        this.fullName.setText(fullName);
        this.designationWithEid.setText(designationWithEid);
        
        // Load employee data into the table
        adminRole.loadEmployeeData(tableModel);
    }
    
    public AdminDashboard() {
        initComponents();
        
        // Initialize AdminRole and tableModel
        adminRole = new AdminRole();
        tableModel = (DefaultTableModel) employeeTable.getModel();
        
        // Load employee data into the table
        adminRole.loadEmployeeData(tableModel);
    }
    
    private void initializeDashboard(String username) {
        try {
            // Load employee data
            employeeDetails = EmployeeDetails.loadByUsername(username, connection);
            
            if (employeeDetails != null) {
                // Initialize dashboard components
                dashboardData = new DashboardData();
                dashboardData.processEmployeeData(employeeDetails);
                dashboardData.processAllowanceData(employeeDetails);
                System.out.println("Employee loaded: " + employeeDetails.getFirstName() + " " + 
                    employeeDetails.getLastName() + ", " + employeeDetails.getDesignation());
                System.out.println("Employee ID: " + employeeDetails.getEid());
                
                // Update UI with employee data
                updateUI();
            }
            else {
                JOptionPane.showMessageDialog(this, 
                    "Employee data not found for username: " + username, 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
            }
        }
        catch (Exception ex) {
            JOptionPane.showMessageDialog(this, 
                "Error initializing dashboard: " + ex.getMessage(), 
                "Error",
                JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }
    
    // Update the interface with specific details
    private void updateUI() {
        // Update basic information
        fullName.setText(employeeDetails.getFirstName() + " " + employeeDetails.getLastName());
        designationWithEid.setText(employeeDetails.getDesignation() + " -- " + employeeDetails.getEid());
    }
    
    private void setupTable() {
        // Delegate to AdminRole to setup the table with editable last row
        adminRole.setupEditableTable(employeeTable, tableModel);
        
        // Add an empty row at the bottom for adding new employees
        addNewEmptyRow();
        
        // Add table to scroll pane
        JScrollPane scrollPane = new JScrollPane(employeeTable);
        add(scrollPane, BorderLayout.CENTER);
    }
    
    private void addNewEmptyRow() {
        adminRole.addEmptyRowToTable(tableModel);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        NavigationPanel = new javax.swing.JPanel();
        dashboardLabel = new javax.swing.JLabel();
        payrollLabel = new javax.swing.JLabel();
        viewSettingsButton = new javax.swing.JLabel();
        dashboardPanel = new javax.swing.JPanel();
        dashboardPane = new javax.swing.JLabel();
        profilePanel = new javax.swing.JPanel();
        editProfileButton = new javax.swing.JButton();
        fullName = new javax.swing.JLabel();
        designationWithEid = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        viewSettingsBtn = new javax.swing.JButton();
        addButton = new javax.swing.JButton();
        viewPayslipBtn = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        employeeTable = new javax.swing.JTable();
        updateButton = new javax.swing.JButton();
        searchButton = new javax.swing.JButton();
        deleteButton = new javax.swing.JButton();
        sortButton = new javax.swing.JButton();
        jPanel10 = new javax.swing.JPanel();
        jLabel55 = new javax.swing.JLabel();
        jLabel56 = new javax.swing.JLabel();
        jLabel57 = new javax.swing.JLabel();
        jLabel58 = new javax.swing.JLabel();
        refreshButton = new javax.swing.JButton();
        saveButton = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("MotorPH Admin Dashboard");
        setMinimumSize(new java.awt.Dimension(1024, 720));

        NavigationPanel.setBackground(new java.awt.Color(255, 255, 255));
        NavigationPanel.setToolTipText("");

        dashboardLabel.setFont(new java.awt.Font("Cambria", 1, 18)); // NOI18N
        dashboardLabel.setForeground(new java.awt.Color(51, 51, 51));
        dashboardLabel.setText("Dashboard");
        dashboardLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                dashboardLabelMouseClicked(evt);
            }
        });

        payrollLabel.setFont(new java.awt.Font("Cambria", 0, 18)); // NOI18N
        payrollLabel.setForeground(new java.awt.Color(51, 51, 51));
        payrollLabel.setText("Payroll");
        payrollLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                payrollLabelMouseClicked(evt);
            }
        });

        viewSettingsButton.setFont(new java.awt.Font("Cambria", 0, 18)); // NOI18N
        viewSettingsButton.setForeground(new java.awt.Color(51, 51, 51));
        viewSettingsButton.setText("Settings");
        viewSettingsButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                viewSettingsButtonMouseClicked(evt);
            }
        });

        dashboardPanel.setBackground(new java.awt.Color(245, 245, 245));

        dashboardPane.setFont(new java.awt.Font("Cambria", 1, 14)); // NOI18N
        dashboardPane.setForeground(new java.awt.Color(37, 61, 144));
        dashboardPane.setText("Dashboard");

        profilePanel.setBackground(new java.awt.Color(0, 35, 102));

        editProfileButton.setFont(new java.awt.Font("Cambria", 0, 12)); // NOI18N
        editProfileButton.setText("Edit Profile");
        editProfileButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editProfileButtonActionPerformed(evt);
            }
        });

        fullName.setFont(new java.awt.Font("Cambria", 1, 18)); // NOI18N
        fullName.setForeground(new java.awt.Color(255, 255, 255));
        fullName.setText("fullName");

        designationWithEid.setFont(new java.awt.Font("Cambria", 1, 14)); // NOI18N
        designationWithEid.setForeground(new java.awt.Color(255, 255, 255));
        designationWithEid.setText("designationWithEid");

        jLabel1.setFont(new java.awt.Font("Cambria", 1, 12)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Resource Folder/blankephoto.png"))); // NOI18N

        javax.swing.GroupLayout profilePanelLayout = new javax.swing.GroupLayout(profilePanel);
        profilePanel.setLayout(profilePanelLayout);
        profilePanelLayout.setHorizontalGroup(
            profilePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(profilePanelLayout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addComponent(jLabel1)
                .addGap(50, 50, 50)
                .addGroup(profilePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(fullName)
                    .addComponent(designationWithEid))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(editProfileButton)
                .addGap(81, 81, 81))
        );
        profilePanelLayout.setVerticalGroup(
            profilePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(profilePanelLayout.createSequentialGroup()
                .addComponent(jLabel1)
                .addGap(0, 3, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, profilePanelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(profilePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, profilePanelLayout.createSequentialGroup()
                        .addComponent(editProfileButton)
                        .addGap(39, 39, 39))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, profilePanelLayout.createSequentialGroup()
                        .addComponent(fullName)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(designationWithEid)
                        .addGap(28, 28, 28))))
        );

        jLabel5.setFont(new java.awt.Font("Cambria Math", 1, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(0, 35, 102));
        jLabel5.setText("Quick Actions");

        viewSettingsBtn.setBackground(new java.awt.Color(0, 35, 102));
        viewSettingsBtn.setFont(new java.awt.Font("Cambria", 0, 12)); // NOI18N
        viewSettingsBtn.setForeground(new java.awt.Color(255, 255, 255));
        viewSettingsBtn.setText("Settings");
        viewSettingsBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                viewSettingsBtnActionPerformed(evt);
            }
        });

        addButton.setBackground(new java.awt.Color(0, 35, 102));
        addButton.setFont(new java.awt.Font("Cambria", 0, 12)); // NOI18N
        addButton.setForeground(new java.awt.Color(255, 255, 255));
        addButton.setText("Add");
        addButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addButtonActionPerformed(evt);
            }
        });

        viewPayslipBtn.setBackground(new java.awt.Color(0, 35, 102));
        viewPayslipBtn.setFont(new java.awt.Font("Cambria", 0, 12)); // NOI18N
        viewPayslipBtn.setForeground(new java.awt.Color(255, 255, 255));
        viewPayslipBtn.setText("View Payslip");
        viewPayslipBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                viewPayslipBtnActionPerformed(evt);
            }
        });

        employeeTable.setFont(new java.awt.Font("Cambria", 0, 12)); // NOI18N
        employeeTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "EID", "Full Name", "Birthday", "Address", "Phone_Number", "SSS #", "PhilHealth #", "TIN #", "Pag-ibig #", "Status", "Designation", "Gross Salary"
            }
        ));
        employeeTable.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(employeeTable);

        updateButton.setBackground(new java.awt.Color(0, 35, 102));
        updateButton.setFont(new java.awt.Font("Cambria", 0, 12)); // NOI18N
        updateButton.setForeground(new java.awt.Color(255, 255, 255));
        updateButton.setText("Update");
        updateButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                updateButtonActionPerformed(evt);
            }
        });

        searchButton.setBackground(new java.awt.Color(0, 35, 102));
        searchButton.setFont(new java.awt.Font("Cambria", 0, 12)); // NOI18N
        searchButton.setForeground(new java.awt.Color(255, 255, 255));
        searchButton.setText("Search");
        searchButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchButtonActionPerformed(evt);
            }
        });

        deleteButton.setBackground(new java.awt.Color(0, 35, 102));
        deleteButton.setFont(new java.awt.Font("Cambria", 0, 12)); // NOI18N
        deleteButton.setForeground(new java.awt.Color(255, 255, 255));
        deleteButton.setText("Delete");
        deleteButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteButtonActionPerformed(evt);
            }
        });

        sortButton.setBackground(new java.awt.Color(0, 35, 102));
        sortButton.setFont(new java.awt.Font("Cambria", 0, 12)); // NOI18N
        sortButton.setForeground(new java.awt.Color(255, 255, 255));
        sortButton.setText("Sort");
        sortButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sortButtonActionPerformed(evt);
            }
        });

        jPanel10.setBackground(new java.awt.Color(0, 0, 0));
        jPanel10.setForeground(new java.awt.Color(255, 255, 255));
        jPanel10.setToolTipText("");

        jLabel55.setFont(new java.awt.Font("Candara", 0, 12)); // NOI18N
        jLabel55.setForeground(new java.awt.Color(255, 255, 255));
        jLabel55.setText("Office Hours: 8:30am–5:30pm, Monday through Saturday");

        jLabel56.setFont(new java.awt.Font("Candara", 0, 12)); // NOI18N
        jLabel56.setForeground(new java.awt.Color(255, 255, 255));
        jLabel56.setText("Office Address: 7 Jupiter Avenue cor. F. Sandoval Jr., Bagong Nayon, Quezon City");

        jLabel57.setFont(new java.awt.Font("Candara", 0, 12)); // NOI18N
        jLabel57.setForeground(new java.awt.Color(255, 255, 255));
        jLabel57.setText("Phone: (028) 911-5071 / (028) 911-5072 / (028) 911-5073 ");

        jLabel58.setFont(new java.awt.Font("Candara", 0, 12)); // NOI18N
        jLabel58.setForeground(new java.awt.Color(255, 255, 255));
        jLabel58.setText("Email: corporate@motorph.com");

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addGap(310, 310, 310)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel56, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel10Layout.createSequentialGroup()
                                .addGap(58, 58, 58)
                                .addComponent(jLabel55))
                            .addGroup(jPanel10Layout.createSequentialGroup()
                                .addGap(56, 56, 56)
                                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel57)
                                    .addGroup(jPanel10Layout.createSequentialGroup()
                                        .addGap(61, 61, 61)
                                        .addComponent(jLabel58)))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 63, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel55)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel56)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel57)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel58, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        refreshButton.setBackground(new java.awt.Color(0, 35, 102));
        refreshButton.setFont(new java.awt.Font("Cambria", 0, 12)); // NOI18N
        refreshButton.setForeground(new java.awt.Color(255, 255, 255));
        refreshButton.setText("Refresh");
        refreshButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                refreshButtonActionPerformed(evt);
            }
        });

        saveButton.setBackground(new java.awt.Color(0, 35, 102));
        saveButton.setFont(new java.awt.Font("Cambria", 0, 12)); // NOI18N
        saveButton.setForeground(new java.awt.Color(255, 255, 255));
        saveButton.setText("Save");
        saveButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout dashboardPanelLayout = new javax.swing.GroupLayout(dashboardPanel);
        dashboardPanel.setLayout(dashboardPanelLayout);
        dashboardPanelLayout.setHorizontalGroup(
            dashboardPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(dashboardPanelLayout.createSequentialGroup()
                .addGroup(dashboardPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(dashboardPanelLayout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addGroup(dashboardPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(profilePanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(dashboardPanelLayout.createSequentialGroup()
                                .addGroup(dashboardPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel5)
                                    .addComponent(dashboardPane)
                                    .addGroup(dashboardPanelLayout.createSequentialGroup()
                                        .addComponent(addButton)
                                        .addGap(18, 18, 18)
                                        .addComponent(updateButton)
                                        .addGap(18, 18, 18)
                                        .addComponent(deleteButton)
                                        .addGap(18, 18, 18)
                                        .addComponent(searchButton)
                                        .addGap(18, 18, 18)
                                        .addComponent(sortButton)
                                        .addGap(18, 18, 18)
                                        .addComponent(refreshButton)
                                        .addGap(177, 177, 177)
                                        .addComponent(saveButton)
                                        .addGap(18, 18, 18)
                                        .addComponent(viewPayslipBtn)
                                        .addGap(18, 18, 18)
                                        .addComponent(viewSettingsBtn)))
                                .addGap(0, 0, Short.MAX_VALUE))))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, dashboardPanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane1)))
                .addContainerGap())
        );
        dashboardPanelLayout.setVerticalGroup(
            dashboardPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dashboardPanelLayout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(dashboardPane)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(profilePanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel5)
                .addGap(12, 12, 12)
                .addGroup(dashboardPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(viewSettingsBtn)
                    .addComponent(addButton)
                    .addComponent(viewPayslipBtn)
                    .addComponent(updateButton)
                    .addComponent(searchButton)
                    .addComponent(deleteButton)
                    .addComponent(sortButton)
                    .addComponent(refreshButton)
                    .addComponent(saveButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 257, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 12, Short.MAX_VALUE)
                .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Resource Folder/MotorPHLogo small.png"))); // NOI18N

        javax.swing.GroupLayout NavigationPanelLayout = new javax.swing.GroupLayout(NavigationPanel);
        NavigationPanel.setLayout(NavigationPanelLayout);
        NavigationPanelLayout.setHorizontalGroup(
            NavigationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(NavigationPanelLayout.createSequentialGroup()
                .addGap(59, 59, 59)
                .addComponent(jLabel2)
                .addGap(176, 176, 176)
                .addComponent(dashboardLabel)
                .addGap(75, 75, 75)
                .addComponent(payrollLabel)
                .addGap(68, 68, 68)
                .addComponent(viewSettingsButton)
                .addContainerGap(332, Short.MAX_VALUE))
            .addComponent(dashboardPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        NavigationPanelLayout.setVerticalGroup(
            NavigationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(NavigationPanelLayout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(NavigationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addGroup(NavigationPanelLayout.createSequentialGroup()
                        .addGap(31, 31, 31)
                        .addGroup(NavigationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(dashboardLabel)
                            .addComponent(payrollLabel)
                            .addComponent(viewSettingsButton))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(dashboardPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(NavigationPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(NavigationPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 20, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void dashboardLabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_dashboardLabelMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_dashboardLabelMouseClicked

    private void payrollLabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_payrollLabelMouseClicked
        new PayrollDashboard().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_payrollLabelMouseClicked

    private void viewSettingsButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_viewSettingsButtonMouseClicked
        new ViewSettings().setVisible(true);
    }//GEN-LAST:event_viewSettingsButtonMouseClicked

    private void viewSettingsBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_viewSettingsBtnActionPerformed
        new ViewSettings().setVisible(true);
    }//GEN-LAST:event_viewSettingsBtnActionPerformed

    private void viewPayslipBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_viewPayslipBtnActionPerformed
        new PayrollDashboard().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_viewPayslipBtnActionPerformed

    private void sortButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sortButtonActionPerformed
        adminRole.showSortDialog(this, tableModel);
    }//GEN-LAST:event_sortButtonActionPerformed

    private void refreshButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_refreshButtonActionPerformed
        adminRole.loadEmployeeData(tableModel);
    }//GEN-LAST:event_refreshButtonActionPerformed

    private void addButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addButtonActionPerformed
        addNewEmptyRow();
        employeeTable.scrollRectToVisible(employeeTable.getCellRect(employeeTable.getRowCount() - 1, 0, true));
        employeeTable.setRowSelectionInterval(employeeTable.getRowCount() - 1, employeeTable.getRowCount() - 1);
        employeeTable.editCellAt(employeeTable.getRowCount() - 1, 0);
        saveButton.setEnabled(true); // Enable the saveButton when an input was made
    }//GEN-LAST:event_addButtonActionPerformed

    private void updateButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_updateButtonActionPerformed
        int selectedRow = employeeTable.getSelectedRow();
        if (selectedRow == -1) {
        adminRole.showMessage("Please select an employee to update.", "Selection Required", false);
        return;
        }
        String eid = tableModel.getValueAt(selectedRow, 0).toString();
        adminRole.showUpdateDialog(this, eid, tableModel);
        saveButton.setEnabled(true); // Enable the saveButton when an update was made
    }//GEN-LAST:event_updateButtonActionPerformed

    private void deleteButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteButtonActionPerformed
        int selectedRow = employeeTable.getSelectedRow();
        if (selectedRow == -1) {
            adminRole.showMessage("Please select an employee to delete.", "Selection Required", false);
            return;
        }
        
        String eid = tableModel.getValueAt(selectedRow, 0).toString();
        adminRole.deleteEmployeeWithConfirmation(eid, tableModel);
    }//GEN-LAST:event_deleteButtonActionPerformed

    private void searchButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchButtonActionPerformed
        adminRole.showSearchDialog(this, tableModel);
    }//GEN-LAST:event_searchButtonActionPerformed

    private void saveButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveButtonActionPerformed
        adminRole.saveNewEmployeeFromTable(tableModel);
        adminRole.loadEmployeeData(tableModel);
        saveButton.setEnabled(false); // Disable the saveButton after saving
    }//GEN-LAST:event_saveButtonActionPerformed

    private void editProfileButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editProfileButtonActionPerformed
        new EditProfile().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_editProfileButtonActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(AdminDashboard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AdminDashboard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AdminDashboard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AdminDashboard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new AdminDashboard().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel NavigationPanel;
    private javax.swing.JButton addButton;
    private javax.swing.JLabel dashboardLabel;
    private javax.swing.JLabel dashboardPane;
    private javax.swing.JPanel dashboardPanel;
    private javax.swing.JButton deleteButton;
    private javax.swing.JLabel designationWithEid;
    private javax.swing.JButton editProfileButton;
    private javax.swing.JTable employeeTable;
    private javax.swing.JLabel fullName;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel55;
    private javax.swing.JLabel jLabel56;
    private javax.swing.JLabel jLabel57;
    private javax.swing.JLabel jLabel58;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel payrollLabel;
    private javax.swing.JPanel profilePanel;
    private javax.swing.JButton refreshButton;
    private javax.swing.JButton saveButton;
    private javax.swing.JButton searchButton;
    private javax.swing.JButton sortButton;
    private javax.swing.JButton updateButton;
    private javax.swing.JButton viewPayslipBtn;
    private javax.swing.JButton viewSettingsBtn;
    private javax.swing.JLabel viewSettingsButton;
    // End of variables declaration//GEN-END:variables
}
