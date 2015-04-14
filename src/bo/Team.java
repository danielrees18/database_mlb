package bo;

import javax.persistence.Entity;

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

}
