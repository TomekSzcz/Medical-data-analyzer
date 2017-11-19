package com.ire.app.scheduler;


import com.ire.app.model.entity.ConvertedDataInfo;
import com.ire.app.model.DataForAlgorithm;
import com.ire.app.model.DataToConvert;
import com.ire.app.service.AlgorithmsService;
import com.ire.app.service.ManageDataService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class convertImportedDataScheduler {
    private static final Logger LOGGER = LoggerFactory.getLogger(convertImportedDataScheduler.class);

    @Autowired
    private ManageDataService manageDataService;

    @Autowired
    private AlgorithmsService algorithmsService;



    @Scheduled(fixedRate = 20000)
    public void convertDataForTSNE() {
        LOGGER.info("Schedule for TSNE started");
        List<DataToConvert> dataToConvertList = manageDataService.collectDataForTSNE();
        LOGGER.info("Found imports: {}", dataToConvertList.size());
        dataToConvertList.stream().forEach(dataToConvert -> {
            DataForAlgorithm dataForAlgorithm = algorithmsService.prepareDataForAlgorithm(dataToConvert);
            double[][] resultsTSNE = algorithmsService.useTSneAlgorithm(dataForAlgorithm.getData());
            LOGGER.info("Results for TSNE size: {}", resultsTSNE[0].length);
            ConvertedDataInfo convertedDataInfo = prepareProccessedData(dataToConvert.getImportedDataModel().getFileName(),
                    ConvertedDataInfo.ALGORITHM.TSNE, dataToConvert.getImportedDataModel().getId());
            manageDataService.saveProcessedData(convertedDataInfo,
                    resultsTSNE, dataForAlgorithm.getDiagnoses());
        });
    }

 //   @Scheduled(fixedRate = 500000)
    public void convertDataForPSA() {
        LOGGER.info("Schedule for PSA started");
        List<DataToConvert> dataToConvertList = manageDataService.collectDataForPSA();
        LOGGER.info("Found imports: {}", dataToConvertList.size());
    }

    private ConvertedDataInfo prepareProccessedData(String fileName,
                                                    ConvertedDataInfo.ALGORITHM algorithm, int importID) {
        ConvertedDataInfo convertedDataInfo = new ConvertedDataInfo();
        convertedDataInfo.setFileName(fileName);
        convertedDataInfo.setAlgorithm(algorithm);
        convertedDataInfo.setImportId(importID);
        return convertedDataInfo;
    }


}
