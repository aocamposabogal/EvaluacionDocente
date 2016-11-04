package co.edu.eam.presentation.backingBeans;

import co.edu.eam.exceptions.*;
import co.edu.eam.modelo.*;
import co.edu.eam.modelo.dto.PreguntaDTO;
import co.edu.eam.modelo.dto.TipoEvaluacionDTO;
import co.edu.eam.presentation.businessDelegate.*;
import co.edu.eam.utilities.*;

import org.primefaces.component.calendar.*;
import org.primefaces.component.commandbutton.CommandButton;
import org.primefaces.component.inputtext.InputText;

import org.primefaces.event.RowEditEvent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;

import java.sql.*;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.TimeZone;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.persistence.Convert;

/**
 * 
 * @author Jefry Londoño <jjmb2789@gmail.com>
 * @17/10/2016
 * @version
 */
@ManagedBean
@ViewScoped
public class PreguntaView implements Serializable {
	private static final long serialVersionUID = 1L;
	private static final Logger log = LoggerFactory.getLogger(PreguntaView.class);
	public final static int ESTADO_PREGUNTA = 1;
	private InputText txtEstado;
	private InputText txtPregunta;
	private InputText txtId_Periodo;
	private InputText txtId_TipoEvaluacion;
	private InputText txtId = new InputText();
	private InputText txtTipoEvaluacion;
	private CommandButton btnSave;
	private CommandButton btnModify;
	private CommandButton btnDelete;
	private CommandButton btnClear;
	private List<PreguntaDTO> data;
	private List<PreguntaDTO> dataCopia;
	private PreguntaDTO selectedPregunta;
	private Pregunta entity;
	private boolean showDialog;
	private Integer idTipoEvaluacion;
	private Map<String, Integer> tipoEvaluacio;
	private List<TipoEvaluacion> listaTipoEvaluaciones;
	private List<TipoEvaluacion> listaEntidadTipoEvaluacion;
	private Integer idPeriodo;
	private Map<String, Integer> periodo;
	private List<Periodo> listaPeriodos;
	private List<Periodo> listaEntidadPeriodos;
	private Map<String, Integer> estado;
	private String idEstado;

	@EJB
	private IBusinessDelegatorView businessDelegatorView;

	public PreguntaView() {
		super();
	}

	@PostConstruct
	public void init() throws Exception {

		estado = new HashMap<String, Integer>();
		estado.put("ACTIVO", (int) 0);
		estado.put("INACTIVO", (int) 1);

		listaTipoEvaluaciones = new ArrayList<TipoEvaluacion>();
		tipoEvaluacio = new HashMap<String, Integer>();
		listaEntidadTipoEvaluacion = new ArrayList<TipoEvaluacion>();

		listaTipoEvaluaciones = businessDelegatorView.getTipoEvaluacion();

		for (int i = 0; i < listaTipoEvaluaciones.size(); i++) {
			tipoEvaluacio.put(listaTipoEvaluaciones.get(i).getNombre(), listaTipoEvaluaciones.get(i).getId());
		}

		listaPeriodos = new ArrayList<Periodo>();
		periodo = new HashMap<String, Integer>();
		listaEntidadPeriodos = new ArrayList<Periodo>();

		listaPeriodos = businessDelegatorView.getPeriodo();

		for (int i = 0; i < listaPeriodos.size(); i++) {
			periodo.put(listaPeriodos.get(i).getNombre(), listaPeriodos.get(i).getId());
		}

	}

