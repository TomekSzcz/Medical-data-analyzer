package com.ire.app.service;

import com.ire.app.model.AlgorithmConfigData;
import com.ire.app.model.DataForAlgorithm;
import com.ire.app.model.DataToConvert;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface AlgorithmsService {

    double[][] usePcaAlgorithm(double[][] dataMatrix);

    double[][] useTSneAlgorithm(double[][] dataMatrix);

    double[][] useLLEAlgorithm(double[][] dataMatrix);

    DataForAlgorithm prepareDataForAlgorithm(DataToConvert dataToConvert);

    List<AlgorithmConfigData> getActualAlgorithmsConfiguration();

    void saveAlgorithmConfiguration(AlgorithmConfigData algorithmConfigData);

}
