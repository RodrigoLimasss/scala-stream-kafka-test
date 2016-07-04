package models

import play.api.libs.json.{Json, Format}

/**
  * Created by rodrigolimasss on 01/07/2016.
  */
case class Person(id: Int, name: String, age: Int, height: Double)

object Protocols {
  implicit val formatPerson: Format[Person] = Json.format[Person]
}
