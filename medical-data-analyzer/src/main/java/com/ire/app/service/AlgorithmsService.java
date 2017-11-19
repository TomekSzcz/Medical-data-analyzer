package com.ire.app.service;

import com.ire.app.model.DataForAlgorithm;
import com.ire.app.model.DataToConvert;
import org.springframework.stereotype.Service;

@Service
public interface AlgorithmsService {

    double[][] usePcaAlgorithm(double[][] dataMatrix);

    double[][] useTSneAlgorithm(double[][] dataMatrix);

    DataForAlgorithm prepareDataForAlgorithm(DataToConvert dataToConvert);

}
