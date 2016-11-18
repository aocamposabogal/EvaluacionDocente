package co.edu.eam.ws;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.apache.http.HttpResponse;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import co.edu.eam.modelo.dto.MateriaDTO;
import co.edu.eam.modelo.dto.DocenteDTO;
public class WebServiceClient {

	static JSONArray finalResult = null;
	
	

	public static JSONArray invocarServicioMateriasPorCodigo(String codigo, String combo) {

		DefaultHttpClient httpClient = new DefaultHttpClient();
		try {

			HttpPost request = new HttpPost("http://www.eam.edu.co/service/servicio.php");
			request.addHeader("accept", "application/json");
			List<BasicNameValuePair> nvp = new ArrayList<BasicNameValuePair>();
			nvp.add(new BasicNameValuePair("codig", codigo));
			nvp.add(new BasicNameValuePair("signal", combo));
			//
			request.setEntity(new UrlEncodedFormEntity(nvp, HTTP.UTF_8));
			HttpResponse response = httpClient.execute(request);

			BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
			StringBuilder content = new StringBuilder();
			String line;
			while (null != (line = rd.readLine())) {
				content.append(line);
			}
			Object obj = JSONValue.parse(content.toString());
			finalResult = (JSONArray) obj;

		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		} finally {
			httpClient.getConnectionManager().shutdown();
		}
		return finalResult;
	}
	
	
	/**
	 * 
	 * @param codigo Codigo docente
	 * @return Listado de materias por codigo docente
	 */

	public static List<MateriaDTO> consultarMateriasPorCodigoDocente(String codigo) {

		ArrayList<MateriaDTO> listadoMaterias = new ArrayList<>();
		
		//se llena las lista con todos las materias en un objeto de tipo httpClient
		invocarServicioMateriasPorCodigo(codigo,"1");
		
		// se separan las materias una a una y se agregan a una lista en un objeto tipo MateriaDTO
		@SuppressWarnings("rawtypes")
		Iterator i = finalResult.iterator();
		while (i.hasNext()) {
			JSONObject result = (JSONObject) i.next();
			MateriaDTO materia = new MateriaDTO();
			materia.setNombreDocente((String) result.get("nombre_doc"));
			materia.setCodigo((String) result.get("espacio"));
			materia.setNombre((String) result.get("nom_espacio"));
			materia.setSemestre((String) result.get("sem"));
			materia.setGrupo((String) result.get("grp"));
			materia.setJornada((String) result.get("jorn"));
			listadoMaterias.add(materia);
		}
		
		return listadoMaterias;

	}
	
	public List<MateriaDTO> consultarMateriasPorCodigoEstudiante(String codigo) {

		ArrayList<MateriaDTO> listadoMaterias = new ArrayList<>();
		
		//se llena las lista con todos las materias en un objeto de tipo httpClient
		invocarServicioMateriasPorCodigo(codigo,"1");
		
		// se separan las materias una a una y se agregan a una lista en un objeto tipo MateriaDTO
		@SuppressWarnings("rawtypes")
		Iterator i = finalResult.iterator();
		while (i.hasNext()) {
			JSONObject result = (JSONObject) i.next();
			MateriaDTO materia = new MateriaDTO();
			materia.setNombreDocente((String) result.get("nomprofe"));
			materia.setCodigo((String) result.get("codigom"));
			materia.setNombre((String) result.get("nombrem"));
			materia.setSemestre((String) result.get("sem"));
			materia.setGrupo((String) result.get("grp"));
			materia.setJornada((String) result.get("jorn"));
			listadoMaterias.add(materia);
		}
		
		return listadoMaterias;

	}
	
	/**
	 * Metodo para conocer los datos de los profesor activos por programa
	 * @param codigo, codigo de la materia
	 * @param combo, numero que se obtiene en el combo para filtrar la busqueda
	 * @return, retorna los docentes activos del programa.
	 */
	public static List<DocenteDTO> consultarDocentesActivosPorPrograma(String codigo) {

		ArrayList<DocenteDTO> listadoDocente = new ArrayList<>();
		
		//se llena las lista con todos los docentes en un objeto de tipo httpClient
		invocarServicioMateriasPorCodigo(codigo,"3");
		
		// se separan los docentes uno a uno y se agregan a una lista en un objeto tipo DocenteDTO
		@SuppressWarnings("rawtypes")
		Iterator i = finalResult.iterator();
		while (i.hasNext()) {
			JSONObject result = (JSONObject) i.next();
			DocenteDTO doc = new DocenteDTO();
			doc.setCodigoDocente((String)result.get("codigoprofe"));
			doc.setCodigoPrograma((long)result.get("programa"));
			doc.setNombreDocente((String)result.get("nomcompleto"));
			doc.setNombrePrograma((String)result.get("nprograma"));
		
			listadoDocente.add(doc);
		}
		
		return listadoDocente;

	}
	
	/**
	 * Metodo para obtener los datos de un docente consultando la materia.
	 * @param codigo, Codigo de la materia.
	 * @param combo, Numero que se obtiene al filtrar la busqueda.
	 * @return, Retorna los datos del docente o los docentes.
	 */
	public static List<DocenteDTO> consultarDocentesPorMateria(String codigo) {

		ArrayList<DocenteDTO> listadoDocente = new ArrayList<>();
		
		//se llena las lista con todos las materias en un objeto de tipo httpClient
		invocarServicioMateriasPorCodigo(codigo,"2");
		
		// se separan los docentes uno a uno y se agregan a una lista en un objeto tipo DocenteDTO
		@SuppressWarnings("rawtypes")
		Iterator i = finalResult.iterator();
		while (i.hasNext()) {
			JSONObject result = (JSONObject) i.next();
			DocenteDTO doc = new DocenteDTO();
			doc.setCodigoMateria((String)result.get("espacio"));
			doc.setNombreMateria((String)result.get("nespacio"));
			doc.setCodigoDocente((String)result.get("codigoprofe"));
			doc.setNombreDocente((String)result.get("nomcompleto"));
			doc.setGrupo((String)result.get("grupo"));
			doc.setJornada((String)result.get("jornada"));
		
			listadoDocente.add(doc);
		}
		
		return listadoDocente;

	}
	
	public static void main(String[] args){
		/*
		List<MateriaDTO> materias=consultarMateriasPorCodigoDocente("2706");
		for (MateriaDTO materiaDTO : materias) {
			System.out.println(materiaDTO.getNombre());
		}
		*/
		
		/*
		List<DocenteDTO> doc= consultarDocentesActivosPorPrograma("23","3");
		for (DocenteDTO docts : doc) {
			System.out.println(docts.getCodigoDocente()+","+ docts.getNombreDocente());
		}
		*/
		
		/*
		List<DocenteDTO> doc= consultarDocentesPorMateria("20755", "2");
		for (DocenteDTO docts : doc) {
			System.out.println(docts.getCodigoDocente()+","+ docts.getNombreDocente());
		}
		*/
		
		
		
	}

}