package com.invoker.async.web;

import com.invoker.async.service.AsyncService;
import com.invoker.async.service.UserHolder;
import com.invoker.async.util.DecryptUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 2021/9/1-4:18 下午
 *
 * @author <a href="mailto:javajason@163.com">Jason(LiuJianJun)</a>
 */
@RestController
public class AsyncController {

    @Autowired
    private AsyncService asyncService;

    @GetMapping("async")

    public void async() {
        UserHolder.set("Invoker");
        DecryptUtil.fileDe();
        this.asyncService.asyncWithTraceAndThreadLocal();
    }

}
