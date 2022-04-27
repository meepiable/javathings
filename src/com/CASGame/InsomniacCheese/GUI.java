package com.CASGame.InsomniacCheese;

import java.awt.*;
import java.awt.List;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;

import static com.CASGame.InsomniacCheese.Game.*;

public class GUI extends MouseAdapter {

    private final Game game;

    //textbox gui
    public static boolean textActive = false;
    private static boolean nextText = false;
    private static int k = 0;
    private static boolean done = false;
    private static boolean last = false;
    private static List texts = new List();
    private static List oldTexts;
    private static boolean test = false;
    private static Font fnt = new Font("Arial", Font.PLAIN, 12);;

    //player GUI
    public static boolean playerActive = false;
    private static Font fnt2 = new Font("Arial", Font.PLAIN, 16);
    private static String[] playerStatNames = {"Morality", "Total Will to Live", "Current Will to Live", "Agility", "Power", "Intelligence", "Ethos", "Pathos", "Logos", "Kill Count", "Reason Count"};

    //inventory gui
    public static boolean windowActive = false;
    public static boolean invUpdate = false;
    private static ArrayList listOfItems;
    private static ArrayList numOfItems;
    private static int pageNum = 0;
    private static int maxPage = 1;

    //reply gui
    private static boolean replyActive = false;
    private static int argsLength = -1;
    private static boolean[] replyList;
    private static boolean[] tempReply;
    public static boolean cheese = false;

    public GUI(Game game) {
        this.game = game;
    }

    private boolean mouseOver(int mx, int my, int x, int y, int width, int height) {
        if (mx > x && mx < x + width) {
            return my > y && my < y + height;
        } else
            return false;
    }

    public void mousePressed(MouseEvent e) {

        int mx = e.getX();
        int my = e.getY();

        if (Game.gameState == Game.STATE.NonCombat) {
            //if (Backdrop.currentBackdrop != Backdrop.BACKDROP.Transition) textActive = true;
            if (windowActive) if (listOfItems != null && numOfItems != null) nextPage();//clicks while window is active
            if (playerActive) {
                for (int i = 0; i < 9; i++) {
                    if (mouseOver(mx, my, 32 * 8 - 10, 32 * (i + 1) - 23, 380, 30) && i != 2 && i != 0) {
                        switch (playerStatNames[i]) {
                            case "Total Will to Live":
                                if (user.getInventory().get("Knowledge Fragment") >= 2) {
                                    user.setWill(user.getWill() + 20);
                                    user.editInventory("Knowledge Fragment", -2);
                                }
                                break;
                            case "Agility":
                                if (user.getInventory().get("Knowledge Fragment") >= 2) {
                                    user.setAgility(user.getAgility()+5);
                                    user.editInventory("Knowledge Fragment", -2);
                                }
                                break;
                            case "Power":
                                if (user.getInventory().get("Knowledge Fragment") >= 2) {
                                    user.setPower(user.getPower() + 5);
                                    user.editInventory("Knowledge Fragment", -2);
                                }
                                break;
                            case "Intelligence":
                                if (user.getInventory().get("Knowledge Fragment") >= 2) {
                                    user.setIntelligence(user.getIntelligence() + 5);
                                    user.editInventory("Knowledge Fragment", -2);
                                }
                                break;
                            case "Ethos":
                                if (user.getInventory().get("Knowledge Fragment") >= 3) {
                                    user.reason[1] += 5;
                                    user.editInventory("Knowledge Fragment", -3);
                                }
                                break;
                            case "Pathos":
                                if (user.getInventory().get("Knowledge Fragment") >= 3) {
                                    user.reason[0] += 5;
                                    user.editInventory("Knowledge Fragment", -3);
                                }
                                break;
                            case "Logos":
                                if (user.getInventory().get("Knowledge Fragment") >= 3) {
                                    user.reason[2] += 5;
                                    user.editInventory("Knowledge Fragment", -3);
                                }
                                break;
                            default:
                                System.out.println("h");
                                break;
                        }
                    }
                }
            }

            if (textActive && k == 4 && last) done = true;
            if (textActive && mouseOver(mx, my, (int) (WIDTH * 0.1), (int) (HEIGHT * 0.72), (int) (WIDTH * 0.7 + 130 - (WIDTH * 0.1)), 100)) nextText = true;
            if (replyActive && argsLength != -1) for (int i = 0; i < argsLength; i++) if (mouseOver(mx, my, (int) (WIDTH*0.1), (int) (HEIGHT*(0.42 + (i*0.2))), (int) (WIDTH * 0.7 + 130 - (WIDTH * 0.1)), (int) (HEIGHT*0.2))) replyList[i] = true;
        }
    }

