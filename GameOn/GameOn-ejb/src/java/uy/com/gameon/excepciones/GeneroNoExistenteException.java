/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uy.com.gameon.excepciones;

public class GeneroNoExistenteException extends Exception {

    private String codError;
    
    /**
     * Creates a new instance of <code>GeneroNoExistenteException</code> without
     * detail message.
     */
    public GeneroNoExistenteException() {
    }

    /**
     * Constructs an instance of <code>GeneroNoExistenteException</code> with
     * the specified detail message.
     *
     * @param msg the detail message.
     */
    public GeneroNoExistenteException(String msg) {
        super(msg);
    }
    
    public GeneroNoExistenteException(String codError, String msg) {
        super(msg);
        this.codError = codError;
    }

    public String getCodError() {
        return codError;
    }

    public void setCodError(String codError) {
        this.codError = codError;
    }
    
}
