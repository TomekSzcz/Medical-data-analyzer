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
@RestController()
public class ImportDataController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ImportDataController.class);

    @Autowired
    private Environment env;

    @Autowired
    private ManageDataService manageDataService;

    @RequestMapping(value="/fileUpload",method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<?> uploadFile(
            @RequestParam("file") MultipartFile uploadFile) {

        try {
            String filename = uploadFile.getOriginalFilename();
            String directory = env.getProperty("paths.uploadedFiles");
            manageDataService.importData(uploadFile);
//            BufferedOutputStream stream =
//                    new BufferedOutputStream(new FileOutputStream(new File()));
//            stream.write(uploadFile.getBytes());
//            stream.close();
        }
        catch (Exception e) {
            LOGGER.error(e.getMessage());
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity(HttpStatus.OK);
    }
    @RequestMapping("/test")
    public String test(){
        return "OK";
    }
}
