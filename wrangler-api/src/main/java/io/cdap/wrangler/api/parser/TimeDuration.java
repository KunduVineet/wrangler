package io.cdap.wrangler.api.parser;

import com.google.gson.JsonElement;

public class TimeDuration implements Token {
    private final String value;
    private final long nanos;

    public TimeDuration(String value) {
        this.value = value;
        this.nanos = parse(value);
    }

    private long parse(String input) {
        String num = input.replaceAll("[^0-9.]", "");
        String unit = input.replaceAll("[0-9.]", "").toLowerCase();
        double val = Double.parseDouble(num);
        if (unit.equals("ms")) return (long) (val * 1_000_000);
        if (unit.equals("s")) return (long) (val * 1_000_000_000);
        return (long) val; // Default to nanos
    }

    public long getNanos() {
        return nanos;
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