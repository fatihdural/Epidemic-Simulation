package view;

import controller.MainController;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import model.Individual;
import model.Model;
import java.util.*;

public class CanvasView implements View {
    Map<Integer, View> components;
    MainController mainController;

    Pane layout;

    public CanvasView(MainController mainController, Pane layout){
        this.components = new HashMap<>();
        this.mainController = mainController;
        this.layout = layout;
        this.draw();
    }

    @Override
    public void update(Observable o, Object arg) {
        if( o instanceof Model){
            Model model = (Model) o;
            Map<Integer, Individual> individuals = model.getIndividuals();

            int index = (Integer) arg;
            if( index == -1 ){
                this.draw();
                return;
            }

            if( !(individuals.containsKey(index)) ){
                if( ((RectangleView) components.get(index)).getRectangle() != null ){
                    layout.getChildren().removeAll(((RectangleView) components.get(index)).getRectangle());
                    components.remove(index);

                }
                return;
            }

            Individual individual = individuals.get(index);

            if( individual.getSituation() == 'N' ){
                RectangleView newRectangle = new RectangleView(layout, 5, 5);
                newRectangle.setData( individual.getLayoutX(), individual.getLayoutY(), Color.BLUE);
                components.put(index, newRectangle);
                components.get(index).draw();
                ((RectangleView) components.get(index)).show();

            }
            else if( individual.getSituation() == 'H' ){
                RectangleView rectangleView = (RectangleView) components.get(index);
                rectangleView.setData( individual.getLayoutX(), individual.getLayoutY(), Color.BLUE);
                components.get(index).draw();
                ((RectangleView) components.get(index)).show();

            }
            else if( individual.getSituation() == 'I' ){
                RectangleView rectangleView = (RectangleView) components.get(index);
                rectangleView.setData(individual.getLayoutX(), individual.getLayoutY(), Color.RED );
                components.get(index).draw();
                ((RectangleView) components.get(index)).show();

            }
            else if( individual.getSituation() == 'W' ){
                RectangleView rectangleView = (RectangleView) components.get(index);
                rectangleView.setData(individual.getLayoutX(), individual.getLayoutY() );
                components.get(index).draw();
                ((RectangleView) components.get(index)).show();


            }
            else if( individual.getSituation() == 'U' ){
                RectangleView rectangleView = (RectangleView) components.get(index);
                rectangleView.setData(individual.getLayoutX(), individual.getLayoutY() );
                components.get(index).draw();

                ((RectangleView) components.get(index)).hide();
            }
            else if( individual.getSituation() == 'D' ){

                if( ((RectangleView) components.get(index)).getRectangle() != null ){
                    layout.getChildren().removeAll(((RectangleView) components.get(index)).getRectangle());
                    components.remove(index);
                }
            }
        }
    }

    @Override
    public void draw() {
        layout.setPrefWidth(1000);
        layout.setPrefSize(1000, 600);
        layout.setMaxSize(1000, 600);
        layout.setMinSize(1000, 600);
        layout.setStyle("-fx-background-color: #d4d3d3;");

        for(View view : components.values()){
            view.draw();
        }
    }
}
