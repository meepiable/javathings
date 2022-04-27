package com.CASGame.InsomniacCheese;

import com.CASGame.InsomniacCheese.Attacks.*;
import com.CASGame.InsomniacCheese.NPCs.*;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.*;

import static com.CASGame.InsomniacCheese.GUI.renderTextBox;
import static com.CASGame.InsomniacCheese.GUI.textActive;
import static com.CASGame.InsomniacCheese.Game.HEIGHT;
import static com.CASGame.InsomniacCheese.Game.WIDTH;

public class Combat extends MouseAdapter {

    private final Game game;
    private final Handler handler;
    private final CombatHandler combatHandler;
    public final DamageDealer dmgD;
    public final Sound sound;
    public final Visual visual;
    public final Spacial spacial;

    public final ShadowArts shadow;
    public final ThunderousMelody thunder;
    public final SonnetOfTheLost sonnet;
    public final RayOfAbstraction ray;
    public final InkBoundLance ink;

    public GameObject enemy;
    public CombatObject player;
    public GameObject test;

    private boolean h = false;

    public enum CURRENT_STATE {
        RoundStart,
        PlayerAction,
        PlayerAttack,
        PlayerSpell,
        PlayerArtifact,
        PlayerReason,
        PlayerHappening,
        Enemy,
    }

    public enum ATTACK_STATE {
        Music,
        VA,
        Theatre,
        Ink,
        Ray,
        Shadow,
        Sonnet,
        Thunder,
        NA,
    }

    public static CURRENT_STATE state = CURRENT_STATE.RoundStart;
    public static ATTACK_STATE atkState = ATTACK_STATE.NA;
    public int whoFirst = 0; //0 = player and 1 = enemy
    public int whoStage = 0;
    public int turnCount = 0;
    private boolean en = false;
    public boolean done = false;
    public boolean done2 = false;
    public int[] option = new int[2];

    public boolean oneTimeSpell = false;
    public boolean shadowArts = false;
    public boolean sotl = false;
    public boolean ibl = false;

    public HashMap<String, Integer> effectCountdown = new HashMap<>();
    private final Font fnt = new Font("arial", Font.BOLD, 25);
    private final Font fnt2 = new Font("arial", Font.BOLD, 15);

    public Combat(Game game, Handler handler, CombatHandler combatHandler) {
        this.game = game;
        this.handler = handler;
        this.combatHandler = combatHandler;

        this.dmgD = new DamageDealer(this, game);
        this.sound = new Sound(this, game);
        this.visual = new Visual(this, game);
        this.spacial = new Spacial(this, game);

        this.shadow =  new ShadowArts(this);
        this.thunder =  new ThunderousMelody(game, this);
        this.sonnet =  new SonnetOfTheLost(this);
        this.ray =  new RayOfAbstraction(this, game);
        this.ink =  new InkBoundLance(this);
    }

    private boolean mouseOver(int mx, int my, int x, int y, int width, int height) {
        if (mx > x && mx < x + width) {
            return my > y && my < y + height;
        } else
            return false;
    }
    // A Function to generate a random permutation of arr[]
    public String[] randomize(String arr[]) {
        // Start from the last element and swap one by one. We don't
        // need to run for the first element that's why i > 0
        for (int i = (arr.length)-1; i > 0; i--) {

            // Pick a random index from 0 to i
            int j = game.r.nextInt(i+1);

            // Swap arr[i] with the element at random index
            String temp = arr[i];
            arr[i] = arr[j];
            arr[j] = temp;
        }

        return arr;
    }

    public Color[] randomizeCol(Color arr[]) {
        // Start from the last element and swap one by one. We don't
        // need to run for the first element that's why i > 0
        for (int i = (arr.length)-1; i > 0; i--) {

            // Pick a random index from 0 to i
            int j = game.r.nextInt(i+1);

            // Swap arr[i] with the element at random index
            Color temp = arr[i];
            arr[i] = arr[j];
            arr[j] = temp;
        }

        return arr;
    }

    public boolean equalLists(ArrayList<String> one, ArrayList<String> two){
        if (one == null && two == null){
            return true;
        }

        if((one == null && two != null) || one != null && two == null || one.size() != two.size()){
            return false;
        }

        //to avoid messing the order of the lists we will use a copy
        one = new ArrayList<String>(one);
        two = new ArrayList<String>(two);

        Collections.sort(one);
        Collections.sort(two);
        return one.equals(two);
    }

