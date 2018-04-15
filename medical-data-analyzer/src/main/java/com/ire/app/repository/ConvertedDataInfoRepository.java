package com.ire.app.repository;

import com.ire.app.model.entity.ConvertedDataInfo;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConvertedDataInfoRepository extends CrudRepository<ConvertedDataInfo, Integer> {

    ConvertedDataInfo findByImportIdAndAlgorithm(int importId, String algorithm);
}
