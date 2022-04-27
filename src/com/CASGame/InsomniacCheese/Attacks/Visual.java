package com.CASGame.InsomniacCheese.Attacks;

import com.CASGame.InsomniacCheese.Combat;
import com.CASGame.InsomniacCheese.Game;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

import static com.CASGame.InsomniacCheese.Game.HEIGHT;
import static com.CASGame.InsomniacCheese.Game.WIDTH;

public class Visual extends BasicAttack{

    private final Combat combat;
    private final Game game;

    public HashMap<Color, String> hm = new HashMap<>();
    public Color[] sec = {Color.RED, Color.ORANGE, Color.YELLOW, Color.GREEN, Color.BLUE, Color.MAGENTA, Color.BLACK};
    public ArrayList<String> clickedColors = new ArrayList<>();
    public boolean stuffsssent = false;
    public boolean cho2 = true;
    private final Font fnt = new Font("arial", Font.BOLD, 20);

    public Visual(Combat combat, Game game) {
        this.combat = combat;
        this.game = game;

        hm.put(Color.RED, "RED");
        hm.put(Color.ORANGE, "ORANGE");
        hm.put(Color.YELLOW, "YELLOW");
        hm.put(Color.GREEN, "GREEN");
        hm.put(Color.BLUE, "BLUE");
        hm.put(Color.MAGENTA, "MAGENTA");
        hm.put(Color.BLACK, "BLACK");
    }

    public void tick() {
        combat.atkState = Combat.ATTACK_STATE.VA;


        if (!cho2){
            combat.randomizeCol(sec);
            cho2 = true;
        }

        if (stuffsssent) {
            ArrayList<String> colors = new ArrayList<>();
            switch(hm.get(sec[0])) {
                case "RED":
                    colors.add("RED");
                    break;
                case "ORANGE":
                    colors.add("RED");
                    colors.add("YELLOW");
                    break;
                case "YELLOW":
                    colors.add("YELLOW");
                    break;
                case "GREEN":
                    colors.add("YELLOW");
                    colors.add("BLUE");
                    break;
                case "BLUE":
                    colors.add("BLUE");
                    break;
                case "MAGENTA":
                    colors.add("BLUE");
                    colors.add("RED");
                    break;
                case "BLACK": //all primary colors
                default:
                    colors.add("RED");
                    colors.add("BLUE");
                    colors.add("YELLOW");
                    break;
            }



            if (combat.equalLists(clickedColors, colors)) combat.dmgD.dealDamage(combat.enemy, 2*Game.user.getPower(), 100, 0);;
            combat.done = true;
        }
    }

    public void render(Graphics g) {
        g.setFont(fnt);
        g.setColor(Color.WHITE);
        g.fillRect((int) (WIDTH * 0.05), (int) (HEIGHT * 0.72), (int) (WIDTH * 0.8), 100);
        g.drawRect(0, 0, WIDTH, (int) (HEIGHT*0.12)+1);

        g.setColor(sec[0]);
        g.fillRect(0, 0, WIDTH, (int) (HEIGHT*0.12));

        g.setColor(Color.BLACK);
        g.drawString("Select a Color Combination: ", (int) (WIDTH * 0.05), (int) (HEIGHT * 0.82));

        if (clickedColors.size() == 0) {
            g.drawString(" ", (int) (WIDTH * 0.325), (int) (HEIGHT * 0.82));
            g.drawString(" ", (int) (WIDTH * 0.525), (int) (HEIGHT * 0.82));
            g.drawString(" ", (int) (WIDTH * 0.625), (int) (HEIGHT * 0.82));
        } else if (clickedColors.size() == 1) {
            g.drawString(clickedColors.get(0), (int) (WIDTH * 0.425), (int) (HEIGHT * 0.82));
            g.drawString(" ", (int) (WIDTH * 0.525), (int) (HEIGHT * 0.82));
            g.drawString(" ", (int) (WIDTH * 0.625), (int) (HEIGHT * 0.82));
        } else if (clickedColors.size() == 2) {
            g.drawString(clickedColors.get(0), (int) (WIDTH * 0.425), (int) (HEIGHT * 0.82));
            g.drawString(clickedColors.get(1), (int) (WIDTH * 0.525), (int) (HEIGHT * 0.82));
            g.drawString(" ", (int) (WIDTH * 0.625), (int) (HEIGHT * 0.82));
        } else if (clickedColors.size() == 3) {
            g.drawString(clickedColors.get(0), (int) (WIDTH * 0.425), (int) (HEIGHT * 0.82));
            g.drawString(clickedColors.get(1), (int) (WIDTH * 0.525), (int) (HEIGHT * 0.82));
            g.drawString(clickedColors.get(2), (int) (WIDTH * 0.625), (int) (HEIGHT * 0.82));
        }

        g.setColor(Color.RED);
        g.fillRect((int) (WIDTH * 0.32), (int) (HEIGHT * 0.45), (int) (HEIGHT*0.172), (int) (HEIGHT*0.104));

        g.setColor(Color.YELLOW);
        g.fillRect((int) (WIDTH * 0.52), (int) (HEIGHT * 0.45), (int) (HEIGHT*0.172), (int) (HEIGHT*0.104));

        g.setColor(Color.BLUE);
        g.fillRect((int) (WIDTH * 0.72), (int) (HEIGHT * 0.45), (int) (HEIGHT*0.172), (int) (HEIGHT*0.104));
    }

    public void resetValues() {
        stuffsssent = false;
        clickedColors.clear();
    }
}
