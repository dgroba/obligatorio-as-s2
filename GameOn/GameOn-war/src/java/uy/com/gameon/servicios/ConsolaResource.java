/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uy.com.gameon.servicios;

import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.ejb.EJB;
import javax.faces.bean.RequestScoped;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import uy.com.gameon.dominio.Consola;
import uy.com.gameon.negocio.ConsolaNegocioSBLocal;

@Path("consolas")
@RequestScoped
public class ConsolaResource {
    
    @EJB
    private ConsolaNegocioSBLocal beanConsola;
    
    @GET
    @Path("/consultar_consolas")
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerConsolas() {
        assert beanConsola != null;
        Map<String, Consola> mapConsolas = beanConsola.obtenerConsolas();
        Gson gson = new Gson();
        List<Consola> consolas = new ArrayList(mapConsolas.values());
        
        return Response.accepted(gson.toJson(consolas)).build();
    }
    
}
