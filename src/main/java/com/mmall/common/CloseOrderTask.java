package com.mmall.common;

import com.mmall.service.IOrderService;
import com.mmall.util.PropertiesUtil;
import com.mmall.util.RedisShardedPoolUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.redisson.api.RLock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * @program: mmall
 * @author: yanrui
 * @description
 * @create: 2019-12-03 15:36
 */
@Component
@Slf4j
public class CloseOrderTask {

    @Autowired
    private IOrderService iOrderService;

    @Autowired
    private RedissonManager redissonManager;

//    @Scheduled(cron = "0 */1 * * * ?")
    public void closeOrder() {
        int hour = Integer.parseInt(PropertiesUtil.getProperty("close.order.task.time", "2"));
        iOrderService.closeOrder(hour);
    }

    @Scheduled(cron = "0 */1 * * * ?")
    public void closeOrder2() {
        long timeout = Long.parseLong(PropertiesUtil.getProperty("lock.timeout"));
        Long result = RedisShardedPoolUtil.setnx(Const.REDIS_LOCK.CLOSE_ORDER_TASK_LOCK, String.valueOf(System.currentTimeMillis() + timeout));
        if (result != null && result.intValue() == 1) {
            //如果返回值为1代表设置成功
            closeOrder(Const.REDIS_LOCK.CLOSE_ORDER_TASK_LOCK);
        } else {
            //未获取锁，判断时间戳是否可以重置并获取
            String lockValue = RedisShardedPoolUtil.get(Const.REDIS_LOCK.CLOSE_ORDER_TASK_LOCK);
            if (lockValue != null && System.currentTimeMillis() > Long.parseLong(lockValue)) {
                String getsetResult = RedisShardedPoolUtil.getset(Const.REDIS_LOCK.CLOSE_ORDER_TASK_LOCK, String.valueOf(System.currentTimeMillis() + timeout));
                if (getsetResult == null || (getsetResult != null && StringUtils.equals(lockValue, getsetResult))) {
                    closeOrder(Const.REDIS_LOCK.CLOSE_ORDER_TASK_LOCK);
                } else {
                    log.info("没有获得分布式锁：{}", Const.REDIS_LOCK.CLOSE_ORDER_TASK_LOCK);
                }
            }

            log.info("没有获得分布式锁：{}", Const.REDIS_LOCK.CLOSE_ORDER_TASK_LOCK);
        }
    }


    private void closeOrder(String lockName) {
        RedisShardedPoolUtil.expire(lockName, 5);
        log.info("获取{},ThreadName:{}", Const.REDIS_LOCK.CLOSE_ORDER_TASK_LOCK, Thread.currentThread());
        int hour = Integer.parseInt(PropertiesUtil.getProperty("close.order.task.time", "2"));
        iOrderService.closeOrder(hour);
        RedisShardedPoolUtil.del(Const.REDIS_LOCK.CLOSE_ORDER_TASK_LOCK);
        log.info("释放{},ThreadName:{}", Const.REDIS_LOCK.CLOSE_ORDER_TASK_LOCK, Thread.currentThread());
    }

    @Scheduled(cron = "0 */1 * * * ?")
    public void closeOrderTaskv4() {
        RLock lock = redissonManager.getRedisson().getLock(Const.REDIS_LOCK.CLOSE_ORDER_TASK_LOCK);
        boolean getLock = false;
        try {
            if (lock.tryLock(0, 5, TimeUnit.SECONDS)) {
                log.info("Redisson获取分布式锁:{},ThreadName:{}", Const.REDIS_LOCK.CLOSE_ORDER_TASK_LOCK, Thread.currentThread());
                int hour = Integer.parseInt(PropertiesUtil.getProperty("close.order.task.time", "2"));
                iOrderService.closeOrder(hour);
            } else {
                log.info("获取{},ThreadName:{}", Const.REDIS_LOCK.CLOSE_ORDER_TASK_LOCK, Thread.currentThread());
            }
        } catch (InterruptedException e) {
            log.error("获取锁异常", e);
        }finally {
            if (!getLock) {
                return;
            }
            lock.unlock();
        }
    }
}
