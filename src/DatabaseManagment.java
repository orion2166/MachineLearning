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
    private Vector<String> Attribute_name = new Vector<>();

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
                String[] tmp = line.split(",");
                Attribute_name.add(tmp[0]);
                String[] newtemp = new String[tmp.length - 1];
                for(int i = 1;i<tmp.length;i++)
                    newtemp[i-1] = tmp[i];
                List<String> lineset = Arrays.asList(newtemp);
                attributes.put(tmp[0], lineset);
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
        }catch(Exception e){System.out.println("Error while reading file line by line:" + e.getMessage());}}

    String [] get_value(int i){
        return dataset.get(i);
    }
    Vector get_attribute_keys(){return Attribute_name;}
    int get_size(){ return dataset.size();}
    int get_attribute_size(){return attributes.size();}
    List<String> get_attribute_classifications(String key){return attributes.get(key);}
}
