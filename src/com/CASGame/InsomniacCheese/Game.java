package com.CASGame.InsomniacCheese;

import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Random;

import com.CASGame.InsomniacCheese.Backdrop.BACKDROP;
import com.CASGame.InsomniacCheese.NPCs.StandardNPC;

import javax.swing.*;

public class Game extends Canvas implements Runnable{

    public static final int WIDTH = 640;
    public static final int HEIGHT = WIDTH/12 * 9;

    private Thread thread;
    private boolean running = false;
    private final Handler handler;
    public final Window window;
    public static JFrame frame;

    public Random r = new Random();
    private final Combat combat;
    private final HUD hud;
    private final CombatHandler combatHandler;
    private final Backdrop backdrop;
    private final Momento momento;
    private final CombatBackdrop cBackdrop;
    public static Player user;
    private final BufferedImageLoader bil;
    private final GUI gui;
    public static BufferedImage[] sprites = new BufferedImage[40];
    private boolean h = false;
    public static int deathCount = 0;

    public enum STATE {
        OutGame,
        NonCombat,
        Combat,
        Death,
    }
    public Game() throws IOException {
        gui = new GUI(this);
        bil = new BufferedImageLoader();
        handler = new Handler();
        momento = new Momento();
        combatHandler = new CombatHandler();
        combat = new Combat(this, handler, combatHandler);
        backdrop = new Backdrop(this, handler, combatHandler, combat, bil);
        if ((new File("src/com/CASGame/InsomniacCheese/tmp/backdrop.ser").exists())) {
            if (load("backdrop.ser") != null) {
                for (int i = 0; i < backdrop.sprites2.length; i++) {
                    if (backdrop.sprites2[i] instanceof StandardNPC) ((StandardNPC) backdrop.sprites2[i]).defeated = ((StandardNPC)((Momento) load("backdrop.ser")).field2[i]).defeated;
                    else if (backdrop.sprites2[i] instanceof KnowledgeFragments) ((KnowledgeFragments) backdrop.sprites2[i]).collected = ((KnowledgeFragments)((Momento) load("backdrop.ser")).field2[i]).collected;
                }
            }
        }

        hud = new HUD(combat);
        cBackdrop = new CombatBackdrop(this, combat, combatHandler, hud, handler);

        user = new Player((int) (WIDTH / 2 - 32), (int) (HEIGHT / 2 - 32), ID.Player, handler, this, 32, 32, combat, 3, 500, 40, 100, 50, 500, 25, 25, 25);
        //edit stats if exist
        if ((new File("src/com/CASGame/InsomniacCheese/tmp/user.ser").exists())) {
            if (load("user.ser") != null) {
                user.setMorality(((Player) load("user.ser")).getMorality());
                user.setWill(((Player) load("user.ser")).getWill());
                user.setAgility(((Player) load("user.ser")).getAgility());
                user.setPower(((Player) load("user.ser")).getPower());
                user.setIntelligence(((Player) load("user.ser")).getIntelligence());
                user.setHP(((Player) load("user.ser")).getHP());
                user.splInventory = ((Player) load("user.ser")).splInventory;
                user.atkInventory = ((Player) load("user.ser")).atkInventory;
                user.inventory = ((Player) load("user.ser")).inventory;
                user.reason = ((Player) load("user.ser")).reason;
                user.x = ((Player) load("user.ser")).x;
                user.y = ((Player) load("user.ser")).y;
                user.setKillCount(((Player) load("user.ser")).getKillCount());
                user.setReasonCount(((Player) load("user.ser")).getReasonCount());
            }
        }

        this.addKeyListener(new KeyInput(handler, this, combat, combatHandler));
        this.addMouseListener(backdrop);
        this.addMouseListener(combat);
        this.addMouseListener(gui);

        handler.addObject(user);
        window = new Window(WIDTH, HEIGHT, "CAS Game", this);
        frame = window.getFrame();
    }

    public static STATE gameState = STATE.OutGame;

    public synchronized void start() {
        thread = new Thread(this);
        thread.start();
        running = true;
    }

