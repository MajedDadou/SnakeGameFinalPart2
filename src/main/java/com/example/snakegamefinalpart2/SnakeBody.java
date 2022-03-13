package com.example.snakegamefinalpart2;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.util.ArrayList;
import java.util.List;

public class SnakeBody extends Circle {
    private List<Circle> tails;
    private int length = 0;
    private Movements dierection;

    //Game Speed
    private static final int STEP = 5;


    public SnakeBody(double v, double v1, double v2) {
        super(v, v1, v2);
        tails = new ArrayList<>();
        dierection = Movements.UP;

    }

    public void step() {
        for (int i = length - 1; i >= 0; i--) {
            if (i == 0) {
                tails.get(i).setCenterX(getCenterX());
                tails.get(i).setCenterY(getCenterY());

            } else {
                tails.get(i).setCenterX(tails.get(i - 1).getCenterX());
                tails.get(i).setCenterY(tails.get(i - 1).getCenterY());
            }
        }


        if (dierection == Movements.UP) {
            setCenterY(getCenterY() - STEP);
        } else if (dierection == Movements.DOWN) {
            setCenterY(getCenterY() + STEP);
        } else if (dierection == Movements.RIGHT) {
            setCenterX(getCenterX() + STEP);
        } else if (dierection == Movements.LEFT) {
            setCenterX(getCenterX() - STEP);
        }

    }

    public int getLength() {
        return length;
    }

    public Movements getDierection() {
        return dierection;
    }

    public void setDierection(Movements dierection) {
        this.dierection = dierection;
    }

    private Circle endTail() {
        if (length == 0) {
            return this;
        }

        return tails.get(length - 1);
    }

    public boolean deathByBody(){
        for (int i = 0; i<length;i++){
            if (this.getCenterX()==tails.get(i).getCenterX() && this.getCenterY()==tails.get(i).getCenterY()){
                return true;

            }
        }
        return false;
    }

    public void snakeGrow(Circle food) {
        Circle tail = endTail();
        food.setCenterX(tail.getCenterX());
        food.setCenterY(tail.getCenterY());
        food.setFill(Color.BLACK);
        tails.add(length++, food);


    }


}

