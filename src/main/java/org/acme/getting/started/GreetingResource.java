package org.acme.getting.started;

import javax.enterprise.context.RequestScoped;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.eclipse.microprofile.config.ConfigProvider;
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

    private static String HOSTNAME;

    String appVersion = ConfigProvider.getConfig().getValue("app.version", String.class);

    @Inject
    @RestClient
    ServiceAClient serviceA;

    @Inject
    @RestClient
    ServiceBClient serviceB;

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String getMessage() {
        String msg = "Hello. Your message is: (" + getIdentification() + "): ";
        String msgA=null, msgB=null;

        msgA = serviceA != null ? serviceA.getMessage() : "(unknown-A)";
        msgB = serviceB != null ? serviceB.getMessage() : "(unknown-B)";

        return msg + " " + msgA + " " + msgB;
    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("health")
    public String getHealth(){
        return "ok - up and running";
    }

    private String getIdentification(){
        return appVersion + "; " + getHostName();
    }

    private String getHostName(){
        if(GreetingResource.HOSTNAME == null){
            try{    
                GreetingResource.HOSTNAME = System.getenv("HOSTNAME");
                if(GreetingResource.HOSTNAME == null) 
                    GreetingResource.HOSTNAME = System.getenv("COMPUTERNAME");  //Windows desktop name
                
                System.out.println("HOSTNAME=" + GreetingResource.HOSTNAME);
                
                //max 128 char
                if(GreetingResource.HOSTNAME != null && GreetingResource.HOSTNAME.length() > 128){                    
                    GreetingResource.HOSTNAME = GreetingResource.HOSTNAME.substring(0, 127);
                }                
            }catch(Exception e){
                e.printStackTrace();
            }
        }

        if(GreetingResource.HOSTNAME == null) GreetingResource.HOSTNAME = "/local PC/not hosted";
        return GreetingResource.HOSTNAME;
    }      
}