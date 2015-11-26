/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uy.com.gameon.seguridad;

import java.security.GeneralSecurityException;
import javax.ejb.Local;
import javax.security.auth.login.LoginException;

/**
 *
 * @author diegogroba
 */
@Local
public interface AuthenticatorSBLocal {
    
    String login( String email, String password ) throws LoginException;
    
    boolean authTokenValido( String emailUsuario, String authToken ) throws GeneralSecurityException;
    
    void logout( String emailUsuario, String authToken ) throws GeneralSecurityException;
    
}
