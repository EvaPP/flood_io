package floodio.scenarios

import floodio.requests.floodioRequests
import io.gatling.core.Predef.scenario

import io.gatling.core.Predef._

object floodioScenario {

  val scn = scenario("RecordedSimulation")
    .exec(floodioRequests.start)
    .pause(2)
    .exec(floodioRequests.step1)
    .pause(4)
    .exec(floodioRequests.step2)
    .foreach("${order_list}", "order") {
      floodioRequests.extractSelectedOrder
    }
    .pause(8)
    .exec(floodioRequests.step3)
    .pause(1)
    .exec(floodioRequests.createChalangerOrderMap)
    .exec(floodioRequests.step4)
    .pause(6)
    .exec(floodioRequests.step5)

}