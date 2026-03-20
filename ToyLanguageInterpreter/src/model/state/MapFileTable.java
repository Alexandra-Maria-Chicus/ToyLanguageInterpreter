package model.state;

import model.value.StringValue;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class MapFileTable implements FileTable{
    private final Map<StringValue, BufferedReader> fileTable;

    public MapFileTable() {
        this.fileTable = new HashMap<>();
    }

    public MapFileTable(Map<StringValue, BufferedReader> fileTable) {
        this.fileTable = new HashMap<>();
    }

    @Override
    public boolean isOpened(StringValue filename) {
        return fileTable.containsKey(filename);
    }

    @Override
    public void add(StringValue fileName, BufferedReader bufferedReader) {
        fileTable.put(fileName, bufferedReader);
    }

    @Override
    public BufferedReader getFile(StringValue fileName) {
        return fileTable.get(fileName);
    }

    @Override
    public void close(StringValue fileName) {
        try {
            fileTable.remove(fileName).close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public Map<String, BufferedReader> getContent() {
        Map<String, BufferedReader> content = new HashMap<>();
        for (Map.Entry<StringValue, BufferedReader> entry : fileTable.entrySet()) {
            content.put(entry.getKey().val(), entry.getValue());
        }
        return content;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("FileTable:");
        for (StringValue filenameValue : fileTable.keySet()) {
            builder.append("\n").append(filenameValue.val());
        }
        return builder.toString();
    }
}
