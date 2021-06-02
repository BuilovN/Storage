package org.builovn.storage.database;

import java.sql.Connection;
import java.sql.SQLException;

public class PoolManager {
    private static ConnectionPool connectionPool;
    private String url = "jdbc:postgresql://188.120.237.91:5432/netcracker";
    private String user = "keycloak";
    private String password = "a2ffc214e4354c4e807b65101e02b7eb";

    public PoolManager(String url, String user, String password){
        this.url = url;
        this.user = user;
        this.password = password;
    }

    public Connection getConnection() throws SQLException {
        if (connectionPool == null) {
            connectionPool = ConnectionPool.create(url, user, password);
        }
        return connectionPool.getConnection();
    }

    public boolean releaseConnection(Connection connection) throws SQLException {
        if (connectionPool == null) {
            connectionPool = ConnectionPool.create(url, user, password);
        }

        return connectionPool.releaseConnection(connection);
    }

    public void shutdown() throws SQLException {
        if (connectionPool != null) {
            connectionPool.shutdown();
        }
    }
}
