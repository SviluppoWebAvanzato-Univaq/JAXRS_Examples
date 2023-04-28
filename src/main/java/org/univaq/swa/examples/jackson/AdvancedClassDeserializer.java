package org.univaq.swa.examples.jackson;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import org.univaq.swa.examples.base.AdvancedClass;

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
            JsonParser inner_jp = node.findValue("oggetto").traverse();
            inner_jp.setCodec(jp.getCodec());
            oggetto = inner_jp.readValueAs(new TypeReference<AdvancedClass>() {
            });
        }
        AdvancedClass doc = new AdvancedClass(stringa, intero, oggetto);
        return doc;
    }
}
