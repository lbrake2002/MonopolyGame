package cosa.models;

import javafx.scene.paint.Color;

public enum PlayerColor {
    GREEN(Color.GREEN), RED(Color.RED), BLUE(Color.BLUE), PURPLE(Color.PURPLE);

    public final Color COLOR;

    PlayerColor(Color color) {
        this.COLOR = color;
    }
}
