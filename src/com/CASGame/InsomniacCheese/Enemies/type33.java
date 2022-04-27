package com.CASGame.InsomniacCheese.Enemies;

import com.CASGame.InsomniacCheese.*;

import java.awt.*;
import java.util.Random;

public class type33 extends BasicEnemy{

    private CombatObject player;
    private final CombatHandler combatHandler;
    private Random r = new Random();

    public type33(float x, float y, ID id, int width, int height, CombatHandler combatHandler, int power) {
        super(x, y, id, width, height, 0, 0, power);

        this.combatHandler = combatHandler;
        for (int i = 0; i < combatHandler.object.size(); i++) {
            if (combatHandler.object.get(i).getID() == ID.CombatPlayer) player = combatHandler.object.get(i);
        }
    }

    public Rectangle getBounds() {
        return new Rectangle((int) x, (int) y, 16, 16);
    }

    public void tick() {
        x += velX*2;
        y += velY*2;

        float diffX = x-player.getX() - 8;
        float diffY = y-player.getY() - 8;
        float distance = (float) Math.sqrt(((x-player.getX()) * (x-player.getX())) + ((y-player.getY()) * (y-player.getY())));

        velX = (float) (((-1.0/distance) * diffX)*(r.nextInt(6+1-2)+2));
        velY = (float) (((-1.0/distance) * diffY)*(r.nextInt(6+1-2)+2));

        if (y <= 0 || y >= Game.HEIGHT-32) velY *= -1;
        if (x <= 0 || x >= Game.WIDTH-16) velX *= -1;

        combatHandler.addObject(new Trail(x, y, ID.Interact, Color.BLACK, 16, 16, 0.05f, combatHandler));
    }

    public void render(Graphics g) {
        g.setColor(Color.BLACK);
        g.fillRect((int) x, (int) y, 16, 16);
    }

    public void resetValues() {
        //do nothing
    }
}
