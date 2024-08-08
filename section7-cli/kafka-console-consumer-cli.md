# kafka-console-consumer.sh

# consuming
kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic first_topic


# other terminal
kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic first_topic


# consuming from beginning
kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic first_topic --from-beginning

# display key, values and timestamp in consumer

kafka-console-consumer --bootstrap-server localhost:9092 --topic first_topic --formatter kafka.tools.DefaultMessageFormatter --property print.timestamp=true --property print.key=true --property print.value=true --from-beginning

```sh
kafka-console-consumer --bootstrap-server localhost:9092 --topic first_topic --formatter kafka.tools.DefaultMessageFormatter --property print.timestamp=true --property print.key=true --property print.value=true --from-beginning
CreateTime:1655366716925	null	a
CreateTime:1655366717905	null	a
CreateTime:1655366720930	null	b
CreateTime:1655367059382	null	1,2,3
CreateTime:1655367129185	null	こんにちわ💓
CreateTime:1655367813658	null	example key2: name
CreateTime:1655367869359	example key2	 name
CreateTime:1655367874392	example key2	 name
CreateTime:1655366702640	null	a
CreateTime:1655366702650	null	a
CreateTime:1655366703848	null	a
CreateTime:1655366719048	null	c
CreateTime:1655366724001	null	hello
CreateTime:1655367049321	null	ajaaa
CreateTime:1655367803241	null	example key: label
CreateTime:1655367948463	key	 name ,{ data: { name: "hoge" }
CreateTime:1655366726504	null	world
CreateTime:1655367115102	null	bbbb
CreateTime:1655367856816	example key	 label
CreateTime:1655368045005	example key	 [ { name: "hoge" } , { name: "huga" } ]
CreateTime:1655368757570	example key	 test
CreateTime:1655368781490	key 2	 data2
```