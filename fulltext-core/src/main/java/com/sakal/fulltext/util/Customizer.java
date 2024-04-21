package com.sakal.fulltext.util;

public interface Customizer<T> {
  static <T> Customizer<T> withDefault() {
    return t -> {};
  }

  void customize(T t);
}
