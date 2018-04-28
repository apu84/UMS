package org.ums.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import org.ums.util.UmsUtils;

import java.io.IOException;
import java.util.Date;

/**
 * Created by Monjur-E-Morshed on 28-Apr-18.
 */
public class UmsDateSerializer extends StdSerializer<Date> {

  public UmsDateSerializer() {
    this(null);
  }

  public UmsDateSerializer(Class T) {
    super(T);
  }

  @Override
  public void serialize(Date pDate, JsonGenerator pJsonGenerator, SerializerProvider pSerializerProvider)
      throws IOException {
    pJsonGenerator.writeString(UmsUtils.formatDate(pDate, "dd-MM-yyyy"));
  }
}
