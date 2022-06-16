

# Starting Kafka without Zookeeper

Start Kafka in KRaft mode


```sh
kafka-storage.sh random-uuid


kafka-storage.sh format -t 04xLvXEBTiahcSpCzSdC4g -c config/kraft/server.properties

kafka-server-start.sh config/kraft/server.properties
```