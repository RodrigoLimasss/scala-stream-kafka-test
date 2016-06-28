package actors

/**
  * Created by rodrigolimasss on 21/06/2016.
  */

import akka.actor.{Actor, Props}

object InitializerActor {

  def props: Props = Props(new InitializerActor())
}

class InitializerActor extends Actor{

  override def preStart: Unit = {
    context.actorOf(ConsumerActor.props, "consumer-actor")
  }

  def receive: Receive = {
    case ConsumerStarted => context.actorOf(ProducerActor.props, "producer-actor")
  }
}
