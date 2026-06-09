package com.yellowfinbi.api.common;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Supplier;

/**
 * Global factory registry mapping interface types to creation functions.
 * Mirrors YFFactoryRegistry from C# and TYFFactoryRegistry from Delphi.
 *
 * <pre>
 *   YFFactoryRegistry.registerFactory(IYFUser.class, YFUser::new);
 *   IYFUser user = YFFactoryRegistry.createNew(IYFUser.class);
 * </pre>
 */
public final class YFFactoryRegistry {

    private static final Map<Class<?>, Supplier<?>> FACTORIES = new ConcurrentHashMap<>();

    private YFFactoryRegistry() { }

    public static <T> void registerFactory(Class<T> type, Supplier<T> factory) {
        FACTORIES.put(type, factory);
    }

    @SuppressWarnings("unchecked")
    public static <T> Supplier<T> resolveFactory(Class<T> type) {
        Supplier<?> factory = FACTORIES.get(type);
        if (factory == null) {
            throw new IllegalStateException("No factory registered for " + type.getName()
                    + ". Call YFFactoryRegistration.registerAll() at startup.");
        }
        return (Supplier<T>) factory;
    }

    @SuppressWarnings("unchecked")
    public static <T> T createNew(Class<T> type) {
        return (T) resolveFactory(type).get();
    }
}
