package it.polito.tdp.ufo.model;

import java.time.Year;
import java.util.*;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleDirectedGraph;
import org.jgrapht.traverse.BreadthFirstIterator;

import it.polito.tdp.ufo.db.SightingsDAO;

public class Model {
	
	private List<AnnoCount> anniAvvistamenti;
	private Graph<State,DefaultEdge> graph;
	private StateIdMap stateIdMap ;
	private List<State> stati;
	
	private List<State> ottima;
		
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
	
		/**  SOLUZIONE 1Cii -- VALIDE ENTRAMBE  */
	
	public List<State> displayNeighbours(State stato) {

		List<State> vicini = new ArrayList<State>();

		// Ottengo la lista dei vicini di un vertice
		vicini.addAll(Graphs.neighborListOf(graph, stato));

		// Ritorno la lista dei vicini
		return vicini;
	}
	
	public List<State> displayNeighboursBreadthFirstIterator(State stato) {
		
		State start = stateIdMap.get(stato); // trova il vertice di partenza
		
		List<State> visitati = new ArrayList<>(); // visita il grafo
		
		BreadthFirstIterator<State, DefaultEdge> dfv = new BreadthFirstIterator<>(this.graph, start);
		
		dfv.next(); // salto il primo stato (quello di partenza)
		
		while (dfv.hasNext())
			visitati.add(dfv.next());

		return visitati;
	}

				/** --> PUNTO 2 <-- */ 
	
	public List<State> getPercorsoMax(State stato) {
		this.ottima = new ArrayList<>();
		List<State> parziale = new ArrayList<>();
		parziale.add(stato);
		
		cercaSequenza(parziale);
		
		return this.ottima;
	}
	
	private void cercaSequenza(List<State> parziale) {
		
		// caso terminale
		if(parziale.size() > ottima.size()) {
			this.ottima = new ArrayList<>(parziale) ;
		}
		
		// passo ricorsivo
		List<State> candidati = this.mostraSuccessori(parziale.get(parziale.size()-1));
		for(State prova : candidati) {
			if(!parziale.contains(prova)) {
				parziale.add(prova);
				cercaSequenza(parziale);
				parziale.remove(parziale.size()-1);
			}
		}
	}
	
	
	
}
