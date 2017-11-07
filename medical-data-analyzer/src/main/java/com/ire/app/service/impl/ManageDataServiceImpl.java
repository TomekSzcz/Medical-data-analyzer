package com.ire.app.service.impl;

import com.ire.app.service.CSVReaderService;
import com.ire.app.service.ManageDataService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

@Service
public class ManageDataServiceImpl implements ManageDataService {

    private final static Logger LOGGER = LoggerFactory.getLogger(ManageDataService.class);

    @Autowired
    private CSVReaderService csvReaderService;


    @Override
    public boolean importData(MultipartFile multipartFile) {
        try {
            File file = transferMultipartToConvFile(multipartFile);
            csvReaderService.readCSVfile(file);

        } catch (Exception e) {
            LOGGER.error("Cannot import data {}", e.getMessage());
            return false;
        }
        return true;
    }

    private File transferMultipartToConvFile(MultipartFile multipartFile) throws IOException {
        File convFile = new File(multipartFile.getOriginalFilename());
        convFile.createNewFile();
        FileOutputStream fos = new FileOutputStream(convFile);
        fos.write(multipartFile.getBytes());
        fos.close();
        return convFile;
    }
}
