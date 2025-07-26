package org.exam.board.service;


import org.exam.board.domain.Board;
import org.exam.board.dto.*;

import java.util.List;
import java.util.stream.Collectors;

public interface BoardService {

    Long register (BoardDTO boardDTO);
    BoardDTO readOne (Long bno);
    void modify (BoardDTO boardDTO);
    void remove (Long bno);

    PageResponseDTO<BoardDTO> list(PageRequestDTO pageRequestDTO);

    PageResponseDTO<BoardListReplyCountDTO> listWithReplyCount(PageRequestDTO pageRequestDTO);

    default Board dtoTOEntity(BoardDTO boardDTO) {

        Board board = Board.builder()
                .bno(boardDTO.getBno())
                .title(boardDTO.getTitle())
                .content(boardDTO.getContent())
                .writer(boardDTO.getWriter())
                .build();
        if(boardDTO.getFileNames() != null) {
            boardDTO.getFileNames().forEach(fileName -> {
                String[] arr = fileName.split("_");
                board.addImage(arr[0], arr[1]);
                    });
        }//if종료
        return board;
    }// dtoTOEntity 종료

    default BoardDTO entityTODTO(Board board) {

        BoardDTO boardDTO = BoardDTO.builder()
                .bno(board.getBno())
                .title(board.getTitle())
                .content(board.getContent())
                .writer(board.getWriter())
                .regDate(board.getRegDate())
                .modDate(board.getModDate())
                .build();

        List<String> fileNames = board.getImageSet()
                .stream()
                .sorted()
                .map(boardImage ->
                        boardImage.getUuid()+"_"
                        +boardImage.getFileName())
                .collect(Collectors.toList());
        boardDTO.setFileNames(fileNames);
        return boardDTO;
    } // entityTODTO 종료

    PageResponseDTO<BoardListAllDTO> listWithAll(PageRequestDTO pageRequestDTO);

} // interface 종료
