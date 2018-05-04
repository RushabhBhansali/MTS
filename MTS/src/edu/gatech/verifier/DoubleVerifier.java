package edu.gatech.verifier;

import javax.swing.*;
import java.awt.*;

public class DoubleVerifier extends InputVerifier {
    @Override
    public boolean verify(JComponent jComponent) {
        boolean valid = true;
        JTextField textField = ((JTextField) jComponent);
        String inputText = textField.getText();
        try {
            if( !inputText.isEmpty() ) {
                double parsedNumber = Double.parseDouble(inputText);
                if( parsedNumber < 0 ) {
                    valid = false;
                }
            }
        } catch (NumberFormatException e) {
            valid = false;
        }
        if( valid ) {
            textField.setForeground(Color.BLACK);
        } else {
            textField.setForeground(Color.RED);
            JOptionPane.showMessageDialog(textField, "Invalid Input...Please enter positive value.");
        }
        return valid;
    }
}
