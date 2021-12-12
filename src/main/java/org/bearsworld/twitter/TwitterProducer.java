package org.bearsworld.twitter;

import com.google.common.collect.Lists;
import com.twitter.hbc.ClientBuilder;
import com.twitter.hbc.core.Client;
import com.twitter.hbc.core.Constants;
import com.twitter.hbc.core.Hosts;
import com.twitter.hbc.core.HttpHosts;
import com.twitter.hbc.core.endpoint.StatusesFilterEndpoint;
import com.twitter.hbc.core.processor.StringDelimitedProcessor;
import com.twitter.hbc.httpclient.auth.Authentication;
import com.twitter.hbc.httpclient.auth.OAuth1;
import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.protocol.types.Field;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yaml.snakeyaml.Yaml;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;

import java.util.List;
import java.util.Properties;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import static java.lang.System.getProperty;

public class TwitterProducer {

    Logger logger = LoggerFactory.getLogger(TwitterProducer.class.getName());
    public TwitterProducer()  {
    }

    static public List<String> terms = Lists.newArrayList("churi_p_irm", "@churi_p_irm");
    public void run() {

        logger.info("Setup");

        // create a twitter client
        /** Set up your blocking queues: Be sure to size these properly based on expected TPS of your stream */
        BlockingQueue<String> msgQueue = new LinkedBlockingQueue<String>(1000);
        var client = createTwitterClient(msgQueue);

        client.connect();

        // create a kafka producer
        var kafkaProducer = createKafkaProducer();


        // add a shutdown hook
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            logger.info("stopping application...");
            logger.info("shutting down client from twitter...");
            client.stop();
            logger.info("closing producer...");
            kafkaProducer.close();
            logger.info("done...");
        }));
        // loop to send tweets to kafka
        // on a different thread, or multiple different threads....
        while (!client.isDone()) {
            String msg = null;
            try {
                msg = msgQueue.poll(5, TimeUnit.SECONDS);
            } catch (InterruptedException e) {
                e.printStackTrace();
                client.stop();
            }
            if (msg != null) {
                kafkaProducer.send(new ProducerRecord<>("twitter_tweets", null, msg), new Callback() {
                    @Override
                    public void onCompletion(RecordMetadata recordMetadata, Exception e) {

                        if (e != null) {
                            logger.error("Something bad happened...", e);
                        }
                    }
                });
                logger.info(msg);
            }
        }

        logger.info("End of Applications.");
    }

    public Client createTwitterClient(BlockingQueue<String> msgQueue) {

        /** Declare the host you want to connect to, the endpoint, and authentication (basic auth or oauth) */
        var hosebirdHosts = new HttpHosts(Constants.STREAM_HOST);
        var hosebirdEndpoint = new StatusesFilterEndpoint();
        hosebirdEndpoint.trackTerms(terms);
        var props = new Properties();
        try {
            props.load(TwitterProducer.class.getClassLoader().getResourceAsStream("twitter.properties"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        var consumerKey = props.getProperty("consumerKey");
        var consumerSecret = props.getProperty("consumerSecret");
        var accessToken = props.getProperty("accessToken");
        var accessTokenSecret = props.getProperty("accessTokenSecret");

        // These secrets should be read from a config file
        Authentication hosebirdAuth = new OAuth1(
                consumerKey, consumerSecret, accessToken, accessTokenSecret);

//        new TwitterFactory().getInstance();
//        twitter.setOAuthConsumer(CONSUMER_KEY, CONSUMER_SECRET_KEY);
//        AccessToken oauthAccessToken = new AccessToken(getSavedAccessToken(), getSavedAccessTokenSecret());
//        twitter.setOAuthAccessToken(oauthAccessToken);

        var builder = new ClientBuilder()
                .name("Hosebird-Client-01")                              // optional: mainly for the logs
                .hosts(hosebirdHosts)
                .authentication(hosebirdAuth)
                .endpoint(hosebirdEndpoint)
                .processor(new StringDelimitedProcessor(msgQueue));

        Client hosebirdClient = builder.build();
        return hosebirdClient;

    }
    public KafkaProducer<String, String> createKafkaProducer() {
        var bootstrapServers = "127.0.0.1:9092";
        // create Producer properties
        var properties = new Properties();

        // create Producer properties
        properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

        // create safe Producer
        properties.setProperty(ProducerConfig.ENABLE_IDEMPOTENCE_DOC, "true");
        properties.setProperty(ProducerConfig.ACKS_CONFIG, "all");
        properties.setProperty(ProducerConfig.RETRIES_CONFIG, Integer.toString(Integer.MAX_VALUE));
        //properties.setProperty(ProducerConfig.MAX_IN_FLIGHT_REQUESTS_PER_CONNECTION, "5");

        //create the producer
        return new KafkaProducer<String, String>(properties);
    }

    public static void main(String[] argv) {
        new TwitterProducer().run();
    }
}
