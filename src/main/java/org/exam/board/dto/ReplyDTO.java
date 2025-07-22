package org.exam.board.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReplyDTO {

    private Long rno; // 댓글번호

    @NotNull
    private Long bno; // 게시글 번호 (fk)

    @NotEmpty
    private String replyText; // 댓글내용

    @NotEmpty
    private String replyer; // 댓글 작성자

    private LocalDateTime regDate, modDate; // 등록일, 수정일

} // class 종료
