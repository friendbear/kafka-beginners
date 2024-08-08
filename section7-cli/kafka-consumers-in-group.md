# Kafka Consumers in Group


`--group option`

１つのトピックに対して同じグループを指定した場合は、複数のConsumerに対して分散してメッセージが送信されるが、
groupが違う場合はtopicに書き込まれた内容は同じ内容が送信されるが

```sh
kafka-console-consumer --bootstrap-server localhost:9092 --topic first_topic --group my-first-consumer-group
kafka-console-consumer --bootstrap-server localhost:9092 --topic first_topic --group my-first-consumer-group
kafka-console-consumer --bootstrap-server localhost:9092 --topic first_topic --group my-first-consumer-group
```

---
groupを分けることにより同じトピックを受け取ることができる。


kafka-console-consumer --bootstrap-server localhost:9092 --topic first_topic --group my-first-consumer-group

kafka-console-consumer --bootstrap-server localhost:9092 --topic first_topic --group `my-second-consumer-group`

```sh
 kafka-console-consumer --bootstrap-server localhost:9092 --topic first_topic --group my-first-consumer-group --from-beginning
hello producer
2 consumer group

kafka-console-consumer --bootstrap-server localhost:9092 --topic first_topic --group my-second-consumer-group --from-beginning
hello producer
2 consumer group
```
---

`--from-beginning`

