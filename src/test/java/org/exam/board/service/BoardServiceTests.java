package org.exam.board.service;
import org.exam.board.dto.PageResponseDTO;
import org.exam.board.dto.PageRequestDTO;
import lombok.extern.log4j.Log4j2;
import org.exam.board.dto.BoardDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Log4j2
public class BoardServiceTests {
    
    @Autowired
    private BoardService boardService;
    
    @Test
    public void testRegister(){

         log.info("=====등록용 테스트 실행===");
         log.info(boardService.getClass().getName());

        BoardDTO boardDTO = BoardDTO.builder()
                .title("BoardServiceTests 제목")
                .content("BoardServiceTests 내용")
                .writer("BoardServiceTests님")
                .build();

        Long bno = boardService.register(boardDTO);

        log.info("=====> 테스트 결과 bno : " + bno);
    } // testRegister 종료

    @Test
    public void testModify(){

        BoardDTO boardDTO = BoardDTO.builder()
                .bno(401L)
                .title("BoardServiceTests수정제목")
                .content("BoardServiceTests수정내용")
                .build();
        boardService.modify(boardDTO); // 프론트에서 객체가 넘어가 수정이되었는 테스트

       
    } //testModify 종료

    @Test
    public void testList(){
        // 프론트에서 넘어오는 데이터를 이용해서 페이징과 검색과 정렬 처리 용
        PageRequestDTO pageRequestDTO = PageRequestDTO.builder()
                .type("tcw")  // 제목, 내용, 작성자
                .keyword("1") // 1을 찾는다.
                .page(1)      // 현재 페이지는 1
                .size(10)   // 10개씩 보여달라
                .build();

        PageResponseDTO<BoardDTO> responseDTO = boardService.list(pageRequestDTO);

        log.info(responseDTO);
       

    } //testList 종료

} //class 종료
