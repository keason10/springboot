package com.wykj.springboot.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
@RequestMapping("/sync")
@RestController
public class MySyncController {
//    ==============================================================DeferredResult ===================================================
    //用队列接收很多请求 好处就是请求不会长时间占用http 服务连接池，提高服务器的吞吐量。
    private ConcurrentLinkedDeque<DeferredResult<String>> deferredResultsQueue = new ConcurrentLinkedDeque<DeferredResult<String>>();
    @GetMapping("/deferredResult")
    public DeferredResult<String> getDeferredResult() throws Exception{
        //设置 5秒就会超时
        final DeferredResult<String> stringDeferredResult = new DeferredResult<String>(5000L);
        log.info("deferredResult enter");
        //将请求加入到队列中
        deferredResultsQueue.add(stringDeferredResult);
        final String message = "{username:keason}";
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        executorService.submit(new Runnable() {
            @Override
            public void run() {

                try {
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                //业务处理
                log.info("deferredResult 业务处理 ");
                stringDeferredResult.setResult(message);
            }
        });


        //setResult完毕之后，调用该方法
        stringDeferredResult.onCompletion(new Runnable() {
            @Override
            public void run() {
                log.info("deferredResult 异步调用完成 ");
                //响应完毕之后，将请求从队列中去除掉
                deferredResultsQueue.remove(stringDeferredResult);
            }
        });

        stringDeferredResult.onTimeout(new Runnable() {
            @Override
            public void run() {
                log.info("deferredResult 业务处理超时 ");
                stringDeferredResult.setResult("error:timeOut");
            }
        });

        return stringDeferredResult;
    }

    //开启线程定时扫描队列，响应客户端  需要@EnableScheduling
    @Scheduled(fixedRate = 4000)
    public void scheduleResult(){
        System.out.println(new Date());
        for(int i = 0;i < deferredResultsQueue.size();i++){
            log.info("com.wykj.springboot.controller.MySyncController.scheduleResult deferredResultsQueue[{}]", i);
            DeferredResult<String> deferredResult = deferredResultsQueue.getFirst();
            deferredResult.setResult("result:" + i);
        }
    }



    //    ==============================================================Callable  ===================================================
    @PostMapping("/callable")
    public Callable<Map> getStr(){
            log.info("com.wykj.springboot.controller.MySyncController.getStr enter");
            return () -> {
                log.info("com.wykj.springboot.controller.MySyncController.getStr enter callable");
                Thread.sleep(4000);
                Map map = new HashMap<>();
                map.put("msg", "hello keason callable");
                return map;
            };
    }

    @PostMapping("/nocallable")
    public Map getStrNo(){
        try {
            log.info("com.wykj.springboot.controller.MySyncController.getStr enter callable");
            Thread.sleep(4000);
            Map map = new HashMap<>();
            map.put("msg", "hello keason callable");
            return map;
        } catch (InterruptedException e) {
            e.printStackTrace();
            return new HashMap();
        }
    }

}
