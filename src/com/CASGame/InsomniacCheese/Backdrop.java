package com.CASGame.InsomniacCheese;

import com.CASGame.InsomniacCheese.Enemies.type1;
import com.CASGame.InsomniacCheese.NPCs.*;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.Serializable;

import static com.CASGame.InsomniacCheese.GUI.*;
import static com.CASGame.InsomniacCheese.Game.*;

class Momento implements Serializable {
    public Backdrop.BACKDROP field;
    public GameObject[] field2;
}

public class Backdrop extends MouseAdapter implements Serializable {
    private transient final Game game;
    private transient static Handler handler;
    private transient final BufferedImageLoader bil;
    private transient final CombatHandler combatHandler;
    private transient final Combat combat;
    public transient BufferedImage[] img = new BufferedImage[20];
    private transient int red = 230;
    private transient int blue = 250;
    private transient int green = 230;
    private transient boolean transition = false;
    private transient boolean h = false;
    private transient boolean gh = false;
    private transient Font fnt = new Font("arial", Font.BOLD, 50);
    private transient Font fnt2 = new Font("arial", Font.PLAIN, 30);
    private transient Font fnt3 = new Font("arial", Font.PLAIN, 15);

    //sprites
    public GameObject[] sprites2;
    private final ClayGolem golem11_1;
    private final ClayGolem golem19_1;
    private final ClayGolem golem13_1;
    private final ClayGolem golem101_1;
    private final ClayGolem golem15_1;
    private final ClayGolem golem113_1;
    private final ClayGolem golem17_1;
    private final ClayGolem golem17_2;
    private final ClayGolem golem17_3;
    private final KilnHardenedGolem kGolem13_1;
    private final LivingStatue statue11_1;
    private final LivingStatue statue11_2;
    private final LivingStatue statue19_1;
    private final LivingStatue statue16_1;
    private final LivingStatue statue13_1;
    private final RogueArtist rogue116_1;

    //book/knowledge fragments
    private final Book book1NorthHouse_2;
    private final Book book1SouthHouse;
    private final Book book1EastHouse;
    private final Book book1WestHouse;
    private final KnowledgeFragments kF1NorthHouse_2;
    private final KnowledgeFragments kF1SouthHouse;
    private final KnowledgeFragments kF1EastHouse;
    private final KnowledgeFragments kF1WestHouse;

    //list of all the backdrops (like map 1-1, map 1-2, etc etc)
    public enum BackdropType {
        North,
        East,
        South,
        West,
        HouseNorth,
        HouseNorth2,
        HouseSouth,
        HouseEast,
        HouseWest,
        HouseExitNorth,
        HouseExitNorth2,
        HouseExitSouth,
        HouseExitEast,
        HouseExitWest,
        NA
    }
    //idea: if have north, do blah, if have east, do blah, if have south, do blah, and if have west, do blah
    public enum BACKDROP {
        Menu (new BackdropType[]{BackdropType.NA}, -1, -1),
        Help (new BackdropType[]{BackdropType.NA}, -2, -2),
        Test (new BackdropType[]{BackdropType.NA}, -3, -2),
        Map11 (new BackdropType[]{BackdropType.East, BackdropType.South, BackdropType.HouseNorth2}, 1, 1),
        Map12 (new BackdropType[]{BackdropType.East, BackdropType.West}, 2, 1),
        Map13 (new BackdropType[]{BackdropType.North, BackdropType.East, BackdropType.South, BackdropType.West}, 3, 1),
        Map14 (new BackdropType[]{BackdropType.East, BackdropType.West, BackdropType.HouseSouth}, 4, 1),
        Map15 (new BackdropType[]{BackdropType.West, BackdropType.South}, 5, 1),
        Map16 (new BackdropType[]{BackdropType.North, BackdropType.South, BackdropType.HouseWest}, 1, 2),
        Map17 (new BackdropType[]{BackdropType.North, BackdropType.South}, 3, 2),
        Map18 (new BackdropType[]{BackdropType.North, BackdropType.South, BackdropType.HouseEast}, 5, 2),
        Map19 (new BackdropType[]{BackdropType.North, BackdropType.East}, 1, 3),
        Map110 (new BackdropType[]{BackdropType.East, BackdropType.West, BackdropType.HouseNorth}, 2, 3),
        Map111 (new BackdropType[]{BackdropType.North, BackdropType.East, BackdropType.South, BackdropType.West}, 3, 3),
        Map112 (new BackdropType[]{BackdropType.East, BackdropType.West}, 4, 3),
        Map113 (new BackdropType[]{BackdropType.North, BackdropType.West}, 5, 3),
        Map114 (new BackdropType[]{BackdropType.North, BackdropType.South}, 3, 4),
        Map115 (new BackdropType[]{BackdropType.North}, 3, 5),
        Map116 (new BackdropType[]{BackdropType.North, BackdropType.South}, 3, 0),
        Death (new BackdropType[]{BackdropType.NA}, -4, -4),
        Transition (new BackdropType[]{BackdropType.NA}, -5, -5),
        NorthHouse (new BackdropType[]{BackdropType.HouseExitNorth2}, 1, 0),
        NorthHouseTwo (new BackdropType[]{BackdropType.HouseExitNorth}, 2, 2),
        EastHouse (new BackdropType[]{BackdropType.HouseExitEast}, 6, 2),
        SouthHouse (new BackdropType[]{BackdropType.HouseExitSouth}, 4, 2),
        WestHouse (new BackdropType[]{BackdropType.HouseExitWest}, 0, 2);

