package com.yevhenii.security;

import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.Annotated;
import javax.enterprise.inject.spi.InjectionPoint;

public class AlgorithmProducer {

    @Produces
    @HashAlgorithm
    public HashGenerator produceHashGenerator(InjectionPoint ip) {

        Annotated annotated = ip.getAnnotated();

        HashAlgorithm hashAlgorithm = annotated.getAnnotation(HashAlgorithm.class);

        return new HashGenerator(hashAlgorithm.algorithm().getAlgorithmName());
    }
}
