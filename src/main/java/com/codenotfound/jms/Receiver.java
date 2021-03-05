package com.codenotfound.jms;

import lombok.extern.slf4j.Slf4j;
import org.apache.activemq.command.ActiveMQObjectMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;

@Slf4j
@Component
public class Receiver {

  private TopicConfig topicConfig;

  public Receiver(TopicConfig topicConfig) {
    this.topicConfig = topicConfig;
    log.info("receive messages on {}", topicConfig.getTopicConsumerName());
  }

  @JmsListener(destination = "#{@topicConfig.getTopicConsumerName()}")
  public void receive(ActiveMQObjectMessage message) {
    try {
      log.info("received message='{}'", message.getObject());
    } catch (JMSException e) {
      log.error("error", e);
    }
  }
}
