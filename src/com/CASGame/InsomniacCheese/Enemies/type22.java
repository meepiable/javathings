package com.CASGame.InsomniacCheese.Enemies;

import com.CASGame.InsomniacCheese.Combat;
import com.CASGame.InsomniacCheese.CombatHandler;
import com.CASGame.InsomniacCheese.Game;
import com.CASGame.InsomniacCheese.ID;

import java.awt.*;

public class type22 extends BasicEnemy{

    private Combat combat;
    private CombatHandler combatHandler;

    public type22(float x, float y, ID id, int width, int height, Combat combat, CombatHandler combatHandler, int power) {
        super(x, y, id, width, height, 0, 0, power);
        this.combat = combat;
        this.combatHandler = combatHandler;

        velX = 0;
        velY = 2;
    }

    public void tick() {
        x += velX*2;
        y += velY*2;

        if (y >= Game.HEIGHT+160) {
            combatHandler.removeObject(this);
        }
    }

    public void render(Graphics g) {
        g.setColor(Color.RED);
        g.fillRect((int) x, (int) y, 160, 32);
    }

    public void resetValues() {
        //do nothing
    }

    public Rectangle getBounds() {
        return new Rectangle((int) x, (int) y, 160, 32);
    }
}
