/**
  * Created by rodrigolimasss on 21/06/2016.
  */

package actors

import akka.actor.{Props, Actor}
import akka.kafka.ProducerMessage
import akka.kafka.scaladsl.Producer
import akka.stream.scaladsl.Source
import org.apache.kafka.clients.producer.ProducerRecord

object ProducerActor {

  def props: Props = Props(new ProducerActor())
}

class ProducerActor extends Actor {

  import settings.ServiceSettings._

  override def preStart = {
    Source(1 to 10000)
      .map(elem => ProducerMessage.Message(new ProducerRecord[Array[Byte], String]("test", elem.toString), elem))
      .via(Producer.flow(producerSettings))
      .map { result =>
        val record = result.message.record
        println(s"${record.topic}/${record.partition} ${result.offset}: ${record.value} (${result.message.passThrough}")
        result
      }

    println("Producer Started")
  }

  def receive: Receive = {
    case msg => println(s"[${self.path.name}]: UNKNOWN MESSAGE: $msg FROM ${sender.path}")
  }
}
