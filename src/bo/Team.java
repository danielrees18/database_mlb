package bo;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;


/**
 * 
 * Microsoft SQL Server DDL
 *
 * create table team (
 *	teamId			numeric(10,0) 	IDENTITY(10000,5) primary key,
 *	name			varchar(50)		not null,
 *	league			varchar(2)		not null,
 *	yearFounded		numeric(4,0),
 *	yearLast		numeric(4,0));
 *
 */

@Entity(name = "team")
public class Team {

	/**
	 * SELECT clause for a team's teamID, name, and leageID from the MySQL Teams table, grouped by teamID
	 */
	public static String SQL_SELECT_TEAM = "SELECT teamID, name, lgID FROM Teams GROUP BY teamID";
	
	
	// Hibernate variables
	@Column
	String teamID;
	@Column
	String name;
	@Column
	String league;
	
	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "year.teamID")
	Set<TeamSeason> seasons = new HashSet<TeamSeason>();
	
	
	// Constructors
	public Team(ResultSet rs) {
		try {
			this.teamID = rs.getString("teamID");
			this.name = rs.getString("name");
			this.league = rs.getString("lgID");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	// Public Methods
	@Override
	public int hashCode() {
		int hash = 0;
		if(this.teamID != null) hash += this.teamID.hashCode();
		
		return hash;
	}

	@Override
	public boolean equals(Object obj) {
		if(!(obj instanceof TeamSeason)){
			return false;
		}
		Team other = (Team) obj;
		return this.teamID.equalsIgnoreCase(other.getTeamID());
	}
	
	/**
	 * Searches current seasons associated with the team for the given yearID. If a match
	 * is found, that team season is returned. If a match is not found then null is returned.
	 * 
	 * @param seasonYear	-	yearID of the season being searched for
	 * @return	-	TeamSeason obect if year is found. Null otherwise
	 */
	public TeamSeason getTeamSeason(int seasonYear) {
		for(TeamSeason season : seasons) {
			if(season.getYear() == seasonYear) {
				return season;
			}
		}
		return null;
	}
	
	
	// Getters
	public void addSeason(TeamSeason season) {
		seasons.add(season);
	}

	public String getTeamID() {
		return teamID;
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

	
	// Setters
	public void setTeamID(String teamID) {
		this.teamID = teamID;
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
	
	
}
