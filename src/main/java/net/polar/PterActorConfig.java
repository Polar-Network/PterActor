package net.polar;


import net.polar.informer.InformerType;

import java.util.Map;

public record PterActorConfig(
        String pterodactylHost,
        String pterodactylKey,
        Map<Integer, String> secondBasedMessages,
        InformerType informerType
) {

}
