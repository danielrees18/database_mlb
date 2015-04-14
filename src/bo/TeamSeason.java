package bo;

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

}
