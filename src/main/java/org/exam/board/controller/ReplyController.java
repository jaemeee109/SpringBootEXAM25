package org.exam.board.controller;


import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;
import org.exam.board.dto.ReplyDTO;
import org.springframework.http.MediaType;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("replies")
@Log4j2
public class ReplyController {

    // C
    @PostMapping(value = "/", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Long> register(@Valid @RequestBody ReplyDTO replyDTO, BindingResult bindingResult) throws BindException {

        log.info(replyDTO);

        if(bindingResult.hasErrors()) {
            throw new BindException(bindingResult);
        }//if종료

        Map<String, Long> resultMap = new HashMap<>();
        resultMap.put("rno", 111L); // 테스트용 코드

        return resultMap;
    } //Map<String, Long> register 종료


} // class종료
