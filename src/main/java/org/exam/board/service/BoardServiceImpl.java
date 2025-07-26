package org.exam.board.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.exam.board.domain.Board;
import org.exam.board.dto.*;
import org.exam.board.repository.BoardRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@Log4j2
@RequiredArgsConstructor
@Transactional
public class BoardServiceImpl implements BoardService{

    private final ModelMapper modelMapper;
    private final BoardRepository boardRepository;

    @Override
    public Long register (BoardDTO boardDTO){
        Board board = dtoTOEntity(boardDTO);
        Long bno = boardRepository.save(board).getBno();
        return bno;
    } // register 종료

    @Override
    public BoardDTO readOne(Long bno) {
        // 기존 findById → findByIdWithImage 로 변경해야 함
        Optional<Board> result = boardRepository.findByIdWithImage(bno);
        Board board = result.orElseThrow();

        BoardDTO boardDTO = modelMapper.map(board, BoardDTO.class);

        // imageSet을 fileNames로 변환해서 DTO에 넣기
        List<String> fileNames = board.getImageSet().stream()
                .sorted()
                .map(image -> image.getUuid() + "_" + image.getFileName())
                .collect(Collectors.toList());

        boardDTO.setFileNames(fileNames); // 이거 꼭 해줘야 수정화면에서 파일 보임

        return boardDTO;

    } // readOne 종료

    @Transactional
    @Override
    public void modify(BoardDTO boardDTO) {

        Optional<Board> result = boardRepository.findByIdWithImage(boardDTO.getBno());
        Board board = result.orElseThrow();
        board.change(boardDTO.getTitle(),boardDTO.getContent());

        board.clearImages();


        if(boardDTO.getFileNames() != null) {
            for(String fileName : boardDTO.getFileNames()) {
            String[] arr = fileName.split("_");
            board.addImage(arr[0], arr[1]);
            } // for종료
        } // if종료

        boardRepository.save(board);

    } //modify 종료

    @Override
    public void remove(Long bno) {
        
        boardRepository.deleteById(bno);
    } // remove 종료

    @Override
    public PageResponseDTO<BoardDTO> list(PageRequestDTO pageRequestDTO) {

        String[] types = pageRequestDTO.getTypes();
        String keyword = pageRequestDTO.getKeyword();
        Pageable pageable = pageRequestDTO.getPageable("bno");

        Page<Board> result = boardRepository.searchAll(types, keyword, pageable);

        List<BoardDTO> dtoList = result.getContent().stream()
                .map(board -> modelMapper.map(board,BoardDTO.class))
                .collect(Collectors.toList());

        return PageResponseDTO.<BoardDTO>withAll()
                .pageRequestDTO(pageRequestDTO)
                .dtoList(dtoList)
                .total((int)result.getTotalElements())
                .build();
    } //PageResponseDTO<BoardDTO> list 종료

    @Override
    public PageResponseDTO<BoardListReplyCountDTO> listWithReplyCount(PageRequestDTO pageRequestDTO) {


        String[] types = pageRequestDTO.getTypes();
        String keyword = pageRequestDTO.getKeyword();
        Pageable pageable = pageRequestDTO.getPageable("bno");

        Page<BoardListReplyCountDTO> result = boardRepository.searchWithReplyCount(types, keyword, pageable);

        return PageResponseDTO.<BoardListReplyCountDTO>withAll()
                .pageRequestDTO(pageRequestDTO)
                .dtoList(result.getContent())
                .total((int)result.getTotalElements())
                .build();
    } // listWithReplyCount 종료

    @Override
    public PageResponseDTO<BoardListAllDTO> listWithAll(PageRequestDTO pageRequestDTO) {
        
        String[] types = pageRequestDTO.getTypes();
        String keyword = pageRequestDTO.getKeyword();
        Pageable pageable = pageRequestDTO.getPageable("bno");
        Page<BoardListAllDTO> result = boardRepository.searchWithAll(types, keyword, pageable);
        
        return  PageResponseDTO.<BoardListAllDTO>withAll().pageRequestDTO(pageRequestDTO)
                .dtoList(result.getContent())
                .total((int)result.getTotalElements())
                .build();
    } // listWithAll 종료


} //  class 종료
