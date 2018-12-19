package com.distributed.problem.feign;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

public class subProblemSerializer extends JsonSerializer<subProblem> {
    @Override
    public void serialize(subProblem value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeStartObject();
        gen.writeFieldName("code");
        gen.writeString(value.getCode());
        gen.writeFieldName("isbn");
        gen.writeString(value.getPro_hash());
        gen.writeFieldName("content");
        gen.writeString(value.getContent());
        gen.writeFieldName("localDate");
        gen.writeString(String.valueOf(value.getLocalDate()));
        gen.writeFieldName("loalDateTime");
        gen.writeString(String.valueOf(value.getLocalDateTime()));
        gen.writeEndObject();
    }
}
