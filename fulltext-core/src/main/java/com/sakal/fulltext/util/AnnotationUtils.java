package com.sakal.fulltext.util;

import java.lang.annotation.Annotation;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * @author Sakal TUM sakal.tum@allweb.com.kh
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AnnotationUtils {

  /**
   * Finds an annotation on the target object, directly or indirectly on the parent class of target
   * object class.
   *
   * @param target the target object to find the annotation
   * @param annotationType annotation's type
   * @return annotation, or else null.
   */
  public static <A extends Annotation> A getAnnotation(Object target, Class<A> annotationType) {
    return getAnnotation(target.getClass(), annotationType);
  }

  /**
   * Finds an annotation on the target class, directly or indirectly on the parent class.
   *
   * @param targetClass the target class to find the annotation
   * @param annotationType annotation's type
   * @return annotation, or else null
   */
  public static <A extends Annotation> A getAnnotation(
      Class<?> targetClass, Class<A> annotationType) {
    A directDeclaredAnnotation = targetClass.getDeclaredAnnotation(annotationType);

    if (directDeclaredAnnotation != null) {
      return directDeclaredAnnotation;
    }

    Class<?> superclass = targetClass.getSuperclass();
    if (superclass == null) {
      return null;
    }

    return getAnnotation(superclass, annotationType);
  }
}
