/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uy.com.gameon.servicios;

import com.google.gson.Gson;
import java.net.URI;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.faces.bean.RequestScoped;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import uy.com.gameon.dominio.Usuario;
import uy.com.gameon.excepciones.ConsolaNoExistenteException;
import uy.com.gameon.excepciones.GeneroNoExistenteException;
import uy.com.gameon.excepciones.UsuarioNoExistenteException;
import uy.com.gameon.negocio.UsuarioNegocioSBLocal;

@Path("usuarios")
@RequestScoped
public class UsuarioResource {
    
    @EJB
    private UsuarioNegocioSBLocal beanUsuario;

    @POST
    @Path("/agregar_favorito")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public Response agregarFavorito(@FormParam("email") String email,
                                @FormParam("generoFavorito") String favorito){
        Usuario usuario;
        Gson gson = new Gson();
        try {
            beanUsuario.agregarFavorito(email, favorito);
            usuario = beanUsuario.obtenerUsuarioPorEmail(email);
            
            return Response.ok(gson.toJson(usuario)).build();
        } catch (GeneroNoExistenteException | UsuarioNoExistenteException ex) {
            Logger.getLogger(UsuarioResource.class.getName()).log(Level.SEVERE, ex.getMessage());
           
            return Response.notAcceptable(null).build();
        }
    }
    
    @POST
    @Path("/agregar_consola")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public Response agregarConsola(@FormParam("email") String email,
                                @FormParam("consola") String codConsola){
        Usuario usuario;
        Gson gson = new Gson();
        try {
            beanUsuario.agregarConsola(email, codConsola);
            usuario = beanUsuario.obtenerUsuarioPorEmail(email);
            
            return Response.ok(gson.toJson(usuario)).build();
        } catch (ConsolaNoExistenteException | UsuarioNoExistenteException ex) {
            Logger.getLogger(UsuarioResource.class.getName()).log(Level.SEVERE, ex.getMessage());
            
            return Response.notAcceptable(null).build();
        }
    }
    
    @GET
    @Path("/{emailUsuario}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerUsuarioPorEmail(@PathParam("emailUsuario") String emailUsuario) {
        Usuario usuario;
        Gson gson = new Gson();
        try {
            usuario = beanUsuario.obtenerUsuarioPorEmail(emailUsuario);
            
            return Response.accepted(gson.toJson(usuario)).build();
        } catch (UsuarioNoExistenteException ex) {
            Logger.getLogger(UsuarioResource.class.getName()).log(Level.SEVERE, ex.getMessage());
            
            return Response.noContent().build();
        }     
    }
    
    @POST
    @Path("/registro_usuario")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response registro(@FormParam("email") String email,
                             @FormParam("nombre") String nombre,
                             @FormParam("apellido") String apellido) {
        URI uriOfCreatedResource;
        
        beanUsuario.registro(apellido, apellido, email);
        uriOfCreatedResource = URI.create("/GameOn-war/usuarios/" + email);
        
        return Response.created(uriOfCreatedResource).build();
    }
    
}
