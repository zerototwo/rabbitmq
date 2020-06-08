package com.lpp.direct;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RabbitListener(queues = "TestDirectQueue")
public class DirectReceiverNew {

  @RabbitHandler
  public void process(Map testMessage) {

    System.out.println("第二台：DirectReceiver消费者收到消息  : " + testMessage.toString());
  }
}
