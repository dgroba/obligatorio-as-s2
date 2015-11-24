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
import uy.com.gameon.dominio.Genero;
import uy.com.gameon.negocio.GeneroNegocioSBLocal;

@Path("generos")
@RequestScoped
public class GeneroResource {
    
    @EJB
    private GeneroNegocioSBLocal beanGenero;
    
    @GET
    @Path("/consultar_generos")
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerGeneros() {
        Map<String, Genero> mapGeneros = beanGenero.obtenerGeneros();
        Gson gson = new Gson();
        List<Genero> generos = new ArrayList(mapGeneros.values());
        return Response.accepted(gson.toJson(generos)).build();
    }
    
}
