package com.ire.app.service.impl;

import com.ire.app.service.CSVReaderService;
import com.ire.app.service.ManageDataService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class CSVReaderServiceImpl implements CSVReaderService {
    private final static Logger LOGGER = LoggerFactory.getLogger(CSVReaderService.class);

    private static final String SEPARATOR = ";";

    @Override
    public List<List<String>> readCSVfile(File file) {
        LOGGER.info("file name: {}", file.getName());
        return parseCSVfile(file);
    }

    private List<List<String>> parseCSVfile(File file) {
        try (Stream<String> lines = Files.lines(Paths.get(file.toURI()))) {
            List<List<String>> values = lines.map(line -> Arrays.asList(line.split(","))).collect(Collectors.toList());
            values.forEach(value -> LOGGER.info(value.toString()));
            return values;
        } catch (IOException e) {
            LOGGER.error("Exception in parseCSVfile method. Cannot read CSV file {} ", e.getMessage());
            throw new IllegalArgumentException();
        }
    }
}

