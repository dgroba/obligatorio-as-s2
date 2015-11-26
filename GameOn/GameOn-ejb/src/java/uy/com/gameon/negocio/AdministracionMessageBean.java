/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uy.com.gameon.negocio;

import java.util.Enumeration;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.EJB;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.mail.Address;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import uy.com.gameon.dominio.Usuario;

@MessageDriven(activationConfig = {
    @ActivationConfigProperty(propertyName = "destinationLookup", propertyValue = "jms/Topic"),
    @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Topic")
})
public class AdministracionMessageBean implements MessageListener {
    
    @EJB
    private UsuarioNegocioSBLocal beanUsuario;
    
    public AdministracionMessageBean() {
    }
    
    @Override
    public void onMessage(Message message) {
        List<Usuario> usuariosAComunicar;
        
        try {
            //Verifico el tipo de mensaje
            if (message instanceof MapMessage) {
                MapMessage mapMessage = (MapMessage) message;
                //Asigno el procesamiento del mensaje
                Enumeration<String> mapNames = mapMessage.getMapNames();
                String request = "";
                while (mapNames.hasMoreElements()) {
                    // Read all of the map elements as strings
                    String name = mapNames.nextElement();
                    request += name + ": " + mapMessage.getString(name) + " | ";
                }
                
                assert beanUsuario != null;
                usuariosAComunicar = beanUsuario.obtenerUsuariosPorConsolaGenero(message.getStringProperty("Consola"), message.getStringProperty("Genero"));
                System.out.println(this.getClass().getName() + ": Received a request for " + message.getStringProperty("Genero") + " & " + message.getStringProperty("Consola"));
                
                Address[] addresses = null;
                int contador = 0;
                
                if (!usuariosAComunicar.isEmpty()) {
                    addresses = new Address[usuariosAComunicar.size()];
                    for (Usuario usuarioAComunicar : usuariosAComunicar) {
                        addresses[contador] = new InternetAddress(usuarioAComunicar.getEmail());
                        System.out.println(usuarioAComunicar.getEmail() + ", ");
                        contador++;
                    }
                }

                // Get system properties
                Properties properties = System.getProperties();

                // Setup mail server
                properties.put("mail.smtp.auth", "true");
                properties.put("mail.smtp.starttls.enable", "true");
                properties.put("mail.smtp.host", "smtp.gmail.com");
                properties.put("mail.smtp.port", "587");
                
                // Get the default Session object.
                Session session = crearSesion(properties);

                // Create a default MimeMessage object.
                MimeMessage mimeMessage = new MimeMessage(session);

                // Set Subject: header field
                mimeMessage.setSubject("gameOn News!");

                // Now set the actual mimeMessage
                mimeMessage.setText(request);

                // Send mimeMessage
                if (addresses != null){
                    Transport.send(mimeMessage, addresses);
                    System.out.println("Sent message successfully.");
                }
                System.out.println("No existen usuario que posean las consolas o géneros ingresados.");
            }   
        } catch (JMSException | MessagingException ex) {
            Logger.getLogger(AdministracionMessageBean.class.getName()).log(Level.SEVERE, ex.getMessage());
        }
    }
    
    private static Session crearSesion(Properties properties) {
        String userName = "gameonuy@gmail.com";
        String password = "obligatorio2015";
                
        Session session = Session.getInstance(properties, 
                new javax.mail.Authenticator() {
                    @Override
                    protected PasswordAuthentication getPasswordAuthentication(){
                        return new PasswordAuthentication(userName, password);
                    }
                            
                });
        return session;
    }
    
}
