package org.exam.board.service;


import org.exam.board.domain.Board;
import org.exam.board.dto.BoardDTO;
import org.exam.board.dto.PageRequestDTO;
import org.exam.board.dto.PageResponseDTO;

public interface BoardService {

    Long register (BoardDTO boardDTO);
    BoardDTO readOne (Long bno);
    void modify (BoardDTO boardDTO);
    void remove (Long bno);

    PageResponseDTO<BoardDTO> list(PageRequestDTO pageRequestDTO);

}
