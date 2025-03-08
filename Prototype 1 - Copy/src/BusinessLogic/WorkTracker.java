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
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;


public class WorkTracker {
    /** Standard work hours per day */
    public static final int REGULAR_WORK_HOURS = 8;
    /** Standard work minutes per day (8 hours * 60 minutes) */
    private static final int REGULAR_WORK_MINUTES = REGULAR_WORK_HOURS * 60;
    
    /** Total minutes worked in the current calculation period */
    public long calculateMinutesWorked = 0;
    /** Total overtime minutes in the current calculation period */
    public long calculateOvertimeMinutes = 0;
    /** Total undertime minutes in the current calculation period */
    public long calculateUndertimeMinutes = 0;
    
    /** Data structure to store work records */
    private final List<WorkRecord> workRecords = new ArrayList<>();
    
    /**
     * Represents a single day's work record
     */
    
    private static class WorkRecord
    {
        private LocalDate date;
        private LocalTime startTime;
        private LocalTime endTime;
        
        public WorkRecord
        (
        LocalDate date,
        LocalTime startTime,
        LocalTime endTime
        )
            {
            this.date = date;
            this.startTime = startTime;
            this.endTime = endTime;
            }
    }
    
    /**
     * Initializes a new WorkTracker with an empty record list
     */
    
    public WorkTracker(){}
    
    /**
     * Records work times for a specific date
     * 
     * @param date The date of the work record
     * @param startTime Time when work started
     * @param endTime Time when work ended
     */
    
    public void addWorkTime(LocalDate date, LocalTime startTime, LocalTime endTime)
    {
        workRecords.add(new WorkRecord(date, startTime, endTime));
    }
    
    /**
     * Calculates the duration between start and end times in minutes
     * 
     * @param startTime Beginning of work period
     * @param endTime End of work period
     * @return Total minutes worked
     */
    
    public long calculateMinutesWorked(LocalTime startTime, LocalTime endTime) {
        return ChronoUnit.MINUTES.between(startTime, endTime);
    }
    
    /**
     * Generates a monthly summary of work hours, including overtime and undertime
     * Accounts for standard lunch break deduction
     * 
     * @param month Month to analyze (1-12)
     * @param year Year to analyze
     */
    
    public void calculateMonthlyWorkHours(int month, int year)
    {
        long totalMinutesWorked = 0;
        long totalOvertimeMinutes = 0;
        long totalUndertimeMinutes = 0;
        
        LocalDate startDate = LocalDate.of(year, month, 1);
        LocalDate endDate = startDate.withDayOfMonth(startDate.lengthOfMonth());
        
        for (WorkRecord record : workRecords)
        {
            if (record.date.getMonth() == startDate.getMonth() && 
                record.date.getYear() == startDate.getYear())
            {
                
                long minutesWorked = calculateMinutesWorked(record.startTime, record.endTime);
                // Deduct standard 1-hour lunch break
                minutesWorked -= 60;
                
                totalMinutesWorked += minutesWorked;
                
                if (minutesWorked > REGULAR_WORK_MINUTES)
                {
                    totalOvertimeMinutes += (minutesWorked - REGULAR_WORK_MINUTES);
                } else if (minutesWorked < REGULAR_WORK_MINUTES)
                {
                    totalUndertimeMinutes += (REGULAR_WORK_MINUTES - minutesWorked);
                }
            }
        }
        
        // Store calculated values
        calculateMinutesWorked = totalMinutesWorked;
        calculateOvertimeMinutes = totalOvertimeMinutes;
        calculateUndertimeMinutes = totalUndertimeMinutes;
        
        // Print summary
        System.out.printf("Total hours worked: %d hours and %d minutes%n", 
            totalMinutesWorked / 60, totalMinutesWorked % 60);
        System.out.printf("Overtime: %d hours and %d minutes%n", 
            totalOvertimeMinutes / 60, totalOvertimeMinutes % 60);
        System.out.printf("Undertime: %d hours and %d minutes%n", 
            totalUndertimeMinutes / 60, totalUndertimeMinutes % 60);
    }
}
