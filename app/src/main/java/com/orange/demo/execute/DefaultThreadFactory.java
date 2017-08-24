package com.orange.demo.execute;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Administrator on 2017/6/22 0022.
 */

public class DefaultThreadFactory implements ThreadFactory {
    /**
     * corePoolSize
     线程池中的核心线程数，当提交一个任务时，线程池创建一个新线程执行任务，直到当前线程数等于corePoolSize；
     如果当前线程数为corePoolSize，继续提交的任务被保存到阻塞队列中，等待被执行；
     如果执行了线程池的prestartAllCoreThreads()方法，线程池会提前创建并启动所有核心线程。
     maximumPoolSize
     线程池中允许的最大线程数。如果当前阻塞队列满了，且继续提交任务，则创建新的线程执行任务，前提是当前线程数小于maximumPoolSize
     keepAliveTime
     线程空闲时的存活时间，即当线程没有任务执行时，继续存活的时间。默认情况下，该参数只在线程数大于corePoolSize时才有用
     workQueue
     workQueue必须是BlockingQueue阻塞队列。当线程池中的线程数超过它的corePoolSize的时候，线程会进入阻塞队列进行阻塞等待。通过workQueue，线程池实现了阻塞功能

     几种排队的策略：
     （1）不排队，直接提交
     将任务直接交给线程处理而不保持它们，可使用SynchronousQueue
     如果不存在可用于立即运行任务的线程（即线程池中的线程都在工作），则试图把任务加入缓冲队列将会失败，因此会构造一个新的线程来处理新添加的任务，并将其加入到线程池中（corePoolSize-->maximumPoolSize扩容）
     Executors.newCachedThreadPool()采用的便是这种策略
     （2）无界队列
     可以使用LinkedBlockingQueue（基于链表的有界队列，FIFO），理论上是该队列可以对无限多的任务排队
     将导致在所有corePoolSize线程都工作的情况下将新任务加入到队列中。这样，创建的线程就不会超过corePoolSize，也因此，maximumPoolSize的值也就无效了
     （3）有界队列
     可以使用ArrayBlockingQueue（基于数组结构的有界队列，FIFO），并指定队列的最大长度
     使用有界队列可以防止资源耗尽，但也会造成超过队列大小和maximumPoolSize后，提交的任务被拒绝的问题，比较难调整和控制。

     RejectedExecutionHandler（饱和策略）
     线程池的饱和策略，当阻塞队列满了，且没有空闲的工作线程，如果继续提交任务，必须采取一种策略处理该任务，线程池提供了4种策略：
     （1）AbortPolicy：直接抛出异常，默认策略；
     （2）CallerRunsPolicy：用调用者所在的线程来执行任务；
     （3）DiscardOldestPolicy：丢弃阻塞队列中靠最前的任务，并执行当前任务；
     （4）DiscardPolicy：直接丢弃任务；
     当然也可以根据应用场景实现RejectedExecutionHandler接口，自定义饱和策略，如记录日志或持久化存储不能处理的任务。


     2、ThreadPoolExecutor线程池执行流程
     根据ThreadPoolExecutor源码前面大段的注释，我们可以看出，当试图通过execute方法将一个Runnable任务添加到线程池中时，按照如下顺序来处理：
     （1）如果线程池中的线程数量少于corePoolSize，就创建新的线程来执行新添加的任务；
     （2）如果线程池中的线程数量大于等于corePoolSize，但队列workQueue未满，则将新添加的任务放到workQueue中，按照FIFO的原则依次等待执行（线程池中有线程空闲出来后依次将队列中的任务交付给空闲的线程执行）；
     （3）如果线程池中的线程数量大于等于corePoolSize，且队列workQueue已满，但线程池中的线程数量小于maximumPoolSize，则会创建新的线程来处理被添加的任务；
     （4）如果线程池中的线程数量等于了maximumPoolSize，就用RejectedExecutionHandler来做拒绝处理
     总结，当有新的任务要处理时，先看线程池中的线程数量是否大于corePoolSize，再看缓冲队列workQueue是否满，最后看线程池中的线程数量是否大于maximumPoolSize
     另外，当线程池中的线程数量大于corePoolSize时，如果里面有线程的空闲时间超过了keepAliveTime，就将其移除线程池
     */
    private static final AtomicInteger poolNumber = new AtomicInteger(1);
    private final ThreadGroup group;
    private final AtomicInteger threadNumber = new AtomicInteger(1);
    private final String namePrefix;

    private DefaultThreadFactory() {
        SecurityManager s = System.getSecurityManager();
        group = (s != null) ? s.getThreadGroup() : Thread.currentThread().getThreadGroup();
        namePrefix = "pool-" + poolNumber.getAndIncrement() + "-thread-";
    }

    public Thread newThread(Runnable r) {
        Thread t = new Thread(group, r, namePrefix + threadNumber.getAndIncrement(), 0);
        if (t.isDaemon()) t.setDaemon(false);
        if (t.getPriority() != Thread.NORM_PRIORITY) t.setPriority(Thread.NORM_PRIORITY);
        return t;
    }
}