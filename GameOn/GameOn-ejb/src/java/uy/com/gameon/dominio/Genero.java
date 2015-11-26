/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uy.com.gameon.dominio;

import java.util.Objects;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Genero {
    
    private String codGenero;
    private String nombre;

    public Genero() {
    }

    public Genero(String codGenero, String nombre) {
        this.codGenero = codGenero;
        this.nombre = nombre;
    }

    public Genero(String codGenero) {
        this.codGenero = codGenero;
    }

    public String getCodGenero() {
        return codGenero;
    }

    public void setCodGenero(String codGenero) {
        this.codGenero = codGenero;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public String toString() {
        return "[" + this.codGenero + " - " + this.nombre + "] ";
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + Objects.hashCode(this.codGenero);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Genero other = (Genero) obj;
        if (!Objects.equals(this.codGenero, other.codGenero)) {
            return false;
        }
        return true;
    }
    
}
