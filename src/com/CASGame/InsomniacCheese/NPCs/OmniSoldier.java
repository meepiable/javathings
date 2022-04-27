package com.CASGame.InsomniacCheese.NPCs;

import com.CASGame.InsomniacCheese.Combat;
import com.CASGame.InsomniacCheese.CombatHandler;
import com.CASGame.InsomniacCheese.Enemies.BasicEnemy;
import com.CASGame.InsomniacCheese.Enemies.type2;
import com.CASGame.InsomniacCheese.ID;

import java.awt.*;
import java.awt.image.BufferedImage;

import static com.CASGame.InsomniacCheese.Game.WIDTH;

public class OmniSoldier extends StandardNPC{

    public String string = "";
    private final int lvl, will, agility, power, intelligence;
    private final boolean reasoned = false;

    public OmniSoldier(float x, float y, ID id, int width, int height, String direction, Combat combat, CombatHandler combatHandler, BufferedImage sprite) {//WHAT DOES WEB MEAN???
        super(x, y, id, width, height, direction, new type2((float) (WIDTH*0.5)-64, (float) -64, ID.Interact, 16, 16, combat, combatHandler, 500, 40, 100), "TRUST IN THE PROCESS, YOU ARE BUT A MERE MORTAL", 1, 500, 40, 100, 50, false, (byte) 7, sprite, null);

        this.lvl = 1;
        this.will = 500;
        this.agility = 40;
        this.power = 100;
        this.intelligence = 50;
    }

    public void tick() {

    }

    public void render(Graphics g) {

    }

    public void render2(Graphics g) {

    }

    public Rectangle getSelf() {
        return null;
    }
}
