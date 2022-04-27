package com.CASGame.InsomniacCheese.Enemies;

import com.CASGame.InsomniacCheese.CombatHandler;
import com.CASGame.InsomniacCheese.Game;
import com.CASGame.InsomniacCheese.ID;

import java.awt.*;
import java.util.Random;

public class type3 extends BasicEnemy{
    private final float originX;
    private final float originY;
    private final CombatHandler combatHandler;
    private Random r = new Random();
    private int lol = 0;
    private int timer = 0;

    public type3(float x, float y, ID id, int width, int height, CombatHandler combatHandler, int totalWill, int agility, int power) {
        super(x, y, id, width, height, totalWill, agility, power);
        this.originX = x;
        this.originY = y;

        this.combatHandler = combatHandler;
    }

    public void tick() {
        x += velX*2;
        y += velY*2;
        timer++;

        int spawn = r.nextInt(100);

        if (spawn == 0 && lol < 101) {
            combatHandler.addObject(new type33(x, y, ID.Interact, 16, 16, combatHandler, power));
            lol++;
        }

        if (timer%500 == 0) {

        }
    }

    public void render(Graphics g) {
        g.setColor(Color.BLUE);
        g.fillRect((int) x, (int) y, width, height);
    }

    public void resetValues() {
        x = originX;
        y = originY;
        velX = 0;
        velY = 0;
        timer = 0;
        lol = 0;
    }

    public Rectangle getBounds() {
        return new Rectangle((int) x, (int) y, width, height);
    }
}
