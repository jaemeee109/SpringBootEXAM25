package org.exam.board.repository;

import lombok.extern.log4j.Log4j2;
import org.exam.board.domain.Board;
import org.exam.board.dto.BoardListReplyCountDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

@SpringBootTest // 메서드용 테스트 동작
@Log4j2 // 로그용

public class BoardRepositoryTests {
    // 영속성 계층에 테스트용
    
    @Autowired // 생성자 자동주입
    private BoardRepository boardRepository;
    
    @Test
    public void testInsert(){
        // DB 데이터 주입(c) 테스트 코드
        IntStream.rangeClosed(1,100).forEach(i->{
            // i변수에 1~99 까지 100개의 정수를 반보해서 생성
            Board board = Board.builder()
                    .title("제목" +i) // board.setTite()
                    .content("내용" + i) //board.setSetContent()
                    .writer("작성자"+ (i%10)) //board.setWriter()
                    .build(); // @Builder용 (Setter 대신 간단하고 가독성 좋음)
            
            //log.info(board);
            Board result = boardRepository.save(board); // DB에 기록하는 코드
            //                              .save 메서드는 jpa에서 상속한 메서드로 값을 저장하는 용도
            //                              이미 값이 있으면 update 진행
            log.info("게시물 번호 출력 : " + result.getBno()+ "/ 게시물 제목 : " + result.getTitle());
        } // forEach종료 
        ); // IntStream 종료
        
    } // testInsert 종료
    
    @Test
    public void testSelect(){
        Long bno = 100L; // 게시물 번호가 100인 개체 확인
        Optional<Board> result = boardRepository.findById(bno);
        //Optional null 값이 나올 경우를 대비한 객체
        //findById(bno)의 역할 : select * from board where bno = bno;
        
        Board board = result.orElseThrow(); // 값이 있으면 넣어라
        log.info("no."+bno+"는(은) DB에 존재합니다");
        log.info(board);
    } // testSelect 종료
    
    @Test
    public void testUpdate(){
        
        Long bno = 100L; // 100번 게시물을 가져와서 수정 후 테스트 종료
        Optional<Board> result = boardRepository.findById(bno); // bno를 찾아서 result에 넣는다
        Board board = result.orElseThrow(); // 가져온 값이 있으면 board 타입 객체에 넣는다
        board.change("수정테스트 제목","수정테스트 내용"); // 제목과 내용만 수정할 수 있는 메서드 
        boardRepository.save(board); // .save 메서드는 pk값이 없으면 insert, 있으면 update
    } //testUpdate 종료
    
    @Test
    public void testDelete(){
        
        Long bno = 200L;
        boardRepository.deleteById(bno);
        //              .deleteById(bno); -> delete from board where bno = bno
    } // testDelete 종료
    
    @Test
    public void testPaging(){
        // .findAll() : 모든 리스트를 출력하는 메서드 = select * from board;
        // 전체 리스트에 페이징과 정렬 기법도 추가 해보자

        Pageable pageable = PageRequest.of(0,20, Sort.by("bno").descending());
        //                                              시작번호, 20개 목록
        //                                                                  번호를 기준으로 내림차순 정렬

        Page<Board> result = boardRepository.findAll(pageable);
        // 1장에 Board객체를 가지고 있는 결과는 result에 담긴다
        // page 클래스는 다음 페이지 존재 여부, 이전 페이지 존재 여부, 전체 데이터 개수, 등을 계산

        log.info("전체 게시물 수 : " + result.getTotalElements());
        log.info("총 페이지 수 : " + result.getTotalPages());
        log.info("현재 페이지 번호 : " + result.getNumber());
        log.info("페이지당 게시물 수 : " + result.getSize());
        log.info("다음페이지가 있는지? : " + result.hasNext());
        log.info("현재 페이지가 시작 페이지인지? : " + result.isFirst());

        //콘솔에 결과 출력하기
       List<Board> boardList = result.getContent(); // 페이징 처리 된 내용을 호출
        boardList.forEach(board -> log.info(board));
        // forEach는 인덱스를 사용하지 않고 앞에서부터 객체 리턴
        // board -> log.info(board),
        // 람다식 (1개의 명령어가 있을 때 활용)


    } // testPaging종료

    @Test
    public void testSearch1(){

         Pageable pageable = PageRequest.of(1,10, Sort.by("bno").descending());
         Page<Board> result = boardRepository.search1(pageable);
         result.getContent().forEach(board->log.info(board));

    } //testSearch1 종료

    @Test
    public void testSearchAll(){
        String[] types = {"t","w"};
        String keyword = "10";
        Pageable pageable = PageRequest.of(0,10,Sort.by("bno").descending());
        Page<Board> result = boardRepository.searchAll(types,keyword,pageable);
        log.info("전체 게시물 수 : " + result.getTotalElements());
        log.info("총 페이지 수 : " + result.getTotalPages());
        log.info("현재 페이지 번호 : " + result.getNumber());
        log.info("페이지당 데이터 개수 : " + result.getSize() );
        log.info("다음페이지가 있는지? : " + result.hasNext());
        log.info("현재 위치가 시작페이지인지? : " + result.isFirst());

        result.getContent().forEach(board->log.info(board));
    } // testSearchAll 종료

    @Test
    public void testSearchReplyCount(){

        String[] types = {"t","c","w"};
        String keyword = "1";

        Pageable pageable = PageRequest.of(0,10,Sort.by("bno").descending());
        Page<BoardListReplyCountDTO> result = boardRepository.searchWithReplyCount(types,keyword,pageable);

        log.info("전체 게시물 수 : " + result.getTotalElements());
        log.info("총 페이지 수 : " + result.getTotalPages());
        log.info("현재 페이지 번호 : " + result.getNumber());
        log.info("페이지당 데이터 개수 : " + result.getSize() );
        log.info("다음페이지 여부 : " + result.hasNext());
        log.info("시작페이지 여부 : " + result.isFirst());

        result.getContent().forEach(board -> log.info(board));

    } //testSearchReplyCount 종료



} // class 종료
