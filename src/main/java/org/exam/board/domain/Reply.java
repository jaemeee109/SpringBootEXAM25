package org.exam.board.domain;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = "board")
@Table(name ="Reply", indexes = {@Index(name="idx_reply_board_bno", columnList = "board_bno")})
public class Reply extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long rno; // 게시물 번호

    @ManyToOne(fetch = FetchType.LAZY)
    private Board board;

    private String replyText;
    private String replyer;
    
    // 세터 대신 변경시 활용
    public void changeText(String text){
        this.replyText = text;
    } // changeText 종료
    
} // class 종료
