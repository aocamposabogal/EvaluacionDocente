package co.edu.eam.modelo.control;

import co.edu.eam.dataaccess.dao.*;
import co.edu.eam.exceptions.*;
import co.edu.eam.modelo.*;
import co.edu.eam.modelo.dto.EvaluacionDTO;
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


/**
* @author Zathura Code Generator http://zathuracode.org
* www.zathuracode.org
*
*/
@Stateless
public class EvaluacionLogic implements IEvaluacionLogic {
    private static final Logger log = LoggerFactory.getLogger(EvaluacionLogic.class);

    /**
     * DAO injected by Spring that manages Evaluacion entities
     *
     */
    @EJB
    private IEvaluacionDAO evaluacionDAO;

    /**
    * DAO injected by Spring that manages Presentacion entities
    *
    */
    @EJB
    private IPresentacionDAO presentacionDAO;

    /**
    * Logic injected by Spring that manages Periodo entities
    *
    */
    @EJB
    IPeriodoLogic logicPeriodo1;

    /**
    * Logic injected by Spring that manages ProgramaAcademico entities
    *
    */
    @EJB
    IProgramaAcademicoLogic logicProgramaAcademico2;

    /**
    * Logic injected by Spring that manages TipoEvaluacion entities
    *
    */
    @EJB
    ITipoEvaluacionLogic logicTipoEvaluacion3;

