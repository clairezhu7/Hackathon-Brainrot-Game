import javax.swing.*;
import java.awt.Font;
import java.awt.event.*;
import java.io.*;
import javax.sound.sampled.*;

public class WelcomeScreen extends JFrame {
    ImageIcon backgroundImage = new ImageIcon(getClass().getResource("/welcomeScreenBackground.png"));
    JLabel backgroundLabel = new JLabel();
    JLabel highScoreLabel = new JLabel();
    JButton playButton = new JButton();
    JButton instructionsButton = new JButton();
    Font displayFont = new Font("Gill Sans MT", Font.PLAIN, 25);
    static Clip clip;
    static int highScore = 0;
    
    public WelcomeScreen(int high) { 
        highScore = high;
        setTitle("Brainrot Memory Game");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setSize(1100, 700);
        setLocationRelativeTo(null);
        setLayout(null);
        
        playSound("src/chillGuyMusic.wav");
        
        backgroundLabel.setIcon(backgroundImage);
        backgroundLabel.setBounds(0, 0, 1100, 700);

        highScoreLabel.setFont(displayFont);
        highScoreLabel.setBounds(605, 518, 60, 40);
        if (highScore < 10){
            highScoreLabel.setText(" ---");
        } else {
            highScoreLabel.setText(String.valueOf(highScore));
        }
        add(highScoreLabel);
        
        playButton.setBounds(445, 295, 215, 75);
        playButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new GameGUI();
                dispose();
            }
        });
        playButton.setContentAreaFilled(false); 
        playButton.setBorderPainted(false);   
        playButton.setFocusPainted(false); 
        add(playButton);
        
        instructionsButton.setBounds(445, 395, 215, 75);
        instructionsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new InstructionsGUI();
            }
        });
        instructionsButton.setContentAreaFilled(false); 
        instructionsButton.setBorderPainted(false);   
        instructionsButton.setFocusPainted(false); 
        add(instructionsButton);
        
        add(backgroundLabel);
        setVisible(true);
    }

    public static int getHighScore(){
        return highScore;
    }

    public static void setHighScore(int score){
        highScore = score;
    }
    
    public static void playSound(String soundFileName) {
        stopAllAudio();
        try {
            File audioFile = new File(soundFileName);
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);
            clip = AudioSystem.getClip();

            clip.open(audioStream);
            clip.start();
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        } 
        catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    public static void stopAllAudio() {
        if (clip != null && clip.isRunning()) {
            clip.stop();
            clip.close();
        }
    }

    public static void main(String[] args) {
        new WelcomeScreen(0);
    }
}