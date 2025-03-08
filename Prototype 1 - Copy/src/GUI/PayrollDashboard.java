/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package GUI;


/**
 *
 * @author dashcodes and jaf 
 */
import DatabaseConnector.DatabaseConnection;
import BusinessLogic.Allowances;
import BusinessLogic.DashboardData;
import BusinessLogic.Deductions;
import BusinessLogic.EmployeeDetails;
import Controller.SalaryCalculator;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.util.Locale;
import javax.swing.JFrame;
import javax.swing.JOptionPane;


    public class PayrollDashboard extends javax.swing.JFrame {
    // Core Components
    private Connection connection;
    private String loggedInUsername;
    
    // Business Logic Components
    private EmployeeDetails employeeDetails;
    private Allowances allowances;
    private Deductions deductions;
    private DashboardData dashboardData;
    private SalaryCalculator salaryCalculator;
    
    // Number formatter for consistent currency display
    private static final NumberFormat CURRENCY_FORMATTER = NumberFormat.getInstance(Locale.US);
    
    static {
        CURRENCY_FORMATTER.setMinimumFractionDigits(2);
        CURRENCY_FORMATTER.setMaximumFractionDigits(2);
    }
    
    /**
     * Creates new form payrollDashboard
     */
    public PayrollDashboard() {
        initComponents();
    }

     /**
     * Constructor that accepts username to load user-specific data
     * @param username The username of the logged-in user
     * @param connection Database connection
     */
    public PayrollDashboard(String username, Connection connection) {
        initComponents();
        
        // Disables Fullscreen and always launch at the center of the screen
        setExtendedState(JFrame.NORMAL);
        setResizable(false);
        setLocationRelativeTo(null);
        
        // Stores the username
        this.loggedInUsername = username;
        
        try {
            // Gets connections from DB (singleton)
            this.connection = DatabaseConnection.getInstance().getConnection();
        
        
        // Load employee data and initialize dashboard
        initializeDashboard(username);
    } catch (SQLException ex) {
        JOptionPane.showMessageDialog(this, 
            "Error connecting to database: " + ex.getMessage(), 
            "Connection Error", 
            JOptionPane.ERROR_MESSAGE);
    }
}
     
    /**
     * Constructor that accepts basic user data
     * @param firstName Employee's first name
     * @param lastName Employee's last name
     * @param designation Employee's job title
     * @param eid Employee ID
     * @param connection Database connection
     */
    public PayrollDashboard(
        String firstName,
        String lastName,
        String designation,
        int eid,
        Connection connection) 
    {
        initComponents();
        
        // Stores database connection
        this.connection = connection;
        
        // Update UI with user data
        String fullName = firstName + " " + lastName;
        String designationWithEid = designation + " -- " + eid;
        
        // Set these values to UI components
        this.firstName.setText(fullName);
        this.designationWithEid.setText(designationWithEid);
    }
    
    /**
     * Constructor with comprehensive employee data
     * @param firstName Employee's first name
     * @param lastName Employee's last name
     * @param designation Employee's job title
     * @param eid Employee ID
     * @param basicPay Employee's basic pay
     * @param overTimePay Employee's overtime pay
     * @param holidayPay Employee's holiday pay
     * @param performanceBonus Employee's performance bonus
     * @param riceAllowance Employee's rice allowance
     * @param phoneAllowance Employee's phone allowance
     * @param clothingAllowance Employee's clothing allowance
     * @param pagIbigContribution Employee's PAG-IBIG contribution
     * @param philHealthContribution Employee's PhilHealth contribution
     * @param sssContribution Employee's SSS contribution
     * @param withholdingTax Employee's withholding tax
     * @param grossPay Employee's gross pay
     * @param totalAllowances Employee's total allowances
     * @param totalDeductions Employee's total deductions
     * @param netPay Employee's net pay
     * @param connection Database connection
     */
    public PayrollDashboard(
        // Basic Information
        String firstName,
        String lastName,
        String designation,
        int eid,
        
        // Gross Pay Values
        float basicPay,
        float overTimePay,
        float holidayPay,
        float performanceBonus,
        
        // Allowances Values
        float riceAllowance,
        float phoneAllowance,
        float clothingAllowance,
        
        // Deductions Values
        float pagIbigContribution,
        float philHealthContribution,
        float sssContribution,
        float withholdingTax,
        
        // Payroll Summary
        float grossPay,
        float totalAllowances,
        float totalDeductions,
        float netPay,
   
        // Connection
        Connection connection) 
    {
        initComponents();
        
        // Store the database connection
        this.connection = connection;
        
        // Update UI with user data
        String fullName = firstName + " " + lastName;
        String designationWithEid = designation + " -- " + eid;
        
        // Set basic employee information
        this.firstName.setText(fullName);
        this.designationWithEid.setText(designationWithEid);
        
        // Update all UI elements with formatted values
        updateFinancialUI(
                
            // Gross Pay values
            basicPay, 
            overTimePay, 
            holidayPay, 
            performanceBonus,
            
            // Allowances Values
            riceAllowance, 
            phoneAllowance, 
            clothingAllowance,
            
            // Deductions Values
            sssContribution, 
            philHealthContribution, 
            pagIbigContribution, 
            withholdingTax,
            
            // Payroll Summary Values
            grossPay, 
            totalAllowances, 
            totalDeductions, 
            netPay
        );
    }
    
     /**
     * Update the financial UI components with formatted values
     * 
     * @param basicPay Employee's basic pay
     * @param overTimePay Employee's overtime pay
     * @param holidayPay Employee's holiday pay
     * @param performanceBonus Employee's performance bonus
     * @param riceAllowance Employee's rice allowance
     * @param phoneAllowance Employee's phone allowance
     * @param clothingAllowance Employee's clothing allowance
     * @param sssContribution Employee's SSS contribution
     * @param philHealthContribution Employee's PhilHealth contribution
     * @param pagIbigContribution Employee's Pag-IBIG contribution
     * @param withholdingTax Employee's withholding tax
     * @param grossPay Employee's gross pay
     * @param totalAllowances Employee's total allowances
     * @param totalDeductions Employee's total deductions
     * @param netPay Employee's net pay
     */
    private void updateFinancialUI(
        float basicPay, 
        float overTimePay, 
        float holidayPay, 
        float performanceBonus,
        float riceAllowance, 
        float phoneAllowance, 
        float clothingAllowance,
        float sssContribution, 
        float philHealthContribution, 
        float pagIbigContribution, 
        float withholdingTax,
        float grossPay, 
        float totalAllowances, 
        float totalDeductions, 
        float netPay
    ) 
    
    {
        /// Assigned jLabels + formatted values
        
        // Gross Pay Section
        this.basicPayAmount1.setText(formatCurrency(basicPay));
        this.overTimePayAmount1.setText(formatCurrency(overTimePay));
        this.holidayPayAmount1.setText(formatCurrency(holidayPay));
        this.performancePayAmount1.setText(formatCurrency(performanceBonus));
        
        // Allowances Section
        this.riceAllowance.setText(formatCurrency(riceAllowance));
        this.phoneAllowance.setText(formatCurrency(phoneAllowance));
        this.clothingAllowance.setText(formatCurrency(clothingAllowance));
        
        // Deductions Section
        this.sssContribution.setText(formatCurrency(sssContribution));
        this.philHealthContribution.setText(formatCurrency(philHealthContribution));
        this.pagIbigContribution.setText(formatCurrency(pagIbigContribution));
        this.withholdingTax.setText(formatCurrency(withholdingTax));
        
        // Payroll Summary
        this.grossPay.setText(formatCurrency(grossPay));
        this.totalAllowances.setText(formatCurrency(totalAllowances));
        this.totalDeductions.setText(formatCurrency(totalDeductions));
        this.netPayAmount.setText(formatCurrency(netPay));
    }
    
     /**
     * Format a floating-point value as currency with "₱" prefix
     * 
     * @param value The value to format
     * @return Formatted currency string
     */
    private String formatCurrency(float value) {
        return "₱" + CURRENCY_FORMATTER.format(value);
    }
    
    /**
     * Initialize the dashboard with user data
     * 
     * @param username The username of the logged-in user
     */
    private void initializeDashboard(String username) {
        try {
            // Load employee data
            employeeDetails = EmployeeDetails.loadByUsername(username, connection);
            
            if (employeeDetails != null) {
                
                // Initializes salary calculator
                salaryCalculator = new SalaryCalculator(connection);
		salaryCalculator.performAllCalculations(employeeDetails);
                
                System.out.println("Employee loaded: " + employeeDetails.getFirstName() + " " + 
                    employeeDetails.getLastName() + ", " + employeeDetails.getDesignation());
                System.out.println("Employee ID: " + employeeDetails.getEid());
                
                // Initializes allowances object
                allowances = new Allowances(employeeDetails.getEid());
                allowances.setRiceAllowance(parseFloatSafely(employeeDetails.getRiceSubsidy()));
                allowances.setPhoneAllowance(parseFloatSafely(employeeDetails.getPhoneAllowance()));
                allowances.setClothingAllowance(parseFloatSafely(employeeDetails.getClothingAllowance()));
                
                // Initializes deductions object
                deductions = new Deductions(employeeDetails.getEid());
            	deductions.setSssContribution(salaryCalculator.getDeduction("sss"));
            	deductions.setPhilHealthContribution(salaryCalculator.getDeduction("philhealth"));
            	deductions.setPagIbigContribution(salaryCalculator.getDeduction("pagibig"));
            	deductions.setWithholdingTax(salaryCalculator.getDeduction("tax"));
            	deductions.setTotalDeductions(salaryCalculator.getTotalDeductions());
                
                // Initializes salary calculator to perform all calculations
                salaryCalculator.performAllCalculations (employeeDetails);
                
                // Initializes dashboard components
                dashboardData = new DashboardData();
                dashboardData.processEmployeeData(employeeDetails);
                dashboardData.processAllowanceData(employeeDetails);
                dashboardData.processDeductionData(employeeDetails, salaryCalculator);
                dashboardData.processSalaryData(employeeDetails, salaryCalculator);
                
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
     * Updates the interface with employee details from the business logic components
     */
    private void updateUI() {
        try {
            // Update basic information
            firstName.setText(employeeDetails.getFirstName() + " " + employeeDetails.getLastName());
            designationWithEid.setText(employeeDetails.getDesignation() + " -- " + employeeDetails.getEid());
            
            // Extract all values needed for the UI
            float basicPay = employeeDetails.getBasicSalary();
            float overTimePay = employeeDetails.getOverTimePay(); // added na EmoployeeDetails need nalang implement sa sql tables
            float holidayPay = employeeDetails.getHolidayPay(); // added na sa EmoployeeDetails
            float performanceBonus = employeeDetails.getPerformanceBonus(); //added na sa EmoployeeDetails
            
            String riceAllowance = employeeDetails.getRiceSubsidy();  
            float phoneAllowance = allowances.getPhoneAllowance();
            float clothingAllowance = allowances.getClothingAllowance();
            
            float sssContribution = salaryCalculator.getDeduction("sss");
            float philHealthContribution = salaryCalculator.getDeduction("philhealth");
            float pagIbigContribution = salaryCalculator.getDeduction("pagibig");
            float withholdingTax = salaryCalculator.getDeduction("tax");
            
            float grossPay = salaryCalculator.getGrossPay(); // implemented pero need pa other values
            float totalAllowances = salaryCalculator.calculateTotalAllowances(allowances); 
            float totalDeductions = salaryCalculator.getTotalDeductions();
            float netPay = salaryCalculator.getNetPay();
            
            // Uses DashboardData to display data in the UI
            updateFinancialUI(
            employeeDetails.getBasicSalary(),
            employeeDetails.getOverTimePay(),
            employeeDetails.getHolidayPay(),
            employeeDetails.getPerformanceBonus(),
            
            allowances.getRiceAllowance(),
            allowances.getPhoneAllowance(),
            allowances.getClothingAllowance(),
        
            sssContribution, 
            philHealthContribution,
            pagIbigContribution,
            withholdingTax,
        
            salaryCalculator.getGrossPay(), 
            salaryCalculator.getTotalAllowances(), 
            salaryCalculator.getTotalDeductions(),
            salaryCalculator.getNetPay()
        );
    } catch (Exception ex) {
        JOptionPane.showMessageDialog(this, 
            "Error updating UI: " + ex.getMessage(), 
            "Error",
            JOptionPane.ERROR_MESSAGE);
        ex.printStackTrace();
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

        jPanel1 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        NavigationPanel = new javax.swing.JPanel();
        dashboardLabel = new javax.swing.JLabel();
        payrollLabel = new javax.swing.JLabel();
        settingsLabel = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        dashboardPanel = new javax.swing.JPanel();
        dashboardPane = new javax.swing.JLabel();
        profilePanel = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        editProfileButton = new javax.swing.JButton();
        firstName = new javax.swing.JLabel();
        designationWithEid = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        homeButton = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        riceAllowance = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        phoneAllowance = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        clothingAllowance = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        sssContribution = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        philHealthContribution = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        pagIbigContribution = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        withholdingTax = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        totalAllowances = new javax.swing.JLabel();
        totalDeductions = new javax.swing.JLabel();
        netPayAmount = new javax.swing.JLabel();
        jLabel37 = new javax.swing.JLabel();
        grossPay = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();
        jLabel33 = new javax.swing.JLabel();
        basicPayAmount1 = new javax.swing.JLabel();
        jLabel34 = new javax.swing.JLabel();
        overTimePayAmount1 = new javax.swing.JLabel();
        jLabel35 = new javax.swing.JLabel();
        holidayPayAmount1 = new javax.swing.JLabel();
        jLabel36 = new javax.swing.JLabel();
        performancePayAmount1 = new javax.swing.JLabel();
        payrollCycle = new javax.swing.JLabel();
        printButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

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
                .addGap(326, 326, 326))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        NavigationPanel.setBackground(new java.awt.Color(255, 255, 255));

        dashboardLabel.setFont(new java.awt.Font("Cambria", 0, 18)); // NOI18N
        dashboardLabel.setForeground(new java.awt.Color(51, 51, 51));
        dashboardLabel.setText("Dashboard");

        payrollLabel.setFont(new java.awt.Font("Cambria", 1, 18)); // NOI18N
        payrollLabel.setForeground(new java.awt.Color(51, 51, 51));
        payrollLabel.setText("Payroll");

        settingsLabel.setFont(new java.awt.Font("Cambria", 0, 18)); // NOI18N
        settingsLabel.setForeground(new java.awt.Color(51, 51, 51));
        settingsLabel.setText("Settings");
        settingsLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                settingsLabelMouseClicked(evt);
            }
        });

        jLabel17.setFont(new java.awt.Font("Cambria", 1, 12)); // NOI18N
        jLabel17.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Resource Folder/MotorPHLogo small.png"))); // NOI18N

        dashboardPanel.setBackground(new java.awt.Color(245, 245, 245));

        dashboardPane.setFont(new java.awt.Font("Cambria", 1, 14)); // NOI18N
        dashboardPane.setForeground(new java.awt.Color(37, 61, 144));
        dashboardPane.setText("Payroll");

        profilePanel.setBackground(new java.awt.Color(0, 35, 102));

        jLabel1.setFont(new java.awt.Font("Cambria", 1, 12)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Resource Folder/blankephoto.png"))); // NOI18N

        editProfileButton.setFont(new java.awt.Font("Cambria", 0, 12)); // NOI18N
        editProfileButton.setForeground(java.awt.Color.gray);
        editProfileButton.setText("Edit Profile");
        editProfileButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editProfileButtonActionPerformed(evt);
            }
        });

        firstName.setFont(new java.awt.Font("Cambria", 1, 18)); // NOI18N
        firstName.setForeground(new java.awt.Color(255, 255, 255));
        firstName.setText("fullName");

        designationWithEid.setFont(new java.awt.Font("Cambria", 1, 14)); // NOI18N
        designationWithEid.setForeground(new java.awt.Color(255, 255, 255));
        designationWithEid.setText("designationWithEid");

        javax.swing.GroupLayout profilePanelLayout = new javax.swing.GroupLayout(profilePanel);
        profilePanel.setLayout(profilePanelLayout);
        profilePanelLayout.setHorizontalGroup(
            profilePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(profilePanelLayout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addComponent(jLabel1)
                .addGap(55, 55, 55)
                .addGroup(profilePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(firstName)
                    .addComponent(designationWithEid))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 581, Short.MAX_VALUE)
                .addComponent(editProfileButton)
                .addGap(83, 83, 83))
        );
        profilePanelLayout.setVerticalGroup(
            profilePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(profilePanelLayout.createSequentialGroup()
                .addGroup(profilePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(profilePanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel1))
                    .addGroup(profilePanelLayout.createSequentialGroup()
                        .addGap(26, 26, 26)
                        .addComponent(firstName)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(designationWithEid))
                    .addGroup(profilePanelLayout.createSequentialGroup()
                        .addGap(42, 42, 42)
                        .addComponent(editProfileButton)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        firstName.getAccessibleContext().setAccessibleDescription("");

        jLabel5.setFont(new java.awt.Font("Cambria Math", 1, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(0, 35, 102));
        jLabel5.setText("Quick Actions");

        homeButton.setBackground(new java.awt.Color(0, 35, 102));
        homeButton.setFont(new java.awt.Font("Cambria", 0, 12)); // NOI18N
        homeButton.setForeground(new java.awt.Color(255, 255, 255));
        homeButton.setText("Home");
        homeButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                homeButtonActionPerformed(evt);
            }
        });

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        jLabel10.setFont(new java.awt.Font("Cambria", 1, 12)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(37, 61, 144));
        jLabel10.setText("Allowances");

        jLabel14.setFont(new java.awt.Font("Cambria", 1, 12)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(37, 61, 144));
        jLabel14.setText("Deductions");

        jLabel23.setFont(new java.awt.Font("Cambria", 0, 12)); // NOI18N
        jLabel23.setForeground(new java.awt.Color(143, 143, 143));
        jLabel23.setText("Rice Subsidy:");

        riceAllowance.setFont(new java.awt.Font("Cambria", 0, 12)); // NOI18N
        riceAllowance.setForeground(new java.awt.Color(143, 143, 143));
        riceAllowance.setText("0.00");

        jLabel24.setFont(new java.awt.Font("Cambria", 1, 12)); // NOI18N
        jLabel24.setText("Amount");

        jLabel25.setFont(new java.awt.Font("Cambria", 0, 12)); // NOI18N
        jLabel25.setForeground(new java.awt.Color(143, 143, 143));
        jLabel25.setText("Phone Allowance:");

        phoneAllowance.setFont(new java.awt.Font("Cambria", 0, 12)); // NOI18N
        phoneAllowance.setForeground(new java.awt.Color(143, 143, 143));
        phoneAllowance.setText("0.00");

        jLabel26.setFont(new java.awt.Font("Cambria", 0, 12)); // NOI18N
        jLabel26.setForeground(new java.awt.Color(143, 143, 143));
        jLabel26.setText("Clothing Allowance:");

        clothingAllowance.setFont(new java.awt.Font("Cambria", 0, 12)); // NOI18N
        clothingAllowance.setForeground(new java.awt.Color(143, 143, 143));
        clothingAllowance.setText("0.00");

        jLabel28.setFont(new java.awt.Font("Cambria", 0, 12)); // NOI18N
        jLabel28.setForeground(new java.awt.Color(143, 143, 143));
        jLabel28.setText("SSS Contribution:");

        sssContribution.setFont(new java.awt.Font("Cambria", 0, 12)); // NOI18N
        sssContribution.setForeground(new java.awt.Color(143, 143, 143));
        sssContribution.setText("0.00");

        jLabel29.setFont(new java.awt.Font("Cambria", 0, 12)); // NOI18N
        jLabel29.setForeground(new java.awt.Color(143, 143, 143));
        jLabel29.setText("Philhealth:");

        philHealthContribution.setFont(new java.awt.Font("Cambria", 0, 12)); // NOI18N
        philHealthContribution.setForeground(new java.awt.Color(143, 143, 143));
        philHealthContribution.setText("0.00");

        jLabel30.setFont(new java.awt.Font("Cambria", 0, 12)); // NOI18N
        jLabel30.setForeground(new java.awt.Color(143, 143, 143));
        jLabel30.setText("Pag-Ibig:");

        pagIbigContribution.setFont(new java.awt.Font("Cambria", 0, 12)); // NOI18N
        pagIbigContribution.setForeground(new java.awt.Color(143, 143, 143));
        pagIbigContribution.setText("0.00");

        jLabel31.setFont(new java.awt.Font("Cambria", 0, 12)); // NOI18N
        jLabel31.setForeground(new java.awt.Color(143, 143, 143));
        jLabel31.setText("Withholding Tax:");

        withholdingTax.setFont(new java.awt.Font("Cambria", 0, 12)); // NOI18N
        withholdingTax.setForeground(new java.awt.Color(143, 143, 143));
        withholdingTax.setText("0.00");

        jLabel27.setFont(new java.awt.Font("Cambria", 1, 12)); // NOI18N
        jLabel27.setText("Amount");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel31)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(withholdingTax))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel23)
                                    .addComponent(jLabel25)
                                    .addComponent(jLabel28)
                                    .addComponent(jLabel29)
                                    .addComponent(jLabel30))
                                .addGap(59, 59, 59)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(riceAllowance)
                                    .addComponent(clothingAllowance)
                                    .addComponent(phoneAllowance)
                                    .addComponent(sssContribution)
                                    .addComponent(philHealthContribution)
                                    .addComponent(pagIbigContribution))))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel10)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel24))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel26)
                                    .addComponent(jLabel14))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 41, Short.MAX_VALUE)
                                .addComponent(jLabel27)))
                        .addGap(40, 40, 40))))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel10, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel24))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(riceAllowance)
                    .addComponent(jLabel23, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel25)
                    .addComponent(phoneAllowance))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel26)
                    .addComponent(clothingAllowance))
                .addGap(21, 21, 21)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(jLabel27))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(sssContribution)
                    .addComponent(jLabel28))
                .addGap(13, 13, 13)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel29)
                    .addComponent(philHealthContribution))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 13, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel30)
                    .addComponent(pagIbigContribution))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel31)
                    .addComponent(withholdingTax))
                .addGap(18, 18, 18))
        );

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));

        jLabel11.setFont(new java.awt.Font("Cambria", 1, 12)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(37, 61, 144));
        jLabel11.setText("Summary");

        jLabel18.setFont(new java.awt.Font("Cambria", 1, 12)); // NOI18N
        jLabel18.setText("Amount");

        jLabel20.setFont(new java.awt.Font("Cambria", 0, 12)); // NOI18N
        jLabel20.setForeground(new java.awt.Color(143, 143, 143));
        jLabel20.setText("Allowances:");

        jLabel21.setFont(new java.awt.Font("Cambria", 0, 12)); // NOI18N
        jLabel21.setForeground(new java.awt.Color(143, 143, 143));
        jLabel21.setText("Deductions:");

        jLabel22.setFont(new java.awt.Font("Cambria", 1, 12)); // NOI18N
        jLabel22.setText("Net Pay:");

        totalAllowances.setFont(new java.awt.Font("Cambria", 0, 12)); // NOI18N
        totalAllowances.setForeground(new java.awt.Color(143, 143, 143));
        totalAllowances.setText("0.00");

        totalDeductions.setFont(new java.awt.Font("Cambria", 0, 12)); // NOI18N
        totalDeductions.setForeground(new java.awt.Color(143, 143, 143));
        totalDeductions.setText("0.00");

        netPayAmount.setFont(new java.awt.Font("Cambria", 1, 12)); // NOI18N
        netPayAmount.setText("0.00");

        jLabel37.setFont(new java.awt.Font("Cambria", 0, 12)); // NOI18N
        jLabel37.setForeground(new java.awt.Color(143, 143, 143));
        jLabel37.setText("Gross Pay:");

        grossPay.setFont(new java.awt.Font("Cambria", 0, 12)); // NOI18N
        grossPay.setForeground(new java.awt.Color(143, 143, 143));
        grossPay.setText("0.00");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(55, 55, 55)
                        .addComponent(jLabel11))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(54, 54, 54)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel21)
                            .addComponent(jLabel20)
                            .addComponent(jLabel37)
                            .addComponent(jLabel22))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 97, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel18)
                        .addGap(48, 48, 48))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(netPayAmount)
                            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(totalAllowances)
                                .addComponent(grossPay)
                                .addComponent(totalDeductions)))
                        .addGap(56, 56, 56))))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel18)
                        .addGap(18, 18, 18)
                        .addComponent(grossPay)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(totalAllowances)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(totalDeductions)
                        .addGap(18, 18, 18)
                        .addComponent(netPayAmount)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel11)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel37)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel20)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel21)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel22)
                        .addGap(41, 41, 41))))
        );

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));

        jLabel13.setFont(new java.awt.Font("Cambria", 1, 12)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(37, 61, 144));
        jLabel13.setText("Gross Pay");

        jLabel32.setFont(new java.awt.Font("Cambria", 1, 12)); // NOI18N
        jLabel32.setText("Amount");

        jLabel33.setFont(new java.awt.Font("Cambria", 0, 12)); // NOI18N
        jLabel33.setForeground(new java.awt.Color(143, 143, 143));
        jLabel33.setText("Basic Pay:");

        basicPayAmount1.setFont(new java.awt.Font("Cambria", 0, 12)); // NOI18N
        basicPayAmount1.setForeground(new java.awt.Color(143, 143, 143));
        basicPayAmount1.setText("0.00");

        jLabel34.setFont(new java.awt.Font("Cambria", 0, 12)); // NOI18N
        jLabel34.setForeground(new java.awt.Color(143, 143, 143));
        jLabel34.setText("Over time Pay:");

        overTimePayAmount1.setFont(new java.awt.Font("Cambria", 0, 12)); // NOI18N
        overTimePayAmount1.setForeground(new java.awt.Color(143, 143, 143));
        overTimePayAmount1.setText("0.00");

        jLabel35.setFont(new java.awt.Font("Cambria", 0, 12)); // NOI18N
        jLabel35.setForeground(new java.awt.Color(143, 143, 143));
        jLabel35.setText("Holiday Pay:");

        holidayPayAmount1.setFont(new java.awt.Font("Cambria", 0, 12)); // NOI18N
        holidayPayAmount1.setForeground(new java.awt.Color(143, 143, 143));
        holidayPayAmount1.setText("0.00");

        jLabel36.setFont(new java.awt.Font("Cambria", 0, 12)); // NOI18N
        jLabel36.setForeground(new java.awt.Color(143, 143, 143));
        jLabel36.setText("Performance Bonus:");

        performancePayAmount1.setFont(new java.awt.Font("Cambria", 0, 12)); // NOI18N
        performancePayAmount1.setForeground(new java.awt.Color(143, 143, 143));
        performancePayAmount1.setText("0.00");

        payrollCycle.setFont(new java.awt.Font("Cambria", 0, 12)); // NOI18N
        payrollCycle.setForeground(new java.awt.Color(143, 143, 143));
        payrollCycle.setText("Payroll Cycle");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel34)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addGap(2, 2, 2)
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel5Layout.createSequentialGroup()
                                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(overTimePayAmount1)
                                            .addGroup(jPanel5Layout.createSequentialGroup()
                                                .addComponent(jLabel33)
                                                .addGap(157, 157, 157)
                                                .addComponent(basicPayAmount1)))
                                        .addGap(0, 9, Short.MAX_VALUE))
                                    .addGroup(jPanel5Layout.createSequentialGroup()
                                        .addComponent(jLabel13)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jLabel32))))
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(jPanel5Layout.createSequentialGroup()
                                        .addComponent(jLabel36)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(performancePayAmount1))
                                    .addGroup(jPanel5Layout.createSequentialGroup()
                                        .addComponent(jLabel35)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(holidayPayAmount1)))
                                .addGap(9, 9, 9)))
                        .addGap(52, 52, 52))))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(payrollCycle)
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(43, 43, 43)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel32)
                    .addComponent(jLabel13))
                .addGap(18, 18, 18)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(basicPayAmount1)
                    .addComponent(jLabel33))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel34)
                    .addComponent(overTimePayAmount1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(holidayPayAmount1, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel35))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel36)
                    .addComponent(performancePayAmount1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 29, Short.MAX_VALUE)
                .addComponent(payrollCycle)
                .addContainerGap())
        );

        printButton.setBackground(new java.awt.Color(0, 35, 102));
        printButton.setFont(new java.awt.Font("Cambria", 0, 12)); // NOI18N
        printButton.setForeground(new java.awt.Color(255, 255, 255));
        printButton.setText("Print");
        printButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                printButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout dashboardPanelLayout = new javax.swing.GroupLayout(dashboardPanel);
        dashboardPanel.setLayout(dashboardPanelLayout);
        dashboardPanelLayout.setHorizontalGroup(
            dashboardPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dashboardPanelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(dashboardPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(dashboardPane)
                    .addComponent(profilePanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(dashboardPanelLayout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addGroup(dashboardPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5)
                            .addGroup(dashboardPanelLayout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addGroup(dashboardPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(dashboardPanelLayout.createSequentialGroup()
                                        .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(homeButton)))))
                    .addGroup(dashboardPanelLayout.createSequentialGroup()
                        .addGap(316, 316, 316)
                        .addComponent(printButton))))
        );
        dashboardPanelLayout.setVerticalGroup(
            dashboardPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dashboardPanelLayout.createSequentialGroup()
                .addContainerGap(22, Short.MAX_VALUE)
                .addComponent(dashboardPane)
                .addGap(18, 18, 18)
                .addComponent(profilePanel, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(dashboardPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(dashboardPanelLayout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addGap(11, 11, 11)
                        .addGroup(dashboardPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(homeButton)
                            .addComponent(printButton))
                        .addGroup(dashboardPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(dashboardPanelLayout.createSequentialGroup()
                                .addGap(46, 46, 46)
                                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(dashboardPanelLayout.createSequentialGroup()
                                .addGap(43, 43, 43)
                                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(39, 39, 39))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, dashboardPanelLayout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())))
        );

        javax.swing.GroupLayout NavigationPanelLayout = new javax.swing.GroupLayout(NavigationPanel);
        NavigationPanel.setLayout(NavigationPanelLayout);
        NavigationPanelLayout.setHorizontalGroup(
            NavigationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(NavigationPanelLayout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addComponent(jLabel17)
                .addGap(186, 186, 186)
                .addComponent(dashboardLabel)
                .addGap(75, 75, 75)
                .addComponent(payrollLabel)
                .addGap(76, 76, 76)
                .addComponent(settingsLabel)
                .addContainerGap(405, Short.MAX_VALUE))
            .addGroup(NavigationPanelLayout.createSequentialGroup()
                .addComponent(dashboardPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        NavigationPanelLayout.setVerticalGroup(
            NavigationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, NavigationPanelLayout.createSequentialGroup()
                .addGroup(NavigationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(NavigationPanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel17))
                    .addGroup(NavigationPanelLayout.createSequentialGroup()
                        .addGap(33, 33, 33)
                        .addGroup(NavigationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(dashboardLabel)
                            .addComponent(payrollLabel)
                            .addComponent(settingsLabel))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(dashboardPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(NavigationPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(NavigationPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void homeButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_homeButtonActionPerformed
        try {
        if (this.loggedInUsername == null || this.loggedInUsername.isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "User session not found. Please log in again.", 
                "Session Error", 
                JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Opens EmployeeDashboard and passes the username and connection
        new EmployeeDashboard (this.loggedInUsername, this.connection).setVisible(true);
        this.dispose();
    } catch (Exception ex) {
        JOptionPane.showMessageDialog(this, 
            "Error opening EmployeeDashboard: " + ex.getMessage(), 
            "Error", 
            JOptionPane.ERROR_MESSAGE);}
    }//GEN-LAST:event_homeButtonActionPerformed

    private void printButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_printButtonActionPerformed
                try {
        if (this.loggedInUsername == null || this.loggedInUsername.isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "User session not found. Please log in again.", 
                "Session Error", 
                JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Opens PaySlip and passes the username and connection
        new PaySlip (this.loggedInUsername, this.connection).setVisible(true);
        this.dispose();
    } catch (Exception ex) {
        JOptionPane.showMessageDialog(this, 
            "Error opening PaySlip: " + ex.getMessage(), 
            "Error", 
            JOptionPane.ERROR_MESSAGE);}
    }//GEN-LAST:event_printButtonActionPerformed

    private void settingsLabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_settingsLabelMouseClicked
        new ViewSettings().setVisible(true);
    }//GEN-LAST:event_settingsLabelMouseClicked

    private void editProfileButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editProfileButtonActionPerformed
        try {
        if (this.loggedInUsername == null || this.loggedInUsername.isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "User session not found. Please log in again.", 
                "Session Error", 
                JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Opens PaySlip and passes the username and connection
        new ViewProfile (this.loggedInUsername, this.connection).setVisible(true);
        this.dispose();
    } catch (Exception ex) {
        JOptionPane.showMessageDialog(this, 
            "Error opening EditProfile: " + ex.getMessage(), 
            "Error", 
            JOptionPane.ERROR_MESSAGE);}
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
            java.util.logging.Logger.getLogger(PayrollDashboard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(PayrollDashboard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(PayrollDashboard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(PayrollDashboard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new PayrollDashboard().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel NavigationPanel;
    private javax.swing.JLabel basicPayAmount1;
    private javax.swing.JLabel clothingAllowance;
    private javax.swing.JLabel dashboardLabel;
    private javax.swing.JLabel dashboardPane;
    private javax.swing.JPanel dashboardPanel;
    private javax.swing.JLabel designationWithEid;
    private javax.swing.JButton editProfileButton;
    private javax.swing.JLabel firstName;
    private javax.swing.JLabel grossPay;
    private javax.swing.JLabel holidayPayAmount1;
    private javax.swing.JButton homeButton;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JLabel netPayAmount;
    private javax.swing.JLabel overTimePayAmount1;
    private javax.swing.JLabel pagIbigContribution;
    private javax.swing.JLabel payrollCycle;
    private javax.swing.JLabel payrollLabel;
    private javax.swing.JLabel performancePayAmount1;
    private javax.swing.JLabel philHealthContribution;
    private javax.swing.JLabel phoneAllowance;
    private javax.swing.JButton printButton;
    private javax.swing.JPanel profilePanel;
    private javax.swing.JLabel riceAllowance;
    private javax.swing.JLabel settingsLabel;
    private javax.swing.JLabel sssContribution;
    private javax.swing.JLabel totalAllowances;
    private javax.swing.JLabel totalDeductions;
    private javax.swing.JLabel withholdingTax;
    // End of variables declaration//GEN-END:variables
}
