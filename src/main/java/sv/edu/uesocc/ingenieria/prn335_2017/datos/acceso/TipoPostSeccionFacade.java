/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.edu.uesocc.ingenieria.prn335_2017.datos.acceso;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import sv.edu.uesocc.ingenieria.prn335_2017.datos.definiciones.TipoPostSeccion;

/**
 *
 * @author gonzalez
 */
@Stateless
public class TipoPostSeccionFacade extends AbstractFacade<TipoPostSeccion> implements TipoPostSeccionFacadeLocal {

    @PersistenceContext(unitName = "ues.occ.prn335_Guia05_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public TipoPostSeccionFacade() {
        super(TipoPostSeccion.class);
    }
    
}
