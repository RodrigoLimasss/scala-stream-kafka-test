package actors

import akka.actor.{Props, Actor}

/**
  * Created by rodrigolimasss on 21/06/2016.
  */

object ConsumerActor {

  def props: Props = Props(new ConsumerActor())
}

class ConsumerActor extends Actor{
  def receive: Receive = ???
}
