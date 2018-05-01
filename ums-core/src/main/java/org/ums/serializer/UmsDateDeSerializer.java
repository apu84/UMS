package org.ums.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import org.ums.util.UmsUtils;

import java.io.IOException;
import java.util.Date;

public class UmsDateDeSerializer extends StdDeserializer<Date> {
  public UmsDateDeSerializer() {
    this(null);
  }

  public UmsDateDeSerializer(Class T) {
    super(T);
  }

  @Override
  public Date deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException,
      JsonProcessingException {
    Date date = new Date();
    try {
      date = UmsUtils.convertToDate(jsonParser.getText(), "dd-MM-yyyy");
    } catch(Exception e) {
      e.printStackTrace();
    }
    return date;
  }
}