        private final BackdropType[] type;
        private int tempX;
        private int tempY;
        private int x;
        private int y;
        BACKDROP(BackdropType[] type, int x, int y) {
            this.type = type;
            this.x = x;
            this.y = y;
            this.tempX = x;
            this.tempY = y;
        }
        private BackdropType[] returnType() {
            return type;
        }

        private int returnX() {
            return x;
        }
        private int returnY() {
            return y;
        }

        public int[] getCoords() {
            return new int[]{x, y};
        }

        private void changeX(int x) {
            if (this.x < 0) return;
            changeBack(x, y);
        }

        private void changeY(int y) {
            if (this.y < 0) return;
            changeBack(x, y);
        }

        public BACKDROP lookForNum(int x, int y) {
            switch (y + "," + x) {
                case "1,1":
                    return Map11;
                case "1,2":
                    return Map12;
                case "1,3":
                    return Map13;
                case "1,4":
                    return Map14;
                case "1,5":
                    return Map15;
                case "2,1":
                    return Map16;
                case "2,3":
                    return Map17;
                case "2,5":
                    return Map18;
                case "3,1":
                    return Map19;
                case "3,2":
                    return Map110;
                case "3,3":
                    return Map111;
                case "3,4":
                    return Map112;
                case "3,5":
                    return Map113;
                case "4,3":
                    return Map114;
                case "5,3":
                    return Map115;
                case "0,3":
                    return Map116;
                case "0,1":
                    return NorthHouse;
                case "2,2":
                    return NorthHouseTwo;
                case "2,6":
                    return EastHouse;
                case "2,4":
                    return SouthHouse;
                case "2,0":
                    return WestHouse;
                default:
                    System.out.println(y + "," + x);
                    return null;
            }
        }
        public void changeBack(int x, int y) {
            gg = false;
            if (x < 0 || y < 0) return;
            handler.object.clear();
            handler.object.add(Game.user);
            changeState(lookForNum(x, y));
        }
    }//etc etc

    public static BACKDROP currentBackdrop = BACKDROP.Menu;
    public transient static BACKDROP lastBackdrop;
    public transient static BACKDROP nextBackdrop;

