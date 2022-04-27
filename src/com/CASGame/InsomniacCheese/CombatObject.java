package com.CASGame.InsomniacCheese;

import java.awt.*;

public abstract class CombatObject {
    protected float x, y, velX, velY;
    protected ID id;
    protected int width, height;
    protected int totalWill;
    protected int currentWill;
    protected int agility;
    protected int power;
    protected int lvl = 1;

    public CombatObject (float x, float y, ID id, int width, int height, int totalWill, int agility, int power) {
        this.totalWill = totalWill;
        this.currentWill = (int) (HUD.HEALTH*0.01*totalWill);
        this.agility = agility;
        this.power = power;
        this.x = x;
        this.y = y;
        this.id = id;
        this.width = width;
        this.height = height;
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

    public int getTotalWill() {
        return this.totalWill;
    }
    public int getCurrentWill() {
        return this.currentWill;
    }
    public int getAgility() {
        return this.agility;
    }
    public int getPower() {
        return this.power;
    }

    public int setLvl() {
        return this.lvl;
    }

    public void setTotalWill(int totalWill) {
        this.totalWill = totalWill;
    }
    public void setCurrentWill(int currentWill) {
        this.currentWill = currentWill;
    }
    public void setAgility(int agility) {
        this.agility = agility;
    }
    public void setPower(int power) {
        this.power = power;
    }

    public void setLvl(int lvl) {
        this.lvl = lvl;
    }

}
