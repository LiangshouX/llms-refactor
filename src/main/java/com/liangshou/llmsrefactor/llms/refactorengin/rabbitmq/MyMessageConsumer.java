package com.liangshou.llmsrefactor.llms.refactorengin.rabbitmq;

import com.liangshou.llmsrefactor.llms.openai.GptCompletionService;
import com.rabbitmq.client.Channel;
import jakarta.annotation.Resource;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

/**
 * @author X-L-S
 */
@Component
public class MyMessageConsumer {

    @Resource
    private GptCompletionService gptCompletionService;

    private final Logger logger = LoggerFactory.getLogger(getClass());

    // 指定程序监听的消息队列和确认机制
    @SneakyThrows
    @RabbitListener(queues = {"code_queue"}, ackMode = "MANUAL")
    public void receiveMessage(String message, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long deliveryTag) {
        logger.info("receiveMessage message = {}", message);
        long cedeId = Long.parseLong(message);
        try {
            gptCompletionService.refactorCompletion(cedeId);
            channel.basicAck(deliveryTag, false);
            logger.info("Consumed message = {}", message);
        } catch (Exception e) {
            channel.basicNack(deliveryTag, false, false);
        }
    }
}