    public Backdrop(Game game, Handler handler, CombatHandler combatHandler, Combat combat, BufferedImageLoader bil) throws IOException {
        this.game = game;
        Backdrop.handler = handler;
        this.combatHandler = combatHandler;
        this.combat = combat;
        this.bil = bil;
        //load sprites
        sprites[0] = bil.loadImage("Sprites/World1-resized/book-of-lost-notes.png");
        sprites[1] = bil.loadImage("Sprites/World1-resized/clay mask.png");
        sprites[2] = bil.loadImage("Sprites/World1-resized/gem of mystique.png");
        sprites[3] = bil.loadImage("Sprites/World1-resized/guardian of auditory.png");
        sprites[4] = bil.loadImage("Sprites/World1-resized/guardian of visual.png");
        sprites[5] = bil.loadImage("Sprites/World1-resized/harp of the forbidden.png");
        sprites[6] = bil.loadImage("Sprites/World1-resized/knowledge fragment.png");
        sprites[7] = bil.loadImage("Sprites/World1-resized/large health potion.png");
        sprites[8] = bil.loadImage("Sprites/World1-resized/small health potion.png");
        sprites[9] = bil.loadImage("Sprites/World1-resized/mirror of abstraction.png");
        sprites[10] = bil.loadImage("Sprites/World1-resized/staff of the tragicomic.png");
        sprites[11] = bil.loadImage("Sprites/World1-resized/statues.png");
        sprites[12] = bil.loadImage("Sprites/World1-resized/statues 2.png");
        sprites[13] = bil.loadImage("Sprites/World1-resized/stone pillars.png");
        sprites[14] = bil.loadImage("Sprites/World1-resized/chaos golem.png");
        sprites[15] = bil.loadImage("Sprites/World1-resized/chaos golem 2.png");
        sprites[16] = bil.loadImage("Sprites/World1-resized/chaos golem 3.png");
        sprites[17] = bil.loadImage("Sprites/World1-resized/chaos golem 4.png");
        sprites[18] = bil.loadImage("Sprites/World1-resized/clay golem.png");
        sprites[19] = bil.loadImage("Sprites/World1-resized/clay golem 2.png");
        sprites[20] = bil.loadImage("Sprites/World1-resized/clay golem 3.png");
        sprites[21] = bil.loadImage("Sprites/World1-resized/clay golem 4.png");
        sprites[22] = bil.loadImage("Sprites/World1-resized/iron golem.png");
        sprites[23] = bil.loadImage("Sprites/World1-resized/iron golem 2.png");
        sprites[24] = bil.loadImage("Sprites/World1-resized/iron golem 3.png");
        sprites[25] = bil.loadImage("Sprites/World1-resized/iron golem 4.png");
        sprites[26] = bil.loadImage("Sprites/World1-resized/kiln forged golem.png");
        sprites[27] = bil.loadImage("Sprites/World1-resized/kiln forged golem 2.png");
        sprites[28] = bil.loadImage("Sprites/World1-resized/kiln forged golem 3.png");
        sprites[29] = bil.loadImage("Sprites/World1-resized/kiln forged golem 4.png");
        sprites[30] = bil.loadImage("Sprites/World1-resized/rogue artist.png");
        sprites[31] = bil.loadImage("Sprites/World1-resized/rogue artist 2.png");
        sprites[32] = bil.loadImage("Sprites/World1-resized/rogue artist 3.png");
        sprites[33] = bil.loadImage("Sprites/World1-resized/rogue artist 4.png");

        //load uh backdrops
        img[0] = bil.loadImage("Maps/L1-resized/W1L1_1_1.png");
        img[1] = bil.loadImage("Maps/L1-resized/W1L1_1_2.png");
        img[2] = bil.loadImage("Maps/L1-resized/W1L1_1_3.png");
        img[3] = bil.loadImage("Maps/L1-resized/W1L1_1_4.png");
        img[4] = bil.loadImage("Maps/L1-resized/W1L1_1_5.png");
        img[5] = bil.loadImage("Maps/L1-resized/W1L1_2_1.png");
        img[6] = bil.loadImage("Maps/L1-resized/W1L1_2_3.png");
        img[7] = bil.loadImage("Maps/L1-resized/W1L1_2_5.png");
        img[8] = bil.loadImage("Maps/L1-resized/W1L1_3_1.png");
        img[9] = bil.loadImage("Maps/L1-resized/W1L1_3_2.png");
        img[10] = bil.loadImage("Maps/L1-resized/W1L1_3_3.png");
        img[11] = bil.loadImage("Maps/L1-resized/W1L1_3_4.png");
        img[12] = bil.loadImage("Maps/L1-resized/W1L1_3_5.png");
        img[13] = bil.loadImage("Maps/L1-resized/W1L1Top.png");
        img[14] = bil.loadImage("Maps/L1-resized/W1L1Below.png");
        img[15] = bil.loadImage("Maps/L1-resized/W1L1BelowBelow.png");
        img[16] = bil.loadImage("Maps/L1-resized/TopEntrance_H.png");
        img[17] = bil.loadImage("Maps/L1-resized/EastEntrance_H.png");
        img[18] = bil.loadImage("Maps/L1-resized/BottomEntrance_H.png");
        img[19] = bil.loadImage("Maps/L1-resized/WestEntrance_H.png");

        golem11_1 = new ClayGolem((float) (349), (float) (372), ID.Interact, 15, 15, "west", combat, combatHandler, sprites[20], "Die.", "Turn order is determined by the higher agility stat. On your turn, you can use attacks or magic spells that you have prepared. You can also attempt to reason with your foe to get the to stop fighting. Killing  lowers your morality whilst reasoning raises your morality.", true);
        golem19_1 = new ClayGolem((float) (332), (float) (177), ID.Interact, 15, 15, "west", combat, combatHandler, sprites[20], "Die.", "", false);
        golem13_1 = new ClayGolem((float) (276), (float) (81), ID.Interact, 15, 15, "east", combat, combatHandler, sprites[21], "Die.", "", false);
        golem101_1 = new ClayGolem((float) (357), (float) (317), ID.Interact, 15, 15, "west", combat, combatHandler, sprites[20], "Die.", "", false);
        golem15_1 = new ClayGolem((float) (361), (float) (357), ID.Interact, 15, 15, "west", combat, combatHandler, sprites[20], "Die.", "", false);
        golem113_1 = new ClayGolem((float) (276), (float) (2), ID.Interact, 15, 15, "east", combat, combatHandler, sprites[21], "Die.", "", false);
        golem17_1 = new ClayGolem((float) (267), (float) (357), ID.Interact, 15, 15, "east", combat, combatHandler, sprites[21], "Die.", "", false);;
        golem17_2 = new ClayGolem((float) (357), (float) (187), ID.Interact, 15, 15, "west", combat, combatHandler, sprites[20], "Die.", "", false);;
        golem17_3 = new ClayGolem((float) (267), (float) (47), ID.Interact, 15, 15, "east", combat, combatHandler, sprites[21], "Die.", "", false);;
        kGolem13_1 = new KilnHardenedGolem((float) (356), (float) (26), ID.Interact, 15, 15, "west", combat, combatHandler, sprites[28], "Ni hao", "");

        statue13_1 = new LivingStatue((float) (361), (float) (371), ID.Interact, 15, 15, "west", combat, combatHandler, sprites[11], "Why? Why have I become like the sculptures I created?", "");
        statue11_1 = new LivingStatue((float) (243), (float) (298), ID.Interact, 15, 15, "east", combat, combatHandler, sprites[11], "Free me please.", "");
        statue11_2 = new LivingStatue((float) (354), (float) (128), ID.Interact, 15, 15, "west", combat, combatHandler, sprites[12], "We trusted him… and he did this to us.", "");
        statue19_1 = new LivingStatue((float) (282), (float) (242), ID.Interact, 15, 15, "east", combat, combatHandler, sprites[12], "Have you come to help us?", "");
        statue16_1 = new LivingStatue((float) (272), (float) (17), ID.Interact, 15, 15, "east", combat, combatHandler, sprites[12], "Get me out of here, get me out of this stone body.", "");

        rogue116_1 = new RogueArtist((float) (311), (float) (212), ID.Interact, 50, 50, "null", combat, combatHandler, sprites[30], "Die, you Omni Soldier Scum", "");

        kF1NorthHouse_2 = new KnowledgeFragments((float) (191), (float) (315), ID.Interact, 15, 15, sprites[6], handler);
        kF1SouthHouse = new KnowledgeFragments((float) (414), (float) (137), ID.Interact, 15, 15, sprites[6], handler);
        kF1EastHouse = new KnowledgeFragments((float) (434), (float) (302), ID.Interact, 15, 15, sprites[6], handler);
        kF1WestHouse = new KnowledgeFragments((float) (424), (float) (312), ID.Interact, 15, 15, sprites[6], handler);

        book1NorthHouse_2 = new Book((float) (191), (float) (255), ID.Interact, 15, 15, "Day 26: I tried to continue work today but found I lacked even the    strength to move much. This disease, I don’t know what it is, but it  seems to be affecting everything, me, the house, the others, the woods", sprites[0], handler);
        book1SouthHouse = new Book((float) (414), (float) (197), ID.Interact, 15, 15, "They arrived today in their suits of armour, the Omni King has truly  gone corrupt!", sprites[0], handler);
        book1EastHouse = new Book((float) (349), (float) (302), ID.Interact, 15, 15, "'I can no longer feel my muse, since touching the pillars, she has     left me' - Sabbatius IV, Poet extraordinaire", sprites[0], handler);
        book1WestHouse = new Book((float) (344), (float) (312), ID.Interact, 15, 15, "The Golems have gone crazy, I pray their souls will be alright!", sprites[0], handler);


        sprites2 = new GameObject[]{golem11_1, golem19_1, golem13_1, golem101_1, golem15_1, golem113_1, golem17_1, golem17_2, golem17_3, kGolem13_1, statue13_1, statue11_1, statue11_2, statue16_1, statue19_1, rogue116_1, kF1SouthHouse, kF1NorthHouse_2, kF1EastHouse, kF1WestHouse, book1SouthHouse, book1NorthHouse_2, book1EastHouse, book1WestHouse};
    }

