package com.sakal.fulltext.index;

import java.util.Objects;

public class DefaultIndexingContext extends IndexContext {

  public DefaultIndexingContext(IndexContext indexContext) {
    this.putAll(indexContext);
  }

  public String getReference() {
    return Objects.requireNonNullElse(this.get("IDX-REQ-ID"), "").toString();
  }

  public void setReference(String reference) {
    this.put("IDX-REQ-ID", reference);
  }
}
