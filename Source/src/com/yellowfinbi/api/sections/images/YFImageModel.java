package com.yellowfinbi.api.sections.images;
import java.util.Base64;
public class YFImageModel implements IYFImageModel {
    private byte[] data = new byte[0];
    @Override public byte[] getAsBytes() { return data; }
    @Override public String getAsBase64() { return Base64.getEncoder().encodeToString(data); }
    @Override public void assign(byte[] data) { this.data = data != null ? data : new byte[0]; }
}
