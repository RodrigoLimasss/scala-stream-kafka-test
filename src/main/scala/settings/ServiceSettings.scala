package settings

/**
  * Created by rodrigolimasss on 21/06/2016.
  */

import akka.actor.ActorSystem
import akka.event.{Logging, LoggingAdapter}
import akka.kafka.{ConsumerSettings, ProducerSettings}
import akka.stream.ActorMaterializer
import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.common.serialization.{StringDeserializer, ByteArrayDeserializer, StringSerializer, ByteArraySerializer}

trait ServiceSettings {
  implicit val actorSystem: ActorSystem = ActorSystem("stream-kafka-system")
  implicit val executionContext = actorSystem.dispatcher
  implicit val log: LoggingAdapter = Logging(actorSystem, getClass)
  implicit val materializer: ActorMaterializer = ActorMaterializer()

  private val kafkaHost = "10.152.20.213:9092"

  val producerSettings = ProducerSettings(actorSystem, new ByteArraySerializer, new StringSerializer)
    .withBootstrapServers(kafkaHost)

  val consumerSettings = ConsumerSettings(actorSystem, new ByteArrayDeserializer, new StringDeserializer, Set("test"))
    .withBootstrapServers(kafkaHost)
    .withGroupId("test")
    .withProperty(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest")


}

object ServiceSettings extends ServiceSettings
