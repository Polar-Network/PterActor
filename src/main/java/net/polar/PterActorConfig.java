package net.polar;

import net.polar.informer.InformerType;

import java.util.Map;

public record PterActorConfig(
        String pterodactylHost,
        String pterodactylKey,
        String primaryServerId,
        Map<Integer, String> shutdownMessages,
        int shutdownInterval,
        InformerType informerType
) {

}
