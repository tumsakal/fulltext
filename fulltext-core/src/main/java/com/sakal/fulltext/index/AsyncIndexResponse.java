package com.sakal.fulltext.index;

import java.util.concurrent.CompletableFuture;

public interface AsyncIndexResponse<T extends IndexResponse> extends IndexResponse {
  CompletableFuture<T> getCompletableFutureResponse();
}
