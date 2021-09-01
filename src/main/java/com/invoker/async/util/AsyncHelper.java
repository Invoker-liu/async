package com.invoker.async.util;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.sleuth.instrument.async.TraceableExecutorService;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ForkJoinPool;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import static com.alibaba.ttl.threadpool.TtlExecutors.getTtlExecutorService;

/**
 * 2021/9/1-9:39 上午
 *
 * @author <a href="mailto:javajason@163.com">Jason(LiuJianJun)</a>
 */
@Component
@Slf4j
public class AsyncHelper implements InitializingBean {

    @Autowired
    private BeanFactory beanFactory;

    private static TraceableExecutorService TRACEABLE_EXECUTOR_SERVICE;


    @Override
    public void afterPropertiesSet() throws Exception {
        TRACEABLE_EXECUTOR_SERVICE = new TraceableExecutorService(beanFactory,
                                                                  ForkJoinPool.commonPool());

    }

    public static <T> List<T> asyncSupply(List<Supplier<T>> suppliers) {
        List<CompletableFuture<T>> futures =
                suppliers.stream()
                         .map(supplier -> CompletableFuture.supplyAsync(supplier, TRACEABLE_EXECUTOR_SERVICE))
                         .collect(Collectors.toList());
        CompletableFuture.allOf(futures.toArray(new CompletableFuture<?>[0])).join();
        return futures.stream().map(CompletableFuture::join).collect(Collectors.toList());
    }


}
