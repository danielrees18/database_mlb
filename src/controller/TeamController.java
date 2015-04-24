package controller;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import bo.Player;
import bo.PlayerSeason;
import bo.Team;
import bo.TeamSeason;
import dataaccesslayer.HibernateUtil;
import view.TeamView;

public class TeamController extends BaseController {

	@Override
	public void init(String query) {
		System.out.println("building dynamic html for team");
        view = new TeamView();
        process(query);
	}

	@Override
	protected void performAction() {	
		String action = keyVals.get("action");
        System.out.println("playercontroller performing action: " + action);
        if (action.equalsIgnoreCase(ACT_SEARCHFORM)) {
            processSearchForm();
        } else if (action.equalsIgnoreCase(ACT_SEARCH)) {
            processSearch();
        } else if (action.equalsIgnoreCase(ACT_DETAIL)) {
            processDetails();
        } else if (action.equalsIgnoreCase(ACT_ROSTER)) {
            processRoster();
        } 
	}
	
	protected void processSearchForm() {
        view.buildSearchForm();
    }
	
	protected final void processSearch() {
        String name = keyVals.get("name");
        if (name == null) {
            return;
        }
        String v = keyVals.get("exact");
        boolean exact = (v != null && v.equalsIgnoreCase("on"));
        List<Team> bos = HibernateUtil.retrieveTeamsByName(name, exact);
        view.printSearchResultsMessage(name, exact);
        buildSearchResultsTableTeam(bos);
        view.buildLinkToSearch();
    }
	
	protected final void processDetails() {
        String id = keyVals.get("id");
        if (id == null) {
            return;
        }
        Team t = (Team) HibernateUtil.retrieveTeamById(Integer.valueOf(id));
        if (t == null) return;
        buildSearchResultsTableTeamDetail(t);
        view.buildLinkToSearch();
    }
	
	//***make it get the right thing
	protected final void processRoster() {
        String id = keyVals.get("id");
        String year = keyVals.get("year"); 
        if (id == null) {
            return;
        }
        List<TeamSeason> ts = new ArrayList<TeamSeason>(HibernateUtil.retrieveTeamSeasonByTeamIdAndYear(id, year));
        if (ts == null) return;
        buildSearchResultsTableTeamRoster(ts.get(0));
        view.buildLinkToSearch();
    }
	
	private void buildSearchResultsTableTeam(List<Team> bos) {
        // need a row for the table headers
        String[][] table = new String[bos.size() + 1][5];
        table[0][0] = "Id";
        table[0][1] = "Name";
        table[0][2] = "League";
        table[0][3] = "Year Founded";
        table[0][4] = "Most Recent Year";
        
        for (int i = 0; i < bos.size(); i++) {
            Team t = bos.get(i);
            String tid = t.getTeamID().toString();
            table[i + 1][0] = view.encodeLink(new String[]{"id"}, new String[]{tid}, tid, ACT_DETAIL, SSP_TEAM);
            table[i + 1][1] = t.getName();
            table[i + 1][2] = t.getLeague();
            table[i + 1][3] = t.getYearFounded().toString();
            table[i + 1][4] = t.getYearLast().toString();
        }
        view.buildTable(table);
    }
    
    private void buildSearchResultsTableTeamDetail(Team t) {
    	Set<TeamSeason> seasons = t.getSeasons();
    	List<TeamSeason> list = new ArrayList<TeamSeason>(seasons);
    	Collections.sort(list, TeamSeason.teamSeasonsComparator);
    	// build 2 tables.  first the team details, then the season details
        // need a row for the table headers
        String[][] teamTable = new String[2][4];
        teamTable[0][0] = "Name";
        teamTable[0][1] = "League";
        teamTable[0][2] = "Year Founded";
        teamTable[0][3] = "Most Recent Year";
        teamTable[1][0] = t.getName();
        teamTable[1][1] = t.getLeague();
        teamTable[1][2] = t.getYearFounded().toString();
        teamTable[1][3] = t.getYearLast().toString();
        
        view.buildTable(teamTable);
        // now for seasons
        String[][] seasonTable = new String[seasons.size()+1][7];
        seasonTable[0][0] = "Year";
        seasonTable[0][1] = "Games Played";
        seasonTable[0][2] = "Roster";
        seasonTable[0][3] = "Wins";
        seasonTable[0][4] = "Losses";
        seasonTable[0][5] = "Rank";
        seasonTable[0][6] = "Attendance";
        
        int i = 0;
        for (TeamSeason ts: list) {
        	i++;
        	String tid = t.getTeamID().toString();
        	String yid = ts.getYear().toString();
        	seasonTable[i][0] = yid;
        	seasonTable[i][1] = ts.getGamesPlayed().toString();
        	seasonTable[i][2] = view.encodeLink(new String[]{"id", "year"}, new String[]{tid, yid}, "Roster", ACT_ROSTER, SSP_TEAM);
        	seasonTable[i][3] = ts.getGamesWon().toString();
        	seasonTable[i][4] = ts.getGamesLost().toString();
        	seasonTable[i][5] = ts.getTeamRank().toString();
        	seasonTable[i][6] = INTEGER_FORMAT.format(ts.getTotalAttendance());
        }
        view.buildTable(seasonTable);
    }
    
    // ***needs to be changed
    private void buildSearchResultsTableTeamRoster(TeamSeason ts) { 
    	Set<Player> roster = ts.getRoster();
    	List<Player> list = new ArrayList<Player>(roster);
    	
    	String year = ts.getYear().toString();
    	int tid = ts.getTeamID();
    	Team t = (Team) HibernateUtil.retrieveTeamById(tid);
    	// build 2 tables.  first the team details, then the season details
        // need a row for the table headers
        String[][] teamTable = new String[2][4];
        teamTable[0][0] = "Name";
        teamTable[0][1] = "League";
        teamTable[0][2] = "Year";
        teamTable[0][3] = "Player Payroll";
        teamTable[1][0] = t.getName();
        teamTable[1][1] = t.getLeague();
        teamTable[1][2] = year;
        teamTable[1][3] = null; // what is this? sum of all salaries?
        
        view.buildTable(teamTable);
        // now for seasons
        String[][] playersTable = new String[roster.size()+1][3];
        playersTable[0][0] = "Name";
        playersTable[0][1] = "Games Played";
        playersTable[0][2] = "Player Payroll";
        
        int i = 0;
        for (Player p: list) {
        	i++;
        	PlayerSeason ps = findPS(p, year);
        	String name = p.getName();
        	String pid = p.getId().toString();
        	playersTable[i][0] = view.encodeLink(new String[]{"id"}, new String[]{pid}, name, ACT_DETAIL, SSP_PLAYER);
        	playersTable[i][1] = ps.getGamesPlayed().toString();
        	playersTable[i][2] = DOLLAR_FORMAT.format(ps.getSalary());
        }
        view.buildTable(playersTable);
    }
    
    private PlayerSeason findPS(Player p, String year) {
    	Set<PlayerSeason> seasons = p.getSeasons();
    	List<PlayerSeason> list = new ArrayList<PlayerSeason>(seasons);
    	
    	for (PlayerSeason ps: list) {
        	if (ps.getYear() == Integer.valueOf(year)) {
        		return ps;
        	}
        }
    	return null;
    }
}
