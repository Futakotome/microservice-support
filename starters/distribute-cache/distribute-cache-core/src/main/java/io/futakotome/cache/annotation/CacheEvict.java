package io.futakotome.cache.annotation;

import java.lang.annotation.*;

@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface CacheEvict {

    String name() default "";

    String[] key() default "";

    boolean allEntries() default false;

    boolean beforeInvocation() default false;
}
