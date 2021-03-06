package co.edu.eam.modelo.control;

import co.edu.eam.dataaccess.dao.*;
import co.edu.eam.exceptions.*;
import co.edu.eam.modelo.*;
import co.edu.eam.modelo.dto.PreguntaDTO;
import co.edu.eam.utilities.Utilities;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;


/**
* @author Zathura Code Generator http://zathuracode.org
* www.zathuracode.org
*
*/
@Stateless
public class PreguntaLogic implements IPreguntaLogic {
    private static final Logger log = LoggerFactory.getLogger(PreguntaLogic.class);

    /**
     * DAO injected by Spring that manages Pregunta entities
     *
     */
    @EJB
    private IPreguntaDAO preguntaDAO;

    /**
    * DAO injected by Spring that manages Respuesta entities
    *
    */
    @EJB
    private IRespuestaDAO respuestaDAO;

    /**
    * Logic injected by Spring that manages Periodo entities
    *
    */
    @EJB
    IPeriodoLogic logicPeriodo1;

    /**
    * Logic injected by Spring that manages TipoEvaluacion entities
    *
    */
    @EJB
    ITipoEvaluacionLogic logicTipoEvaluacion2;
    
   
	public PreguntaLogic(EntityManager entityManager) {
		// log.debug(nombrePrueba);
		 preguntaDAO = new PreguntaDAO(entityManager);
		  
	}
	
	public PreguntaLogic (){
		
	}

