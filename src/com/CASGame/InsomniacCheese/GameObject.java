package com.CASGame.InsomniacCheese;

import com.CASGame.InsomniacCheese.Enemies.BasicEnemy;

import java.awt.*;
import java.io.Serializable;

public abstract class GameObject implements Serializable {

    protected float x, y;
    protected transient ID id;
    protected float velX, velY;
    protected int width, height;
    protected transient BasicEnemy type;

    public GameObject(float x, float y, ID id, int width, int height, BasicEnemy type) {
        this.x = x;
        this.y = y;
        this.id = id;
        this.width = width;
        this.height = height;
        this.type = type;
    }

    public abstract void tick();
    public abstract void render(Graphics g);
    public abstract Rectangle getBounds();

    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public void setID(ID id) {
        this.id = id;
    }

    public ID getID() {
        return id;
    }

    public void setVelX(float velX) {
        this.velX = velX;
    }

    public void setVelY(float velY) {
        this.velY = velY;
    }

    public float getVelX() {
        return velX;
    }

    public float getVelY() {
        return velY;
    }

    public BasicEnemy getType() {return type;}

    public void setType(BasicEnemy type) {this.type = type;}
}