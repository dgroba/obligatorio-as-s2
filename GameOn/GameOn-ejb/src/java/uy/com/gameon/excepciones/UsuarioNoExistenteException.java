/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uy.com.gameon.excepciones;

public class UsuarioNoExistenteException extends Exception {

    private String codError;
    
    /**
     * Creates a new instance of <code>UsuarioNoExistenteException</code>
     * without detail message.
     */
    public UsuarioNoExistenteException() {
    }

    /**
     * Constructs an instance of <code>UsuarioNoExistenteException</code> with
     * the specified detail message.
     *
     * @param msg the detail message.
     */
    public UsuarioNoExistenteException(String msg) {
        super(msg);
    }

    public UsuarioNoExistenteException(String codError, String message) {
        super(message);
        this.codError = codError;
    }

    public String getCodError() {
        return codError;
    }

    public void setCodError(String codError) {
        this.codError = codError;
    }
    
}
