package floodio

import io.gatling.core.Predef._
import floodio.scenarios.floodioScenario

class floodioSimulation extends Simulation {

  val users_quantity = Integer.valueOf(System.getProperty("users"))

  setUp(floodioScenario.scn.inject(atOnceUsers(users_quantity))).protocols(constants.httpProtocol)

}