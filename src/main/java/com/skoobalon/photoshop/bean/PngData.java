package com.skoobalon.photoshop.bean;

import java.util.Arrays;

public class PngData {
    private byte[] data = new byte[0];

    public PngData() {

    }

    public PngData(PngData other) {
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
        return "PngData{" +
                "data=(" + data.length + " bytes)" +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PngData pngData = (PngData) o;
        return Arrays.equals(data, pngData.data);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(data);
    }
}
