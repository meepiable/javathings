package com.CASGame.InsomniacCheese.Attacks;

import com.CASGame.InsomniacCheese.Combat;

import java.awt.*;

public class ShadowArts extends BasicAttack{

    private final Combat combat;
    private boolean done = false;

    public ShadowArts(Combat combat) {
        this.combat = combat;
    }

    public void tick() {
        if (done) {
            combat.atkState = Combat.ATTACK_STATE.Shadow;
            //-50% chance of hitting from original accuracy
            combat.shadowArts = true;
            //heal 50
            combat.dmgD.dealDamage(combat.player, -50, 100, 0);
            done = true;
        }
    }

    public void render(Graphics g) {
        if (!combat.done) {
            //play animation
            //if animation done,
            combat.done = true;
        }
    }

    public void resetValues() {

    }
}
