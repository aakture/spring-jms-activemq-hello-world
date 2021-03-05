package com.codenotfound.jms;

import lombok.extern.slf4j.Slf4j;
import org.apache.activemq.command.ActiveMQObjectMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Component
@Slf4j
public class Sender {

    private volatile int count = 0;
    @Autowired
    private JmsTemplate jmsTemplate;
    @Autowired
    private TopicConfig topicConfig;

    public void send(String message) {
        try {
            log.info("sending message='{}' on topic {}", message, topicConfig.topicProducerName);

            jmsTemplate.send(topicConfig.getTopicProducerName(), session -> {
                ActiveMQObjectMessage msg = new ActiveMQObjectMessage();
                msg.setObject(message);
                return msg;
            });
        } catch (Exception ex) {
            log.error("error", ex);
        }
    }

    @PostConstruct
    public void start() {
        log.info("start()");
        ScheduledExecutorService ses = Executors.newScheduledThreadPool(1);

        Runnable task2 = () -> send("hello " + count++);

        //run this task after 5 seconds, nonblock for task3
        ses.scheduleAtFixedRate(task2, 5, 5, TimeUnit.SECONDS);
    }
}