    public void mousePressed(MouseEvent e) {
        int mx = e.getX();
        int my = e.getY();
        if (Game.gameState == Game.STATE.Combat && state != CURRENT_STATE.Enemy && state != CURRENT_STATE.PlayerHappening) {
            if (mouseOver(mx, my, (int) (WIDTH * 0.1), (int) (HEIGHT * 0.6), 130, 50)) {
                //fight
                state = CURRENT_STATE.PlayerAttack;
            } else if (mouseOver(mx, my, (int) (WIDTH * 0.3), (int) (HEIGHT * 0.6), 130, 50)) {
                //skill/spell
                if (!oneTimeSpell) state = CURRENT_STATE.PlayerSpell;
            } else if (mouseOver(mx, my, (int) (WIDTH * 0.5), (int) (HEIGHT * 0.6), 130, 50)) {
                //Reason
                state = CURRENT_STATE.PlayerReason;
            } else if (mouseOver(mx, my, (int) (WIDTH * 0.7), (int) (HEIGHT * 0.6), 130, 50)) {
                //Use artifact
                state = CURRENT_STATE.PlayerArtifact;
            }

            if (state == CURRENT_STATE.PlayerArtifact && mouseOver(mx, my,(int) (WIDTH * 0.9)-4, (int) (HEIGHT*0.16)-8, 16, 16)) {
                state = CURRENT_STATE.PlayerAction;
            }

            if (state != CURRENT_STATE.PlayerAction && state != CURRENT_STATE.PlayerHappening && state != CURRENT_STATE.PlayerReason) {
                for (int i = 0; i < 4; i++) {
                    if (mouseOver(mx, my, (int) (WIDTH*(0.12+(i*0.20))), (int) (HEIGHT*0.75), 110, 50)) {
                        if (state == CURRENT_STATE.PlayerAttack) {
                            option[0] = 0;
                        } else if (state == CURRENT_STATE.PlayerSpell) {
                            option[0] = 1;
                        } else if (state == CURRENT_STATE.PlayerArtifact) {
                            option[0] = 3;
                        }

                        option[1] = i;
                        whoStage++;
                        state = CURRENT_STATE.RoundStart;
                        sound.cho = false;
                        visual.cho2 = false;
                        break;
                    }
                }
            } else if (state == CURRENT_STATE.PlayerReason) {
                for (int i = 0; i < 3; i++) {
                    if (mouseOver(mx, my, (int) (WIDTH*(0.12+(i*0.27))), (int) (HEIGHT*0.75), 150, 50)) {
                        option[0] = 2;
                        option[1] = i;
                        whoStage++;
                        state = CURRENT_STATE.RoundStart;
                        sound.cho = false;
                        visual.cho2 = false;
                    }
                }
            }
        }

        if (state == CURRENT_STATE.PlayerHappening && atkState == ATTACK_STATE.VA) {
            for (int i = 1; i < 4; i++) {
                if (mouseOver(mx, my, (int) (WIDTH*(0.12+(i*0.20))), (int) (HEIGHT*0.45), 110, 50)) {
                    switch (i) {
                        case 1:
                            visual.clickedColors.add("RED");
                            break;
                        case 2:
                            visual.clickedColors.add("YELLOW");
                            break;
                        case 3:
                            visual.clickedColors.add("BLUE");
                            break;
                        default:
                            break;
                    }
                    break;
                }
            }
        }
    }

    public void mouseReleased(MouseEvent e) {

    }

