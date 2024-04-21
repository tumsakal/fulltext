package com.sakal.fulltext.index;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.stream.Collectors;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
public class DefaultDocumentIndexerFactory extends DocumentIndexerFactory {
  private final Map<String, DocumentIndexer<? extends IndexRequest, ? extends IndexResponse>>
      cachedIndexers = new ConcurrentHashMap<>();

  @Setter private String type;

  public DefaultDocumentIndexerFactory(
      String type,
      List<DocumentIndexer<? extends IndexRequest, ? extends IndexResponse>> documentIndexers) {
    this.addIndexers(documentIndexers);
    this.type = type;
  }

  @Override
  public String getType() {
    return this.type;
  }

  @SuppressWarnings("unchecked")
  @Override
  public DocumentIndexer<IndexRequest, IndexResponse> getDocumentIndexer(String indexKey) {
    return (DocumentIndexer<IndexRequest, IndexResponse>) cachedIndexers.get(indexKey);
  }

  public void addIndexer(
      String indexKey,
      DocumentIndexer<? extends IndexRequest, ? extends IndexResponse> documentIndexer) {
    this.cachedIndexers.put(indexKey, documentIndexer);
  }

  public void addIndexers(
      List<DocumentIndexer<? extends IndexRequest, ? extends IndexResponse>> indexers) {
    Map<String, DocumentIndexer<? extends IndexRequest, ? extends IndexResponse>> indexerMap =
        indexers.stream()
            .collect(Collectors.toMap(DocumentIndexer::getIndexKey, Function.identity()));
    this.cachedIndexers.putAll(indexerMap);
  }
}
