package com.ire.app.repository;

import com.ire.app.model.entity.ImportedDataModel;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImportedDataRepository extends CrudRepository<ImportedDataModel, Integer> {

    List<ImportedDataModel> getAllByImportStatusAndConvertedByPSA(String importStatus, boolean isConv);

    List<ImportedDataModel> getAllByImportStatusAndConvertedByTSNE(String importStatus, boolean isConv);

    ImportedDataModel getById(int id);

    List<ImportedDataModel> getAllByImportStatus(String importStatus);
}
