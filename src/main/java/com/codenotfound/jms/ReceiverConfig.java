package com.codenotfound.jms;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;

@Configuration
@EnableJms
public class ReceiverConfig {
//
//  @Value("${activemq.broker-url}")
//  private String brokerUrl;
//
//  @Bean
//  public ActiveMQConnectionFactory receiverActiveMQConnectionFactory() {
//    ActiveMQConnectionFactory activeMQConnectionFactory =
//        new ActiveMQConnectionFactory();
//    activeMQConnectionFactory.setBrokerURL(brokerUrl);
//
//    return activeMQConnectionFactory;
//  }
//
//  @Bean
//  public DefaultJmsListenerContainerFactory jmsListenerContainerFactory() {
//    DefaultJmsListenerContainerFactory factory =
//        new DefaultJmsListenerContainerFactory();
//    factory
//        .setConnectionFactory(receiverActiveMQConnectionFactory());
//
//    return factory;
//  }

  @Bean
  public TopicConfig topicConfig(@Value("${topic.consumer.name}") String topicConsumerName, @Value("${topic.producer.name}") String topicProducerName) {
    return new TopicConfig(topicConsumerName, topicProducerName);
  }

}
