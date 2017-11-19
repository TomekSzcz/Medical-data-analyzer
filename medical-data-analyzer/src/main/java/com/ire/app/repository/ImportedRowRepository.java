package com.ire.app.repository;

import com.ire.app.model.entity.ImportedRow;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImportedRowRepository extends CrudRepository<ImportedRow, Integer> {
    List<ImportedRow> getAllByImportIdOrderByRowId(int importId);
}
