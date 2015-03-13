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

/**
 * Class to manage a link.
 *
 * @author MIAGE
 */
public class Link implements Comparable<Link> {

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((content == null) ? 0 : content.hashCode());
        result = prime * result + ((href == null) ? 0 : href.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Link other = (Link) obj;
        if (content == null) {
            if (other.content != null) {
                return false;
            }
        } else if (!content.equals(other.content)) {
            return false;
        }
        if (href == null) {
            if (other.href != null) {
                return false;
            }
        } else if (!href.equals(other.href)) {
            return false;
        }
        return true;
    }

    /**
     * <tt>Link</tt> reference.
     */
    String href;
    /**
     * <tt>Link</tt> content (between tags).
     */
    String content;

    /**
     * Score
     */
    int score;

    /**
     * Initialize a Link with given parameters.
     *
     * @param h <tt>String</tt> representing the reference
     * @param c <tt>String</tt> representing the content (between tags)
     */
    public Link(String h, String c) {
        href = h;
        content = c;
    }

    public int getScore() {
        return score;
    }

    public void increaseScore(int increment) {
        score += increment;
    }

    public void resetScore() {
        score = 0;
    }

    /**
     * Getter for reference.
     *
     * @return <tt>String</tt> reference
     */
    public String getHref() {
        return href;
    }

    /**
     * Getter for content.
     *
     * @return <tt>String</tt> content
     */
    public String getContent() {
        return content;
    }

    /**
     * Returns a human-readable <tt>Link</tt>.
     *
     * @return String human-readable <tt>Link</tt>
     */
    @Override
    public String toString() {
        return content + ": " + href;
    }

	@Override
	public int compareTo(Link link2) {
		// TODO Auto-generated method stub
		 if (this.getScore() > link2.getScore()) 
		 {
			 return -1;
		 } 
	     else if(this.getScore() == link2.getScore()) 
	     {
	    	 return 0;
	     } 
	     
	     else return 1; 
	}
}
