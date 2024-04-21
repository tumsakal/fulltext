package com.sakal.fulltext;

public class FullTextOperationException extends RuntimeException {
  public FullTextOperationException(String message) {
    super(message);
  }

  public FullTextOperationException(String message, Throwable cause) {
    super(message, cause);
  }
}
