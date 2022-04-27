package com.CASGame.InsomniacCheese.Attacks;

import java.awt.*;

public abstract class BasicAttack {

    protected String description;

    public BasicAttack() {

    }

    public abstract void tick();
    public abstract void render(Graphics g);

    public abstract void resetValues();

}
