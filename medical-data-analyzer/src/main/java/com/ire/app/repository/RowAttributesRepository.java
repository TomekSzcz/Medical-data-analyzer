package com.ire.app.repository;

import com.ire.app.model.entity.RowAttribute;
import com.ire.app.model.entity.RowAttributePK;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RowAttributesRepository extends CrudRepository<RowAttribute, RowAttributePK> {
    List<RowAttribute> getAllByCopyRowIdOrderByCopyRowId(int rowId);
}
