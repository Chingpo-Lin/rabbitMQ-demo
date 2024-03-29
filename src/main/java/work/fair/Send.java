package work.fair;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.nio.charset.StandardCharsets;

public class Send {

    private final static String QUEUE_NAME = "work_mq_fair";

    public static void main(String[] argv) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("120.24.94.91");
        factory.setUsername("admin");
        factory.setPassword("cannot let u know");
        factory.setVirtualHost("/dev");
        factory.setPort(5672);

        try (Connection connection = factory.newConnection();

             // create channel
             Channel channel = connection.createChannel()) {

            /**
             *  name
             *  duration: mq restart and still here
             *  exclusive: only one consumer listener queue, if we want to delete the queue when connection close
             *  (true when we deploy sth or subscribe sth
             *  auto delete: false, when no consumer, auto delete
             *  other argument:
             *
             *  when queue not exist then will auto create. if exists will not override
             *
             */
            channel.queueDeclare(QUEUE_NAME, false, false, false, null);

            for (int i = 0; i < 10; i++) {
                String message = "Hello World!" + i;
                channel.basicPublish("", QUEUE_NAME, null, message.getBytes(StandardCharsets.UTF_8));
                System.out.println(" [x] Sent '" + message + "'");
            }
        }
    }
}
