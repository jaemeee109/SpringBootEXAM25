package org.exam.board.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.exam.board.dto.BoardDTO;
import org.exam.board.dto.BoardListReplyCountDTO;
import org.exam.board.dto.PageResponseDTO;
import org.exam.board.service.BoardService;
import org.exam.board.dto.PageRequestDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.nio.file.Files;
import java.util.List;

@Controller
@RequestMapping("/board")
@Log4j2
@RequiredArgsConstructor
public class BoardController {

    @Value("${org.exam.upload.path}")
    private String uploadPath;

    private final BoardService boardService;

    @GetMapping("/list")
    public void list(PageRequestDTO pageRequestDTO, Model model){

        PageResponseDTO<BoardListReplyCountDTO> responseDTO=boardService.listWithReplyCount(pageRequestDTO);
        log.info(responseDTO);
        model.addAttribute("responseDTO", responseDTO);
    } // list종료

    @GetMapping("/register")
    public String registerForm(Model model) {

        return "board/register";
    }

    @PostMapping("/register")
    public String registerPost(@Valid BoardDTO boardDTO, BindingResult bindingResult, RedirectAttributes redirectAttributes){

        log.info("===Board Post Register===");
        if(bindingResult.hasErrors()){
            log.info("===has error===");
            redirectAttributes.addAttribute("errors",bindingResult.getAllErrors());
            return "board/register";
        } // if 종료

        log.info(boardDTO);
        Long bno = boardService.register(boardDTO);
        redirectAttributes.addFlashAttribute("result",bno);
        return "redirect:/board/list";

    } // register 종료

    @GetMapping({"/read","modify"})
    public void read(Long bno, PageRequestDTO pageRequestDTO, Model model){
        BoardDTO boardDTO = boardService.readOne(bno);
        log.info(boardDTO);
        model.addAttribute("dto",boardDTO);
        model.addAttribute("pageRequestDTO", pageRequestDTO);
        return;

    } // read 종료

    @PostMapping("/modify")
    public String modify(PageRequestDTO pageRequestDTO,@Valid BoardDTO boardDTO,BindingResult bindingResult,RedirectAttributes redirectAttributes){

        log.info("===Board Modify Post===" + boardDTO);
        if(bindingResult.hasErrors()){
            log.info("===has error===");

            String link = pageRequestDTO.getLink();
            redirectAttributes.addFlashAttribute("errors",bindingResult.getAllErrors());
            redirectAttributes.addFlashAttribute("bno",boardDTO.getBno());
            return "redirect:/board/modify?"+link;

        } // if 종료

        boardService.modify(boardDTO);
        redirectAttributes.addFlashAttribute("result","modified");
        redirectAttributes.addAttribute("bno",boardDTO.getBno());
        return "redirect:/board/read";


    } //modify  종료

    @PostMapping("/remove")
    public String remove(BoardDTO boardDTO, RedirectAttributes redirectAttributes){

        Long bno = boardDTO.getBno();

        boardService.remove(bno);


        List<String> fileNames = boardDTO.getFileNames();
        if(fileNames != null && fileNames.size() > 0) {
            removeFiles(fileNames);
        }
        redirectAttributes.addFlashAttribute("result", "removed");

        return "redirect:/board/list";
    } // remove 종료

    public void removeFiles(List<String> files){

        for (String fileName:files){
            Resource resource = new FileSystemResource(uploadPath+ File.separator+fileName);
            String resourceName = resource.getFilename();

            try{
                String contentType = Files.probeContentType(resource.getFile().toPath());
                resource.getFile().delete();

                if(contentType.startsWith("image")){
                    File thumbnailFile = new File(uploadPath+ File.separator+"s_"+resourceName);
                    thumbnailFile.delete();
                } // if종료
            }catch(Exception e){
                log.error(e);
            } // try-catch 종료
        } // for 종료
    }// removeFiles 종료


} // class종료





