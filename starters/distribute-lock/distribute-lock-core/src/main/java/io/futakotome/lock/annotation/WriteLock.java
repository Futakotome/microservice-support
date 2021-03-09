package io.futakotome.lock.annotation;

import java.lang.annotation.*;

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface WriteLock {

    String name() default "";

    String key() default "";
}
