package com.ire.app.controller;

import com.ire.app.service.ManageDataService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * Created by Tomek on 01.11.2017.
 */
@RestController
@RequestMapping("/import")
public class ImportDataController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ImportDataController.class);

    @Autowired
    private ManageDataService manageDataService;

    @RequestMapping(value="/fileUpload",method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<?> uploadFile(
            @RequestParam("file") MultipartFile uploadFile) {

        LOGGER.info("New file to upload {}", uploadFile.getOriginalFilename());
        boolean wasSuccess = manageDataService.importData(uploadFile);
        if (wasSuccess){
            return new ResponseEntity(HttpStatus.OK);
        }
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

    @RequestMapping("/test")
    public String test(){
        return "OK";
    }
}
