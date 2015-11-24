/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uy.com.gameon.persistencia;

import java.util.Map;
import javax.ejb.Local;
import uy.com.gameon.dominio.Genero;
import uy.com.gameon.dominio.Usuario;

/**
 *
 * @author diegogroba
 */
@Local
public interface BaseDeDatosSingletonSBLocal {

    Long agregarUsuario(Usuario usuario);
    
    Map<Long, Usuario> obtenerUsuarios();
    
    Usuario obtenerUsuarioPorId(Long idUsuario);

    void agregarGenero(Genero genero);

    Map<String, Genero> obtenerGeneros();
    
}
