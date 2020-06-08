package com.lpp.dead;


import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class RabbitMQConfig {

  public static final String BUSINESS_EXCHANGE_NAME = "dead.letter.demo.simple.business.exchange";
  public static final String BUSINESS_QUEUEA_NAME = "dead.letter.demo.simple.business.queuea";
  public static final String BUSINESS_QUEUEB_NAME = "dead.letter.demo.simple.business.queueb";
  public static final String DEAD_LETTER_EXCHANGE = "dead.letter.demo.simple.deadletter.exchange";
  public static final String DEAD_LETTER_QUEUEA_ROUTING_KEY = "dead.letter.demo.simple.deadletter.queuea.routingkey";
  public static final String DEAD_LETTER_QUEUEB_ROUTING_KEY = "dead.letter.demo.simple.deadletter.queueb.routingkey";
  public static final String DEAD_LETTER_QUEUEA_NAME = "dead.letter.demo.simple.deadletter.queuea";
  public static final String DEAD_LETTER_QUEUEB_NAME = "dead.letter.demo.simple.deadletter.queueb";

  //业务Exchange
  @Bean("businessExchange")
  public FanoutExchange businessExchange() {

    return new FanoutExchange(BUSINESS_EXCHANGE_NAME);
  }


  //死信Exchange
  @Bean("deadLetterExchange")
  public DirectExchange deadLetterExchange() {

    return new DirectExchange(DEAD_LETTER_EXCHANGE);

  }


  @Bean("businessQueueA")
  public Queue businessQueueA() {

    Map<String, Object> args = new HashMap<>();
    //声明当前队列绑定死信交换寄
    args.put("x-dead-letter-exchange", DEAD_LETTER_EXCHANGE);
    //当前队列得路由key
    args.put("x-dead-letter-routing-key", DEAD_LETTER_QUEUEA_ROUTING_KEY);

    return QueueBuilder.durable(BUSINESS_QUEUEA_NAME).withArguments(args).build();
  }


  @Bean("businessQueueB")
  public Queue businessQueueB() {

    Map<String, Object> args = new HashMap<>();
    //声明当前队列绑定死信交换寄
    args.put("x-dead-letter-exchange", DEAD_LETTER_EXCHANGE);
    //当前队列得路由key
    args.put("x-dead-letter-routing-key", DEAD_LETTER_QUEUEB_ROUTING_KEY);

    return QueueBuilder.durable(BUSINESS_QUEUEB_NAME).withArguments(args).build();
  }


  //死信队列A
  @Bean("deadLetterQueueA")
  public Queue deadLetterQueueA(){
    return new Queue(DEAD_LETTER_QUEUEA_NAME);
  }


  //死信队列B
  @Bean("deadLetterQueueB")
  public Queue deadLetterQueueB(){
    return new Queue(DEAD_LETTER_QUEUEB_NAME);
  }

  //
  @Bean
  public Binding businessBindindA(@Qualifier("businessQueueA") Queue queue, @Qualifier("businessExchange") FanoutExchange exchange) {

    return BindingBuilder.bind(queue).to(exchange);
  }

  @Bean
  public Binding businessBindindB(@Qualifier("businessQueueB") Queue queue, @Qualifier("businessExchange") FanoutExchange exchange) {

    return BindingBuilder.bind(queue).to(exchange);
  }

  // 声明死信队列A绑定关系
  @Bean
  public Binding deadLetterBindingA(@Qualifier("deadLetterQueueA") Queue queue,
                                    @Qualifier("deadLetterExchange") DirectExchange exchange){
    return BindingBuilder.bind(queue).to(exchange).with(DEAD_LETTER_QUEUEA_ROUTING_KEY);
  }

  // 声明死信队列B绑定关系
  @Bean
  public Binding deadLetterBindingB(@Qualifier("deadLetterQueueB") Queue queue,
                                    @Qualifier("deadLetterExchange") DirectExchange exchange){
    return BindingBuilder.bind(queue).to(exchange).with(DEAD_LETTER_QUEUEB_ROUTING_KEY);
  }


}
