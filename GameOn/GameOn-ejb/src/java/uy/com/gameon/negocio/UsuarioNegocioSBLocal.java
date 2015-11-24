/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uy.com.gameon.negocio;

import javax.ejb.Local;
import uy.com.gameon.dominio.Usuario;

@Local
public interface UsuarioNegocioSBLocal {

    Long registro(String nombre, String apellido, String email);

    Usuario getUsuario(Long idUsuario);

}
