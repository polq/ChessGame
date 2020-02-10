package items.chesspieces.knight;

import items.chesspieces.ChessFigure;

public abstract class Knight extends ChessFigure {
    @Override
    public boolean move() {
        return false;
    }

    @Override
    public boolean beat() {
        return false;
    }
}
