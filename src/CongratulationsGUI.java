import javax.swing.*;
import java.awt.event.*;

public class CongratulationsGUI extends JFrame implements ActionListener {
    ImageIcon backgroundImage = new ImageIcon(getClass().getResource("/congratulationsBackground.png"));
    JLabel backgroundLabel = new JLabel();
    JButton yesButton = new JButton();
    JButton noButton = new JButton();

    public CongratulationsGUI() {
        setTitle("Congratulations");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setSize(750, 528);
        setLocationRelativeTo(null);
        setLayout(null);
        setAlwaysOnTop(true);
        
        backgroundLabel.setIcon(backgroundImage);
        backgroundLabel.setBounds(0, 0, 750, 500);
        
        yesButton.setBounds(247, 306, 110, 50);
        yesButton.setContentAreaFilled(false);  
        yesButton.setBorderPainted(false);      
        yesButton.setFocusPainted(false); 
        yesButton.addActionListener(this);
        add(yesButton);
        
        noButton.setBounds(387, 307, 110, 50);
        noButton.setContentAreaFilled(false);
        noButton.setBorderPainted(false);   
        noButton.setFocusPainted(false); 
        noButton.addActionListener(this);
        add(noButton);
        
        add(backgroundLabel);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == yesButton) {
            new WelcomeScreen(WelcomeScreen.getHighScore());
            dispose();
        }
        else if (e.getSource() == noButton) {
            System.exit(0);
        } 
    }
    
    public JButton getYesButton(){
        return yesButton;
    }

}