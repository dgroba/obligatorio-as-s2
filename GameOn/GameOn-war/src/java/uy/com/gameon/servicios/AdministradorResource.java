/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uy.com.gameon.servicios;

import com.google.gson.Gson;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.RequestScoped;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Session;
import javax.jms.Topic;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author diegogroba
 */
@Path("administrador")
@RequestScoped
public class AdministradorResource {
    
    @POST
    @Path("/agregar_novedad")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public Response agregarFavorito(@FormParam("genero") String genero,
                                @FormParam("consola") String consola,
                                @FormParam("descripcion") String descripcion,
                                @Context HttpHeaders httpHeaders){
        try {
            Gson gson = new Gson();
            //Seteo las Properties para el contexto
            Properties props = new Properties(); 
            props.setProperty("java.naming.factory.initial", "com.sun.enterprise.naming.SerialInitContextFactory");
            props.setProperty("java.naming.factory.url.pkgs", "com.sun.enterprise.naming");
            props.setProperty("java.naming.factory.state", "com.sun.corba.ee.impl.presentation.rmi.JNDIStateFactoryImpl");
            props.setProperty("org.omg.CORBA.ORBInitialHost", "localhost");
            props.setProperty("org.omg.CORBA.ORBInitialPort", "3700");
            //Creo el Contexto para obtener los recursos del servidor 
            InitialContext ic = new InitialContext(props);
            // Obtenemos a traves del servicio JNDI la ConnectionFactory del // servidor de aplicaciones
            ConnectionFactory connectionFactory = (ConnectionFactory) ic.lookup("jms/topicFactory"); // Obtenemos a traves del servicio JNDI la "destination" que vamos
            // a utilizar, en este caso un Topic
            Topic queue = (Topic) ic.lookup("jms/Topic");
            //Creo la Connection mediante la ConnectionFactory
            Connection connection = connectionFactory.createConnection();
            //Creo la Session mediante la Connection
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            //Creo la MessageProducer mediante la Session
            javax.jms.MessageProducer messageProducer = session.createProducer(queue);
            
            MapMessage mm = session.createMapMessage();
            mm.setString("Descripcion", descripcion);
            mm.setStringProperty("Genero", genero);
            mm.setStringProperty("Consola", consola);
            
            messageProducer.send(mm);
            
            return Response.ok(gson.toJson("Novedad enviada a Tópico")).build();
        } catch (NamingException | JMSException ex) {
            Logger.getLogger(AdministradorResource.class.getName()).log(Level.SEVERE, ex.getMessage());
            JsonObjectBuilder jsonObjBuilder = Json.createObjectBuilder();
            JsonObject jsonObj;
            jsonObjBuilder.add( "message", ex.getMessage() );
            jsonObj = jsonObjBuilder.build();
            
            return Response.notAcceptable(null).entity(jsonObj.toString()).build();
        }
       
    }
    
}
