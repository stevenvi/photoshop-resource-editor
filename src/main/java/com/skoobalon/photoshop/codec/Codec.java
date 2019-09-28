package com.skoobalon.photoshop.codec;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public interface Codec<T> {
    byte[] serialize(T input);
    T deserialize(byte[] data);

    default void serializeToFile(T input, String filename) throws IOException {
        FileOutputStream os = new FileOutputStream(filename);
        os.write(serialize(input));
        os.close();
    }

    default T deserialize(String filename) throws IOException {
        FileInputStream is = new FileInputStream(filename);
        byte[] data = is.readAllBytes();
        is.close();
        return deserialize(data);
    }
}
