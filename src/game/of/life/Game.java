/*
 *  Developed by András Ács (acsandras@gmail.com)
 *  Erhvervsakademi Sjælland / www.easj.dk
 *  Licensed under the WFTPL License - http://www.wtfpl.net/txt/copying/
 *  2017
 *
 */
package game.of.life;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.util.Duration;

/**
 *
 * @author andrasacs
 */
public class Game {

    // Settings (Constants)
    private static int WORLD_SIZE_X = 10, WORLD_SIZE_Y = 10;

    private Cell[][] arrayH = new Cell[WORLD_SIZE_X][WORLD_SIZE_Y];
    private int lifeCycles;
    private Game world;
    private int nIteration;

    // Populating the world with either living or dead cells
    private Game() {
        nIteration = 0;
        for (int y = 0; y < WORLD_SIZE_Y; y++) {
            for (int x = 0; x < WORLD_SIZE_X; x++) {
                Random randomGenerator = new Random();
                int r = randomGenerator.nextInt(2);
                Cell cell = new Cell();
                cell.setName("X" + x + "Y" + y); // The cells have unique names
                if (r == 0) {
                    cell.setAlive(false);
                } else {
                    cell.setAlive(true);
                }
                arrayH[x][y] = cell;

                //DEBUG
                // System.out.print(r);
            }
           // System.out.println("");
        }
        // cellDebug();

    }

    public static Game intialize() {
        Game world = new Game();
        return world;
    }

    // Lav en update()-metode, der først løber hele arrayet igennem og sætter hver celles livingNeighbours,
    // dernæst løber arrayet igennem og kalder update() på hver celle.
    public Game update() {
        nIteration++;
        int yMax = this.arrayH.length;
        int xMax = this.arrayH[0].length;
        for (int y = 0; y < yMax; y++) {
            for (int x = 0; x < xMax; x++) {

                // Evaluerer alle nabopositioner som følgende:
                // NW | N | NE
                // S  | . | E
                // SW | S | SE
                List neighborList = new ArrayList();

                // DEBUG System.out.println("Evaluating " +  x + "#" + y + arrayH[x][y].getName());
                //System.out.println(arrayH[x][y].toString());
                if (y < WORLD_SIZE_Y - 1 && x > 0) {
                    if (arrayH[x - 1][y + 1].isAlive()) {
                        neighborList.add("SW");
//                        System.out.println("Has neighbor to SW.");
                    }
                }
                if (y < WORLD_SIZE_Y - 1) {
                    if (arrayH[x][y + 1].isAlive()) {
                        neighborList.add("S");
//                        System.out.println("Has neighbor to S.");
                    }
                }
                if (x < WORLD_SIZE_X - 1 && y < WORLD_SIZE_Y - 1) {
                    if (arrayH[x + 1][y + 1].isAlive()) {
                        neighborList.add("SE");
//                        System.out.println("Has neighbor to SE.");
                    }
                }

                if (x > 0) {
                    if (arrayH[x - 1][y].isAlive()) {
                        neighborList.add("W");
//                        System.out.println("Has neighbor to W.");
                        //System.out.println(arrayH[x - 1][y].toString());

                    }
                }

                if (x < WORLD_SIZE_X - 1) {
                    if (arrayH[x + 1][y].isAlive()) {
                        neighborList.add("E");
//                        System.out.println("Has neighbor to E.");

                    }
                }

                if (x > 0 && y > 0) {
                    if (arrayH[x - 1][y - 1].isAlive()) {
                        neighborList.add("NW");
//                        System.out.println("Has neighbor to NW.");
                    }
                }
                if (y > 0) {
                    if (arrayH[x][y - 1].isAlive()) {
                        neighborList.add("N");
//                        System.out.println("Has neighbor to N.");
                    }
                }
                if (x < WORLD_SIZE_X - 1 && y > 0) {
                    if (arrayH[x + 1][y - 1].isAlive()) {
                        neighborList.add("NE");
//                        System.out.println("Has neighbor to NE.");
                    }
                }

                arrayH[x][y].setLivingNeighbours(neighborList.size());
                arrayH[x][y].setNeighborList(neighborList);

                // DEBUG
                // System.out.println("Round" + nIteration + " - " + arrayH[x][y].getName() + neighborList);

            }
        }
        
        // Updating all the cells in the 2D array (they will live or DIE!)
        for (int y = 0; y < yMax; y++) {
            for (int x = 0; x < xMax; x++) {
                String result = arrayH[x][y].update();
            }
        }

        //cellDebug();
        return world;
    }
    
    // Used for debugging
    public void cellDebug() {
        System.out.println("**********");
        int yMax = this.arrayH.length;
        int xMax = this.arrayH[0].length;
        int cell = 1;
        for (int y = 0; y < yMax; y++) {
            for (int x = 0; x < xMax; x++) {
                if (arrayH[x][y].isAlive()) {
                    System.out.print(String.valueOf(cell++));
                } else {
                    System.out.print(".");
                }
                if (cell == 10) {
                    cell = 1;
                }
            }
            System.out.println("");
        }
        System.out.println("**********");
    }

    public static int getWORLD_SIZE_X() {
        return WORLD_SIZE_X;
    }

    public static void setWORLD_SIZE_X(int WORLD_SIZE_X) {
        Game.WORLD_SIZE_X = WORLD_SIZE_X;
    }

    public int getLifeCycles() {
        return lifeCycles;
    }

    public void setLifeCycles(int lifeCycles) {
        this.lifeCycles = lifeCycles;
    }

    public static int getWORLD_SIZE_Y() {
        return WORLD_SIZE_Y;
    }

    public static void setWORLD_SIZE_Y(int WORLD_SIZE_Y) {
        Game.WORLD_SIZE_Y = WORLD_SIZE_Y;
    }

    public Cell[][] getArrayH() {
        return arrayH;
    }

    public void setArrayH(Cell[][] arrayH) {
        this.arrayH = arrayH;
    }

    public int getnIteration() {
        return nIteration;
    }

    public void setnIteration(int nIteration) {
        this.nIteration = nIteration;
    }

}
