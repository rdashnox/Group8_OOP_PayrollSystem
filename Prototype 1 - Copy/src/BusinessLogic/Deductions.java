/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package BusinessLogic;

/**
 *
 * @author dashcodes
 */

public class Deductions
    {
    private int eid;
    private float basicSalary;
    private float pagIbigContribution;
    private float philHealthContribution;
    private float sssContribution;
    private float withholdingTax;
    private float totalDeductions;
    
    /**
     * Constructor overloading allows a class to have multiple constructors 
     * with different parameters, providing flexible ways to create objects.
     * @param eid
     */
    
    public Deductions
        (
        int eid
        )
    {
        this.eid = eid;
    } 

    public Deductions
        (
        int eid,
        float basicSalary,
        float pagIbigContribution,
        float philHealthContribution,
        float sssContribution,
        float withholdingTax,
        float totalDeductions
        )
        
        {
        this.eid = eid;
        this.basicSalary = basicSalary;
        this.pagIbigContribution = pagIbigContribution;
        this.philHealthContribution = philHealthContribution;
        this.sssContribution = sssContribution;  
        this.withholdingTax = withholdingTax;
        this.totalDeductions = totalDeductions;
        }    

    // Getters

    public int getEid() {
        return eid;
    }

    public float getBasicSalary() {
        return basicSalary;
    }

    public float getPagIbigContribution() {
        return pagIbigContribution;
    }

    public float getPhilHealthContribution() {
        return philHealthContribution;
    }

    public float getSssContribution() {
        return sssContribution;
    }

    public float getWithholdingTax() {
        return withholdingTax;
    }

    public float getTotalDeductions() {
        return totalDeductions;
    }
        
    // Setters

    public void setEid(int eid) {
        this.eid = eid;
    }

    public void setBasicSalary(float basicSalary) {
        this.basicSalary = basicSalary;
    }

    public void setPagIbigContribution(float pagIbigContribution) {
        this.pagIbigContribution = pagIbigContribution;
    }

    public void setPhilHealthContribution(float philHealthContribution) {
        this.philHealthContribution = philHealthContribution;
    }

    public void setSssContribution(float sssContribution) {
        this.sssContribution = sssContribution;
    }

    public void setWithholdingTax(float withholdingTax) {
        this.withholdingTax = withholdingTax;
    }

    public void setTotalDeductions(float totalDeductions) {
        this.totalDeductions = totalDeductions;
    }
}
