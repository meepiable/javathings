package com.CASGame.InsomniacCheese.NPCs;

import com.CASGame.InsomniacCheese.Enemies.BasicEnemy;
import com.CASGame.InsomniacCheese.ID;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Objects;

import com.CASGame.InsomniacCheese.Enemies.*;
import com.CASGame.InsomniacCheese.*;

import static com.CASGame.InsomniacCheese.Game.HEIGHT;
import static com.CASGame.InsomniacCheese.Game.WIDTH;

public class ClayGolem extends StandardNPC{

    public String string = "";
    private final int lvl, will, agility, power, intelligence;
    private final boolean reasoned;

    public ClayGolem(float x, float y, ID id, int width, int height, String direction, Combat combat, CombatHandler combatHandler, BufferedImage sprite, String string, String string2, boolean bool) {
        super(x, y, id, width, height, direction, new type2((float) (WIDTH*0.5)-64, (float) -64, ID.Interact, 16, 16, combat, combatHandler, 300, 15, 85), string, 1, 300, 15, 85, 0, bool, (byte) 1, sprite, string2);
        this.string = "Get pummelled!";
        this.lvl = 1;
        this.will = 300;
        this.agility = 15;
        this.power = 85;
        this.intelligence = 0;
        this.reasoned = false;
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
