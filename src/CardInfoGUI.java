import java.awt.Color;
import java.awt.Font;

import javax.swing.*;

public class CardInfoGUI extends JFrame {
    ImageIcon backgroundImage = new ImageIcon(getClass().getResource("/cardInfoPopUp.png"));
    ImageIcon cardImage;
    JLabel backgroundLabel = new JLabel();
    JLabel imageLabel;
    JTextArea brainrotInfo;
    Card c;
    Font descriptionFont = new Font("Gill Sans MT", Font.PLAIN, 14);
    

    
    public CardInfoGUI(Card card) {
        c = card;
        setTitle(c.getType() + " Card Unlocked!");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setResizable(false);
        setSize(312, 435);
        setLocationRelativeTo(null);
        setLayout(null);
        setAlwaysOnTop(true);
        
        
        brainrotInfo = new JTextArea(c.toString()); 
        brainrotInfo.setBounds(25, 310, 250, 70);
        brainrotInfo.setLineWrap(true); 
        brainrotInfo.setWrapStyleWord(true);
        brainrotInfo.setEditable(false); 
        brainrotInfo.setOpaque(false);                      
        brainrotInfo.setBackground(new Color(0, 0, 0, 0)); 
        brainrotInfo.setForeground(Color.BLACK);           
        brainrotInfo.setBorder(null);    
        brainrotInfo.setFont(descriptionFont);           
        add(brainrotInfo);

        cardImage = new ImageIcon(getClass().getResource(card.getPNG()));
        imageLabel = new JLabel();
        imageLabel.setIcon(cardImage);
        imageLabel.setBounds(69, 36, 161, 231);
        add(imageLabel);
        
        backgroundLabel.setIcon(backgroundImage);
        backgroundLabel.setBounds(0, 0, 300, 400);
        add(backgroundLabel);

        setVisible(true);
    }
}
