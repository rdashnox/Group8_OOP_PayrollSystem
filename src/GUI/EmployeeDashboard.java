/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package GUI;

/**
 *
 * @author dashcodes
 */

import BusinessLogic.DashboardData;
import BusinessLogic.EmployeeDetails;
import Controller.RoleAuthenticator;
import Controller.SalaryCalculator;
import Controller.TimeManager;
import java.sql.Connection;
import java.text.NumberFormat;
import java.util.Locale;
import javax.swing.JOptionPane;

public class EmployeeDashboard extends javax.swing.JFrame {
        // Core components
        private TimeManager timeManager;
        private RoleAuthenticator roleAuthenticator;
        private Connection connection;
        private String loggedInUsername;
       
        
        // Business logic components
        private EmployeeDetails employeeDetails;
        private DashboardData dashboardData;
        private SalaryCalculator salaryCalculator;
        
        //Default constructor
    public EmployeeDashboard() {
        initComponents();

        // Initialize time manager for real-time date and time display
        timeManager = new TimeManager(dateTodayLabel, timeNowLabel);
        timeManager.startClock();
    }

   /**
     * Constructor that accepts username to load user-specific data
     * @param username The username of the logged-in user
     * @param connection Database connection
     */
    public EmployeeDashboard(String username, Connection connection) {
        initComponents();
        
        // Initialize time manager for real-time date and time display
        timeManager = new TimeManager(dateTodayLabel, timeNowLabel);
        timeManager.startClock();
        
        // Store the database connection and username
        this.connection = connection;
        this.loggedInUsername = username;
        
        // Load employee data and initialize dashboard
        initializeDashboard(username);
    }

    // Constructor that accepts user data and financial data
    public EmployeeDashboard
        (
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

        // Combine firstName and lastName into fullName
        String fullName = firstName + " " + lastName;

        // Combine designation and eid into a single string
        String designationWithEid = designation + " -- " + eid;

        // Update UI with user data
        this.fullName.setText(fullName); // Display fullName in the firstName label
        this.designationWithEid.setText(designationWithEid); // Display designation with EID

        // Format financial data with commas as thousand separators
        NumberFormat formatter = NumberFormat.getInstance(Locale.US);
        formatter.setMinimumFractionDigits(2); // Ensure 2 decimal places
        formatter.setMaximumFractionDigits(2);

        String formattedBasicPay = formatter.format(basicPay);
        String formattedTotalAllowances = formatter.format(totalAllowances);
        String formattedTotalDeductions = formatter.format(totalDeductions);
        String formattedNetPay = formatter.format(netPay);

        // Update UI with formatted financial data
        this.basicPayLabel.setText("₱" + formattedBasicPay);
        this.totalAllowancesLabel.setText("₱" + formattedTotalAllowances);
        this.totalDeductionsLabel.setText("₱" + formattedTotalDeductions);
        this.netPayLabel.setText("₱" + formattedNetPay);

        // Initialize time manager and other components
        timeManager = new TimeManager(dateTodayLabel, timeNowLabel);
        timeManager.startClock();
        }

    /**
     * Initialize dashboard with employee data
     * @param username The username of the logged-in user
     */
        
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
                
