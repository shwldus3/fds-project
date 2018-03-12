package com.kakao.event;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class EventMapper {
  private static final ObjectMapper mapper = new ObjectMapper();

  public static String serializer(Object obj) {
    try {
      return mapper.writeValueAsString(obj);
    } catch (JsonProcessingException e) {
      throw new RuntimeException(e);
    }
  }

  public static <T> T deserializer(String str, Class<T> tClass) {
    try {
      return mapper.readValue(str, tClass);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  public static Event parse(String msg) {
    try {
      JsonNode obj = mapper.readTree(msg);
      String type = obj.get("type").textValue();

      if (type == null) return null;

      if (NewAccountEvent.class.getSimpleName().equals(type)) {
        return deserializer(msg, NewAccountEvent.class);
      } else if (DepositEvent.class.getSimpleName().equals(type)) {
        return deserializer(msg, DepositEvent.class);
      } else if (RemittanceEvent.class.getSimpleName().equals(type)) {
        return deserializer(msg, RemittanceEvent.class);
      } else if (WithdrawEvent.class.getSimpleName().equals(type)) {
        return deserializer(msg, WithdrawEvent.class);
      } else {
        return deserializer(msg, DefaultEvent.class);
      }
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
}
