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
public class Link {

    /**
     * <tt>Link</tt> reference.
     */
    String href;
    /**
     * <tt>Link</tt> content (between tags).
     */
    String content;

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
}
