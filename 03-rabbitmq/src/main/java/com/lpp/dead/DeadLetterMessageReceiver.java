package com.lpp.dead;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class DeadLetterMessageReceiver {

  @RabbitListener(queues = "dead.letter.demo.simple.deadletter.queuea")
  public void receiveA(Message message, Channel channel) throws IOException {
    System.out.println("收到死信消息A：" + new String(message.getBody()));
    channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);

  }


  @RabbitListener(queues = "dead.letter.demo.simple.deadletter.queueb")
  public void receiveB(Message message, Channel channel) throws IOException {

    System.out.println("收到死信消息B：" + new String(message.getBody()));
    channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
  }


}
