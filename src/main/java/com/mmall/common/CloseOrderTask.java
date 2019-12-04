package com.mmall.common;

import com.mmall.service.IOrderService;
import com.mmall.util.PropertiesUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

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

    @Scheduled(cron = "0 */1 * * * ?")
    public void closeOrder() {
        int hour = Integer.parseInt(PropertiesUtil.getProperty("close.order.task.time", "2"));
        iOrderService.closeOrder(hour);
    }
}
