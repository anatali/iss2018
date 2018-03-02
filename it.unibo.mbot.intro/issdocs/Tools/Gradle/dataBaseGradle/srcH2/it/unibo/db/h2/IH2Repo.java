package it.unibo.db.h2;
import java.util.List;
import it.unibo.data.model.DataItem;
/*
 * CRUD (create, read, update, delete)
 * operations
 */
public interface IH2Repo extends it.unibo.db.IRepository{
    List<DataItem> findAllActive();
    List<DataItem> findAllConsumed();
}