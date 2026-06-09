package com.yellowfinbi.connector;

import java.io.*;
import java.nio.file.*;
import java.util.*;

/**
 * Simple INI file reader/writer.
 * Keeps credentials in a local YFSandbox.ini alongside the JAR / working directory.
 * Mirrors YFSettingsINI from the C# example.
 */
public class YFSettingsINI {

    private final Path filePath;
    private final LinkedHashMap<String, LinkedHashMap<String, String>> data = new LinkedHashMap<>();

    public YFSettingsINI(String fileName) {
        this.filePath = Paths.get(System.getProperty("user.dir"), fileName);
        load();
    }

    public String readString(String section, String key, String defaultValue) {
        var sectionData = data.get(section.toUpperCase());
        if (sectionData != null) {
            String value = sectionData.get(key.toUpperCase());
            if (value != null) return value;
        }
        return defaultValue;
    }

    public int readInteger(String section, String key, int defaultValue) {
        String value = readString(section, key, "");
        if (value.isEmpty()) return defaultValue;
        try { return Integer.parseInt(value); }
        catch (NumberFormatException e) { return defaultValue; }
    }

    public void writeString(String section, String key, String value) {
        data.computeIfAbsent(section.toUpperCase(), k -> new LinkedHashMap<>())
            .put(key.toUpperCase(), value);
        save();
    }

    private void load() {
        if (!Files.exists(filePath)) return;
        try {
            String currentSection = "";
            for (String line : Files.readAllLines(filePath)) {
                String trimmed = line.trim();
                if (trimmed.isEmpty() || trimmed.startsWith(";")) continue;

                if (trimmed.startsWith("[") && trimmed.endsWith("]")) {
                    currentSection = trimmed.substring(1, trimmed.length() - 1).toUpperCase();
                    data.computeIfAbsent(currentSection, k -> new LinkedHashMap<>());
                } else if (!currentSection.isEmpty()) {
                    int eq = trimmed.indexOf('=');
                    if (eq > 0) {
                        String key = trimmed.substring(0, eq).trim().toUpperCase();
                        String value = trimmed.substring(eq + 1).trim();
                        data.get(currentSection).put(key, value);
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Warning: Could not read " + filePath + ": " + e.getMessage());
        }
    }

    private void save() {
        try (PrintWriter writer = new PrintWriter(Files.newBufferedWriter(filePath))) {
            for (var entry : data.entrySet()) {
                writer.println("[" + entry.getKey() + "]");
                for (var kvp : entry.getValue().entrySet()) {
                    writer.println(kvp.getKey() + "=" + kvp.getValue());
                }
                writer.println();
            }
        } catch (IOException e) {
            System.err.println("Warning: Could not write " + filePath + ": " + e.getMessage());
        }
    }
}
