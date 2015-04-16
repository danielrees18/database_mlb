package bo;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


/**
 * Microsoft SQL Server DDL
 * 
 * playerId		numeric(10,0),
 *	teamId			numeric(10,0),
 *	year			numeric(4,0),
 *	primary key(playerId, teamId, year),
 *	foreign key(playerId) references player on delete cascade,
 *	foreign key(teamId, year) references teamseason on delete cascade);
 *
 */


@SuppressWarnings("serial")
@Entity(name = "teamseasonplayer")
public class TeamSeasonPlayer implements Serializable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Integer teamSeasonPlayerId;

}
