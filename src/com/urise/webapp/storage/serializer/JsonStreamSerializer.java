package com.urise.webapp.storage.serializer;

import com.urise.webapp.model.Resume;
import com.urise.webapp.util.JsonParser;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class JsonStreamSerializer implements StreamSerializer{

    @Override
    public void doWrite(Resume resume, OutputStream os) throws IOException {
        try(OutputStreamWriter outputStreamWriter = new OutputStreamWriter(os, StandardCharsets.UTF_8)) {
            JsonParser.write(resume, outputStreamWriter);

        }
    }

    @Override
    public Resume doRead(InputStream is) throws IOException {
        try (InputStreamReader inputStreamReader = new InputStreamReader(is, StandardCharsets.UTF_8)){
            return JsonParser.read(inputStreamReader, Resume.class);
        }
    }
}
