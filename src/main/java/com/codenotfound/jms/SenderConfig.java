package com.codenotfound.jms;

import lombok.extern.slf4j.Slf4j;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.jms.pool.PooledConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerContainerFactory;
import org.springframework.jms.connection.CachingConnectionFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.MessageType;

@Configuration
@Slf4j
public class SenderConfig {

  @Value("${activemq.broker-url}")
  private String brokerUrl;
  @Value("${activemq.username}")
  private String brokerUsername;
  @Value("${activemq.password}")
  private String brokerPassword;

  /**
   * AMQ Connection Factory
   */
  @Bean
  public ActiveMQConnectionFactory activeMQConnectionFactory() {
    log.debug("ActiveMQConnectionFactory.activeMQConnectionFactory, using brokerUrl: {}", brokerUrl);
    // Create a connection factory.
    final ActiveMQConnectionFactory connectionFactory =
        new ActiveMQConnectionFactory(brokerUrl);
    // Pass the username and password.
    connectionFactory.setUserName(brokerUsername);
    connectionFactory.setPassword(brokerPassword);

    return connectionFactory;
  }

  @Bean
  public PooledConnectionFactory pooledConnectionFactory(ActiveMQConnectionFactory activeMQConnectionFactory) {
    PooledConnectionFactory pooledConnectionFactory = new PooledConnectionFactory();
    pooledConnectionFactory.setConnectionFactory(activeMQConnectionFactory);
    return pooledConnectionFactory;
  }

  @Bean
  public JmsListenerContainerFactory<?> queueListenerFactory(MessageConverter messageConverter) {
    DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
    factory.setMessageConverter(messageConverter);
    return factory;
  }


  @Bean
  public MessageConverter messageConverter() {
    MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
    converter.setTargetType(MessageType.TEXT);
    converter.setTypeIdPropertyName("_type");
    return converter;
  }

  @Bean
  public CachingConnectionFactory cachingConnectionFactory(ActiveMQConnectionFactory activeMQConnectionFactory) {
    return new CachingConnectionFactory(
        activeMQConnectionFactory);
  }

  @Bean
  public JmsTemplate jmsTemplate(CachingConnectionFactory cachingConnectionFactory) {
    JmsTemplate template = new JmsTemplate();
    template.setConnectionFactory(cachingConnectionFactory);
    template.setPubSubDomain(true);
    return template;
  }

  @Bean
  public DefaultJmsListenerContainerFactory jmsListenerContainerFactory(ActiveMQConnectionFactory activeMQConnectionFactory) {
    DefaultJmsListenerContainerFactory factory =
        new DefaultJmsListenerContainerFactory();
    factory.setConnectionFactory(activeMQConnectionFactory);
    return factory;
  }

}
