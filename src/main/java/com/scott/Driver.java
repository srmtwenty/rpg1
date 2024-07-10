package com.scott;


import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Driver implements ApplicationRunner {
    //@Autowired
    //ImageRepository imageRepository;

    public static void main(String [] args){
        SpringApplication.run(Driver.class, args);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception{
        //var image=new Image();
        //image.setFilename("test1.jpg");
        //image.setMineType("image/jpeg");
        //image.setData(Files.readAllBytes(Paths.get("test1.jpg")));
        //imageRepository.save(image);
    }
}
