package com.xu.atchat.config.thread;

import cn.hutool.core.thread.ThreadFactoryBuilder;

import java.util.concurrent.*;

/**
 * @author xucl
 * @version 1.0
 * @date 2020/8/15 16:54
 * @description 自定义线程池
 *
 * corePoolSize    int 	核心线程池大小(线程池的基本大小，即在没有任务需要执行的时候线程池的大小，并且只有在工作队列满了的情况下才会创建超出这个数量的线程)
 * maximumPoolSize 	int 	最大线程池大小(线程池中允许的最大线程数，线程池中的当前线程数目不会超过该值。如果队列中任务已满，并且当前线程个数小于maximumPoolSize，那么会创建新的线程来执行任务。)
 * keepAliveTime 	long 	最大线程池空闲时间(当线程空闲时间达到keepAliveTime时，线程会退出，直到线程数量=corePoolSize)
 * unit 	TimeUnit 	时间单位
 * workQueue 	BlockingQueue<Runnable> 	线程等待队列，如ArrayBlockingQueue，有界队列，构造函数需要传入队列最大值。
 * threadFactory 	ThreadFactory 	线程创建工厂
 * handler 	RejectedExecutionHandler 	拒绝策略
 *        * 两种情况会拒绝处理任务：
 *             - 当线程数已经达到maxPoolSize，切队列已满，会拒绝新任务
 *             - 当线程池被调用shutdown()后，会等待线程池里的任务执行完毕，再shutdown。如果在调用shutdown()和线程池真正shutdown之间提交任务，会拒绝新任务
 *         * 线程池会调用rejectedExecutionHandler来处理这个任务。如果没有设置默认是AbortPolicy，会抛出异常
 *         * ThreadPoolExecutor类有几个内部实现类来处理这类情况：
 *             - AbortPolicy 丢弃任务，抛运行时异常
 *             - CallerRunsPolicy 执行任务
 *             - DiscardPolicy 忽视，什么都不会发生
 *             - DiscardOldestPolicy 从队列中踢出最先进入队列（最后一个执行）的任务
 *         * 实现RejectedExecutionHandler接口，可自定义处理器
 *
 */
public class CommonThreadPool {

    /**
     * 最大可用的CPU核数
     */
    public static final int PROCESSORS=Runtime.getRuntime().availableProcessors();
    /**
     * 线程最大的空闲存活时间，单位为秒
     */
    public static final int KEEPALIVETIME=60;
    /**
     * 任务缓存队列长度
     */
    public static final int BLOCKINGQUEUE_LENGTH=500;

    public static ThreadFactory namedThreadFactory = new ThreadFactoryBuilder().setNamePrefix("AT-ctp-pool-").build();


    public static class MyRejectedExecutionHandler implements RejectedExecutionHandler {

        @Override
        public void rejectedExecution(Runnable r, ThreadPoolExecutor e) {
            doLog(r, e);
        }

        private void doLog(Runnable r, ThreadPoolExecutor e) {
            // 可做日志记录等
            System.err.println( r.toString() + " rejected");
            // System.out.println("completedTaskCount: " + e.getCompletedTaskCount());
        }
    }

    /**
     * 自定义线程池
     */
    public static ExecutorService executors = new ThreadPoolExecutor(PROCESSORS * 2,
            PROCESSORS * 4,KEEPALIVETIME, TimeUnit.SECONDS,new ArrayBlockingQueue<Runnable>(BLOCKINGQUEUE_LENGTH),
            namedThreadFactory,new MyRejectedExecutionHandler());



}
