package floodio

import io.gatling.core.Predef._
import io.gatling.http.Predef.http

import scala.util.matching.Regex

object constants {

  val httpProtocol = http
    .baseURL("https://challengers.flood.io")
    .acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8")
    .acceptEncodingHeader("gzip, deflate")
    .acceptLanguageHeader("en-US,en;q=0.5")
    .userAgentHeader("Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:49.0) Gecko/20100101 Firefox/49.0")

  val headers_0 = Map("Upgrade-Insecure-Requests" -> "1")

  val headers_1 = Map("Accept" -> "*/*")

  val headers_6 = Map(
    "Accept" -> "*/*",
    "X-Requested-With" -> "XMLHttpRequest")

  val authenticity_token_regex = """name="authenticity_token" type="hidden" value="(.+?)""""
  val step_id_regex = """name="challenger\[step_id\]" type="hidden" value="(.+?)""""
  val orders_regex = """<span class="radio"><input class="radio_buttons optional".+? value=".+?" .+?>([0-9]+)</label></span>"""
  val order_selected_regex = """<input class="radio_buttons optional".+? value="(.+?)" />"""
  val all_order_regex = """(<span class="radio"><input class="radio_buttons optional".+? value=".+?" .+?>[0-9]+</label></span>)"""
  val max_order_regex = new Regex("""<span class="radio"><input class="radio_buttons optional".+? value="(.+?)" .+?>([0-9]+)</label></span>""")
  val challenger_order_regex ="""<input id="challenger_order_[0-9]+" name="(.+?)" type="hidden" value="[0-9]+" />""";
  val challenger_order_value_regex ="""<input id="challenger_order_[0-9]+" name=".+?" type="hidden" value="([0-9]+)" />""";
  val success_str ="""Congratulations, your scripting skills are impressive! Please share your test plan with us at support@flood.io and we'll publish your results""";

}