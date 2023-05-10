package org.univaq.swa.framework.jackson;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import java.util.Calendar;
import org.univaq.swa.examples.model.AdvancedClass;

public class AdvancedClassDeserializer extends StdDeserializer<AdvancedClass> {

    public AdvancedClassDeserializer() {
        this(null);
    }

    public AdvancedClassDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public AdvancedClass deserialize(JsonParser jp, DeserializationContext ctxt)
            throws IOException, JsonProcessingException {
        JsonNode node = jp.getCodec().readTree(jp);
        int intero = node.has("intero") ? node.get("intero").asInt() : 0;
        String stringa = node.has("stringa") ? node.get("stringa").asText() : "";
        AdvancedClass oggetto = null;
        if (node.has("oggetto")) {
            JsonParser inner_jp = node.get("oggetto").traverse();
            inner_jp.setCodec(jp.getCodec());
            oggetto = inner_jp.readValueAs(new TypeReference<AdvancedClass>() {
            });
        }

        Calendar timestamp = null;
        if (node.has("timestamp")) {
            JsonParser inner_jp = node.get("timestamp").traverse();
            inner_jp.setCodec(jp.getCodec());
            timestamp = inner_jp.readValueAs(new TypeReference<Calendar>() {
            });
        }
        AdvancedClass doc = new AdvancedClass(stringa, intero, oggetto, timestamp);
        return doc;
    }
}
