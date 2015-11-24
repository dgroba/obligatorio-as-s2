/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uy.com.gameon.servicios;

import java.net.URI;
import javax.ejb.EJB;
import javax.faces.bean.RequestScoped;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import uy.com.gameon.dominio.Usuario;
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
        Long idUsuario;
        URI uriOfCreatedResource;
        
        idUsuario = beanUsuario.registro(usuario.getNombre(), usuario.getApellido(), usuario.getEmail());
        uriOfCreatedResource = URI.create("/GameOn-war/usuarios/" + idUsuario);
        
        return Response.created(uriOfCreatedResource).build();
    }
    
    @GET
    @Path("/{idUsuario}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerUsuarioPorId(@PathParam("idUsuario") String idUsuario) {
        if (idUsuario != null) {
            Long idUsuarioLong = new Long(idUsuario);
            Usuario usuario = beanUsuario.getUsuario(idUsuarioLong);
            return Response.accepted(usuario).build();
        } else {
            return Response.serverError().build();
        }
    }
    
}
