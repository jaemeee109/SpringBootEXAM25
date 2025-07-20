package org.exam.board.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity // DB테이블 관련 객체
@Getter
@Builder // 빌더패턴 (세터 대신 활용)
@AllArgsConstructor // 모든 필드값으로 생성자 만듦
@NoArgsConstructor // 기본생성자
@ToString // 테스트용 : 객체 주소가 아닌 값을 출력
public class Board extends BaseEntity { // BaseEntity -> 날짜관련된 jpa 를 연결
    
    /* 필드 */
    @Id // pk선언 (not Null, unique, indexing)
    @GeneratedValue (strategy = GenerationType.IDENTITY) /// 자동번호 생성
    private Long bno; // 게시물 번호
    
    @Column(length = 500, nullable = false) // nullable = false : not Null
    private String title; // 제목
    
    @Column(length = 2000, nullable = false)
    private String content; // 내용
    
    @Column(length = 50, nullable = false)
    private String writer; // 작성자

    public void change (String title, String content){
        // 제목과 내용만 수정하는 메서드 (세터 대체)
        this.title = title;
        this.content = content;
    } // change 종료
    
} // class 종료

