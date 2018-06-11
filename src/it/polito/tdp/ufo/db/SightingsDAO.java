package it.polito.tdp.ufo.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Year;
import java.util.ArrayList;
import java.util.List;

import it.polito.tdp.ufo.model.AnnoCount;
import it.polito.tdp.ufo.model.Sighting;
import it.polito.tdp.ufo.model.State;
import it.polito.tdp.ufo.model.StateIdMap;
import it.polito.tdp.ufo.model.StateResult;

public class SightingsDAO {
	
	public List<Sighting> getSightings() {
		String sql = "SELECT * FROM sighting" ;
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			
			List<Sighting> list = new ArrayList<>() ;
			
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				list.add(new Sighting(res.getInt("id"),
						res.getTimestamp("datetime").toLocalDateTime(),
						res.getString("city"), 
						res.getString("state"), 
						res.getString("country"),
						res.getString("shape"),
						res.getInt("duration"),
						res.getString("duration_hm"),
						res.getString("comments"),
						res.getDate("date_posted").toLocalDate(),
						res.getDouble("latitude"), 
						res.getDouble("longitude"))) ;
			}
			
			conn.close();
			return list ;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null ;
		}
	}

	public List<AnnoCount> getAnni() {
		String sql = "select distinct year(datetime) as anno, count(id) as cnt " + 
				"from sighting " + 
				"where country = 'us' " + 
				"group by anno " + 
				"order by anno asc" ;
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			
			List<AnnoCount> list = new ArrayList<>() ;
			
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				list.add(new AnnoCount(Year.of(res.getInt("anno")), res.getInt("cnt"))) ;
			}
			
			conn.close();
			return list ;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null ;
		}
	}
	
	public List<State> getStati(Year anno, StateIdMap stateIdMap) {
		String sql = "select distinct state " + 
				"from sighting " + 
				"where country = 'us' " + 
				"and year(datetime) = ? " + 
				"order by state asc";
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			
			List<State> list = new ArrayList<>() ;
			
			st.setInt(1, anno.getValue());
			
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				list.add(stateIdMap.get(new State(res.getString("state")))) ;
			}
			
			conn.close();
			return list ;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null ;
		}
	}

	public List<StateResult> getStateResults(Year anno, StateIdMap stateIdMap) {
		String sql = "select s1.state as st1, s2.state as st2, count(*) as c " + 
				"from sighting s1, sighting s2 " + 
				"where year(s1.datetime) = year(s2.datetime) " + 
				"and year(s1.datetime) = ? " + 
				"and s1.country = 'us' " + 
				"and s2.country = 'us' " +
				"and s1.state <> s2.state " +
				"and s2.datetime > s1.datetime " + 
				"group by st1, st2";
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			
			List<StateResult> list = new ArrayList<>() ;
			
			st.setInt(1, anno.getValue());
			
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				
				State s1 = stateIdMap.get(res.getString("st1"));
				State s2 = stateIdMap.get(res.getString("st2"));
				
				if (s1 == null || s2 == null) {
					System.err.format("Skipping %d %d\n", res.getString("st1"), res.getString("st2"));
				} else {
					list.add(new StateResult(s1, s2 , res.getInt("c")));
				}
				
			}
			
			conn.close();
			return list ;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null ;
		}
	}

}
