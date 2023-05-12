# Domain Driven Design Application

# Setting Up Kafka Lab Excercise

## Steps
1. Add Avro Schema in src/main/avro as mentioned [here](https://github.com/dhinojosa/kafka-client/blob/answers/src/main/avro/Order.avsc)
2. Add Avro pom dependencies (plugin & regular dependencies). You can look the source data from [here](https://github.com/dhinojosa/kafka-client/blob/answers/pom.xml)
3. Create Producer class inside your project as mentioned [here](https://github.com/dhinojosa/kafka-client/blob/answers/src/main/java/com/xyzcorp/MyAvroProducer.java)
4. Run Docker compose from command line
```aidl
docker-compose up -d broker schema-registry control-center
docker-compose down
```
5. Access Kafka cluster using [this](http://localhost:9021)
6. Create kafka stream for the topic
    ```
    CREATE STREAM my_avro_orders \
    (total BIGINT, shipping VARCHAR, state VARCHAR, discount DOUBLE, \
    gender VARCHAR) WITH (kafka_topic='my_avro_orders', value_format='AVRO');
    ```
7. Run few commands in KSQLDB
   ```
   select * from MY_AVRO_ORDERS where total > 25000 EMIT CHANGES;
   ```
8. Run Aggregates query in KSQLDB (it creates an epheremal/temp table within KSQLDB)
   ```Aggregates based of State
   select state,count(*) as count from MY_AVRO_ORDERS group by state EMIT CHANGES;
   ```
9.Creating the Permanent table that is queryable
   ```
   create table orders_by_state with (partitions=3) as select state,count(*) as count from MY_AVRO_ORDERS group by state EMIT CHANGES;
   ```
10. Now we can use either another producer that reads from above perm table and publish a message that goes to another topic which other consumers consumes aggregates events.

11. Stop the docker
   ```
   docker-compose down
   ```