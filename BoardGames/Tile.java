import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

abstract public class Tile<B extends Board> extends JButton {

  public Tile(B b, int r, int c) {
      board = b;
      row = r; col = c;

      state = initState();
      state.start();
  }

  abstract protected State initState();

  public final void setState(State new_state) {
      State old_state = state;

      old_state.terminate();

      if (!new_state.terminated) {
        state = new_state;
         new_state.start();
      }
  }

  //getters

  public <T extends Tile<B>> LinkedList<T> getNeighbors() {
    LinkedList<T> n = new LinkedList<T>();

    for (int i = -1; i <= 1; i++) {
      for (int j = -1; j <= 1; j++) {
          if (i != 0 || j != 0) {
            try { n.add((T)board.getTile(row + i, col + j)); }
            catch(RuntimeException e) { }
          }
      }
    }

    return n;
  }

  protected B board;

  protected State state;

  protected int row, col;

  public String toString() {
    return "Tile: [" + Integer.toString(row) + " , " + Integer.toString(col) + "]";
  }

  public class State<T extends Tile> implements ActionListener {
    public State(T t) { tile = t; terminated = false; }

    public final void actionPerformed(ActionEvent e) { update(e); }

    public void start() { tile.addActionListener(this); }
    public void update(ActionEvent e) {}
    public void terminate() { tile.removeActionListener(this); terminated = true; }

    protected T tile;

    boolean terminated;
  }
}
