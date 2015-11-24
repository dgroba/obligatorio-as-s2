/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uy.com.gameon.negocio;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import uy.com.gameon.dominio.Usuario;
import uy.com.gameon.persistencia.BaseDeDatosSingletonSBLocal;

@Stateless
public class UsuarioNegocioSB implements UsuarioNegocioSBLocal {
    
    @EJB
    private BaseDeDatosSingletonSBLocal baseDeDatos;
    
    @Override
    public Long registro(String nombre, String apellido, String email) {
        return baseDeDatos.agregarUsuario(new Usuario(nombre, apellido, email));
    }

    @Override
    public Usuario getUsuario(Long idUsuario) {
        return baseDeDatos.obtenerUsuarioPorId(idUsuario);
    }

}
