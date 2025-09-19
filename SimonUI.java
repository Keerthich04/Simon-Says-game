import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class SimonUI extends Frame implements ActionListener {
    private ColorButton pinkButton, greenButton, blueButton, lavenderButton;
    private SimonGame game;
    private Label status;
    private final int MAX_ROUNDS = 10; // You can change this to increase challenge

    public SimonUI() {
        game = new SimonGame(MAX_ROUNDS);

        setTitle("Simon Says - Pastel Edition");
        setSize(350, 400);
        setBackground(Color.decode("#F7F7F7"));
        setLayout(new BorderLayout(10, 10));

        status = new Label("Watch the pattern!", Label.CENTER);
        status.setFont(new Font("Arial", Font.BOLD, 18));
        add(status, BorderLayout.NORTH);

        Panel buttonPanel = new Panel(new GridLayout(2, 2, 10, 10));

        pinkButton = new ColorButton("Pink", Color.decode("#FFB3BA"));
        greenButton = new ColorButton("Green", Color.decode("#BAFFC9"));
        blueButton = new ColorButton("Blue", Color.decode("#BAE1FF"));
        lavenderButton = new ColorButton("Lavender", Color.decode("#E3BAFF"));

        buttonPanel.add(pinkButton.getButton());
        buttonPanel.add(greenButton.getButton());
        buttonPanel.add(blueButton.getButton());
        buttonPanel.add(lavenderButton.getButton());

        add(buttonPanel, BorderLayout.CENTER);

        pinkButton.getButton().addActionListener(this);
        greenButton.getButton().addActionListener(this);
        blueButton.getButton().addActionListener(this);
        lavenderButton.getButton().addActionListener(this);

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });

        setVisible(true);
        playPattern();
    }

    private void playPattern() {
        status.setText("Watch carefully...");
        ArrayList<String> pattern = game.getPattern();
        new Thread(() -> {
            try {
                Thread.sleep(800);
                for (String color : pattern) {
                    flashButton(color);
                    Thread.sleep(300);
                }
                status.setText("Your turn!");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }

    private void flashButton(String color) {
        switch (color) {
            case "Pink": pinkButton.flash(); break;
            case "Green": greenButton.flash(); break;
            case "Blue": blueButton.flash(); break;
            case "Lavender": lavenderButton.flash(); break;
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String color = "";
        if (e.getSource() == pinkButton.getButton()) color = "Pink";
        else if (e.getSource() == greenButton.getButton()) color = "Green";
        else if (e.getSource() == blueButton.getButton()) color = "Blue";
        else if (e.getSource() == lavenderButton.getButton()) color = "Lavender";

        if (!game.checkMove(color)) {
            status.setText("❌ Wrong! Game Over!");
            disableButtons();
            showGameEndDialog(false);
            return;
        }

        if (game.isRoundComplete()) {
            if (game.isGameWon()) {
                status.setText("🎉 You Won All Rounds!");
                disableButtons();
                showGameEndDialog(true);
                return;
            }
            status.setText("✅ Good! Next Round...");
            game.addNextStep();
            playPattern();
        }
    }

    private void disableButtons() {
        pinkButton.getButton().setEnabled(false);
        greenButton.getButton().setEnabled(false);
        blueButton.getButton().setEnabled(false);
        lavenderButton.getButton().setEnabled(false);
    }

    private void enableButtons() {
        pinkButton.getButton().setEnabled(true);
        greenButton.getButton().setEnabled(true);
        blueButton.getButton().setEnabled(true);
        lavenderButton.getButton().setEnabled(true);
    }

    private void showGameEndDialog(boolean won) {
        String message = won ? "🎉 Congratulations! You won!" : "❌ Game Over! Wrong move.";
        int result = showConfirmDialog(message + "\n\nDo you want to play again?");
        if (result == 0) { // Yes
            game.resetGame();
            enableButtons();
            playPattern();
        } else {
            System.exit(0);
        }
    }

    private int showConfirmDialog(String msg) {
        Dialog dialog = new Dialog(this, "Game Result", true);
        dialog.setLayout(new BorderLayout(10, 10));

        Label messageLabel = new Label(msg, Label.CENTER);
        messageLabel.setFont(new Font("Arial", Font.BOLD, 14));
        dialog.add(messageLabel, BorderLayout.CENTER);

        Panel buttonPanel = new Panel();
        Button yes = new Button("Play Again");
        Button no = new Button("Exit");
        buttonPanel.add(yes);
        buttonPanel.add(no);
        dialog.add(buttonPanel, BorderLayout.SOUTH);

        final int[] result = {-1};
        yes.addActionListener(e -> { result[0] = 0; dialog.dispose(); });
        no.addActionListener(e -> { result[0] = 1; dialog.dispose(); });

        dialog.setSize(300, 150);
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);

        return result[0];
    }
}
