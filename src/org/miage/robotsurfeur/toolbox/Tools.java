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
package org.miage.robotsurfeur.toolbox;

/**
 *
 * @author MIAGE
 */
public class Tools {

    public static boolean isInteger(String str) {
        if(str == null) {
            return false;
        }
        int length = str.length();
        if(length == 0) {
            return false;
        }
        int i = 0;
        if(str.charAt(0) == '-') {
            if(length == 1) {
                return false;
            }
            i = 1;
        }
        for(; i < length; i++) {
            char c = str.charAt(i);
            if(c <= '/' || c >= ':') {
                return false;
            }
        }
        return true;
    }
}
