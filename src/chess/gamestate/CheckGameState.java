package chess.gamestate;

import chess.items.board.Board;

public class CheckGameState extends GameState {

    public CheckGameState(Board board) {
        super(board);
    }

    @Override
    public GameState switchGameState() {
        return new ActiveGameState(getGameBoard());
    }

    @Override
    public void executeCommand(String fromCoordinate, String toCoordinate) {

    }

    @Override
    public String toString() {
        return  super.toString() +"\nIt's " + getCurrentTurnPlayer()  +" turn. Player is under check" ;
    }
}
