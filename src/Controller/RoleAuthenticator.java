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
import BusinessLogic.Allowances;
import BusinessLogic.Deductions;
import BusinessLogic.EmployeeDetails;
import GUI.EmployeeDashboard;
import GUI.AdminDashboard;
import GUI.ChangePassword;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class RoleAuthenticator {
    private DBQueries dbq = new DBQueries();
    private DatabaseConnection dbConnection;
    private Connection connection;
    private SalaryCalculator salaryCalculator;

    public RoleAuthenticator(Connection connection) {
        this.salaryCalculator = new SalaryCalculator(connection);
    }
    
    /**
     * Gets all the data needed for the Employee Dashboard
     * @param eid
     * @return EmployeeDashboardData containing all necessary dashboard information
     * @throws SQLException if there's a database error
     */
    public EmployeeDashboardData getEmployeeDashboardData(int eid) throws SQLException {
        if (connection == null || connection.isClosed()) {
            connection = DatabaseConnection.getInstance().getConnection();
        }
        
        EmployeeDetails employee = getEmployeeDetails(eid);
        Allowances allowances = salaryCalculator.getAllowancesForEmployee(eid);
        Deductions deductions = salaryCalculator.getDeductionsForEmployee(eid);

        if (employee != null && allowances != null && deductions != null) {
            return new EmployeeDashboardData(
                employee.getFirstName(),
                employee.getLastName(),
                employee.getDesignation(),
                allowances.getBasicSalary(),
                salaryCalculator.calculateTotalAllowances(allowances),
                deductions.getTotalDeductions()
            );
        }
        throw new SQLException("Could not retrieve all required employee data");
    }
    
    // An inner class to hold All Dashboard Data
    public static class EmployeeDashboardData {
        private final String firstName;
        private final String lastName;
        private final String designation;
        private final float basicPay;
        private final float totalAllowances;
        private final float totalDeductions;
        private final float netPay;
        
        
        public EmployeeDashboardData(String firstName, String lastName, String designation, 
                                     float basicPay, float totalAllowances, float totalDeductions) {
            this.firstName = firstName;
            this.lastName = lastName;
            this.designation = designation;
            this.basicPay = basicPay;
            this.totalAllowances = totalAllowances;
            this.totalDeductions = totalDeductions;
            this.netPay = basicPay + totalAllowances - totalDeductions;
        }

        // Getter methods
        public String getFirstName() {
            return firstName;
        }

        public String getLastName() {
            return lastName;
        }

        public String getDesignation() {
            return designation;
        }

        public float getBasicPay() {
            return basicPay;
        }

        public float getTotalAllowances() {
            return totalAllowances;
        }

        public float getTotalDeductions() {
            return totalDeductions;
        }

        public float getNetPay() {
            return netPay;
        }
    }
    
    public RoleAuthenticator() throws SQLException {
        try {
            this.connection = DatabaseConnection.getInstance().getConnection();
        } catch (SQLException e) {
            System.err.println("Failed to initialize database connection: " + e.getMessage());
            throw e;
        }
    }
    
    public boolean checkUsernameExists(String username) throws SQLException {
        if (connection == null || connection.isClosed()) {
            connection = DatabaseConnection.getInstance().getConnection();
        }

        try (PreparedStatement stmt = connection.prepareStatement(dbq.getUsernameExistsQuery())) {
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        }
        return false;
    }
        
    /**
     * Handles the entire login process - authentication, role determination, 
     * first login check, and dashboard redirection
     * 
     * @param username The username entered
     * @param password The password entered
     * @param loginForm The login form to dispose on success
     * @return boolean indicating if login was successful
     */
    public boolean handleLogin(String username, String password, JFrame loginForm) {
       try {
            // First, check if credentials are valid
            EmployeeDetails loggedInUser = authenticateAndGetUserDetails(username, password);
            
            if (loggedInUser != null) {
                // Check if this is the user's first login
                boolean isFirstLogin = checkFirstLogin(username);
                
                // Close login form
                if (loginForm != null) {
                    loginForm.dispose();
                }
                
                if (isFirstLogin) {
                    // Open change password screen for first-time users
                    ChangePassword changePasswordForm = new ChangePassword(username, true);
                    changePasswordForm.setVisible(true);
                } else {
                    // Regular login - redirect to appropriate dashboard
                    redirectBasedOnRole(loggedInUser);
                }
                return true;
            } else {
                JOptionPane.showMessageDialog(loginForm, 
                    "Invalid username or password", 
                    "Login Error", 
                    JOptionPane.ERROR_MESSAGE);
                return false;
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(loginForm, 
                "Error during login: " + e.getMessage(), 
                "Database Error", 
                JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }
    
    /**
     * Checks if this is the user's first login
     * 
     * @param username The username to check
     * @return true if first login, false otherwise
     * @throws SQLException If database access fails
     */
    public boolean checkFirstLogin(String username) throws SQLException {
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        try {
            connection = DatabaseConnection.getInstance().getConnection();
            pstmt = connection.prepareStatement(dbq.userFirstLogin);
            pstmt.setString(1, username);
            rs = pstmt.executeQuery();
            
            if (rs.next()) {
                int firstLoginValue = rs.getInt("FirstLogin");
                return firstLoginValue == 1;
            }
            return false;
        } finally {
            // Close resources in reverse order
            if (rs != null) rs.close();
            if (pstmt != null) pstmt.close();
            // Don't close connection here as it may be reused
        }
    }
    
    /**
     * Updates the FirstLogin flag to 0 after password change
     * 
     * @param username The username to update
     * @return true if update successful, false otherwise
     * @throws SQLException If database access fails
     */
    public boolean updateFirstLoginFlag(String username) throws SQLException {
        Connection connection = null;
        PreparedStatement pstmt = null;
        
        try {
            connection = DatabaseConnection.getInstance().getConnection();
            pstmt = connection.prepareStatement(dbq.firstLoginQuery);
            pstmt.setString(1, username);
            
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } finally {
            if (pstmt != null) pstmt.close();
            // Don't close connection here as it may be reused
        }
    }
    
    /**
     * Redirects user to the appropriate dashboard based on their designation.
     * 
     * @param loggedInUser The authenticated user's details
     */
    public void redirectBasedOnRole(EmployeeDetails loggedInUser) {
        if (loggedInUser == null) {
            throw new IllegalArgumentException("Logged-in user details cannot be null.");
        }

        String designation = loggedInUser.getDesignation();

        if (isRankAndFile(designation)) {
            openEmployeeDashboard(loggedInUser);
        } else {
            openAdminDashboard(loggedInUser);
        }
    }

    /**
     * For use with ChangePassword - redirects user after looking up their details.
     * 
     * @param username The username of the user
     * @param password The password of the user
     * @throws SQLException If database access fails or user details cannot be retrieved
     */
    public void redirectBasedOnRole(String username, String password) throws SQLException {
        EmployeeDetails loggedInUser = authenticateAndGetUserDetails(username, password);
        if (loggedInUser != null) {
            redirectBasedOnRole(loggedInUser);
        } else {
            throw new SQLException("Failed to retrieve user details after password change.");
        }
    }

    /**
     * Checks if the user's designation is "Rank and File".
     * @param designation The user's designation
     * @return True if the designation contains "Rank and File", otherwise false
     */
    private boolean isRankAndFile(String designation) {
        return designation != null && designation.contains("Rank and File");
    }

    /**
     * Opens the EmployeeDashboard with the provided user details.
     * 
     * @param loggedInUser The authenticated user's details
     */
    private void openEmployeeDashboard(EmployeeDetails loggedInUser) {
        new EmployeeDashboard(
            loggedInUser.getFirstName(),
            loggedInUser.getLastName(),
            loggedInUser.getDesignation(),
            loggedInUser.getEid(),
            loggedInUser.getBasicPay(),
            loggedInUser.getTotalAllowances(),
            loggedInUser.getTotalDeductions(),
            loggedInUser.getNetPay()
        ).setVisible(true);
    }

    /**
     * Opens the AdminDashboard with the provided user details.
     * 
     * @param loggedInUser The authenticated user's details
     */
    private void openAdminDashboard(EmployeeDetails loggedInUser) {
        new AdminDashboard(
            loggedInUser.getFirstName(),
            loggedInUser.getLastName(),
            loggedInUser.getDesignation(),
            loggedInUser.getEid()
            ).setVisible(true);
    }
    
    /**
     * Authenticates user and returns their details
     * 
     * @param username The username of the user
     * @param password The password of the user
     * @return EmployeeDetails object with user information, or null if authentication fails
     * @throws SQLException If database access fails
     */    
    public EmployeeDetails authenticateAndGetUserDetails(String username, String password) throws SQLException {
        List<EmployeeDetails> userDetailsList = userLogin(username, password);

        if (!userDetailsList.isEmpty()) {
            EmployeeDetails userDetails = userDetailsList.get(0);

            // Fetch financial data for the user
            float basicPay = getBasicPay(userDetails.getEid());
            float totalAllowances = getTotalAllowances(userDetails.getEid());
            float totalDeductions = getTotalDeductions(userDetails.getEid());
            float netPay = basicPay + totalAllowances - totalDeductions;

            // Set financial data in the EmployeeDetails object
            userDetails.setBasicPay(basicPay);
            userDetails.setTotalAllowances(totalAllowances);
            userDetails.setTotalDeductions(totalDeductions);
            userDetails.setNetPay(netPay);

            return userDetails; // Return the user details with financial data
        }

        return null; // Authentication failed
    }
    
     
    public List<EmployeeDetails> userLogin(String username, String password) throws SQLException {
        List<EmployeeDetails> list = new ArrayList<>();

        if (connection == null || connection.isClosed()) {
            connection = DatabaseConnection.getInstance().getConnection();
        }

        try (PreparedStatement ps = connection.prepareStatement(dbq.getUserLoginQuery())) {
            ps.setString(1, username);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(new EmployeeDetails(
                    rs.getInt("EID"),
                    rs.getString("Last_Name"),
                    rs.getString("First_Name"),
                    rs.getDate("Birthday"),
                    rs.getString("Username"),
                    rs.getString("Password"),
                    rs.getString("Designation"),
                    rs.getString("Address"),
                    rs.getString("Phone_Number"),
                    rs.getString("SSS_#"),
                    rs.getString("Philhealth_#"),
                    rs.getString("TIN_#"),
                    rs.getString("Pag-ibig_#"),
                    rs.getString("Status"),
                    rs.getString("Immediate_Supervisor")
                ));
            }

            if (!list.isEmpty()) {
                // Success message will be handled by the caller
            } else {
                // Error message will be handled by the caller
            }
        }
        return list;
    }
        
    /**
     * Gets employee details by employee ID
     */
    private EmployeeDetails getEmployeeDetails(int eid) throws SQLException {
        try (PreparedStatement ps = connection.prepareStatement(
                "SELECT * FROM employee_details WHERE EID = ?")) {
            ps.setInt(1, eid);
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                return new EmployeeDetails(
                    rs.getInt("EID"),
                    rs.getString("Last_Name"),
                    rs.getString("First_Name"),
                    rs.getDate("Birthday"),
                    rs.getString("Username"),
                    rs.getString("Password"),
                    rs.getString("Designation"),
                    rs.getString("Address"),
                    rs.getString("Phone_Number"),
                    rs.getString("SSS_#"),
                    rs.getString("Philhealth_#"),
                    rs.getString("TIN_#"),
                    rs.getString("Pag-ibig_#"),
                    rs.getString("Status"),
                    rs.getString("Immediate_Supervisor")
                );
            }
        }
        return null;
    }

    
    /**
     * Calculates total allowances from an Allowances object
     */
    private float calculateTotalAllowances(Allowances allowances) {
        return allowances.getRiceAllowance() + 
               allowances.getPhoneAllowance() + 
               allowances.getClothingAllowance();
    }
    
    /**
     * Gets the current date formatted as a string
     * @return Formatted date string
     */
    public static String getCurrentDateFormatted() {
        LocalDate today = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM dd, yyyy");
        return today.format(formatter);
    }
    
    /**
     * Gets the current time formatted as a string
     * @return Formatted time string
     */
    public static String getCurrentTimeFormatted() {
        LocalTime now = LocalTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm:ss a");
        return now.format(formatter);
    }
    
    public void updatePassword(String username, String newPassword) throws SQLException {
        try (Connection connection = DatabaseConnection.getInstance().getConnection();
             PreparedStatement ps = connection.prepareStatement(dbq.getUpdatePasswordQuery())) {
            ps.setString(1, newPassword);
            ps.setString(2, username);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
}
    
   
    /**
     * Fetches the basic pay for the given employee ID.
     */
    private float getBasicPay(int eid) throws SQLException {
        // Execute a query to fetch basicPay from the database
        // Example: SELECT basicPay FROM payroll WHERE EID = ?
        // Return the fetched value
        return 25000.0f; // Replace with actual database query
    }

    /**
     * Fetches the total allowances for the given employee ID.
     */
    private float getTotalAllowances(int eid) throws SQLException {
        // Execute a query to fetch totalAllowances from the database
        // Example: SELECT totalAllowances FROM payroll WHERE EID = ?
        // Return the fetched value
        return 5000.0f; // Replace with actual database query
    }

    /**
     * Fetches the total deductions for the given employee ID.
     */
    private float getTotalDeductions(int eid) throws SQLException {
        // Execute a query to fetch totalDeductions from the database
        // Example: SELECT totalDeductions FROM payroll WHERE EID = ?
        // Return the fetched value
        return 3000.0f; // Replace with actual database query
    }

   /**
     * Authenticates user based on their designation and opens the appropriate dashboard
     * 
     * @param designation The user's designation from the database
     * @return boolean indicating successful authentication
     */
    public boolean authenticateAndRedirect(String designation) {
        try {
            if (designation != null) {
                if (designation.contains("Rank and File")) {
                    // For all Rank and File designations (HR, Payroll, Account)
                    EmployeeDashboard employeeDashboard = new EmployeeDashboard();
                    employeeDashboard.setVisible(true);
                } else {
                    // For all other designations (CEO, managers, TLs, etc.)
                    AdminDashboard adminDashboard = new AdminDashboard();
                    adminDashboard.setVisible(true);
                }
                return true;
                
            } else {
                System.out.println("Error: Designation is null");
                return false;
            }
        } catch (Exception e) {
            System.out.println("Error during authentication: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public void closeConnection() {
        if (dbConnection != null) {
            dbConnection.closeConnection();
        }
    }
}