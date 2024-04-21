package com.sakal.fulltext.support;

import com.sakal.fulltext.FullTextPlatform;
import com.sakal.fulltext.util.AnnotationUtils;
import java.util.List;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Slf4j
public class FullTextPlatformHelper {

  public static <T> List<T> getFilteredFullTextPlatformObjects(
      String targetPlatform, List<? extends T> targets) {

    if (StringUtils.isEmpty(targetPlatform)) {
      throw new NullPointerException("The target platform must not be null");
    }

    return targets.stream()
        .filter(
            targetObject -> {
              String platformName = getPlatformName(targetObject);

              if (StringUtils.isEmpty(targetPlatform)) {
                log.warn(
                    "The target object type is not annotated with @FullTextPlatform: {}",
                    targetObject.getClass().getName());
              }

              return targetPlatform.equalsIgnoreCase(platformName);
            })
        .map(target -> (T) target)
        .toList();
  }

  private static String getPlatformName(Object target) {
    FullTextPlatform declaredAnnotation =
        AnnotationUtils.getAnnotation(target, FullTextPlatform.class);

    if (declaredAnnotation != null) {
      return declaredAnnotation.value();
    }

    return null;
  }
}
