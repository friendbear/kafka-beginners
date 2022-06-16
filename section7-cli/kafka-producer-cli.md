#  kafka-console-producer


```bsh

kafka-console-producer --bootstrap-server localhost:9092 --topic first_topic
a
a>
>>a
>a
>a
>c
>b
>hello
>world
```

---
```bsh
[main] INFO bearsworld.ConsumerDemo - Key: null, Value: a
[main] INFO bearsworld.ConsumerDemo - Partition: 1
[main] INFO bearsworld.ConsumerDemo - Key: null, Value: a
[main] INFO bearsworld.ConsumerDemo - Partition: 1
[main] INFO bearsworld.ConsumerDemo - Key: null, Value: a
[main] INFO bearsworld.ConsumerDemo - Partition: 1
[main] INFO bearsworld.ConsumerDemo - Key: null, Value: a
[main] INFO bearsworld.ConsumerDemo - Partition: 0
[main] INFO bearsworld.ConsumerDemo - Key: null, Value: a
[main] INFO bearsworld.ConsumerDemo - Partition: 0
[main] INFO bearsworld.ConsumerDemo - Key: null, Value: c
[main] INFO bearsworld.ConsumerDemo - Partition: 1
[main] INFO bearsworld.ConsumerDemo - Key: null, Value: b
[main] INFO bearsworld.ConsumerDemo - Partition: 0
[main] INFO bearsworld.ConsumerDemo - Key: null, Value: hello
[main] INFO bearsworld.ConsumerDemo - Partition: 1
[main] INFO bearsworld.ConsumerDemo - Key: null, Value: world
[main] INFO bearsworld.ConsumerDemo - Partition: 2
[main] INFO org.apache.kafka.clients.NetworkClient - [Consumer clientId=consumer-my-fourth-application-1, groupId=my-fourth-application] Node -1 disconnected.
```

---

```bsh
bsh ✗  kafka-console-producer --bootstrap-server localhost:9092 --topic first_topic --producer-property acks=all
>ajaaa
>1,2,3
>bbbb
>こんにちわ💓
>^C



[main] INFO bearsworld.ConsumerDemo - Key: null, Value: ajaaa
[main] INFO bearsworld.ConsumerDemo - Partition: 1
[main] INFO bearsworld.ConsumerDemo - Key: null, Value: 1,2,3
[main] INFO bearsworld.ConsumerDemo - Partition: 0
[main] INFO bearsworld.ConsumerDemo - Key: null, Value: bbbb
[main] INFO bearsworld.ConsumerDemo - Partition: 2
[main] INFO bearsworld.ConsumerDemo - Key: null, Value: こんにちわ💓
[main] INFO bearsworld.ConsumerDemo - Partition: 0
```

