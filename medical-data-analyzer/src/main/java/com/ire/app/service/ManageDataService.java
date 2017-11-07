package com.ire.app.service;


import org.springframework.web.multipart.MultipartFile;

public interface ManageDataService {

    boolean importData(MultipartFile multipartFile);

}
