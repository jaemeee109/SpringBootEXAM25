package org.exam.board.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@ToString
public class PageResponseDTO<E> {
    
    private int page, size, total; 
    private int start; // 페이지 시작 번호
    private int end; // 페이지 끝 번호
    
    private boolean prev;
    private boolean next;
    
    private List<E> dtoList; // 게시물 목록

    @Builder(builderMethodName = "withAll")
    public PageResponseDTO(PageRequestDTO pageRequestDTO, List<E> dtoList, int total){

        if(total <= 0){
            return;
        }
        this.page=pageRequestDTO.getPage();
        this.size=pageRequestDTO.getSize();
        this.total=total;
        this.dtoList=dtoList;

        this.end = (int)(Math.ceil(this.page/10.0))*10;
        this.start=this.end-9;

        int last=(int)((Math.ceil(total/(double)size)));

        this.end=end>last?last:end;

        this.prev=this.start>1;
        this.next=total>this.end*this.size;
;
    }//PageResponseDTO 종료



} // class 종료
