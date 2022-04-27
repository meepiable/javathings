package com.CASGame.InsomniacCheese.Attacks;

import com.CASGame.InsomniacCheese.Combat;
import com.CASGame.InsomniacCheese.Game;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import static com.CASGame.InsomniacCheese.Game.HEIGHT;
import static com.CASGame.InsomniacCheese.Game.WIDTH;

public class Spacial extends BasicAttack{

    private final Combat combat;
    private final Game game;

    public boolean stuffssssent = false;
    public boolean[] clickedStages = {false, false, false, false, false, false, false, false, false};
    boolean[] correctDirect = {false, false, false, false, false, false, false, false, false};

    public boolean cho3 = false;
    private final Font fnt = new Font("arial", Font.BOLD, 11);

    public Spacial(Combat combat, Game game) {
        this.combat = combat;
        this.game = game;
        this.description = "hi";
    }

    public boolean[] randomize(boolean[] arr) {
        // Start from the last element and swap one by one. We don't
        // need to run for the first element that's why i > 0
        for (int i = (arr.length)-1; i > 0; i--) {

            // Pick a random index from 0 to i
            int j = game.r.nextInt(i+1);

            // Swap arr[i] with the element at random index
            boolean temp = arr[i];
            arr[i] = arr[j];
            arr[j] = temp;
        }

        return arr;
    }

    public boolean equalLists(boolean[] one, boolean[] two){
        if (one == null && two == null){
            return true;
        }

        if((one == null && two != null) || one != null && two == null || one.length != two.length){
            return false;
        }

        //to avoid messing the order of the lists we will use a copy
        int[] onetest = new int[one.length];
        for (int i = 0; i < one.length; i++) {
            if (one[i])
                onetest[i] = 1;
            else
                onetest[i] = 0;
        }


        int[] twotest = new int[two.length];
        for (int i = 0; i < two.length; i++) {
            if (two[i])
                twotest[i] = 1;
            else
                twotest[i] = 0;
        }

        Arrays.sort(onetest);
        Arrays.sort(twotest);
        return onetest.equals(twotest);
    }

    public void tick() {
        combat.atkState = Combat.ATTACK_STATE.Theatre;

        if (!cho3) {
            for (int i = game.r.nextInt(5)+1; i > 0; i--) correctDirect[i] = true;
            correctDirect = randomize(correctDirect);
            cho3 = true;
        }


        if (stuffssssent) {
            if (Arrays.equals(clickedStages, correctDirect)) {
                //deal dmg
                combat.dmgD.dealDamage(combat.enemy, 500, 100, 0);
            }

            combat.done = true;
        }
    }

