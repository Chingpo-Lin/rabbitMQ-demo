package pub;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.nio.charset.StandardCharsets;

public class Send {

    private final static String EXCHANGE_NAME = "exchange_fanout";

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

            // bind with exchange fanout
            channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.FANOUT);
            
            String msg = "rabbit mq publish fanout";

            channel.basicPublish(EXCHANGE_NAME, "", null, msg.getBytes(StandardCharsets.UTF_8));

            System.out.println("fanout send success");
        }
    }
}
