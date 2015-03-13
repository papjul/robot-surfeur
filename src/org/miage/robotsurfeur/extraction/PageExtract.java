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

import java.net.URL;
import java.net.URLConnection;
import java.util.LinkedList;
import java.util.Map;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * Class to help extracting information from a page.
 *
 * @author MIAGE
 */
public class PageExtract {

    /**
     * Extract links from a given URL.
     *
     * @param wd <tt>WebDriver</tt>
     * @return <tt>LinkedList&lt;Link&gt;</tt> a collection of links
     */
    public static LinkedList<Link> getLinks(WebDriver wd) {

//        System.out.println("Extract....");
        List<WebElement> listLink = wd.findElements(By.xpath("//a[@href]"));
        LinkedList<Link> allLinks = new LinkedList<>();

        for (WebElement anchor : listLink) {
            String link = anchor.getAttribute("href");
            String text = anchor.getText();
            allLinks.add(new Link(link, text));
        }

        return allLinks;
    }

    //Check if the URL is valid
    public static boolean correctLink(String website) {
        try {
            URL url = new URL(website);
            URLConnection uconnection = (URLConnection) url.openConnection();
            Map<String, List<String>> map = uconnection.getHeaderFields();

            String serv = uconnection.getHeaderField("Server");

            return serv != null;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

}
