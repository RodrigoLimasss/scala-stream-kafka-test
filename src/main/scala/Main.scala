import actors.InitializerActor
import settings.ServiceSettings._

/**
  * Created by rodrigolimasss on 21/06/2016.
  */

object Main extends App {

  actorSystem.actorOf(InitializerActor.props, "initializer-actor")

  actorSystem.whenTerminated

}