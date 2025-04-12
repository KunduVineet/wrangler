package io.cdap.wrangler.api.parser;

import com.google.gson.JsonElement;

public class ByteSize implements Token {
    private final String value;
    private final long bytes;

    public ByteSize(String value) {
        this.value = value;
        this.bytes = parse(value);
    }

    private long parse(String input) {
        String num = input.replaceAll("[^0-9.]", "");
        String unit = input.replaceAll("[0-9.]", "").toUpperCase();
        double val = Double.parseDouble(num);
        if (unit.equals("KB")) return (long) (val * 1024);
        if (unit.equals("MB")) return (long) (val * 1024 * 1024);
        return (long) val; // Default to bytes
    }

    public long getBytes() {
        return bytes;
    }

    @Override
    public String getValue() {
        return value;
    }

    @Override
    public Object value() {
        return null;
    }

    @Override
    public TokenType type() {
        return null;
    }

    @Override
    public JsonElement toJson() {
        return null;
    }
}