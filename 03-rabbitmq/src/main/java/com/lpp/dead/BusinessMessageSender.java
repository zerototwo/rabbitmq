package com.lpp.dead;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BusinessMessageSender {

  @Autowired
  private RabbitTemplate rabbitTemplate;

  public void sendMsg(String msg) {

    rabbitTemplate.convertSendAndReceive("dead.letter.demo.simple.business.exchange", "",msg);
  }


}
