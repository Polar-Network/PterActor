package net.polar;

public interface PterActorPlugin {

    PterActorConfig getConfig();

    default void init() {

    }

    default void shutdown() {

    }

    void reloadConfig();

}
