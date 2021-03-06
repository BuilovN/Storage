package org.builovn.storage.DI.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Аннотация, которой помечают поля, для которых необходимо будет внедрить зависимость.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Autowired {
    /**
     * Поле, отвечающее за тип зависимости, которую надо будет внедрить. Необходимо, когда зависимость имеет класс List,
     * из-за стирания типов.
     * @return Поле, отвечающее за тип зависимости.
     */
    Class clazz() default Object.class;
}