    @TransactionAttribute
    public List<Pregunta> getPregunta() throws Exception {
        log.debug("finding all Pregunta instances");

        List<Pregunta> list = new ArrayList<Pregunta>();

        try {
            list = preguntaDAO.findAll();
        } catch (Exception e) {
            log.error("finding all Pregunta failed", e);
            throw new ZMessManager().new GettingException(ZMessManager.ALL +
                "Pregunta");
        } finally {
        }

        return list;
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void savePregunta(Pregunta entity) throws Exception {
        log.debug("saving Pregunta instance");

        try {
            if (entity.getPeriodo() == null) {
                throw new ZMessManager().new ForeignException("periodo");
            }

            if (entity.getTipoEvaluacion() == null) {
                throw new ZMessManager().new ForeignException("tipoEvaluacion");
            }

            if (entity.getEstado() == null) {
                throw new ZMessManager().new EmptyFieldException("estado");
            }

            if ((entity.getEstado() != null) &&
                    (Utilities.checkWordAndCheckWithlength(entity.getEstado(), 1) == false)) {
                throw new ZMessManager().new NotValidFormatException("estado");
            }

            if (entity.getId() == null) {
                throw new ZMessManager().new EmptyFieldException("id");
            }

            if (entity.getPregunta() == null) {
                throw new ZMessManager().new EmptyFieldException("pregunta");
            }

            if ((entity.getPregunta() != null) &&
                    (Utilities.checkWordAndCheckWithlength(
                        entity.getPregunta(), 30) == false)) {
                throw new ZMessManager().new NotValidFormatException("pregunta");
            }

            if (entity.getPeriodo().getId() == null) {
                throw new ZMessManager().new EmptyFieldException("id_Periodo");
            }

            if (entity.getTipoEvaluacion().getId() == null) {
                throw new ZMessManager().new EmptyFieldException(
                    "id_TipoEvaluacion");
            }

            if (getPregunta(entity.getId()) != null) {
                throw new ZMessManager(ZMessManager.ENTITY_WITHSAMEKEY);
            }

            preguntaDAO.save(entity);
            log.debug("save Pregunta successful");
        } catch (Exception e) {
            log.error("save Pregunta failed", e);
            throw e;
        } finally {
        }
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void deletePregunta(Pregunta entity) throws Exception {
        log.debug("deleting Pregunta instance");

        if (entity == null) {
            throw new ZMessManager().new NullEntityExcepcion("Pregunta");
        }

        if (entity.getId() == null) {
            throw new ZMessManager().new EmptyFieldException("id");
        }

        List<Respuesta> respuestas = null;

        try {
            respuestas = respuestaDAO.findByProperty("pregunta.id",
                    entity.getId());

            if (Utilities.validationsList(respuestas) == true) {
                throw new ZMessManager().new DeletingException("respuestas");
            }

            preguntaDAO.deleteById(entity.getId());
            log.debug("delete Pregunta successful");
        } catch (Exception e) {
            log.error("delete Pregunta failed", e);
            throw e;
        } finally {
        }
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void updatePregunta(Pregunta entity) throws Exception {
        log.debug("updating Pregunta instance");

        try {
            if (entity == null) {
                throw new ZMessManager().new NullEntityExcepcion("Pregunta");
            }

            if (entity.getPeriodo() == null) {
                throw new ZMessManager().new ForeignException("periodo");
            }

            if (entity.getTipoEvaluacion() == null) {
                throw new ZMessManager().new ForeignException("tipoEvaluacion");
            }

            if (entity.getEstado() == null) {
                throw new ZMessManager().new EmptyFieldException("estado");
            }

            if ((entity.getEstado() != null) &&
                    (Utilities.checkWordAndCheckWithlength(entity.getEstado(), 1) == false)) {
                throw new ZMessManager().new NotValidFormatException("estado");
            }

            if (entity.getId() == null) {
                throw new ZMessManager().new EmptyFieldException("id");
            }

            if (entity.getPregunta() == null) {
                throw new ZMessManager().new EmptyFieldException("pregunta");
            }

            if ((entity.getPregunta() != null) &&
                    (Utilities.checkWordAndCheckWithlength(
                        entity.getPregunta(), 30) == false)) {
                throw new ZMessManager().new NotValidFormatException("pregunta");
            }

            if (entity.getPeriodo().getId() == null) {
                throw new ZMessManager().new EmptyFieldException("id_Periodo");
            }

            if (entity.getTipoEvaluacion().getId() == null) {
                throw new ZMessManager().new EmptyFieldException(
                    "id_TipoEvaluacion");
            }

            preguntaDAO.update(entity);

            log.debug("update Pregunta successful");
        } catch (Exception e) {
            throw e;
        } finally {
        }
    }

    @TransactionAttribute
    public List<PreguntaDTO> getDataPregunta() throws Exception {
        try {
            List<Pregunta> pregunta = preguntaDAO.findAll();

            List<PreguntaDTO> preguntaDTO = new ArrayList<PreguntaDTO>();

            for (Pregunta preguntaTmp : pregunta) {
                PreguntaDTO preguntaDTO2 = new PreguntaDTO();

                preguntaDTO2.setId(preguntaTmp.getId());
                preguntaDTO2.setEstado((preguntaTmp.getEstado() != null)
                    ? preguntaTmp.getEstado() : null);
                preguntaDTO2.setPregunta((preguntaTmp.getPregunta() != null)
                    ? preguntaTmp.getPregunta() : null);
                preguntaDTO2.setId_Periodo((preguntaTmp.getPeriodo().getId() != null)
                    ? preguntaTmp.getPeriodo().getId() : null);
                preguntaDTO2.setId_TipoEvaluacion((preguntaTmp.getTipoEvaluacion()
                                                              .getId() != null)
                    ? preguntaTmp.getTipoEvaluacion().getId() : null);
                preguntaDTO.add(preguntaDTO2);
            }

            return preguntaDTO;
        } catch (Exception e) {
            throw e;
        }
    }

	/*
     * (non-Javadoc)
     * @see co.edu.eam.modelo.control.IPreguntaLogic#getDataPregunta(java.lang.String)
     */
    @TransactionAttribute
    public List<PreguntaDTO> getDataPregunta(int tipoEvaluacion, int estado) throws Exception {
        try {
        	
        	String whereCondition = "model.tipoEvaluacion.id = "+tipoEvaluacion +" and model.estado = '"+estado+"'";
            List<Pregunta> pregunta = preguntaDAO.findByCriteria(whereCondition);
            Pregunta pregunta2 = preguntaDAO.findById(1);
            pregunta2.getPregunta();

            List<PreguntaDTO> preguntaDTO = new ArrayList<PreguntaDTO>();

            for (Pregunta preguntaTmp : pregunta) {
                PreguntaDTO preguntaDTO2 = new PreguntaDTO();

                preguntaDTO2.setId(preguntaTmp.getId());
                preguntaDTO2.setEstado((preguntaTmp.getEstado() != null)
                    ? preguntaTmp.getEstado() : null);
                preguntaDTO2.setPregunta((preguntaTmp.getPregunta() != null)
                    ? preguntaTmp.getPregunta() : null);
                preguntaDTO2.setId_Periodo((preguntaTmp.getPeriodo().getId() != null)
                    ? preguntaTmp.getPeriodo().getId() : null);
                preguntaDTO2.setId_TipoEvaluacion((preguntaTmp.getTipoEvaluacion()
                                                              .getId() != null)
                    ? preguntaTmp.getTipoEvaluacion().getId() : null);
                preguntaDTO.add(preguntaDTO2);
            }

            return preguntaDTO;
        } catch (Exception e) {
            throw e;
        }
    }
	
    @TransactionAttribute
    public Pregunta getPregunta(Integer id) throws Exception {
        log.debug("getting Pregunta instance");

        Pregunta entity = null;

        try {
            entity = preguntaDAO.findById(id);
        } catch (Exception e) {
            log.error("get Pregunta failed", e);
            throw new ZMessManager().new FindingException("Pregunta");
        } finally {
        }

        return entity;
    }

    @TransactionAttribute
    public List<Pregunta> findPagePregunta(String sortColumnName,
        boolean sortAscending, int startRow, int maxResults)
        throws Exception {
        List<Pregunta> entity = null;

        try {
            entity = preguntaDAO.findPage(sortColumnName, sortAscending,
                    startRow, maxResults);
        } catch (Exception e) {
            throw new ZMessManager().new FindingException("Pregunta Count");
        } finally {
        }

        return entity;
    }

    @TransactionAttribute
    public Long findTotalNumberPregunta() throws Exception {
        Long entity = null;

        try {
            entity = preguntaDAO.count();
        } catch (Exception e) {
            throw new ZMessManager().new FindingException("Pregunta Count");
        } finally {
        }

        return entity;
    }

    /**
    *
    * @param varibles
    *            este arreglo debera tener:
    *
    * [0] = String variable = (String) varibles[i]; representa como se llama la
    * variable en el pojo
    *
    * [1] = Boolean booVariable = (Boolean) varibles[i + 1]; representa si el
    * valor necesita o no ''(comillas simples)usado para campos de tipo string
    *
    * [2] = Object value = varibles[i + 2]; representa el valor que se va a
    * buscar en la BD
    *
    * [3] = String comparator = (String) varibles[i + 3]; representa que tipo
    * de busqueda voy a hacer.., ejemplo: where nombre=william o where nombre<>william,
        * en este campo iria el tipo de comparador que quiero si es = o <>
            *
            * Se itera de 4 en 4..., entonces 4 registros del arreglo representan 1
            * busqueda en un campo, si se ponen mas pues el continuara buscando en lo
            * que se le ingresen en los otros 4
            *
            *
            * @param variablesBetween
            *
            * la diferencia son estas dos posiciones
            *
            * [0] = String variable = (String) varibles[j]; la variable ne la BD que va
            * a ser buscada en un rango
            *
            * [1] = Object value = varibles[j + 1]; valor 1 para buscar en un rango
            *
            * [2] = Object value2 = varibles[j + 2]; valor 2 para buscar en un rango
            * ejempolo: a > 1 and a < 5 --> 1 seria value y 5 seria value2
                *
                * [3] = String comparator1 = (String) varibles[j + 3]; comparador 1
                * ejemplo: a comparator1 1 and a < 5
                    *
                    * [4] = String comparator2 = (String) varibles[j + 4]; comparador 2
                    * ejemplo: a comparador1>1  and a comparador2<5  (el original: a > 1 and a <
                            * 5) *
                            * @param variablesBetweenDates(en
                            *            este caso solo para mysql)
                            *  [0] = String variable = (String) varibles[k]; el nombre de la variable que hace referencia a
                            *            una fecha
                            *
                            * [1] = Object object1 = varibles[k + 2]; fecha 1 a comparar(deben ser
                            * dates)
                            *
                            * [2] = Object object2 = varibles[k + 3]; fecha 2 a comparar(deben ser
                            * dates)
                            *
                            * esto hace un between entre las dos fechas.
                            *
                            * @return lista con los objetos que se necesiten
                            * @throws Exception
                            */
    @TransactionAttribute
    public List<Pregunta> findByCriteria(Object[] variables,
        Object[] variablesBetween, Object[] variablesBetweenDates)
        throws Exception {
        List<Pregunta> list = new ArrayList<Pregunta>();
        String where = new String();
        String tempWhere = new String();

        if (variables != null) {
            for (int i = 0; i < variables.length; i++) {
                if ((variables[i] != null) && (variables[i + 1] != null) &&
                        (variables[i + 2] != null) &&
                        (variables[i + 3] != null)) {
                    String variable = (String) variables[i];
                    Boolean booVariable = (Boolean) variables[i + 1];
                    Object value = variables[i + 2];
                    String comparator = (String) variables[i + 3];

                    if (booVariable.booleanValue()) {
                        tempWhere = (tempWhere.length() == 0)
                            ? ("(model." + variable + " " + comparator + " \'" +
                            value + "\' )")
                            : (tempWhere + " AND (model." + variable + " " +
                            comparator + " \'" + value + "\' )");
                    } else {
                        tempWhere = (tempWhere.length() == 0)
                            ? ("(model." + variable + " " + comparator + " " +
                            value + " )")
                            : (tempWhere + " AND (model." + variable + " " +
                            comparator + " " + value + " )");
                    }
                }

                i = i + 3;
            }
        }

        if (variablesBetween != null) {
            for (int j = 0; j < variablesBetween.length; j++) {
                if ((variablesBetween[j] != null) &&
                        (variablesBetween[j + 1] != null) &&
                        (variablesBetween[j + 2] != null) &&
                        (variablesBetween[j + 3] != null) &&
                        (variablesBetween[j + 4] != null)) {
                    String variable = (String) variablesBetween[j];
                    Object value = variablesBetween[j + 1];
                    Object value2 = variablesBetween[j + 2];
                    String comparator1 = (String) variablesBetween[j + 3];
                    String comparator2 = (String) variablesBetween[j + 4];
                    tempWhere = (tempWhere.length() == 0)
                        ? ("(" + value + " " + comparator1 + " " + variable +
                        " and " + variable + " " + comparator2 + " " + value2 +
                        " )")
                        : (tempWhere + " AND (" + value + " " + comparator1 +
                        " " + variable + " and " + variable + " " +
                        comparator2 + " " + value2 + " )");
                }

                j = j + 4;
            }
        }

        if (variablesBetweenDates != null) {
            for (int k = 0; k < variablesBetweenDates.length; k++) {
                if ((variablesBetweenDates[k] != null) &&
                        (variablesBetweenDates[k + 1] != null) &&
                        (variablesBetweenDates[k + 2] != null)) {
                    String variable = (String) variablesBetweenDates[k];
                    Object object1 = variablesBetweenDates[k + 1];
                    Object object2 = variablesBetweenDates[k + 2];
                    String value = null;
                    String value2 = null;

                    try {
                        Date date1 = (Date) object1;
                        Date date2 = (Date) object2;
                        value = Utilities.formatDateWithoutTimeInAStringForBetweenWhere(date1);
                        value2 = Utilities.formatDateWithoutTimeInAStringForBetweenWhere(date2);
                    } catch (Exception e) {
                        list = null;
                        throw e;
                    }

                    tempWhere = (tempWhere.length() == 0)
                        ? ("(model." + variable + " between \'" + value +
                        "\' and \'" + value2 + "\')")
                        : (tempWhere + " AND (model." + variable +
                        " between \'" + value + "\' and \'" + value2 + "\')");
                }

                k = k + 2;
            }
        }

        if (tempWhere.length() == 0) {
            where = null;
        } else {
            where = "(" + tempWhere + ")";
        }

        try {
            list = preguntaDAO.findByCriteria(where);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        } finally {
        }

        return list;
    }
}
