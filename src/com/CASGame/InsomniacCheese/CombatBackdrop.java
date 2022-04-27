package com.CASGame.InsomniacCheese;

import com.CASGame.InsomniacCheese.NPCs.StandardNPC;

import java.awt.*;

import static com.CASGame.InsomniacCheese.Game.HEIGHT;
import static com.CASGame.InsomniacCheese.Game.WIDTH;

public class CombatBackdrop {

    private final Combat combat;
    private final Game game;
    private final CombatHandler cHandler;
    private final HUD hud;
    private final Handler handler;
    private final Font fnt = new Font("arial", Font.BOLD, 25);

    public CombatBackdrop(Game game, Combat combat, CombatHandler cHandler, HUD hud, Handler handler) {
        this.game = game;
        this.combat = combat;
        this.cHandler = cHandler;
        this.hud = hud;
        this.handler = handler;
    }

    public enum CBACKDROP {
        Combat1,
        Combat2,
    }//etc etc

    public static CBACKDROP backdropCurrent = CBACKDROP.Combat1;

    public void tick() {
        //do nothing
    }

    public void render(Graphics g) {
        switch (backdropCurrent) {
            case Combat1:
                if (combat.state != Combat.CURRENT_STATE.Enemy && combat.state != Combat.CURRENT_STATE.PlayerArtifact && combat.state != Combat.CURRENT_STATE.PlayerHappening) {
                    g.setColor(Color.BLACK);
                    g.fillRect(0, 0, WIDTH, HEIGHT);
                    g.setFont(fnt);

                    g.setColor(Color.YELLOW);
                    g.fillRect((int) (WIDTH * 0.1), (int) (HEIGHT * 0.6), 130, 50);
                    g.fillRect((int) (WIDTH * 0.3), (int) (HEIGHT * 0.6), 130, 50);
                    g.fillRect((int) (WIDTH * 0.5), (int) (HEIGHT * 0.6), 130, 50);
                    g.fillRect((int) (WIDTH * 0.7), (int) (HEIGHT * 0.6), 130, 50);


                    g.fillRect((int) (WIDTH * 0.1), (int) (HEIGHT * 0.72), (int) (WIDTH * 0.7 + 130 - (WIDTH * 0.1)), 100);

                    g.setColor(Color.CYAN);
                    g.drawRect((int) (WIDTH * 0.1), (int) (HEIGHT * 0.6), 130, 50);
                    g.drawRect((int) (WIDTH * 0.3), (int) (HEIGHT * 0.6), 130, 50);
                    g.drawRect((int) (WIDTH * 0.5), (int) (HEIGHT * 0.6), 130, 50);
                    g.drawRect((int) (WIDTH * 0.7), (int) (HEIGHT * 0.6), 130, 50);

                    g.setColor(Color.RED);
                    g.drawString("Fight", (int) (WIDTH * 0.15), (int) (HEIGHT * 0.67));
                    g.drawString("Skill", (int) (WIDTH * 0.36), (int) (HEIGHT * 0.67));
                    g.drawString("Reason", (int) (WIDTH * 0.53), (int) (HEIGHT * 0.67));
                    g.drawString("Artifact", (int) (WIDTH * 0.74), (int) (HEIGHT * 0.67));

                    if (Game.gameState == Game.STATE.Combat) {
                        if (combat.enemy == null || !(combat.enemy instanceof StandardNPC)) {
                            for (int i = 0; i < handler.object.size(); i++) {
                                if (handler.object.get(i).getID() == ID.Interacting) {
                                    combat.enemy = handler.object.get(i);
                                }
                            }
                        }
                        ((StandardNPC) combat.enemy).render2(g);
                        /*g.setColor(Color.BLUE);
                        g.fillRect((int) (WIDTH*0.48), (int) (HEIGHT*0.4), ((StandardNPC) combat.enemy).getSelf().width, ((StandardNPC) combat.enemy).getSelf().height);*/
                    }
                } else if (combat.state == Combat.CURRENT_STATE.PlayerHappening ){
                    g.setColor(Color.BLACK);
                    g.fillRect(0, 0, WIDTH, HEIGHT);
                    if (combat.atkState != Combat.ATTACK_STATE.Theatre) {
                        g.setColor(Color.YELLOW);
                        g.fillRect((int) (WIDTH * 0.1), (int) (HEIGHT * 0.72), (int) (WIDTH * 0.7 + 130 - (WIDTH * 0.1)), 100);
                    }
                }
                break;
            case Combat2:
                break;
            default:
                break;
        }
        combat.render(g);
        hud.render(g);
    }
}
