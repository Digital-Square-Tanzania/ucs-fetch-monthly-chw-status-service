package tz.go.moh;

import akka.actor.typed.ActorRef;
import akka.actor.typed.ActorSystem;
import akka.actor.typed.Behavior;
import akka.actor.typed.javadsl.AbstractBehavior;
import akka.actor.typed.javadsl.ActorContext;
import akka.actor.typed.javadsl.Behaviors;
import akka.actor.typed.javadsl.Receive;
import tz.go.moh.actors.CheckChwEligibilityStatusProcessor;
import tz.go.moh.domain.ChwEligibilityResults;
import tz.go.moh.domain.EligibleChwCheckRequest;

import java.util.List;

/**
 * This class represents the registry actor for checking CHW (Community Health Worker) eligibility status.
 * It handles the reception of commands to check CHW eligibility and returns the results.
 */
//#UCSLab-registry-actor
public class UcsChwStatusCheckRegistry extends AbstractBehavior<UcsChwStatusCheckRegistry.Command> {

    private UcsChwStatusCheckRegistry(ActorContext<Command> context) {
        super(context);
    }

    /**
     * Creates a new instance of the UcsChwStatusCheckRegistry actor.
     *
     * @return A Behavior for the UcsChwStatusCheckRegistry actor.
     */
    public static Behavior<Command> create() {
        return Behaviors.setup(UcsChwStatusCheckRegistry::new);
    }

    /**
     * Defines the behavior of the actor when receiving messages.
     * It specifies how to handle different types of commands.
     *
     * @return A Receive instance that defines the message handling logic.
     */
    @Override

    public Receive<Command> createReceive() {
        return newReceiveBuilder()
                .onMessage(CheckChwEligibilityStatus.class, this::onCheckChwEligibility)
                .build();
    }

    private Behavior<Command> onCheckChwEligibility(CheckChwEligibilityStatus command) {
        List<ChwEligibilityResults> response = new CheckChwEligibilityStatusProcessor().checkEligibility(command.eligibleChwCheckRequest,
                command.system.settings().config().getString("chw-status-check-service.database.server-name"),
                command.system.settings().config().getInt("chw-status-check-service.database.port"),
                command.system.settings().config().getString("chw-status-check-service.database.database-name"),
                command.system.settings().config().getString("chw-status-check-service.database.username"),
                command.system.settings().config().getString("chw-status-check-service.database.password")
        );
        command.replyTo().tell(new ActionPerformed(response));
        return this;
    }

    /**
     * This interface represents the protocol for commands that the UcsChwStatusCheckRegistry actor can receive.
     */
    sealed interface Command {
    }

    /**
     * This record represents a command to check the eligibility status of a CHW.
     * It contains the request details, the actor system, and the actor reference to reply to.
     *
     * @param eligibleChwCheckRequest The request containing the details for checking CHW eligibility.
     * @param system                  The actor system.
     * @param replyTo                 The actor reference to reply to with the results.
     */
    public final static record CheckChwEligibilityStatus(
            EligibleChwCheckRequest eligibleChwCheckRequest,
            ActorSystem<?> system,
            ActorRef<ActionPerformed> replyTo) implements Command {
    }

    /**
     * This record represents the action performed result.
     * It contains the list of CHW eligibility results.
     *
     * @param response The list of CHW eligibility results.
     */
    public final static record ActionPerformed(List<ChwEligibilityResults> response) implements Command {
    }

}
