package com.CASGame.InsomniacCheese;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Arrays;

import static com.CASGame.InsomniacCheese.GUI.*;

public class KeyInput implements KeyListener {

    public static Boolean[] keyDown = {false, false, false, false};
    private final Handler handler;
    private final Game game;
    private final Combat combat;
    private final CombatHandler combatHandler;

    public KeyInput(Handler handler, Game game, Combat combat, CombatHandler combatHandler) {
        this.handler = handler;
        this.game = game;
        this.combat = combat;
        this.combatHandler = combatHandler;
    }

    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        String keyPress = KeyEvent.getKeyText(key);

        if (Game.gameState == Game.STATE.NonCombat && key == KeyEvent.VK_E && !textActive && !playerActive)
                windowActive = !windowActive;
        if (Game.gameState == Game.STATE.NonCombat && key == KeyEvent.VK_I && !textActive && !windowActive)
            playerActive = !playerActive;

        if (Game.gameState == Game.STATE.NonCombat) {
            if (key == KeyEvent.VK_SHIFT) {
                game.saveGame();
            }

            for (int i = 0; i < handler.object.size(); i++) {
                GameObject temp = handler.object.get(i);
                if (temp.getID() == ID.Player && !textActive && !windowActive) {

                    if (key == KeyEvent.VK_W || key == KeyEvent.VK_UP) {
                        temp.setVelY(-5);
                        keyDown[0] = true;
                    }
                    if (key == KeyEvent.VK_S || key == KeyEvent.VK_DOWN) {
                        temp.setVelY(5);
                        keyDown[1] = true;
                    }
                    if (key == KeyEvent.VK_A || key == KeyEvent.VK_LEFT) {
                        temp.setVelX(-5);
                        keyDown[2] = true;
                    }
                    if (key == KeyEvent.VK_D || key == KeyEvent.VK_RIGHT) {
                        temp.setVelX(5);
                        keyDown[3] = true;
                    }
                }
            }
        } else if (combat.state == Combat.CURRENT_STATE.Enemy) {
            for (int i = 0; i < combatHandler.object.size(); i++) {
                CombatObject temp = combatHandler.object.get(i);
                if (temp.getID() == ID.CombatPlayer) {

                    if (key == KeyEvent.VK_W || key == KeyEvent.VK_UP) {
                        temp.setVelY(-5);
                        keyDown[0] = true;
                    }
                    if (key == KeyEvent.VK_S || key == KeyEvent.VK_DOWN) {
                        temp.setVelY(5);
                        keyDown[1] = true;
                    }
                    if (key == KeyEvent.VK_A || key == KeyEvent.VK_LEFT) {
                        temp.setVelX(-5);
                        keyDown[2] = true;
                    }
                    if (key == KeyEvent.VK_D || key == KeyEvent.VK_RIGHT) {
                        temp.setVelX(5);
                        keyDown[3] = true;
                    }
                }
            }
        }

