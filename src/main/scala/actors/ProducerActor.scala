/**
  * Created by rodrigolimasss on 21/06/2016.
  */

package actors

import akka.actor.{Props, Actor}

object ProducerActor {

  def props: Props = Props(new ProducerActor())
}

class ProducerActor extends Actor{
  def receive: Receive = ???
}
