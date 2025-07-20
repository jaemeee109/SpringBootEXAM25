package org.exam.board.domain;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter // 날짜 처리만 (sysdate)-> DB에서 날짜만 가져오겠다는 뜻 (보안 이슈)
@MappedSuperclass // 공통적인 최상위 클래스
@EntityListeners(value= AuditingEntityListener.class) // 감시용 클래스 명시
abstract class BaseEntity { // 추상적인 abstract -> 자체적인 실행은 안됨
    // 모든 테이블에 공통적으로 사용되는 필드를 만든다

    @CreatedDate // 생성일
    @Column(name="regdate",updatable = false) // updatable=false : 수정금지
    private LocalDateTime regDate; //등록일

    @LastModifiedDate // 수정일
    @Column(name="moddate") //DB필드명 지정
    private LocalDateTime modDate; //수정일
}
