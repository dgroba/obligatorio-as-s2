/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uy.com.gameon.negocio;

import java.util.List;
import java.util.Map;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import uy.com.gameon.dominio.Consola;
import uy.com.gameon.dominio.Genero;
import uy.com.gameon.dominio.Usuario;
import uy.com.gameon.excepciones.ConsolaNoExistenteException;
import uy.com.gameon.excepciones.GeneroNoExistenteException;
import uy.com.gameon.excepciones.UsuarioNoExistenteException;
import uy.com.gameon.persistencia.BaseDeDatosSingletonSBLocal;
import uy.com.gameon.util.Constantes;

@Stateless
public class UsuarioNegocioSB implements UsuarioNegocioSBLocal {
    
    @EJB
    private BaseDeDatosSingletonSBLocal baseDeDatos;
    
    @Override
    public Long registro(String nombre, String apellido, String email, String password) {
        assert baseDeDatos != null;
        return baseDeDatos.agregarUsuario(new Usuario(nombre, apellido, email, password));
    }
    
    @Override
    public Usuario obtenerUsuarioPorEmail(String emailUsuario) throws UsuarioNoExistenteException {
        assert baseDeDatos != null;
        Usuario usuario = baseDeDatos.obtenerUsuarioPorEmail(emailUsuario);
        
        if (usuario == null) {
            throw new UsuarioNoExistenteException(Constantes.codErrorUsuarioNoExistenteException, Constantes.mensajeUsuarioNoExistenteException);
        }
        
        return usuario;
    }

    @Override
    public void agregarFavorito(String email, String generoFavorito) throws GeneroNoExistenteException {
        assert baseDeDatos != null;
        Map<String, Genero> mapaGeneros = baseDeDatos.obtenerGeneros();
        Genero genero = mapaGeneros.get(generoFavorito);
        Usuario usuario;
        
        if (genero != null) {
            usuario = baseDeDatos.obtenerUsuarioPorEmail(email);
            usuario.getPreferenciasGenero().add(genero);
        } else {
           throw new GeneroNoExistenteException(Constantes.codErrorGeneroNoExistenteException, Constantes.mensajeGeneroNoExistenteException);
        }
    }

    @Override
    public void agregarConsola(String email, String codigoConsola) throws ConsolaNoExistenteException {
        assert baseDeDatos != null;
        Map<String, Consola> mapaConsolas = baseDeDatos.obtenerConsolas();
        Consola consola = mapaConsolas.get(codigoConsola);
        Usuario usuario;
        
        if (consola != null) {
            usuario = baseDeDatos.obtenerUsuarioPorEmail(email);
            usuario.getConsolas().add(consola);
        } else {
           throw new ConsolaNoExistenteException(Constantes.codErrorConsolaNoExistenteException, Constantes.mensajeConsolaNoExistenteException);
        }
    }

    @Override
    public List<Usuario> obtenerUsuariosPorConsolaGenero(String consola, String genero) {
        assert baseDeDatos != null;
        return baseDeDatos.obtenerUsuariosPorConsolaGenero(consola, genero);
    }

}
