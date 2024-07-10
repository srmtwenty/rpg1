package com.scott.services;


import com.scott.models.Filedb;
import com.scott.repositories.FiledbRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Stream;

@Service
public class FileStorageService {

    @Autowired
    private FiledbRepository filedbRepository;

    public Filedb store(MultipartFile file) throws IOException {
        String fileName= StringUtils.cleanPath(file.getOriginalFilename());
        Filedb Filedb=new Filedb(fileName, file.getContentType(), file.getBytes());

        return filedbRepository.save(Filedb);
    }
    public Filedb getFile(String id){
        return filedbRepository.findById(id).get();
    }
    public Stream<Filedb> getAllFiles(){
        return filedbRepository.findAll().stream();
    }

    public Filedb getFileByName(String name){
        return filedbRepository.findByName(name);
    }

    public List<Filedb> findAllFiles(){
        return filedbRepository.findAll();
    }


}
