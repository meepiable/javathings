package com.CASGame.InsomniacCheese.Attacks;

import com.CASGame.InsomniacCheese.Combat;

import java.awt.*;

public class SonnetOfTheLost extends BasicAttack{

    private final Combat combat;
    private boolean done = false;

    public SonnetOfTheLost(Combat combat) {
        this.combat = combat;
    }

    public void tick() {
        if (!done) {
            combat.atkState = Combat.ATTACK_STATE.Sonnet;
            combat.dmgD.dealDamage(combat.enemy, 400, 100, 0);
            combat.sotl = true;
            done = true;
        }
    }

    public void render(Graphics g) {
        if (!combat.done) {
            //play animation
            combat.done = true;
        }
    }

    public void resetValues() {
        done = false;
    }
}
