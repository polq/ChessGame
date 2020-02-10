package items.chesspieces.bishop;

import items.chesspieces.ChessFigure;

public abstract class Bishop extends ChessFigure {
    @Override
    public boolean move() {
        return false;
    }

    @Override
    public boolean beat() {
        return false;
    }
}
