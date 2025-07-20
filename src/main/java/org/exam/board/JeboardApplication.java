package org.exam.board;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing // 메인메서드 실행시 감시 작업 동작
//@EntityListeners(value= AuditingEntityListener.class) 와 세트로 동작함
public class JeboardApplication {

    public static void main(String[] args) {
        
        SpringApplication.run(JeboardApplication.class, args);
    }

} // class 종료
