package org.builovn.storage.DI.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Аннотация, которой помечаются классы, которые будут внедрять зависимости.
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface Configuration {
    /**
     * @return список пакетов, в которых будут искаться зависимости.
     */
    String[] packages();
}
