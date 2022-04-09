echo "Waiting for Kafka to come online..."

cub kafka-ready -b kafka:29092 1 20

# create the users topic
kafka-topics \
  --bootstrap-server kafka:9092 \
  --topic inputTopic \
  --replication-factor 1 \
  --partitions 1 \
  --create

kafka-topics \
  --bootstrap-server kafka:9092 \
  --topic inputTopic2 \
  --replication-factor 1 \
  --partitions 1 \
  --create

kafka-topics \
  --bootstrap-server kafka:9092 \
  --topic in_pizza_topic \
  --replication-factor 1 \
  --partitions 1 \
  --create

kafka-topics \
  --bootstrap-server kafka:9092 \
  --topic validMessages \
  --replication-factor 1 \
  --partitions 1 \
  --create

kafka-topics \
  --bootstrap-server kafka:9092 \
  --topic invalidMessages \
  --replication-factor 1 \
  --partitions 1 \
  --create

kafka-topics \
  --bootstrap-server kafka:9092 \
  --topic pizzaOrder \
  --replication-factor 1 \
  --partitions 1 \
  --create

kafka-topics \
    --bootstrap-server kafka:9092 \
    --topic pineapplePizzaTopic \
    --replication-factor 1 \
    --partitions 1 \
    --create

sleep infinity