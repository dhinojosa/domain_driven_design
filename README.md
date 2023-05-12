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

## Outbox Lab (Application -> Postgress <- Kafka <- MongoDB Connector)
1. Bring gitpod
2. install docker
3. run docker-compose up (should bring kafka connect, kafka, ksqldb, mssql, postgress)
4. login to kafka connect container with Attach Shell and install following connectors (https://www.confluent.io/hub/confluentinc/kafka-connect-jdbc)
   4.1 JDBC connect to connect to Postgress (where our app will write) - `confluent-hub install mongodb/kafka-connect-mongodb:1.10.0`
   4.2 MongoDB Connect that reads from psotgress and do the rest as per Outbox pattern - `confluent-hub install confluentinc/kafka-connect-jdbc:10.7.1`
5. Run the App
   5.1 sdk list java
   5.2  sdk install java 17.0.7-amzn
   5.2 mvn clean compile
   5.3 mvn exec:java -Dexec.mainClass=com.xyzcorp.outbox.CreateStocks

6. Right connect on the psogress container and click 'Attach Shell' and run following Posgress Commands
   6.1 export PGPASSWORD='docker'
   6.2 psql -d docker -U docker
   6.3 \dt (shows all tables)
   6.4 \d stock_trade (show specific table schema)
   6.5 SELECT * from stock_trade;
7. Setup the JDBC connector (reads data from soiurce table and create Kafka topics)
   7.1 Key Converter Class - `io.confluent.connect.avro.AvroConverter`
   7.2 Value Converter Class - `io.confluent.connect.avro.AvroConverter`
   7.3 DataBase -> JDBC URL - `jdbc:postgresql://postgres:5432/`
   7.3 DataBase -> JDBC URL - `jdbc:postgresql://postgres:5432/`
   7.4 DataBase -> JDBC User & Pass - `docker`
   7.5 DataBase Dialect-> `PostgressSqlDatabaseDialect`
   7.6 Mode -> Table Loading Mode - `incrementing`
   7.7 Connector -> Topic Prefix - `postgress_`
   7.8 Additional Properties -> key.converter.schema.registry.url - `http://schema-registry:8081`
   7.9 Additional Properties -> value.converter.schema.registry.url - `http://schema-registry:8081`
10. Go to KsqlDB to create Stream
```aidl
CREATE STREAM stock_trades WITH (
  KAFKA_TOPIC = 'postgress_stock_trade',
  VALUE_FORMAT = 'AVRO'
);
```
12. Go to KSQL-CLI Container -> Attach Shell and run following commands
    12.1 ksql http://ksqldb-server:8088
    12.2 show streams;
    12.3 select * from STOCK_TRADES;
13. ....