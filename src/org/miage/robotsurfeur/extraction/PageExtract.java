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
import java.net.URL;
import java.net.URLConnection;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.text.BadLocationException;
import javax.swing.text.EditorKit;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.html.HTML;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLEditorKit;

public class PageExtract {

    /**
     * Extract links from a given URL
     */
    public static LinkedList<Link> getLinks(String website) {
        LinkedList<Link> allLinks = new LinkedList<Link>();
        try {
            // Loading page
            URL url = new URL(website);
            URLConnection uconnection = url.openConnection();
            Reader rd = new InputStreamReader(uconnection.getInputStream());

            // Reading HTML document
            EditorKit kit = new HTMLEditorKit();
            HTMLDocument doc = (HTMLDocument) kit.createDefaultDocument();
            doc.putProperty("IgnoreCharsetDirective", true);
            kit.read(rd, doc, 0);

            // Browse link tags
            HTMLDocument.Iterator it = doc.getIterator(HTML.Tag.A);
            while(it.isValid()) {
                SimpleAttributeSet s = (SimpleAttributeSet) it.getAttributes();
                String href = (String) s.getAttribute(HTML.Attribute.HREF);
                if(href != null) {
                    // Add link to our list
                    Link curLink = new Link(href);
                    try {
                        allLinks.add(curLink);
                    } catch(NullPointerException e) {
                        System.err.println("Error while adding link to list:" + curLink);
                    }
                }
                it.next();
            }
        } catch(BadLocationException | IOException e) {
            Logger.getLogger(PageExtract.class.getName()).log(Level.SEVERE, null, e);
        }

        return allLinks;
    }
}
