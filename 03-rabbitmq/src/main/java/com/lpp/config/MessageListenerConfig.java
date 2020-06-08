package com.lpp.config;

import org.omg.CORBA.PUBLIC_MEMBER;
import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MessageListenerConfig {

  @Autowired
  private CachingConnectionFactory connectionFactory;
  @Autowired
  private MyAckReceiver myAckReceiver;

  @Bean
  public SimpleMessageListenerContainer simpleMessageListenerContainer() {
    SimpleMessageListenerContainer container = new SimpleMessageListenerContainer(connectionFactory);

    container.setConcurrentConsumers(1);
    container.setMaxConcurrentConsumers(1);
    container.setAcknowledgeMode(AcknowledgeMode.MANUAL);
    container.setQueueNames("TestDirectQueue");

    container.setMessageListener(myAckReceiver);

    return container;

  }


}
