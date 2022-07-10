package topic;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.nio.charset.StandardCharsets;

public class Send {

    private final static String EXCHANGE_NAME = "exchange_topic";

    public static void main(String[] argv) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("120.24.94.91");
        factory.setUsername("admin");
        factory.setPassword("ljb112233");
        factory.setVirtualHost("/dev");
        factory.setPort(5672);

        try (Connection connection = factory.newConnection();

             // create channel
             Channel channel = connection.createChannel()) {

            // bind with exchange direct
            channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.TOPIC);

            String error = "I am order error";
            String info = "I am order info";
            String debug = "I am order debug from product";

            channel.basicPublish(EXCHANGE_NAME, "order.log.error", null, error.getBytes(StandardCharsets.UTF_8));
            channel.basicPublish(EXCHANGE_NAME, "order.log.info", null, info.getBytes(StandardCharsets.UTF_8));
            channel.basicPublish(EXCHANGE_NAME, "product.log.debug", null, debug.getBytes(StandardCharsets.UTF_8));

            System.out.println("topic send success");
        }
    }
}
