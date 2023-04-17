package io.quarkus.playground;

import io.quarkus.arc.impl.Sets;
import io.quarkus.cache.CacheResult;
import java.util.Set;
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class OperationsBean {

    @CacheResult(cacheName = "CACHE")
    public Set<String> getOperations() throws Exception {
        return Sets.of(new String[]{"op1", "op2"});
    }

}
