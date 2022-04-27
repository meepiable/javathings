package com.CASGame.InsomniacCheese;

import com.CASGame.InsomniacCheese.NPCs.ChaosGolem;
import com.CASGame.InsomniacCheese.NPCs.KilnHardenedGolem;
import com.CASGame.InsomniacCheese.NPCs.LivingStatue;
import com.CASGame.InsomniacCheese.NPCs.StandardNPC;

public class DamageDealer {

    private final Combat combat;
    private final Game game;

    public DamageDealer(Combat combat, Game game) {
        this.combat = combat;
        this.game = game;
    }

    //dmgType list: 0 (magic), 1 (normal), 2 (physical?), etc

    public void dealDamage(GameObject target, int dmg, int accuracy, int dmgType) {
        if (((StandardNPC) target).getLOE().contains("Ward") && (dmgType == 0 || dmgType == 1)) {
            dmg /= 2;
        }

        if (((StandardNPC) target).getLOE().contains("Resistance") && dmgType == 0) {
            dmg /= 2;
        }

        if (((StandardNPC) target).getLOE().contains("Vulnerable") && dmgType == 0) {
            dmg *= 2;
        }

        if (((CombatPlayer) combat.player).getLOE().contains("Stun")) {
            accuracy = 0;
        }
        //resistances, immunities, weaknesses
        //0: TestNPC, 1: ClayGolem, 2: KilnHardenedGolem, 3: LivingStatue, 4: RogueArtist, 5: ChaosGolem, 6: IronGolem, 7: OmniSoldier
        switch (((StandardNPC) target).getCode()) {
            case 0:
                if (dmgType == 0) dmg *= 2;
                else if (dmgType == 1) dmg /= 2;
                break;
            case 2:
                if (dmgType == 0) dmg *= 2;
                else if (dmgType == 1) dmg /= 2;
                break;
            case 3:
                if (dmgType == 1) dmg*=2;
                break;
            case 4:
                if (dmgType == 1) dmg /= 2;
                break;
            case 5:
                if (dmgType == 1 || dmgType == 0) dmg/=2;
                break;
            case 6:
                if (dmgType == 0 || dmgType == 1) dmg/=2;
                break;
        }

        if (game.probSim(accuracy)) target.getType().setCurrentWill(target.getType().getCurrentWill()-dmg);
    }

    public void dealDamage(CombatObject target, int dmg, int accuracy, int dmgType) {
        if (((CombatPlayer) target).getLOE().contains("Ward") && (dmgType == 0 || dmgType == 1)) {
            dmg /= 2;
        }

        if (((CombatPlayer) target).getLOE().contains("Resistance") && dmgType == 0) {
            dmg /= 2;
        }

        if (((CombatPlayer) target).getLOE().contains("Vulnerable") && dmgType == 0) {
            dmg *= 2;
        }

        if (((StandardNPC) combat.enemy).getLOE().contains("Stun")) {
            accuracy = 0;
        }

        //dmg player


        if (combat.ibl) {
            dmg += 30;
            combat.ibl = false;
        }

        if (combat.shadowArts) {
            accuracy -= 50;
            combat.shadowArts = false;
        }

        if (game.probSim(accuracy)) {
            target.currentWill = target.getCurrentWill() - dmg;
            //secondary effects

            if (combat.enemy instanceof KilnHardenedGolem) ((CombatPlayer) target).addLOE("Burn");
            else if (combat.enemy instanceof LivingStatue) target.setAgility(target.getAgility()-3);
        }
    }
}
