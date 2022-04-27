package com.CASGame.InsomniacCheese.Attacks;

import com.CASGame.InsomniacCheese.Combat;
import com.CASGame.InsomniacCheese.Game;

import java.awt.*;

public class ThunderousMelody extends BasicAttack{
    private final Game game;
    private final Combat combat;
    private boolean done = false;

    public ThunderousMelody(Game game, Combat combat) {
        this.game = game;
        this.combat = combat;
    }

    public void tick() {
        if (!done) {
            combat.atkState = Combat.ATTACK_STATE.Thunder;
            combat.dmgD.dealDamage(combat.enemy, 300, 100, 0);
            done = true;
        }
    }

    public void render(Graphics g) {
        //render animation here
        if (!combat.done) {
            //play animation
            //if animation done,
            combat.done = true;
        }
    }

    public void resetValues() {

    }
}
