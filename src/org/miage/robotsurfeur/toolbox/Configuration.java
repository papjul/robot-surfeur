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

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 *
 * @author MIAGE
 */
public final class Configuration {

    public static Properties config = new Properties();
    private static boolean instancied = false;

    public static boolean isInstancied() {
        return instancied;
    }

    public static String getString(String prop) {
        return config.getProperty(prop);
    }

    public static Boolean getBoolean(String prop) {
        return Boolean.parseBoolean(config.getProperty(prop));
    }

    public static void setUp() {
        try {
            FileInputStream in = new FileInputStream("config.properties");
            config.load(in);
            in.close();
        } catch(IOException e) {
            System.err.println(e);
        }
    }

}
