package bearsworld.demos.kafka.opensearch;

import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.Credentials;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.DefaultConnectionKeepAliveStrategy;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.protocol.types.Field;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.opensearch.action.index.IndexRequest;
import org.opensearch.client.RequestOptions;
import org.opensearch.client.RestClient;
import org.opensearch.client.RestClientBuilder;
import org.opensearch.client.RestHighLevelClient;
import org.opensearch.client.indices.CreateIndexRequest;
import org.opensearch.client.indices.GetIndexRequest;
import org.opensearch.common.xcontent.XContentType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URI;
import java.security.Principal;
import java.time.Duration;
import java.util.Collections;
import java.util.Properties;

public class OpenSearchConsumer {

    public static void main(String[] args) throws IOException {

        Logger log = LoggerFactory.getLogger(OpenSearchConsumer.class.getSimpleName());
        // we build a URI from the connection string

        // first create on OpenSearch Client

        RestHighLevelClient openSearchClient = createOpenSearchClient();

        try (openSearchClient) {

            Boolean indexExists = openSearchClient.indices().exists(new GetIndexRequest("wikimedia"), RequestOptions.DEFAULT);
            if (indexExists) {

                log.info("The Wikimedia Index already exists.");
            } else {
                CreateIndexRequest createIndexRequest = new CreateIndexRequest("wikimedia");
                openSearchClient.indices().create(createIndexRequest, RequestOptions.DEFAULT);
                log.info("The Wikimedia Index has been created.");
            }
        }

        // create our Kafka Client
        var consumer = createKafkaConsumer();

        consumer.subscribe(Collections.singleton("wikimedia.recentchange"));

        while (true) {

            ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(3000));
            int recordCount = records.count();

            log.info("Received " + recordCount + " records(s)");

            for (ConsumerRecord<String, String> record: records) {

                IndexRequest indexRequest = new IndexRequest("wikimedia")
                        .source(record.value(), XContentType.JSON);
                openSearchClient.index(indexRequest, RequestOptions.DEFAULT);

                log.info("Inserted I document into OpenSearch");
            }
        }


        // main code logic

        // case things
    }

    private static KafkaConsumer<String, String> createKafkaConsumer() {


        String bootstrapServers = "127.0.0.1:9092";
        String groupId = "consumer-opensearch-demo";

        Properties properties = new Properties();
        properties.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        properties.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.setProperty(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        properties.setProperty(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "latest");

        return new KafkaConsumer<String, String>(properties);
    }
    public static RestHighLevelClient createOpenSearchClient() {
        String connString = "http://localhost:9200";

        RestHighLevelClient restHighLevelClient;
        URI connUri = URI.create(connString);
        String userInfo = connUri.getUserInfo();

        if (userInfo == null) {

            restHighLevelClient = new RestHighLevelClient(RestClient.builder(
                    new HttpHost(connUri.getHost(), connUri.getPort()))
            );

        } else {

            String[] auth = userInfo.split(":");

            final CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
            credentialsProvider.setCredentials(AuthScope.ANY,
                    new UsernamePasswordCredentials(auth[0], auth[1]));

            RestClientBuilder builder = RestClient.builder(new HttpHost(connUri.getHost(), connUri.getPort()))
                    .setHttpClientConfigCallback(httpClientBuilder ->
                            httpClientBuilder.setDefaultCredentialsProvider(credentialsProvider));

           restHighLevelClient = new RestHighLevelClient(builder);
        }
        return restHighLevelClient;
    }
}
