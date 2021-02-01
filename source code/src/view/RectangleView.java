package view;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import java.util.Observable;

public class RectangleView implements View {
    private Rectangle rectangle;
    private double layoutX;
    private double layoutY;
    private Color color;

    public RectangleView(Pane layout, int width, int height) {
        this.rectangle = new Rectangle(width, height, Color.BLUE);
        layout.getChildren().add(rectangle);
    }

    @Override
    public void draw() {
        this.rectangle.setLayoutX(layoutX);
        this.rectangle.setLayoutY(layoutY);
        this.rectangle.setFill(color);
        this.rectangle.setStroke(Color.BLACK);

    }

    public void hide(){
        this.rectangle.setVisible(false);
    }
    public void show(){
        this.rectangle.setVisible(true);
    }
    public Rectangle getRectangle(){
        return rectangle;
    }

    @Override
    public void update(Observable o, Object arg) {
        this.draw();
    }

    public void setData(Double layoutX, Double layoutY, Color color) {
        this.layoutX = layoutX;
        this.layoutY = layoutY;
        this.color = color;
    }
    public void setData(Double layoutX, Double layoutY) {
        this.layoutX = layoutX;
        this.layoutY = layoutY;
    }
}
