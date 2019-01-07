package com.cmpe451.retro.controllers;

import com.cmpe451.retro.services.AmazonClient;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@CrossOrigin(origins = "*", allowCredentials ="true", allowedHeaders ="*",
        methods = {RequestMethod.HEAD,RequestMethod.GET,RequestMethod.POST,RequestMethod.PUT,RequestMethod.DELETE})
@RestController
public class MediaController {


    private AmazonClient amazonClient;

    @Autowired
    MediaController(AmazonClient amazonClient) {
        this.amazonClient = amazonClient;
    }

    @ApiOperation(value = "Upload a file")
    @PostMapping("/media")
    public String uploadFile(@RequestPart(value = "file") MultipartFile file) {
        return this.amazonClient.uploadFile(file);
    }

    @ApiOperation(value = "Delete a file")
    @DeleteMapping("/media")
    public void deleteFile(@RequestParam String fileUrl) {
        this.amazonClient.deleteFileFromS3Bucket(fileUrl);
    }
}