    @TransactionAttribute
    public List<Evaluacion> getEvaluacion() throws Exception {
        log.debug("finding all Evaluacion instances");

        List<Evaluacion> list = new ArrayList<Evaluacion>();

        try {
            list = evaluacionDAO.findAll();
        } catch (Exception e) {
            log.error("finding all Evaluacion failed", e);
            throw new ZMessManager().new GettingException(ZMessManager.ALL +
                "Evaluacion");
        } finally {
        }

        return list;
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void saveEvaluacion(Evaluacion entity) throws Exception {
        log.debug("saving Evaluacion instance");

        try {
            if (entity.getPeriodo() == null) {
                throw new ZMessManager().new ForeignException("periodo");
            }

            if (entity.getProgramaAcademico() == null) {
                throw new ZMessManager().new ForeignException(
                    "programaAcademico");
            }

            if (entity.getTipoEvaluacion() == null) {
                throw new ZMessManager().new ForeignException("tipoEvaluacion");
            }

            if (entity.getId() == null) {
                throw new ZMessManager().new EmptyFieldException("id");
            }

            if (entity.getNombre() == null) {
                throw new ZMessManager().new EmptyFieldException("nombre");
            }

            if ((entity.getNombre() != null) &&
                    (Utilities.checkWordAndCheckWithlength(entity.getNombre(),
                        30) == false)) {
                throw new ZMessManager().new NotValidFormatException("nombre");
            }

            if (entity.getPeriodo().getId() == null) {
                throw new ZMessManager().new EmptyFieldException("id_Periodo");
            }

            if (entity.getProgramaAcademico().getId() == null) {
                throw new ZMessManager().new EmptyFieldException(
                    "id_ProgramaAcademico");
            }

            if (entity.getTipoEvaluacion().getId() == null) {
                throw new ZMessManager().new EmptyFieldException(
                    "id_TipoEvaluacion");
            }

            if (getEvaluacion(entity.getId()) != null) {
                throw new ZMessManager(ZMessManager.ENTITY_WITHSAMEKEY);
            }

            evaluacionDAO.save(entity);
            log.debug("save Evaluacion successful");
        } catch (Exception e) {
            log.error("save Evaluacion failed", e);
            throw e;
        } finally {
        }
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void deleteEvaluacion(Evaluacion entity) throws Exception {
        log.debug("deleting Evaluacion instance");

        if (entity == null) {
            throw new ZMessManager().new NullEntityExcepcion("Evaluacion");
        }

        if (entity.getId() == null) {
            throw new ZMessManager().new EmptyFieldException("id");
        }

        List<Presentacion> presentacions = null;

        try {
            presentacions = presentacionDAO.findByProperty("evaluacion.id",
                    entity.getId());

            if (Utilities.validationsList(presentacions) == true) {
                throw new ZMessManager().new DeletingException("presentacions");
            }

            evaluacionDAO.deleteById(entity.getId());
            log.debug("delete Evaluacion successful");
        } catch (Exception e) {
            log.error("delete Evaluacion failed", e);
            throw e;
        } finally {
        }
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void updateEvaluacion(Evaluacion entity) throws Exception {
        log.debug("updating Evaluacion instance");

        try {
            if (entity == null) {
                throw new ZMessManager().new NullEntityExcepcion("Evaluacion");
            }

            if (entity.getPeriodo() == null) {
                throw new ZMessManager().new ForeignException("periodo");
            }

            if (entity.getProgramaAcademico() == null) {
                throw new ZMessManager().new ForeignException(
                    "programaAcademico");
            }

            if (entity.getTipoEvaluacion() == null) {
                throw new ZMessManager().new ForeignException("tipoEvaluacion");
            }

            if (entity.getId() == null) {
                throw new ZMessManager().new EmptyFieldException("id");
            }

            if (entity.getNombre() == null) {
                throw new ZMessManager().new EmptyFieldException("nombre");
            }

            if ((entity.getNombre() != null) &&
                    (Utilities.checkWordAndCheckWithlength(entity.getNombre(),
                        30) == false)) {
                throw new ZMessManager().new NotValidFormatException("nombre");
            }

            if (entity.getPeriodo().getId() == null) {
                throw new ZMessManager().new EmptyFieldException("id_Periodo");
            }

            if (entity.getProgramaAcademico().getId() == null) {
                throw new ZMessManager().new EmptyFieldException(
                    "id_ProgramaAcademico");
            }

            if (entity.getTipoEvaluacion().getId() == null) {
                throw new ZMessManager().new EmptyFieldException(
                    "id_TipoEvaluacion");
            }

            evaluacionDAO.update(entity);

            log.debug("update Evaluacion successful");
        } catch (Exception e) {
            throw e;
        } finally {
        }
    }

    @TransactionAttribute
    public List<EvaluacionDTO> getDataEvaluacion() throws Exception {
        try {
            List<Evaluacion> evaluacion = evaluacionDAO.findAll();

            List<EvaluacionDTO> evaluacionDTO = new ArrayList<EvaluacionDTO>();

            for (Evaluacion evaluacionTmp : evaluacion) {
                EvaluacionDTO evaluacionDTO2 = new EvaluacionDTO();

                evaluacionDTO2.setId(evaluacionTmp.getId());
                evaluacionDTO2.setNombre((evaluacionTmp.getNombre() != null)
                    ? evaluacionTmp.getNombre() : null);
                evaluacionDTO2.setId_Periodo((evaluacionTmp.getPeriodo().getId() != null)
                    ? evaluacionTmp.getPeriodo().getId() : null);
                evaluacionDTO2.setId_ProgramaAcademico((evaluacionTmp.getProgramaAcademico()
                                                                     .getId() != null)
                    ? evaluacionTmp.getProgramaAcademico().getId() : null);
                evaluacionDTO2.setId_TipoEvaluacion((evaluacionTmp.getTipoEvaluacion()
                                                                  .getId() != null)
                    ? evaluacionTmp.getTipoEvaluacion().getId() : null);
                evaluacionDTO.add(evaluacionDTO2);
            }

            return evaluacionDTO;
        } catch (Exception e) {
            throw e;
        }
    }

    @TransactionAttribute
    public Evaluacion getEvaluacion(Integer id) throws Exception {
        log.debug("getting Evaluacion instance");

        Evaluacion entity = null;

        try {
            entity = evaluacionDAO.findById(id);
        } catch (Exception e) {
            log.error("get Evaluacion failed", e);
            throw new ZMessManager().new FindingException("Evaluacion");
        } finally {
        }

        return entity;
    }

    @TransactionAttribute
    public List<Evaluacion> findPageEvaluacion(String sortColumnName,
        boolean sortAscending, int startRow, int maxResults)
        throws Exception {
        List<Evaluacion> entity = null;

        try {
            entity = evaluacionDAO.findPage(sortColumnName, sortAscending,
                    startRow, maxResults);
        } catch (Exception e) {
            throw new ZMessManager().new FindingException("Evaluacion Count");
        } finally {
        }

        return entity;
    }

    @TransactionAttribute
    public Long findTotalNumberEvaluacion() throws Exception {
        Long entity = null;

        try {
            entity = evaluacionDAO.count();
        } catch (Exception e) {
            throw new ZMessManager().new FindingException("Evaluacion Count");
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
    public List<Evaluacion> findByCriteria(Object[] variables,
        Object[] variablesBetween, Object[] variablesBetweenDates)
        throws Exception {
        List<Evaluacion> list = new ArrayList<Evaluacion>();
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
            list = evaluacionDAO.findByCriteria(where);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        } finally {
        }

        return list;
    }
}
