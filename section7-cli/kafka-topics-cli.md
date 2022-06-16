

# List topic

```sh
kafka-topics.sh

kafka-topics.sh --bootstrap-server localhost:9092 --list

kafka-topics.sh --bootstrap-server localhost:9092 --topic first_topic --create

kafka-topics.sh --bootstrap-server localhost:9092 --topic first_topic --create --partitions 3


kafka-topics.sh --bootstrap-server localhost:9092 --topic first_topic --create --partitions 3 --replication-factor 2
```

# Create a topic 

* OK

```sh
kafka-topics.sh --bootstrap-server localhost:9092 --topic first_topic --create --partitions 3 --replication-factor 1
```

* ERROR
BrokersとReplication Factorの関係に注目

```sh
kafka-topics.sh --bootstrap-server localhost:9092 --topic second_topic --create --partitions 3 --replication-factor 2

Error while executing topic command : Replication factor: 2 larger than available brokers: 1.
[2022-06-16 14:10:03,953] ERROR org.apache.kafka.common.errors.InvalidReplicationFactorException: Replication factor: 2 larger than available brokers: 1.
 (kafka.admin.TopicCommand$)

```

* Replcation Factor を1にしてtopicを作成

```sh
bsh ➜ kafka-topics.sh --bootstrap-server localhost:9092 --topic second_topic --describe
Topic: second_topic	TopicId: _DCN5F7QRjeEQMrfq2-6wg	PartitionCount: 3	ReplicationFactor: 1	Configs: segment.bytes=1073741824
	Topic: second_topic	Partition: 0	Leader: 0	Replicas: 0	Isr: 0
	Topic: second_topic	Partition: 1	Leader: 0	Replicas: 0	Isr: 0
	Topic: second_topic	Partition: 2	Leader: 0	Replicas: 0	Isr: 0
```

# Descrbe topic 

```bsh
kafka-topics.sh --bootstrap-server localhost:9092 --describe

Topic: twitter_tweets	TopicId: rbDCmF7KTFyuEk_eLjbaKg	PartitionCount: 3	ReplicationFactor: 1	Configs: cleanup.policy=delete,segment.bytes=1073741824
	Topic: twitter_tweets	Partition: 0	Leader: 0	Replicas: 0	Isr: 0
	Topic: twitter_tweets	Partition: 1	Leader: 0	Replicas: 0	Isr: 0
	Topic: twitter_tweets	Partition: 2	Leader: 0	Replicas: 0	Isr: 0
```