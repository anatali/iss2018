package it.unibo.db;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicLong;
import it.unibo.data.model.DataItem;

/*
 * CRUD (create, read, update, delete)
 * functionalities
 */
public class InMemoryDb implements IRepository {
    private AtomicLong currentId = new AtomicLong();
    private ConcurrentMap<Long, DataItem> toDos = new ConcurrentHashMap<Long, DataItem>();

    @Override
    public List<DataItem> findAll() {
        List<DataItem> toDoItems = new ArrayList<DataItem>(toDos.values());
        Collections.sort(toDoItems);
        return toDoItems;
    }

    @Override
    public DataItem findById(Long id) {
        return toDos.get(id);
    }

    @Override
    public Long insert(DataItem toDoItem) {
        Long id = currentId.incrementAndGet();
        toDoItem.setId(id);
        toDos.putIfAbsent(id, toDoItem);
        return id;
    }

    @Override
    public void update(DataItem toDoItem) {
        toDos.replace(toDoItem.getId(), toDoItem);
    }

    @Override
    public void delete(DataItem toDoItem) {
        toDos.remove(toDoItem.getId());
    }
}