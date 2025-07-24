package org.exam.board.repository.search;

import org.exam.board.domain.Board;
import org.exam.board.dto.BoardListAllDTO;
import org.exam.board.dto.BoardListReplyCountDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BoardSearch {

    Page<Board> search1(Pageable pageable);

    Page<Board> searchAll(String[] types, String keyword, Pageable pageable);

    Page<BoardListReplyCountDTO> searchWithReplyCount (String[] types, String keyword, Pageable pageable);

    Page<BoardListAllDTO> searchWithAll (String[] types, String keyword, Pageable pageable);
} //interface 종료
