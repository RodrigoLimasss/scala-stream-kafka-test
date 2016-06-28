package settings

/**
  * Created by rodrigolimasss on 21/06/2016.
  */

import akka.actor.ActorSystem
import akka.event.{Logging, LoggingAdapter}
import akka.stream.ActorMaterializer
import com.softwaremill.react.kafka.{ConsumerProperties, ProducerProperties, ReactiveKafka}
import org.apache.kafka.common.serialization.{StringDeserializer, StringSerializer}

trait ServiceSettings {
  implicit val actorSystem: ActorSystem = ActorSystem("stream-kafka-system")
  implicit val executionContext = actorSystem.dispatcher
  implicit val log: LoggingAdapter = Logging(actorSystem, getClass)
  implicit val materializer: ActorMaterializer = ActorMaterializer()

  private val kafkaHost = "10.152.20.213:9092"
  private val topic = "test"

  val producerSettings = ProducerProperties(
    bootstrapServers = kafkaHost,
    topic = topic,
    valueSerializer = new StringSerializer()
  )

  val consumerSettings = ConsumerProperties(
    bootstrapServers = kafkaHost,
    topic = topic,
    groupId = "groupName",
    valueDeserializer = new StringDeserializer()
  ).readFromEndOfStream()

  val kafka = new ReactiveKafka()

}

object ServiceSettings extends ServiceSettings
