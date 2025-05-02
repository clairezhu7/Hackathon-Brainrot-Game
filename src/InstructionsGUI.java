import javax.swing.*;

public class InstructionsGUI extends JFrame {
    ImageIcon backgroundImage = new ImageIcon(getClass().getResource("/instructionsBackground.png"));
    JLabel backgroundLabel = new JLabel();

    public InstructionsGUI() {
        setTitle("Instructions");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setResizable(false);
        setSize(750, 528);
        setLocationRelativeTo(null);
        setLayout(null);
        setAlwaysOnTop(true);
        
        backgroundLabel.setIcon(backgroundImage);
        backgroundLabel.setBounds(0, 0, 750, 500);
        add(backgroundLabel);

        setVisible(true);
    }
}