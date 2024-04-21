package com.sakal.fulltext.query;

import java.util.HashMap;
import java.util.Objects;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class MapQueryRequest extends HashMap<String, Object> implements QueryRequest {
  private final transient String queryName;

  @Override
  public String getQueryName() {
    return this.queryName;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    if (!super.equals(o)) return false;
    MapQueryRequest that = (MapQueryRequest) o;
    return Objects.equals(queryName, that.queryName);
  }

  @Override
  public int hashCode() {
    return Objects.hash(super.hashCode(), queryName);
  }
}
