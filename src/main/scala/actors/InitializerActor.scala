/**
  * Created by rodrigolimasss on 21/06/2016.
  */

package actors

import akka.actor.{ActorRef, Actor, Props}

object InitializerActor {

  def props: Props = Props(new InitializerActor())
}

class InitializerActor extends Actor{

  private var producerActor: ActorRef = _
  private var consumerActor: ActorRef = _

  override def preStart: Unit = {
    producerActor = context.actorOf(ProducerActor.props, "producer-actor")
    consumerActor = context.actorOf(ConsumerActor.props, "consumer-actor")
  }

  def receive: Receive = ???
}
