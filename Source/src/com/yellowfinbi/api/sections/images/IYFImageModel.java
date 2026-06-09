package com.yellowfinbi.api.sections.images;
public interface IYFImageModel {
    byte[] getAsBytes(); String getAsBase64(); void assign(byte[] data);
}
