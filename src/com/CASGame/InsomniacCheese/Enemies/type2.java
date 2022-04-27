package com.CASGame.InsomniacCheese.Enemies;

import com.CASGame.InsomniacCheese.*;

import java.awt.*;
import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class type2 extends BasicEnemy{
    //summons for 10 rounds, then goes down for 10 seconds
    private Combat combat;
    private Random random = new Random();
    private int timer = 0;
    private int timer2 = 0;
    int timer3 = 0;
    private CombatHandler combatHandler;

    public boolean[] thing = new boolean[4];
    private final float originX, originY;

    public type2(float x, float y, ID id, int width, int height, Combat combat, CombatHandler combatHandler, int totalWill, int agility, int power) {
        super(x, y, id, width, height, totalWill, agility, power);
        this.combat = combat;
        this.combatHandler = combatHandler;
        this.originX = x;
        this.originY = y;

        thing[0] = true;
        thing[1] = true;
        thing[2] = true;
        thing[3] = false;

        velX = 0;
        velY = 0;
    }

    public void tick() {

        x += velX*2;
        y += velY*2;

        if (timer2/(500) == 1 && timer2 != 0) {
            thing[0] = false;
            thing[1] = false;
            thing[2] = false;
            thing[3] = false;

            velY = 1;
            if (timer3/50 == 1 && timer3 != 0) {
                velY = 5;
                velX = 5;
                timer2 = 0;
            } else timer3++;
        }

        if (timer3%25 == 0 && timer3 != 0) {
            combatHandler.addObject(new Trail(x, y, ID.Interact, Color.BLACK, width, height, (float) 0.1, combatHandler));
            if (x <= 0 || x >= Game.WIDTH - width) velX *= -1;
            if (y <= 0 || y >= Game.HEIGHT - height) velY *= -1;

            Random r = new Random();
            int spawn = r.nextInt(10);
            String dir;
            if (velX == 0) {
                if (x < Game.WIDTH*0.5) {
                    dir = "east";
                } else {
                    dir = "west";
                }
            } else {
                if (y < Game.HEIGHT*0.5) {
                    dir = "north";
                } else {
                    dir = "south";
                }
            }

            if (spawn == 0) combatHandler.addObject(new type11(x, y, ID.Interact, 16, 16, combat, dir, combatHandler, power));
        }

        if (timer/50 == 1) {
            timer = 0;
        }

        if (timer == 0) {
            shuffleArray(thing);
            for (int i = 0; i < thing.length; i++) {
                boolean temp = thing[i];
                CombatObject ene;
                if (temp) {
                    switch (i) {
                        case 0:
                            ene = new type22(0, 0, ID.Interact, 160, 32, combat, combatHandler, power);
                            break;
                        case 1:
                            ene = new type22(160, 0, ID.Interact, 160, 32, combat, combatHandler, power);
                            break;
                        case 2:
                            ene = new type22(320, 0, ID.Interact, 160, 32, combat, combatHandler, power);
                            break;
                        case 3:
                            ene = new type22(480, 0, ID.Interact, 160, 32, combat, combatHandler, power);
                            break;
                        default:
                            System.out.println("lol");
                            ene = new type22(0, 0, ID.Interact, 160, 32, combat, combatHandler, power);
                    }
                    combatHandler.addObject(ene);
                }
            }
        }

        if (timer2%1000 != 0 || timer2 == 0) {
            timer++;
            timer2++;
        }

    }

    public void resetValues() {
        x = originX;
        y = originY;
        velX = 0;
        velY = 0;
        timer = 0;
        timer2 = 0;
        timer3 = 0;
        thing[0] = true;
        thing[1] = true;
        thing[2] = true;
        thing[3] = false;
    }

    public void render(Graphics g) {
        g.setColor(Color.BLACK);
        g.fillRect((int) x, (int) y, width, height);
    }

    public Rectangle getBounds() {
        return new Rectangle((int) x, (int) y, width, height);
    }

    static void shuffleArray(boolean[] ar) {
        Random rnd = ThreadLocalRandom.current();
        for (int i = ar.length - 1; i > 0; i--) {
            int index = rnd.nextInt(i + 1);
            // Simple swap
            boolean a = ar[index];
            ar[index] = ar[i];
            ar[i] = a;
        }
    }
}
