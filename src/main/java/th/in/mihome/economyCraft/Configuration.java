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
package th.in.mihome.economyCraft;

import org.bukkit.configuration.file.FileConfiguration;
import th.in.mihome.economyCraft.Options.DatabaseEngine;
import th.in.mihome.economyCraft.Options.MatchingAlgorithm;

/**
 *
 * @author Kolatat Thangkasemvathana
 */
public class Configuration {

    public final int EXPORT_PENALTY_LINEAR,
            EXPORT_PENALTY_SQRT,
            EXPORT_PENALTY_LOG,
            TRADING_QUEUE_INITIAL_SIZE,
            MYSQL_PORT;

    public final String MYSQL_HOST,
            MYSQL_DATABASE,
            MYSQL_USER,
            MYSQL_PASSWORD;

    public final DatabaseEngine DATABASE_ENGINE;
    public final MatchingAlgorithm MATCHING_ALGORITHM;

    public Configuration(FileConfiguration config) {
        EXPORT_PENALTY_LINEAR = config.getInt("economy.exportPenalty.linear");
        EXPORT_PENALTY_LOG = config.getInt("economy.exportPenalty.log");
        EXPORT_PENALTY_SQRT = config.getInt("economy.exportPenalty.sqrt");

        MATCHING_ALGORITHM = MatchingAlgorithm.valueOf(config.getString("exchange.trading.matchingAlgorithm"));
        TRADING_QUEUE_INITIAL_SIZE = config.getInt("exchange.trading.queueInitialSize");

        DATABASE_ENGINE = DatabaseEngine.valueOf(config.getString("database.engine"));

        MYSQL_HOST = config.getString("database.mysql.host");
        MYSQL_PORT = config.getInt("database.mysql.port");
        MYSQL_DATABASE = config.getString("database.mysql.database");
        MYSQL_USER = config.getString("database.mysql.user");
        MYSQL_PASSWORD = config.getString("database.mysql.password");
    }
}