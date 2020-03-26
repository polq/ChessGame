package org.buzevych.web.rest.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.buzevych.core.boardgame.game.GameSnapshot;
import org.buzevych.core.boardgame.items.board.Cell;

import java.util.Map;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
public class BoardResponseDTO {
  private boolean active;
  private String status;
  private Map<String, String> board;

  public static BoardResponseDTO getInstance(GameSnapshot gameSnapshot) {
    BoardResponseDTO boardResponseDTO = new BoardResponseDTO();
    boardResponseDTO.setActive(gameSnapshot.isActive());
    boardResponseDTO.setStatus(gameSnapshot.getGameStatusMessage());
    Map<String, String> resultBoardMap =
        gameSnapshot.getBoard().getBoardCells().values().stream()
            .collect(
                Collectors.toMap(
                    Cell::getStringKey,
                    cell -> cell.isEmpty() ? "empty" : cell.getFigure().getIconStringName()));
    boardResponseDTO.setBoard(resultBoardMap);
    return boardResponseDTO;
  }
}
