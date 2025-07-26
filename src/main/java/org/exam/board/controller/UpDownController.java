package org.exam.board.controller;

import lombok.extern.log4j.Log4j2;
import net.coobird.thumbnailator.Thumbnailator;
import org.exam.board.dto.upload.UploadFileDTO;
import org.exam.board.dto.upload.UploadResultDTO;
import org.hibernate.query.results.ResultSetMapping;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@RestController
@Log4j2
public class UpDownController {

    @Value("${org.exam.upload.path}")
    private String uploadPath;

    @PostMapping(value="/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public List<UploadResultDTO> upload(UploadFileDTO uploadFileDTO){
        log.info(uploadFileDTO);

        if (uploadFileDTO.getFiles() != null) {

            final List<UploadResultDTO> list = new ArrayList<>();

            uploadFileDTO.getFiles().forEach(
                    multipartFile -> {
                        String originalName = multipartFile.getOriginalFilename();
                        log.info(originalName);

                        String uuid = UUID.randomUUID().toString();
                        Path savePath = Paths.get(uploadPath, uuid+"_"+originalName);

                        boolean image = false;

                        try{
                            multipartFile.transferTo(savePath);
                            if(Files.probeContentType(savePath).startsWith("image")){
                                image = true;
                                File thumbFile = new File(uploadPath,"s_"+uuid+"_"+originalName);
                                Thumbnailator.createThumbnail(savePath.toFile(), thumbFile, 200, 200);
                            } // if종료
                        }catch(IOException e){
                            e.printStackTrace();
                        } // try-catch 종료
                        list.add(UploadResultDTO.builder()
                                .uuid(uuid)
                                .fileName(originalName)
                                .img(image)
                                .build());

                    } // 람다식 종료
            ); // forEach, 종료
            return list;

        }// if종료
        return null;


    }//upload 메서드 종료

@GetMapping("/view/{fileName}")
    public ResponseEntity<Resource> viewFileGET(@PathVariable String fileName){

        Resource resource = new FileSystemResource(uploadPath+File.separator+fileName);
        String resourceName = resource.getFilename();
        HttpHeaders headers = new HttpHeaders();

        try{
            headers.add("Content-Type",Files.probeContentType(resource.getFile().toPath()));
        }catch(IOException e){
            return ResponseEntity.internalServerError().build();

        } // try-catch 종료

    return ResponseEntity.ok().headers(headers).body(resource);

}//viewFileGet 종료

    @DeleteMapping("/remove/{fileName}")
    public Map<String, Boolean> remove(@PathVariable String fileName){

        Resource resource = new FileSystemResource(uploadPath+File.separator+fileName);
        String resourceName = resource.getFilename();
        Map<String, Boolean> resultMap = new HashMap<>();

        boolean removed = false;

        try{
            String contentType = Files.probeContentType(resource.getFile().toPath());
            removed = resource.getFile().delete();

            if(contentType.startsWith("image")){
                File thumbnailFile = new File(uploadPath+File.separator+fileName);
                thumbnailFile.delete();
            }//if종료
        }catch (Exception e){
            log.error(e.getMessage());
        }// try-catch 종료

        resultMap.put("result",removed);

        return resultMap;

    }// remove 종료


} // class 종료