    public static boolean renderTextBox(Graphics g, String text) {
        //lag here
        if (textActive && gameState == STATE.NonCombat) {
            g.setColor(Color.YELLOW);
            g.fillRect((int) (WIDTH * 0.1), (int) (HEIGHT * 0.72), (int) (WIDTH * 0.7 + 130 - (WIDTH * 0.1)), 300);
            g.setColor(Color.BLACK);
            g.setFont(fnt);
            if (text.length() > 70) {
                //split text into multiple
                //lag here (it keeps adding to texts without caring if it stops)
                for (int i = 0; i <= (int) (Math.ceil((text.length()) / 70.0)) && !test; i++) {
                    if (70 * (i + 1) <= text.length()) {
                        texts.add(text.substring((i * 70), 70 * (i + 1)), i);
                    } else {
                        texts.add(text.substring(((i) * 70)), i);
                        test = true;
                        //probably add a variable here to stop it from adding any more
                        break;
                    }
                }

                for (int i = 0; i <= texts.getItemCount(); i++) {
                    k = i;
                    if (i == 4 && (text.length() / 70) >= 4) {
                        last = false;
                        if (nextText) {
                            k = 0;
                            renderTextBox(g, text.substring(140));
                        }
                        break;
                    } else if (i == 4 && (text.length() / 70) < 4) {
                        last = true;
                        if (done) {
                            textActive = false;
                            nextText = false;
                            k = 0;
                            last = false;
                            done = false;
                            break;
                        }
                    } else if (i < 4){
                        if (i == texts.getItemCount()) {
                            k = 4;
                            last = true;
                            if (done) {
                                textActive = false;
                                nextText = false;
                                k = 0;
                                last = false;
                                done = false;
                                break;
                            }
                        } else g.drawString(texts.getItem(i), (int) (WIDTH * 0.12), (int) (HEIGHT * 0.77 + (i * 20)));
                    }

                }
            } else {
                last = true;
                k = 4;
                g.drawString(text, (int) (WIDTH * 0.1), (int) (HEIGHT * 0.72 + (20)));

                if (done) {
                    textActive = false;
                    nextText = false;
                    k = 0;
                    last = false;
                    done = false;
                }
            }

            oldTexts = texts;


            return k == 0;
        }


        return false;
    }

    public static void renderShopGUI(HashMap<String, Integer> items, Graphics g) {
        g.setColor(Color.GREEN);
        g.drawRoundRect(0,0, WIDTH-10, HEIGHT-10, 10, 10);
    }

    public static void renderPlayerStatsGUI(Graphics g) {
        if (!playerActive) return;
        //stats for readability
        int moral = user.getMorality();
        int will = user.getWill();
        int hp = user.getHP();
        int agility = user.getAgility();
        int power = user.getPower();
        int intelligence = user.getIntelligence();
        int ethos = user.reason[1];
        int pathos = user.reason[0];
        int logos = user.reason[2];
        int killCount = user.getKillCount();
        int reasonCount = user.getReasonCount();
        int[] playerStats = {moral, will, hp, agility, power, intelligence, ethos, pathos, logos, killCount, reasonCount};

                //set backdrop
        g.setColor(Color.CYAN);
        g.fillRect(0, 0, WIDTH, HEIGHT);

        //set color and font
        g.setColor(Color.BLACK);
        g.setFont(fnt2); //let's hope this doesn't lag the game

        for (int i = 0; i < playerStats.length; i++) {
            g.drawString(playerStatNames[i] + ": " + playerStats[i], 32, 32 * (i + 1));
            if (i <= 8 && i != 2 && i != 0) {

                if (user.getInventory().containsKey("Knowledge Fragment")) {

                    switch (i) {
                        case 1:
                            if (user.getInventory().get("Knowledge Fragment") >= 2) {
                                g.drawRect(32 * 8 - 10, 32 * (i + 1) - 23, 380, 30);//draw buttons
                                g.drawString("Spend 2 Knowledge Fragments to add 20 to this stat", 32 * 8 - 6, 32 * (i + 1) - 3);
                            }
                            break;
                        case 3:
                        case 4:
                        case 5:
                            if (user.getInventory().get("Knowledge Fragment") >= 2) {
                                g.drawRect(32 * 8 - 10, 32 * (i + 1) - 23, 380, 30);//draw buttons
                                g.drawString("Spend 2 Knowledge Fragments to add +5 to this stat", 32 * 8 - 6, 32 * (i + 1) - 3);
                            }
                            break;
                        case 6:
                        case 7:
                        case 8:
                            if (user.getInventory().get("Knowledge Fragment") >= 3) {
                                g.drawRect(32 * 8 - 10, 32 * (i + 1) - 23, 380, 30);//draw buttons
                                g.drawString("Spend 3 Knowledge Fragments to add +5 to this stat", 32 * 8 - 6, 32 * (i + 1) - 3);
                            }
                            break;
                    }
                }
            }
        }
    }

