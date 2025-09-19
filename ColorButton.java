import java.awt.*;

public class ColorButton {
    private Button button;
    private Color normalColor;
    private String name;

    public ColorButton(String name, Color normalColor) {
        this.name = name;
        this.normalColor = normalColor;
        button = new Button();
        button.setBackground(normalColor);
        button.setPreferredSize(new Dimension(120, 120));
        button.setFont(new Font("Arial", Font.BOLD, 16));
        button.setLabel("");
    }

    public Button getButton() {
        return button;
    }

    public String getName() {
        return name;
    }

    public void flash() {
        Color oldColor = button.getBackground();
        button.setBackground(Color.WHITE);
        try {
            Thread.sleep(250);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        button.setBackground(oldColor);
    }
}
