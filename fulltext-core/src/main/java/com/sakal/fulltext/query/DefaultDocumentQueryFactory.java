package com.sakal.fulltext.query;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.stream.Collectors;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
public class DefaultDocumentQueryFactory extends DocumentQueryFactory {
  private final Map<String, DocumentQuery<? extends QueryRequest, ? extends QueryResponse>>
      cachedSearchers = new ConcurrentHashMap<>();

  @Setter private String type;

  public DefaultDocumentQueryFactory(
      String type, List<DocumentQuery<? extends QueryRequest, ? extends QueryResponse>> searchers) {
    this.addSearchers(searchers);
    this.type = type;
  }

  @Override
  public String getType() {
    return this.type;
  }

  @SuppressWarnings("unchecked")
  @Override
  public DocumentQuery<QueryRequest, QueryResponse> getDocumentQuery(String key) {
    return (DocumentQuery<QueryRequest, QueryResponse>) cachedSearchers.get(key);
  }

  public void addSearcher(
      String indexKey,
      DocumentQuery<? extends QueryRequest, ? extends QueryResponse> documentIndexer) {
    this.cachedSearchers.put(indexKey, documentIndexer);
  }

  public void addSearchers(
      List<DocumentQuery<? extends QueryRequest, ? extends QueryResponse>> searchers) {
    Map<String, DocumentQuery<? extends QueryRequest, ? extends QueryResponse>> searcherMap =
        searchers.stream().collect(Collectors.toMap(DocumentQuery::getName, Function.identity()));
    this.cachedSearchers.putAll(searcherMap);
  }
}