	public String action_clear() {
		entity = null;
		selectedPregunta = null;

		if (txtEstado != null) {
			txtEstado.setValue(null);
			txtEstado.setDisabled(true);
		}

		if (txtPregunta != null) {
			txtPregunta.setValue(null);
			txtPregunta.setDisabled(true);
		}

		if (txtId_Periodo != null) {
			txtId_Periodo.setValue(null);
			txtId_Periodo.setDisabled(true);
		}

		if (txtId_TipoEvaluacion != null) {
			txtId_TipoEvaluacion.setValue(null);
			txtId_TipoEvaluacion.setDisabled(true);
		}

		if (txtId != null) {
			txtId.setValue(null);
			txtId.setDisabled(false);
		}

		if (btnSave != null) {
			btnSave.setDisabled(true);
		}

		if (btnDelete != null) {
			btnDelete.setDisabled(true);
		}

		return "";
	}

//	public void listener_txtId() {
//		try {
//			Integer id = FacesUtils.checkInteger(txtId);
//			entity = (id != null) ? businessDelegatorView.getPregunta(id) : null;
//		} catch (Exception e) {
//			entity = null;
//		}
//
//		if (entity == null) {
//			txtEstado.setDisabled(false);
//			txtPregunta.setDisabled(false);
//			txtId_Periodo.setDisabled(false);
//			txtId_TipoEvaluacion.setDisabled(false);
//			txtId.setDisabled(false);
//			btnSave.setDisabled(false);
//		} else {
//			txtEstado.setValue(entity.getEstado());
//			txtEstado.setDisabled(false);
//			txtPregunta.setValue(entity.getPregunta());
//			txtPregunta.setDisabled(false);
//			txtId_Periodo.setValue(entity.getPeriodo().getId());
//			txtId_Periodo.setDisabled(false);
//			txtId_TipoEvaluacion.setValue(entity.getTipoEvaluacion().getId());
//			txtId_TipoEvaluacion.setDisabled(false);
//			txtId.setValue(entity.getId());
//			txtId.setDisabled(true);
//			btnSave.setDisabled(false);
//
//			if (btnDelete != null) {
//				btnDelete.setDisabled(false);
//			}
//		}
//	}

	/**
	 * <b>
	 * <p>
	 * Este Metodo se encarga de Generar un id aleatorio <br>
	 * que se va utilizar al moment de guardar la evaluacione<br>
	 * </p>
	 * </b>
	 * 
	 * @author Daniel Henao <br>
	 *         Email: <danielhenao22@outlook.com> <br>
	 *         13/10/2016
	 * @return Cadena vacia
	 * 
	 */
	public Integer generarID() {
		Random rdn = new Random();
		return rdn.nextInt(1000);
	}

	public String action_edit(ActionEvent evt) {
		selectedPregunta = (PreguntaDTO) (evt.getComponent().getAttributes().get("selectedPregunta"));
		txtEstado.setValue(selectedPregunta.getEstado());
		txtEstado.setDisabled(false);
		txtPregunta.setValue(selectedPregunta.getPregunta());
		txtPregunta.setDisabled(false);
		txtId_Periodo.setValue(selectedPregunta.getId_Periodo());
		txtId_Periodo.setDisabled(false);
		txtId_TipoEvaluacion.setValue(selectedPregunta.getId_TipoEvaluacion());
		txtId_TipoEvaluacion.setDisabled(false);
		txtId.setValue(selectedPregunta.getId());
		txtId.setDisabled(true);
		btnSave.setDisabled(false);
		setShowDialog(true);

		return "";
	}

	/**
	 * <b>
	 * <p>
	 * Metodo que se encarga de gestionar el guardar de una <br>
	 * pregunta el cual internamente se llama el metodo: <br>
	 * <ul>
	 * <li>action_create</li>
	 * </ul>
	 * </p>
	 * </b>
	 * 
	 * @author Daniel Henao <br>
	 *         Email: <danielhenao22@outlook.com> <br>
	 *         ${date}
	 * @return Cadena vacia
	 *
	 */
	public String action_save() {
		try {

			action_create();

			data = null;
		} catch (Exception e) {
			FacesUtils.addErrorMessage(e.getMessage());
		}

		return "";
	}

