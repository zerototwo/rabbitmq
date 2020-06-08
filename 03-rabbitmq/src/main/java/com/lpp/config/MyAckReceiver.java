package com.lpp.config;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.listener.api.ChannelAwareMessageListener;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
public class MyAckReceiver implements ChannelAwareMessageListener {

  @Override
  public void onMessage(Message message, Channel channel) throws Exception {
    long deliveryTag = message.getMessageProperties().getDeliveryTag();

    try {
      String msg = message.toString();
      String[] msgArray = msg.split(",");
      Map<String, String> msgMap = mapStringToMap(msgArray[1].trim());
      String messageId=msgMap.get("messageId");
      String messageData=msgMap.get("messageData");
      String createTime=msgMap.get("createTime");
      System.out.println("  MyAckReceiver  messageId:"+messageId+"  messageData:"+messageData+"  createTime:"+createTime);
      System.out.println("消费的主题消息来自："+message.getMessageProperties().getConsumerQueue());
      channel.basicAck(deliveryTag, true);
//			channel.basicReject(deliveryTag, true);//为true会重新放回队列
    } catch (IOException e) {
      channel.basicReject(deliveryTag, false);
      e.printStackTrace();
    }


  }


  private Map<String, String> mapStringToMap(String str) {

    str = str.substring(1, str.length() - 1);
    String[] strs = str.split(",");

    Map<String, String> map = new HashMap<>();
    for (String string : strs) {
      String key = string.split("=")[0].trim();
      String value = string.split("=")[1];
      map.put(key, value);
    }
    return map;


  }


}
