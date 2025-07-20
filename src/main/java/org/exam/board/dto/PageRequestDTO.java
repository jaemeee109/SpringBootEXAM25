package org.exam.board.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Log4j2
public class PageRequestDTO {
    
    @Builder.Default
    private int page =1; // 첫페이지
    
    @Builder.Default
    private int size =10; // 게시물 수
    
    private String type ; // 다중검색용
    
    private String keyword; 
    
    private String link;
    
    public String getLink() {

        if (link == null) {
            StringBuilder builder = new StringBuilder();

            builder.append("page=" + this.page);
            builder.append("&size=" + this.size);

            if (type != null && type.length() > 0) {
                builder.append("&type=" + type);
            }
            if(keyword != null){
                try{
                    builder.append("&keyword="+ URLEncoder.encode(keyword,"UTF-8"));
               }catch (UnsupportedEncodingException e){
                    log.info(e.getStackTrace());
                    log.info("UTF-8 처리 중 요류발생");
                } //try 종료
            } //if 종료
            link = builder.toString();
        } // if 종료
        return  link;
    } // getLink 종료

    public String[] getTypes(){
        if(type==null || type.isEmpty()){
            return null;
        }
        return type.split("");
    } // getTypes종료

    //테스트용 코드
    public Pageable getPageable(String...props){ // String...props 배열이 몇개가 들어올지 모를때
        return PageRequest.of(this.page-1, this.size, Sort.by(props).descending());
    }//getPageable 종료
    
    
    
    
} //class 종료
