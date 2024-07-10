package com.scott.controllers;

import com.scott.message.ResponseFile;
import com.scott.message.ResponseMessage;
import com.scott.models.Filedb;
import com.scott.repositories.FiledbRepository;
import com.scott.services.FileStorageService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin("http://localhost:3000")

public class FileController {
    @Autowired
    private FileStorageService fileStorageService;

    @Autowired
    FiledbRepository fileRepository;

    @PostMapping("/files/upload")
    //@PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ResponseMessage> uploadFile(@RequestParam("file") MultipartFile file){
        String message="";
        try{
            fileStorageService.store(file);

            message = "Uploaded the file successfully: " + file.getOriginalFilename();
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
        } catch (Exception e) {
            message = "Could not upload the file: " + file.getOriginalFilename() + "!";
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
        }
    }
    @GetMapping("/files")
    public ResponseEntity<List<ResponseFile>> getListFiles(){
        List<ResponseFile> files= fileStorageService.getAllFiles().map(dbFile->{
            String fileDownloadUri = ServletUriComponentsBuilder
                    .fromCurrentContextPath()
                    .path("/files/")
                    .path(dbFile.getId())
                    .toUriString();

            return new ResponseFile(
                    dbFile.getId(),
                    dbFile.getName(),
                    fileDownloadUri,
                    dbFile.getType(),
                    dbFile.getData().length);

        }).collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.OK).body(files);
    }
    @GetMapping("/files/{id}")

    public ResponseEntity<byte[]> getFile(@PathVariable String id) {
        Filedb filedb = fileStorageService.getFile(id);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filedb.getName() + "\"")
                .body(filedb.getData());
    }
    @GetMapping("/files/find/{name}")
    public ResponseEntity<byte[]> findByName(@PathVariable String name) {
        Filedb filedb = fileStorageService.getFileByName(name);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filedb.getName() + "\"")
                .body(filedb.getData());
    }


    @GetMapping("/files/file/{id}")
    public void getFile(@PathVariable String id, HttpServletResponse response) throws IOException {
        Filedb file = fileRepository.findById(id).get();
        response.setContentType(file.getType());
        response.getOutputStream().write(file.getData());
        response.getOutputStream().close();
    }

    @GetMapping("/files/file1/{id}")
    public Filedb getFile1(@PathVariable String id) {
        return fileStorageService.getFile(id);

    }
    @GetMapping("/files/findAll")
    public List<Filedb> getAllFiles(){
        return fileStorageService.findAllFiles();
    }


    @DeleteMapping("/files/{id}/delete")
    //@PreAuthorize("hasRole('ADMIN')")
    public void deleteFile(@PathVariable String id){
        fileRepository.deleteById(id);
    }

    @GetMapping("/{offset}/{pageSize}/{field}")
    public Page<Filedb> findFilesPaginationAndSorting(@PathVariable int offset, @PathVariable int pageSize, @PathVariable String field){
        return fileRepository.findAll(PageRequest.of(offset, pageSize).withSort(Sort.by(field)));
    }
}