    public void tick() {
        //todo: mouseover descriptions here
        if (game.gameState == Game.STATE.Combat) {

            if (enemy.getID() != ID.Interacting || enemy == null) {
                for (int i = 0; i < handler.object.size(); i++) {
                    if (handler.object.get(i).getID() == ID.Interacting) {
                        this.enemy = handler.object.get(i);
                    }
                }
            }

            if (this.player == null) {
                this.player = new CombatPlayer((float) (WIDTH * 0.5), (float) (HEIGHT * 0.5), ID.CombatPlayer, this, 32, 32, combatHandler, game.user.getWill(), game.user.getAgility(), game.user.getPower());
                this.player.setCurrentWill(game.user.getHP());
                this.player.setAgility(game.user.getAgility());
                this.player.setLvl(game.user.getMorality());
                this.player.setPower(game.user.getPower());
                this.player.setTotalWill(game.user.getWill());
                ((CombatPlayer) this.player).setIntelligence(game.user.getIntelligence());
            }

            HUD.HEALTH2 = (int) (((double) player.currentWill/(double) player.totalWill)*100);
            HUD.HEALTH = (int) (((double) enemy.getType().getCurrentWill()/(double) enemy.getType().getTotalWill())*100);

            if (state == CURRENT_STATE.Enemy) {
                combatHandler.tick();
                if (!en) {
                    combatHandler.object.clear();
                    combatHandler.addObject(player);
                    if (enemy.getID() == ID.Interacting) {
                        //add enemy to list of existing objects in combat and running its processes
                        combatHandler.addObject(enemy.getType());

                        //says that enemy exists and has been added
                        en = true;
                    }
                }
            }
            if (whoStage == 0) {
                if (this.player.getAgility() >= ((StandardNPC) this.enemy).getAgility()) {
                    whoFirst = 0; //player go first if player faster or tie
                } else {
                    whoFirst = 1; //enemy go first if enemy faster
                }
            }


            if (state == CURRENT_STATE.RoundStart) {
                if (whoStage == 3) {
                    whoStage = 0;
                    turnCount++;
                    done2 = false;
                    for (Map.Entry<String, Integer> entry : effectCountdown.entrySet()) {
                        effectCountdown.replace(entry.getKey(), entry.getValue() - 1);

                        if (entry.getValue() <= 0) {
                            ((CombatPlayer) player).removeLOE(((CombatPlayer) player).getLOE().indexOf(entry.getKey()));
                            effectCountdown.remove(entry.getKey());
                        }
                    }
                }

                if (whoStage == 0) state = CURRENT_STATE.PlayerAction;
                else if (whoStage == 1) {
                    if (whoFirst == 0) {
                        state = CURRENT_STATE.PlayerHappening;
                    } else {
                        en = false;
                        enemy.getType().resetValues();
                        if (enemy instanceof ChaosGolem) {
                            //Mystique Strike
                            if (!effectCountdown.containsKey("Mystique Strike")) {
                                dmgD.dealDamage(player, 100, 100, 0);
                                if (game.probSim(25)) ((CombatPlayer) player).addLOE("Cripple");
                                effectCountdown.put("Mystique Strike", 3);
                            }
                        }
                        state = CURRENT_STATE.Enemy;
                    }
                } else if (whoStage == 2) {
                    if (whoFirst == 0) {
                        en = false;
                        enemy.getType().resetValues();
                        if (enemy instanceof ChaosGolem) {
                            //Mystique Strike
                            if (!effectCountdown.containsKey("Mystique Strike")) {
                                dmgD.dealDamage(player, 100, 100, 0);
                                effectCountdown.put("Mystique Strike", 3);
                            }
                        }
                        state = CURRENT_STATE.Enemy;
                    } else {
                        state = CURRENT_STATE.PlayerHappening;
                    }
                }
            }


            if (enemy instanceof StandardNPC) {
                if ((player.currentWill <= 0 || enemy.getType().getCurrentWill() <= 0 || ((StandardNPC) enemy).reasoned) && !((StandardNPC) enemy).defeated) {
                    //change to savestate
                    //reset all vars for next combat
                    if ((enemy.getType().getCurrentWill() <= 0 || ((StandardNPC) enemy).reasoned) && !((StandardNPC) enemy).defeated) {
                        switch (((StandardNPC) enemy).getCode()) {
                            //0: TestNPC, 1: ClayGolem, 2: KilnHardenedGolem, 3: LivingStatue, 4: RogueArtist, 5: ChaosGolem, 6: IronGolem, 7: OmniSoldier
                            case 0:
                                Game.user.editInventory("Knowledge Fragment", 100);
                                break;
                            case 1:
                                Game.user.editInventory("Knowledge Fragment", 1);
                                break;
                            case 2:
                                Game.user.editInventory("Knowledge Fragment", 2);
                                break;
                            case 3:
                                if (((StandardNPC) enemy).reasoned) Game.user.editInventory("Knowledge Fragment", 2);
                                else Game.user.editInventory("Knowledge Fragment");
                                break;
                            case 5:
                                Game.user.editInventory("Knowledge Fragment", 3);
                                int a;
                                if (((StandardNPC) enemy).reasoned) a = 20;
                                else a = 10;

                                if (game.probSim(a)) {
                                    Game.user.editInventory("Health Vial");
                                    Game.user.editInventory("Rare Relic");
                                }
                                break;
                            case 6:
                                Game.user.editInventory("Health Vial", 3);
                                Game.user.editInventory("Knowledge Fragmemnts", 5);
                                Game.user.editInventory("Gem of Mystique");
                                if (game.probSim(15)) Game.user.editInventory("Mystique Relic");
                                break;
                            default:
                                //System.out.println(((StandardNPC) enemy).getCode());
                        }
                    }

                    Game.user.setHP(player.currentWill);
                    combatHandler.object.clear();
                    turnCount = 0;
                    whoStage = 0;

                    KeyInput.keyDown[0] = false;
                    KeyInput.keyDown[1] = false;
                    KeyInput.keyDown[2] = false;
                    KeyInput.keyDown[3] = false;

                    if (((StandardNPC) enemy).reasoned) {
                        Game.user.setMorality(Game.user.getMorality() + 1);
                        Game.user.setReasonCount(Game.user.getReasonCount()+1);
                        Game.gameState = Game.STATE.NonCombat;
                        enemy.setID(ID.Interact);
                        Backdrop.changeState(Backdrop.currentBackdrop);
                        ((StandardNPC) enemy).defeated = true;
                    } else if (!((StandardNPC) enemy).reasoned && Game.user.getHP() > 0 && enemy.getType().getCurrentWill() <= 0){
                        Game.user.setMorality(Game.user.getMorality() - 1);
                        Game.user.setKillCount(Game.user.getKillCount()+1);
                        Game.gameState = Game.STATE.NonCombat;
                        enemy.setID(ID.Interact);
                        ((StandardNPC) enemy).defeated = true;
                        Backdrop.changeState(Backdrop.currentBackdrop);
                    } else if (Game.user.getHP() <= 0) {
                        Game.user.x = (int) (WIDTH / 2.0 - 32);
                        Game.user.y = (int) (HEIGHT / 2.0 - 32);
                        Backdrop.gg = false;
                        enemy.setID(ID.Interact);

                        player.currentWill = player.totalWill;
                        Backdrop.BACKDROP temp = Backdrop.currentBackdrop;
                        Game.deathCount++;
                        Game.gameState = Game.STATE.Death;
                        Backdrop.lastBackdrop = temp;
                    }
                }

                if (state != CURRENT_STATE.Enemy) combatHandler.object.clear();
            }


            if (!done2) {

                //on player
                if (((CombatPlayer) player).getLOE().contains("Agility") && state == Combat.CURRENT_STATE.PlayerAction) {
                    player.setAgility(player.getAgility() + 20);
                }
                if (((CombatPlayer) player).getLOE().contains("Strength") && state == Combat.CURRENT_STATE.PlayerAction) {
                    player.setPower(player.getPower() + 20);
                }
                if (((CombatPlayer) player).getLOE().contains("Regeneration") && state == Combat.CURRENT_STATE.PlayerAction) {
                    dmgD.dealDamage(player, -50, 100, 0);
                }
                if (((CombatPlayer) player).getLOE().contains("Cripple") && state == Combat.CURRENT_STATE.PlayerAction) {
                    player.setAgility(player.getAgility() - 20);
                }
                if (((CombatPlayer) player).getLOE().contains("Drain") && state == Combat.CURRENT_STATE.PlayerAction) {
                    player.setPower(player.getPower() - 30);
                }
                if (((CombatPlayer) player).getLOE().contains("Burn") && state == Combat.CURRENT_STATE.PlayerAction) {
                    dmgD.dealDamage(player, 50, 100, 0);
                }

                //on enemy
                if (((StandardNPC) enemy).getLOE().contains("Agility") && state == Combat.CURRENT_STATE.PlayerAction) {
                    enemy.getType().setAgility(enemy.getType().getAgility() + 20);
                }
                if (((StandardNPC) enemy).getLOE().contains("Strength") && state == Combat.CURRENT_STATE.PlayerAction) {
                    enemy.getType().setPower(enemy.getType().getPower() + 20);
                }
                if (((StandardNPC) enemy).getLOE().contains("Regeneration") && state == Combat.CURRENT_STATE.PlayerAction) {
                    dmgD.dealDamage(enemy, -50, 100, 0);
                }
                if (((StandardNPC) enemy).getLOE().contains("Cripple") && state == Combat.CURRENT_STATE.PlayerAction) {
                    enemy.getType().setAgility(enemy.getType().getAgility() - 20);
                }
                if (((StandardNPC) enemy).getLOE().contains("Drain") && state == Combat.CURRENT_STATE.PlayerAction) {
                    enemy.getType().setPower(enemy.getType().getPower() - 30);
                }
                if (((StandardNPC) enemy).getLOE().contains("Burn") && state == Combat.CURRENT_STATE.PlayerAction) {
                    dmgD.dealDamage(enemy, 50, 100, 0);
                }

                done2 = true;
            }

            if (state == CURRENT_STATE.PlayerHappening) {
                switch (option[0]) {
                    case 0:
                        if (Game.user.atkInventory == null || Game.user.atkInventory.length < option[1]) {
                            dmgD.dealDamage(enemy, (2 * Game.user.getPower() + Game.user.getMorality()), 100, -1);
                        } else {
                            switch (Game.user.atkInventory[option[1]]) {
                                case "Sound":
                                    sound.tick();
                                    break;
                                case "Visual":
                                    visual.tick();
                                    break;
                                case "Space":
                                    spacial.tick();
                                    break;
                                case "Basic":
                                    dmgD.dealDamage(enemy, Game.user.getPower(), 100, 10);
                                    done = true;
                                    break;
                                case "Death":
                                    //dmgD.dealDamage(enemy, enemy.getType().getTotalWill(), 100, 1);
                                    break;
                                default:
                                    dmgD.dealDamage(enemy, (2 * Game.user.getPower() + Game.user.getMorality()), 100, -1);
                                    System.out.println(Game.user.atkInventory[option[1]]);
                                    done = true;
                                    break;
                            }
                        }
                        break;
                    case 1://todo: test later
                        switch (Game.user.splInventory[option[1]]) {
                            case "Shadow Arts":
                                shadow.tick();
                                break;
                            case "Thunderous Melody":
                                thunder.tick();
                                break;
                            case "Sonnet of the Lost":
                                sonnet.tick();
                                break;
                            case "Ray of Abstraction":
                                ray.tick();
                                break;
                            case "Inkbound Lance":
                                ink.tick();
                                break;
                            default:
                                dmgD.dealDamage(enemy, (2*game.user.getPower() + game.user.getMorality()), 100, -1);
                                System.out.println(game.user.splInventory[option[1]]);
                                done = true;
                                break;
                        }

                        oneTimeSpell = true;
                        break;
                    case 2:
                        //check if reasonable, set yellow text to pathos ethos logos, calculate morality, etc
                        if (((StandardNPC) enemy).getReasonable()) {
                            int a = game.r.nextInt(100)+Game.user.reason[option[1]];
                            int b = 75;
                            if (sotl) {
                                b-=25;
                                sotl = false;
                            }

                            if (a >= b) {
                                ((StandardNPC) enemy).reasoned = true;
                                done = true;
                            } else {
                                done = true;
                            }

                        } else {
                            state = CURRENT_STATE.PlayerAction;
                            ((StandardNPC) enemy).string = "Enemy cannot be reasoned with!";
                            whoStage--;
                        }
                        break;

                    case 3:
                        //set yellow text to list of artifacts, call item class/method
                        //probably don't do before jan presentation
                        done = true;
                        break;
                    default:
                        System.out.println("lol something went wrong. Enemy: " + enemy + " Player: " + player + " Action: " + option[0] + " option: " + option[1]);
                        done = true;
                }

                if (done) {
                    done = false;
                    visual.resetValues();
                    sound.resetValues();
                    spacial.resetValues();
                    //reset values for all spells
                    whoStage++;
                    state = CURRENT_STATE.RoundStart;
                    atkState = ATTACK_STATE.NA;
                }
            }
        }
    }

