package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;
import com.urise.webapp.util.SerializationFileOrPath;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class ObjectStreamFileStorage extends AbstractFileStorage {
    protected ObjectStreamFileStorage(File directory) {
        super(directory);
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
