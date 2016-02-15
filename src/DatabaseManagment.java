import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;

/**
 * Created by orion2166 on 1/31/2016.
 */
public class DatabaseManagment {
    private Vector <String []> dataset = new Vector<String []>();
//    private String target_attribute;
    private HashMap<String,List<String>> attributes = new HashMap<String,List<String>>();

    public DatabaseManagment(String attributefile){
        try{

            //Create object of FileReader
            FileReader inputFile = new FileReader(attributefile);

            //Instantiate the BufferedReader Class
            BufferedReader bufferReader = new BufferedReader(inputFile);

            //Variable to hold the one line data
            String line;

            // Read file line by line and print on the console
            while ((line = bufferReader.readLine()) != null)   {
                List<String> lineset = Arrays.asList(line.split(","));
                String attribute_values = lineset.get(0);
                lineset.remove(0);
                attributes.put(attribute_values,lineset);
            }
            //Close the buffer reader
            bufferReader.close();
        }catch(Exception e){
            System.out.println("Error while reading file line by line:" + e.getMessage());
        }
    }
    void setDataset (String file){
        try{

            //Create object of FileReader
            FileReader inputFile = new FileReader(file);

            //Instantiate the BufferedReader Class
            BufferedReader bufferReader = new BufferedReader(inputFile);

            //Variable to hold the one line data
            String line;

            // Read file line by line and print on the console
            while ((line = bufferReader.readLine()) != null)   {
                String[] set_value = line.split(",");
                dataset.add(set_value);
            }
            //Close the buffer reader
            bufferReader.close();
        }catch(Exception e){
            System.out.println("Error while reading file line by line:" + e.getMessage());
        }

    }

    String [] get_value(int i){
        return dataset.get(i);
    }
    Object[] get_attribute_keys(){
        Set<String> attribute_sets = attributes.keySet();
        return attribute_sets.toArray();

    }
}
