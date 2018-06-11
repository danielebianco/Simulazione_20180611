package it.polito.tdp.ufo.model;

import java.time.Year;
import java.util.List;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleDirectedGraph;

import it.polito.tdp.ufo.db.SightingsDAO;

public class Model {
	
	private List<AnnoCount> anniAvvistamenti;
	private Graph<State,DefaultEdge> graph;
	private StateIdMap stateIdMap ;
	
	
	
	public Model() {
		this.stateIdMap = new StateIdMap();
	}

	public List<AnnoCount> getAvvistamenti(){
		SightingsDAO dao = new SightingsDAO();
		this.anniAvvistamenti = dao.getAnni();
		return anniAvvistamenti;
	}
	
	public void creaGrafo(Year anno) {
		this.graph = new SimpleDirectedGraph<>(DefaultEdge.class);
		SightingsDAO dao = new SightingsDAO();
		List<State> stati = dao.getStati(anno, stateIdMap);
		Graphs.addAllVertices(graph, stati);
		for (StateResult sr : dao.getStateResults(anno, stateIdMap)) {
			Graphs.addEdgeWithVertices(graph, sr.getStato1(), sr.getStato2());
		}
		
		System.out.format("Grafo creato: %d archi, %d nodi\n",
				graph.edgeSet().size(), graph.vertexSet().size());
	}
	
}
