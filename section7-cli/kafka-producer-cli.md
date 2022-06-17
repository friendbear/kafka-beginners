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


```bsh
[main] INFO bearsworld.ConsumerDemo - Key: null, Value: ajaaa
[main] INFO bearsworld.ConsumerDemo - Partition: 1
[main] INFO bearsworld.ConsumerDemo - Key: null, Value: 1,2,3
[main] INFO bearsworld.ConsumerDemo - Partition: 0
[main] INFO bearsworld.ConsumerDemo - Key: null, Value: bbbb
[main] INFO bearsworld.ConsumerDemo - Partition: 2
[main] INFO bearsworld.ConsumerDemo - Key: null, Value: こんにちわ💓
[main] INFO bearsworld.ConsumerDemo - Partition: 0
```


## option  

--property parse.key=true --property parse.key=true --property key.separator=:

---
```sh
bsh ✗  kafka-console-producer.sh --bootstrap-server localhost:9092 --topic first_topic --property parse.key=true --property perse.key= true --property key.separator=:
>key: name ,{ data: { name: "hoge" }
>example key: [ { name: "hoge" } , { name: "huga" } ]


[main] INFO bearsworld.ConsumerDemo - Key: key, Value:  name ,{ data: { name: "hoge" }
[main] INFO bearsworld.ConsumerDemo - Partition: 1
[main] INFO bearsworld.ConsumerDemo - Key: example key, Value:  [ { name: "hoge" } , { name: "huga" } ]
[main] INFO bearsworld.ConsumerDemo - Partition: 2
```

```sh
bsh ✗  kafka-console-producer.sh --bootstrap-server localhost:9092 --topic first_topic --property parse.key=true --property perse.key= true --property key.separator=:
>example key: test
>key 2: data2
```
> [main] INFO bearsworld.ConsumerDemo - Key: example key, Value:  test
[main] INFO bearsworld.ConsumerDemo - Partition: 2
[main] INFO bearsworld.ConsumerDemo - Key: key 2, Value:  data2
[main] INFO bearsworld.ConsumerDemo - Partition: 2