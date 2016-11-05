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

public class WebServiceClient {

	JSONArray finalResult = null;

	private JSONArray invocarServicioMateriasPorCodigo(String codigo) {

		DefaultHttpClient httpClient = new DefaultHttpClient();
		try {

			HttpPost request = new HttpPost("http://www.eam.edu.co/service/servicio.php");
			request.addHeader("accept", "application/json");
			List<BasicNameValuePair> nvp = new ArrayList<BasicNameValuePair>();
			nvp.add(new BasicNameValuePair("codig", "2706"));
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

	public List<MateriaDTO> consultarMateriasPorCodigoDocente(String codigo) {

		ArrayList<MateriaDTO> listadoMaterias = new ArrayList<>();
		
		invocarServicioMateriasPorCodigo(codigo);
		
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

}