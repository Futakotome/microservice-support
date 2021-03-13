package io.futakotome.cache.annotation;

import java.lang.annotation.*;

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface Cacheable {
    String name() default "";

    String[] key() default "";

    long expire() default -1L;
}