    public void render(Graphics g) {
        if (game.gameState == Game.STATE.Combat) {
                if (state == CURRENT_STATE.PlayerAttack || state == CURRENT_STATE.PlayerSpell) {
                    g.setColor(Color.CYAN);
                    g.fillRect((int) (WIDTH * 0.12), (int) (HEIGHT * 0.75), 110, 50);
                    g.fillRect((int) (WIDTH * 0.32), (int) (HEIGHT * 0.75), 110, 50);
                    g.fillRect((int) (WIDTH * 0.52), (int) (HEIGHT * 0.75), 110, 50);
                    g.fillRect((int) (WIDTH * 0.72), (int) (HEIGHT * 0.75), 110, 50);
                }

                if (state == CURRENT_STATE.PlayerAction) {
                    g.setColor(Color.RED);
                    g.setFont(fnt2);
                    //g.drawString("Click an action to make your move!", (int) (WIDTH * 0.15), (int) (HEIGHT * 0.8));
                    g.drawString(((StandardNPC) enemy).string, (int) (WIDTH * 0.15), (int) (HEIGHT * 0.8));
                } else if (state == CURRENT_STATE.Enemy) {
                    g.setColor(Color.PINK);
                    g.fillRect(0, 0, WIDTH, HEIGHT);
                    combatHandler.render(g);
                } else if (state == CURRENT_STATE.PlayerAttack) {

                    g.setColor(Color.BLACK);
                    g.setFont(fnt);

                    for (int i = 0; i < 4; i++) {
                        if (game.user.atkInventory[i] != null) g.drawString(game.user.atkInventory[i], (int) (WIDTH * (0.125 + (i * 0.2))), (int) (HEIGHT * 0.82));
                        else g.drawString("Slot Empty", (int) (WIDTH * (0.125 + (i * 0.2))), (int) (HEIGHT * 0.82));
                    }
                } else if (state == CURRENT_STATE.PlayerSpell) {
                    g.setColor(Color.BLACK);
                    g.setFont(fnt);

                    for (int i = 0; i < 4; i++) {
                        if (game.user.splInventory[i] != null) g.drawString(game.user.splInventory[i], (int) (WIDTH * (0.125 + (i * 0.2))), (int) (HEIGHT * 0.82));
                        else g.drawString("Spell", (int) (WIDTH * (0.125 + (i * 0.2))), (int) (HEIGHT * 0.82));
                    }
                } else if (state == CURRENT_STATE.PlayerArtifact) {
                    //open inventory
                    g.setColor(Color.GREEN);
                    g.fillRect((int) (WIDTH * 0.1), (int) (HEIGHT * 0.26), (int) (WIDTH * 0.805), (int) ((WIDTH * 0.6)));
                    g.setColor(Color.RED);
                    g.fillRect((int) (WIDTH * 0.9) - 4, (int) (HEIGHT * 0.26) - 8, 16, 16);
                    g.setColor(Color.BLACK);
                    g.setFont(fnt);
                    g.drawString("X", (int) (WIDTH * 0.9) - 2, (int) (HEIGHT * 0.27));
                } else if (state == CURRENT_STATE.PlayerReason) {
                    g.setColor(Color.CYAN);
                    g.fillRect((int) (WIDTH * 0.12), (int) (HEIGHT * 0.75), 150, 50);
                    g.fillRect((int) (WIDTH * 0.39), (int) (HEIGHT * 0.75), 150, 50);
                    g.fillRect((int) (WIDTH * 0.66), (int) (HEIGHT * 0.75), 150, 50);

                    g.setColor(Color.BLACK);
                    g.setFont(fnt);
                    g.drawString("Pathos", (int) (WIDTH * 0.17), (int) (HEIGHT * 0.82));
                    g.drawString("Ethos", (int) (WIDTH * 0.44), (int) (HEIGHT * 0.82));
                    g.drawString("Logos", (int) (WIDTH * 0.71), (int) (HEIGHT * 0.82));
                } else if (state == CURRENT_STATE.PlayerHappening) {
                    if (atkState == ATTACK_STATE.Music) {
                        sound.render(g);
                    } else if (atkState == ATTACK_STATE.VA) {
                        visual.render(g);
                    } else if (atkState == ATTACK_STATE.Theatre) {
                        spacial.render(g);
                    } else if (atkState == ATTACK_STATE.Ink) {
                        ink.render(g);
                    } else if (atkState == ATTACK_STATE.Ray) {
                        ray.render(g);
                    } else if (atkState == ATTACK_STATE.Shadow) {
                        shadow.render(g);
                    } else if (atkState == ATTACK_STATE.Sonnet) {
                        sonnet.render(g);
                    } else if (atkState == ATTACK_STATE.Thunder) {
                        thunder.render(g);
                    }
                }
        }
    }
}
