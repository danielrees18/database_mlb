package bo;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.persistence.Column;
import javax.persistence.Entity;

/**
 * Microsoft SQL Server DDL
 * 
 * create table teamseason (
 *	teamId			numeric(10,0)	references team on delete cascade,
 *	year			numeric(4,0),
 *	gamesPlayed		numeric(3,0),
 *	wins			numeric(4,0),
 *	losses			numeric(4,0),
 *	rank			numeric(2,0),
 *	totalAttendance numeric(7,0),
 *	primary key (teamId, year));
 *
 */

@Entity(name = "teamseason")
public class TeamSeason {
	
	/**
	 * SELECT clause for a team seasons year, games played, wins, losses, rank, and attendance from the MySQL Teams table
	 */
	public static String SQL_TEAM_SEASON_SELECT = "SELECT yearID, G, W, L, Rank, attendance FROM Teams ";
	
	
	// Hibernate variables
	@Column
	String teamID;
	@Column
	Integer year;
	@Column
	Integer gamesPlayed;
	@Column
	Integer gamesWon;
	@Column
	Integer gamesLost;
	@Column
	Integer teamRank;
	@Column
	Integer totalAttendance;
	
	
	// Constructors
	public TeamSeason(ResultSet rs, String tid) {
		try {
			teamID = tid;
			year = rs.getInt("yearID");
			gamesPlayed = rs.getInt("G");
			gamesWon = rs.getInt("W");
			gamesLost = rs.getInt("L");
			teamRank = rs.getInt("Rank");
			totalAttendance = rs.getInt("attendance");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	// Public Methods
	@Override
	public int hashCode() {
		int hash = 0;
		
		if(this.teamID != null) hash += this.getTeamID().hashCode();
		if(this.year != null) 	hash += this.getYear().hashCode();
		
		return hash;
	}

	@Override
	public boolean equals(Object obj) {
		if(!(obj instanceof TeamSeason)){
			return false;
		}
		TeamSeason other = (TeamSeason) obj;
		return (this.teamID.equalsIgnoreCase(other.getTeamID()) && this.year == other.getYear());
	}
	
	
	// Getters
	public String getTeamID() {
		return teamID;
	}

	public Integer getYear() {
		return year;
	}

	public Integer getGamesPlayed() {
		return gamesPlayed;
	}

	public Integer getGamesWon() {
		return gamesWon;
	}

	public Integer getGamesLost() {
		return gamesLost;
	}

	public Integer getTeamRank() {
		return teamRank;
	}

	public Integer getTotalAttendance() {
		return totalAttendance;
	}

	public void setTeamID(String teamId) {
		this.teamID = teamId;
	}

	
	// Setters
	public void setYear(Integer year) {
		this.year = year;
	}

	public void setGamesPlayed(Integer gamesPlayed) {
		this.gamesPlayed = gamesPlayed;
	}

	public void setGamesWon(Integer gamesWon) {
		this.gamesWon = gamesWon;
	}

	public void setGamesLost(Integer gamesLost) {
		this.gamesLost = gamesLost;
	}

	public void setTeamRank(Integer teamRank) {
		this.teamRank = teamRank;
	}

	public void setTotalAttendance(Integer totalAttendance) {
		this.totalAttendance = totalAttendance;
	}
}