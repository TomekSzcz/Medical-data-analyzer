package com.ire.app.repository;

import com.ire.app.model.entity.ConvertedDataRows;
import com.ire.app.model.entity.ConvertedDataRowsPK;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConvertedDataRowsRepository extends CrudRepository<ConvertedDataRows, ConvertedDataRowsPK> {

    List<ConvertedDataRows> getAllByConvertedDataRowsPK_ConvertedDataIdOrderByConvertedDataRowsPK_ConvertedDataId(int copyId);
}
