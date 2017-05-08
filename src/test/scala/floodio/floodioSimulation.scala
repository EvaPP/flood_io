package floodio

import io.gatling.core.Predef._
import floodio.scenarios.floodioScenario

class floodioSimulation extends Simulation {

  setUp(floodioScenario.scn.inject(atOnceUsers(1))).protocols(constants.httpProtocol)

}
