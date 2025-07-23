package org.exam.board.service;

import lombok.extern.log4j.Log4j2;
import org.exam.board.dto.ReplyDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Log4j2
public class ReplyServiceTests {
    
    @Autowired
    private ReplyService replyService;
    
    @Test
    public void testRegister(){

        ReplyDTO replyDTO = ReplyDTO.builder()
                .replyText("서비스에서 댓글등록")
                .replyer("서비스테스트")
                .bno(98L)
                .build();
        log.info("=====testRegister()메서드 실행=====");
        log.info(replyService.register(replyDTO));
        
    } // testRegister 종료
    
} // class 종료
