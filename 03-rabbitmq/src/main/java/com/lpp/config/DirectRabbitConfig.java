package com.lpp.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DirectRabbitConfig {

  /**
   * 队列名
   * @return
   */
  @Bean
  public Queue TestDirectQueue() {

    return new Queue("TestDirectQueue", true);

  }


  /**
   * 交换机
   * @return
   */
  @Bean
  DirectExchange TestDirectExchange() {

    return new DirectExchange("TestDirectExchange", true, false);

  }

  /**
   * binding
   * @return
   */
  @Bean
  Binding bindingDirect(){

    return BindingBuilder.bind(TestDirectQueue()).to(TestDirectExchange()).with("TestDirectRouting");
  }


  @Bean
  DirectExchange lonelyDirectExchange() {

    return new DirectExchange("lonelyDirectExchange");

  }
}
