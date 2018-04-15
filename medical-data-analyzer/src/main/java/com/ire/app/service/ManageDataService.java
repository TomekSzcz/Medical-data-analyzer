package com.ire.app.service;


import com.ire.app.model.AvailableData;
import com.ire.app.model.DataForChart;
import com.ire.app.model.entity.ConvertedDataInfo;
import com.ire.app.model.DataToConvert;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ManageDataService {

    boolean importData(MultipartFile multipartFile) throws IOException;

    void saveProcessedData(ConvertedDataInfo convertedDataInfo, double[][] dataMatrix, List<String> diagnoses,
                           List<Integer> originalDataRowsIds);

    List<DataToConvert> collectDataForTSNE();

    List<DataToConvert> collectDataForPCA();

    List<DataToConvert> collectDataForLLE();

    List<AvailableData> getImports();

    List<DataForChart> getChartData(int importId, ConvertedDataInfo.ALGORITHM algorithm);

}
