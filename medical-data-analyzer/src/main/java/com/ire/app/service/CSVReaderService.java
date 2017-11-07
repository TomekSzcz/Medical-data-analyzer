package com.ire.app.service;

import java.io.File;
import java.util.List;

public interface CSVReaderService {

    List<List<String>> readCSVfile(File file);
}
