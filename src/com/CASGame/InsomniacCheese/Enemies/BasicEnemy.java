package com.CASGame.InsomniacCheese.Enemies;

import com.CASGame.InsomniacCheese.CombatObject;
import com.CASGame.InsomniacCheese.GameObject;
import com.CASGame.InsomniacCheese.ID;

import java.awt.*;

public abstract class BasicEnemy extends CombatObject {

    public BasicEnemy(float x, float y, ID id, int width, int height, int totalWill, int agility, int power) {
        super(x, y, id, width, height, totalWill, agility, power);
    }

    public abstract void tick();

    public abstract void render(Graphics g);

    public abstract void resetValues();

    public abstract Rectangle getBounds();
}
