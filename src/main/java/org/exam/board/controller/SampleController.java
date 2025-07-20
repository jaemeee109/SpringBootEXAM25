package org.exam.board.controller;

import lombok.ToString;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@Log4j2
public class SampleController {
    // 컨트롤러는 URL 생성, 프론트와 연결하는 부분 (= servlet-context.xml)

    @GetMapping("/hello") // http://192.168.111.105:80/hello
    public void hello (Model model){

        log.info("===SampleController.hello 메서드 실행===");
        model.addAttribute("msg","안녕하세요!");
    }//hello 종료

    @GetMapping("/ex/ex1") // http://192.168.111.105:80/ex/ex1
    public void ex1(Model model){
        // 리스트 타입으로 데이터 보내기
        List<String> list = Arrays.asList("떡잎방범대","짱구","철수","유리","훈이","맹구");
        model.addAttribute("list",list); // ("변수", 값);


    } // ex1종료

    /* 클래스 안에 클래스 만들기 */
    @ToString
    class SampleDTO{
        // 이너 클래스
        //필드
        private String p1,p2,p3;

        //기본생성자

        //메서드
        // CODE -> GeNERATE에서 자동생성

        public String getP1() {
            return p1;
        }

        public String getP2() {
            return p2;
        }

        public String getP3() {
            return p3;

        }
    }// 이너클래스 SampleDTO 종료

    @GetMapping("/ex/ex2")
    public void ex2(Model model){
        log.info("===== SampleController.ex2 메서드 실행 ==== ");
        // 이너클래스로 객체 사용하기

        List<String> strList = IntStream.range(1,10) // 1부터 10까지 정수 생성
                .mapToObj(i->"데이터"+i)
                .collect(Collectors.toList()); // 리스트에 정수(숫자) 문자열이 생성됨
        // [데이터 1~데이터9]

        Map<String,String> map = new HashMap<>();
        map.put("id","scg");
        map.put("pw","1234");

        SampleDTO sampleDTO = new SampleDTO();
        sampleDTO.p1 = "값 p1";
        sampleDTO.p2 = "값 p2";
        sampleDTO.p3 = "값 p3";
        // 최종적으로 객체 3개 완성 됨

    model.addAttribute("list",strList);
    model.addAttribute("map",map);
    model.addAttribute("dto",sampleDTO);

    // 리턴타입이 void -> /ex/ex1.html을 찾는다
    } // ex2 종료

    @GetMapping("/ex/ex3") // http://192.168.111.105:80/ex/ex3
    public void ex3(Model model){
        log.info("=== SampleController.ex3 메서드실행 ===");
        model.addAttribute("arr",new String[]{"짱구,액션가면,부리부리맨"});
    }//ex3 종료


}//class종료
