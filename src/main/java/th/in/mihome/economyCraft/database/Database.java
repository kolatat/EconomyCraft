/*
 * The MIT License
 *
 * Copyright 2016 Kolatat Thangkasemvathana.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package th.in.mihome.economyCraft.database;

import java.sql.*;
import java.util.logging.Level;
import th.in.mihome.economyCraft.ECPlugin;
import th.in.mihome.economyCraft.PluginComponent;

/**
 *
 * @author Kolatat Thangkasemvathana
 */
public class Database extends PluginComponent implements AutoCloseable {

    private boolean connected = false;
    private final Connection connection;

    private Statement selectStmt;

    public Database(ECPlugin plugin) {
        super(plugin);
        connection = newConnection();
    }

    @Override
    public void close() throws Exception {
        if (isConnected()) {
            connection.close();
        }
    }

    /**
     * @return the connected
     */
    public boolean isConnected() {
        return connected;
    }

    public boolean isValid() {
        try {
            return connection.isValid(plugin.config.DATABASE_TIMEOUT);
        } catch (SQLException ex) {
            plugin.logException(ex, Level.WARNING, this);
            return false;
        }
    }

    public Connection getConnection() {
        return connection;
    }

    public SelectStatement select() {
        return new SelectStatement(selectStmt);
    }

    private Connection newConnection() {
        Connection conn = null;
        switch (plugin.config.DATABASE_ENGINE) {
            case MYSQL:
                try {
                    conn = DriverManager.getConnection(
                            String.format("jdbc:mysql://%s:%d/%s?autoReconnect=true",
                                    plugin.config.MYSQL_HOST,
                                    plugin.config.MYSQL_PORT,
                                    plugin.config.MYSQL_DATABASE),
                            plugin.config.MYSQL_USER,
                            plugin.config.MYSQL_PASSWORD);
                } catch (SQLException ex) {
                    plugin.logException(ex, Level.SEVERE, this);
                }
                break;
            case SQLITE:
                try {
                    conn = DriverManager.getConnection("jdbc:sqlite:"
                            + plugin.config.SQLITE_FILE);
                } catch (SQLException ex) {
                    plugin.logException(ex, Level.SEVERE, this);
                }
        }

        if (conn != null) {
            try {
                selectStmt = connection.createStatement();
                connected = true;
            } catch (SQLException ex) {
                plugin.logException(ex, Level.SEVERE, this);
            }
        }
        return conn;
    }

}