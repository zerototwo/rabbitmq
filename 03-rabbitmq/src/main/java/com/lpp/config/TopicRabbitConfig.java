package com.lpp.config;

import org.omg.CORBA.PUBLIC_MEMBER;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TopicRabbitConfig {

  //绑定键
  public static final String man = "topic.man";
  public static final String woman = "topic.women";

  @Bean
  public Queue firstQueue() {

    return new Queue(man);
  }


  @Bean
  public Queue secondQueue() {
    return new Queue(woman);

  }


  @Bean
  TopicExchange exchange() {
    return new TopicExchange("topicExchange");

  }


  @Bean
  Binding bindingExchangeMessage() {

    return BindingBuilder.bind(firstQueue()).to(exchange()).with(man);
  }


  @Bean
  Binding bindingExchangeMessage2(){

    return BindingBuilder.bind(secondQueue()).to(exchange()).with(woman);
  }


}
