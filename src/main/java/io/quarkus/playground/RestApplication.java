package io.quarkus.playground;

import io.smallrye.common.annotation.Blocking;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

@ApplicationPath("/")
@Blocking
public class RestApplication extends Application {

    public RestApplication() {
    }
}
