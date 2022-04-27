package com.CASGame.InsomniacCheese.NPCs;

import com.CASGame.InsomniacCheese.*;
import com.CASGame.InsomniacCheese.Enemies.BasicEnemy;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Objects;

import static com.CASGame.InsomniacCheese.GUI.renderTextBox;
import static com.CASGame.InsomniacCheese.GUI.textActive;

public abstract class StandardNPC extends GameObject {

    protected boolean hasSentience = true;
    protected String direction;
    public boolean defeated;
    public String string;
    protected int lvl, will, agility, power, intelligence;
    public boolean reasoned = false;
    public LinkedList<String> listOfEffects = new LinkedList<>();
    public boolean h = false;
    private final boolean doesString;
    byte nCode;
    private transient final BufferedImage sprite;
    private String string2;

    public StandardNPC(float x, float y, ID id, int width, int height, String direction, BasicEnemy type, String string, int lvl, int will, int agility, int power, int intelligence, boolean doesString, byte code, BufferedImage sprite, String string2) {
        super(x, y, id, width, height, type);
        this.direction = direction;
        this.string = string;
        this.lvl = lvl;
        this.will = will;
        this.agility = agility;
        this.power = power;
        this.intelligence = intelligence;
        this.doesString = doesString;
        this.nCode = code;
        this.sprite = sprite;
        this.string2 = string2;
    }

    public abstract void tick();
    public void render(Graphics g) {
        g.drawImage(sprite, (int) x, (int) y, null);
        collision(g);
    }

    public void render2(Graphics g) {
        if (this instanceof ClayGolem) {
            try {
                g.drawImage(BufferedImageLoader.resize(Game.sprites[18], 75, 75), (int) (Game.WIDTH * 0.4), (int) (Game.HEIGHT * 0.3), null);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (this instanceof KilnHardenedGolem) {
            try {
                g.drawImage(BufferedImageLoader.resize(Game.sprites[26], 75, 75), (int) (Game.WIDTH * 0.4), (int) (Game.HEIGHT * 0.3), null);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (this instanceof RogueArtist) {
            try {
                g.drawImage(BufferedImageLoader.resize(Game.sprites[30], 75, 75), (int) (Game.WIDTH * 0.4), (int) (Game.HEIGHT * 0.3), null);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            try {
                g.drawImage(BufferedImageLoader.resize(Game.sprites[11], 75, 75), (int) (Game.WIDTH * 0.4), (int) (Game.HEIGHT * 0.3), null);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void collision(Graphics g) {
        boolean result = getBounds().intersects(Game.user.getBounds()) && getID() == ID.Interact && !defeated && Game.gameState == Game.STATE.NonCombat;
        if (result) {

            if (!h && doesString) {
                textActive = true;
                h = true;
            }
            if (textActive) textActive = !renderTextBox(g, string2);
            if (doesString && !textActive) {
                Player.engageCombat(this);
                //h = false;
            } else if (!doesString) {
                Player.engageCombat(this);
            }
        }
    }

    public Rectangle getBounds() {
        if (!defeated) {
            if (Objects.equals(direction, "north")) return new Rectangle((int) x, (int) y - (height * 6), width, (height * 7));
            else if (Objects.equals(direction, "east")) return new Rectangle((int) x, (int) y, width * 6, height);
            else if (Objects.equals(direction, "south")) return new Rectangle((int) x, (int) y, width, height * 6);
            else if (Objects.equals(direction, "west")) return new Rectangle((int) (x - (width * 6)), (int) y, (width * 7), height);
            else return new Rectangle((int) x - width * 6, (int) y - width * 6, width * 7, height * 7);
        } else {
            return new Rectangle((int) x, (int) y, width, height);
        }
    }

    public abstract Rectangle getSelf();

    public int getWill() {
        return this.will;
    }
    public int getLvl() {
        return this.lvl;
    }
    public int getAgility() {
        return this.agility;
    }
    public int getPower() {
        return this.power;
    }

    public void setWill(int will) {
        this.will = will;
    }
    public void setLvl(int lvl) {
        this.lvl = lvl;
    }

    public void setAgility(int agility) {
        this.agility = agility;
    }
    public void setPower(int power) {
        this.power = power;
    }

    public boolean getReasonable() {
        return intelligence >= 20;
    }

    public LinkedList<String> getLOE() {
        return this.listOfEffects;
    }

    public void addLOE(String thing) {
        //add a key and map when new thing is added
        this.listOfEffects.add(thing);
    }

    public void removeLOE(int index) {
        this.listOfEffects.remove(index);
    }

    public void clearLOE() {
        this.listOfEffects.clear();
    }

    public byte getCode() {
        return nCode;
    }
}
