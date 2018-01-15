package com.ire.app.controller;

import com.ire.app.model.AlgorithmConfigData;
import com.ire.app.service.AlgorithmsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/config")
public class AlgorithmConfigController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AlgorithmConfigController.class);

    @Autowired
    private AlgorithmsService algorithmsService;

    @RequestMapping(method= RequestMethod.GET, path = "/algorithm/data")
    public @ResponseBody
    ResponseEntity<List<AlgorithmConfigData>> algorithmConfigList(){
        LOGGER.info("Loading algorithm data list");
        try {
            List<AlgorithmConfigData> configDataList  =
                    algorithmsService.getActualAlgorithmsConfiguration();
            LOGGER.info("Config size: {}", configDataList.size());
            return new ResponseEntity<>(configDataList, HttpStatus.OK);
        }catch (Exception e) {
            LOGGER.error("Error occured during retrieving algorithm config data {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value="/algorithm",method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<?> saveConfig(@RequestBody AlgorithmConfigData algorithmConfigData){
        LOGGER.info("Saving config for: {}", algorithmConfigData.getAlgorithmName());
        try {
            algorithmsService.saveAlgorithmConfiguration(algorithmConfigData);
            return new ResponseEntity<>(HttpStatus.OK);
        }catch (Exception e) {
            LOGGER.error("Error occured during saving algorithm config data {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
