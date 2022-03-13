package com.example.snakegamefinalpart2;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Random;

public class HelloApplication extends Application {

    private static final int WIDTH = 400;
    private static final int HIHGT = 300;
    private static final int RADIUS = 5;


    private Text score;
    private Pane root;
    //Food
    private Circle food;
    private Random random;
    private SnakeBody snake;


    private boolean GameEnding() {
        return snake.deathByBody();


    }



    private void newFood() {
        //so the food won't spawn outside of the desired window
        food = new Circle(random.nextInt(WIDTH), random.nextInt(HIHGT), RADIUS);
        food.setFill(Color.YELLOW);
        root.getChildren().add(food);


    }

    private void newSnake() {
        snake = new SnakeBody(WIDTH / 2, HIHGT / 2, RADIUS + 2);
        root.getChildren().add(snake);
        snake.setFill(Color.GREEN);
        for (int i = 0; i < 25; i++) {
            newFood();
            snake.snakeGrow(food);
        }


    }

    private void Location() {
        if (snake.getCenterX() < 0) {
            snake.setCenterX(WIDTH);
        } else if (snake.getCenterX() > WIDTH) {
            snake.setCenterX(0);
        }
        if (snake.getCenterY() < 0) {
            snake.setCenterY(HIHGT);
        } else if (snake.getCenterY() > HIHGT) {
            snake.setCenterY(0);
        }


    }
//    private void step() {
//        if (dierection == Movements.UP) {
//            snake.setCenterY(snake.getCenterY() - STEP);
//        } else if (dierection == Movements.DOWN) {
//            snake.setCenterY(snake.getCenterY() + STEP);
//        } else if (dierection == Movements.RIGHT) {
//            snake.setCenterX(snake.getCenterX() + STEP);
//        } else if (dierection == Movements.LEFT) {
//            snake.setCenterX(snake.getCenterX() - STEP);
//        }
//
//    }


    private void Movement() {
        Platform.runLater(() -> {
            snake.step();
            Location();
            if (eating()) {
                snake.snakeGrow(food);
//                score.setText("" + snake.getLength());

                newFood();
            } else if (GameEnding()) {
                root.getChildren().clear();
                root.getChildren().add(score);
                score.setText("YOU DIED :(" + snake.getLength());
                newFood();
                newSnake();

            }

        });


    }

    private boolean eating() {
        return food.intersects(snake.getBoundsInLocal());
    }

    @Override
    public void start(Stage ps) {


        root = new Pane();
        root.setPrefSize(WIDTH, HIHGT);
        random = new Random();
//        dierection = Movements.UP;
        score = new Text(0, 32, "0");
        newSnake();

        Runnable r = () -> {
            try {
                for (; ; ) {
                    Movement();
                    Thread.sleep(100 / (1 + snake.getLength() / 10));
                }
            } catch (InterruptedException ie) {
            }

        };


        Scene scene = new Scene(root);
        scene.addEventFilter(KeyEvent.KEY_PRESSED, keyEvent -> {
            KeyCode code = keyEvent.getCode();
            if (code == KeyCode.UP) {
                snake.setDierection(Movements.UP);

            } else if (code == KeyCode.DOWN) {
                snake.setDierection(Movements.DOWN);

            } else if (code == KeyCode.RIGHT) {
                snake.setDierection(Movements.RIGHT);

            } else if (code == KeyCode.LEFT) {
                snake.setDierection(Movements.LEFT);
            }

        });

        ps.setTitle("Beware The Snake");
        ps.setScene(scene);
        ps.show();
        ps.setFullScreen(false);
        ps.setResizable(false);
        scene.setFill(Color.RED);
        Thread th = new Thread(r);
        th.setDaemon(true);
        th.start();


    }

    public static void main(String[] args) {
        launch();
    }
}
