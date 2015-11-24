/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uy.com.gameon.negocio;

import java.util.Map;
import javax.ejb.Local;
import uy.com.gameon.dominio.Genero;

@Local
public interface GeneroNegocioSBLocal {
    Map<String, Genero> obtenerGeneros();
}
