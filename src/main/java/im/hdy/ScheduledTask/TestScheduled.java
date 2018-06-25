package im.hdy.ScheduledTask;

import org.springframework.stereotype.Component;

@Component
public class TestScheduled {
    @org.springframework.scheduling.annotation.Scheduled(cron = "0 20 20 ? * *")
    public void test() {
        System.err.println("这是测试内容");
        System.err.println("这是测试内容");
        System.err.println("这是测试内容");
        System.err.println("这是测试内容");
        System.err.println("这是测试内容");
        System.err.println("这是测试内容");
        System.err.println("这是测试内容");
    }
}
