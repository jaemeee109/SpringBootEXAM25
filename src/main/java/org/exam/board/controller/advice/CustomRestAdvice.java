package org.exam.board.controller.advice;


import lombok.extern.log4j.Log4j2;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;


import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

@RestControllerAdvice
@Log4j2
public class CustomRestAdvice {

    @ExceptionHandler({BindException.class})
    @ResponseStatus(HttpStatus.EXPECTATION_FAILED)
    public ResponseEntity<Map<String, String>> handleBindException(BindException e) {

        log.error(e);
        Map<String,String> errorMap = new HashMap<>();

        if(e.hasErrors()){
            BindingResult bindingResult = e.getBindingResult();

            bindingResult.getFieldErrors().forEach(fieldError -> {
                errorMap.put(fieldError.getField(), fieldError.getCode());
            });
        }//if 종료
        return ResponseEntity.badRequest().body(errorMap);


    } // ResponseEntity 종료
    
    // 500 에러
    
    @ExceptionHandler({DataIntegrityViolationException.class})
    @ResponseStatus(HttpStatus.EXPECTATION_FAILED)
    public ResponseEntity<Map<String, String>> handelFKException(Exception e) {
        log.error(e);
        Map<String,String> errorMap = new HashMap<>();
        
        errorMap.put("에러발생시간 : ",""+System.currentTimeMillis());
        errorMap.put("에러메세지 : ","sql or data ERROR");
        return ResponseEntity.badRequest().body(errorMap);
    } // 500에러 종료

    // 댓글에 번호 없을때
    @ExceptionHandler({NoSuchElementException.class, 
            EmptyResultDataAccessException.class})  
    
    @ResponseStatus(HttpStatus.EXPECTATION_FAILED)
    public ResponseEntity<Map<String,String>> handelNoSuchElementException(Exception e){ 

        log.error(e);
        Map<String,String > errorMap = new HashMap<>();
        

       
        errorMap.put("에러발생시간 : ",""+ System.currentTimeMillis());
        errorMap.put("에러메세지1 : ","찾는 댓글 번호가 없습니다");
        errorMap.put("에러메세지2 : ","찾는 객체가 없습니다");
        return ResponseEntity.badRequest().body(errorMap);

    } // 댓글 번호 없을때 종료


}// class 종료
