package org.ums.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.joda.time.LocalTime;

import java.io.IOException;

public class UmsTimeSerializer extends JsonSerializer<LocalTime> {

  @Override
  public void serialize(LocalTime localTime, JsonGenerator jsonGenerator, SerializerProvider serializerProvider)
      throws IOException, JsonProcessingException {
    jsonGenerator.writeString(localTime.toString("HH:mm a"));
  }
}
