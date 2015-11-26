/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uy.com.gameon.servicios;

import com.google.gson.Gson;
import java.net.URI;
import java.security.GeneralSecurityException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.faces.bean.RequestScoped;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.security.auth.login.LoginException;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import uy.com.gameon.dominio.Usuario;
import uy.com.gameon.excepciones.ConsolaNoExistenteException;
import uy.com.gameon.excepciones.GeneroNoExistenteException;
import uy.com.gameon.excepciones.UsuarioNoExistenteException;
import uy.com.gameon.negocio.UsuarioNegocioSBLocal;
import uy.com.gameon.seguridad.AuthenticatorSBLocal;
import uy.com.gameon.util.Constantes;

@Path("usuarios")
@RequestScoped
public class UsuarioResource {
    
    @EJB
    private UsuarioNegocioSBLocal beanUsuario;
    @EJB
    private AuthenticatorSBLocal beanAuthenticator;
    
    @POST
    @Path("/agregar_favorito")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public Response agregarFavorito(@FormParam("email") String email,
                                @FormParam("generoFavorito") String favorito,
                                @Context HttpHeaders httpHeaders){
        JsonObjectBuilder jsonObjBuilder = Json.createObjectBuilder();
        JsonObject jsonObj;
        String authToken = httpHeaders.getHeaderString( HTTPHeaderNames.AUTH_TOKEN );
        Usuario usuario;
        Gson gson = new Gson();
        
        try {
            assert beanAuthenticator != null;
            if (beanAuthenticator.authTokenValido(email, authToken)) {
                assert beanUsuario != null;
                beanUsuario.agregarFavorito(email, favorito);
                usuario = beanUsuario.obtenerUsuarioPorEmail(email);
                
                return Response.ok(gson.toJson(usuario)).build();
            } else {
                jsonObjBuilder.add( "message", Constantes.codAutenInvalido );
                jsonObj = jsonObjBuilder.build();
                
                return Response.status(Response.Status.UNAUTHORIZED).entity(jsonObj.toString()).build();
            }
        } catch (GeneroNoExistenteException | UsuarioNoExistenteException ex) {
            Logger.getLogger(UsuarioResource.class.getName()).log(Level.SEVERE, ex.getMessage());
            jsonObjBuilder.add( "message", ex.getMessage() );
            jsonObj = jsonObjBuilder.build();
            
            return Response.notAcceptable(null).entity(jsonObj.toString()).build();
        } catch (GeneralSecurityException ex) {
            jsonObjBuilder.add( "message", ex.getMessage() );
            jsonObj = jsonObjBuilder.build();
            Logger.getLogger(UsuarioResource.class.getName()).log(Level.SEVERE, ex.getMessage());
            
            return Response.status(Response.Status.UNAUTHORIZED).entity(jsonObj.toString()).build();
        }
       
    }
    
    @POST
    @Path("/agregar_consola")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public Response agregarConsola(@FormParam("email") String email,
                                @FormParam("consola") String codConsola,
                                @Context HttpHeaders httpHeaders){
        Usuario usuario;
        Gson gson = new Gson();
        JsonObjectBuilder jsonObjBuilder = Json.createObjectBuilder();
        JsonObject jsonObj;
        String authToken = httpHeaders.getHeaderString( HTTPHeaderNames.AUTH_TOKEN );
        
        try {
            assert beanAuthenticator != null;
            if (beanAuthenticator.authTokenValido(email, authToken)) {
                assert beanUsuario != null;
                beanUsuario.agregarConsola(email, codConsola);
                usuario = beanUsuario.obtenerUsuarioPorEmail(email);

                return Response.ok(gson.toJson(usuario)).build();
            } else {
                jsonObjBuilder.add( "message", Constantes.codAutenInvalido );
                jsonObj = jsonObjBuilder.build();
                
                return Response.status(Response.Status.UNAUTHORIZED).entity(jsonObj.toString()).build();
            }
        } catch (ConsolaNoExistenteException | UsuarioNoExistenteException ex) {
            Logger.getLogger(UsuarioResource.class.getName()).log(Level.SEVERE, ex.getMessage());
            jsonObjBuilder.add( "message", ex.getMessage() );
            jsonObj = jsonObjBuilder.build();
            
            return Response.notAcceptable(null).entity(jsonObj.toString()).build();
        } catch (GeneralSecurityException ex) {
            jsonObjBuilder.add( "message", ex.getMessage() );
            jsonObj = jsonObjBuilder.build();
            Logger.getLogger(UsuarioResource.class.getName()).log(Level.SEVERE, ex.getMessage());
            
            return Response.status(Response.Status.UNAUTHORIZED).entity(jsonObj.toString()).build();
        }
    }
    
