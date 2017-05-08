package floodio.requests

import floodio.constants
import io.gatling.core.Predef._
import io.gatling.http.Predef._

object floodioRequests {

  val start = http("start")
    .get("/")
    .headers(constants.headers_0)
    .resources(http("request_1")
      .get("/img/header.jpg")
      .headers(constants.headers_1))
    .check(regex(constants.step_id_regex).find.saveAs("step_id"))
    .check(regex(constants.authenticity_token_regex).find.saveAs("authenticity_token"))

  val step1 = http("step1")
    .post("/start")
    .headers(constants.headers_0)
    .formParam("utf8", "✓")
    .formParam("authenticity_token", "${authenticity_token}")
    .formParam("challenger[step_id]", "${step_id}")
    .formParam("challenger[step_number]", "1")
    .formParam("commit", "Start")
    .check(regex(constants.step_id_regex).find.saveAs("step_id"))
    .check(regex(constants.authenticity_token_regex).find.saveAs("authenticity_token"))

  val step2 = http("step2")
    .post("/start")
    .headers(constants.headers_0)
    .formParam("utf8", "✓")
    .formParam("authenticity_token", "${authenticity_token}")
    .formParam("challenger[step_id]", "${step_id}")
    .formParam("challenger[step_number]", "2")
    .formParam("challenger[age]", "21")
    .formParam("commit", "Next")
    .check(regex(constants.step_id_regex).find.saveAs("step_id"))
    .check(regex(constants.authenticity_token_regex).find.saveAs("authenticity_token"))
    .check(regex(constants.orders_regex).find.saveAs("largest_order"))
    .check(regex(constants.order_selected_regex).find.saveAs("order_selected"))
    .check(regex(constants.all_order_regex).findAll.saveAs("order_list")
    )

  var extractSelectedOrder=exec(session => {
    val order = Integer.valueOf((constants.max_order_regex findFirstMatchIn session("order").as[String]).get.group(2))
    var largest_order = Integer.valueOf(session.attributes("largest_order").toString)
    var order_selected = session.attributes("order_selected").toString
    if (largest_order < order.intValue()) {
      largest_order = order
      order_selected = (constants.max_order_regex findFirstMatchIn session("order").as[String]).get.group(1)
    }
    session
      .set("largest_order", largest_order)
      .set("order_selected", order_selected)
  })

  val step3 = http("step3")
    .post("/start")
    .headers(constants.headers_0)
    .formParam("utf8", "✓")
    .formParam("challenger[step_number]", "3")
    .formParam("authenticity_token", "${authenticity_token}")
    .formParam("challenger[step_id]", "${step_id}")
    .formParam("challenger[largest_order]", "${largest_order}")
    .formParam("challenger[order_selected]", "${order_selected}")
    .formParam("commit", "Next")
    .check(regex(constants.step_id_regex).find.saveAs("step_id"))
    .check(regex(constants.authenticity_token_regex).find.saveAs("authenticity_token"))
    .check(regex(constants.challenger_order_regex).findAll.saveAs("challenger_orders"))
    .check(regex(constants.challenger_order_value_regex).find.saveAs("challenger_order_value"))

  val step4 = http("step4")
    .post("/start")
    .headers(constants.headers_0)
    .formParam("utf8", "✓")
    .formParam("authenticity_token", "${authenticity_token}")
    .formParam("challenger[step_id]", "${step_id}")
    .formParam("challenger[step_number]", "4")
    .formParamMap("${challenger_order_map}")
    .check(regex(constants.step_id_regex).find.saveAs("step_id"))
    .check(regex(constants.authenticity_token_regex).find.saveAs("authenticity_token"))
    .resources(http("request_6")
      .get("/code")
      .headers(constants.headers_6)
      .check(jsonPath("$..code").saveAs("one_time_token")))

  val step5 = http("step5")
    .post("/start")
    .headers(constants.headers_0)
    .header("User-Agent", "I AM ROBOT")
    .formParam("utf8", "✓")
    .formParam("authenticity_token", "${authenticity_token}")
    .formParam("challenger[step_id]", "${step_id}")
    .formParam("challenger[step_number]", "5")
    .formParam("challenger[one_time_token]", "${one_time_token}")
    .formParam("commit", "Next")
    .check(regex(constants.success_str).find)

  val createChalangerOrderMap = exec(session => {
    val mp = collection.mutable.Map[String, String]()
    val orderList = session.attributes("challenger_orders").asInstanceOf[List[String]]
    for (i <- orderList.indices) {
      mp put(orderList(i).toString, session.attributes("challenger_order_value").toString)
    }
    val n = collection.immutable.Map(mp.toSeq: _*)
    session.set("challenger_order_map", n)
  })

}