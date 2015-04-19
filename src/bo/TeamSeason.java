package bo;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

/**
 * Daniel Rees
 * Andrei Popa
 * Database CS3610 Final Project
 *
 */
@SuppressWarnings("serial")
@Entity(name = "teamseason")
public class TeamSeason implements Serializable {
	
	/**
	 * SELECT clause for a team seasons year, games played, wins, losses, rank, and attendance from the MySQL Teams table
	 */
	public static String SQL_TEAM_SEASON_SELECT = "SELECT name, yearID, G, W, L, Rank, attendance FROM Teams ";
	
	/**
	 * Static class to represent the composite 
	 * primary key of the TeamSeason entity set
	 */
	@Embeddable
	protected class TeamSeasonId implements Serializable {
		@ManyToOne
		@JoinColumn(name = "teamId", referencedColumnName = "teamId", insertable = false, updatable = false)
		Team team;
		@Column(name="year")
		Integer yearId;
		
		public TeamSeasonId(Team team, Integer yr) {
			this.team = team;
			this.yearId = yr;
		}
		
		@Override
		public boolean equals(Object obj) {
			if(!(obj instanceof TeamSeasonId)){
				return false;
			}
			TeamSeasonId other = (TeamSeasonId)obj;
			return (this.team == other.team &&
					this.yearId == other.yearId);
		}
		 
		@Override
		public int hashCode() {
			Integer hash = 0;
			if (this.team != null) hash += this.team.hashCode();
			if (this.yearId != null)   hash += this.yearId.hashCode();
			return hash;
		}
	}

	// Hibernate variables
	@EmbeddedId
	TeamSeasonId id;
	@Column
	Integer gamesPlayed;
	@Column
	Integer wins;
	@Column
	Integer losses;
	@Column
	Integer rank;
	@Column
	Integer totalAttendance;
	
	/**
	 * Set up the Many-to-Many association table relationship between
	 * a team season and a player.
	 */
	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(
		name="teamseasonplayer",
		joinColumns = {
			@JoinColumn(name="year", referencedColumnName="year"),
			@JoinColumn(name="teamId", referencedColumnName="teamId")
		},
		inverseJoinColumns = @JoinColumn(name="playerId", referencedColumnName="playerId")
	)	
	Set<Player> players = new HashSet<Player>();
	
	
	// Constructors
	public TeamSeason(ResultSet rs, Team team) {
		try {
			id = new TeamSeasonId(team, rs.getInt("yearID"));
			gamesPlayed = rs.getInt("G");
			wins = rs.getInt("W");
			losses = rs.getInt("L");
			rank = rs.getInt("Rank");
			totalAttendance = rs.getInt("attendance");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	
	// Public Methods
	public void addPlayerToRoster(Player player) {
		this.players.add(player);
	}
	
	@Override
	public int hashCode() {
		int hash = 0;
		
		if(this.getTeamID() != null) hash += this.getTeamID().hashCode();
		if(this.getYear() != null) 	hash += this.getYear().hashCode();
		
		return hash;
	}

	@Override
	public boolean equals(Object obj) {
		if(!(obj instanceof TeamSeason)){
			return false;
		}
		TeamSeason other = (TeamSeason) obj;
		return (this.getTeamID() == other.getTeamID() && this.getYear() == other.getYear());
	}
	

	// Getters
	public Integer getTeamID() {
		return id.team.getTeamID();
	}

	public Integer getYear() {
		return id.yearId;
	}

	public Integer getGamesPlayed() {
		return gamesPlayed;
	}

	public Integer getGamesWon() {
		return wins;
	}

	public Integer getGamesLost() {
		return losses;
	}

	public Integer getTeamRank() {
		return rank;
	}

	public Integer getTotalAttendance() {
		return totalAttendance;
	}
	
	public TeamSeasonId getTeamSeasonId() {
		return id;
	}
	
	public Set<Player> getRoster() {
		return players;
	}
	
	// Setters
	public void setTeamID(Integer teamId) {
		this.id.team.setTeamID(teamId);
	}

	public void setYear(Integer year) {
		this.id.yearId = year;
	}

	public void setGamesPlayed(Integer gamesPlayed) {
		this.gamesPlayed = gamesPlayed;
	}

	public void setGamesWon(Integer gamesWon) {
		this.wins = gamesWon;
	}

	public void setGamesLost(Integer gamesLost) {
		this.losses = gamesLost;
	}

	public void setTeamRank(Integer teamRank) {
		this.rank = teamRank;
	}

	public void setTotalAttendance(Integer totalAttendance) {
		this.totalAttendance = totalAttendance;
	}
	
	public void setTeamSeasonId(TeamSeasonId teamSeasonId) {
		this.id = teamSeasonId;
	}
	
	public void setRoster(Set<Player> players) {
		this.players = players;
	}
}