    /*
coords: 311.0, 212.0
location: Map116
*/

    public void mousePressed(MouseEvent e) {
        int mx = e.getX();
        int my = e.getY();
        System.out.println(user.getHP());

        if (Game.gameState == Game.STATE.OutGame) {
            //play
            if (mouseOver(mx, my, (int) (Game.WIDTH * 0.5) - 100, (int) (Game.HEIGHT * 0.4) - 32, 200, 64)) {
                if (deathCount < 1) lastBackdrop = currentBackdrop;
                else {
                    currentBackdrop = BACKDROP.Map114;
                }
                Game.gameState = Game.STATE.NonCombat;
                invUpdate = true;
            }

            //help
            if (mouseOver(mx, my, (int) (Game.WIDTH * 0.5) - 100, (int) (Game.HEIGHT * 0.6) - 32, 200, 64)) {
                gg = false;
                changeState(BACKDROP.Help);
            }

            //quit
            if (mouseOver(mx, my, (int) (Game.WIDTH * 0.5) - 100, (int) (Game.HEIGHT * 0.8) - 32, 200, 64) && currentBackdrop == BACKDROP.Menu)
                System.exit(1);
            if (currentBackdrop == BACKDROP.Help && mouseOver(mx, my, (int) (Game.WIDTH * 0.2) - 100, (int) (Game.HEIGHT * 0.8) - 32, 200, 64)) {
                gg = false;
                changeState(BACKDROP.Menu);
            }
        } else if (currentBackdrop == BACKDROP.Death) {
            gg = false;
            changeState(BACKDROP.Menu);
            gameState = STATE.OutGame;
            Game.user.setHP(Game.user.getWill());
        }
    }

