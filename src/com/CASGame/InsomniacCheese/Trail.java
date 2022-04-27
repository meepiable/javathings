package com.CASGame.InsomniacCheese;

import java.awt.*;

public class Trail extends CombatObject{

    private float alpha = 1;
    private final float life;

    private final CombatHandler combatHandler;
    private final Color color;

    private final int height, width;

    public Trail(float x, float y, ID id, Color color, int width, int height, float life, CombatHandler combatHandler) {
        super(x, y, id, width, height, 0,0, 0);
        this.combatHandler = combatHandler;
        this.color = color;
        this.width = width;
        this.height = height;
        this.life = life;
    }

    public void tick() {
        if (alpha > life) {
            alpha -= (life-0.00001f);
        } else combatHandler.removeObject(this);
    }

    public void render(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setComposite(makeTransparent(alpha));

        g.setColor(color);
        g.fillRect((int) x, (int) y, width, height);

        g2d.setComposite(makeTransparent(1));

    }

    private AlphaComposite makeTransparent(float alpha) {
        int type = AlphaComposite.SRC_OVER;
        return AlphaComposite.getInstance(type, alpha);
    }


    public Rectangle getBounds() {
        return null;
    }
}
