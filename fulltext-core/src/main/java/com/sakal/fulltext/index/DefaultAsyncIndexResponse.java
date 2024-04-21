package com.sakal.fulltext.index;

import java.util.concurrent.CompletableFuture;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class DefaultAsyncIndexResponse<T extends IndexResponse> implements AsyncIndexResponse<T> {
  private final String reference;
  private final CompletableFuture<T> completableFutureIdxRes;

  @Override
  public String getID() {
    return this.reference;
  }

  @Override
  public CompletableFuture<T> getCompletableFutureResponse() {
    return completableFutureIdxRes;
  }
}
