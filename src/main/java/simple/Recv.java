package simple;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import org.json.JSONArray;
import org.json.JSONObject;

public class Recv {

    private final static String QUEUE_NAME = "hello";

    public static void main(String[] argv) throws Exception {
        
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("120.24.94.91");
        factory.setUsername("admin");
        factory.setPassword("ljb112233");
        factory.setVirtualHost("/dev");
        factory.setPort(5672);

        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

        Consumer consumer = new DefaultConsumer(channel) {

            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                // tag
                System.out.println("consumerTag=" + consumerTag);

                // exchange, and routing key
                System.out.println("envelope=" + envelope);

                System.out.println("properties=" + properties);
                System.out.println("body=" + new String(body, "utf-8"));
            }
        };

        // will continue consume without end
        channel.basicConsume(QUEUE_NAME, true, consumer);

        // auto consume
//        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
//            String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
//            System.out.println(" [x] Received '" + message + "'");
//        };
//
//        channel.basicConsume(QUEUE_NAME, true, deliverCallback, consumerTag -> { });
    }
}