    public void mouseReleased(MouseEvent e) {

    }

    private boolean mouseOver(int mx, int my, int x, int y, int width, int height) {
        if (mx > x && mx < x + width) {
            return my > y && my < y + height;
        } else
            return false;
    }


    public void tick() {
        switch(currentBackdrop) {
            case Map11:
                handler.addObject(golem101_1);
                break;
            case Map13:
                handler.addObject(kGolem13_1);
                handler.addObject(golem13_1);
                handler.addObject(statue13_1);
                break;
            case Map15:
                handler.addObject(golem15_1);
                break;
            case Map16:
                handler.addObject(statue16_1);
                break;
            case Map17:
                handler.addObject(golem17_1);
                handler.addObject(golem17_2);
                handler.addObject(golem17_3);
                break;
            case Map19:
                handler.addObject(golem19_1);
                handler.addObject(statue19_1);
                break;
            case Map111:
                handler.addObject(golem11_1);
                handler.addObject(statue11_1);
                handler.addObject(statue11_2);
                break;
            case Map113:
                handler.addObject(golem113_1);
                break;
            case Map116:
                handler.addObject(rogue116_1);
                break;
            case EastHouse:
                handler.addObject(book1EastHouse);
                handler.addObject(kF1EastHouse);
                break;
            case WestHouse:
                handler.addObject(book1WestHouse);
                handler.addObject(kF1WestHouse);
                break;
            case NorthHouse:
            case NorthHouseTwo:
                handler.addObject(book1NorthHouse_2);
                handler.addObject(kF1NorthHouse_2);
                break;
            case SouthHouse:
                handler.addObject(book1SouthHouse);
                handler.addObject(kF1SouthHouse);
                break;
            default:
                break;
        }

        for (int i = 0; i < currentBackdrop.returnType().length; i++) {
            switch (currentBackdrop.returnType()[i]) {//do things
                case North:
                    if (Game.user.x > 273 && Game.user.x < 363 && user.y < 1) {
                        user.y = 412;
                        currentBackdrop.changeY(currentBackdrop.returnY()-1);
                    }
                    break;
                case East:
                    if (user.x > 607 && user.y > 182 && user.y < 287) {
                        user.x = 1;
                        currentBackdrop.changeX(currentBackdrop.returnX()+1);
                    }
                    break;
                case South:
                    if (Game.user.x > 273 && Game.user.x < 363 && user.y > 412) {
                        user.y = 2;
                        currentBackdrop.changeY(currentBackdrop.returnY()+1);
                    }
                    break;
                case West:
                    if (user.x < 1 && user.y > 182 && user.y < 287) {
                        user.x = 607;
                        currentBackdrop.changeX(currentBackdrop.returnX()-1);
                    }
                    break;
                case HouseNorth:
                    if (user.x > 317 && user.x < 342 && user.y < 177) {
                        user.x = 416;//find location
                        user.y = 340;
                        currentBackdrop.changeY(currentBackdrop.returnY()-1);
                    }
                    break;
                case HouseExitNorth:
                    if (user.x > 409 && user.x < 424 && user.y > 340) {
                        user.x = 329;
                        user.y = 177;
                        currentBackdrop.changeY(currentBackdrop.returnY()+1);
                    }
                    break;
                case HouseNorth2:
                    if (user.x > 552 && user.x < 567 && user.y < 161) {
                        user.x = 416;//find location
                        user.y = 340;
                        currentBackdrop.changeY(currentBackdrop.returnY()-1);
                    }
                    break;
                case HouseExitNorth2:
                    if (user.x > 409 && user.x < 424 && user.y > 340) {
                        user.x = 558;
                        user.y = 161;
                        currentBackdrop.changeY(currentBackdrop.returnY()+1);
                    }
                    break;
                case HouseEast:
                    if (user.y > 152 && user.y < 182 && user.x > 381) {
                        user.x = 184;//find location
                        user.y = 122;
                        currentBackdrop.changeX(currentBackdrop.returnX()+1);
                    }
                    break;
                case HouseExitEast:
                    if (user.y > 117 && user.y < 162 && user.x < 144) {
                        user.x = 381;
                        user.y = 167;
                        currentBackdrop.changeX(currentBackdrop.returnX()-1);
                    }
                    break;
                case HouseSouth:
                    if (user.x > 281 && user.x < 316 && user.y > 270) {
                        user.x = 184;//find location
                        user.y = 122;
                        currentBackdrop.changeY(currentBackdrop.returnY()+1);
                    }
                    break;
                case HouseExitSouth:
                    if (user.x > 185 && user.x < 228 && user.y < 122) {
                        user.x = 298;
                        user.y = 270;
                        currentBackdrop.changeY(currentBackdrop.returnY()-1);
                    }
                    break;
                case HouseWest:
                    if (user.y > 155 && user.y < 163 && user.x < 273) {
                        user.x = 459;//find location
                        user.y = 137;
                        currentBackdrop.changeX(currentBackdrop.returnX()-1);
                    }
                    break;
                case HouseExitWest:
                    if (user.y > 127 && user.y < 147 && user.x > 459) {
                        user.x = 273;
                        user.y = 159;
                        currentBackdrop.changeX(currentBackdrop.returnX()+1);
                    }
                    break;
                default:
                    break;
            }
        }
    }

