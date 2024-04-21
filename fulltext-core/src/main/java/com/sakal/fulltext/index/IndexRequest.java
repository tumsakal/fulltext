package com.sakal.fulltext.index;

import java.io.Serializable;

public interface IndexRequest extends Serializable {
  String getIndexKey();
}
