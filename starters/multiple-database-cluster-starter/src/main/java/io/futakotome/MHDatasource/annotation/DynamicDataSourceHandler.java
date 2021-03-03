package io.futakotome.MHDatasource.annotation;

import io.futakotome.MHDatasource.config.DynamicDatasourceContextHolder;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Slf4j
@Aspect
@Component
public class DynamicDataSourceHandler {
    @Pointcut(value = "@annotation(io.futakotome.MHDatasource.annotation.DynamicDatasourceSwitch)")
    public void pointcut() {
    }

    @Before("pointcut()")
    public void doBefore(JoinPoint joinPoint) {
        Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
        DynamicDatasourceSwitch annotationClz = method.getAnnotation(DynamicDatasourceSwitch.class);
        if (annotationClz == null) {
            return;
        }
        //todo 可以加有效性判断
        String datasourceKey = annotationClz.dataSourceKey();

        DynamicDatasourceContextHolder.setDatasource(datasourceKey);

        log.info("[{}]({})切换数据源---->{}", joinPoint.getTarget().getClass().getName(), method.getName(), datasourceKey);

    }

    @After("pointcut()")
    public void doAfter(JoinPoint joinPoint) {
        DynamicDatasourceContextHolder.clearDatasourceKey();
    }
}
