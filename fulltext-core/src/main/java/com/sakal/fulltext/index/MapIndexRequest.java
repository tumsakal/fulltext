package com.sakal.fulltext.index;

import java.util.HashMap;
import java.util.Objects;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class MapIndexRequest extends HashMap<String, Object> implements IndexRequest {
  private final transient String indexKey;

  @Override
  public String getIndexKey() {
    return this.indexKey;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    if (!super.equals(o)) return false;
    MapIndexRequest that = (MapIndexRequest) o;
    return Objects.equals(indexKey, that.indexKey);
  }

  @Override
  public int hashCode() {
    return Objects.hash(super.hashCode(), indexKey);
  }
}
