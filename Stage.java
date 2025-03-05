import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;

public class Stage extends JFrame {
    int width = 400;
    int height = 600;
    String title = "Number Guessing Game";

    private JLabel scoreLabel, lifeLabel;
    private JTextField inputField;
    private JButton submitButton;
    private JPanel buttonPanel;
    private JButton[] numButtons = new JButton[10];
    private NumberGuessingLogic gameLogic;
    private AnimatedBackgroundPanel backgroundPanel;

    public Stage() {
        this.setTitle(title);
        this.setSize(width, height);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setResizable(false);

        backgroundPanel = new AnimatedBackgroundPanel();
        backgroundPanel.setLayout(null);
        this.setContentPane(backgroundPanel);

        scoreLabel = createLabel("Score: 0", 20, 20, 150, 30);
        backgroundPanel.add(scoreLabel);

        lifeLabel = createLabel("Lives: 10", 250, 20, 150, 30);
        backgroundPanel.add(lifeLabel);

        inputField = new JTextField();
        inputField.setBounds(130, 70, 120, 40);
        inputField.setFont(new Font("Arial", Font.BOLD, 20));
        inputField.setHorizontalAlignment(JTextField.CENTER);
        inputField.setBorder(BorderFactory.createLineBorder(Color.CYAN, 2));
        backgroundPanel.add(inputField);

        buttonPanel = new JPanel();
        buttonPanel.setBounds(50, 130, 300, 200);
        buttonPanel.setLayout(new GridLayout(4, 3, 10, 10));
        buttonPanel.setOpaque(false);
        backgroundPanel.add(buttonPanel);

        for (int i = 0; i < 10; i++) {
            numButtons[i] = createButton(String.valueOf(i));
            buttonPanel.add(numButtons[i]);
            final int number = i;
            numButtons[i].addActionListener(e -> inputField.setText(inputField.getText() + number));
        }

        submitButton = createButton("Submit");
        submitButton.setBounds(130, 350, 120, 40);
        backgroundPanel.add(submitButton);

        gameLogic = new NumberGuessingLogic(inputField, scoreLabel, lifeLabel, backgroundPanel);
        submitButton.addActionListener(gameLogic.getSubmitAction());

        this.setVisible(true);
    }

    private JLabel createLabel(String text, int x, int y, int width, int height) {
        JLabel label = new JLabel(text);
        label.setForeground(Color.WHITE);
        label.setFont(new Font("Arial", Font.BOLD, 18));
        label.setBounds(x, y, width, height);
        return label;
    }

    private JButton createButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 18));
        button.setForeground(Color.WHITE);
        button.setBackground(new Color(50, 50, 50));
        button.setBorder(BorderFactory.createLineBorder(Color.CYAN, 2));
        button.setFocusPainted(false);
        return button;
    }

    private class AnimatedBackgroundPanel extends JPanel {
        private ArrayList<FallingNumber> fallingNumbers = new ArrayList<>();
        private Random random = new Random();
        private int targetNumber = -1; // Default invalid number

        public AnimatedBackgroundPanel() {
            setLayout(null);
            setBackground(Color.BLACK);
            setDoubleBuffered(true);

            Timer timer = new Timer(50, e -> {
                for (FallingNumber num : fallingNumbers) {
                    num.y += num.speed;
                    if (num.y > height) {
                        num.y = -random.nextInt(100);
                        num.x = random.nextInt(width);
                        num.value = getRandomNumberExcluding(targetNumber);
                    }
                }
                repaint();
            });

            Timer addNumberTimer = new Timer(1000, e -> {
                if (fallingNumbers.size() < 15) {
                    int value = getRandomNumberExcluding(targetNumber);
                    fallingNumbers.add(new FallingNumber(value, random.nextInt(width), -random.nextInt(100), random.nextInt(3) + 2));
                }
            });

            timer.start();
            addNumberTimer.start();
        }

        private int getRandomNumberExcluding(int exclude) {
            int num;
            do {
                num = random.nextInt(10);
            } while (num == exclude);
            return num;
        }

        public void setTargetNumber(int newTarget) {
            this.targetNumber = newTarget;
            // Update falling numbers to remove the target number
            fallingNumbers.removeIf(num -> num.value == targetNumber);
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            g2d.setFont(new Font("Arial", Font.BOLD, 30));
            g2d.setColor(Color.CYAN);

            for (FallingNumber num : fallingNumbers) {
                g2d.drawString(String.valueOf(num.value), num.x, num.y);
            }
        }
    }

    private class FallingNumber {
        int value;
        int x, y;
        int speed;

        public FallingNumber(int value, int x, int y, int speed) {
            this.value = value;
            this.x = x;
            this.y = y;
            this.speed = speed;
        }
    }

        class NumberGuessingLogic {
        private int targetNumber;
        private int score;
        private int lives;
        private JTextField inputField;
        private JLabel scoreLabel;
        private JLabel lifeLabel;
        private Random random;
        private Stage.AnimatedBackgroundPanel backgroundPanel;

        public NumberGuessingLogic(JTextField inputField, JLabel scoreLabel, JLabel lifeLabel, Stage.AnimatedBackgroundPanel backgroundPanel) {
            this.inputField = inputField;
            this.scoreLabel = scoreLabel;
            this.lifeLabel = lifeLabel;
            this.backgroundPanel = backgroundPanel;
            this.random = new Random();
            
            this.lives = 10;
            this.score = 0;
            generateNewTarget();
        }

        private void generateNewTarget() {
            targetNumber = random.nextInt(10);  // Generate a new target (0-9)
            backgroundPanel.setTargetNumber(targetNumber);  // Remove correct number from animation
        }

        public ActionListener getSubmitAction() {
            return new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String inputText = inputField.getText();
                    if (inputText.isEmpty()) {
                        JOptionPane.showMessageDialog(null, "Please enter a number!");
                        return;
                    }

                    try {
                        int guessedNumber = Integer.parseInt(inputText);
                        
                        if (guessedNumber == targetNumber) {
                            score += 10;
                            JOptionPane.showMessageDialog(null, "Correct! You get 10 points.");
                            generateNewTarget();  
                        } else {
                            lives--;
                            JOptionPane.showMessageDialog(null, "Wrong guess! Try again.");
                        }

                        scoreLabel.setText("Score: " + score);
                        lifeLabel.setText("Lives: " + lives);
                        inputField.setText("");

                        if (lives <= 0) {
                            JOptionPane.showMessageDialog(null, "Game Over! Your final score: " + score);
                            resetGame();
                        }

                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(null, "Invalid input! Enter a valid number.");
                    }
                }
            };
        }

        private void resetGame() {
            score = 0;
            lives = 10;
            generateNewTarget();
            scoreLabel.setText("Score: 0");
            lifeLabel.setText("Lives: 10");
        }
    }

}
