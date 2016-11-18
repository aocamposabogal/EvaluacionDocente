package co.edu.eam.presentation.backingBeans;

import co.edu.eam.modelo.*;
import co.edu.eam.modelo.dto.RespuestaDTO;
import co.edu.eam.presentation.businessDelegate.*;
import co.edu.eam.utilities.*;
import org.primefaces.component.commandbutton.CommandButton;
import org.primefaces.component.inputtext.InputText;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.TimeZone;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/**
 * @author Zathura Code Generator http://zathuracode.org www.zathuracode.org
 *
 */
@ManagedBean
@ViewScoped
public class RespuestaView implements Serializable {
	private static final long serialVersionUID = 1L;
	private InputText txtNota;
	private InputText txtId_Pregunta;
	private InputText txtId_Presentacion;
	private InputText txtId;

	private CommandButton btnSave;
	private CommandButton btnModify;
	private CommandButton btnDelete;
	private CommandButton btnClear;
	private List<RespuestaDTO> data;
	private RespuestaDTO selectedRespuesta;
	private Respuesta entity;
	private boolean showDialog;

	// nota refiere al tipo de calificacion de una pregunta
	private Integer nota;

	@EJB
	private IBusinessDelegatorView businessDelegatorView;

	public RespuestaView() {
		super();
	}

	public String action_clear() {
		entity = null;
		selectedRespuesta = null;

		if (txtNota != null) {
			txtNota.setValue(null);
			txtNota.setDisabled(true);
		}

		if (txtId_Pregunta != null) {
			txtId_Pregunta.setValue(null);
			txtId_Pregunta.setDisabled(true);
		}

		if (txtId_Presentacion != null) {
			txtId_Presentacion.setValue(null);
			txtId_Presentacion.setDisabled(true);
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

	/**
	 * 
	 * <p>
	 * <b>Este método se encarga de llamar al metodo que buscara la respuesta a
	 * modificar su nota, y ademas procede a realizar el update en la base de
	 * datos</b>
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
	 */
	public void find_Entity() {
		try {
			Integer id = FacesUtils.checkInteger(txtId_Pregunta);
			List<RespuestaDTO> info = new LinkedList<>();
			info = getDataRespuesta(id, 1);
			data = null;
			entity = new Respuesta();
			if (!info.isEmpty()) {
				entity.setId(info.get(0).getId());
				entity.setPregunta((info.get(0).getId_Pregunta() != null)
						? businessDelegatorView.getPregunta(info.get(0).getId_Pregunta()) : null);
				entity.setPresentacion((info.get(0).getId_Presentacion() != null)
						? businessDelegatorView.getPresentacion(info.get(0).getId_Presentacion()) : null);
				entity.setNota(nota);
				businessDelegatorView.updateRespuesta(entity);

			}
		} catch (Exception e) {
			entity = null;
			e.printStackTrace();
		}
	}

	public Integer generarid() {
		Random r = new Random();
		return r.nextInt(1000000);
	}

	/**
	 * 
	 * <p>
	 * <b>Este método se encarga de llamar al metodo que buscara la respuesta a
	 * modificar su nota, y ademas procede a realizar el update en la base de
	 * datos</b>
	 * </p>
	 * <br/>
	 * <ul>
	 * <li></li>
	 * </ul>
	 * <br/>
	 * 
	 * @author EAM <br/>
	 *         Daniel Henao <br/>
	 *         Email: danielhenao22@outlook.com <br/>
	 *         07/11/2016
	 * @version 1.0
	 * @throws Exception
	 */
	public String action_create_vacio(Integer presentacion, Integer evaluacion, Integer tipoEvaluacion)
			throws Exception {
		List<Pregunta> preguntas = new ArrayList<Pregunta>();
		preguntas = businessDelegatorView.getPregunta();
		System.out.println("********" + preguntas.size());
		System.out.flush();
		for (int i = 0; i < preguntas.size(); i++) {
			System.out.println("********" + preguntas.get(i).getTipoEvaluacion());
			System.out.flush();
			// if (preguntas.get(i).getTipoEvaluacion().getId() ==
			// tipoEvaluacion && preguntas.get(i).getEstado() == "1") {
			try {
				entity = new Respuesta();
				Integer id = generarid();
				entity.setId(id);
				entity.setNota(0);
				entity.setPregunta((preguntas.get(i).getId() != null)
						? businessDelegatorView.getPregunta(preguntas.get(i).getId()) : null);
				entity.setPresentacion(
						(presentacion != null) ? businessDelegatorView.getPresentacion(presentacion) : null);
				businessDelegatorView.saveRespuesta(entity);
				action_clear();
			} catch (Exception e) {
				entity = null;
				FacesUtils.addErrorMessage(e.getMessage());
			}
		}

		// }

		return "";
	}

	/**
	 * 
	 * <p>
	 * <b>Metodo que traera la informacion del objeto Respuesta, segun el id de
	 * la pregunta y el id de la presentación que en ese momento se este
	 * dando</b>
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
	 */
	public List<RespuestaDTO> getDataRespuesta(Integer idPregunta, Integer idPresentacion) {
		try {
			if (data == null) {
				data = businessDelegatorView.getDataRespuesta(idPregunta, idPresentacion);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return data;
	}

	public InputText getTxtNota() {
		return txtNota;
	}

	public void setTxtNota(InputText txtNota) {
		this.txtNota = txtNota;
	}

	public InputText getTxtId_Pregunta() {
		return txtId_Pregunta;
	}

	public void setTxtId_Pregunta(InputText txtId_Pregunta) {
		this.txtId_Pregunta = txtId_Pregunta;
	}

	public InputText getTxtId_Presentacion() {
		return txtId_Presentacion;
	}

	public void setTxtId_Presentacion(InputText txtId_Presentacion) {
		this.txtId_Presentacion = txtId_Presentacion;
	}

	public InputText getTxtId() {
		return txtId;
	}

	public void setTxtId(InputText txtId) {
		this.txtId = txtId;
	}

	public List<RespuestaDTO> getData() {
		try {
			if (data == null) {
				data = businessDelegatorView.getDataRespuesta();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return data;
	}

	public void setData(List<RespuestaDTO> respuestaDTO) {
		this.data = respuestaDTO;
	}

	public RespuestaDTO getSelectedRespuesta() {
		return selectedRespuesta;
	}

	public void setSelectedRespuesta(RespuestaDTO respuesta) {
		this.selectedRespuesta = respuesta;
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

	public void setBusinessDelegatorView(IBusinessDelegatorView businessDelegatorView) {
		this.businessDelegatorView = businessDelegatorView;
	}

	public boolean isShowDialog() {
		return showDialog;
	}

	public void setShowDialog(boolean showDialog) {
		this.showDialog = showDialog;
	}

	public Integer getNota() {
		return nota;
	}

	public void setNota(Integer nota) {
		this.nota = nota;
	}

}
