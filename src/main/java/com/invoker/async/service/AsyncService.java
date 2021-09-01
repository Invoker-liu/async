package com.invoker.async.service;

import brave.Tracer;
import cn.hutool.core.collection.CollUtil;
import com.invoker.async.util.AsyncHelper;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.locks.LockSupport;

/**
 * 2021/9/1-4:00 下午
 *
 * @author <a href="mailto:javajason@163.com">Jason(LiuJianJun)</a>
 */
@Service
@Slf4j
public class AsyncService {

    @Autowired
    private Tracer tracer;

    public void asyncWithTraceAndThreadLocal() {
        log.info(tracer.currentSpan().context().traceIdString());
        log.info(UserHolder.get());
        List<String> list =
                AsyncHelper.asyncSupply(CollUtil.toList(() -> {
                    log.info(UserHolder.get());
                    LockSupport.parkNanos(1000000);
                    return "One";
                }));
        log.info("列表【{}】", list);
    }

}