    public void render(Graphics g) {
        g.setFont(fnt);

        g.setColor(Color.YELLOW);
        g.fillRect(0, 0, WIDTH, (int) (HEIGHT*0.12));
        g.fillRect(0, (int)(HEIGHT*0.80), WIDTH, (int) (HEIGHT*0.12));
        String chosen = "";


        if (clickedStages[0]) chosen = "Front stage left";
        if (clickedStages[1]) chosen += " Front stage center";
        if (clickedStages[2]) chosen += " Front stage right";
        if (clickedStages[3]) chosen += " Center stage left";
        if (clickedStages[4]) chosen += " Center stage";
        if (clickedStages[5]) chosen += " Center stage right";
        if (clickedStages[6]) chosen += " Back stage left";
        if (clickedStages[7]) chosen += " Back stage center";
        if (clickedStages[8]) chosen += " Back stage right";

        //g.fillRect((int) (Game.WIDTH*0.25), (int) (Game.HEIGHT*0.25), (int) (Game.WIDTH*0.5*0.33333333), (int) (Game.HEIGHT*0.5*0.33333333));


        g.fillRect((int) (Game.WIDTH*0.25), (int) (Game.HEIGHT*0.25), (int) (Game.WIDTH*0.5), (int) (Game.HEIGHT*0.5));

        g.setColor(Color.BLACK);
        g.drawString(chosen, (int) (WIDTH*0.1), (int) (HEIGHT*0.893));
        g.drawString("Type following directions", (int) (WIDTH*0.2), (int) (HEIGHT*0.073));

        String directions = "";

        if (correctDirect[0]) directions = "Front stage left";
        if (correctDirect[1]) directions += " Front stage center";
        if (correctDirect[2]) directions += " Front stage right";
        if (correctDirect[3]) directions += " Center stage left";
        if (correctDirect[4]) directions += " Center stage";
        if (correctDirect[5]) directions += " Center stage right";
        if (correctDirect[6]) directions += " Back stage left";
        if (correctDirect[7]) directions += " Back stage center";
        if (correctDirect[8]) directions += " Back stage right";

        g.drawString(directions, (int) (WIDTH*0.1), (int) (HEIGHT*0.093));



        g.drawRect((int) (Game.WIDTH*0.25), (int) (Game.HEIGHT*0.25), (int) (Game.WIDTH*0.5*0.33333333), (int) (Game.HEIGHT*0.5*0.33333333));
        g.drawString("Front Stage Left", (int) (Game.WIDTH*0.25+1), (int) (Game.HEIGHT*0.25+(Game.HEIGHT*0.5*0.33333333)/3));

        g.drawRect((int) (Game.WIDTH*0.25+(Game.WIDTH*0.5*0.33333333)), (int) (Game.HEIGHT*0.25), (int) (Game.WIDTH*0.5*0.33333333), (int) (Game.HEIGHT*0.5*0.33333333));
        g.drawString("Front Stage Center", (int) (Game.WIDTH*0.25+(Game.WIDTH*0.5*0.33333333)+1), (int) (Game.HEIGHT*0.25+(Game.HEIGHT*0.5*0.33333333)/3));

        g.drawRect((int) (Game.WIDTH*0.25+((Game.WIDTH*0.5*0.33333333)*2)), (int) (Game.HEIGHT*0.25), (int) (Game.WIDTH*0.5*0.33333333), (int) (Game.HEIGHT*0.5*0.33333333));
        g.drawString("Front Stage Right", (int) (Game.WIDTH*0.25+((Game.WIDTH*0.5*0.33333333)*2)+1), (int) (Game.HEIGHT*0.25+(Game.HEIGHT*0.5*0.33333333)/3));

        g.drawRect((int) (Game.WIDTH*0.25), (int) (Game.HEIGHT*0.25+(Game.HEIGHT*0.5*0.33333333)), (int) (Game.WIDTH*0.5*0.33333333), (int) (Game.HEIGHT*0.5*0.33333333));
        g.drawString("Center Stage Left", (int) (Game.WIDTH*0.25+1), (int) ((Game.HEIGHT*0.25+(Game.HEIGHT*0.5*0.33333333))+(Game.HEIGHT*0.5*0.33333333)/3));

        g.drawRect((int) (Game.WIDTH*0.25+(Game.WIDTH*0.5*0.33333333)), (int) (Game.HEIGHT*0.25+(Game.HEIGHT*0.5*0.33333333)), (int) (Game.WIDTH*0.5*0.33333333), (int) (Game.HEIGHT*0.5*0.33333333));
        g.drawString("Center Stage", (int) (Game.WIDTH*0.25+(Game.WIDTH*0.5*0.33333333)+1), (int) (Game.HEIGHT*0.25+(Game.HEIGHT*0.5*0.33333333)+(Game.HEIGHT*0.5*0.33333333)/3));

        g.drawRect((int) (Game.WIDTH*0.25+((Game.WIDTH*0.5*0.33333333)*2)), (int) (Game.HEIGHT*0.25+(Game.HEIGHT*0.5*0.33333333)), (int) (Game.WIDTH*0.5*0.33333333), (int) (Game.HEIGHT*0.5*0.33333333));
        g.drawString("Center Stage Right", (int) (Game.WIDTH*0.25+(Game.WIDTH*0.5*0.33333333*2)+1), (int) (Game.HEIGHT*0.25+(Game.HEIGHT*0.5*0.33333333)+(Game.HEIGHT*0.5*0.33333333)/3));

        g.drawRect((int) (Game.WIDTH*0.25), (int) (Game.HEIGHT*0.25+(Game.HEIGHT*0.5*0.33333333)*2), (int) (Game.WIDTH*0.5*0.33333333), (int) (Game.HEIGHT*0.5*0.33333333));
        g.drawString("Back Stage Left", (int) (Game.WIDTH*0.25+1), (int) (Game.HEIGHT*0.25+(Game.HEIGHT*0.5*0.33333333*2)+(Game.HEIGHT*0.5*0.33333333)/3));

        g.drawRect((int) (Game.WIDTH*0.25+(Game.WIDTH*0.5*0.33333333)), (int) (Game.HEIGHT*0.25+(Game.HEIGHT*0.5*0.33333333)*2), (int) (Game.WIDTH*0.5*0.33333333), (int) (Game.HEIGHT*0.5*0.33333333));
        g.drawString("Back Stage Center", (int) (Game.WIDTH*0.25+(Game.WIDTH*0.5*0.33333333)+1), (int) (Game.HEIGHT*0.25+(Game.HEIGHT*0.5*0.33333333*2)+(Game.HEIGHT*0.5*0.33333333)/3));

        g.drawRect((int) (Game.WIDTH*0.25+((Game.WIDTH*0.5*0.33333333)*2)), (int) (Game.HEIGHT*0.25+(Game.HEIGHT*0.5*0.33333333)*2), (int) (Game.WIDTH*0.5*0.33333333), (int) (Game.HEIGHT*0.5*0.33333333));
        g.drawString("Back Stage Right", (int) (Game.WIDTH*0.25+(Game.WIDTH*0.5*0.33333333*2)+1), (int) (Game.HEIGHT*0.25+(Game.HEIGHT*0.5*0.33333333*2)+(Game.HEIGHT*0.5*0.33333333)/3));
    }

    public void resetValues() {
        cho3 = false;
        stuffssssent = false;
        Arrays.fill(correctDirect, false);
        Arrays.fill(clickedStages, false);
    }
}
