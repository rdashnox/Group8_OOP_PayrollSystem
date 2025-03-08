/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package GUI;

/**
 *
 * @author dashcodes
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

public class PaySlip extends javax.swing.JFrame {
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
     * Creates new form PaySlip
     */
    public PaySlip() {
        initComponents();
        
        // Disables Fullscreen and always launch at the center of the screen
        setExtendedState(JFrame.NORMAL);
        setResizable(false);
        setLocationRelativeTo(null);
    }
    
     /**
     * Constructor that accepts username to load user-specific data
     * @param username The username of the logged-in user
     * @param connection Database connection
     */
    public PaySlip(String username, Connection connection) {
        initComponents();
        
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
    public PaySlip(
        String firstName,
        String lastName,
        String designation,
        int eid,
        Connection connection) 
    {
        initComponents();
        
        // Stores database connection
        this.connection = connection;
        
        // Set values to UI components
        this.firstNameLabel.setText(firstName);
        this.lastNameLabel.setText(lastName);
        this.designationLabel.setText(designation);
        this.eidLabel.setText(String.valueOf(eid));
    }

    /**
     * Constructor with comprehensive employee data
     * @param firstName Employee's first name
     * @param lastName Employee's last name
     * @param designation Employee's job title
     * @param eid Employee ID
     * @param basicPay Employee's basic pay
     * @param hourlyRate Employee's Hourly Rate
     * @param daysWorked Number of Days Employee worked
     * @param overTimePay Employee's overtime pay
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
    
    public PaySlip(
        // Basic Information
        String firstName,
        String lastName,
        String designation,
        int eid,
        
        // Earnings Section Values
        float basicPay, // Monthly Rate
        float hourlyRate,
        float daysWorked,
        float overTimePay,
        
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
        float totalAllowances, //I'll assume na Benefits to
        float totalDeductions,
        float netPay,
   
        // Connection
        Connection connection) 
    {
        initComponents();
        
        // Store the database connection
        this.connection = connection;
        
        // Set basic employee information
        this.firstNameLabel.setText(firstName);
        this.lastNameLabel.setText(lastName);
        this.designationLabel.setText(designation);
        this.eidLabel.setText(String.valueOf(eid));
        
        
        // Update all UI elements with formatted values
        updateFinancialUI(
                
            // Earnings values
            basicPay, 
            hourlyRate, 
            daysWorked, 
            overTimePay,
            
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
     * @param pagIbigContribution Employee's PAG-IBIG contribution
     * @param withholdingTax Employee's withholding tax
     * @param grossPay Employee's gross pay
     * @param totalAllowances Employee's total allowances
     * @param totalDeductions Employee's total deductions
     * @param netPay Employee's net pay
     */
    private void updateFinancialUI(
        float basicPay, 
        float overTimePay, 
        float hourlyRate,
        float daysWorked, //assumed ko na float to since gagamitin sa calculation
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
        
        // Earnings Section
        this.basicPayAmount1.setText(formatCurrency(basicPay)); //Monthly rate
        this.hourlyRateAmount1.setText(formatCurrency(hourlyRate));
        this.daysWorkedLabel.setText(String.valueOf(daysWorked));
        this.overTimePayAmount1.setText(formatCurrency(overTimePay));
        
        
        // Benefits Section
        this.riceAllowance.setText(formatCurrency(riceAllowance));
        this.phoneAllowance.setText(formatCurrency(phoneAllowance));
        this.clothingAllowance.setText(formatCurrency(clothingAllowance));
        // pa add nalang here incentives if itutuloy pa bro
        
        // Deductions Section
        this.sssContribution.setText(formatCurrency(sssContribution));
        this.philHealthContribution.setText(formatCurrency(philHealthContribution));
        this.pagIbigContribution.setText(formatCurrency(pagIbigContribution));
        this.withholdingTax.setText(formatCurrency(withholdingTax)); //not yet implemented yung calculation nito sa Salary Calculator
        
        // Payroll Summary
        this.grossPay.setText(formatCurrency(grossPay)); //kasama dito yung values sa Payroll dashboard please update yung salary calculator if needed
        this.totalAllowances.setText(formatCurrency(totalAllowances)); //Incentives in the GUI we can just change this name if needed
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
            this.firstNameLabel.setText(employeeDetails.getFirstName());
            this.lastNameLabel.setText(employeeDetails.getLastName());
            this.designationLabel.setText(employeeDetails.getDesignation());
            this.eidLabel.setText(String.valueOf(employeeDetails.getEid()));
            
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
            
            float grossPay = salaryCalculator.getGrossPay(); // implemented pero need pa values
            float totalAllowances = salaryCalculator.calculateTotalAllowances(allowances); 
            float totalDeductions = salaryCalculator.getTotalDeductions();
            float netPay = salaryCalculator.getNetPay();
            
            // Uses DashboardData 
            updateFinancialUI(
                    
                employeeDetails.getBasicSalary(),
                employeeDetails.getHourlyRate(),
                employeeDetails.getDaysWorked(),
                employeeDetails.getOverTimePay(),
                
                
                allowances.getRiceAllowance(),
                allowances.getPhoneAllowance(),
                allowances.getClothingAllowance(),
            
                sssContribution, 
                philHealthContribution,
                pagIbigContribution,
                withholdingTax,
            
                salaryCalculator.getGrossPay(), // implemented pero need pa values
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

        NavigationPanel = new javax.swing.JPanel();
        jLabel17 = new javax.swing.JLabel();
        dashboardPanel = new javax.swing.JPanel();
        dashboardPane = new javax.swing.JLabel();
        profilePanel = new javax.swing.JPanel();
        firstNameLabel = new javax.swing.JLabel();
        lastNameLabel = new javax.swing.JLabel();
        designationLabel = new javax.swing.JLabel();
        payPeriodLabel = new javax.swing.JLabel();
        eidLabel = new javax.swing.JLabel();
        payrollCycle = new javax.swing.JLabel();
        backButton = new javax.swing.JButton();
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
        jLabel27 = new javax.swing.JLabel();
        incentives = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        sssContribution = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        philHealthContribution = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        pagIbigContribution = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        withholdingTax = new javax.swing.JLabel();
        jLabel38 = new javax.swing.JLabel();
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
        hourlyRateAmount1 = new javax.swing.JLabel();
        jLabel35 = new javax.swing.JLabel();
        daysWorkedLabel = new javax.swing.JLabel();
        jLabel36 = new javax.swing.JLabel();
        overTimePayAmount1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        NavigationPanel.setBackground(new java.awt.Color(255, 255, 255));

        jLabel17.setFont(new java.awt.Font("Cambria", 1, 12)); // NOI18N
        jLabel17.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Resource Folder/MotorPHLogo small.png"))); // NOI18N

        dashboardPanel.setBackground(new java.awt.Color(245, 245, 245));

        dashboardPane.setFont(new java.awt.Font("Cambria", 1, 14)); // NOI18N
        dashboardPane.setForeground(new java.awt.Color(37, 61, 144));
        dashboardPane.setText("Employee Pay Slip");

        profilePanel.setBackground(new java.awt.Color(0, 35, 102));

        firstNameLabel.setFont(new java.awt.Font("Cambria", 1, 18)); // NOI18N
        firstNameLabel.setForeground(new java.awt.Color(255, 255, 255));
        firstNameLabel.setText("firstName");

        lastNameLabel.setFont(new java.awt.Font("Cambria", 1, 18)); // NOI18N
        lastNameLabel.setForeground(new java.awt.Color(255, 255, 255));
        lastNameLabel.setText("lastName");

        designationLabel.setFont(new java.awt.Font("Cambria", 1, 14)); // NOI18N
        designationLabel.setForeground(new java.awt.Color(255, 255, 255));
        designationLabel.setText("position");

        payPeriodLabel.setFont(new java.awt.Font("Cambria", 1, 18)); // NOI18N
        payPeriodLabel.setForeground(new java.awt.Color(255, 255, 255));
        payPeriodLabel.setText("March 2025");

        eidLabel.setFont(new java.awt.Font("Cambria", 1, 14)); // NOI18N
        eidLabel.setForeground(new java.awt.Color(255, 255, 255));
        eidLabel.setText("eid");

        payrollCycle.setFont(new java.awt.Font("Cambria", 0, 10)); // NOI18N
        payrollCycle.setForeground(new java.awt.Color(255, 255, 255));
        payrollCycle.setText("Payroll Cycle");

        backButton.setFont(new java.awt.Font("Cambria", 1, 12)); // NOI18N
        backButton.setForeground(new java.awt.Color(37, 61, 144));
        backButton.setText("Back");
        backButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                backButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout profilePanelLayout = new javax.swing.GroupLayout(profilePanel);
        profilePanel.setLayout(profilePanelLayout);
        profilePanelLayout.setHorizontalGroup(
            profilePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(profilePanelLayout.createSequentialGroup()
                .addGap(88, 88, 88)
                .addGroup(profilePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(profilePanelLayout.createSequentialGroup()
                        .addComponent(firstNameLabel)
                        .addGap(18, 18, 18)
                        .addComponent(lastNameLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(payrollCycle)
                        .addGap(52, 52, 52))
                    .addGroup(profilePanelLayout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(designationLabel)
                        .addGap(56, 56, 56)
                        .addComponent(eidLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(backButton)
                        .addGap(44, 44, 44))))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, profilePanelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(payPeriodLabel)
                .addGap(34, 34, 34))
        );
        profilePanelLayout.setVerticalGroup(
            profilePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, profilePanelLayout.createSequentialGroup()
                .addContainerGap(8, Short.MAX_VALUE)
                .addComponent(payPeriodLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(profilePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(payrollCycle)
                    .addGroup(profilePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(firstNameLabel)
                        .addComponent(lastNameLabel)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(profilePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(designationLabel)
                    .addComponent(eidLabel)
                    .addComponent(backButton))
                .addGap(22, 22, 22))
        );

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        jLabel10.setFont(new java.awt.Font("Cambria", 1, 12)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(37, 61, 144));
        jLabel10.setText("Benefits");

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

        jLabel27.setFont(new java.awt.Font("Cambria", 0, 12)); // NOI18N
        jLabel27.setForeground(new java.awt.Color(143, 143, 143));
        jLabel27.setText("Incentives:");

        incentives.setFont(new java.awt.Font("Cambria", 0, 12)); // NOI18N
        incentives.setForeground(new java.awt.Color(143, 143, 143));
        incentives.setText("0.00");

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

        jLabel38.setFont(new java.awt.Font("Cambria", 1, 12)); // NOI18N
        jLabel38.setText("Amount");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel26)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(clothingAllowance))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel25)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(phoneAllowance))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel23)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(riceAllowance))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel27)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(incentives)))
                .addGap(55, 55, 55))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel10)
                    .addComponent(jLabel14)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(17, 17, 17)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel31)
                            .addComponent(jLabel28)
                            .addComponent(jLabel29)
                            .addComponent(jLabel30))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel24)
                    .addComponent(jLabel38)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(sssContribution)
                            .addComponent(pagIbigContribution)
                            .addComponent(withholdingTax)
                            .addComponent(philHealthContribution))))
                .addGap(46, 46, 46))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(jLabel24))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel23)
                    .addComponent(riceAllowance))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel25)
                    .addComponent(phoneAllowance))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel26)
                    .addComponent(clothingAllowance))
                .addGap(10, 10, 10)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel27)
                    .addComponent(incentives))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel38)
                    .addComponent(jLabel14))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(sssContribution)
                    .addComponent(jLabel28))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(philHealthContribution)
                    .addComponent(jLabel29))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pagIbigContribution)
                    .addComponent(jLabel30))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(withholdingTax)
                    .addComponent(jLabel31))
                .addContainerGap(20, Short.MAX_VALUE))
        );

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));

        jLabel11.setFont(new java.awt.Font("Cambria", 1, 12)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(37, 61, 144));
        jLabel11.setText("Summary");

        jLabel18.setFont(new java.awt.Font("Cambria", 0, 12)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(143, 143, 143));
        jLabel18.setText("Amount");

        jLabel20.setFont(new java.awt.Font("Cambria", 0, 12)); // NOI18N
        jLabel20.setForeground(new java.awt.Color(143, 143, 143));
        jLabel20.setText("Benefits:");

        jLabel21.setFont(new java.awt.Font("Cambria", 0, 12)); // NOI18N
        jLabel21.setForeground(new java.awt.Color(143, 143, 143));
        jLabel21.setText("Deductions:");

        jLabel22.setFont(new java.awt.Font("Cambria", 1, 14)); // NOI18N
        jLabel22.setText("Net Pay:");

        totalAllowances.setFont(new java.awt.Font("Cambria", 0, 12)); // NOI18N
        totalAllowances.setForeground(new java.awt.Color(143, 143, 143));
        totalAllowances.setText("0.00");

        totalDeductions.setFont(new java.awt.Font("Cambria", 0, 12)); // NOI18N
        totalDeductions.setForeground(new java.awt.Color(143, 143, 143));
        totalDeductions.setText("0.00");

        netPayAmount.setFont(new java.awt.Font("Cambria", 1, 14)); // NOI18N
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
                .addGap(24, 24, 24)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel20, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel21, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel37, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel22, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel11, javax.swing.GroupLayout.Alignment.LEADING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 149, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel18)
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(grossPay)
                        .addComponent(netPayAmount)
                        .addComponent(totalAllowances)
                        .addComponent(totalDeductions)))
                .addGap(67, 67, 67))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap(26, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel18)
                    .addComponent(jLabel11))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel37)
                    .addComponent(grossPay))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel20)
                    .addComponent(totalAllowances))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel21)
                    .addComponent(totalDeductions))
                .addGap(43, 43, 43)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel22)
                    .addComponent(netPayAmount))
                .addGap(48, 48, 48))
        );

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));

        jLabel13.setFont(new java.awt.Font("Cambria", 1, 12)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(37, 61, 144));
        jLabel13.setText("Earnings");

        jLabel32.setFont(new java.awt.Font("Cambria", 1, 12)); // NOI18N
        jLabel32.setText("Amount");

        jLabel33.setFont(new java.awt.Font("Cambria", 0, 12)); // NOI18N
        jLabel33.setForeground(new java.awt.Color(143, 143, 143));
        jLabel33.setText("Monthly Rate:");

        basicPayAmount1.setFont(new java.awt.Font("Cambria", 0, 12)); // NOI18N
        basicPayAmount1.setForeground(new java.awt.Color(143, 143, 143));
        basicPayAmount1.setText("0.00");

        jLabel34.setFont(new java.awt.Font("Cambria", 0, 12)); // NOI18N
        jLabel34.setForeground(new java.awt.Color(143, 143, 143));
        jLabel34.setText("Hourly Rate:");

        hourlyRateAmount1.setFont(new java.awt.Font("Cambria", 0, 12)); // NOI18N
        hourlyRateAmount1.setForeground(new java.awt.Color(143, 143, 143));
        hourlyRateAmount1.setText("0.00");

        jLabel35.setFont(new java.awt.Font("Cambria", 0, 12)); // NOI18N
        jLabel35.setForeground(new java.awt.Color(143, 143, 143));
        jLabel35.setText("Days Worked:");

        daysWorkedLabel.setFont(new java.awt.Font("Cambria", 0, 12)); // NOI18N
        daysWorkedLabel.setForeground(new java.awt.Color(143, 143, 143));
        daysWorkedLabel.setText("0.00");

        jLabel36.setFont(new java.awt.Font("Cambria", 0, 12)); // NOI18N
        jLabel36.setForeground(new java.awt.Color(143, 143, 143));
        jLabel36.setText("Over Time:");

        overTimePayAmount1.setFont(new java.awt.Font("Cambria", 0, 12)); // NOI18N
        overTimePayAmount1.setForeground(new java.awt.Color(143, 143, 143));
        overTimePayAmount1.setText("0.00");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel36)
                            .addComponent(jLabel35)
                            .addComponent(jLabel34)
                            .addComponent(jLabel33))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel13)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 121, Short.MAX_VALUE)
                        .addComponent(jLabel32)
                        .addGap(82, 82, 82))))
            .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel5Layout.createSequentialGroup()
                    .addContainerGap(201, Short.MAX_VALUE)
                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(basicPayAmount1)
                        .addComponent(hourlyRateAmount1)
                        .addComponent(daysWorkedLabel)
                        .addComponent(overTimePayAmount1))
                    .addGap(93, 93, 93)))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(38, 38, 38)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(jLabel32))
                .addGap(28, 28, 28)
                .addComponent(jLabel33)
                .addGap(18, 18, 18)
                .addComponent(jLabel34)
                .addGap(18, 18, 18)
                .addComponent(jLabel35)
                .addGap(18, 18, 18)
                .addComponent(jLabel36)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel5Layout.createSequentialGroup()
                    .addGap(82, 82, 82)
                    .addComponent(basicPayAmount1)
                    .addGap(18, 18, 18)
                    .addComponent(hourlyRateAmount1)
                    .addGap(18, 18, 18)
                    .addComponent(daysWorkedLabel)
                    .addGap(18, 18, 18)
                    .addComponent(overTimePayAmount1)
                    .addContainerGap(70, Short.MAX_VALUE)))
        );

        javax.swing.GroupLayout dashboardPanelLayout = new javax.swing.GroupLayout(dashboardPanel);
        dashboardPanel.setLayout(dashboardPanelLayout);
        dashboardPanelLayout.setHorizontalGroup(
            dashboardPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dashboardPanelLayout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(dashboardPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(dashboardPanelLayout.createSequentialGroup()
                        .addComponent(dashboardPane)
                        .addContainerGap(901, Short.MAX_VALUE))
                    .addGroup(dashboardPanelLayout.createSequentialGroup()
                        .addGroup(dashboardPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(profilePanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(dashboardPanelLayout.createSequentialGroup()
                                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(18, 18, 18)
                                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(17, 17, 17))))
        );
        dashboardPanelLayout.setVerticalGroup(
            dashboardPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dashboardPanelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(dashboardPane)
                .addGap(18, 18, 18)
                .addComponent(profilePanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(dashboardPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(68, 68, 68))
        );

        javax.swing.GroupLayout NavigationPanelLayout = new javax.swing.GroupLayout(NavigationPanel);
        NavigationPanel.setLayout(NavigationPanelLayout);
        NavigationPanelLayout.setHorizontalGroup(
            NavigationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(dashboardPanel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, NavigationPanelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel17)
                .addGap(443, 443, 443))
        );
        NavigationPanelLayout.setVerticalGroup(
            NavigationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, NavigationPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel17)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(dashboardPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 437, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(NavigationPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(NavigationPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void backButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_backButtonActionPerformed
        this.dispose();
    }//GEN-LAST:event_backButtonActionPerformed

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
            java.util.logging.Logger.getLogger(PaySlip.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(PaySlip.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(PaySlip.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(PaySlip.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new PaySlip().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel NavigationPanel;
    private javax.swing.JButton backButton;
    private javax.swing.JLabel basicPayAmount1;
    private javax.swing.JLabel clothingAllowance;
    private javax.swing.JLabel dashboardPane;
    private javax.swing.JPanel dashboardPanel;
    private javax.swing.JLabel daysWorkedLabel;
    private javax.swing.JLabel designationLabel;
    private javax.swing.JLabel eidLabel;
    private javax.swing.JLabel firstNameLabel;
    private javax.swing.JLabel grossPay;
    private javax.swing.JLabel hourlyRateAmount1;
    private javax.swing.JLabel incentives;
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
    private javax.swing.JLabel jLabel38;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JLabel lastNameLabel;
    private javax.swing.JLabel netPayAmount;
    private javax.swing.JLabel overTimePayAmount1;
    private javax.swing.JLabel pagIbigContribution;
    private javax.swing.JLabel payPeriodLabel;
    private javax.swing.JLabel payrollCycle;
    private javax.swing.JLabel philHealthContribution;
    private javax.swing.JLabel phoneAllowance;
    private javax.swing.JPanel profilePanel;
    private javax.swing.JLabel riceAllowance;
    private javax.swing.JLabel sssContribution;
    private javax.swing.JLabel totalAllowances;
    private javax.swing.JLabel totalDeductions;
    private javax.swing.JLabel withholdingTax;
    // End of variables declaration//GEN-END:variables
}
