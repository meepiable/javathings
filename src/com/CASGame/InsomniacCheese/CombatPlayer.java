package com.CASGame.InsomniacCheese;

import com.CASGame.InsomniacCheese.Enemies.*;

import java.awt.*;
import java.util.LinkedList;

public class CombatPlayer extends CombatObject{

    private final Combat combat;
    private final CombatHandler combatHandler;
    private final float originX, originY;
    private int lvl, will, agility, power, intelligence;
    private LinkedList<String> listOfEffects = new LinkedList<String>();

    public CombatPlayer(float x, float y, ID id, Combat combat, int width, int height, CombatHandler combatHandler, int totalWill, int agility, int power) {
        super(x, y, id, width, height, totalWill, agility, power);
        this.combat = combat;
        this.originX = x;
        this.originY = y;
        this.combatHandler = combatHandler;
    }


    private boolean done = false;
    public void tick() {

        x += velX*2;
        y += velY*2;

        x = Game.clamp(x, 0, Game.WIDTH-width);
        y = Game.clamp(y, 0, Game.HEIGHT-(width*2));

        //buffs and debuffs to player stats
        //maybe i'll set done to true on roundstart/playeraction
        //checks for if buff/debuff exist and does its effect if the turn is on player turn, then finishes




        collision();
    }

    public void render(Graphics g) {
        g.setColor(Color.GREEN);
        g.fillRect((int) x, (int) y, width, height);
    }

    public Rectangle getBounds() {
        return new Rectangle((int) x, (int) y, width, height);
    }

    private void collision() {
        for (int i = 0; i < combatHandler.object.size(); i++) {
            CombatObject temp = combatHandler.object.get(i);
            if (combat.state == Combat.CURRENT_STATE.Enemy) {
                if (temp instanceof type1 || temp instanceof type2) {
                    if (getBounds().intersects(temp.getBounds())) {
                        if (temp instanceof type1 && ((type1) temp).stage%2 == 0 && ((type1) temp).stage != 0) {
                            combat.whoStage++;
                            combat.state = Combat.CURRENT_STATE.RoundStart;
                            x = originX;
                            y = originY;
                            velX = 0;
                            velY = 0;
                            combatHandler.object.clear();
                        } else if (temp instanceof type2) {
                            combat.whoStage++;
                            combat.state = Combat.CURRENT_STATE.RoundStart;
                            x = originX;
                            y = originY;
                            velX = 0;
                            velY = 0;
                            combatHandler.object.clear();
                        }
                    }
                } else if (temp instanceof type11 || temp instanceof type22) {
                    if (getBounds().intersects(temp.getBounds())) {
                        combat.dmgD.dealDamage(combat.player, combat.enemy.getType().getPower(), 100, 0);
                        combatHandler.object.remove(temp);
                    }
                }
            }
        }
    }

    public int getIntelligence() {
        return this.intelligence;
    }

    public void setIntelligence(int intelligence) {
        this.intelligence = intelligence;
    }

    public LinkedList<String> getLOE() {
        return this.listOfEffects;
    }

    public void addLOE(String thing) {
        //add a key and map when new thing is added
        this.listOfEffects.add(thing);
        int countdown;
        switch (thing) {
            case "Agility":
            case "Strength":
            case "Regeneration":
            case "Cripple":
            case "Drain":
            case "Vulnerable":
            case "Burn":
                countdown = 2;
                break;
            case "Stun":
            case "Resistance":
            case "Ward":
            default:
                countdown = 1;
                break;
        }

        combat.effectCountdown.put(thing, countdown);
    }

    public void removeLOE(int index) {
        this.listOfEffects.remove(index);
        System.out.println("loe: " + ((CombatPlayer) combat.player).getLOE());
    }

    public void clearLOE() {
        this.listOfEffects.clear();
    }
}
