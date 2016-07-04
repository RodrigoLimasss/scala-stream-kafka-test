package actors

/**
  * Created by rodrigolimasss on 21/06/2016.
  */

import akka.actor.{Props, Actor}
import akka.stream.OverflowStrategy
import akka.stream.scaladsl.Source
import com.softwaremill.react.kafka.KafkaMessages.StringConsumerRecord
import com.softwaremill.react.kafka.ProducerMessage
import models.Protocols
import org.reactivestreams.Publisher
import play.api.libs.json.Json
import settings.ServiceSettings._

object ConsumerActor {

  def props: Props = Props(new ConsumerActor())
}

class ConsumerActor extends Actor {

  override def preStart = {
    initConsumer()
    println("\n * \n * Consumer Started \n * \n")
    context.parent ! ConsumerStarted
  }

  def receive: Receive = {
    case pMsg: ProducerMessage[Array[Byte], String] => printInWindow(pMsg)
    case msg => println(s"[${self.path.name}]: UNKNOWN MESSAGE: $msg FROM ${sender.path}")
  }

  def initConsumer(): Unit = {

    val publisher: Publisher[StringConsumerRecord] = kafka.consume(consumerSettings)

    Source
      .fromPublisher(publisher)
      .buffer(10000, OverflowStrategy.dropHead)
      .map(m => ProducerMessage(m.value.toString))
      .runForeach(self ! _)
  }

  def printInWindow(msg: ProducerMessage[Array[Byte], String]): Unit = {
    val obj = Json.fromJson(Json.parse(msg.value))(Protocols.formatPerson).get
    println(s"${obj.id} - ${obj.name} - ${obj.age} - ${obj.height} ")
  }

}

case object ConsumerStarted