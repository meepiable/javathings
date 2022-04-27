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

public class LivingStatue extends StandardNPC{

    public String string = "";
    private final int lvl, will, agility, power, intelligence;
    private final boolean reasoned = false;

    public LivingStatue(float x, float y, ID id, int width, int height, String direction, Combat combat, CombatHandler combatHandler, BufferedImage sprite, String string, String string2) {
        super(x, y, id, width, height, direction, new type2((float) (WIDTH*0.5)-64, (float) -64, ID.Interact, 16, 16, combat, combatHandler, 250, 10, 70), string, 1, 250, 10, 70, 30, false, (byte) 3, sprite, string2);
        this.lvl = 1;
        this.will = 250;
        this.agility = 10;
        this.power = 70;
        this.intelligence = 30;
    }

    public void tick() {

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
