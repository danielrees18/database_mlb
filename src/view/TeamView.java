package view;

public class TeamView extends BaseView {

	public TeamView() {
        title = "Team";
    }
	
	@Override
	public void buildSearchForm() {
		body.append("<div id = \"main-team\">\r\n");
		body.append("<form action=\"");
        body.append(title.toLowerCase());
        body.append(".ssp\" method=\"get\">\r\n");
        body.append("<input class=\"search\" type=\"text\" size=\"30\" name=\"name\" placeholder=\"Player Name\"><input type=\"checkbox\" name=\"exact\"> Exact Match?\r\n");
        body.append("<br/>\r\n");
        body.append("<input type=\"hidden\" name=\"action\" value=\"search\">\r\n");
        body.append("<input class=\"btn\" type=\"submit\" value=\"Search\">\r\n");
        body.append("</form>\r\n"); 
        body.append("</div>\r\n");
	}

}
