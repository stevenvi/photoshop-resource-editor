package com.skoobalon.photoshop.bean;

import java.util.Arrays;

/**
 * This file seems useless, just copy it verbatim
 */
public class Headerfile {
    private byte[] data = new byte[0];

    public Headerfile() {

    }

    public Headerfile(Headerfile other) {
        this.data = Arrays.copyOf(other.getData(), other.getData().length);
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data == null ? new byte[0] : data;
    }

    @Override
    public String toString() {
        return "Headerfile{" +
                "data=" + Arrays.toString(data) +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Headerfile that = (Headerfile) o;

        return Arrays.equals(data, that.data);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(data);
    }
}
