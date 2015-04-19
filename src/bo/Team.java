package bo;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

/**
 * Daniel Rees
 * Andrei Popa
 * Database CS3610 Final Project
 *
 */
@SuppressWarnings("serial")
@Entity(name = "team")
public class Team implements Serializable {

	/**
	 * SELECT clause for a team's teamID, name, and leageID from the MySQL Teams table, grouped by teamID
	 */
	public static String SQL_SELECT_TEAM = "SELECT franchID, teamID, name, lgID FROM Teams GROUP BY teamID";
	
	
	// Hibernate variables
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Integer teamId;
	
	@Column
	String name;
	@Column
	String league;
	@Column
	Integer yearFounded;
	@Column
	Integer yearLast;
	
	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "id.team")
	@Fetch(FetchMode.JOIN)
	Set<TeamSeason> seasons = new HashSet<TeamSeason>();
	
	@Transient
	ArrayList<String> teamIDs = new ArrayList<String>();
	
	// Constructors
	public Team(ResultSet rs) {
		try {
			this.name = rs.getString("name");
			this.league = rs.getString("lgID");
			this.teamIDs.add(rs.getString("teamID"));
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	// Public Methods
	@Override
	public int hashCode() {
		int hash = 0;
		if(this.teamId != null) hash += this.teamId.hashCode();
		
		return hash;
	}

	@Override
	public boolean equals(Object obj) {
		if(!(obj instanceof TeamSeason)){
			return false;
		}
		Team other = (Team) obj;
		return this.teamId == other.getTeamID();
	}
	
	/**
	 * Searches current seasons associated with the team for the given yearID. If a match
	 * is found, that team season is returned. If a match is not found then null is returned.
	 */
	public TeamSeason getTeamSeason(int seasonYear) {
		for(TeamSeason season : seasons) {
			if(season.getYear() == seasonYear) {
				return season;
			}
		}
		return null;
	}
	
	public void addSeason(TeamSeason season) {
		seasons.add(season);
	}
	
	public boolean hasTeamID(String tid) {
		for(String id : teamIDs) {
			if (tid.equalsIgnoreCase(id)) { return true; }
		}
		return false;
	}
	
	public void addTeamID(String tid) {
		this.teamIDs.add(tid);
	}

	// Getters
	public Integer getTeamID() {
		return teamId;
	}

	public String getName() {
		return name;
	}

	public String getLeague() {
		return league;
	}

	public Set<TeamSeason> getSeasons() {
		return seasons;
	}
	
	public Integer getYearFounded() {
		return yearFounded;
	}

	public Integer getYearLast() {
		return yearLast;
	}

	// Setters
	public void setTeamID(Integer teamID) {
		this.teamId = teamID;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setLeague(String league) {
		this.league = league;
	}

	public void setSeasons(Set<TeamSeason> seasons) {
		this.seasons = seasons;
	}

	public void setYearFounded(Integer yearFounded) {
		this.yearFounded = yearFounded;
	}

	public void setYearLast(Integer yearLast) {
		this.yearLast = yearLast;
	}
	
}
