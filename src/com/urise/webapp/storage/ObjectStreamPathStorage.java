package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;
import com.urise.webapp.util.SerializationFileOrPath;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Path;

public class ObjectStreamPathStorage extends AbstractPathStorage {
    protected ObjectStreamPathStorage(Path directory) {
        super(directory.getFileName().toString());
        strategy = new SerializationFileOrPath();
    }

    @Override
    public void doWrite(Resume resume, OutputStream os) throws IOException {
        strategy.doWrite(resume, os);
    }

    @Override
    public Resume doRead(InputStream is) throws IOException {
        return strategy.doRead(is);
    }
}
