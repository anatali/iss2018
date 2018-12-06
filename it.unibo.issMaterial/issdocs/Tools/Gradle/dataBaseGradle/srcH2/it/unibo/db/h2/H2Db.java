package it.unibo.db.h2;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import it.unibo.data.model.DataItem;

public class H2Db implements IH2Repo {
    @Override
    public List<DataItem> findAll() {
        List<DataItem> DataItems = new ArrayList<DataItem>();
        Connection conn = null;
        Statement stmt = null; 
        ResultSet rs = null;

        try {
            conn = createConnection();
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT id, name, consumed from unibodata_item");

            while (rs.next()) {
                DataItem DataItem = new DataItem();
                DataItem.setId(rs.getLong("id"));
                DataItem.setName(rs.getString("name"));
                DataItem.setConsumed(rs.getBoolean("consumed"));
                DataItems.add(DataItem);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeConnection(conn);
            closeStatement(stmt);
            closeResultSet(rs);
        }

        return DataItems;
    }

    @Override
    public List<DataItem> findAllActive() {
        List<DataItem> activeToDos = new ArrayList<DataItem>();
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;

        try {
            conn = createConnection();
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT id, name, consumed FROM unibodata_item WHERE consumed = 0");

            while (rs.next()) {
                DataItem DataItem = new DataItem();
                DataItem.setId(rs.getLong("id"));
                DataItem.setName(rs.getString("name"));
                DataItem.setConsumed(rs.getBoolean("consumed"));
                activeToDos.add(DataItem);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeConnection(conn);
            closeStatement(stmt);
            closeResultSet(rs);
        }

        return activeToDos;
    }

    @Override
    public List<DataItem> findAllConsumed() {
        List<DataItem> consumedToDos = new ArrayList<DataItem>();
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;

        try {
            conn = createConnection();
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT id, name, consumed FROM unibodata_item WHERE consumed = 1");

            while (rs.next()) {
                DataItem DataItem = new DataItem();
                DataItem.setId(rs.getLong("id"));
                DataItem.setName(rs.getString("name"));
                DataItem.setConsumed(rs.getBoolean("consumed"));
                consumedToDos.add(DataItem);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeConnection(conn);
            closeStatement(stmt);
            closeResultSet(rs);
        }

        return consumedToDos;
    }

    @Override
    public DataItem findById(Long id) {
        DataItem DataItem = null;
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = createConnection();
            stmt = conn.prepareStatement("SELECT id, name, consumed from unibodata_item WHERE id = ?");
            stmt.setLong(1, id);
            rs = stmt.executeQuery();

            if (rs.first()) {
                DataItem = new DataItem();
                DataItem.setId(rs.getLong("id"));
                DataItem.setName(rs.getString("name"));
                DataItem.setConsumed(rs.getBoolean("consumed"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeConnection(conn);
            closeStatement(stmt);
            closeResultSet(rs);
        }

        return DataItem;
    }

    @Override
    public Long insert(DataItem DataItem) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Long newId = null;

        try {
            conn = createConnection();
            System.out.println("insert conn=" + conn);
            stmt = conn.prepareStatement("INSERT INTO unibodata_item (name, consumed) VALUES (?,?)", Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, DataItem.getName());
            stmt.setBoolean(2, DataItem.isConsumed());
            stmt.executeUpdate();

            rs = stmt.getGeneratedKeys();

            if (rs.next()) {
                newId = rs.getLong(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeConnection(conn);
            closeStatement(stmt);
            closeResultSet(rs);
        }

        return newId;
    }

    @Override
    public void update(DataItem DataItem) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = createConnection();
            stmt = conn.prepareStatement("UPDATE unibodata_item SET name = ?, consumed = ? where id = ?");
            stmt.setString(1, DataItem.getName());
            stmt.setBoolean(2, DataItem.isConsumed());
            stmt.setLong(3, DataItem.getId());
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeConnection(conn);
            closeStatement(stmt);
            closeResultSet(rs);
        }
    }

   @Override
    public void delete(DataItem DataItem) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = createConnection();
            stmt = conn.prepareStatement("DELETE FROM unibodata_item WHERE id = ?");
            stmt.setLong(1, DataItem.getId());
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeConnection(conn);
            closeStatement(stmt);
            closeResultSet(rs);
        }
    }

    private Connection createConnection() throws ClassNotFoundException, SQLException {
        Class.forName("org.h2.Driver");
        return DriverManager.getConnection("jdbc:h2:~/unibodata", "sa", "");
    }

    private void closeConnection(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
            }
        }
    }

    private void closeStatement(Statement statement) {
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
            }
        }
    }

    private void closeResultSet(ResultSet resultSet) {
        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException e) {
            }
        }
    }
}