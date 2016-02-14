import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Vector;

/**
 * Created by orion2166 on 1/31/2016.
 */
public class DatabaseManagment {
    Vector <String []> dataset = new Vector<String []>();
    public DatabaseManagment(String file){
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
}
