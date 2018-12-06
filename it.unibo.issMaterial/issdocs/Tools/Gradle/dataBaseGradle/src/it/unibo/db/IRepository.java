package it.unibo.db;
import java.util.List;
import it.unibo.data.model.DataItem;
/*
 * CRUD (create, read, update, delete)
 * operations
 */
public interface IRepository {
    List<DataItem> findAll();
    DataItem findById(Long id);
    Long insert(DataItem d);
    void update(DataItem d);
    void delete(DataItem d);
}