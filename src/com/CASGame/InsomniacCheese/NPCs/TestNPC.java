package com.CASGame.InsomniacCheese.NPCs;

import com.CASGame.InsomniacCheese.*;
import com.CASGame.InsomniacCheese.Enemies.BasicEnemy;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Objects;

import static com.CASGame.InsomniacCheese.GUI.renderTextBox;

public class TestNPC extends StandardNPC {

    private String direction;
    public boolean defeated = false;
    private final Handler handler;

    public TestNPC(float x, float y, ID id, int width, int height, String direction, BasicEnemy type, int lvl, int will, int agility, int power, int intelligence, Handler handler, BufferedImage sprite) {
        super(x, y, id, width, height, direction, type, "test", lvl, will, agility, power, intelligence, true, (byte) 0, sprite, null);
        this.handler = handler;
    }

    public void tick() {

    }

    public Rectangle getBounds() {
        if (!defeated) {
            if (Objects.equals(direction, "north")) return new Rectangle((int) x, (int) y - (height * 6), width, (height * 7));
            else if (Objects.equals(direction, "east")) return new Rectangle((int) x, (int) y, width * 6, height);
            else if (Objects.equals(direction, "south")) return new Rectangle((int) x, (int) y, width, height * 6);
            else return new Rectangle((int) (x - (width * 6)), (int) y, (width * 7), height);
        } else {
            return new Rectangle((int) x, (int) y, width, height);
        }
    }

    public Rectangle getSelf() {
        return new Rectangle((int) x, (int) y, width, height);
    }

    public BasicEnemy getType() {
        return this.type;
    }

    public void setType(BasicEnemy type) {
        this.type = type;
    }
}
