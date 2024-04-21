package com.sakal.fulltext.store;

import com.sakal.fulltext.index.IndexRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.ZonedDateTime;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@RequiredArgsConstructor
public class PersistentIndexRequest implements IndexRequest {
  private final ObjectMapper objectMapper;
  private IndexRequest delegate;

  @Setter private int processCount;
  @Setter private ZonedDateTime lastProcessTime;

  @Override
  public String getIndexKey() {
    return delegate.getIndexKey();
  }

  public String toJsonData() throws JsonProcessingException {
    return this.objectMapper.writeValueAsString(delegate);
  }

  public void fromJsonData(String value) throws JsonProcessingException {
    this.delegate = this.objectMapper.readValue(value, delegate.getClass());
  }
}
