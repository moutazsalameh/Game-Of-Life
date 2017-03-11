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

/**
 *
 * @author andrasacs
 */
public class Cell {

    private String name;
    private int livingNeighbours;
    private List neighborList;
    private int nIteration;

    private boolean alive;

    public Cell() {

        livingNeighbours = -1;
        nIteration = 0;
        List neighborList = new ArrayList();

    }

    // returns true if alive, false if dead
    public String update() {
        String strAlive = "dead";
        if (alive) {
            strAlive = "alive";
        }

        String status = "No changes.";
        // Note: The number of live neighbours is always based on the cells before the rule was applied. In other words, we must first find all of the cells that change before changing any of them.
        // A dead cell with exactly three live neighbours becomes a live cell (birth). 
        if (!alive && (livingNeighbours == 3)) {
            alive = true;
            status = "Cell born!";
        }
        // A live cell with two or three live neighbours stays alive (survival).
        if ((alive && (livingNeighbours == 2))
                || (alive && (livingNeighbours == 3))) {
            alive = true;
            status = "Cell stays alive.";
        }
        // In all other cases, a cell dies or remains dead (overcrowding or loneliness).
        if ((livingNeighbours < 2) || (livingNeighbours > 3)) {
            if (!alive) {
                status = "Remains dead.";
            }
            alive = false;
            if (livingNeighbours < 2) {
                status = "Dieded of loneliness.";
            }
            if (livingNeighbours > 3) {
                status = "Dieded of overcrowding!";
            }
        }
        nIteration++;
        return "Round " + nIteration + ": " + name + " is " + strAlive + ", with " + livingNeighbours + " neighbors. " + status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLivingNeighbours() {
        return livingNeighbours;
    }

    public void setLivingNeighbours(int livingNeighbours) {
        this.livingNeighbours = livingNeighbours;
    }

    public boolean isAlive() {
        return alive;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    public int getnIteration() {
        return nIteration;
    }

    public void setnIteration(int nIteration) {
        this.nIteration = nIteration;
    }

    public List getNeighborList() {
        return neighborList;
    }

    public void setNeighborList(List neighborList) {
        this.neighborList = neighborList;
    }

    @Override
    public String toString() {
        return "Round: " + nIteration + ", Cell{" + "name=" + name + ", livingNeighbours=" + livingNeighbours + ", neighborList=" + neighborList + ", alive=" + alive + '}';
    }

}
