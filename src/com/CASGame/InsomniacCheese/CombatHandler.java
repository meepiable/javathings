package com.CASGame.InsomniacCheese;

import java.util.*;
import java.awt.Graphics;

public class CombatHandler {

    public LinkedList<CombatObject> object = new LinkedList<>();

    public void addObject(CombatObject object) {
        this.object.add(object);
    }

    public void removeObject(CombatObject object) {
        this.object.remove(object);
    }

    public void tick() {
        for (int i = 0; i < object.size(); i++) {
            object.get(i).tick();
        }
    }

    public void render(Graphics g) {
        for (int i = 0; i < object.size(); i++) {
            object.get(i).render(g);
        }
    }

}