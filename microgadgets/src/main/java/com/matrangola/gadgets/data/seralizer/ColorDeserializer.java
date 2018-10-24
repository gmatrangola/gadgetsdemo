package com.matrangola.gadgets.data.seralizer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.matrangola.gadgets.data.model.Color;

import java.io.IOException;

public class ColorDeserializer extends JsonDeserializer<Color> {
    @Override
    public Color deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        JsonNode node = p.getCodec().readTree(p);
        String rgb = node.get("rgb").textValue();
        Color color = new Color();
        color.setRed(Integer.valueOf(rgb.substring(0,2), 16));
        color.setGreen(Integer.valueOf(rgb.substring(2,4), 16));
        color.setBlue(Integer.valueOf(rgb.substring(4,6), 16));
        return color;
    }
}