                // Initialize salary calculator
                salaryCalculator = new SalaryCalculator();
                salaryCalculator.calculateDeductions(employeeDetails);
                salaryCalculator.calculateNetPay(employeeDetails);
                               
            }
            else
                {
                // No employee found with this username
                JOptionPane.showMessageDialog(this, 
                        "Employee data not found for username: " + username, 
                        "Error", 
                        JOptionPane.ERROR_MESSAGE);
                }
            }
            catch (Exception ex)
                {
            JOptionPane.showMessageDialog(this, 
                "Error initializing dashboard: " + ex.getMessage(), 
                "Error",
                JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
                }
            }
    
    // Update the interface with specific details
    private void updateUI()
    {
        // Update basic information
        fullName.setText(dashboardData.getDisplayValue("firstName"));
        fullName.setText(dashboardData.getDisplayValue("lastName"));
        designationWithEid.setText(dashboardData.getDisplayValue("designation"));
    }
    
    /**
     * Safely parses a string to float, returning 0 if parsing fails
     */
    private float parseFloatSafely(String value) {
        if (value == null || value.trim().isEmpty()) {
            return 0;
        }
        
        try {
            return Float.parseFloat(value);
        } catch (NumberFormatException e) {
            return 0;
        }
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
        settingsLabel = new javax.swing.JLabel();
        dashboardPanel = new javax.swing.JPanel();
        dashboardPane = new javax.swing.JLabel();
        profilePanel = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        fullName = new javax.swing.JLabel();
        designationWithEid = new javax.swing.JLabel();
        editProfileButton = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        viewPayslipButton = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        timeInLog = new javax.swing.JLabel();
        dateToday1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        logOutTime = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        hoursWorked = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        overtimeHours = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        totalHoursWorked = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        basicPay = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        deductionsAmount = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        basicPayLabel = new javax.swing.JLabel();
        totalAllowancesLabel = new javax.swing.JLabel();
        totalDeductionsLabel = new javax.swing.JLabel();
        netPayLabel = new javax.swing.JLabel();
        cycleLabel = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        annualProgress = new javax.swing.JProgressBar();
        compensationProgress = new javax.swing.JProgressBar();
        jLabel12 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        sickProgress = new javax.swing.JProgressBar();
        annualLeaveRemaining = new javax.swing.JLabel();
        sickLeaveRemaining = new javax.swing.JLabel();
        compensationLeaveRemaining = new javax.swing.JLabel();
        dateTodayLabel = new javax.swing.JLabel();
        timeNowLabel = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("MotorPH Employee Dashboard");

        NavigationPanel.setBackground(new java.awt.Color(255, 255, 255));

        dashboardLabel.setFont(new java.awt.Font("Cambria", 1, 18)); // NOI18N
        dashboardLabel.setForeground(new java.awt.Color(51, 51, 51));
        dashboardLabel.setText("Dashboard");

        payrollLabel.setFont(new java.awt.Font("Cambria", 0, 18)); // NOI18N
        payrollLabel.setForeground(new java.awt.Color(51, 51, 51));
        payrollLabel.setText("Payroll");
        payrollLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                payrollLabelMouseClicked(evt);
            }
        });

        settingsLabel.setFont(new java.awt.Font("Cambria", 0, 18)); // NOI18N
        settingsLabel.setForeground(new java.awt.Color(51, 51, 51));
        settingsLabel.setText("Settings");
        settingsLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                settingsLabelMouseClicked(evt);
            }
        });

        dashboardPanel.setBackground(new java.awt.Color(245, 245, 245));

        dashboardPane.setFont(new java.awt.Font("Cambria", 1, 14)); // NOI18N
        dashboardPane.setForeground(new java.awt.Color(37, 61, 144));
        dashboardPane.setText("Dashboard");

        profilePanel.setBackground(new java.awt.Color(0, 35, 102));

        jLabel1.setFont(new java.awt.Font("Cambria", 1, 12)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Resource Folder/blankephoto.png"))); // NOI18N

        fullName.setFont(new java.awt.Font("Cambria", 1, 18)); // NOI18N
        fullName.setForeground(new java.awt.Color(255, 255, 255));
        fullName.setText("fullName");

        designationWithEid.setFont(new java.awt.Font("Cambria", 1, 14)); // NOI18N
        designationWithEid.setForeground(new java.awt.Color(255, 255, 255));
        designationWithEid.setText("designationWithEid");

        editProfileButton.setFont(new java.awt.Font("Cambria", 0, 12)); // NOI18N
        editProfileButton.setText("Edit Profile");
        editProfileButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                editProfileButtonMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout profilePanelLayout = new javax.swing.GroupLayout(profilePanel);
        profilePanel.setLayout(profilePanelLayout);
        profilePanelLayout.setHorizontalGroup(
            profilePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(profilePanelLayout.createSequentialGroup()
                .addGap(39, 39, 39)
                .addComponent(jLabel1)
                .addGap(99, 99, 99)
                .addGroup(profilePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(profilePanelLayout.createSequentialGroup()
                        .addComponent(fullName)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 46, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(designationWithEid))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 555, Short.MAX_VALUE)
                .addComponent(editProfileButton)
                .addGap(86, 86, 86))
        );
        profilePanelLayout.setVerticalGroup(
            profilePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(profilePanelLayout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(fullName)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(designationWithEid)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, profilePanelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(profilePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, profilePanelLayout.createSequentialGroup()
                        .addComponent(editProfileButton)
                        .addGap(91, 91, 91))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, profilePanelLayout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(39, 39, 39))))
        );

        jLabel5.setFont(new java.awt.Font("Cambria Math", 1, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(0, 35, 102));
        jLabel5.setText("Quick Actions");

        viewPayslipButton.setBackground(new java.awt.Color(0, 35, 102));
        viewPayslipButton.setFont(new java.awt.Font("Cambria", 0, 12)); // NOI18N
        viewPayslipButton.setForeground(new java.awt.Color(255, 255, 255));
        viewPayslipButton.setText("View Payslip");
        viewPayslipButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                viewPayslipButtonActionPerformed(evt);
            }
        });

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        jLabel10.setFont(new java.awt.Font("Cambria", 1, 12)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(37, 61, 144));
        jLabel10.setText("Attendance");

        timeInLog.setFont(new java.awt.Font("Cambria", 0, 12)); // NOI18N
        timeInLog.setForeground(new java.awt.Color(143, 143, 143));
        timeInLog.setText("08:00 AM");

        dateToday1.setFont(new java.awt.Font("Cambria", 0, 12)); // NOI18N
        dateToday1.setForeground(new java.awt.Color(143, 143, 143));
        dateToday1.setText("Log In Time:");

        jLabel3.setFont(new java.awt.Font("Cambria", 0, 12)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(143, 143, 143));
        jLabel3.setText("Log Out Time:");

        logOutTime.setFont(new java.awt.Font("Cambria", 0, 12)); // NOI18N
        logOutTime.setForeground(new java.awt.Color(143, 143, 143));
        logOutTime.setText("0.00");

        jLabel4.setFont(new java.awt.Font("Cambria", 0, 12)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(143, 143, 143));
        jLabel4.setText("Hours Worked:");

        hoursWorked.setFont(new java.awt.Font("Cambria", 0, 12)); // NOI18N
        hoursWorked.setForeground(new java.awt.Color(143, 143, 143));
        hoursWorked.setText("0.00");

        jLabel14.setFont(new java.awt.Font("Cambria", 0, 12)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(143, 143, 143));
        jLabel14.setText("Over Time:");

        overtimeHours.setFont(new java.awt.Font("Cambria", 0, 12)); // NOI18N
        overtimeHours.setForeground(new java.awt.Color(143, 143, 143));
        overtimeHours.setText("0.00");

        jLabel23.setFont(new java.awt.Font("Cambria", 1, 12)); // NOI18N
        jLabel23.setForeground(new java.awt.Color(143, 143, 143));
        jLabel23.setText("Total Hours:");

        totalHoursWorked.setFont(new java.awt.Font("Cambria", 1, 12)); // NOI18N
        totalHoursWorked.setForeground(new java.awt.Color(143, 143, 143));
        totalHoursWorked.setText("0.00");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel10)
                            .addComponent(dateToday1))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 129, Short.MAX_VALUE)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(logOutTime)
                            .addComponent(timeInLog)
                            .addComponent(hoursWorked)
                            .addComponent(overtimeHours)
                            .addComponent(totalHoursWorked))
                        .addGap(84, 84, 84))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel23)
                            .addComponent(jLabel14)
                            .addComponent(jLabel4))
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(jLabel10)
                .addGap(57, 57, 57)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(dateToday1)
                    .addComponent(timeInLog))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(logOutTime))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(hoursWorked))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(overtimeHours))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel23)
                    .addComponent(totalHoursWorked))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));

        jLabel11.setFont(new java.awt.Font("Cambria", 1, 12)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(37, 61, 144));
        jLabel11.setText("Pay slip Quickview");

        jLabel18.setFont(new java.awt.Font("Cambria", 0, 12)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(143, 143, 143));
        jLabel18.setText("Amount");

        basicPay.setFont(new java.awt.Font("Cambria", 0, 12)); // NOI18N
        basicPay.setForeground(new java.awt.Color(143, 143, 143));
        basicPay.setText("Basic Pay:");

        jLabel20.setFont(new java.awt.Font("Cambria", 0, 12)); // NOI18N
        jLabel20.setForeground(new java.awt.Color(143, 143, 143));
        jLabel20.setText("Allowances:");

        deductionsAmount.setFont(new java.awt.Font("Cambria", 0, 12)); // NOI18N
        deductionsAmount.setForeground(new java.awt.Color(143, 143, 143));
        deductionsAmount.setText("Deductions:");

        jLabel22.setFont(new java.awt.Font("Cambria", 1, 12)); // NOI18N
        jLabel22.setForeground(new java.awt.Color(143, 143, 143));
        jLabel22.setText("Net Pay:");

        basicPayLabel.setFont(new java.awt.Font("Cambria", 0, 12)); // NOI18N
        basicPayLabel.setForeground(new java.awt.Color(143, 143, 143));
        basicPayLabel.setText("0.00");

        totalAllowancesLabel.setFont(new java.awt.Font("Cambria", 0, 12)); // NOI18N
        totalAllowancesLabel.setForeground(new java.awt.Color(143, 143, 143));
        totalAllowancesLabel.setText("0.00");

        totalDeductionsLabel.setFont(new java.awt.Font("Cambria", 0, 12)); // NOI18N
        totalDeductionsLabel.setForeground(new java.awt.Color(143, 143, 143));
        totalDeductionsLabel.setText("0.00");

        netPayLabel.setFont(new java.awt.Font("Cambria", 1, 12)); // NOI18N
        netPayLabel.setForeground(new java.awt.Color(143, 143, 143));
        netPayLabel.setText("0.00");

        cycleLabel.setFont(new java.awt.Font("Cambria", 0, 12)); // NOI18N
        cycleLabel.setForeground(new java.awt.Color(143, 143, 143));
        cycleLabel.setText("March 2025");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel11)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel22)
                            .addComponent(deductionsAmount)
                            .addComponent(jLabel20)
                            .addComponent(basicPay))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 126, Short.MAX_VALUE)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(basicPayLabel)
                            .addComponent(jLabel18)
                            .addComponent(totalAllowancesLabel)
                            .addComponent(totalDeductionsLabel)
                            .addComponent(netPayLabel))
                        .addGap(87, 87, 87))))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(cycleLabel)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(jLabel11)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 40, Short.MAX_VALUE)
                .addComponent(jLabel18)
                .addGap(30, 30, 30)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(basicPay)
                    .addComponent(basicPayLabel))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel20)
                    .addComponent(totalAllowancesLabel))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(deductionsAmount)
                    .addComponent(totalDeductionsLabel))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel22)
                    .addComponent(netPayLabel))
                .addGap(17, 17, 17)
                .addComponent(cycleLabel)
                .addContainerGap())
        );

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));

        jLabel13.setFont(new java.awt.Font("Cambria", 1, 12)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(37, 61, 144));
        jLabel13.setText("Leave Credits");

        annualProgress.setFont(new java.awt.Font("Cambria", 0, 12)); // NOI18N

        compensationProgress.setFont(new java.awt.Font("Cambria", 0, 12)); // NOI18N

        jLabel12.setFont(new java.awt.Font("Cambria", 0, 12)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(10, 4, 4));
        jLabel12.setText("Annual Leave");

        jLabel15.setFont(new java.awt.Font("Cambria", 0, 12)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(10, 4, 4));
        jLabel15.setText("Sick Leave");

        jLabel16.setFont(new java.awt.Font("Cambria", 0, 12)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(10, 4, 4));
        jLabel16.setText("Compensation Leave");

        sickProgress.setFont(new java.awt.Font("Cambria", 0, 12)); // NOI18N

        annualLeaveRemaining.setFont(new java.awt.Font("Cambria", 0, 12)); // NOI18N
        annualLeaveRemaining.setForeground(new java.awt.Color(10, 4, 4));
        annualLeaveRemaining.setText("10");

        sickLeaveRemaining.setFont(new java.awt.Font("Cambria", 0, 12)); // NOI18N
        sickLeaveRemaining.setForeground(new java.awt.Color(10, 4, 4));
        sickLeaveRemaining.setText("8");

        compensationLeaveRemaining.setFont(new java.awt.Font("Cambria", 0, 12)); // NOI18N
        compensationLeaveRemaining.setForeground(new java.awt.Color(10, 4, 4));
        compensationLeaveRemaining.setText("10");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel13)
                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(sickProgress, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 265, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addComponent(jLabel15)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(sickLeaveRemaining))
                            .addComponent(compensationProgress, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 265, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                                .addComponent(jLabel16)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(compensationLeaveRemaining, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addComponent(jLabel12)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(annualLeaveRemaining, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(annualProgress, javax.swing.GroupLayout.PREFERRED_SIZE, 265, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(27, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(jLabel13)
                .addGap(35, 35, 35)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 13, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(annualLeaveRemaining, javax.swing.GroupLayout.PREFERRED_SIZE, 13, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(annualProgress, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(34, 34, 34)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 13, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(sickLeaveRemaining, javax.swing.GroupLayout.PREFERRED_SIZE, 13, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(sickProgress, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(compensationLeaveRemaining, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 13, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel16, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 13, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(compensationProgress, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(17, 17, 17))
        );

        dateTodayLabel.setFont(new java.awt.Font("Cambria", 0, 14)); // NOI18N
        dateTodayLabel.setForeground(new java.awt.Color(143, 143, 143));
        dateTodayLabel.setText("Date Today");

        timeNowLabel.setFont(new java.awt.Font("Cambria", 0, 14)); // NOI18N
        timeNowLabel.setForeground(new java.awt.Color(143, 143, 143));
        timeNowLabel.setText("Time Now");

        javax.swing.GroupLayout dashboardPanelLayout = new javax.swing.GroupLayout(dashboardPanel);
        dashboardPanel.setLayout(dashboardPanelLayout);
        dashboardPanelLayout.setHorizontalGroup(
            dashboardPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dashboardPanelLayout.createSequentialGroup()
                .addGroup(dashboardPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(dashboardPanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(dashboardPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(dashboardPanelLayout.createSequentialGroup()
                                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(dashboardPanelLayout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addComponent(jLabel5))))
                    .addGroup(dashboardPanelLayout.createSequentialGroup()
                        .addGap(32, 32, 32)
                        .addComponent(viewPayslipButton)))
                .addGap(18, 18, 18)
                .addGroup(dashboardPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(dashboardPanelLayout.createSequentialGroup()
                        .addComponent(dateTodayLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(timeNowLabel)
                        .addGap(132, 132, 132))
                    .addGroup(dashboardPanelLayout.createSequentialGroup()
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
            .addGroup(dashboardPanelLayout.createSequentialGroup()
                .addContainerGap(17, Short.MAX_VALUE)
                .addGroup(dashboardPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(dashboardPane)
                    .addComponent(profilePanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18))
        );
        dashboardPanelLayout.setVerticalGroup(
            dashboardPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dashboardPanelLayout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(dashboardPane)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(profilePanel, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(dashboardPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(dashboardPanelLayout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addGroup(dashboardPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(dateTodayLabel)
                            .addComponent(timeNowLabel))
                        .addGap(34, 34, 34))
                    .addGroup(dashboardPanelLayout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(viewPayslipButton)
                        .addGap(18, 18, 18)))
                .addGroup(dashboardPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Resource Folder/MotorPHLogo small.png"))); // NOI18N

        jPanel1.setBackground(new java.awt.Color(0, 0, 0));
        jPanel1.setForeground(new java.awt.Color(255, 255, 255));

        jLabel6.setFont(new java.awt.Font("Candara", 0, 12)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Office Hours: 8:30am–5:30pm, Monday through Saturday");

        jLabel7.setFont(new java.awt.Font("Candara", 0, 12)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("Office Address: 7 Jupiter Avenue cor. F. Sandoval Jr., Bagong Nayon, Quezon City");

        jLabel8.setFont(new java.awt.Font("Candara", 0, 12)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("Phone: (028) 911-5071 / (028) 911-5072 / (028) 911-5073 ");

        jLabel9.setFont(new java.awt.Font("Candara", 0, 12)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("Email: corporate@motorph.com");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(58, 58, 58)
                                .addComponent(jLabel6))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(56, 56, 56)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel8)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGap(61, 61, 61)
                                        .addComponent(jLabel9)))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 63, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(306, 306, 306))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout NavigationPanelLayout = new javax.swing.GroupLayout(NavigationPanel);
        NavigationPanel.setLayout(NavigationPanelLayout);
        NavigationPanelLayout.setHorizontalGroup(
            NavigationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(NavigationPanelLayout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addComponent(jLabel2)
                .addGap(180, 180, 180)
                .addComponent(dashboardLabel)
                .addGap(75, 75, 75)
                .addComponent(payrollLabel)
                .addGap(78, 78, 78)
                .addComponent(settingsLabel)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(NavigationPanelLayout.createSequentialGroup()
                .addComponent(dashboardPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        NavigationPanelLayout.setVerticalGroup(
            NavigationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(NavigationPanelLayout.createSequentialGroup()
                .addGroup(NavigationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(NavigationPanelLayout.createSequentialGroup()
                        .addGap(47, 47, 47)
                        .addGroup(NavigationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(dashboardLabel)
                            .addComponent(payrollLabel)
                            .addComponent(settingsLabel))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, NavigationPanelLayout.createSequentialGroup()
                        .addContainerGap(11, Short.MAX_VALUE)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)))
                .addComponent(dashboardPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(NavigationPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(NavigationPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void viewPayslipButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_viewPayslipButtonActionPerformed
            new PaySlip().setVisible(true);
    }//GEN-LAST:event_viewPayslipButtonActionPerformed

    private void payrollLabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_payrollLabelMouseClicked
            new PayrollDashboard().setVisible(true);
            this.dispose();
    }//GEN-LAST:event_payrollLabelMouseClicked

    private void editProfileButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_editProfileButtonMouseClicked
            new EditProfile().setVisible(true);
            this.dispose();
    }//GEN-LAST:event_editProfileButtonMouseClicked

    private void settingsLabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_settingsLabelMouseClicked
            new ViewSettings().setVisible(true);
            this.dispose();
    }//GEN-LAST:event_settingsLabelMouseClicked

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
            java.util.logging.Logger.getLogger(EmployeeDashboard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(EmployeeDashboard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(EmployeeDashboard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(EmployeeDashboard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new EmployeeDashboard().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel NavigationPanel;
    private javax.swing.JLabel annualLeaveRemaining;
    private javax.swing.JProgressBar annualProgress;
    private javax.swing.JLabel basicPay;
    private javax.swing.JLabel basicPayLabel;
    private javax.swing.JLabel compensationLeaveRemaining;
    private javax.swing.JProgressBar compensationProgress;
    private javax.swing.JLabel cycleLabel;
    private javax.swing.JLabel dashboardLabel;
    private javax.swing.JLabel dashboardPane;
    private javax.swing.JPanel dashboardPanel;
    private javax.swing.JLabel dateToday1;
    private javax.swing.JLabel dateTodayLabel;
    private javax.swing.JLabel deductionsAmount;
    private javax.swing.JLabel designationWithEid;
    private javax.swing.JButton editProfileButton;
    private javax.swing.JLabel fullName;
    private javax.swing.JLabel hoursWorked;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JLabel logOutTime;
    private javax.swing.JLabel netPayLabel;
    private javax.swing.JLabel overtimeHours;
    private javax.swing.JLabel payrollLabel;
    private javax.swing.JPanel profilePanel;
    private javax.swing.JLabel settingsLabel;
    private javax.swing.JLabel sickLeaveRemaining;
    private javax.swing.JProgressBar sickProgress;
    private javax.swing.JLabel timeInLog;
    private javax.swing.JLabel timeNowLabel;
    private javax.swing.JLabel totalAllowancesLabel;
    private javax.swing.JLabel totalDeductionsLabel;
    private javax.swing.JLabel totalHoursWorked;
    private javax.swing.JButton viewPayslipButton;
    // End of variables declaration//GEN-END:variables
}
