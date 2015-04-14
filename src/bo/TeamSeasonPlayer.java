package bo;

import javax.persistence.Entity;


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

@Entity(name = "teamseasonplayer")
public class TeamSeasonPlayer {

}
