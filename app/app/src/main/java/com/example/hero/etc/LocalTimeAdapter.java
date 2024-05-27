package com.example.hero.etc;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class LocalTimeAdapter extends TypeAdapter<LocalTime> {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");

    @Override
    public void write(JsonWriter jsonWriter, LocalTime localTime) throws IOException {
        if (localTime == null) {
            jsonWriter.nullValue();
        } else {
            jsonWriter.value(localTime.format(formatter));
        }
    }

    @Override
    public LocalTime read(JsonReader jsonReader) throws IOException {
        if (jsonReader.peek() == JsonToken.NULL) {
            jsonReader.nextNull();
            return null;
        }
        return LocalTime.parse(jsonReader.nextString(), formatter);
    }
}

