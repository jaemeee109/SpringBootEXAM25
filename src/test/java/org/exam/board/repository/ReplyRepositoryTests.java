package org.exam.board.repository;

import lombok.extern.log4j.Log4j2;
import org.exam.board.domain.Board;
import org.exam.board.domain.Reply;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Log4j2
public class ReplyRepositoryTests {
    
    @Autowired
    private ReplyRepository replyRepository;
    
    @Test
    public void testInsert() {
        // 댓글 넣기

        Long bno = 100L;

        Board board = Board.builder().bno(bno).build();

        Reply reply = Reply.builder()
                .board(board)
                .replyText("리포지토리에서 테스트")
                .replyer("리포지토리")
                .build();

        replyRepository.save(reply);
        
    }// testInsert종료
    
    @Test
    @Transactional
    public void testBoardReplies(){


        Long bno = 100L;

        Pageable pageable = PageRequest.of(0, 10, Sort.by("rno").descending());

        Page<Reply> result = replyRepository.listOfBoard(bno, pageable);

        result.getContent().forEach(reply -> {
            log.info(reply);
        });

    } // testBoardReplies종료


    
} // class 종료