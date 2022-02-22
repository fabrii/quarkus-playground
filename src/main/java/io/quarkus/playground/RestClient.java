package io.quarkus.playground;

import java.io.Serializable;
import javax.annotation.PreDestroy;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import javax.ws.rs.core.UriBuilder;
import org.eclipse.microprofile.rest.client.RestClientBuilder;

@Named
@RequestScoped
public class RestClient implements Serializable {

    private ClientInterface proxy;
    
    public RestClient() {
        
        proxy = RestClientBuilder.newBuilder()
                    .baseUri(UriBuilder.fromPath("http://localhost:8888").build())
                    .register(new AuthHeaderRequestFilter())
                    .build(ClientInterface.class);
     
    }

    public boolean isContainedInitialized(long id) {
        return proxy.isContainedInitialized(id);
    }
    
    @PreDestroy
    public void predestroy(){
        try {
            proxy.close();
        } catch (Exception ex){}
        
    }

}
