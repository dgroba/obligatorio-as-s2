/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uy.com.gameon.persistencia;

import java.util.HashMap;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import uy.com.gameon.dominio.Genero;
import uy.com.gameon.dominio.Usuario;

/**
 *
 * @author diegogroba
 */
@Singleton
public class BaseDeDatosSingletonSB implements BaseDeDatosSingletonSBLocal {
    public Map<String, Usuario> usuarios;
    public Long contadorIdUsuarios;
    public Map<String, Genero> generos;

    public BaseDeDatosSingletonSB() {
        this.contadorIdUsuarios = new Long(0);
        this.usuarios = new HashMap<>();
        this.generos = new HashMap<>();
    }
    
    @Override
    public void agregarGenero(Genero genero) {
        this.generos.put(genero.getCodGenero(), genero);
    }
    
    @Override
    public Long agregarUsuario(Usuario usuario){
        usuario.setId(++this.contadorIdUsuarios);
        this.usuarios.put(usuario.getEmail(), usuario);
        return this.contadorIdUsuarios;
    }
    
    @Override
    public Map<String, Genero> obtenerGeneros() {
        return this.generos;
    }
    
    @Override
    public Map<String, Usuario> obtenerUsuarios() {
        return this.usuarios;
    }
    
    @Override
    public Usuario obtenerUsuarioPorEmail(String emailUsuario) {
        return this.usuarios.get(emailUsuario);
    }
    
    
    
    @PostConstruct
    private void init(){
        this.agregarUsuario(new Usuario("Nombre 1", "Apellido 1", "email1@icloud.com"));
        this.agregarUsuario(new Usuario("Nombre 2", "Apellido 2", "email2@icloud.com"));
        this.agregarUsuario(new Usuario("Nombre 3", "Apellido 3", "email3@icloud.com"));
        
        this.agregarGenero(new Genero("act", "Action"));
        this.agregarGenero(new Genero("shoot", "Shooter"));
        this.agregarGenero(new Genero("adv", "Adventure"));
        this.agregarGenero(new Genero("sim", "Simulation"));
        this.agregarGenero(new Genero("str", "strategy"));
        this.agregarGenero(new Genero("sp", "Sports"));
        this.agregarGenero(new Genero("rol", "Role Playing"));
    }

}
