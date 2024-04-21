package com.sakal.fulltext.store;

import com.sakal.fulltext.util.Builder;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class IndexRequestStoreBuilder extends Builder<IndexRequestStore> {
  private IndexRequestStore indexRequestStore;

  public static IndexRequestStoreBuilder getInstance() {
    return getDefault();
  }

  public static IndexRequestStoreBuilder getDefault() {
    IndexRequestStoreBuilder indexRequestStoreBuilder = new IndexRequestStoreBuilder();
    indexRequestStoreBuilder.indexRequestStore = new InMemoryIndexRequestStore();
    return indexRequestStoreBuilder;
  }

  @Override
  public IndexRequestStore build() {
    return this.indexRequestStore;
  }
}
