package co.edu.eam.modelo.control;

import co.edu.eam.modelo.Respuesta;
import co.edu.eam.modelo.dto.RespuestaDTO;

import java.math.BigDecimal;

import java.util.*;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.ejb.Local;

/**
 * @author Zathura Code Generator http://zathuracode.org www.zathuracode.org
 *
 */
@Local
public interface IRespuestaLogic {
	public List<Respuesta> getRespuesta() throws Exception;

	/**
	 * Save an new Respuesta entity
	 */
	public void saveRespuesta(Respuesta entity) throws Exception;

	/**
	 * Delete an existing Respuesta entity
	 *
	 */
	public void deleteRespuesta(Respuesta entity) throws Exception;

	/**
	 * 
	 * <p>
	 * <b>Realiza las validaciones de los campos de una respuesta y
	 * posteriormente envia los datos al DAO para ser posteriormente
	 * actualizados </b>
	 * </p>
	 * <br/>
	 * <ul>
	 * <li></li>
	 * </ul>
	 * <br/>
	 * 
	 * @author EAM <br/>
	 *         Jefry Londoño Acosta <br/>
	 *         Email: jjmb2789@gmail.com <br/>
	 * @author EAM <br/>
	 *         Alvaro Javier Lotero <br/>
	 *         Email: <br/>
	 * @author EAM <br/>
	 *         Santiago Idarraga <br/>
	 *         <br/>
	 *         14/10/2016
	 * @version 1.0
	 * @param entity
	 *            Respuesta que se va a actualizar
	 * @throws Exception
	 */
	public void updateRespuesta(Respuesta entity) throws Exception;

	/**
	 * Load an existing Respuesta entity
	 *
	 */
	public Respuesta getRespuesta(Integer id) throws Exception;

	public List<Respuesta> findByCriteria(Object[] variables, Object[] variablesBetween, Object[] variablesBetweenDates)
			throws Exception;

	public List<Respuesta> findPageRespuesta(String sortColumnName, boolean sortAscending, int startRow, int maxResults)
			throws Exception;

	public Long findTotalNumberRespuesta() throws Exception;

	public List<RespuestaDTO> getDataRespuesta() throws Exception;

	/**
	 * Obtiene las respuestas de la base de datos para enviarlas a
	 * BusinessDelgator
	 * 
	 * @param whereCondition
	 *            condicion que servira para listar las respuestas que se
	 *            encuentren en la base de datos
	 * @return una lista con todas las respuestas que se modificaran
	 *         posteriormente en la base de datos
	 * @throws Exception
	 */
	/**
	 * 
	 * <p>
	 * <b>Obtiene las respuestas de la base de datos para enviarlas a
	 * BusinessDelgator </b>
	 * </p>
	 * <br/>
	 * <ul>
	 * <li></li>
	 * </ul>
	 * <br/>
	 * 
	 * @author EAM <br/>
	 *         Jefry Londoño Acosta <br/>
	 *         Email: jjmb2789@gmail.com <br/>
	 * @author EAM <br/>
	 *         Alvaro Javier Lotero <br/>
	 *         Email: <br/>
	 * @author EAM <br/>
	 *         Santiago Idarraga <br/>
	 *         <br/>
	 *         14/10/2016
	 * @version 1.0
	 * @param idPregunta
	 *            identificador de la pregunta
	 * @param idPresentacion
	 *            identificador de la presentacion
	 * @return una lista con el objeto que se va a modificar
	 * @throws Exception
	 */
	public List<RespuestaDTO> getDataRespuesta(Integer idPregunta, Integer idPresentacion) throws Exception;
}
