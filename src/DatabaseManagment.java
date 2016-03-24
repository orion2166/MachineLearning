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
    private int target_location = 0;
    public Vector<Double> continuous_mean = new Vector<>();
    public Vector<Double> continuous_standard_dev = new Vector<>();
    public Vector<Boolean> continuous_marker = new Vector<>();
    private boolean allcontinuous = false;
    public DatabaseManagment(String attributefile,int target,boolean inputallcontinuous){
        target_location = target;
        allcontinuous = inputallcontinuous;
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
                if(tmp[1].equals("continuous") || allcontinuous)
                    continuous_marker.add(true);
                else
                    continuous_marker.add(false);
                continuous_mean.add(0.0);
                List<String> lineset = Arrays.asList(newtemp);
                attributes.put(tmp[0], lineset);
            }
            //Close the buffer reader
            bufferReader.close();
        }catch(Exception e){
            System.out.println("Error while reading file line by line:" + e.getMessage());
        }
        String tmp = Attribute_name.firstElement();
        Attribute_name.set(0,Attribute_name.get(target_location));
        Attribute_name.set(target_location,tmp);
    }

    void setDataset (String file,Vector values){
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
                String tmp = set_value[0];
                set_value[0] = set_value[target_location];
                set_value[target_location] = tmp;
                for(int i = 1;i<set_value.length;i++)
                    if(continuous_marker.get(i)){
                        double temperary_values = 0;
                        HashMap<String,Integer> temp = (HashMap)values.get(i);
                        if(!temp.containsKey("continuous"))
                            temperary_values = temp.get(set_value[i]);
                        if(temp.containsKey("continuous"))
                        {
                            temperary_values = Double.parseDouble(set_value[i]);
                        }
                        continuous_mean.set(i,continuous_mean.get(i)+ temperary_values);
                    }
                dataset.add(set_value);
            }
            //Close the buffer reader
            bufferReader.close();
        }catch(Exception e){System.out.println("Error while reading file line by line:" + e.getMessage());}
        for(int i = 1;i < continuous_mean.size();i++)
            continuous_mean.set(i,continuous_mean.get(i)/dataset.size());
        setContinuous_standard_dev(values);
    }

    String [] get_value(int i){
        return dataset.get(i);
    }
    Vector get_attribute_keys(){return Attribute_name;}
    int get_size(){ return dataset.size();}
    int get_attribute_size(){return attributes.size();}
    List<String> get_attribute_classifications(String key){return attributes.get(key);}
    void randomize_data(){
        Vector <String []> temperary = new Vector<>();
        Random rand = new Random();
        int max = dataset.size();
        for(int i = 0;i < max;i++)
        {
            int random_value1 = rand.nextInt(max);
            temperary.add(dataset.get(random_value1));
        }
        dataset = temperary;
    }
    void setContinuous_standard_dev(Vector values){
        double temperary_values = 0;
        double[] variance = new double[continuous_marker.size()];
        for(int i = 0;i < variance.length;i++)
            variance[i] = 0.0;
        for(int i = 0;i < dataset.size();i++){
            for(int j = 1; j < variance.length;j++){
                if(continuous_marker.get(j)){
                    HashMap<String,Integer> temp = (HashMap)values.get(j);
                    if(!temp.containsKey("continuous"))
                        temperary_values = temp.get(dataset.get(i)[j]);
                    if(temp.containsKey("continuous"))
                    {
                        temperary_values = Double.parseDouble(dataset.get(i)[j]);
                    }
                    variance[j] += Math.pow(temperary_values - continuous_mean.get(j),2);
                }
            }
        }
        for(int i = 0;i<variance.length;i++)
            continuous_standard_dev.add(Math.sqrt(variance[i]/(dataset.size()-1)));
    }
}
