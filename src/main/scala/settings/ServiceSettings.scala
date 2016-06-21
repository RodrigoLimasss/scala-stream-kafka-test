package settings

/**
  * Created by rodrigolimasss on 21/06/2016.
  */

import akka.actor.ActorSystem
import akka.event.{Logging, LoggingAdapter}
import akka.stream.ActorMaterializer

trait ServiceSettings {
  implicit val actorSystem: ActorSystem = ActorSystem("stream-kafka-system")
  implicit val executionContext = actorSystem.dispatcher
  implicit val log: LoggingAdapter = Logging(actorSystem, getClass)
  implicit val materializer: ActorMaterializer = ActorMaterializer()
}

object ServiceSettings extends ServiceSettings
