/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package Controller;



/**
 *
 * @author dashcodes
 */

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DBQueries {

    // Read-only query strings
    public String userName = "SELECT * FROM payrollmph WHERE username = ?";
    private final String userLogin = "SELECT * FROM payrollmph WHERE username = ? AND password = ?";
    private final String usernameExistsQuery = "SELECT COUNT(*) FROM payrollmph WHERE username = ?";
    private final String userLoginQuery = "SELECT * FROM payrollmph WHERE username = ? AND password = ?";
    public final String userDesignation = "SELECT designation FROM payrollmph WHERE username = ? AND password = ?";   
    public final String userFirstLogin = "SELECT FirstLogin FROM payrollmph WHERE username = ?";
    public final String adminAccess = "SELECT * FROM payrollmph";
    
    
    /* CRUD OPTION + Sort
    * Create/Add/Insert
    * Read/Search/View
    * Update
    * Delete/Disable    
    */
    
    private final String getEmployeeByEid = "SELECT * FROM payrollmph WHERE EID = ?";
    
    private final String insertEmployee = "INSERT INTO payrollmph "
                + "(EID, Last_Name, First_Name, Birthday, Address, Phone_Number, "
                + "SSS_#, Philhealth_#, `Pag-ibig_#`, Status, Designation, `Half_Month_Rate`) " 
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    
    private final String updateEmployee = "UPDATE payrollmph SET "
                + "Last_Name = ?,"
                + "First_Name = ?,"
                + "Birthday = ?,"
                + "Address = ?,"
                + "Phone_Number = ?,"
                + "SSS_# = ?,"
                + "Philhealth_# = ?,"
                + "TIN_# = ?,"
                + "Pag-ibig_# = ?,"
                + "Status = ?,"
                + "Designation = ?,"                
                + "Half_Month_Rate= ? WHERE EID = ?";
    
    private final String deleteEmployee = "DELETE FROM payrollmph WHERE EID = ?";
    
    public String createSearchQuery(String columnName) {
        return "SELECT * FROM payrollmph WHERE " + columnName + " LIKE ?";
    }
    
    public String createSortQuery(String columnName, boolean ascending) {
        String orderDirection = ascending ? "ASC" : "DESC";
        return "SELECT * FROM payrollmph ORDER BY " + columnName + " " + orderDirection;
    }
    
    
     // Mutable query strings
    private String insertData;
    private String returnAllData;
    private String updateDetails;
    private String updateDefaultPassword;
    private String updatePassword;
    private String loadData;
    private String searchEmployee;
    private String displayEmployeeInfo;
    private String searchAllowancebyEID;
    private String searchByEID;
    private String searchUsername;
    public String userSearchByEID;
    public String DisplayLogInfoByEIDandDate;

    // Column names
    public String eid;
    public String lastName;
    public String firstName;
    public String birthDay;
    private String password;
    public String designation;
    private String address;
    private String phoneNumber;
    private String SSS;
    private String philHealth;
    private String tin;
    private String pagIbig;
    public String status;
    private String immediateSupervisor;
    private String halfMonthRate;
    private String riceAllowance;
    private String phoneAllowance;
    private String clothingAllowance;
    private String hourlyRate;
    public String logDate;
    public String logTime;
    public String firstLoginQuery;
    public String firstLogin;

    public DBQueries() {
        initializeQueries();
        initializeColumnNames();
    }
    
    // 
    private void initializeQueries() {
        this.insertData = "INSERT INTO payrollmph"
                + "(Last_Name,"
                + "First_Name,"
                + "Birthday,"
                + "Username,"
                + "Password,"
                + "Designation,"
                + "Address,"
                + "Phone_Number,"
                + "SSS_#,"
                + "Philhealth_#,"
                + "TIN_#,"
                + "Pag-ibig_#,"
                + "Status,"
                + "Immediate_Supervisor,"
                + "FirstLogin)"
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, 1)";

        this.returnAllData = "UPDATE payrollmph SET"
                + "Last_Name = ?,"
                + "First_Name = ?,"
                + "Birthday = ?,"
                + "Username = ?,"
                + "Password = ?,"
                + "Designation = ?"
                + "WHERE ID = ?";

        this.updateDetails = "UPDATE payrollmph SET"
                + "Last_Name = ?,"
                + "First_Name = ?,"
                + "Birthday = ?,"
                + "Username = ?,"
                + "Designation = ?,"
                + "Address = ?,"
                + "Phone_Number = ?,"
                + "SSS_# = ?,"
                + "Philhealth_# = ?,"
                + "TIN_# = ?,"
                + "Pag-ibig_# = ?,"
                + "Status = ?,"
                + "Immediate_Supervisor = ? WHERE EID = ?";

        this.updateDefaultPassword = "UPDATE payrollmph SET"
                + "Password = ?,"
                + "FirstLogin = 0 WHERE EID = ?";
        
        this.firstLoginQuery = "UPDATE payrollmph SET FirstLogin = 0 WHERE username = ?";

        this.updatePassword = "UPDATE payrollmph SET"
                + "Password = ? WHERE Username = ?";

        this.loadData = "SELECT * FROM payrollmph";
        
        this.searchEmployee = "SELECT * FROM payrollmph "
                + "WHERE EID = ? OR Last_Name "
                + "LIKE ? OR First_Name LIKE ?";

        this.displayEmployeeInfo = "SELECT SSS_#,"
                + "Philhealth_#,"
                + "Pag-ibig_#,"
                + "TIN_# FROM payrollmph WHERE EID = ?";

        this.searchAllowancebyEID = "SELECT EID, "
                + "Basic_Salary, "
                + "Rice_Allowance, "
                + "Phone_Allowance, "
                + "Clothing_Allowance, "
                + "Hourly_Rate "
                + "Half_Month_Rate"
                + "FROM payrollmph WHERE EID = ?";

        this.searchByEID = "SELECT * FROM payrollmph WHERE EID = ? ";
        this.searchUsername = "SELECT * FROM payrollmph WHERE Username = ?";
        
        this.userSearchByEID = "SELECT EID,"
                + "Last_Name,"
                + "First_Name,"
                + "Birthday,"
                + "Username,"
                + "Designation,"
                + "FirstLogin FROM payrollmph WHERE ID = ? ORDER BY ID ASC LIMIT 1";

        this.DisplayLogInfoByEIDandDate = "SELECT EID,"
                + "EID,"
                + "LogDate,"
                + "LogTime,"
                + "Status FROM attendance WHERE ID = ? AND LogDate BETWEEN ? AND ?";
    }

    private void initializeColumnNames() {
        this.eid = "EID";
        this.lastName = "Last_Name";
        this.firstName = "First_Name";
        this.birthDay = "Birthday";
        this.password = "Password";
        this.userName = "Username";
        this.designation = "Designation";
        this.address = "Address";
        this.phoneNumber = "Phone_Number";
        this.SSS = "SSS_#";
        this.philHealth = "Philhealth_#";
        this.tin = "TIN_#";
        this.pagIbig = "Pag-ibig_#";
        this.status = "Status";
        this.immediateSupervisor = "Immediate_Supervisor";
        this.halfMonthRate = "Half_Month_Rate";
        this.riceAllowance = "Rice_Allowance";
        this.phoneAllowance = "Phone_Allowance";
        this.clothingAllowance = "Clothing_Allowance";
        this.hourlyRate = "Hourly_Rate";
        this.logDate = "LogDate";
        this.logTime = "LogTime";
        this.firstLogin = "FirstLogin";
    }
    
    private final String updatePasswordQuery = "UPDATE payrollmph SET Password = ? WHERE Username = ?";
    

    // Helper method to convert date string to java.sql.Date
    public java.sql.Date convertStringToSqlDate(String dateString) {
    try {
        // First, try to parse in MM/dd/yyyy format
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        dateFormat.setLenient(false);
        Date parsedDate = dateFormat.parse(dateString);
        return new java.sql.Date(parsedDate.getTime());
    } catch (ParseException e) {
        try {
            // If that fails, try yyyy-MM-dd format
            SimpleDateFormat altFormat = new SimpleDateFormat("yyyy-MM-dd");
            altFormat.setLenient(false);
            Date parsedDate = altFormat.parse(dateString);
            return new java.sql.Date(parsedDate.getTime());
        } catch (ParseException e2) {
            // If both formats fail, throw an exception with a clear message
            throw new IllegalArgumentException("Invalid date format. Please use MM/dd/yyyy or yyyy-MM-dd format. Input was: " + dateString);
            }
        }
    }

    // Getter methods
    public String getUsername() {
        return userName;
    }

    public String getUserLogin() {
        return userLogin;
    }

    public String getInsertData() {
        return insertData;
    }

    public String getReturnAllData() {
        return returnAllData;
    }

    public String getUpdateDetails() {
        return updateDetails;
    }

    public String getUpdateDefaultPassword() {
        return updateDefaultPassword;
    }

    public String getUpdatePassword() {
        return updatePassword;
    }

    public String getLoadData() {
        return loadData;
    }

    public String getSearchEmployee() {
        return searchEmployee;
    }

    public String getDisplayEmployeeInfo() {
        return displayEmployeeInfo;
    }

    public String getSearchAllowancebyEID() {
        return searchAllowancebyEID;
    }

    public String getSearchByEID() {
        return searchByEID;
    }

    public String getSearchUsername() {
        return searchUsername;
    }

    public String getUserSearchByEID() {
        return userSearchByEID;
    }

    public String getDisplayLogInfoByEIDandDate() {
        return DisplayLogInfoByEIDandDate;
    }

    public String getEid() {
        return eid;
    }

    public String getLastName() {
        return lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getBirthDay() {
        return birthDay;
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

    public String getSSS() {
        return SSS;
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

    public String getStatus() {
        return status;
    }

    public String getImmediateSupervisor() {
        return immediateSupervisor;
    }

    public String getHalfMonthRate() {
        return halfMonthRate;
    }

    public String getRiceAllowance() {
        return riceAllowance;
    }

    public String getPhoneAllowance() {
        return phoneAllowance;
    }

    public String getClothingAllowance() {
        return clothingAllowance;
    }

    public String getHourlyRate() {
        return hourlyRate;
    }

    public String getLogDate() {
        return logDate;
    }

    public String getLogTime() {
        return logTime;
    }

    public String getUsernameExistsQuery() {
        return usernameExistsQuery;
    }

    public String getUserLoginQuery() {
        return userLoginQuery;
    }

    public String getUpdatePasswordQuery() {
        return updatePasswordQuery;
    }

    public String getUserName() {
        return userName;
    }

    public String getFirstLoginQuery() {
        return firstLoginQuery;
    }

    public String getFirstLogin() {
        return firstLogin;
    }

    public String getUserDesignation() {
        return userDesignation;
    }

    public String getUserFirstLogin() {
        return userFirstLogin;
    }

    public String getAdminAccess() {
        return adminAccess;
    }

    public String getGetEmployeeByEid() {
        return getEmployeeByEid;
    }

    public String getInsertEmployee() {
        return insertEmployee;
    }

    public String getUpdateEmployee() {
        return updateEmployee;
    }

    public String getDeleteEmployee() {
        return deleteEmployee;
    }
    
    
    
    
    //Setter Methods

    public void setInsertData(String insertData) {
        this.insertData = insertData;
    }

    public void setReturnAllData(String returnAllData) {
        this.returnAllData = returnAllData;
    }

    public void setUpdateDetails(String updateDetails) {
        this.updateDetails = updateDetails;
    }

    public void setUpdateDefaultPassword(String updateDefaultPassword) {
        this.updateDefaultPassword = updateDefaultPassword;
    }

    public void setUpdatePassword(String updatePassword) {
        this.updatePassword = updatePassword;
    }

    public void setLoadData(String loadData) {
        this.loadData = loadData;
    }

    public void setSearchEmployee(String searchEmployee) {
        this.searchEmployee = searchEmployee;
    }

    public void setDisplayEmployeeInfo(String displayEmployeeInfo) {
        this.displayEmployeeInfo = displayEmployeeInfo;
    }

    public void setSearchAllowancebyEID(String searchAllowancebyEID) {
        this.searchAllowancebyEID = searchAllowancebyEID;
    }

    public void setSearchByEID(String searchByEID) {
        this.searchByEID = searchByEID;
    }

    public void setSearchUsername(String searchUsername) {
        this.searchUsername = searchUsername;
    }

    public void setUserSearchByEID(String userSearchByEID) {
        this.userSearchByEID = userSearchByEID;
    }

    public void setDisplayLogInfoByEIDandDate(String DisplayLogInfoByEIDandDate) {
        this.DisplayLogInfoByEIDandDate = DisplayLogInfoByEIDandDate;
    }

    public void setEid(String eid) {
        this.eid = eid;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setBirthDay(String birthDay) {
        this.birthDay = birthDay;
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

    public void setSSS(String SSS) {
        this.SSS = SSS;
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

    public void setStatus(String status) {
        this.status = status;
    }

    public void setImmediateSupervisor(String immediateSupervisor) {
        this.immediateSupervisor = immediateSupervisor;
    }

    public void setHalfMonthRate(String halfMonthRate) {
        this.halfMonthRate = halfMonthRate;
    }

    public void setRiceAllowance(String riceAllowance) {
        this.riceAllowance = riceAllowance;
    }

    public void setPhoneAllowance(String phoneAllowance) {
        this.phoneAllowance = phoneAllowance;
    }

    public void setClothingAllowance(String clothingAllowance) {
        this.clothingAllowance = clothingAllowance;
    }

    public void setHourlyRate(String hourlyRate) {
        this.hourlyRate = hourlyRate;
    }

    public void setLogDate(String logDate) {
        this.logDate = logDate;
    }

    public void setLogTime(String logTime) {
        this.logTime = logTime;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setFirstLoginQuery(String firstLoginQuery) {
        this.firstLoginQuery = firstLoginQuery;
    }

    public void setFirstLogin(String firstLogin) {
        this.firstLogin = firstLogin;
    }
    
}

    