package org.ums.serializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import org.joda.time.LocalTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.io.IOException;

public class UmsTimeDeserializer extends JsonDeserializer<LocalTime> {
  @Override
  public LocalTime deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
      throws IOException, JsonProcessingException {
    DateTimeFormatter formatter = DateTimeFormat.forPattern("HH:mm a");
    return formatter.parseLocalTime(jsonParser.getText());
  }
}
