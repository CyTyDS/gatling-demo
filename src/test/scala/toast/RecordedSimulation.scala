package toast

import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

class RecordedSimulation extends Simulation {

	val httpProtocol = http
		.baseUrl("http://computer-database.gatling.io")
		.inferHtmlResources(BlackList(""".*\.css""", """.*\.js""", """.*\.ico"""), WhiteList())
		.acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8")
		.acceptEncodingHeader("gzip, deflate")
		.acceptLanguageHeader("fr,fr-FR;q=0.8,en-US;q=0.5,en;q=0.3")
		.upgradeInsecureRequestsHeader("1")
		.userAgentHeader("Mozilla/5.0 (Windows NT 6.1; WOW64; rv:55.0) Gecko/20100101 Firefox/55.0")





	val scn = scenario("RecordedSimulation")
		.exec(http("request_0")
			.get("/"))
		.pause(16)
		.exec(http("request_1")
			.get("/computers?f=amstrad"))
		.pause(10)
		.exec(http("request_2")
			.get("/computers/40260"))
		.pause(24)
		.exec(http("request_3")
			.get("/"))
		.pause(11)
		.exec(http("request_4")
			.get("/computers?p=1"))
		.pause(1)
		.exec(http("request_5")
			.get("/computers?p=2")
			.resources(http("request_6")
			.get("/computers?p=3"),
            http("request_7")
			.get("/computers?p=4"),
            http("request_8")
			.get("/computers?p=5"),
            http("request_9")
			.get("/computers?p=6"),
            http("request_10")
			.get("/computers?p=7")))

	setUp(scn
		.inject(atOnceUsers(1), //1 Un seul utilisateur virtuel exécute le scénario
			rampUsersPerSec(10) to(50) during(2 minutes) //2 La simulation commence avec un ajout de 10 utilisateurs par secondes, puis monte progressivement jusqu'à 50, et ce pendant 2 minutes.
			)
		.protocols(httpProtocol))
}
