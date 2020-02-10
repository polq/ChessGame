package items.chesspieces.king;

import items.chesspieces.ChessFigure;

public abstract class King extends ChessFigure {
    @Override
    public boolean move() {
        return false;
    }

    @Override
    public boolean beat() {
        return false;
    }
}
