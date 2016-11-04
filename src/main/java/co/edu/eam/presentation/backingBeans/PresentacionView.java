package co.edu.eam.presentation.backingBeans;

import co.edu.eam.exceptions.*;
import co.edu.eam.modelo.*;
import co.edu.eam.modelo.dto.PresentacionDTO;
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
import java.util.List;
import java.util.Set;
import java.util.TimeZone;

import javax.ejb.EJB;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;


/**
 * @author Zathura Code Generator http://zathuracode.org
 * www.zathuracode.org
 *
 */
@ManagedBean
@ViewScoped
public class PresentacionView implements Serializable {
    private static final long serialVersionUID = 1L;
    private static final Logger log = LoggerFactory.getLogger(PresentacionView.class);
    private InputText txtComentario;
    private InputText txtDocente;
    private InputText txtEstado;
    private InputText txtEvaluador;
    private InputText txtMateria;
    private InputText txtId_Evaluacion;
    private InputText txtId;
    private CommandButton btnSave;
    private CommandButton btnModify;
    private CommandButton btnDelete;
    private CommandButton btnClear;
    private List<PresentacionDTO> data;
    private PresentacionDTO selectedPresentacion;
    private Presentacion entity;
    private boolean showDialog;
    @EJB
    private IBusinessDelegatorView businessDelegatorView;

    public PresentacionView() {
        super();
    }

    public void rowEventListener(RowEditEvent e) {
        try {
            PresentacionDTO presentacionDTO = (PresentacionDTO) e.getObject();

            if (txtComentario == null) {
                txtComentario = new InputText();
            }

            txtComentario.setValue(presentacionDTO.getComentario());

            if (txtDocente == null) {
                txtDocente = new InputText();
            }

            txtDocente.setValue(presentacionDTO.getDocente());

            if (txtEstado == null) {
                txtEstado = new InputText();
            }

            txtEstado.setValue(presentacionDTO.getEstado());

            if (txtEvaluador == null) {
                txtEvaluador = new InputText();
            }

            txtEvaluador.setValue(presentacionDTO.getEvaluador());

            if (txtMateria == null) {
                txtMateria = new InputText();
            }

            txtMateria.setValue(presentacionDTO.getMateria());

            if (txtId_Evaluacion == null) {
                txtId_Evaluacion = new InputText();
            }

            txtId_Evaluacion.setValue(presentacionDTO.getId_Evaluacion());

            if (txtId == null) {
                txtId = new InputText();
            }

            txtId.setValue(presentacionDTO.getId());

            Integer id = FacesUtils.checkInteger(txtId);
            entity = businessDelegatorView.getPresentacion(id);

            action_modify();
        } catch (Exception ex) {
        }
    }

    public String action_new() {
        action_clear();
        selectedPresentacion = null;
        setShowDialog(true);

        return "";
    }

