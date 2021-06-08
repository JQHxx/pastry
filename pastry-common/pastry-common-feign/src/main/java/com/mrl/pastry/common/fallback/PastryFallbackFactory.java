package com.mrl.pastry.common.fallback;

import feign.Target;
import feign.hystrix.FallbackFactory;
import org.springframework.cglib.proxy.Enhancer;

/**
 * Common fallback factory
 *
 * @author MrL
 * @version 1.0
 * @date 2021/4/26
 */
public class PastryFallbackFactory<T> implements FallbackFactory<T> {

    private final Target<T> target;

    public PastryFallbackFactory(Target<T> target) {
        this.target = target;
    }

    @Override
    public T create(Throwable cause) {
        final Class<T> targetType = target.type();
        final String targetName = target.name();

        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(targetType);
        enhancer.setUseCache(true);
        enhancer.setCallback(new PastryFeignFallback<>(targetType, targetName, cause));
        return (T) enhancer.create();
    }
}
