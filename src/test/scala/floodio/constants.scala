package floodio

import com.typesafe.config.ConfigFactory
import io.gatling.core.Predef._
import io.gatling.http.Predef.http

import scala.util.matching.Regex

object constants {

  val config = ConfigFactory.parseFile(new java.io.File(sys.env("GATLING_HOME") + "/user-files/simulations/floodio/floodio.properties"))

  val environment = System.getProperty("url")

  val httpProtocol = http
    .baseURL(environment)
    .acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8")
    .acceptEncodingHeader("gzip, deflate")
    .acceptLanguageHeader("en-US,en;q=0.5")
    .userAgentHeader("I AM ROBOT")

  val authenticity_token_regex = """name="authenticity_token" type="hidden" value="(.+?)""""
  val step_id_regex = """name="challenger\[step_id\]" type="hidden" value="(.+?)""""
  val orders_regex = """<span class="radio"><input class="radio_buttons optional".+? value=".+?" .+?>([0-9]+)</label></span>"""
  val order_selected_regex = """<input class="radio_buttons optional".+? value="(.+?)" />"""
  val all_order_regex = """(<span class="radio"><input class="radio_buttons optional".+? value=".+?" .+?>[0-9]+</label></span>)"""
  val max_order_regex = new Regex("""<span class="radio"><input class="radio_buttons optional".+? value="(.+?)" .+?>([0-9]+)</label></span>""")
  val challenger_order_regex ="""<input id="challenger_order_[0-9]+" name="(.+?)" type="hidden" value="[0-9]+" />""";
  val challenger_order_value_regex ="""<input id="challenger_order_[0-9]+" name=".+?" type="hidden" value="([0-9]+)" />""";
  val one_time_token_regex ="""\{\"code\":([0-9]+)\}"""
  val success_str ="""Congratulations, your scripting skills are impressive! Please share your test plan with us at support@flood.io and we'll publish your results""";

}