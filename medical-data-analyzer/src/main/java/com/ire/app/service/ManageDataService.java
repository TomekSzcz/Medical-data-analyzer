package com.ire.app.service;


import com.ire.app.model.AvailableData;
import com.ire.app.model.DataForChart;
import com.ire.app.model.entity.ConvertedDataInfo;
import com.ire.app.model.DataToConvert;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ManageDataService {

    boolean importData(MultipartFile multipartFile);

    void saveProcessedData(ConvertedDataInfo convertedDataInfo, double[][] dataMatrix, List<String> diagnoses);

    List<DataToConvert> collectDataForTSNE();

    List<DataToConvert> collectDataForPSA();

    List<AvailableData> getImports();

    List<DataForChart> getChartData(int importId, ConvertedDataInfo.ALGORITHM algorithm);

}
