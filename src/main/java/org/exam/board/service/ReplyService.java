package org.exam.board.service;

import org.exam.board.domain.Reply;
import org.exam.board.dto.PageRequestDTO;
import org.exam.board.dto.PageResponseDTO;
import org.exam.board.dto.ReplyDTO;

public interface ReplyService {

    Long register(ReplyDTO replyDTO);

    ReplyDTO read (Long rno);

    PageResponseDTO<ReplyDTO> getListOfBoard(Long bno, PageRequestDTO pageRequest);

    void modify(ReplyDTO replyDTO);

    void remove(Long rno);
} // interface종료