	/**
	 * <b>
	 * <p>
	 * Metodo que se encarga de crear la pregunta el cual se le setea los datos
	 * a la Pregunta <br>
	 * </p>
	 * </b>
	 * 
	 * @author Daniel Giraldo <br>
	 *         Email: <pipe_635@hotmail.com> <br>
	 *         ${date}
	 * @return Cadena vacia
	 * 
	 */
	public String action_create() {
		try {
			entity = new Pregunta();
			Integer id = generarID();
			Integer tipo = 2;
			entity.setEstado(idEstado);
			entity.setId(id);
			entity.setPregunta(FacesUtils.checkString(txtPregunta));
			entity.setPeriodo((idPeriodo != null) ? businessDelegatorView.getPeriodo(idPeriodo) : null);
			entity.setTipoEvaluacion((tipo != null) ? businessDelegatorView.getTipoEvaluacion(tipo) : null);
			businessDelegatorView.savePregunta(entity);
			FacesUtils.addInfoMessage(ZMessManager.ENTITY_SUCCESFULLYSAVED);

		} catch (Exception e) {
			entity = null;
			FacesUtils.addErrorMessage(e.getMessage());
		}

		return "";
	}
	
	/**
	 * <b>
	 * <p>
	 * Metodo que se encarga de crear la pregunta el cual se le setea los datos
	 * a la Pregunta <br>
	 * </p>
	 * </b>
	 * 
	 * @author Daniel Henao <br>
	 *         Email: <danielhenao22@outlook.com> <br>
	 *         ${date}
	 * @return Cadena vacia
	 * 
	 */
	public String action_create_ev() {
		try {
			entity = new Pregunta();
			Integer id = generarID();
			Integer tipo = 1;
			entity.setEstado(idEstado);
			entity.setId(id);
			entity.setPregunta(FacesUtils.checkString(txtPregunta));
			entity.setPeriodo((idPeriodo != null) ? businessDelegatorView.getPeriodo(idPeriodo) : null);
			entity.setTipoEvaluacion((tipo != null) ? businessDelegatorView.getTipoEvaluacion(tipo) : null);
			businessDelegatorView.savePregunta(entity);
			FacesUtils.addInfoMessage(ZMessManager.ENTITY_SUCCESFULLYSAVED);

		} catch (Exception e) {
			entity = null;
			FacesUtils.addErrorMessage(e.getMessage());
		}

		return "";
	}
	
	/**
	 * <b>
	 * <p>
	 * Metodo que se encarga de crear la pregunta el cual se le setea los datos
	 * a la Pregunta <br>
	 * </p>
	 * </b>
	 * 
	 * @author Daniel Henao <br>
	 *         Email: <danielhenao22@outlook.com> <br>
	 *         ${date}
	 * @return Cadena vacia
	 * 
	 */
	public String action_create_co() {
		try {
			entity = new Pregunta();
			Integer id = generarID();
			Integer tipo = 3;
			entity.setEstado(idEstado);
			entity.setId(id);
			entity.setPregunta(FacesUtils.checkString(txtPregunta));
			entity.setPeriodo((idPeriodo != null) ? businessDelegatorView.getPeriodo(idPeriodo) : null);
			entity.setTipoEvaluacion((tipo != null) ? businessDelegatorView.getTipoEvaluacion(tipo) : null);
			businessDelegatorView.savePregunta(entity);
			FacesUtils.addInfoMessage(ZMessManager.ENTITY_SUCCESFULLYSAVED);

		} catch (Exception e) {
			entity = null;
			FacesUtils.addErrorMessage(e.getMessage());
		}

		return "";
	}

	/**
	 * <b>
	 * <p>
	 * Metodo que se encarga de modificar la pregunta buscando la <br>
	 * pregunta por el id
	 * </p>
	 * </b>
	 * 
	 * @author Daniel Giraldo <br>
	 *         Email: <pipe_635@hotmail.com> <br>
	 *         ${date}
	 * @return Cadena vacia
	 */
	public String action_modify() {
		try {
			if (entity == null) {
				Integer id = new Integer(selectedPregunta.getId());
				entity = businessDelegatorView.getPregunta(id);
			}

			entity.setEstado(FacesUtils.checkString(txtEstado));
			entity.setPregunta(FacesUtils.checkString(txtPregunta));
			entity.setPeriodo((FacesUtils.checkInteger(txtId_Periodo) != null)
					? businessDelegatorView.getPeriodo(FacesUtils.checkInteger(txtId_Periodo)) : null);
			entity.setTipoEvaluacion((FacesUtils.checkInteger(txtId_TipoEvaluacion) != null)
					? businessDelegatorView.getTipoEvaluacion(FacesUtils.checkInteger(txtId_TipoEvaluacion)) : null);
			businessDelegatorView.updatePregunta(entity);
			FacesUtils.addInfoMessage(ZMessManager.ENTITY_SUCCESFULLYMODIFIED);
		} catch (Exception e) {
			data = null;
			FacesUtils.addErrorMessage(e.getMessage());
		}

		return "";
	}

