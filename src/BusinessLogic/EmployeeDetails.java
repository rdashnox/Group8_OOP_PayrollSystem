/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package BusinessLogic;

/**
 *
 * @author dashcodes
 */

import Controller.DBQueries;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class EmployeeDetails
{
    // Employee basic details
    private int eid;
    private String firstName;
    private String lastName;
    private String userName;
    private String password;
    private String designation;
    private String address;
    private String phoneNumber;
    private Date birthday;
    private String status;
    private String immediateSupervisor;
    
    // Government IDs
    private String sss;
    private String philHealth;
    private String tin;
    private String pagIbig;
    
    // Financial details
    private float basicSalary;
    private String riceSubsidy;
    private String phoneAllowance;
    private String clothingAllowance;
    
    // Financial fields
    private float basicPay;
    private float totalAllowances;
    private float totalDeductions;
    private float netPay;
    
    
    /**
     * Constructor overloading allows a class to have multiple constructors 
     * with different parameters, providing flexible ways to create objects.
     */
    
    public EmployeeDetails (){}
    
    public EmployeeDetails
        (
        int aInt,
        int aInt1,
        String string,
        String string1,
        String string2,
        Date date,
        String string3,
        String string4
        ){}
    
    public EmployeeDetails
            (
            int eid,
            String lastName,
            String firstName,
            Date birthday
            )
                
        {
        this.eid = eid;
        this.lastName = lastName;
        this.firstName = firstName;
        this.birthday = birthday;
        }
    
    public EmployeeDetails
            (
            String userName,
            String designation,
            String address,
            String phoneNumber
            )
                
        {
        this.userName = userName;
        this.designation = designation;
        this.address = address;
        this.phoneNumber = phoneNumber;
        }
   
    public EmployeeDetails
            (
            String sss,
            String philHealth,
            String tin,
            String pagIbig,
            String status,
            String immediateSupervisor
            )
                    
        {
         this.sss = sss;
         this.philHealth = philHealth;
         this.tin = tin;
         this.pagIbig = pagIbig;
         this.status = status;
         this.immediateSupervisor = immediateSupervisor;
        }
    
    public EmployeeDetails
            (
            String riceSubsidy,
            String phoneAllowance,
            String clothingAllowance
            )
           
        {
        this.riceSubsidy = riceSubsidy;
        this.phoneAllowance = phoneAllowance;
        this.clothingAllowance = clothingAllowance;
        }
    
    public EmployeeDetails
            (
            int eid,
            String lastName,
            String firstName,
            Date birthday,
            String userName,
            String password,
            String designation,
            String address,
            String phoneNumber
            )
                    
        {
        this.eid = eid;
        this.lastName = lastName;
        this.firstName = firstName;
        this.birthday = birthday;
        this.userName = userName;
        this.password = password;
        this.designation = designation;
        this.address = address;
        this.phoneNumber = phoneNumber;
        }
            
    public EmployeeDetails
            (
            int eid,
            String lastName,
            String firstName,
            Date birthday,
            String userName,
            String password,
            String designation,
            String address,
            String phoneNumber,
            String status,
            String immediateSupervisor
            )
        
        {
        this.eid = eid;
        this.lastName = lastName;
        this.firstName = firstName;
        this.birthday = birthday;
        this.userName = userName;
        this.password = password;
        this.designation = designation;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.status = status;
        this.immediateSupervisor = immediateSupervisor;
        }
    
    public EmployeeDetails
            (
            int eid,
            String lastName,
            String firstName,
            Date birthday,
            String userName,
            String password,
            String designation,
            String address,
            String phoneNumber,
            String sss,
            String philHealth,
            String tin,
            String pagIbig,
            String status,
            String immediateSupervisor
            )
                    
        {
        
        this.eid = eid;
        this.lastName = lastName;
        this.firstName = firstName;
        this.birthday = birthday;
        this.userName = userName;
        this.password = password;
        this.designation = designation;
        this.address = address;
        this.phoneNumber = phoneNumber;
        
        this.sss = sss;
        this.philHealth = philHealth;
        this.tin = tin;
        this.pagIbig = pagIbig;
        
        this.status = status;
        this.immediateSupervisor = immediateSupervisor;    
        }
    
    public EmployeeDetails
            (
            int eid,
            String lastName,
            String firstName,
            Date birthday,
            String userName,
            String password,
            String designation,
            String address,
            String phoneNumber,
            String sss,
            String philHealth,
            String tin,
            String pagIbig,
            String riceSubsidy,
            String phoneAllowance,
            String clothingAllowance,
            String status,
            String immediateSupervisor,
            Float basicSalary
            )
            
        {
        this.eid = eid;
        this.lastName = lastName;
        this.firstName = firstName;
        this.birthday = birthday;
        this.userName = userName;
        this.password = password;
        this.designation = designation;
        this.address = address;
        this.phoneNumber = phoneNumber;
        
        this.sss = sss;
        this.philHealth = philHealth;
        this.tin = tin;
        this.pagIbig = pagIbig;
        
        this.riceSubsidy = riceSubsidy;
        this.phoneAllowance = phoneAllowance;
        this.clothingAllowance = clothingAllowance;
        
        this.status = status;
        this.immediateSupervisor = immediateSupervisor;
        this.basicSalary = basicSalary;
        }
            
      public EmployeeDetails
            (float basicPay,
             float totalAllowances,
             float totalDeductions,
             float netPay
            )
        {
        this.basicPay = basicPay;
        this.totalAllowances = totalAllowances;
        this.totalDeductions = totalDeductions;
        this.netPay = netPay;
        }


    // Getter methods

    public int getEid() {
        return eid;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public String getDesignation() {
        return designation;
    }

    public String getAddress() {
        return address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public Date getBirthday() {
        return birthday;
    }

    public String getStatus() {
        return status;
    }

    public String getImmediateSupervisor() {
        return immediateSupervisor;
    }

    public String getSss() {
        return sss;
    }

    public String getPhilHealth() {
        return philHealth;
    }

    public String getTin() {
        return tin;
    }

    public String getPagIbig() {
        return pagIbig;
    }

    public float getBasicSalary() {
        return basicSalary;
    }

    public String getRiceSubsidy() {
        return riceSubsidy;
    }

    public String getPhoneAllowance() {
        return phoneAllowance;
    }

    public String getClothingAllowance() {
        return clothingAllowance;
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
    
    
    
    // Setter methods

    public void setEid(int eid) {
        this.eid = eid;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }

    public void setImmediateSupervisor(String immediateSupervisor) {
        this.immediateSupervisor = immediateSupervisor;
    }

    public void setSss(String sss) {
        this.sss = sss;
    }

    public void setPhilHealth(String philHealth) {
        this.philHealth = philHealth;
    }

    public void setTin(String tin) {
        this.tin = tin;
    }

    public void setPagIbig(String pagIbig) {
        this.pagIbig = pagIbig;
    }

    public void setBasicSalary(float basicSalary) {
        this.basicSalary = basicSalary;
    }

    public void setRiceSubsidy(String riceSubsidy) {
        this.riceSubsidy = riceSubsidy;
    }

    public void setPhoneAllowance(String phoneAllowance) {
        this.phoneAllowance = phoneAllowance;
    }

    public void setClothingAllowance(String clothingAllowance) {
        this.clothingAllowance = clothingAllowance;
    }

    public void setBasicPay(float basicPay) {
        this.basicPay = basicPay;
    }

    public void setTotalAllowances(float totalAllowances) {
        this.totalAllowances = totalAllowances;
    }

    public void setTotalDeductions(float totalDeductions) {
        this.totalDeductions = totalDeductions;
    }

    public void setNetPay(float netPay) {
        this.netPay = netPay;
    }
    
    
    
    
    
    /* Loads employee data from database based on username
     * @param username The username of the logged-in user
     * @param connection Database connection
     * @return EmployeeDetails object populated with data, or null if not found
     */
    public static EmployeeDetails loadByUsername(String username, Connection connection) {
        try {
            DBQueries dbQueries = new DBQueries();
            PreparedStatement statement = connection.prepareStatement(dbQueries.getSearchUsername());
            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();
            
            if (resultSet.next()) {
                EmployeeDetails employee = new EmployeeDetails();
                
                // Get employee details from result set
                employee.setEid(resultSet.getInt(dbQueries.getEid()));
                employee.setFirstName(resultSet.getString(dbQueries.getFirstName()));
                employee.setLastName(resultSet.getString(dbQueries.getLastName()));
                employee.setDesignation(resultSet.getString(dbQueries.getDesignation()));
                employee.setAddress(resultSet.getString(dbQueries.getAddress()));
                employee.setPhoneNumber(resultSet.getString(dbQueries.getPhoneNumber()));
                employee.setBirthday(resultSet.getDate(dbQueries.getBirthDay()));
                employee.setStatus(resultSet.getString(dbQueries.getStatus()));
                
                // Get government IDs
                employee.setSss(resultSet.getString(dbQueries.getSSS()));
                employee.setPhilHealth(resultSet.getString(dbQueries.getPhilHealth()));
                employee.setTin(resultSet.getString(dbQueries.getTin()));
                employee.setPagIbig(resultSet.getString(dbQueries.getPagIbig()));
                
                // Get basic salary if available
                try {
                    employee.setBasicSalary(resultSet.getFloat(dbQueries.getHalfMonthRate()));
                } catch (SQLException e) {
                    // Field might not be available in this query
                    employee.setBasicSalary(0);
                }
                
                // Close resources
                resultSet.close();
                statement.close();
                
                // Load allowances for this employee
                loadAllowances(employee, connection);
                
                return employee;
            }
            
            // Close resources
            resultSet.close();
            statement.close();
            
        } catch (SQLException ex) {
            System.err.println("Database error while loading employee: " + ex.getMessage());
            ex.printStackTrace();
        }
        
        return null;
    }
    
    /**
     * Loads employee allowance data from database
     * @param employee The employee object to update with allowances
     * @param connection Database connection
     */
    private static void loadAllowances(EmployeeDetails employee, Connection connection) {
        try {
            DBQueries dbQueries = new DBQueries();
            PreparedStatement statement = connection.prepareStatement(dbQueries.getSearchAllowancebyEID());
            statement.setInt(1, employee.getEid());
            
            ResultSet resultSet = statement.executeQuery();
            
            if (resultSet.next()) {
                // Get allowance values if available
                employee.setRiceSubsidy(resultSet.getString(dbQueries.getRiceAllowance()));
                employee.setPhoneAllowance(resultSet.getString(dbQueries.getPhoneAllowance()));
                employee.setClothingAllowance(resultSet.getString(dbQueries.getClothingAllowance()));
            }
            
            // Close resources
            resultSet.close();
            statement.close();
            
        } catch (SQLException ex) {
            System.err.println("Could not load allowance data: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
    
    /**
     * Formats a date to string using the specified pattern
     * @param date The date to format
     * @param pattern The date pattern (e.g., "MM/dd/yyyy")
     * @return Formatted date string or "N/A" if date is null
     */
    public String formatDate(Date date, String pattern) {
        if (date == null) {
            return "N/A";
        }
        
        SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
        return dateFormat.format(date);
    }
    
    /**
     * Gets the full name of the employee
     * @return First and last name combined
     */
    public String getFullName() {
        return firstName + " " + lastName;
    }

    public void saveToDatabase(Connection connection) {
        
    }

    public void setBirthday(String formattedBirthday) {
        
    }
}
