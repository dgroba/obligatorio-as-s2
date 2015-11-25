/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uy.com.gameon.dominio;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Consola {
    
    private String codigo;
    private String nombre;
    private String fabricante;

    public Consola(String codigo, String nombre, String fabricante) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.fabricante = fabricante;
    }
    
    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getFabricante() {
        return fabricante;
    }

    public void setFabricante(String fabricante) {
        this.fabricante = fabricante;
    }
}
