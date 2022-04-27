package com.CASGame.InsomniacCheese.Attacks;

import com.CASGame.InsomniacCheese.Combat;
import com.CASGame.InsomniacCheese.Game;
import com.CASGame.InsomniacCheese.HUD;
import com.CASGame.InsomniacCheese.NPCs.StandardNPC;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

import static com.CASGame.InsomniacCheese.Game.HEIGHT;
import static com.CASGame.InsomniacCheese.Game.WIDTH;

public class Sound extends BasicAttack{

    private final Combat combat;
    private final Game game;

    public String[] chords = {"c", "d", "e", "f", "g", "a", "b"};
    public ArrayList<String> savedKeysPiano = new ArrayList<String>();
    public boolean stuffssent = false;
    public boolean cho = true;
    private final Font fnt = new Font("arial", Font.BOLD, 25);

    public Sound(Combat combat, Game game) {
        this.combat = combat;
        this.game = game;
    }

    public void tick() {
        combat.atkState = Combat.ATTACK_STATE.Music;

        if (!cho) {
            chords = combat.randomize(chords);
            cho = true;
        }

        if (stuffssent) {
            ArrayList<String> notesInChord;
            switch (chords[0]) {
                case "c":
                    notesInChord = new ArrayList<>();
                    notesInChord.add("c");
                    notesInChord.add("e");
                    notesInChord.add("g");
                    break;
                case "d":
                    notesInChord = new ArrayList<>();
                    notesInChord.add("d");
                    notesInChord.add("F");
                    notesInChord.add("a");
                    break;
                case "e":
                    notesInChord = new ArrayList<>();
                    notesInChord.add("e");
                    notesInChord.add("G");
                    notesInChord.add("b");
                    break;
                case "f":
                    notesInChord = new ArrayList<>();
                    notesInChord.add("f");
                    notesInChord.add("a");
                    notesInChord.add("c");
                    break;
                case "g":
                    notesInChord = new ArrayList<>();
                    notesInChord.add("d");
                    notesInChord.add("g");
                    notesInChord.add("b");
                    break;
                case "a":
                    notesInChord = new ArrayList<>();
                    notesInChord.add("a");
                    notesInChord.add("C");
                    notesInChord.add("e");
                    break;
                case "b":
                    notesInChord = new ArrayList<>();
                    notesInChord.add("b");
                    notesInChord.add("D");
                    notesInChord.add("F");
                    break;
                default:
                    System.out.println("chords[0]" + Arrays.toString(chords));
                    notesInChord = new ArrayList<>();
                    notesInChord.add("c");
                    notesInChord.add("e");
                    notesInChord.add("g");
                    break;
            }
            if (!combat.done) {
                if (combat.equalLists(savedKeysPiano, notesInChord)) {
                    combat.dmgD.dealDamage(combat.enemy, 2*combat.player.getPower(), 100, 0);
                } else {
                    combat.dmgD.dealDamage(combat.enemy, (int) Math.ceil(0.5*combat.player.getPower()), 100, 0);
                    combat.dmgD.dealDamage(combat.player, (int) Math.floor(0.5*combat.player.getPower()), 100, 0);
                }
                combat.done = true;
            }
        }
    }

    public void render(Graphics g) {
        //display text above
        g.setColor(Color.YELLOW);
        g.fillRect(0, 0, WIDTH, (int) (HEIGHT*0.12));
        g.setColor(Color.BLACK);
        g.setFont(fnt);
        g.drawString("Type the notes of the " + chords[0] + " chord", (int) (WIDTH*0.2), (int) (HEIGHT*0.083));
        g.drawString("Keys: ", (int) (WIDTH * 0.125), (int) (HEIGHT * 0.82));

        if (savedKeysPiano.size() == 0) {
            g.drawString(" ", (int) (WIDTH * 0.325), (int) (HEIGHT * 0.82));
            g.drawString(" ", (int) (WIDTH * 0.525), (int) (HEIGHT * 0.82));
            g.drawString(" ", (int) (WIDTH * 0.725), (int) (HEIGHT * 0.82));
        } else if (savedKeysPiano.size() == 1) {
            g.drawString(savedKeysPiano.get(0), (int) (WIDTH * 0.325), (int) (HEIGHT * 0.82));
            g.drawString(" ", (int) (WIDTH * 0.525), (int) (HEIGHT * 0.82));
            g.drawString(" ", (int) (WIDTH * 0.725), (int) (HEIGHT * 0.82));
        } else if (savedKeysPiano.size() == 2) {
            g.drawString(savedKeysPiano.get(0), (int) (WIDTH * 0.325), (int) (HEIGHT * 0.82));
            g.drawString(savedKeysPiano.get(1), (int) (WIDTH * 0.525), (int) (HEIGHT * 0.82));
            g.drawString(" ", (int) (WIDTH * 0.725), (int) (HEIGHT * 0.82));
        } else if (savedKeysPiano.size() == 3) {
            g.drawString(savedKeysPiano.get(0), (int) (WIDTH * 0.325), (int) (HEIGHT * 0.82));
            g.drawString(savedKeysPiano.get(1), (int) (WIDTH * 0.525), (int) (HEIGHT * 0.82));
            g.drawString(savedKeysPiano.get(2), (int) (WIDTH * 0.725), (int) (HEIGHT * 0.82));
        }

        //render enemy
        ((StandardNPC) combat.enemy).render2(g);
    }

    public void resetValues() {
        stuffssent = false;
        savedKeysPiano.clear();
    }
}
