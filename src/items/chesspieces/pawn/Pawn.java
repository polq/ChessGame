package items.chesspieces.pawn;

import items.chesspieces.ChessFigure;
import player.ChessPlayer;

public abstract class Pawn extends ChessFigure {

    ChessPlayer player;

    @Override
    public boolean move() {
        return false;
    }

    @Override
    public boolean beat() {
        return false;
    }
}
