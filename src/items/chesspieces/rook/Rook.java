package items.chesspieces.rook;

import items.chesspieces.ChessFigure;

public abstract class Rook extends ChessFigure {
    @Override
    public boolean move() {
        return false;
    }

    @Override
    public boolean beat() {
        return false;
    }
}
