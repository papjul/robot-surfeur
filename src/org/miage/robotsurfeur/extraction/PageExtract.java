/*
 * Copyright (C) 2014 MIAGE
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */
package org.miage.robotsurfeur.extraction;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

/**
 * Class to help extracting information from a page.
 *
 * @author MIAGE
 */
public class PageExtract{
	
	/**
     * Extract links from a given URL.
     *
     * @param wd <tt>WebDriver</tt> 
     * @return <tt>LinkedList&lt;Link&gt;</tt> a collection of links
     */
	
	
	
	public static LinkedList<Link> getLinks(WebDriver wd){
		
		System.out.println("Extract....");
		List<WebElement> listLink =  wd.findElements(By.xpath("//a[@href]"));
		LinkedList<Link> allLinks = new LinkedList<Link>();
				
		
		String link = new String();
		String text = new String();
				
		for(int i = 0; i < listLink.size(); i++){
			link = listLink.get(i).getAttribute("href");
			text = listLink.get(i).getText();
				
				//add Link to the LinkedList
				allLinks.add(new Link (link,text));
			
		}
		
		//Delete doubloon
		Set<Link> mySet = new HashSet<Link>(allLinks);
		 
	    allLinks = new LinkedList<Link>(mySet);
	    
	    for(int j = 0 ; j < allLinks.size()-1 ; j++){
			System.out.println("Lien : "+j+ " : "+ allLinks.get(j));
		}
	    
		System.out.println("End of extract");
		
		return allLinks;
	}

	//Check if the URL is valid 
	public static boolean correctLink(String website){
		try{
			URL url = new URL(website);
			URLConnection uconnection = (URLConnection) url.openConnection();
			Map<String, List<String>> map = uconnection.getHeaderFields();

			String serv = uconnection.getHeaderField("Server");

			if (serv == null) return false;	
			else return true;
			

		} catch (Exception e) {
			e.printStackTrace();
		}

		return false;
	}

}