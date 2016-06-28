package actors

/**
  * Created by rodrigolimasss on 21/06/2016.
  */

import akka.actor.{ActorRef, Props, Actor}
import akka.stream.OverflowStrategy
import akka.stream.scaladsl.{Sink, Source}
import com.softwaremill.react.kafka.KafkaMessages._
import com.softwaremill.react.kafka.ProducerMessage
import org.reactivestreams.Subscriber

object ProducerActor {

  def props: Props = Props(new ProducerActor())
}

class ProducerActor extends Actor {

  import settings.ServiceSettings._

  private var publisher: ActorRef = _

  override def preStart = {
    initProducer()
    println("\n * \n * Producer Started \n * \n")

    self ! StartSending
  }

  def receive: Receive = {
    case StartSending => sendingMessages()
    case msg => println(s"[${self.path.name}]: UNKNOWN MESSAGE: $msg FROM ${sender.path}")
  }

  def initProducer(): Unit = {

    val subscriber: Subscriber[StringProducerMessage] = kafka.publish(producerSettings)

    publisher = Source
      .actorRef(10000, OverflowStrategy.dropHead)
      .to(Sink.fromSubscriber(subscriber))
      .run()
  }

  def sendingMessages() = {
    Range(0, 10000).foreach(x => {
      Thread.sleep(x)
      println("Producer Message: " + x)
      publisher ! ProducerMessage(x.toString)
    })
  }
}

object StartSending