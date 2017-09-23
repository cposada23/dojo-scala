package controllers

import javax.inject._

import models.Place
import play.api._
import play.api.mvc._
import play.api.libs.json.{JsError, JsSuccess, Json}
/**
 * This controller creates an `Action` to handle HTTP requests to the
 * application's home page.
 */
@Singleton
class HomeController @Inject()(cc: ControllerComponents) extends AbstractController(cc) {

  var places = List(
    Place(1, "Barbosa", Some("Cualquier cosa")),
    Place(2, "UdeA", Some("Otra cosa")),
    Place(3, "Rionegro", None)
  )

  def listPlaces = Action{
    val json = Json.toJson(places)
    Ok(json)
  }

  def addPlace = Action{
    request => val json = request.body.asJson.get
    json.validate[Place] match{
      case success: JsSuccess[Place] =>
        places = places :+ success.get
        val json = Json.toJson(places)
        Ok(json)
      case error:JsError => BadRequest(Json.toJson(Map("error" -> "error")))
    }
  }


  /**
   * Create an Action to render an HTML page.
   *
   * The configuration in the `routes` file means that this method
   * will be called when the application receives a `GET` request with
   * a path of `/`.
   */

  def index() = Action { implicit request: Request[AnyContent] =>
    Ok(views.html.index())
  }
}
