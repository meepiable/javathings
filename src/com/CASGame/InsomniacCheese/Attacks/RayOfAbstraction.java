package com.CASGame.InsomniacCheese.Attacks;

import com.CASGame.InsomniacCheese.Combat;
import com.CASGame.InsomniacCheese.Game;
import com.CASGame.InsomniacCheese.NPCs.StandardNPC;

import java.awt.*;

public class RayOfAbstraction extends BasicAttack{

    private final Combat combat;
    private final Game game;
    private boolean done = false;

    private String[] listOfBuffs = new String[5];
    private String[] listOfDebuffs  = new String[5];

    private String[] addedEffects = new String[2];

    public RayOfAbstraction(Combat combat, Game game) {
        this.combat = combat;
        this.game = game;

        listOfBuffs[0] = "Agility";
        listOfBuffs[1] = "Strength";
        listOfBuffs[2] = "Resistance";
        listOfBuffs[3] = "Ward";
        listOfBuffs[4] = "Regeneration";

        listOfDebuffs[0] = "Cripple";
        listOfDebuffs[1] = "Drain";
        listOfDebuffs[2] = "Vulnerable";
        listOfDebuffs[3] = "Burn";
        listOfDebuffs[4] = "Stun";

    }

    public void tick() {
        if (!done) {
            combat.atkState = Combat.ATTACK_STATE.Ray;
            if (game.probSim(50)) {
                if (game.probSim(50)) {
                    //buff
                    addedEffects[0] = listOfBuffs[game.r.nextInt(5)];
                    addedEffects[1] = listOfBuffs[game.r.nextInt(5)];
                } else {
                    //debuff
                    addedEffects[0] = listOfDebuffs[game.r.nextInt(5)];
                    addedEffects[1] = listOfDebuffs[game.r.nextInt(5)];
                }

                if (!addedEffects[0].equals(addedEffects[1])) {
                    ((StandardNPC) combat.enemy).addLOE(addedEffects[0]);
                    ((StandardNPC) combat.enemy).addLOE(addedEffects[1]);
                } else {
                    ((StandardNPC) combat.enemy).addLOE(addedEffects[0]);
                }
            }
            combat.dmgD.dealDamage(combat.enemy, 400, 100, 0);
            done = true;
        }
    }

    public void render(Graphics g) {
        if (!combat.done) {
            //play animation
            //when animation finish,
            combat.done = true;
        }
    }

    public void resetValues() {
        done = false;
        addedEffects[0] = "";
        addedEffects[1] = "";
    }
}
