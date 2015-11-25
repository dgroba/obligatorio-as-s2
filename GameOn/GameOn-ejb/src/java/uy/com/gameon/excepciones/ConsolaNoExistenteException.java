/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uy.com.gameon.excepciones;

public class ConsolaNoExistenteException extends Exception {
    
    private String codError;
    /**
     * Creates a new instance of <code>ConsolaNoExistenteException</code>
     * without detail message.
     */
    public ConsolaNoExistenteException() {
    }

    /**
     * Constructs an instance of <code>ConsolaNoExistenteException</code> with
     * the specified detail message.
     *
     * @param msg the detail message.
     */
    public ConsolaNoExistenteException(String msg) {
        super(msg);
    }

    public ConsolaNoExistenteException(String codError, String message) {
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
