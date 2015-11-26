/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uy.com.gameon.seguridad;

import java.security.GeneralSecurityException;
import java.util.UUID;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.security.auth.login.LoginException;
import uy.com.gameon.persistencia.BaseDeDatosSingletonSBLocal;
import uy.com.gameon.util.Constantes;

/**
 *
 * @author diegogroba
 */
@Stateless
public class AuthenticatorSB implements AuthenticatorSBLocal {

    @EJB
    private BaseDeDatosSingletonSBLocal baseDeDatos;
    
    @Override
    public String login( String email, String password ) throws LoginException {
        if (baseDeDatos.usuarioExistente(email)) {
            if (baseDeDatos.passwordCorrecto(email, password)) {
                if (baseDeDatos.usuarioAutenticado(email)) {
                    throw new LoginException( Constantes.usuarioConSesion );
                } else {
                    String authToken = UUID.randomUUID().toString();
                    baseDeDatos.agregarUsuarioAutenticado(email, authToken);
                    
                    return authToken;
                }
            } else {
                throw new LoginException( Constantes.passIncorrecto );
            }
        } else {
            throw new LoginException( Constantes.usuarionNoReg );
        } 
    }
 
    @Override
    public boolean authTokenValido( String emailUsuario, String authToken ) throws GeneralSecurityException {
        if (baseDeDatos.usuarioExistente(emailUsuario)) {
            return baseDeDatos.authTokenValido(authToken, emailUsuario);
        } else {
            throw new LoginException( Constantes.usuarionNoReg );
        }
    }
 
    @Override
    public void logout( String emailUsuario, String authToken ) throws GeneralSecurityException {
        if (baseDeDatos.usuarioExistente(emailUsuario)) {
            if (baseDeDatos.usuarioAutenticado(emailUsuario)) {
                baseDeDatos.agregarUsuarioAutenticado(emailUsuario, authToken);
            } else {
                throw new LoginException( Constantes.usuarionSinSesion );
            }
        } else {
            throw new LoginException( Constantes.usuarionNoReg );
        } 
    }
    
}
