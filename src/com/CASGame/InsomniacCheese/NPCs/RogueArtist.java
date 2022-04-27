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

public class RogueArtist extends StandardNPC{

    private static final long serialVersionUID = 9113607390255528715L;
    public String string = "";
    private final int lvl, will, agility, power, intelligence;
    //public boolean reasoned = false; breaks the game

    public RogueArtist(float x, float y, ID id, int width, int height, String direction, Combat combat, CombatHandler combatHandler, BufferedImage sprite, String string, String string2) {//type of attack of this = type of attack of chaos golem
        super(x, y, id, width, height, direction, new type2((float) (WIDTH*0.5)-64, (float) -64, ID.Interact, 16, 16, combat, combatHandler, 600, 10, 90), string, 1, 500, 30, 70, 40, false, (byte) 4, sprite, string2);

        this.lvl = 1;
        this.will = 500;
        this.agility = 30;
        this.power = 70;
        this.intelligence = 40;
    }

    public void tick() {

    }

    public Rectangle getSelf() {
        if (!defeated) {
            return new Rectangle((int) x+width*6, (int) y+width*6, width*7, height*7);
        } else {
            return new Rectangle((int) x, (int) y, width, height);
        }
    }
}
