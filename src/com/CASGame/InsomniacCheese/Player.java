package com.CASGame.InsomniacCheese;

import com.CASGame.InsomniacCheese.NPCs.StandardNPC;
import com.CASGame.InsomniacCheese.NPCs.TestNPC;

import java.awt.*;
import java.io.Serializable;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import static com.CASGame.InsomniacCheese.GUI.*;
import static com.CASGame.InsomniacCheese.Game.*;

public class Player extends GameObject implements Serializable {

    private transient static final long serialVersionUID = 4020289980958696963L;
    private transient final Handler handler;
    private transient final Game game;
    private transient final Combat combat;
    String[] atkInventory = new String[4];
    String[] splInventory = new String[4];
    HashMap<String, Integer> inventory = new HashMap<>();

    private int moral, will, agility, power, intelligence, hp;
    private transient final int baseWill, baseAgility, basePower;
    int[] reason = new int[3];
    private int killCount = 0;
    private int reasonCount = 0;
    private transient boolean updateMoral = false;

    private transient boolean test = false;

    public Player(float x, float y, ID id, Handler handler, Game game, int width, int height, Combat combat, int moral, int will, int agility, int power, int intelligence, int hp, int pathos, int ethos, int logos) {
        super(x, y, id, width, height, null);
        this.handler = handler;
        this.game = game;
        this.combat = combat;
        this.moral = moral; // start at 0
        this.will = will;
        this.baseWill = will;
        this.agility = agility;
        this.baseAgility = agility;
        this.power = power;
        this.basePower = power;
        this.intelligence = intelligence;
        this.hp = hp;
        atkInventory[0] = ("Sound");
        atkInventory[1] = ("Visual");
        atkInventory[2] = ("Space");
        atkInventory[3] = ("Basic");
        reason[0] = pathos; //oathpos
        reason[1] = ethos; //ethos
        reason[2] = logos; //logos
    }

    public void tick() {

        if (Backdrop.currentBackdrop != Backdrop.BACKDROP.Transition && Game.gameState == Game.STATE.NonCombat && !playerActive && !textActive && !windowActive) {
            x += velX*2;
            y += velY*2;
        } else {
            velX = 0;
            velY = 0;
        }


        x = Game.clamp(x, 0, Game.WIDTH-width);
        y = Game.clamp(y, 0, Game.HEIGHT-(width*2));

        if (gameState == Game.STATE.NonCombat) {
            switch (Backdrop.currentBackdrop) {
                case Map11:
                    if (x >= 357) {
                        if (x < 554 || x > 562) y = clamp(y, 192, 262);
                    } else y = clamp(y, 192, HEIGHT);
                    if (y <= 282) x = clamp(x, 277, WIDTH);
                    else x = clamp(x,277, 344);
                    break;
                case Map12:
                case Map112:
                    y = clamp (y, 192, 272);
                    break;
                case Map14:
                    if (!(x > 281 && x < 316 && y > 270)) y = clamp(y, 192, 270);
                    break;
                case Map15:
                    if (x <= 217) {
                        y = clamp(y, 192, 262);
                    } else {
                        y = clamp(y, 192, HEIGHT);
                    }

                    if (y <= 277) {
                        x = clamp(x, 0, 355);
                    } else {
                        x = clamp(x,277, 355);
                    }
                    break;
                case Map16:
                    if (!(y > 155 && y < 163 && x < 273)) x = clamp(x, 268, 358);
                    break;
                case Map17:
                    x = clamp(x, 277, 357);
                    break;
                case Map18:
                    if (!(y > 152 && y < 182 && x > 361)) x = clamp(x, 271, 361);
                    break;
                case Map19:

                    if (x >= 367) {
                        y = clamp(y, 192, 272);
                    } else {
                        y = clamp(y, 0, 272);
                    }

                    if (y <= 182) {
                        x = clamp(x, 277, 355);
                    } else {
                        x = clamp(x,277, WIDTH);
                    }
                    break;

                case Map110:
                    if (x > 299 && x < 357) {
                        y = clamp(y, 87, 273);
                    } else {
                        y = clamp(y, 187, 273);
                    }
                    break;

                case Map13:
                case Map111:
                    if (y >= 382 || y <= 112) {
                        x = clamp(x, 270, 358);
                    } else if (y >= 292 || y <= 182) {
                        x = clamp(x, 170, 458);
                    }

                    if (x >= 468 || x <= 168) {
                        y = clamp(y, 196, 274);
                    } else if (x >= 368 || x <= 268) {
                        y = clamp(y, 127, 367);
                    }
                    break;
                case Map113:
                    if (x < 271) {
                        y = clamp(y, 192, 272);
                    } else {
                        y = clamp(y, 0, 272);
                    }

                    if (y > 162) {
                        x = clamp(x, 0, 361);
                    } else {
                        x = clamp(x,281, 361);
                    }
                    break;
                case Map116:
                case Map114:
                    if (y >= 376 || y <= 116) x = clamp(x, 268, 366);
                    else x = clamp(x, 168, 458);

                    if (x <= 258 || x >= 368) {
                        y = clamp(y, 127, 365);
                    }
                    break;
                case Map115:
                    y = Game.clamp(y, 0, 372);
                    x = Game.clamp(x, 268, 368);
                    break;
                case NorthHouse:
                case NorthHouseTwo:
                    if (!(x == 416 && y >= 332)) {
                        x = clamp(x, 136, 476);
                        y = clamp(y, 113, 333);
                    }
                    break;
                case SouthHouse:
                    if (!(x > 185 && x < 228 && y < 122)) {
                        x = clamp(x, 136, 466);
                        y = clamp(y, 122, 323);
                    }
                    break;
                case EastHouse:
                    if (!(x <= 165 && y == 142)) {
                        x = clamp(x, 164, 454);
                        y = clamp(y, 102, 342);
                    }
                    break;
                case WestHouse:
                    if (!(x >= 459 && y == 137)) {
                        x = clamp(x, 159, 459);
                        y = clamp(y, 97, 347);
                    }
                    break;
            }
        }

        if (updateMoral) {
            if (moral >= 5) {
                reason[0] += (int) ((moral * 0.05) * 25); //buff by reason 10%
                reason[1] += (int) ((moral * 0.05) * 25); //buff by reason 10%
                reason[2] += (int) ((moral * 0.05) * 25); //buff by reason 10%

                agility = (int) (baseAgility + (moral * 0.1) * baseAgility); //buff by agility 10%
            } else if (moral <= 0) {
                //buff by power 20% and will 5%
                power += (int) ((Math.abs(moral) * 0.2) * basePower);
                will += (int) ((Math.abs(moral) * 0.05) * baseWill);

                //debuff by 10%
                reason[0] -= (int) ((Math.abs(moral) * 0.05) * 25);
                reason[1] -= (int) ((Math.abs(moral) * 0.05) * 25);
                reason[2] -= (int) ((Math.abs(moral) * 0.05) * 25);
                agility -= (int) ((moral * 0.1) * baseAgility);
            } else {
                reason[0] += (int) ((Math.abs(moral) * 0.05) * 25);
                reason[1] += (int) ((Math.abs(moral) * 0.05) * 25);
                reason[2] += (int) ((Math.abs(moral) * 0.05) * 25);
            }
            updateMoral = false;
        }
    }