    public void render(Graphics g) {
        switch(currentBackdrop) {
            case Transition:
                Color col = new Color(red, green, blue);
                g.setColor(col);
                g.fillRect(0, 0, WIDTH, HEIGHT);

                if (!transition) {
                    if (red > 0) red--;
                    if (green > 0) green--;
                    if (blue > 0) blue--;
                } else {
                    if (red <= 230) red++;
                    if (green <= 230) green++;
                    if (blue <= 250) blue++;

                    if (red >= 230 || green >= 230 || blue >= 250) {
                        //change to next backdrop
                        lastBackdrop = currentBackdrop;
                        currentBackdrop = nextBackdrop;
                        nextBackdrop = null;
                        transition = false;
                        red = 230;
                        green = 230;
                        blue = 250;
                    }
                }

                if (red == 0 || green == 0 || blue == 0) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    transition = true;
                }
                break;
            case Death:

                g.setColor(Color.BLACK);
                g.fillRect(0, 0, WIDTH, HEIGHT);

                g.setFont(fnt);
                g.setColor(Color.WHITE);
                g.drawString("You're Died. Skill Issue", (int) (Game.WIDTH * 0.1), (int) (Game.HEIGHT * 0.2));
                break;
            case Map11:
                //upload map image

                g.drawImage(img[0], 0, 0, null);
                //items are wip so maybe no need lol
                //game.user.x = Game.clamp(game.user.x, 0, Game.WIDTH-width); //find x coordinate of clamp area x2
                handler.render(g);
                break;
            case Map12:
                g.drawImage(img[1], 0, 0, null);
                handler.render(g);
                break;
            case Map13:
                g.drawImage(img[2], 0, 0, null);
                handler.render(g);
                break;
            case Map14:
                g.drawImage(img[3], 0, 0, null);
                handler.render(g);
                break;
            case Map15:
                g.drawImage(img[4], 0, 0, null);
                handler.render(g);
                break;
            case Map16:
                g.drawImage(img[5], 0, 0, null);
                handler.render(g);
                break;
            case Map17:
                g.drawImage(img[6], 0, 0, null);
                handler.render(g);
                break;
            case Map18:
                g.drawImage(img[7], 0, 0, null);
                handler.render(g);
                break;
            case Map19:
                g.drawImage(img[8], 0, 0, null);
                handler.render(g);
                break;
            case Map110:
                g.drawImage(img[9], 0, 0, null);
                handler.render(g);
                break;
            case Map111:
                g.drawImage(img[10], 0, 0, null);
                handler.render(g);
                break;
            case Map112:
                g.drawImage(img[11], 0, 0, null);
                handler.render(g);
                break;
            case Map113:
                g.drawImage(img[12], 0, 0, null);
                handler.render(g);
                break;
            case Map114:
                g.drawImage(img[14], 0, 0, null);
                handler.render(g, Game.user);
                break;
            case Map115:
                g.drawImage(img[15], 0, 0, null);
                handler.render(g);
                break;
            case Map116:
                g.drawImage(img[13], 0, 0, null);
                if (!h && rogue116_1.defeated) {
                    textActive = true;
                    h = true;
                }

                if (rogue116_1.defeated) {
                    if (rogue116_1.reasoned) {
                        if (textActive)
                            textActive = !renderTextBox(g, "You weren't one of them then, but you wear their uniform. Nevertheless I suggest you flee the forest, it's been infected by the stone curse, and it's infested with golems.");
                    } else {
                        if (textActive)
                            textActive = !renderTextBox(g, "Aaargh, you and your master will never succeed.");
                    }
                }
                handler.render(g);
                break;
            case NorthHouse:
            case NorthHouseTwo:
                g.drawImage(img[18], 0, 0, null);
                handler.render(g);
                break;
            case EastHouse:
                g.drawImage(img[19], 0, 0, null);
                handler.render(g);
                break;
            case SouthHouse:
                g.drawImage(img[16], 0, 0, null);
                handler.render(g);
                break;
            case WestHouse:
                g.drawImage(img[17], 0, 0, null);
                handler.render(g);
                break;
            case Menu:
                g.setColor(Color.black);
                g.fillRect(0, 0, WIDTH, HEIGHT);
                g.setColor(Color.CYAN);
                g.setFont(fnt);
                g.drawString("CAS Game", (int) (Game.WIDTH * 0.1), (int) (Game.HEIGHT * 0.2));

                g.setColor(Color.WHITE);
                g.setFont(fnt2);
                g.drawRect((int) (Game.WIDTH * 0.5) - 100, (int) (Game.HEIGHT * 0.4) - 32, 200, 64);
                g.drawString("PLAY", (int) (Game.WIDTH * 0.44), (int) (Game.HEIGHT * 0.42));

                g.drawRect((int) (Game.WIDTH * 0.5) - 100, (int) (Game.HEIGHT * 0.6) - 32, 200, 64);
                g.drawString("HELP", (int) (Game.WIDTH * 0.44), (int) (Game.HEIGHT * 0.62));

                g.drawRect((int) (Game.WIDTH * 0.5) - 100, (int) (Game.HEIGHT * 0.8) - 32, 200, 64);
                g.drawString("QUIT", (int) (Game.WIDTH * 0.44), (int) (Game.HEIGHT * 0.82));
                break;
            case Help:
                g.setColor(Color.black);
                g.fillRect(0, 0, WIDTH, HEIGHT);
                g.setColor(Color.WHITE);
                g.setFont(fnt);
                g.drawString("Help", (int) (Game.WIDTH * 0.34), (int) (Game.HEIGHT * 0.1));

                g.setFont(fnt3);
                //movement
                g.drawString("Use the WASD or arrow keys to move. Walking in front of enemies means that they fight you.", (int) (WIDTH*0.01), (int) (HEIGHT*0.17));
                g.drawString("Speaking of fights, who goes first is determined by the agility stat of both participants.", (int) (WIDTH*0.01), (int) (HEIGHT*0.22));
                g.drawString("The player can choose the next attack, where you select your action,", (int) (WIDTH*0.01), (int) (HEIGHT*0.27));
                g.drawString("be it an attack, a spell (WIP), reasoning, or to use an item (WIP).", (int) (WIDTH*0.01), (int) (HEIGHT*0.32));
                g.drawString("For sound, the player must type the correct notes to play the major chord (caps for sharps)", (int) (WIDTH*0.01), (int) (HEIGHT*0.37));
                g.drawString("For spacial, the player must type the number associated with the stage direction on screen", (int) (WIDTH*0.01), (int) (HEIGHT*0.42));
                g.drawString("For visual, the player must click the color combination of colors to form the color on screen", (int) (WIDTH*0.01), (int) (HEIGHT*0.47));
                g.drawString("and last and definitely least, the basic attack deals damage equal to the player's power.", (int) (WIDTH*0.01), (int) (HEIGHT*0.52));
                g.drawString("The enemy fights back with different attacks. In the first world, the most common is 'wave'", (int) (WIDTH*0.01), (int) (HEIGHT*0.57));
                g.drawString("The player needs to dodge the falling waves or be damaged severely.", (int) (WIDTH*0.01), (int) (HEIGHT*0.62));
                g.drawString("For text boxes, click on the yellow box to see the next page or to end the conversation.", (int) (WIDTH*0.01), (int) (HEIGHT*0.67));
                g.drawString("For doors and for roads, go to the end of the road/next to the door to go to the room/area.", (int) (WIDTH*0.01), (int) (HEIGHT*0.72));

                g.setFont(fnt2);
                g.drawString("Have fun!", (int) (WIDTH*0.4), (int) (HEIGHT*0.8));
                g.drawRect((int) (Game.WIDTH * 0.2) - 100, (int) (Game.HEIGHT * 0.8) - 32, 200, 64);
                g.drawString("BACK", (int) (Game.WIDTH * 0.14), (int) (Game.HEIGHT * 0.82));

                /*g.drawRect((int) (Game.WIDTH * 0.8) - 100, (int) (Game.HEIGHT * 0.8) - 32, 200, 64);
                g.drawString("NEXT", (int) (Game.WIDTH * 0.74), (int) (Game.HEIGHT * 0.82));*/
                break;
            default:
                break;
        }
    }

    public static boolean gg = false;
    public static void changeState(Backdrop.BACKDROP nextState) {
        if (!gg) {
            if (currentBackdrop != BACKDROP.Transition) {
                System.out.println("current: " + Backdrop.currentBackdrop);
                Backdrop.lastBackdrop = Backdrop.currentBackdrop;
            }
            Backdrop.nextBackdrop = nextState;
            Backdrop.currentBackdrop = Backdrop.BACKDROP.Transition;
            gg = true;
        }
    }
}