    public synchronized void stop() {
        try {
            thread.join();
            running = false;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void run() {
        this.requestFocus();
        long lastTime = System.nanoTime();
        double amountOfTicks = 24.0;
        double ns = 1000000000 / amountOfTicks;
        double delta = 0;
        long timer = System.currentTimeMillis();
        int frames = 0;
        while (running) {
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;
            while (delta >= 1) {
                tick();
                delta--;
            }
            if (running) {
                try {
                    render();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            frames++;

            if (System.currentTimeMillis() - timer > 1000) {
                timer += 1000;
                //System.out.println(frames + " fps");
                //System.out.println(System.currentTimeMillis()/1000 + " time");
                frames = 0;
            }
        }
        stop();
    }

    private void tick() {
        //System.out.println(GUI.textActive);
        //tick loop
        if (gameState == STATE.NonCombat) {
            //load entities
            handler.tick();
            backdrop.tick();
            //load combat
        } else if (gameState == STATE.Combat) {
            combat.tick();
        }

        //load menu
        else if (gameState == STATE.OutGame) {
            backdrop.tick();
        }
    }

    private void render() throws IOException {
        BufferStrategy bs = this.getBufferStrategy();
        if (bs == null) {
            this.createBufferStrategy(3);
            return;
        }

        Graphics g = bs.getDrawGraphics();

        if (gameState == STATE.NonCombat) {
            //do something about saved progress, but if not, can edit location hereqw
            //check if last thing was menu
            if (Backdrop.lastBackdrop == BACKDROP.Menu) {
                if (!h) {
                    if ((new File("src/com/CASGame/InsomniacCheese/tmp/backdrop.ser").exists())) {
                        if (load("backdrop.ser") != null) {
                            Backdrop.gg = false;
                            backdrop.changeState(((Momento) load("backdrop.ser")).field);
                        }
                    } else {
                        Backdrop.gg = false;
                        backdrop.changeState(BACKDROP.Map114);
                    }
                    h = true;
                }
            }

            backdrop.render(g);
            GUI.renderInventoryGUI(g, user.getInventory());
            GUI.renderPlayerStatsGUI(g);
        } else if (gameState == STATE.OutGame) {
            //load menu
            Backdrop.changeState(BACKDROP.Menu);
            backdrop.render(g);
        } else if (gameState == STATE.Death) {
            Backdrop.changeState(BACKDROP.Death);
            backdrop.render(g);
        } else if (gameState == STATE.Combat) {
            //load combat
            CombatBackdrop.backdropCurrent = CombatBackdrop.CBACKDROP.Combat1;
            cBackdrop.render(g);
        }
        g.dispose();
        bs.show();
    }

    public static float clamp(float var, float min, int max) {
        if (var >= max) return var = max;
        else if (var <= min) return var = min;
        else return var;
    }

    public boolean probSim(int percentageChance) {
        return (r.nextInt(100) < percentageChance);
    }

    public void saveGame() {
        if (!(user == null) && !(backdrop == null)) {
            try {
                System.out.println("Saving user data. Do not quit the application");
                FileOutputStream fos = new FileOutputStream("src/com/CASGame/InsomniacCheese/tmp/user.ser");
                ObjectOutputStream oos = new ObjectOutputStream(fos);
                oos.writeObject(user);
                oos.close();
                fos.close();
                System.out.println("Saved user data");

                System.out.println("Saving locational data. Do not quit the application");
                File tempFile = new File("src/com/CASGame/InsomniacCheese/tmp/backdrop.ser");
                FileOutputStream fos2 = new FileOutputStream("src/com/CASGame/InsomniacCheese/tmp/backdrop.ser");
                ObjectOutputStream oos2 = new ObjectOutputStream(fos2);
                momento.field = Backdrop.currentBackdrop;
                momento.field2 = backdrop.sprites2;
                oos2.writeObject(momento);
                oos2.close();
                fos2.close();
                System.out.println("Saved locational data");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public Object load(String fileName) {
        Object cheese;
        try {
            FileInputStream fis = new FileInputStream("src/com/CASGame/InsomniacCheese/tmp/" + fileName);
            ObjectInputStream ois = new ObjectInputStream(fis);
            cheese = ois.readObject();
            ois.close();
            fis.close();
            return cheese;
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static boolean containsTrue(boolean[] arr) {
        if (arr == null) return false;
        for(boolean b: arr) if (b) return true;
        return false;
    }

    public static void main(String[] args) throws IOException {
        new Game();
    }
}