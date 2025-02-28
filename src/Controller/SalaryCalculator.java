/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package Controller;

/**
 *
 * @author dashcodes
 */

import BusinessLogic.Allowances;
import BusinessLogic.Deductions;
import BusinessLogic.EmployeeDetails;
import java.util.HashMap;
import java.util.Map;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SalaryCalculator {
    // Constants for deduction rates
    private static final float SSS_RATE = 0.0363f;        // 3.63% SSS contribution
    private static final float PHILHEALTH_RATE = 0.035f;  // 3.5% PhilHealth
    private static final float PAGIBIG_RATE = 0.02f;      // 2% Pag-IBIG
    private static final float TAX_RATE = 0.15f;          // 15% tax (simplified)
    
    private Map<String, Float> deductions = new HashMap<>();
    private Map<String, String> formattedDeductions = new HashMap<>();
    private float totalDeductions = 0;
    private float netPay = 0;
    
    // Establish Connection
    private Connection connection;

   public SalaryCalculator(){        
    }
   
    public SalaryCalculator(Connection connection) {
        this.connection = connection;
    }
    
    /**
     * Gets allowances for an employee by employee ID
     */
    public Allowances getAllowancesForEmployee(int eid) throws SQLException {
        try (PreparedStatement ps = connection.prepareStatement(
                "SELECT * FROM allowances WHERE EID = ?")) {
            ps.setInt(1, eid);
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                return new Allowances(
                    rs.getInt("EID"),
                    rs.getFloat("Basic_Salary"),
                    rs.getFloat("Rice_Allowance"),
                    rs.getFloat("Phone_Allowance"),
                    rs.getFloat("Clothing_Allowance"),
                    rs.getFloat("Half_Month_Rate"),
                    rs.getFloat("Hourly_Rate")
                );
            }
        }
        return null;
    }
    
    /**
     * Gets deductions for an employee by employee ID
     */
    public Deductions getDeductionsForEmployee(int eid) throws SQLException {
        try (PreparedStatement ps = connection.prepareStatement(
                "SELECT * FROM deductions WHERE EID = ?")) {
            ps.setInt(1, eid);
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                return new Deductions(
                    rs.getInt("EID"),
                    rs.getFloat("Basic_Salary"),
                    rs.getFloat("Pag_Ibig_Contribution"),
                    rs.getFloat("PhilHealth_Contribution"),
                    rs.getFloat("SSS_Contribution"),
                    rs.getFloat("Withholding_Tax"),
                    rs.getFloat("Total_Deductions")
                );
            }
        }
        return null;
    }
    
    /**
     * Calculates total allowances from an Allowances object
     */
    public float calculateTotalAllowances(Allowances allowances) {
        return allowances.getRiceAllowance() + 
               allowances.getPhoneAllowance() + 
               allowances.getClothingAllowance();
    }
    
    /**
     * Calculates deductions based on employee's basic salary and allowances
     * @param employee The employee details
     */
    public void calculateDeductions(EmployeeDetails employee) {
        float basicSalary = employee.getBasicSalary();
        
        // Calculate individual deductions
        deductions.put("sss", basicSalary * SSS_RATE);
        deductions.put("philhealth", basicSalary * PHILHEALTH_RATE);
        deductions.put("pagibig", basicSalary * PAGIBIG_RATE);
        deductions.put("tax", basicSalary * TAX_RATE);
        
        // Calculate total deductions
        totalDeductions = 0;
        for (float value : deductions.values()) {
            totalDeductions += value;
        }
        
        // Format deductions for display
        for (Map.Entry<String, Float> entry : deductions.entrySet()) {
            formattedDeductions.put(entry.getKey(), formatCurrency(entry.getValue()));
        }
        formattedDeductions.put("totalDeductions", formatCurrency(totalDeductions));
    }
    
    /**
     * Calculates the net pay based on salary, allowances, and deductions
     * @param employee The employee details
     */
    public void calculateNetPay(EmployeeDetails employee) {
        float basicSalary = employee.getBasicSalary();
        
        // Calculate total allowances
        float riceSubsidy = parseFloatSafely(employee.getRiceSubsidy());
        float phoneAllowance = parseFloatSafely(employee.getPhoneAllowance());
        float clothingAllowance = parseFloatSafely(employee.getClothingAllowance());
        float totalAllowances = riceSubsidy + phoneAllowance + clothingAllowance;
        
        // Calculate net pay
        netPay = basicSalary + totalAllowances - totalDeductions;
        formattedDeductions.put("netPay", formatCurrency(netPay));
    }
    
    /**
     * Gets a formatted deduction value by key
     * @param key The deduction key
     * @return Formatted deduction amount
     */
    public String getFormattedDeduction(String key) {
        return formattedDeductions.getOrDefault(key, "N/A");
    }
    
    /**
     * Gets the raw numeric value of a deduction
     * @param key The deduction key
     * @return Deduction amount as float
     */
    public float getDeduction(String key) {
        return deductions.getOrDefault(key, 0f);
    }
    
    /**
     * Gets the total deductions amount
     * @return Total deductions
     */
    public float getTotalDeductions() {
        return totalDeductions;
    }
    
    /**
     * Gets the net pay amount
     * @return Net pay
     */
    public float getNetPay() {
        return netPay;
    }
    
    /**
     * Formats a float value as currency with the peso sign (₱)
     * @param value The value to format
     * @return Formatted currency string
     */
    private String formatCurrency(float value) {
        return String.format("₱%.2f", value);
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
}