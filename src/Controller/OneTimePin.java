/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package Controller;

/**
 *
 * @author dashcodes
 */

import java.util.Random;
import javax.swing.JOptionPane;

public class OneTimePin {
    private static String generatedOTP;
    
    public static String generateOTP() {
        Random random = new Random();
        StringBuilder otp = new StringBuilder();
        
        // Generate 6 random digits
        for (int i = 0; i < 6; i++) {
            otp.append(random.nextInt(10));
        }
        
        generatedOTP = otp.toString();
        
        // Display the OTP in popup
        JOptionPane.showMessageDialog(null, 
            "Your OTP is: " + generatedOTP, 
            "One-Time PIN", 
            JOptionPane.INFORMATION_MESSAGE);
            
        return generatedOTP;
    }
    // Verify that the OTP Generated matches the inputOTP in the field
    public static boolean verifyOTP(String inputOTP) {
        return inputOTP != null && inputOTP.equals(generatedOTP);
    }
}