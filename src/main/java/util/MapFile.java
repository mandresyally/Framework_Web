package main.java.util;

public class MapFile {
    private final String nom;
    private final byte[] bytes;

    public MapFile(String nom, byte[] bytes) {
        this.nom = nom;
        this.bytes = bytes;
    }

    public String getNom() {
        return nom;
    }

    public byte[] getBytes() {
        return bytes;
    }

    public String getExtension() {
        int lastDot = nom == null ? -1 : nom.lastIndexOf('.');
        return lastDot >= 0 && lastDot < nom.length() - 1 ? nom.substring(lastDot + 1) : "";
    }
}
