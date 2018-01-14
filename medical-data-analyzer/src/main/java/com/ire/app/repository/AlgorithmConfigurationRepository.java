package com.ire.app.repository;

import com.ire.app.model.entity.AlgorithmsConfig;
import org.springframework.data.repository.CrudRepository;

public interface AlgorithmConfigurationRepository
        extends CrudRepository<AlgorithmsConfig, Integer> {

    AlgorithmsConfig findTopByAlgorithmNameOrderByIdDesc(String algorithmName);
}
