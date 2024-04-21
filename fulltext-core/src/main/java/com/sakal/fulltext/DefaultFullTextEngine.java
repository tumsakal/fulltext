package com.sakal.fulltext;

import com.sakal.fulltext.index.DefaultAsyncIndexResponse;
import com.sakal.fulltext.index.DefaultIndexingContext;
import com.sakal.fulltext.index.DocumentIndexer;
import com.sakal.fulltext.index.IndexRequest;
import com.sakal.fulltext.index.IndexResponse;
import com.sakal.fulltext.store.IndexRequestStore;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Getter
@Setter
@Slf4j
public class DefaultFullTextEngine extends AbstractFullTextEngine {

  private ExecutorService executorService;

  public DefaultFullTextEngine(
      FullTextPlatformFactory fullTextPlatformFactory, IndexRequestStore indexRequestStore) {
    super(fullTextPlatformFactory, indexRequestStore);
  }

  @SuppressWarnings("unchecked")
  @Override
  protected <T extends IndexRequest, R extends IndexResponse> R doIndexInternal(
          DocumentIndexer<T, R> documentIndexer, T indexRequest, DefaultIndexingContext context) {

    CompletableFuture<R> completableFutureIdxRes =
        CompletableFuture.supplyAsync(
                () -> documentIndexer.index(indexRequest, context), this.getExecutorService())
            .whenComplete(
                (response, throwable) -> {
                  if (throwable != null) {
                    this.onIndexingFailed(indexRequest);
                  }
                });

    return (R) new DefaultAsyncIndexResponse<>(context.getReference(), completableFutureIdxRes);
  }

  @Override
  public void shutdown() {
    this.executorService.shutdown();
    log.info("FullTextEngine is completely shut down");
  }
}
