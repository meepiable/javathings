package com.CASGame.InsomniacCheese.NPCs;

import com.CASGame.InsomniacCheese.Combat;
import com.CASGame.InsomniacCheese.CombatHandler;
import com.CASGame.InsomniacCheese.Enemies.BasicEnemy;
import com.CASGame.InsomniacCheese.Enemies.type2;
import com.CASGame.InsomniacCheese.ID;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Objects;

import static com.CASGame.InsomniacCheese.Game.WIDTH;

public class IronGolem extends StandardNPC{ //this will be a pain to code, debug, and play against

    public String string = "";
    private final int lvl, will, agility, power, intelligence;
    private final boolean reasoned = false;

    public IronGolem(float x, float y, ID id, int width, int height, String direction, Combat combat, CombatHandler combatHandler, BufferedImage sprite) {
        super(x, y, id, width, height, direction, new type2((float) (WIDTH*0.5)-64, (float) -64, ID.Interact, 16, 16, combat, combatHandler, 1150, 20, 250), "was a pain to code, debug, and play against", 4, 1150, 20, 250, 19, false, (byte) 6, sprite, "null");
        /*Omni King’s Absorption (Ability) (3 turns): 80 magic damage, the target cannot use Magic next turn.
        Prosecution Laser (Ability) (Starts on cooldown, 4 turns): 200 damage,  the target becomes Vulnerable for two turns (take 100 extra damage from magic attack attacks). */

        this.lvl = 1;
        this.will = 1150;
        this.agility = 20;
        this.power = 250;
        this.intelligence = 19;
    }

    public void tick() {

    }

    public void render(Graphics g) {

    }

    public void render2(Graphics g) {

    }

    public Rectangle getSelf() {
        if (!defeated) {
            if (Objects.equals(direction, "north")) return new Rectangle((int) x, (int) y - (height * 6), width, (height * 7));
            else if (Objects.equals(direction, "east")) return new Rectangle((int) x, (int) y, width * 6, height);
            else if (Objects.equals(direction, "south")) return new Rectangle((int) x, (int) y, width, height * 6);
            else return new Rectangle((int) (x - (width * 6)), (int) y, (width * 7), height);
        } else {
            return new Rectangle((int) x, (int) y, width, height);
        }
    }
}
