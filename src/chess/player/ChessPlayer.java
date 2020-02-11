package chess.player;

abstract public class ChessPlayer {

    int defaultStep;

    public ChessPlayer(){

    }

    public ChessPlayer(int defaultStep){
        this.defaultStep = defaultStep;
    }
}
