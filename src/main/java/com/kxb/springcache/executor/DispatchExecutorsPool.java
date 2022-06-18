package com.kxb.springcache.executor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Service("dispatchExecutorsPool")
public class DispatchExecutorsPool implements DisposableBean, Executor {
    private static final Logger logger = LoggerFactory.getLogger(DispatchExecutorsPool.class);
    //初始化线程池
    private ThreadPoolExecutor threadPool = null;

    @Value("${executor_core_size:5}")
    private String executor_core_size;
    @Value("${executor_max_size:500}")
    private String executor_max_size;
    @Value("${executor_alive_time:300000}")
    private String executor_alive_time;

    /**
     * 线程池集合
     */
    List<ThreadPoolExecutor> executorList = new ArrayList<>();

    @PostConstruct
    public void init() {
        threadPool = generateExecutor(executor_core_size, executor_max_size, executor_alive_time);
    }

    private ThreadPoolExecutor generateExecutor(String coolSize, String maxSize, String keepAliveTime) {
        int configPoolSize = Integer.parseInt(coolSize);
        int configMaxSize = Integer.parseInt(maxSize);
        int configKeepAliveTime = Integer.parseInt(keepAliveTime);
        ThreadPoolExecutor executor = new ThreadPoolExecutor(configPoolSize, configMaxSize, configKeepAliveTime,
                TimeUnit.MILLISECONDS,
                new ArrayBlockingQueue<>(1000), new ThreadPoolExecutor.CallerRunsPolicy());
        executorList.add(executor);
        return executor;
    }


    @Override
    public void destroy() throws Exception {
        for (ThreadPoolExecutor threadPoolExecutor : executorList) {
            threadPoolExecutor.shutdown();
            logger.error("线程池销毁");
        }
    }

    public ThreadPoolExecutor getOrderThreadPool() {
        return threadPool;
    }

    @Override
    public void execute(Runnable command) {
        threadPool.execute(command);
    }
}