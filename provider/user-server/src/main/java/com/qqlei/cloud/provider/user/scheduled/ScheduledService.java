//package com.qqlei.cloud.provider.user.scheduled;
//
//import com.qqlei.cloud.provider.user.fegin.BookFegin;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Component;
//
//import java.text.SimpleDateFormat;
//import java.util.Date;
//
//@Component
//public class ScheduledService {
//
//    @Autowired
//    private BookFegin bookFegin;
//
//    @Scheduled(cron = "0/10 * * * * ?")
//    public void timerToNow(){
//        String result = bookFegin.helloService(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
//        System.out.println("===============>book return message="+result);
//    }
//
//}