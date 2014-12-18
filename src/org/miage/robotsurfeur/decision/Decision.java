package org.miage.robotsurfeur.decision;
import org.miage.robotsurfeur.browsing.Main;

import java.util.LinkedList;
import java.util.Random;

import org.miage.robotsurfeur.extraction.Link;
import org.miage.robotsurfeur.extraction.PageExtract;
import org.miage.robotsurfeur.toolbox.Tools;

import java.util.regex.Pattern;
import java.util.concurrent.TimeUnit;

import org.junit.*;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;

import java.lang.Math;

public class Decision {
	 private static WebDriver driver;
	 private static String randomURL;
	 
	  
	 public static void setUp() throws Exception {
		 driver = new FirefoxDriver();
	     randomURL = Main.getHomeURL();
	 }
	  
	 public static void randomBrowse() throws Exception {
		 
		 //Open Home Page
		 driver.get(randomURL);
		    
	     //Extract links from a given URL 
	     LinkedList<Link> curPageLinks = PageExtract.getLinks(randomURL);
	     
	     //Count number of links
	     int countURL = curPageLinks.size();
	     System.err.println("Taille : "+countURL);
	     
	     //Generate a random number between 0 and the number of links
	     int rand = (int) (Math.random() * countURL );
	     System.err.println("Rand : "+rand);
	     
	     //Get the URL from the chosen one Link
	     Link test = curPageLinks.get(rand);
	     String link = test.getHref();
	     System.err.println("URL : "+link);

	     //Skip links which not starts with http:// or https://
	     while (!link.startsWith("http://") && !link.startsWith("https://")) {
	    	 rand = (int) (Math.random() * countURL );
		     System.err.println("Rand : "+rand);
		        
		     test = curPageLinks.get(rand);
		     System.err.println("test : "+test);
		     link = test.getHref();
		     System.err.println("URL : "+link);
		 }
	        
	     randomURL = link;
	     System.err.println("RandomURL : "+randomURL);
	       
	  }
	  
       	 	
		   
	        
	       
	  
	  
	  public void tearDown() throws Exception {
		    driver.quit();
		    }

}
