package com.sakal.fulltext;

import com.sakal.fulltext.index.AsyncIndexResponse;
import com.sakal.fulltext.index.DocumentIndexer;
import com.sakal.fulltext.index.IndexContext;
import com.sakal.fulltext.index.IndexRequest;
import com.sakal.fulltext.index.IndexResponse;
import com.sakal.fulltext.index.MapIndexRequest;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

//@ExtendWith(MockitoExtension.class)
class FullTextEngineTest extends AbstractFullTextTest {
  private static final String MOCK_RESP_VAL_KEY = "mock-response-value";

  @Override
  protected List<DocumentIndexer<? extends IndexRequest, ? extends IndexResponse>>
      getDocumentIndexers() {
    return List.of(new TestDocIndex());
  }

  @Test
  void successIndex() throws ExecutionException, InterruptedException {

    MapIndexRequest request = new MapIndexRequest(TestDocIndex.TEST_IDX_KEY);
    String mockResponse = UUID.randomUUID().toString();
    request.put(MOCK_RESP_VAL_KEY, mockResponse);

    AsyncIndexResponse<IndexResponse> asyncResponse = getFullTextEngine().index(request);

    Assertions.assertNotNull(asyncResponse);

    IndexResponse indexResponse = asyncResponse.getCompletableFutureResponse().get();
    Assertions.assertEquals(mockResponse, indexResponse.getID());
  }

  @FullTextPlatform(PLATFORM_NAME)
  private static class TestDocIndex implements DocumentIndexer<MapIndexRequest, TestResponse> {
    static final String TEST_IDX_KEY = "";

    @Override
    public String getIndexKey() {
      return TEST_IDX_KEY;
    }

    @Override
    public TestResponse index(MapIndexRequest indexRequest, IndexContext context) {
      return new TestResponse(
          Objects.requireNonNullElse(indexRequest.get(MOCK_RESP_VAL_KEY), "").toString());
    }
  }

  @RequiredArgsConstructor
  private static class TestResponse implements IndexResponse {
    private final String refID;

    @Override
    public String getID() {
      return Objects.requireNonNullElse(refID, "");
    }
  }
}
