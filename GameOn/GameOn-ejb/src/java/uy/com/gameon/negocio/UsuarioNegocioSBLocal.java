/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uy.com.gameon.negocio;

import javax.ejb.Local;
import uy.com.gameon.dominio.Usuario;
import uy.com.gameon.excepciones.GeneroNoExistenteException;
import uy.com.gameon.excepciones.UsuarioNoExistenteException;

@Local
public interface UsuarioNegocioSBLocal {

    Long registro(String nombre, String apellido, String email);

    Usuario obtenerUsuarioPorEmail(String emailUsuario) throws UsuarioNoExistenteException;

    void agregarFavorito(String email, String generoFavorito) throws GeneroNoExistenteException;

}
