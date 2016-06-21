import actors.InitializerActor

/**
  * Created by rodrigolimasss on 21/06/2016.
  */

object Main extends App {

  import settings.ServiceSettings._

  actorSystem.actorOf(InitializerActor.props, "initializer-actor")

  actorSystem.whenTerminated

}
