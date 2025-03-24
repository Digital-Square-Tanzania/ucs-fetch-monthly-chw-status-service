package tz.go.moh;

import akka.actor.typed.ActorRef;
import akka.actor.typed.ActorSystem;
import akka.actor.typed.Scheduler;
import akka.actor.typed.javadsl.AskPattern;
import akka.http.javadsl.marshallers.jackson.Jackson;
import akka.http.javadsl.model.StatusCodes;
import akka.http.javadsl.server.Route;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tz.go.moh.domain.EligibleChwCheckRequest;
import tz.go.moh.util.CustomJacksonSupport;

import java.time.Duration;
import java.util.concurrent.CompletionStage;

import static akka.http.javadsl.server.Directives.*;

/**
 * Defines the routes for checking the monthly status of Community Health Workers (CHWs).
 * This class handles HTTP requests related to CHW status checks.
 */
public class UcsChwStatusCheckRoutes {
    //#routes-class
    private final static Logger log = LoggerFactory.getLogger(UcsChwStatusCheckRoutes.class);
    private final ActorRef<UcsChwStatusCheckRegistry.Command> labIntegrationActor;
    private final Duration askTimeout;
    private final Scheduler scheduler;
    private ActorSystem<?> system;

    /**
     * Constructor for UcsChwStatusCheckRoutes.
     *
     * @param system              The Akka actor system.
     * @param labIntegrationActor The actor responsible for handling CHW status check requests.
     */
    public UcsChwStatusCheckRoutes(ActorSystem<?> system, ActorRef<UcsChwStatusCheckRegistry.Command> labIntegrationActor) {
        this.labIntegrationActor = labIntegrationActor;
        this.system = system;
        scheduler = system.scheduler();
        askTimeout = system.settings().config().getDuration("chw-status-check-service.routes.ask-timeout");

    }

    /**
     * Checks the eligibility status of a CHW.
     *
     * @param eligibleChwCheckRequest The request containing the CHW's information.
     * @return A CompletionStage that will eventually contain the result of the status check.
     */
    private CompletionStage<UcsChwStatusCheckRegistry.ActionPerformed> checkChwStatus(EligibleChwCheckRequest eligibleChwCheckRequest) {
        return AskPattern.ask(labIntegrationActor, ref -> new UcsChwStatusCheckRegistry.CheckChwEligibilityStatus(eligibleChwCheckRequest, system, ref), askTimeout, scheduler);
    }

    /**
     * Defines the route for checking the monthly status of CHWs.
     * <p>
     * This route handles POST requests to the "/chw/monthly-status" endpoint.
     * It expects a JSON payload representing an EligibleChwCheckRequest.
     * </p>
     * @return The defined route.
     */
    public Route checkChwMonthlyStatusRoutes() {
        return pathPrefix("chw", () ->
                path("monthly-status", () ->
                        post(() ->
                                entity(
                                        CustomJacksonSupport.customJacksonUnmarshaller(EligibleChwCheckRequest.class),
                                        eligibleChwCheckRequest ->
                                                onSuccess(checkChwStatus(eligibleChwCheckRequest), performed -> {
                                                    return complete(StatusCodes.OK, performed.response(), Jackson.marshaller());
                                                })
                                )
                        )
                )
        );
    }

}
