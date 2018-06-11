package it.polito.tdp.ufo.model;

import java.time.Year;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleDirectedGraph;

import it.polito.tdp.ufo.db.SightingsDAO;

public class Model {
	
	private List<AnnoCount> anniAvvistamenti;
	private Graph<State,DefaultEdge> graph;
	private StateIdMap stateIdMap ;
	private List<State> stati;
		
	public Model() {
		this.stateIdMap = new StateIdMap();
		this.stati = new ArrayList<State>();
	}

	public List<AnnoCount> getAvvistamenti(){
		SightingsDAO dao = new SightingsDAO();
		this.anniAvvistamenti = dao.getAnni();
		return anniAvvistamenti;
	}
	
	public List<State> getStati() {
		return stati;
	}
	
	public void creaGrafo(Year anno) {
		this.graph = new SimpleDirectedGraph<>(DefaultEdge.class);
		SightingsDAO dao = new SightingsDAO();
		stati = dao.getStati(anno, stateIdMap);
		Graphs.addAllVertices(graph, stati);
		for (StateResult sr : dao.getStateResults(anno, stateIdMap)) {
			Graphs.addEdgeWithVertices(graph, sr.getStato1(), sr.getStato2());
		}
		
		System.out.format("Grafo creato: %d archi, %d nodi\n",
				graph.edgeSet().size(), graph.vertexSet().size());
	}
	
	public List<State> mostraPredecessori(State stato) {
		return Graphs.predecessorListOf(graph, stato);
	}

	public List<State> mostraSuccessori(State stato) {
		return Graphs.successorListOf(graph, stato);
	}	
			
	public Set<State> displayNeighbours(State stato) {

		Set<State> vicini = new HashSet<State>();

		// Ottengo la lista dei vicini di un vertice
		vicini.addAll(Graphs.neighborListOf(graph, stato));

		// Ritorno la lista dei vicini
		return vicini;
	}

	public String getNumNeighbours(State stato) {
		return String.format("%d", graph.degreeOf(stato));
	}
	
//	public Set<State> displayNeighboursBreadthFirstIterator(State stato) {
//
//		// trova il vertice di partenza
//		State start = stateIdMap.get(stato);
//
//		// visita il grafo
//		Set<State> visitati = new HashSet<>();
//		BreadthFirstIterator<State, DefaultEdge> dfv = new BreadthFirstIterator<>(this.graph, start);
//		while (dfv.hasNext())
//			visitati.add(dfv.next());
//
//		return visitati;
//	}

}