    @GET
    @Path("/{emailUsuario}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerUsuarioPorEmail(@Context HttpHeaders httpHeaders,
                                            @PathParam("emailUsuario") String emailUsuario) {
        Usuario usuario;
        Gson gson = new Gson();
        String authToken = httpHeaders.getHeaderString( HTTPHeaderNames.AUTH_TOKEN );
        JsonObjectBuilder jsonObjBuilder = Json.createObjectBuilder();
        JsonObject jsonObj;
        
        try {
            assert beanAuthenticator != null;
            if (beanAuthenticator.authTokenValido(emailUsuario, authToken)) {
                assert beanUsuario != null;
                usuario = beanUsuario.obtenerUsuarioPorEmail(emailUsuario);
                
                return Response.accepted(gson.toJson(usuario)).build();
            } else {
                jsonObjBuilder.add( "message", Constantes.codAutenInvalido );
                jsonObj = jsonObjBuilder.build();
                
                return Response.status(Response.Status.UNAUTHORIZED).entity(jsonObj.toString()).build();
            }
        } catch (UsuarioNoExistenteException ex) {
            jsonObjBuilder.add( "message", ex.getMessage() );
            jsonObj = jsonObjBuilder.build();
            Logger.getLogger(UsuarioResource.class.getName()).log(Level.SEVERE, ex.getMessage());
            
            return Response.noContent().entity(jsonObj.toString()).build();
        } catch (GeneralSecurityException ex) {
            jsonObjBuilder.add( "message", ex.getMessage() );
            jsonObj = jsonObjBuilder.build();
            Logger.getLogger(UsuarioResource.class.getName()).log(Level.SEVERE, ex.getMessage());
            
            return Response.status(Response.Status.UNAUTHORIZED).entity(jsonObj.toString()).build();
        }     
    }
    
    @POST
    @Path("/registro_usuario")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response registro(@FormParam("email") String email,
                             @FormParam("nombre") String nombre,
                             @FormParam("apellido") String apellido,
                             @FormParam("password") String password) {
        URI uriOfCreatedResource;
        JsonObjectBuilder jsonObjBuilder = Json.createObjectBuilder();
        JsonObject jsonObj;
        
        jsonObjBuilder.add( "message", "Usuario registrado correctamente." );
        jsonObj = jsonObjBuilder.build();
        
        assert beanUsuario != null;
        beanUsuario.registro(apellido, apellido, email, password);
        uriOfCreatedResource = URI.create("/GameOn-war/usuarios/" + email);
        
        return Response.created(uriOfCreatedResource).entity(jsonObj.toString()).build();
    }
    
    @POST
    @Path("/login")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public Response login(@FormParam("email") String email,
                            @FormParam("password") String password){
        try {
            Gson gson = new Gson();
            
            assert beanAuthenticator != null;
            String authToken = beanAuthenticator.login(email, password);
            
            return Response.ok(gson.toJson("auth_token: " + authToken)).build();
        } catch (LoginException ex) {
            Logger.getLogger(UsuarioResource.class.getName()).log(Level.SEVERE, ex.getMessage());
            JsonObjectBuilder jsonObjBuilder = Json.createObjectBuilder();
            jsonObjBuilder.add( "message", ex.getMessage() );
            JsonObject jsonObj = jsonObjBuilder.build();
            
            return Response.status(Response.Status.UNAUTHORIZED).entity(jsonObj.toString()).build();
        }
    }
    
    @POST
    @Path("/logout")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public Response logout(@Context HttpHeaders httpHeaders,
                            @FormParam("email") String email){
        JsonObjectBuilder jsonObjBuilder; 
        JsonObject jsonObj;
        
        try {
            Gson gson = new Gson();
            String authToken = httpHeaders.getHeaderString( HTTPHeaderNames.AUTH_TOKEN );
            
            assert beanAuthenticator != null;
            beanAuthenticator.logout(email, authToken);jsonObjBuilder = Json.createObjectBuilder();
            jsonObjBuilder.add( "message", "Usuario: " + email + " ha culminado su sesión.");
            jsonObj = jsonObjBuilder.build();
            
            return Response.ok(gson.toJson(jsonObj)).build();
        } catch (LoginException ex) {
            Logger.getLogger(UsuarioResource.class.getName()).log(Level.SEVERE, ex.getMessage());
            jsonObjBuilder = Json.createObjectBuilder();
            jsonObjBuilder.add( "message", ex.getMessage() );
            jsonObj = jsonObjBuilder.build();
            
            return Response.status(Response.Status.UNAUTHORIZED).entity(jsonObj.toString()).build();
        } catch (GeneralSecurityException ex) {
            jsonObjBuilder = Json.createObjectBuilder();
            jsonObjBuilder.add( "message", ex.getMessage() );
            jsonObj = jsonObjBuilder.build();
            Logger.getLogger(UsuarioResource.class.getName()).log(Level.SEVERE, ex.getMessage());
            
            return Response.status(Response.Status.UNAUTHORIZED).entity(jsonObj.toString()).build();
        }
    }
    
}
