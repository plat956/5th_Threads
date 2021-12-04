package by.latushko.training.reader;

import by.latushko.training.exception.InputFileReadException;

import java.util.List;

public interface CustomDataReader {
    String read(String filePath) throws InputFileReadException;
    List<String> readLines(String filePath) throws InputFileReadException;
}