    public void render(Graphics g) {
        g.setColor(Color.GREEN);
        g.fillRect((int) x, (int) y, width, height);

        //collision(g);
    }

    public Rectangle getBounds() {
        return new Rectangle((int) x, (int) y, width, height);
    }

    private void collision(Graphics g) {
        for (int i = 0; i < handler.object.size(); i++) {
            GameObject temp = handler.object.get(i);

            if (temp.getID() == ID.Interact && temp instanceof StandardNPC) {
                if (!((StandardNPC) temp).defeated && game.gameState == Game.STATE.NonCombat && hp > 0) {
                    if (getBounds().intersects(temp.getBounds())) {
                        engageCombat(temp);
                    }
                }
            }
        }
    }

    public static void engageCombat(GameObject temp){
        Backdrop.changeState(Backdrop.currentBackdrop);

        Game.gameState = Game.STATE.Combat;
        temp.setID(ID.Interacting);
        Combat.state = Combat.CURRENT_STATE.PlayerAction;
        CombatBackdrop.backdropCurrent = CombatBackdrop.CBACKDROP.Combat1;
        HUD.HEALTH = 100;
    }

    public int getWill() {
        return this.will;
    }
    public int getMorality() {
        return this.moral;
    }
    public int getAgility() {
        return this.agility;
    }
    public int getPower() {
        return this.power;
    }
    public int getIntelligence() {
        return this.intelligence;
    }

    public void setWill(int will) {
        this.will = will;
    }
    public void setMorality(int moral) {
        updateMoral = true;
        this.moral = moral;
    }

    public void setAgility(int agility) {
        this.agility = agility;
    }
    public void setPower(int power) {
        this.power = power;
    }

    public void setIntelligence(int intelligence) {
        this.intelligence = intelligence;
    }

    public int getHP() {
        return this.hp;
    }

    public void setHP(int hp) {
        this.hp = hp;
    }

    public int getKillCount() {
        return killCount;
    }

    public void setKillCount(int kill) {
        killCount = kill;
    }

    public int getReasonCount() {
        return reasonCount;
    }

    public void setReasonCount(int reasoning) {
        reasonCount = reasoning;
    }

    public HashMap<String, Integer> getInventory() {
        return this.inventory;
    }

    public void removeItemInv(String thing, int amount) {
        invUpdate = true;
        if (inventory.containsKey(thing)) {
            if (amount < inventory.get(thing)) inventory.replace(thing, inventory.get(thing)-amount);
            else inventory.remove(thing);
        }
    }

    public void editInventory(String thing, int amount) { //can be negative number to remove
        invUpdate = true;
        if (inventory.containsKey(thing)) {
            if (amount < 0) removeItemInv(thing, (-1)*amount);
            else inventory.replace(thing, inventory.get(thing)+amount);
        } else {
            inventory.put(thing, amount);
        }
    }

    public void editInventory(String thing) {
        editInventory(thing, 1);
    }

    public void removeAllInventory(String thing) {
        inventory.remove(thing);
    }

    public void clearInventory() {
        inventory.clear();
    }
}
