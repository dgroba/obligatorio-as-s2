/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uy.com.gameon.persistencia;

import java.util.Map;
import javax.ejb.Local;
import uy.com.gameon.dominio.Consola;
import uy.com.gameon.dominio.Genero;
import uy.com.gameon.dominio.Usuario;

/**
 *
 * @author diegogroba
 */
@Local
public interface BaseDeDatosSingletonSBLocal {

    Long agregarUsuario(Usuario usuario);
    
    Map<String, Usuario> obtenerUsuarios();
    
    Usuario obtenerUsuarioPorEmail(String emailUsuario);

    void agregarGenero(Genero genero);

    Map<String, Genero> obtenerGeneros();

    void agregarConsola(Consola consola);

    Map<String, Consola> obtenerConsolas();

    void agregarUsuarioAutenticado(String emailUsuario, String authKey);

    void quitarUsuarioAutenticado(String emailUsuario);

    Boolean usuarioAutenticado(String emailUsuairo);

    Boolean usuarioExistente(String emailUsuario);

    Boolean passwordCorrecto(String emailUsuario, String password);

    Boolean authTokenValido(String authToken, String emailUsuario);
    
}
