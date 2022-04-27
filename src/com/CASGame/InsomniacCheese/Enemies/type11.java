package com.CASGame.InsomniacCheese.Enemies;

import com.CASGame.InsomniacCheese.*;

import java.awt.*;
import java.util.Objects;
import java.util.Random;

public class type11 extends BasicEnemy {

    private final Combat combat;
    private final CombatHandler combatHandler;
    private String dir;

    public type11(float x, float y, ID id, int width, int height, Combat combat, String dir, CombatHandler combatHandler, int power) {
        super(x, y, id, width, height, 0, 0, power);

        this.combat = combat;
        this.dir = dir;
        this.combatHandler = combatHandler;

        Random r = new Random();
        if (Objects.equals(dir, "north")) {
            velX = r.nextInt(5 - (-5)) + (-5);
            velY = 5;
        } else if (Objects.equals(dir, "east")) {
            velX = 5;
            velY = r.nextInt(5 - (-5)) + (-5);
        } else if (Objects.equals(dir, "south")) {
            velX = r.nextInt(5 - (-5)) + (-5);
            velY = -5;
        } else {
            velX = -5;
            velY = r.nextInt(5 - (-5)) + (-5);
        }
    }

    public void tick() {
        x += velX*2;
        y += velY*2;

        if (y <= 0) combatHandler.removeObject(this);
        if (x >= Game.WIDTH) combatHandler.removeObject(this);
        if (y >= Game.HEIGHT) combatHandler.removeObject(this);
        if (x <= 0) combatHandler.removeObject(this);

        combatHandler.addObject(new Trail(x, y, ID.Interact, Color.RED, width, height, 0.05f, combatHandler));
    }

    public void render(Graphics g) {
        g.setColor(Color.RED);
        g.fillRect((int) x, (int) y, width, height);
    }

    public void resetValues() {
        //do nothing
    }

    public Rectangle getBounds() {
        return new Rectangle((int) x, (int) y, width, height);
    }
}
