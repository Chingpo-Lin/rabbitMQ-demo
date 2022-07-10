package pub;

import com.rabbitmq.client.*;

import java.io.IOException;

public class Recv2 {

    private final static String EXCHANGE_NAME = "exchange_fanout";

    public static void main(String[] argv) throws Exception {

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("120.24.94.91");
        factory.setUsername("admin");
        factory.setPassword("ljb112233");
        factory.setVirtualHost("/dev");
        factory.setPort(5672);

        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        // bind fanout
        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.FANOUT);

        // get queue
        String queueName = channel.queueDeclare().getQueue();

        // bind exchange and queue, fanout don't use routing key
        channel.queueBind(queueName, EXCHANGE_NAME, "");

        Consumer consumer = new DefaultConsumer(channel) {

            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {

                System.out.println("body=" + new String(body, "utf-8"));

                // confirm manually, not multiple confirm
                channel.basicAck(envelope.getDeliveryTag(), false);
            }
        };

        // manually and auto not affect
        channel.basicConsume(queueName, false, consumer);
    }
}
