package com.webshop.model;

public enum PictureFileType {
    png, jpg, gif;

    public static PictureFileType getFromMimeType(String mimetype) {
        if (mimetype.compareTo("image/jpeg") == 0) {
            return jpg;
        }
        if (mimetype.compareTo("image/png") == 0) {
            return png;
        }
        if (mimetype.compareTo("image/gif") == 0) {
            return gif;
        }
        throw new IllegalArgumentException("bad mimetype:" + mimetype);
    }

}
