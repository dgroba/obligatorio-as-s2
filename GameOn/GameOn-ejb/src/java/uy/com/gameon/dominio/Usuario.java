/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uy.com.gameon.dominio;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Usuario {
    private Long id;
    private String nombre;
    private String apellido;
    private String email;
    private List<Consola> consolas;
    private List<Genero> preferenciasGenero;

    public Usuario() {
        this.consolas = new ArrayList<>();
        this.preferenciasGenero = new ArrayList<>();
    }

    public Usuario(String nombre, String apellido, String email) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
        this.consolas = new ArrayList<>();
        this.preferenciasGenero = new ArrayList<>();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Consola> getConsolas() {
        return consolas;
    }

    public void setConsolas(List<Consola> consolas) {
        this.consolas = consolas;
    }

    public List<Genero> getPreferenciasGenero() {
        return preferenciasGenero;
    }

    public void setPreferenciasGenero(List<Genero> preferenciasGenero) {
        this.preferenciasGenero = preferenciasGenero;
    }
}
