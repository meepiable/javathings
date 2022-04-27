package com.CASGame.InsomniacCheese.Enemies;


import com.CASGame.InsomniacCheese.*;

import java.awt.*;
import java.util.Random;

//spatial
public class type1 extends BasicEnemy {

    private final Combat combat;
    private final CombatHandler combatHandler;
    private final Random r = new Random();
    private int timer = 0;
    public int stage = 1;
    private final float originX;
    private final float originY;

    public type1(float x, float y, ID id, int width, int height, Combat combat, CombatHandler combatHandler, int totalWill, int agility, int power) {
        super(x, y, id, width, height, totalWill, agility, power);

        this.originX = x;
        this.originY = y;
        this.combat = combat;
        this.combatHandler = combatHandler;

        velX = 10;
        velY = 0;
    }

    public void tick() {
        x += velX*2;
        y += velY*2;

        timer++;
        if (timer%(500) == 0) {
            if (velX == 10) {
                velY = 10;
                velX = 0;
            } else if (velY == 10){
                velY = 0;
                velX = -10;
            } else if (velX == -10) {
                velY = -10;
                velX = 0;
            } else if (velY == -10){
                velY = 0;
                velX = 10;
            }
            stage++;
        }


        if (x <= 0 || x >= Game.WIDTH - 96) velX *= -1;
        if (y <= 0 || y >= Game.HEIGHT - 96) velY *= -1;

        Random r = new Random();
        int spawn = r.nextInt(10);
        int spawn2 = r.nextInt(10);
        String dir;
        String dir2;
        if (velX == 0) {
            dir = "east";
            dir2 = "west";
        } else {
            dir = "north";
            dir2 = "south";
        }

        if (spawn == 0)
            combatHandler.addObject(new type11(x, y, ID.Interact, 16, 16, combat, dir, combatHandler, power));

        if (spawn2 == 0)
            combatHandler.addObject(new type11(x, y, ID.Interact, 16, 16, combat, dir2, combatHandler, power));
    }

    public void render(Graphics g) {
        if (stage%2 != 0) g.setColor(Color.BLUE);
        else g.setColor(Color.RED);
        g.fillRect((int) x, (int) y, width, height);
    }

    public void resetValues() {
        x = originX;
        y = originY;

        stage = 1;
        timer = 0;
        velX = 10;
        velY = 0;
    }

    public Rectangle getBounds() {
        return new Rectangle((int) x, (int) y, width, height);
    }
}