	/**
	 * <b>
	 * <p>
	 * Metodo que se encarga de borrar la pregunta por su id <br>
	 * </p>
	 * </b>
	 * 
	 * @author Daniel Giraldo <br>
	 *         Email: <pipe_635@hotmail.com> <br>
	 *         13/10/2016
	 * @return
	 * 
	 */
	public void action_delete() throws Exception {
		try {
			businessDelegatorView.deletePregunta(entity);
			FacesUtils.addInfoMessage(ZMessManager.ENTITY_SUCCESFULLYDELETED);
			action_clear();
			data = null;
		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * <b>
	 * <p>
	 * Este metodo se utiliza para cargar todas las preguntas en una<br>
	 * tabla ya que nos permite tener en una lista todos los datos <br>
	 * de la pregunta
	 * </p>
	 * </b>
	 * 
	 * @author Daniel Henao <br>
	 *         Email: <danielhenao22@outlook.com> <br>
	 *         14/10/2016
	 * @return Lista con los datos de las pregunts que se quieren listar
	 * 
	 */
	public List<PreguntaDTO> getData() {
		try {
			if (data == null) {
				data = businessDelegatorView.getDataPregunta();
				for (int i = 0; i < data.size(); i++) {
					if (data.get(i).getId_TipoEvaluacion() != 1) {
						data.remove(data.get(i));
					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return data;
	}

	/**
	 * 
	 * <p>
	 * <b>Muestra en la vista las preguntas segun tipo de evaluacion
	 * Coevaluacion además si la pregunta se encuentra activa</b>
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
	 *         Email: lotero021095@gmail.com<br/>
	 * @author EAM <br/>
	 *         Santiago Idarraga <br/>
	 *         <br/>
	 *         13/10/2016 
	 * @version 1.0
	 * @return la lista con las preguntas que se en encuentre en la base de
	 *         datos con la realizacion de la consulta SQL
	 */
	public List<PreguntaDTO> getDataPregunta() {
		try {
			if (data == null) {
				int tipoEvaluacion = FacesUtils.checkInteger(txtTipoEvaluacion);
				data = businessDelegatorView.getDataPregunta(tipoEvaluacion, ESTADO_PREGUNTA);
				FacesUtils.addInfoMessage(ZMessManager.ENTCHILD);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return data;
	}

	public InputText getTxtEstado() {
		return txtEstado;
	}

	public void setTxtEstado(InputText txtEstado) {
		this.txtEstado = txtEstado;
	}

	public InputText getTxtPregunta() {
		return txtPregunta;
	}

	public void setTxtPregunta(InputText txtPregunta) {
		this.txtPregunta = txtPregunta;
	}
	

	public InputText getTxtId_Periodo() {
		return txtId_Periodo;
	}

	public void setTxtId_Periodo(InputText txtId_Periodo) {
		this.txtId_Periodo = txtId_Periodo;
	}

	public InputText getTxtId_TipoEvaluacion() {
		return txtId_TipoEvaluacion;
	}

	public void setTxtId_TipoEvaluacion(InputText txtId_TipoEvaluacion) {
		this.txtId_TipoEvaluacion = txtId_TipoEvaluacion;
	}

	public InputText getTxtId() {
		return txtId;
	}

	public void setTxtId(InputText txtId) {
		this.txtId = txtId;
	}

	public void setData(List<PreguntaDTO> preguntaDTO) {
		this.data = preguntaDTO;
	}

	public PreguntaDTO getSelectedPregunta() {
		return selectedPregunta;
	}

	public void setSelectedPregunta(PreguntaDTO pregunta) {
		this.selectedPregunta = pregunta;
	}

	public CommandButton getBtnSave() {
		return btnSave;
	}

	public void setBtnSave(CommandButton btnSave) {
		this.btnSave = btnSave;
	}

	public CommandButton getBtnModify() {
		return btnModify;
	}

	public void setBtnModify(CommandButton btnModify) {
		this.btnModify = btnModify;
	}

	public CommandButton getBtnDelete() {
		return btnDelete;
	}

	public void setBtnDelete(CommandButton btnDelete) {
		this.btnDelete = btnDelete;
	}

	public CommandButton getBtnClear() {
		return btnClear;
	}

	public void setBtnClear(CommandButton btnClear) {
		this.btnClear = btnClear;
	}

	public TimeZone getTimeZone() {
		return java.util.TimeZone.getDefault();
	}

	public IBusinessDelegatorView getBusinessDelegatorView() {
		return businessDelegatorView;
	}

	public Integer getIdTipoEvaluacion() {
		return idTipoEvaluacion;
	}

	public void setIdTipoEvaluacion(Integer idTipoEvaluacion) {
		this.idTipoEvaluacion = idTipoEvaluacion;
	}

	public Integer getIdPeriodo() {
		return idPeriodo;
	}

	public void setIdPeriodo(Integer idPeriodo) {
		this.idPeriodo = idPeriodo;
	}

	public void setBusinessDelegatorView(IBusinessDelegatorView businessDelegatorView) {
		this.businessDelegatorView = businessDelegatorView;
	}

	public boolean isShowDialog() {
		return showDialog;
	}

	public Map<String, Integer> getEstado() {
		return estado;
	}

	public void setEstado(Map<String, Integer> estado) {
		this.estado = estado;
	}

	public String getIdEstado() {
		return idEstado;
	}

	public void setIdEstado(String idEstado) {
		this.idEstado = idEstado;
	}

	public void setShowDialog(boolean showDialog) {
		this.showDialog = showDialog;
	}

	public Map<String, Integer> getPeriodo() {
		return periodo;
	}

	public void setPeriodo(Map<String, Integer> periodo) {
		this.periodo = periodo;
	}

	public List<Periodo> getListaPeriodos() {
		return listaPeriodos;
	}

	public void setListaPeriodos(List<Periodo> listaPeriodos) {
		this.listaPeriodos = listaPeriodos;
	}

	public List<Periodo> getListaEntidadPeriodos() {
		return listaEntidadPeriodos;
	}

	public void setListaEntidadPeriodos(List<Periodo> listaEntidadPeriodos) {
		this.listaEntidadPeriodos = listaEntidadPeriodos;
	}

	public Map<String, Integer> getTipoEvaluacio() {
		return tipoEvaluacio;
	}

	public void setTipoEvaluacio(Map<String, Integer> tipoEvaluacio) {
		this.tipoEvaluacio = tipoEvaluacio;
	}

	public List<TipoEvaluacion> getListaTipoEvaluaciones() {
		return listaTipoEvaluaciones;
	}

	public void setListaTipoEvaluaciones(List<TipoEvaluacion> listaTipoEvaluaciones) {
		this.listaTipoEvaluaciones = listaTipoEvaluaciones;
	}

	public List<TipoEvaluacion> getListaEntidadTipoEvaluacion() {
		return listaEntidadTipoEvaluacion;
	}

	public void setDataCopia(List<PreguntaDTO> dataCopia) {
		this.dataCopia = dataCopia;
	}

	public void setListaEntidadTipoEvaluacion(List<TipoEvaluacion> listaEntidadTipoEvaluacion) {
		this.listaEntidadTipoEvaluacion = listaEntidadTipoEvaluacion;
	}

	/**
	 * @return the txtTipoEvaluacion
	 */
	public InputText getTxtTipoEvaluacion() {
		return txtTipoEvaluacion;
	}

	/**
	 * @param txtTipoEvaluacion
	 *            the txtTipoEvaluacion to set
	 */
	public void setTxtTipoEvaluacion(InputText txtTipoEvaluacion) {
		this.txtTipoEvaluacion = txtTipoEvaluacion;
	}

}
