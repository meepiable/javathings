package com.CASGame.InsomniacCheese;

import com.sun.javafx.UnmodifiableArrayList;

import java.io.Serializable;
import java.util.LinkedList;
import java.awt.Graphics;

public class Handler implements Serializable {

    public LinkedList<GameObject> object = new LinkedList<>();

    public void tick() {
        for (GameObject temp : object) {
            temp.tick();
        }
    }

    public void render(Graphics g) {
        for (GameObject temp : object) {
            temp.render(g);
        }
    }

    public void render(Graphics g, Player player) {
        for (GameObject temp : object) {
            if (temp instanceof Player) temp.render(g);
        }
    }

    public void addObject(GameObject object) {
        if (!this.object.contains(object)) this.object.add(object);
    }

    public void removeObject(GameObject object) {
        this.object.remove(object);
    }

    public void removeObject() {
        for (int i = 0; i < object.size(); i++) {
            this.object.remove(object.get(i));
        }
    }

}