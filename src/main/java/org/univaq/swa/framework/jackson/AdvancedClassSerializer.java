package org.univaq.swa.framework.jackson;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import org.univaq.swa.examples.model.AdvancedClass;

public class AdvancedClassSerializer extends StdSerializer<AdvancedClass> {

    public AdvancedClassSerializer() {
        this(null);
    }

    public AdvancedClassSerializer(Class<AdvancedClass> t) {
        super(t);
    }

    @Override
    public void serialize(AdvancedClass item, JsonGenerator jgen, SerializerProvider provider)
            throws IOException, JsonProcessingException {

        jgen.writeStartObject();
        jgen.writeNumberField("intero", item.getI());
        jgen.writeStringField("stringa", item.getS());
        jgen.writeObjectField("timestamp", item.getT());
        jgen.writeObjectField("oggetto", item.getC());        
        jgen.writeEndObject();
    }
}