        if (Game.gameState == Game.STATE.Combat) {
            if (Combat.state == Combat.CURRENT_STATE.PlayerHappening) {
                switch (Combat.atkState) {
                    case Music:
                        if (key == KeyEvent.VK_BACK_SPACE) {
                            if (!combat.sound.savedKeysPiano.isEmpty()) combat.sound.savedKeysPiano.remove(combat.sound.savedKeysPiano.size()-1);
                        } else if (key == KeyEvent.VK_ESCAPE) {
                            if (!combat.sound.savedKeysPiano.isEmpty()) combat.sound.savedKeysPiano.clear();
                        } else if (key == KeyEvent.VK_A || key == KeyEvent.VK_B || key == KeyEvent.VK_C || key == KeyEvent.VK_D || key == KeyEvent.VK_E || key == KeyEvent.VK_F || key == KeyEvent.VK_G) {
                            if (e.isShiftDown())
                                combat.sound.savedKeysPiano.add(keyPress.toUpperCase());
                            else
                                combat.sound.savedKeysPiano.add(keyPress.toLowerCase());
                        } else if (key == KeyEvent.VK_ENTER){
                            combat.sound.stuffssent = true;
                        }

                        break;
                    case VA:
                        if (key == KeyEvent.VK_ENTER){
                            combat.visual.stuffsssent = true;
                        } else if (key == KeyEvent.VK_ESCAPE) {
                            if (!combat.visual.clickedColors.isEmpty()) combat.visual.clickedColors.clear();
                        } else if (key == KeyEvent.VK_BACK_SPACE) {
                            if (!combat.visual.clickedColors.isEmpty()) combat.visual.clickedColors.remove(combat.visual.clickedColors.size()-1);
                        }
                        break;
                    case Theatre:
                        if (key == KeyEvent.VK_ENTER) {
                            combat.spacial.stuffssssent = true;
                        } else if (key == KeyEvent.VK_ESCAPE) {
                            Arrays.fill(combat.spacial.clickedStages, false);
                        } else if (key == KeyEvent.VK_BACK_SPACE) {
                            int pos = -1;
                            for (int i = combat.spacial.clickedStages.length-1; i >= 0; i--) {
                                if (combat.spacial.clickedStages[i]) {
                                    pos = i;
                                    break;
                                }
                            }

                            if (pos != -1) combat.spacial.clickedStages[pos] = false;
                        } else if (key == KeyEvent.VK_1 || key == KeyEvent.VK_NUMPAD1) {
                            combat.spacial.clickedStages[0] = true;
                        } else if (key == KeyEvent.VK_2 || key == KeyEvent.VK_NUMPAD2) {
                            combat.spacial.clickedStages[1] = true;
                        } else if (key == KeyEvent.VK_3 || key == KeyEvent.VK_NUMPAD3) {
                            combat.spacial.clickedStages[2] = true;
                        } else if (key == KeyEvent.VK_4 || key == KeyEvent.VK_NUMPAD4) {
                            combat.spacial.clickedStages[3] = true;
                        } else if (key == KeyEvent.VK_5 || key == KeyEvent.VK_NUMPAD5) {
                            combat.spacial.clickedStages[4] = true;
                        } else if (key == KeyEvent.VK_6 || key == KeyEvent.VK_NUMPAD6) {
                            combat.spacial.clickedStages[5] = true;
                        } else if (key == KeyEvent.VK_7 || key == KeyEvent.VK_NUMPAD7) {
                            combat.spacial.clickedStages[6] = true;
                        } else if (key == KeyEvent.VK_8 || key == KeyEvent.VK_NUMPAD8) {
                            combat.spacial.clickedStages[7] = true;
                        } else if (key == KeyEvent.VK_9 || key == KeyEvent.VK_NUMPAD9) {
                            combat.spacial.clickedStages[8] = true;
                        }

                        break;
                    default:
                        break;
                }
            }
        }
    }

    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();

        if (Game.gameState == Game.STATE.NonCombat && Backdrop.currentBackdrop != Backdrop.BACKDROP.Transition) {
            for (int i = 0; i < handler.object.size(); i++) {
                GameObject temp = handler.object.get(i);

                if (temp.getID() == ID.Player) {

                    if (key == KeyEvent.VK_W || key == KeyEvent.VK_UP) keyDown[0] = false;
                    if (key == KeyEvent.VK_S || key == KeyEvent.VK_DOWN) keyDown[1] = false;
                    if (key == KeyEvent.VK_A || key == KeyEvent.VK_LEFT) keyDown[2] = false;
                    if (key == KeyEvent.VK_D || key == KeyEvent.VK_RIGHT) keyDown[3] = false;

                    //vertical
                    if (!keyDown[0] && !keyDown[1] || Backdrop.currentBackdrop == Backdrop.BACKDROP.Transition) temp.setVelY(0);
                    //horizontal
                    if (!keyDown[2] && !keyDown[3] || Backdrop.currentBackdrop == Backdrop.BACKDROP.Transition) temp.setVelX(0);
                }
            }
        }


        for (int i = 0; i < combatHandler.object.size(); i++) {
            if (combat.state == Combat.CURRENT_STATE.Enemy) {
                CombatObject temp = combatHandler.object.get(i);

                if (temp.getID() == ID.CombatPlayer) {

                    if (key == KeyEvent.VK_W || key == KeyEvent.VK_UP) keyDown[0] = false;
                    if (key == KeyEvent.VK_S || key == KeyEvent.VK_DOWN) keyDown[1] = false;
                    if (key == KeyEvent.VK_A || key == KeyEvent.VK_LEFT) keyDown[2] = false;
                    if (key == KeyEvent.VK_D || key == KeyEvent.VK_RIGHT) keyDown[3] = false;

                    //vertical
                    if (!keyDown[0] && !keyDown[1]) temp.setVelY(0);
                    //horizontal
                    if (!keyDown[2] && !keyDown[3]) temp.setVelX(0);
                }
            }
        }
    }

    public void keyTyped(KeyEvent e) {

    }
}
