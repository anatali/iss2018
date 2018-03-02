package it.unibo.cmdinput;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collection;
import it.unibo.data.model.DataItem;
import it.unibo.db.IRepository;
import it.unibo.db.InMemoryDb;

public class CommandLineInputHandler {
    private IRepository toDoRepository = new InMemoryDb();

    public void printOptions() {
        System.out.println("\n--- To Do Application ---");
        System.out.println("Please make a choice:");
        System.out.println("(a)ll items");
        System.out.println("(f)ind a specific item");
        System.out.println("(i)nsert a new item");
        System.out.println("(u)pdate an existing item");
        System.out.println("(d)elete an existing item");
        System.out.println("(e)xit");
    }

    public String readInput() {
    	String line = "e";   	
    	try {
    		System.out.println(">");
        	BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
			line = bufferedReader.readLine();
		} catch (IOException e) {
 			e.printStackTrace();
		}   
    	return line;
    }

    public void processInput(CommandLineInput input) {
        if (input == null) {
            handleUnknownInput();
        } else {
            switch (input) {
                case FIND_ALL:
                    printAllDataItems();
                    break;
                case FIND_BY_ID:
                    printDataItem();
                    break;
                case INSERT:
                    insertDataItem();
                    break;
                case UPDATE:
                    updateDataItem();
                    break;
                case DELETE:
                    deleteDataItem();
                    break;
                case EXIT:
                	System.out.println("bye bye");
                    break;
                default:
                    handleUnknownInput();
            }
        }
    }

    private Long askForItemId() {
        System.out.println("Please enter the item ID:");
        String input = readInput();
        return Long.parseLong(input);
    }

    private DataItem askForNewToDoAction() {
        DataItem DataItem = new DataItem();
        System.out.println("Please enter the name of the item:");
        DataItem.setName(readInput());
        return DataItem;
    }

    private void printAllDataItems() {
        Collection<DataItem> DataItems = toDoRepository.findAll();

        if (DataItems.isEmpty()) {
            System.out.println("Nothing to do. Go relax!");
        } else {
            for (DataItem DataItem : DataItems) {
                System.out.println(DataItem);
            }
        }
    }

    private void printDataItem() {
        DataItem DataItem = findDataItem();

        if (DataItem != null) {
            System.out.println(DataItem);
        }
    }

    private DataItem findDataItem() {
        Long id = askForItemId();
        DataItem DataItem = toDoRepository.findById(id);

        if (DataItem == null) {
            System.err.println("To do item with ID " + id + " could not be found.");
        }

        return DataItem;
    }

    private void insertDataItem() {
        DataItem DataItem = askForNewToDoAction();
        Long id = toDoRepository.insert(DataItem);
        System.out.println("Successfully inserted to do item with ID " + id + ".");
    }

    private void updateDataItem() {
        DataItem DataItem = findDataItem();

        if (DataItem != null) {
            System.out.println(DataItem);
            System.out.println("Please enter the name of the item:");
            DataItem.setName(readInput());
            System.out.println("Please enter the done status the item:");
            DataItem.setConsumed(Boolean.parseBoolean(readInput()));
            toDoRepository.update(DataItem);
            System.out.println("Successfully updated to do item with ID " + DataItem.getId() + ".");
        }
    }

    private void deleteDataItem() {
        DataItem DataItem = findDataItem();

        if (DataItem != null) {
            toDoRepository.delete(DataItem);
            System.out.println("Successfully deleted to do item with ID " + DataItem.getId() + ".");
        }
    }

    private void handleUnknownInput() {
        System.out.println("Please select a valid option!");
    }
}