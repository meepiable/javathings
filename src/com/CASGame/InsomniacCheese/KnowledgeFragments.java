package com.CASGame.InsomniacCheese;

import com.CASGame.InsomniacCheese.Enemies.BasicEnemy;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.Serializable;

import static com.CASGame.InsomniacCheese.GUI.renderTextBox;
import static com.CASGame.InsomniacCheese.GUI.textActive;

public class KnowledgeFragments extends GameObject implements Serializable{
    private transient final BufferedImage sprite;
    private boolean h = false;
    private final Handler handler;
    public boolean collected = false;

    public KnowledgeFragments(float x, float y, ID id, int width, int height, BufferedImage sprite, Handler handler) {
        super(x, y, id, width, height, null);
        this.sprite = sprite;
        this.handler = handler;
    }

    public void tick() {

    }

    public void render(Graphics g) {
        if (collected) return;
        g.drawImage(sprite, (int) x, (int) y, null);
        if (Game.user.getBounds().intersects(getBounds())) {
            if (Game.user.getInventory().containsKey("Knowledge Fragment")) {
                if (!h) {
                    textActive = true;
                    h = true;
                }
                if (textActive) textActive = !renderTextBox(g, "This is a knowledge fragment. You can use these to buy artifacts, or  to boost your stats.");
            } else {
                if (!h) {
                    textActive = true;
                    h = true;
                }
                if (textActive) textActive = !renderTextBox(g, "You got a Knowledge Fragment!");
            }
        }
        if (h && !textActive) {
            Game.user.editInventory("Knowledge Fragment", 1);
            collected = true;
            handler.removeObject(this);
        }
    }

    public Rectangle getBounds() {
        return new Rectangle((int) x, (int) y, width, height);
    }
}
