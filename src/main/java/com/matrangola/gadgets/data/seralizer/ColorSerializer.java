package com.matrangola.gadgets.data.seralizer;


import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import com.matrangola.gadgets.data.model.Color;

import java.io.IOException;

public class ColorSerializer extends JsonSerializer<Color> {


    @Override
    public void serialize(Color value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeStartObject();
        gen.writeStringField("rgb",
                String.format("%02x%02x%02x", value.getRed(), value.getGreen(), value.getBlue()));
        gen.writeEndObject();
    }
}
