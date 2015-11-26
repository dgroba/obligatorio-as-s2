/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uy.com.gameon.negocio;

import java.util.Enumeration;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

/**
 *
 * @author diegogroba
 */
@MessageDriven(activationConfig = {
    @ActivationConfigProperty(propertyName = "destinationLookup", propertyValue = "jms/Topic"),
    @ActivationConfigProperty(propertyName = "subscriptionName", propertyValue = "jms/Topic"),
    @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Topic")
})
public class AdministracionMessageBean implements MessageListener {
    
    public AdministracionMessageBean() {
    }
    
    @Override
    public void onMessage(Message message) {
        try {
            //Verifico el tipo de mensaje
            if (message instanceof MapMessage) {
                MapMessage mapMessage = (MapMessage) message;
                //Asigno el procesamiento del mensaje
                Enumeration<String> mapNames = mapMessage.getMapNames();
                String request = null;
                while (mapNames.hasMoreElements()) {
                    // Read all of the map elements as strings
                    String name = mapNames.nextElement();
                    request += name + ": " + mapMessage.getString(name) + " | ";
                }
                System.out.println(this.getClass().getName() + ": Received a request for " + message.getStringProperty("Genero") + " & " + message.getStringProperty("Consola"));
                System.out.println(request);
            }
        } catch (JMSException ex) {
            Logger.getLogger(AdministracionMessageBean.class.getName()).log(Level.SEVERE, ex.getMessage());
        }
    }
    
}
