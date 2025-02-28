/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package BusinessLogic;

/**
 *  Model class for employee allowances
 * @author dashcodes
 */

public class Allowances {
    private int eid;
    private float basicSalary;
    private float riceAllowance;
    private float phoneAllowance;
    private float clothingAllowance;
    private float halfMonthRate;
    private float hourlyRate;
    private float totalAllowance;
    
    /**
     * Constructor overloading allows a class to have multiple constructors 
     * with different parameters, providing flexible ways to create objects.
     */
    
    public Allowances (){}
    
    public Allowances
            (
            int eid
            )
        {
        this.eid = eid;
        } 
    
    public Allowances
            (
            int eid,
            float riceAllowance,
            float phoneAllowance,
            float clothingAllowance
            )
        {
        this.eid = eid;
        this.riceAllowance = riceAllowance;
        this.phoneAllowance = phoneAllowance;
        this.clothingAllowance = clothingAllowance;
        calculateTotalAllowance();
        }

    public Allowances
            (
            int eid,
            float basicSalary,
            float riceAllowance,
            float phoneAllowance,
            float clothingAllowance,
            float halfMonthRate,
            float hourlyRate
            )
        {
        this.eid = eid;
        this.basicSalary = basicSalary;
        this.riceAllowance = riceAllowance;
        this.phoneAllowance = phoneAllowance;
        this.clothingAllowance = clothingAllowance;  
        this.halfMonthRate = halfMonthRate;
        this.hourlyRate = hourlyRate;
        calculateTotalAllowance();
        }    
            
     /**
     * Calculates the total allowance from individual allowances
     */
            
    private void calculateTotalAllowance() {
        this.totalAllowance = this.riceAllowance + this.phoneAllowance + this.clothingAllowance;
    }    

    // Getters

    public int getEid() {
        return eid;
    }

    public float getBasicSalary() {
        return basicSalary;
    }

    public float getRiceAllowance() {
        return riceAllowance;
    }

    public float getPhoneAllowance() {
        return phoneAllowance;
    }

    public float getClothingAllowance() {
        return clothingAllowance;
    }

    public float getHalfMonthRate() {
        return halfMonthRate;
    }

    public float getHourlyRate() {
        return hourlyRate;
    }

    public float getTotalAllowance() {
        return totalAllowance;
    }
        
    // Setters

    public void setEid(int eid) {
        this.eid = eid;
    }

    public void setBasicSalary(float basicSalary) {
        this.basicSalary = basicSalary;
    }

    public void setRiceAllowance(float riceAllowance) {
        this.riceAllowance = riceAllowance;
    }

    public void setPhoneAllowance(float phoneAllowance) {
        this.phoneAllowance = phoneAllowance;
    }

    public void setClothingAllowance(float clothingAllowance) {
        this.clothingAllowance = clothingAllowance;
    }

    public void setHalfMonthRate(float halfMonthRate) {
        this.halfMonthRate = halfMonthRate;
    }

    public void setHourlyRate(float hourlyRate) {
        this.hourlyRate = hourlyRate;
    }

    public void setTotalAllowance(float totalAllowance) {
        this.totalAllowance = totalAllowance;
    }
}
