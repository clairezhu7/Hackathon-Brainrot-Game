import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class GameGUI extends JFrame implements ActionListener {

    private final String[][] brainrot = {
        {"Ballerina Cappuccino", "/cappuccino.png", "a cappuccino who is a ballerina (no one knows why)", "Brainrot"}, 
        {"Oiiai Cat", "/cat.png", "cat that endlessly spins clockwise :3", "Brainrot"}, 
        {"Tralalero Tralala", "/shark.png", "shark with nike shoes!!", "Brainrot"}, 
        {"Chicken Nugget", "/chicken_nugget.png", "chicken nugget with the roblox man face", "Brainrot"}, 
        {"Symphony Dolphin", "/symphony.png", "cool dolphin art and upbeat music to cancel out the insane thoughts", "Brainrot"}, 
        {"Blood Pressure Monitor", "/blood_pressure_monitor.png", "(aka sphygmomanometer) a device used to measure blood pressure", "Medical"}, 
        {"Glucose Monitor", "/glucose_monitor.png", "measures blood sugar levels, convenient for diabetes patients", "Medical"}
    };

    private JPanel backgroundPanel, popUpPanel, blockingPanel;
    private JLabel imageLabel, titleLabel, descriptionLabel, numFlipsLabel, cardsLeftLabel;
    private Card[][] cards;
    private int[] match;
    private int numFlips = 0;
    private Map<Card, JButton> cardToButton = new HashMap<>();
    private JButton[][] cardBTN;
    private Color transparent = new Color(0, 0, 0, 0);
    private Font displayFont = new Font("Gill Sans MT", Font.PLAIN, 25);
    private Font titleFont = new Font("Gill Sans MT", Font.BOLD, 20);
    private Font descriptionFont = new Font("Gill Sans MT", Font.PLAIN, 18);

    private Image background = new ImageIcon(getClass().getResource("/background.png")).getImage();
    private ImageIcon cardback = new ImageIcon(getClass().getResource("/cardback.png"));
    private ImageIcon cardbackRollover = new ImageIcon(getClass().getResource("/cardback_rollover.png"));
    private ImageIcon cardbackPressed = new ImageIcon(getClass().getResource("/cardback_pressed.png"));

    public GameGUI(){
        setTitle("Brainrot Memory Game");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setLayout(null);

        cards = new Card[2][5];
        match = new int[2];

        resetCards();

        backgroundPanel = new JPanel(){
            @Override
            public void paintComponent(Graphics g){
                super.paintComponent(g);
                g.drawImage(background, 0, 0, background.getWidth(null), background.getHeight(null), this);
            }
        };
        backgroundPanel.setPreferredSize(new Dimension(background.getWidth(null), background.getHeight(null)));
        backgroundPanel.setLayout(null);
        setContentPane(backgroundPanel);
        
        popUpPanel = new JPanel();
        popUpPanel.setLayout(new BoxLayout(popUpPanel, BoxLayout.Y_AXIS));
        popUpPanel.setSize(new Dimension(300, 600));

        blockingPanel = new JPanel();
        blockingPanel.setOpaque(false);
        blockingPanel.addMouseListener(new MouseAdapter() {});
        setGlassPane(blockingPanel);

        imageLabel = new JLabel();
        titleLabel = new JLabel();
        descriptionLabel = new JLabel();
        numFlipsLabel = new JLabel();
        cardsLeftLabel = new JLabel();

        JLabel[] labels = {imageLabel, titleLabel, descriptionLabel};
        for (int i = 0; i < 3; i++){
            labels[i].setAlignmentX(CENTER_ALIGNMENT);
        }

        labels = new JLabel[]{numFlipsLabel, cardsLeftLabel};
        for (int i = 0; i < 2; i++){
            labels[i].setFont(displayFont);
            labels[i].setBounds(470 + 230 * i, 333, 50, 40);
            getContentPane().add(labels[i]);
        }

        titleLabel.setFont(titleFont);
        descriptionLabel.setFont(descriptionFont);

        resetDisplay();

        UIManager.put("Button.select", transparent);

        cardBTN = new JButton[2][5];
        for (int i = 0; i < 2; i++){
            for (int j = 0; j < 5; j++){
                cardBTN[i][j] = new JButton();
                cardBTN[i][j].setBorder(null);
                cardBTN[i][j].setBounds(219 * j + 32, 368 * i + 54, 161, 231);
                cardBTN[i][j].setFocusPainted(false);
                cardBTN[i][j].setIcon(cardback);
                cardBTN[i][j].setRolloverIcon(cardbackRollover);
                cardBTN[i][j].setPressedIcon(cardbackPressed);
                cardBTN[i][j].addActionListener(this);
                getContentPane().add(cardBTN[i][j]);
            }
        }
        resetButtons();

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        JButton button = (JButton) e.getSource();
        button.setEnabled(false);
        match[Card.getNumCardsFacingDown()%2] = findBTNIndex(button);
        Card.setNumCardsFacingDown(-1);
        numFlips++;
        resetDisplay();

        if (Card.getNumCardsFacingDown() % 2 == 0){
            Card card1 = cards[match[0] / 5][match[0] % 5];
            Card card2 = cards[match[1] / 5][match[1] % 5];
            if (card1.equals(card2)){
                card1.setFacingUp();
                card2.setFacingUp();

                getGlassPane().setVisible(true);

                JFrame cardPopUp = new CardInfoGUI(card1);
                cardPopUp.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosing(WindowEvent e){
                        cardPopUp.dispose();

                        cardToButton.get(card1).setVisible(false);
                        cardToButton.get(card2).setVisible(false);

                        getGlassPane().setVisible(false);

                        if (Card.getNumCardsFacingDown() == 0){
                            updateHighScore();
                            CongratulationsGUI congratulations = new CongratulationsGUI();
                            congratulations.getYesButton().addActionListener(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent e){
                                    dispose();
                                }
                            });
                        }
                    }
                });
            } else{
                getGlassPane().setVisible(true);
                Timer timer = new Timer(1000, new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        cardToButton.get(card1).setEnabled(true);
                        cardToButton.get(card2).setEnabled(true);
                        resetDisplay();
                        getGlassPane().setVisible(false);
                    }
                });
                timer.setRepeats(false);
                timer.start();
                Card.setNumCardsFacingDown(2);
            }
            getContentPane().repaint();
            getContentPane().revalidate();
        }
    }


    private void resetCards(){
        ArrayList<Integer> emptyIndexes = new ArrayList<>();
        for (int i = 0; i < 10; i++){
            emptyIndexes.add(i);
        }

        String unavailableBrainrot = "";
        for (int i = 0; i < 5; i++){
            int choice = -1;
            while (choice < 0 || unavailableBrainrot.contains(String.valueOf(choice))){
                choice = (int)(Math.random() * 7);
            }
            unavailableBrainrot += choice;

            for (int j = 0; j < 2; j++){
                int index = (int)(Math.random() * emptyIndexes.size());
                cards[emptyIndexes.get(index) / 5][emptyIndexes.get(index) % 5] = new Card(brainrot[choice][0], brainrot[choice][1], brainrot[choice][2], brainrot[choice][3]);
                emptyIndexes.remove(index);
            }
        }
    }

    private void resetDisplay(){
        numFlipsLabel.setText(String.valueOf(numFlips));
        cardsLeftLabel.setText(String.valueOf(Card.getNumCardsFacingDown()));
    }

    private void resetButtons(){
        for (int i = 0; i < 2; i++){
            for (int j = 0; j < 5; j++){
                ImageIcon cardFront = new ImageIcon(getClass().getResource(cards[i][j].getPNG()));
                cardBTN[i][j].setDisabledIcon(cardFront);
                cardToButton.put(cards[i][j], cardBTN[i][j]);
                cardBTN[i][j].setEnabled(true);
                cardBTN[i][j].setVisible(true);
            }
        }
    }

    private int findBTNIndex(JButton button){
        for (int i = 0; i < cardBTN.length; i++){
            for (int j = 0; j < cardBTN[0].length; j++){
                if (button.equals(cardBTN[i][j])){
                    return i * 5 + j;
                }
            }
        }
        return -1;
    }

    private void updateHighScore(){
        if (numFlips < WelcomeScreen.getHighScore() || WelcomeScreen.getHighScore() == 0){
            WelcomeScreen.setHighScore(numFlips);
        }
    }
}