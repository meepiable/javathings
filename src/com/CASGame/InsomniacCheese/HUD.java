package com.CASGame.InsomniacCheese;

import java.awt.*;

import static com.CASGame.InsomniacCheese.Game.HEIGHT;

public class HUD {

    public static int HEALTH = 100; //percentage
    private int greenValue = 255;

    public static int HEALTH2 = 100; //percentage
    private int greenValue2 = 255;
    private final Combat combat;
    private final Font fnt = new Font("arial", Font.BOLD, 25);

    public HUD(Combat combat) {
        this.combat = combat;
    }

    public void tick(){
        greenValue = HEALTH * 2;
        greenValue2 = HEALTH2 * 2;

        combat.enemy.getType().setCurrentWill((int) ((HEALTH*0.01)*combat.enemy.getType().getTotalWill()));
        combat.player.currentWill = (int) ((HEALTH2*0.01)*combat.player.totalWill);
    }

    public void render(Graphics g) {
        if (combat.state != Combat.CURRENT_STATE.PlayerHappening){
            g.setFont(fnt);
            g.setColor(Color.GRAY);
            g.fillRect((int) (Game.WIDTH*0.0234), (int) (HEIGHT*0.1125), (int) (Game.WIDTH*0.3125), (int) (HEIGHT*0.0667));
            g.setColor(new Color(25, greenValue, 0));
            g.fillRect((int) (Game.WIDTH*0.0234), (int) (HEIGHT*0.1125), HEALTH * 2, (int) (HEIGHT*0.0667));
            g.setColor(Color.WHITE);
            g.drawRect((int) (Game.WIDTH*0.0234), (int) (HEIGHT*0.1125), (int) (Game.WIDTH*0.3125), (int) (HEIGHT*0.0667));

            g.setColor(Color.GRAY);
            g.fillRect((int) (Game.WIDTH*0.664), (int) (HEIGHT*0.1125), (int) (Game.WIDTH*0.3125), (int) (HEIGHT*0.0667));
            g.setColor(new Color(25, greenValue2, 0));
            g.fillRect((int) (Game.WIDTH*0.664), (int) (HEIGHT*0.1125), HEALTH2 * 2, (int) (HEIGHT*0.0667));
            g.setColor(Color.WHITE);
            g.drawRect((int) (Game.WIDTH*0.664), (int) (HEIGHT*0.1125), (int) (Game.WIDTH*0.3125), (int) (HEIGHT*0.0667));
            if (combat.state == Combat.CURRENT_STATE.Enemy) g.setColor(Color.BLACK);
            g.drawString("Enemy's Health"/* + displayEnemyHealthHere + "/" + displayTotalHealthHere*/, (int) (Game.WIDTH*0.0234), (int) (HEIGHT*0.095));
            g.drawString("Your Health"/* + displayPlayerHealthHere + "/" + displayTotalHealthHere*/, (int) (Game.WIDTH*0.7578), (int) (HEIGHT*0.095));
        }

    }

}
