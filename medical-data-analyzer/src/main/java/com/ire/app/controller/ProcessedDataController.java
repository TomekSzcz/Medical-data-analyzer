package com.ire.app.controller;

import com.ire.app.model.AvailableData;
import com.ire.app.model.DataForChart;
import com.ire.app.model.entity.ConvertedDataInfo;
import com.ire.app.service.ManageDataService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/processed/data")
public class ProcessedDataController {

    private final static Logger LOGGER = LoggerFactory.getLogger(ProcessedDataController.class);

    @Autowired
    private ManageDataService manageDataService;

    @RequestMapping(method=RequestMethod.GET)
    public @ResponseBody
    ResponseEntity<List<AvailableData>> processedDataList(){
        LOGGER.info("Loading processed data list");
        try {
            List<AvailableData> imports = manageDataService.getImports();
            LOGGER.info("Found number of imports: {}", imports.size());
            return new ResponseEntity<>(imports, HttpStatus.OK);
        }catch (Exception e) {
            LOGGER.error("Error occured during retrieving imported data {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(method = RequestMethod.GET, path = "/chart")
    public @ResponseBody ResponseEntity<Map<String, List<DataForChart>>> chartDataList(@RequestParam("importID") int importID,
                                                                          @RequestParam("algorithm") String algorithmString) {
        LOGGER.info("Retrieving chart data for algorithm: {} , import id: {}", algorithmString, importID);
        try{
            ConvertedDataInfo.ALGORITHM algorithm = checkAndGetAlgorithm(algorithmString);
            Map<String, List<DataForChart>> chartData = manageDataService.getChartData(importID, algorithm);
            LOGGER.info("Found data with size: {}", chartData.size());
            return new ResponseEntity<>(chartData, HttpStatus.OK);
        }catch (Exception e){
            LOGGER.error("Error occurred during retrieving data for chart {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    private ConvertedDataInfo.ALGORITHM checkAndGetAlgorithm(String algorithmString){
        String algorithmTrimmed = algorithmString.trim();
        if(ConvertedDataInfo.ALGORITHM.TSNE.getAlgorithmName().equals(algorithmTrimmed)){
            return ConvertedDataInfo.ALGORITHM.TSNE;
        }else if(ConvertedDataInfo.ALGORITHM.PCA.getAlgorithmName().equals(algorithmTrimmed)){
            return ConvertedDataInfo.ALGORITHM.PCA;
        }else if(ConvertedDataInfo.ALGORITHM.LLE.getAlgorithmName().equals(algorithmTrimmed)){
            return ConvertedDataInfo.ALGORITHM.LLE;
        }else{
            throw new IllegalArgumentException("Wrong Algorithm value: " + algorithmTrimmed);
        }
    }
}
