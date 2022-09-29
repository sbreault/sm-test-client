package org.acme.getting.started.client;

import javax.enterprise.context.RequestScoped;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;


@RequestScoped
@Path("/service-b")
@RegisterRestClient
public interface ServiceBClient {
    
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    String getMessage();    
}
