package actors

/**
  * Created by rodrigolimasss on 21/06/2016.
  */

import akka.actor.{Props, Actor}
import akka.kafka.scaladsl.Consumer
import scala.concurrent.Future
import settings.ServiceSettings._

object ConsumerActor {

  def props: Props = Props(new ConsumerActor())
}

class ConsumerActor extends Actor {

  override def preStart = {

    Consumer.atMostOnceSource(consumerSettings.withClientId("client1"))
      .mapAsync(1)(record => ConsumerReceiver.printInWindow(record.value))

    self ! ConsumerStarted
  }

  def receive: Receive = {
    case ConsumerStarted => context.parent ! ConsumerStarted
    case msg => println(s"[${self.path.name}]: UNKNOWN MESSAGE: $msg FROM ${sender.path}")
  }

}

case object ConsumerStarted

object ConsumerReceiver {

  def printInWindow(msg: String): Future[String] = {
    println(s"Message: ${msg}")

    Future("OK")
  }
}