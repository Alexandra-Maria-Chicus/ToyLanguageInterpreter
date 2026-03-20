package model.state;

import model.value.StringValue;

import java.io.BufferedReader;
import java.io.IOException;

public interface FileTable {
    boolean isOpened(StringValue filename);
    void add(StringValue fileName, BufferedReader bufferedReader);
    BufferedReader getFile(StringValue fileName);
    void close(StringValue fileName);
}
