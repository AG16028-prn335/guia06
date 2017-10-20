/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.edu.uesocc.ingenieria.prn335_2017.web.controladores;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import org.primefaces.event.SelectEvent;
import sv.edu.uesocc.ingenieria.prn335_2017.datos.acceso.RolFacadeLocal;
import sv.edu.uesocc.ingenieria.prn335_2017.datos.definiciones.Rol;

/**
 *
 * @author gonzalez
 */
@Named(value = "rolBean")
@ViewScoped
public class RolBean implements Serializable {

    @EJB
    RolFacadeLocal RolFacade;
    List<Rol> listRol = new ArrayList<>();
    Rol rol = new Rol();
    boolean activo, refrescar = false;

    public RolBean() {
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    @PostConstruct
    public void init() {
        if (listRol != null) {
            this.listRol = RolFacade.findAll();
        } else {
            this.listRol = Collections.EMPTY_LIST;
        }
    }

    public List<Rol> getListRol() {
        return listRol;
    }

    public void setListRol(List<Rol> listRol) {
        this.listRol = listRol;
    }

    public RolFacadeLocal getRolFacade() {
        return RolFacade;
    }

    public void setRolFacade(RolFacadeLocal RolFacade) {
        this.RolFacade = RolFacade;
    }
    
    /**
     * este metodo sirve para crear un nuevo registro
     */
    public void crear() {
        try {
            RolFacade.create(rol);
            init();
            showMessage("Datos ingresado correctamente.");
            refresh();
        } catch (Exception e) {
            System.out.println("Error: " + e);
            showMessage("Error al ingresar los datos.");
        }
    }

    public void showMessage(String mensaje) {
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage(mensaje));
    }
    /**
     * este metodo filtra los roles no utilizados
     * @return 
     */
    public List<Rol> obtenerUtilizados() {
        List salida;
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("sv.edu.uesocc.ingenieria.prn335_Guia05_war_1.0-SNAPSHOTPU");
        EntityManager em = emf.createEntityManager();
        Query c = em.createNamedQuery("Rol.noUtilizados");
        salida = c.getResultList();

        if (salida != null) {
            return salida;
        } else {
            return Collections.EMPTY_LIST;
        }
    }
    /**
     * este metodo sirve para la accion de filtrado
     */
    public void chkCambio() {
        if (activo == true) {
            this.listRol = obtenerUtilizados();
            System.out.println("Funciona");
        } else {
            init();
            System.out.println("No funciona");
        }
    }

    public Rol getSelectedRol() {
        return rol;
    }
    /**
     * este metodo es para la seleccion de la tabla 
     */
    public void onRowSelect(SelectEvent event) {
        rol = (Rol) event.getObject();
        refrescar = true;
    }
    /**
     * este metodo sirve para editar un registro
     */
    public void editar() {
        try {
            RolFacade.edit(rol);
            init();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Edicion correcta."));
            refrescar = false;
            refresh();
        } catch (Exception e) {
            System.out.println("Error: " + e);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Error al editar registro."));
        }
    }
    /**
     * con este metodo se elimina un registro
     */
    public void eliminar() {
        try {
            RolFacade.remove(rol);
            init();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Registro removido correctamente."));
            refrescar = false;
            refresh();
        } catch (Exception e) {
            System.out.println("Error: " + e);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Error al eliminar."));
        }
    }

    public boolean isRefrescar() {
        return refrescar;
    }

    public void setRefrescar(boolean refrescar) {
        this.refrescar = refrescar;
    }

    public void Cancelar() {
        refresh();
        refrescar = false;
       
    }
    /**
     * con este metodo se refrescan los campos de la entity 
     */
    public void refresh() {
        rol.setIdRol(null);
        rol.setActivo(false);
        rol.setDescripcion(null);
        rol.setNombre(null);
    }
    
}
