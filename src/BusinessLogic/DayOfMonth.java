/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package BusinessLogic;

/**
 *
 * @author dashcodes
 */

import java.time.LocalDate;

public class DayOfMonth {

     /**
     * Get the last day of the month for a given month and year.
     * @param year  the year
     * @param month the month (1-12)
     * @return the last day of the month as a LocalDate
     */
    
    public static LocalDate getFirstDayOfMonth(int month, int year)
    {
        return LocalDate.now()
                .withYear(year)
                .withMonth(month)
                .withDayOfMonth(1);
    }
    
    public static LocalDate getLastDayOfMonth(int month, int year)
    {
        return LocalDate.now()
                .withYear(year)
                .withMonth(month)
                .withDayOfMonth(1)
                .plusMonths(1)
                .minusDays(1);
    } 
}
