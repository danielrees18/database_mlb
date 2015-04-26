/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

/**
 *
 * @author user
 */
public class PlayerView extends BaseView {

    public PlayerView() {
        title = "Player";
    }
    
    @Override
    public void buildSearchForm() {
    	body.append("<div id = \"main-player\">\r\n");
        body.append("<form action=\"");
        body.append(title.toLowerCase());
        body.append(".ssp\" method=\"get\">\r\n");
        body.append("<input class=\"search\" type=\"text\" size=\"20\" name=\"name\" placeholder=\"Player Name\"><input type=\"checkbox\" name=\"exact\"> Exact Match?\r\n");
        body.append("<br/><br/>\r\n");
        body.append("<input type=\"hidden\" name=\"action\" value=\"search\">\r\n");
        body.append("<input class=\"btn\" type=\"submit\" value=\"Search\">\r\n");
        body.append("</form>\r\n"); 
        body.append("</div>\r\n");
    }

}
