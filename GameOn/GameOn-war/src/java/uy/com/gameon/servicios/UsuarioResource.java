/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uy.com.gameon.servicios;

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
import uy.com.gameon.excepciones.GeneroNoExistenteException;
import uy.com.gameon.excepciones.UsuarioNoExistenteException;
import uy.com.gameon.negocio.UsuarioNegocioSBLocal;

@Path("usuarios")
@RequestScoped
public class UsuarioResource {
    
    @EJB
    private UsuarioNegocioSBLocal beanUsuario;

    @POST
    @Path("/registro_usuario")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response registro(Usuario usuario) {
        URI uriOfCreatedResource;
        
        beanUsuario.registro(usuario.getNombre(), usuario.getApellido(), usuario.getEmail());
        uriOfCreatedResource = URI.create("/GameOn-war/usuarios/" + usuario.getEmail());
        
        return Response.created(uriOfCreatedResource).build();
    }
    
    @POST
    @Path("/agregar_favorito")
    @Consumes("application/x-www-form-urlencoded")
    public Response agregarFavorito(@FormParam("email") String email,
                                @FormParam("generoFavorito") String favorito){
        Usuario usuario;
        try {
            beanUsuario.agregarFavorito(email, favorito);
            usuario = beanUsuario.obtenerUsuarioPorEmail(email);
            
            return Response.ok(usuario).build();
        } catch (GeneroNoExistenteException | UsuarioNoExistenteException ex) {
            Logger.getLogger(UsuarioResource.class.getName()).log(Level.SEVERE, null, ex);
            
            return Response.serverError().build();
        }
    }
    
    @GET
    @Path("/{emailUsuario}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerUsuarioPorEmail(@PathParam("emailUsuario") String emailUsuario) {
        Usuario usuario;
        
        try {
            usuario = beanUsuario.obtenerUsuarioPorEmail(emailUsuario);
            
            return Response.accepted(usuario).build();
        } catch (UsuarioNoExistenteException ex) {
            Logger.getLogger(UsuarioResource.class.getName()).log(Level.SEVERE, null, ex);
            
            return Response.serverError().build();
        }
            
    }
    
}