    public static void renderInventoryGUI(Graphics g, HashMap<String, Integer> playerInventory) {
        if (!windowActive) return;

        if (invUpdate) {
            if (playerInventory.size() > 1) {
                listOfItems = new ArrayList<>(playerInventory.keySet());
                numOfItems = new ArrayList<Integer>(playerInventory.values());
                //maxPage = (int) Math.ceil(listOfItems.size()/148.0);
                invUpdate = false;
            } else {
                listOfItems = new ArrayList<>();
                listOfItems.add("Hi, nothing to see here");
                numOfItems = new ArrayList();
                numOfItems.add(0);
            }
        }

        //set backdrop
        g.setColor(Color.CYAN);
        g.fillRect(0, 0, WIDTH, HEIGHT);

        //set color and font
        g.setColor(Color.BLACK);
        g.setFont(fnt);

        //3 columns
        int column = 0; //0, 1, 2

        //draw strings
        for (int i = 0; i < listOfItems.size(); i++) {
            if (i + (36 * column) >= listOfItems.size()) break;
            //System.out.println(i + (36 * column) + " col");
            String itemName = (String) listOfItems.get(i + (36 * column));
            int itemNum = (int) numOfItems.get(i + (36 * column));

            //if current position is larger than height of the game window
            if (i >= 37) {
                //if not on third column
                if (column < 3) {
                    //go to next column
                    column++;
                    i = 0;
                } else {
                    break;
                }
            }

            int y = (12 + (i * 12));
            //change x to be 5 (original value) + (column number * 200);
            int x = 12 + (column * 150);

            //draw string (hopefully won't lag 200x)
            g.drawString(itemName + ", " + itemNum, x, y);
        }
    }

    public static boolean[] renderReplyMethod(String[] args, Graphics g) {
        if (cheese) {
            replyList = null;
            cheese = false;
        }

        argsLength = args.length;

        if (!containsTrue(replyList)) {
            replyList = new boolean[argsLength];

            replyActive = true;
            g.setColor(Color.YELLOW);
            g.fillRect((int) (WIDTH * 0.1), (int) (HEIGHT * 0.32), (int) (WIDTH * 0.7 + 130 - (WIDTH * 0.1)), 300);
            Font fnt = new Font("Arial", Font.PLAIN, 25);
            g.setFont(fnt);
            g.setColor(Color.BLACK);

            for (int i = 0; i < argsLength; i++) {
                g.drawString(args[i], (int) (WIDTH * 0.1), (int) (HEIGHT * (0.47 + (i * 0.2))));
            }
        } else {
            replyActive = false;
        }
        return replyList;
    }

    private static void nextPage() {
        //delete current displaying
        try {
            if (listOfItems.size() >= 148 && numOfItems.size() >= 148) {
                for (int i = 0; i < 148; i++) listOfItems.remove(i);
                for (int i = 0; i < 148; i++) numOfItems.remove(i);
                pageNum++;
            }
        } catch (IndexOutOfBoundsException e) {
            System.out.println("lmao don't care + ratio + cope + seethe + mald + " + e); //this is great
        }

    }
}