    public String action_clear() {
        entity = null;
        selectedPresentacion = null;

        if (txtComentario != null) {
            txtComentario.setValue(null);
            txtComentario.setDisabled(true);
        }

        if (txtDocente != null) {
            txtDocente.setValue(null);
            txtDocente.setDisabled(true);
        }

        if (txtEstado != null) {
            txtEstado.setValue(null);
            txtEstado.setDisabled(true);
        }

        if (txtEvaluador != null) {
            txtEvaluador.setValue(null);
            txtEvaluador.setDisabled(true);
        }

        if (txtMateria != null) {
            txtMateria.setValue(null);
            txtMateria.setDisabled(true);
        }

        if (txtId_Evaluacion != null) {
            txtId_Evaluacion.setValue(null);
            txtId_Evaluacion.setDisabled(true);
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
    

    public void listener_txtId() {
        try {
            Integer id = FacesUtils.checkInteger(txtId);
            entity = (id != null) ? businessDelegatorView.getPresentacion(id)
                                  : null;
        } catch (Exception e) {
            entity = null;
        }

        if (entity == null) {
            txtComentario.setDisabled(false);
            txtDocente.setDisabled(false);
            txtEstado.setDisabled(false);
            txtEvaluador.setDisabled(false);
            txtMateria.setDisabled(false);
            txtId_Evaluacion.setDisabled(false);
            txtId.setDisabled(false);
            btnSave.setDisabled(false);
        } else {
            txtComentario.setValue(entity.getComentario());
            txtComentario.setDisabled(false);
            txtDocente.setValue(entity.getDocente());
            txtDocente.setDisabled(false);
            txtEstado.setValue(entity.getEstado());
            txtEstado.setDisabled(false);
            txtEvaluador.setValue(entity.getEvaluador());
            txtEvaluador.setDisabled(false);
            txtMateria.setValue(entity.getMateria());
            txtMateria.setDisabled(false);
            txtId_Evaluacion.setValue(entity.getEvaluacion().getId());
            txtId_Evaluacion.setDisabled(false);
            txtId.setValue(entity.getId());
            txtId.setDisabled(true);
            btnSave.setDisabled(false);

            if (btnDelete != null) {
                btnDelete.setDisabled(false);
            }
        }
    }

    public String action_edit(ActionEvent evt) {
        selectedPresentacion = (PresentacionDTO) (evt.getComponent()
                                                     .getAttributes()
                                                     .get("selectedPresentacion"));
        txtComentario.setValue(selectedPresentacion.getComentario());
        txtComentario.setDisabled(false);
        txtDocente.setValue(selectedPresentacion.getDocente());
        txtDocente.setDisabled(false);
        txtEstado.setValue(selectedPresentacion.getEstado());
        txtEstado.setDisabled(false);
        txtEvaluador.setValue(selectedPresentacion.getEvaluador());
        txtEvaluador.setDisabled(false);
        txtMateria.setValue(selectedPresentacion.getMateria());
        txtMateria.setDisabled(false);
        txtId_Evaluacion.setValue(selectedPresentacion.getId_Evaluacion());
        txtId_Evaluacion.setDisabled(false);
        txtId.setValue(selectedPresentacion.getId());
        txtId.setDisabled(true);
        btnSave.setDisabled(false);
        setShowDialog(true);

        return "";
    }

    public String action_save() {
        try {
            if ((selectedPresentacion == null) && (entity == null)) {
                action_create();
            } else {
                action_modify();
            }

            data = null;
        } catch (Exception e) {
            FacesUtils.addErrorMessage(e.getMessage());
        }

        return "";
    }

    public String action_create() {
        try {
            entity = new Presentacion();

            Integer id = FacesUtils.checkInteger(txtId);

            entity.setComentario(FacesUtils.checkString(txtComentario));
            entity.setDocente(FacesUtils.checkString(txtDocente));
            entity.setEstado(FacesUtils.checkString(txtEstado));
            entity.setEvaluador(FacesUtils.checkString(txtEvaluador));
            entity.setId(id);
            entity.setMateria(FacesUtils.checkString(txtMateria));
            entity.setEvaluacion((FacesUtils.checkInteger(txtId_Evaluacion) != null)
                ? businessDelegatorView.getEvaluacion(FacesUtils.checkInteger(
                        txtId_Evaluacion)) : null);
            businessDelegatorView.savePresentacion(entity);
            FacesUtils.addInfoMessage(ZMessManager.ENTITY_SUCCESFULLYSAVED);
            action_clear();
        } catch (Exception e) {
            entity = null;
            FacesUtils.addErrorMessage(e.getMessage());
        }

        return "";
    }

    public String action_modify() {
        try {
            if (entity == null) {
                Integer id = new Integer(selectedPresentacion.getId());
                entity = businessDelegatorView.getPresentacion(id);
            }

            entity.setComentario(FacesUtils.checkString(txtComentario));
            entity.setDocente(FacesUtils.checkString(txtDocente));
            entity.setEstado(FacesUtils.checkString(txtEstado));
            entity.setEvaluador(FacesUtils.checkString(txtEvaluador));
            entity.setMateria(FacesUtils.checkString(txtMateria));
            entity.setEvaluacion((FacesUtils.checkInteger(txtId_Evaluacion) != null)
                ? businessDelegatorView.getEvaluacion(FacesUtils.checkInteger(
                        txtId_Evaluacion)) : null);
            businessDelegatorView.updatePresentacion(entity);
            FacesUtils.addInfoMessage(ZMessManager.ENTITY_SUCCESFULLYMODIFIED);
        } catch (Exception e) {
            data = null;
            FacesUtils.addErrorMessage(e.getMessage());
        }

        return "";
    }

    public String action_delete_datatable(ActionEvent evt) {
        try {
            selectedPresentacion = (PresentacionDTO) (evt.getComponent()
                                                         .getAttributes()
                                                         .get("selectedPresentacion"));

            Integer id = new Integer(selectedPresentacion.getId());
            entity = businessDelegatorView.getPresentacion(id);
            action_delete();
        } catch (Exception e) {
            FacesUtils.addErrorMessage(e.getMessage());
        }

        return "";
    }

    public String action_delete_master() {
        try {
            Integer id = FacesUtils.checkInteger(txtId);
            entity = businessDelegatorView.getPresentacion(id);
            action_delete();
        } catch (Exception e) {
            FacesUtils.addErrorMessage(e.getMessage());
        }

        return "";
    }

    public void action_delete() throws Exception {
        try {
            businessDelegatorView.deletePresentacion(entity);
            FacesUtils.addInfoMessage(ZMessManager.ENTITY_SUCCESFULLYDELETED);
            action_clear();
            data = null;
        } catch (Exception e) {
            throw e;
        }
    }

    public String action_closeDialog() {
        setShowDialog(false);
        action_clear();

        return "";
    }

    public String actionDeleteDataTableEditable(ActionEvent evt) {
        try {
            selectedPresentacion = (PresentacionDTO) (evt.getComponent()
                                                         .getAttributes()
                                                         .get("selectedPresentacion"));

            Integer id = new Integer(selectedPresentacion.getId());
            entity = businessDelegatorView.getPresentacion(id);
            businessDelegatorView.deletePresentacion(entity);
            data.remove(selectedPresentacion);
            FacesUtils.addInfoMessage(ZMessManager.ENTITY_SUCCESFULLYDELETED);
            action_clear();
        } catch (Exception e) {
            FacesUtils.addErrorMessage(e.getMessage());
        }

        return "";
    }

    public String action_modifyWitDTO(String comentario, String docente,
        String estado, String evaluador, Integer id, String materia,
        Integer id_Evaluacion) throws Exception {
        try {
            entity.setComentario(FacesUtils.checkString(comentario));
            entity.setDocente(FacesUtils.checkString(docente));
            entity.setEstado(FacesUtils.checkString(estado));
            entity.setEvaluador(FacesUtils.checkString(evaluador));
            entity.setMateria(FacesUtils.checkString(materia));
            businessDelegatorView.updatePresentacion(entity);
            FacesUtils.addInfoMessage(ZMessManager.ENTITY_SUCCESFULLYMODIFIED);
        } catch (Exception e) {
            //renderManager.getOnDemandRenderer("PresentacionView").requestRender();
            FacesUtils.addErrorMessage(e.getMessage());
            throw e;
        }

        return "";
    }

    public InputText getTxtComentario() {
        return txtComentario;
    }

    public void setTxtComentario(InputText txtComentario) {
        this.txtComentario = txtComentario;
    }

    public InputText getTxtDocente() {
        return txtDocente;
    }

    public void setTxtDocente(InputText txtDocente) {
        this.txtDocente = txtDocente;
    }

    public InputText getTxtEstado() {
        return txtEstado;
    }

    public void setTxtEstado(InputText txtEstado) {
        this.txtEstado = txtEstado;
    }

    public InputText getTxtEvaluador() {
        return txtEvaluador;
    }

    public void setTxtEvaluador(InputText txtEvaluador) {
        this.txtEvaluador = txtEvaluador;
    }

    public InputText getTxtMateria() {
        return txtMateria;
    }

    public void setTxtMateria(InputText txtMateria) {
        this.txtMateria = txtMateria;
    }

    public InputText getTxtId_Evaluacion() {
        return txtId_Evaluacion;
    }

    public void setTxtId_Evaluacion(InputText txtId_Evaluacion) {
        this.txtId_Evaluacion = txtId_Evaluacion;
    }

    public InputText getTxtId() {
        return txtId;
    }

    public void setTxtId(InputText txtId) {
        this.txtId = txtId;
    }

    public List<PresentacionDTO> getData() {
        try {
            if (data == null) {
                data = businessDelegatorView.getDataPresentacion();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return data;
    }

    public void setData(List<PresentacionDTO> presentacionDTO) {
        this.data = presentacionDTO;
    }

    public PresentacionDTO getSelectedPresentacion() {
        return selectedPresentacion;
    }

    public void setSelectedPresentacion(PresentacionDTO presentacion) {
        this.selectedPresentacion = presentacion;
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

    public void setBusinessDelegatorView(
        IBusinessDelegatorView businessDelegatorView) {
        this.businessDelegatorView = businessDelegatorView;
    }

    public boolean isShowDialog() {
        return showDialog;
    }

    public void setShowDialog(boolean showDialog) {
        this.showDialog = showDialog;
    }
}
