package org.acme.getting.started;

import javax.enterprise.context.RequestScoped;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.inject.Inject;

import org.acme.getting.started.client.ServiceAClient;
import org.acme.getting.started.client.ServiceBClient;

@RequestScoped
@Path("/message")
public class GreetingResource {

    @Inject
    @RestClient
    ServiceAClient serviceA;

    @Inject
    @RestClient
    ServiceBClient serviceB;

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String getMessage() {
        String msg = "Hello. Your message is: ";
        String msgA=null, msgB=null;

        msgA = serviceA != null ? serviceA.getMessage() : "(unknown-A)";
        msgB = serviceB != null ? serviceB.getMessage() : "(unknown-B)";

        return msg + " " + msgA + " " + msgB;
    }
}