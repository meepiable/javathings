package com.CASGame.InsomniacCheese;

import com.CASGame.InsomniacCheese.Enemies.BasicEnemy;

import java.awt.*;
import java.awt.image.BufferedImage;

import static com.CASGame.InsomniacCheese.GUI.renderTextBox;
import static com.CASGame.InsomniacCheese.GUI.textActive;
public class Book extends GameObject{

    private final String string;
    private boolean h = false;
    private transient BufferedImage sprite;
    private final Handler handler;
    private boolean read = false;

    public Book(float x, float y, ID id, int width, int height, String string, BufferedImage sprite, Handler handler) {
        super(x, y, id, width, height, null);
        this.string = string;
        this.sprite = sprite;
        this.handler = handler;
    }

    public void tick() {

    }

    public void render(Graphics g) {
        if (read) return;
        g.drawImage(sprite, (int) x, (int) y, null);
        if (getBounds().intersects(Game.user.getBounds()) && !read) {
            if (!h) {
                textActive = true;
                h = true;
            }
            if (textActive) textActive = !renderTextBox(g, string);
        }

        if (h && !textActive) {
            read = true;
            handler.removeObject(this);
        }
    }

    public Rectangle getBounds() {
        return new Rectangle((int) x, (int) y, width, height);
    }
}
