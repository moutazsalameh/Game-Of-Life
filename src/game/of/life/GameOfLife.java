/*
 *  Developed by András Ács (acsandras@gmail.com)
 *  Erhvervsakademi Sjælland / www.easj.dk
 *  Licensed under the WFTPL License - http://www.wtfpl.net/txt/copying/ 
 *  2017
 *
 *  Opgavebeskrivelse: https://goo.gl/qwDeHu 
 *
 */
package game.of.life;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.StrokeType;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 *
 * @author andrasacs
 */
public class GameOfLife extends Application {

    private Cell[][] matrix;
    private Label label;
    private Game world;
    private Pane root;

    @Override
    public void start(Stage primaryStage) {

        Pane root = new Pane();

        Game world = Game.intialize(); // Creating a world for our germs
        matrix = world.getArrayH(); // Getting the world array
        updateScene(matrix, root); // Displaying the initial state of the world

        Scene scene = new Scene(root, 640, 480);

        // Declaring a button event handler
        Button btn = new Button();
        btn.setText("100 iterations");
        btn.setOnAction((ActionEvent event) -> {
        // Setting up a timer
            Timeline timeline = new Timeline(new KeyFrame(
                    Duration.millis(300),
                    ae -> evolve(world, root)));
            timeline.setCycleCount(100);
            timeline.play();

        });

        // Displaying an iteration counter
        label = new Label("0");
        label.setLayoutX(200);
        root.getChildren().add(label);
        root.getChildren().add(btn);

        // The usual JavaFX scene-scetting code
        primaryStage.setTitle("Game of life");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void evolve(Game world, Pane root) {

        matrix = world.getArrayH();
        world.update();
        updateScene(matrix, root);
        String iterations = String.valueOf(world.getnIteration());
        label.setText(iterations);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    public static void updateScene(Cell[][] matrix, Pane root) {
        root.getChildren().removeAll(); // Removing all circles, to draw a new scene

        int yMax = matrix.length;
        int xMax = matrix[0].length;
        for (int x = 0; x < xMax; x++) {
            for (int y = 0; y < yMax; y++) {
                double xVal = x * 20 + 20;
                double yVal = y * 20 + 50;

                // Red circle for living cells,
                // white circles for not living
                Paint color;
                if (matrix[x][y].isAlive()) {
                    color = Paint.valueOf("RED");
                } else {
                    color = Paint.valueOf("WHITE");
                }

                Circle circ = new Circle(xVal, yVal, 6, color);

                if (matrix[x][y].isAlive()) {
                    circ.setStrokeType(StrokeType.OUTSIDE);
                    circ.setStroke(Color.web("red", 1));
                    circ.setStrokeWidth(2);
                } else {
                    circ.setStrokeType(StrokeType.OUTSIDE);
                    circ.setStroke(Color.web("black", 1));
                    circ.setStrokeWidth(2);
                }

                // Tooltip for showing cell info
                String toolTip = matrix[x][y].toString();
                Tooltip.install(
                        circ,
                        new Tooltip(toolTip)
                );
                root.getChildren().add(circ);
                //DEBUG System.out.println("X: " + xVal + "Y: " + yVal + " Obj: " + circ.toString());

            }
        }
    }

}
