/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package Controller;

/**
 *
 * @author dashcodes
 */

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.JLabel;

/**
 * Manages real-time date and time display for dashboard components
 */

public class TimeManager {
    private Timer timer;
    private JLabel dateLabel;
    private JLabel timeLabel;
    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MMMM dd, yyyy");
    private final DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("hh:mm:ss a");
    
    /**
     * Constructor initializes a TimeManager with labels to update
     * @param dateLabel JLabel to display the current date
     * @param timeLabel JLabel to display the current time
     */
     
    public TimeManager(JLabel dateLabel, JLabel timeLabel) {
        this.dateLabel = dateLabel;
        this.timeLabel = timeLabel;
    }
    
    /**
     * Starts updating the date and time labels in real-time
     */
    public void startClock() {
        // Set initial values
        updateDateTimeLabels();
        
        // Create timer that updates every second
        timer = new Timer(true);
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                updateDateTimeLabels();
            }
        }, 0, 1000); // Update every second
    }
    
    /**
     * Stops the clock updates
     */
    public void stopClock() {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }
    
    /**
     * Updates the date and time labels with current values
     */
    private void updateDateTimeLabels() {
        // Use invokeLater to ensure updates happen on the EDT (Event Dispatch Thread)
        java.awt.EventQueue.invokeLater(() -> {
            LocalDate today = LocalDate.now();
            LocalTime now = LocalTime.now();
            
            if (dateLabel != null) {
                dateLabel.setText(today.format(dateFormatter));
            }
            
            if (timeLabel != null) {
                timeLabel.setText(now.format(timeFormatter));
            }
        });
    }
}
