package org.wildfly.swarm.examples.messaging.clustering;

import javax.jms.ConnectionFactory;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.Topic;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;

/**
 * @author Bob McWhirter
 */
@Path("/")
public class MyResource {

    @Context
    private UriInfo uri;

    @GET
    @Produces("text/plain")
    public String get() throws NamingException, JMSException {
        InitialContext ctx = new InitialContext();
        ConnectionFactory factory = (ConnectionFactory) ctx.lookup("ConnectionFactory");
        Topic topic = (Topic) ctx.lookup("/jms/topic/my-topic");

        try (JMSContext context = factory.createContext()) {
            context.createProducer().send(topic, "Hello! from " + uri.getBaseUri());
        }
        return "Howdy!";
    }
}
