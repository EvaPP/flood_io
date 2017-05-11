package floodio.scenarios

import floodio.requests.floodioRequests
import io.gatling.core.Predef.scenario
import io.gatling.core.Predef._
import floodio.constants._

object floodioScenario {

  val min_think_time = config.getInt("min_think_time")
  val max_think_time = config.getInt("max_think_time")

  val scn = scenario("RecordedSimulation")
    .feed(csv("ages.csv").circular)
    .exec(floodioRequests.start)
    .pause(min_think_time, max_think_time)
    .exec(floodioRequests.step1)
    .pause(min_think_time, max_think_time)
    .exec(floodioRequests.step2)
    .foreach("${order_list}", "order") {
      floodioRequests.extractSelectedOrder
    }
    .pause(min_think_time, max_think_time)
    .exec(floodioRequests.step3)
    .pause(min_think_time, max_think_time)
    .exec(floodioRequests.createChalangerOrderMap)
    .exec(floodioRequests.step4)
    .pause(min_think_time, max_think_time)
    .exec(floodioRequests.step5)

}