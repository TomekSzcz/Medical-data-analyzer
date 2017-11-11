package com.ire.app.repository;

import com.ire.app.model.RowAttribute;
import com.ire.app.model.RowAttributePK;
import org.springframework.data.repository.CrudRepository;

public interface RowAttributesRepository extends CrudRepository<RowAttribute, RowAttributePK> {
}
