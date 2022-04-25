//package PutclubHelper.crawler;
//
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Component;
//
//import java.util.concurrent.locks.ReentrantLock;
//
//@Component
//public class testCrawler {
//
//    private final ReentrantLock lock = new ReentrantLock();
//    private boolean updated = true;
//    private static int cnt = 0;
//
//    @Scheduled(cron = "0/5 * * * * ?")
//    public void craw() {
//        System.out.println("craw");
//        lock.lock();
//        updated = false;
//        lock.unlock();
//    }
//
//    @Scheduled(cron = "0/1 * * * * ?")
//    public void checkUpdate() {
//        lock.lock();
//        if(!updated) {
//            System.out.println("checking update.....");
//            cnt++;
//            if(cnt > 2) {
//                updated = true;
//                cnt = 0;
//            }
//        }
//        lock.unlock();
//    }
//}
