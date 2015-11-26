/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uy.com.gameon.negocio;

import java.util.Map;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import uy.com.gameon.dominio.Consola;
import uy.com.gameon.persistencia.BaseDeDatosSingletonSBLocal;

@Stateless
public class ConsolaNegocioSB implements ConsolaNegocioSBLocal {
    
    @EJB
    private BaseDeDatosSingletonSBLocal baseDeDatos;

    @Override
    public Map<String, Consola> obtenerConsolas() {
        assert baseDeDatos != null;
        return baseDeDatos.obtenerConsolas();
    }
 
}
