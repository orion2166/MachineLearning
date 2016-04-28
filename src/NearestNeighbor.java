import java.util.*;

/**
 * Created by orion_000 on 4/11/2016.
 */
public class NearestNeighbor extends NeuralNetwork{

    private Vector<Double> attribute_weights = new Vector<>();
    private HashMap<String,Vector<String[]>> seperation = new HashMap<>();
    private Vector<Vector<Double>> attribute_scale = new Vector<>();
    public NearestNeighbor(String attribute_file_location, String database_datasets_filelocatio,int target, boolean convert_binary) {
        value_information = new DatabaseManagment(attribute_file_location,target,convert_binary);
        build_values();
        value_information.setDataset(database_datasets_filelocatio,values);
        for(int i = 0;i < value_information.get_attribute_size();i++){
            attribute_weights.add(1.0);
        }

    }
    public void setweight(int amountupdates,String targetkeyvalue){
        seperateinstances();
        Random ra = new Random();
        ra.ints(used_data.size());
        double[] differences = null;
        String[] X;
        String[] positive = new String[0];
        String[] negative = new String[0];
        int target = 0;
        Vector<Double> weights = new Vector<>();

        Vector<Double> tempweights = new Vector<>();
        for(int i = 0;i < used_data.get(0).length;i++){
            tempweights.add(0.0);
            weights.add(1.0);
        }

        for(int i = 0;i< amountupdates;i++){
            X = used_data.get(ra.nextInt(used_data.size()));
            for(String key : seperation.keySet()){
                differences = new double[]{100000000000.0,100000000000.0};
                Vector sepvalues = seperation.get(key);
                if(key.equals(targetkeyvalue))
                    target = 0;
                else
                    target = 1;

                for(int k = 0;k < sepvalues.size();k++){
                    if(X.equals(sepvalues.get(k)))
                        continue;
                    double[] diff = nearest_neighbor_calculation(X,weights,(String[])sepvalues.get(k));
                    double value = diff[0] + diff[1];
                    if(differences[target] > value)
                    {
                        differences[target] = value;
                        if(target == 0)
                            positive = (String[]) sepvalues.get(k);
                        else
                            negative = (String[]) sepvalues.get(k);
                    }
                }
            }
            if(!X[0].equals(targetkeyvalue))
                updateweights(tempweights,X,negative,positive);
            else
                updateweights(tempweights,X,positive,negative);
        }

        for(int i = 0;i< tempweights.size();i++){
            if(tempweights.get(i) > 0)
                tempweights.set(i,1/tempweights.get(i)/Math.pow(amountupdates,2));
            else if (tempweights.get(i) == 0)
                tempweights.set(i,1.0);
            else if (tempweights.get(i) <= -amountupdates)
                tempweights.set(i,Math.abs(tempweights.get(i)/amountupdates));
            else
                tempweights.set(i,(Math.abs(tempweights.get(i))+amountupdates)/amountupdates);

        }
        attribute_weights = tempweights;

    }

    private void updateweights(Vector<Double> tempweights, String[] x, String[] positive, String[] negative) {
        double tmp = 0;
        double temp_input_value = 0.0;
        double temp_positive_value = 0.0;
        double temp_negative_value = 0.0;
        if(positive.length<7 || negative.length<7)
            return;

        for(int i = 1;i<x.length;i++){
            if(value_information.continuous_marker.get(i)){
                temp_input_value = (Double.parseDouble(x[i]) - value_information.continuous_mean.get(i)) / value_information.continuous_standard_dev.get(i);
                temp_positive_value = (Double.parseDouble(positive[i]) - value_information.continuous_mean.get(i)) / value_information.continuous_standard_dev.get(i);
                temp_negative_value = (Double.parseDouble(negative[i]) - value_information.continuous_mean.get(i)) / value_information.continuous_standard_dev.get(i);
                tmp = tempweights.get(i) - Math.pow(temp_input_value - temp_positive_value,2)
                        + Math.pow(temp_input_value - temp_negative_value, 2);
                tempweights.set(i,tmp);
                continue;
            }
            tmp = tempweights.get(i) - Math.pow(values.get(i).get(x[i]) - values.get(i).get(positive[i]),2)
                    + Math.pow(values.get(i).get(x[i]) - values.get(i).get(negative[i]), 2);
            tempweights.set(i,tmp);
        }

    }


    private void seperateinstances() {
        for(int i = 0;i<used_data.size();i++){
            if(seperation.containsKey(used_data.get(i)[0])){
                Vector temp = seperation.get(used_data.get(i)[0]);
                temp.add(used_data.get(i));
            }
            else
            {
                Vector<String[]> temp = new Vector<>();
                temp.add(used_data.get(i));
                seperation.put(used_data.get(i)[0],temp);
            }
        }
    }

    public void setattributeweights(){
        HashMap<String,Vector<Vector<Integer>>> Scale = new HashMap<>();
        Vector<Integer> targets = new Vector<>();
        for(int i = 0;i < used_data.size();i++){
            if(!Scale.containsKey(used_data.get(i)[0]))
            {
                Vector<Vector<Integer>> intitialset = new Vector<>();
                for(int j = 1;j < value_information.get_attribute_size();j++) {
                    Vector sets = new Vector();
                    for(int k = 0;k<values.get(j).size();k++)
                        sets.add(0);
                    intitialset.add(sets);
                }
                Scale.put(used_data.get(i)[0],new Vector(intitialset));
            }
            if(targets.size() < values.get(0).get(used_data.get(i)[0])+1)
                targets.add(0);
            else
                targets.set(values.get(0).get(used_data.get(i)[0]),targets.get(values.get(0).get(used_data.get(i)[0]))+1);
        }
        for(int i = 0;i<used_data.size();i++){
            for(int j=1;j<value_information.get_attribute_size();j++){
                Vector<Integer> incremnt = Scale.get(used_data.get(i)[0]).get(j - 1);
                int incrementvalue = incremnt.get(values.get(j).get(used_data.get(i)[j])) + 1;
                incremnt.set(values.get(j).get(used_data.get(i)[j]),incrementvalue);
            }
        }

        MathmaticCalculations entropycalculator = new MathmaticCalculations();
        for(int i = 0; i < value_information.get_attribute_keys().size()-1;i++){
            attribute_scale.add(new Vector<Double>());
            Iterator entries = Scale.entrySet().iterator();
            Vector<Vector<Integer>> temp= new Vector<>();
            while(entries.hasNext()){
                Map.Entry pair = (Map.Entry) entries.next();
                Vector<Vector<Integer>> temperary = (Vector<Vector<Integer>>) pair.getValue();
                temp.add(temperary.get(i));
            }

            for(int l = 0; l < temp.get(0).size();l++){
                int[] positive_negative = new int[]{0,0};
                positive_negative[0] = temp.get(0).get(l);
                for(int j = 1;j < temp.size();j++){
                    positive_negative[1] += temp.get(j).get(l);
                }
                if(positive_negative[0] == 0 && positive_negative[1] == 0)
                {
                    attribute_scale.get(i).add(0.0);
                    continue;
                }
                attribute_scale.get(i).add(entropycalculator.Enthropy(positive_negative));

            }
        }
    }

    public String nearest_neighbor_selection(String[] inputvalue){
        double difference = 10000000000.0;
        int select_neighbor = 0;
        for(int i = 0;i < used_data.size();i++)
        {
            double[] compare = nearest_neighbor_calculation(inputvalue,attribute_weights,used_data.get(i));
            if(difference > (compare[0] + compare[1])) {
                difference = (compare[0] + compare[1]);
                select_neighbor = i;
            }
        }
//        if(used_data.get(select_neighbor)[0] != inputvalue[0])
        used_data.add(inputvalue);
        return used_data.get(select_neighbor)[0];
    }
    public void perform_instance_filter(){
        int remove_section = 0;
        double largest = 10000000000000.0;
        for(int i = 0;i < used_data.size();i++){
            for(int j = 0;j<used_data.size();j++) {
                if(j == i)
                    continue;
                double[] diff = nearest_neighbor_calculation(used_data.get(i),attribute_weights,used_data.get(j));
                if(largest > (diff[0]+diff[1])) {
                    largest = (diff[0] + diff[1]);
                    remove_section = j;
                }
            }
            if(used_data.get(i)[0].equals(used_data.get(remove_section)[0])) {
                used_data.remove(remove_section);
                i=0;
            }
            largest = 1000000000000000.0;
        }
    }
    public double[] nearest_neighbor_calculation(String[] inputvalue, Vector<Double> weightvalue, String[] comparevalue){
        double continuous = 0;
        double discrete = 0;
        double temp_input_value = 0.0;
        double temp_compare_value = 0.0;
        double[] comparedfinal = new double[2];
        for(int i = 1;i<inputvalue.length;i++)
        {
            if(value_information.continuous_marker.get(i)){

                temp_input_value = Double.parseDouble(inputvalue[i]);
                temp_input_value = weightvalue.get(i)*((temp_input_value - value_information.continuous_mean.get(i))/ value_information.continuous_standard_dev.get(i));
                temp_compare_value = Double.parseDouble(comparevalue[i]);
                temp_compare_value = weightvalue.get(i)*((temp_compare_value - value_information.continuous_mean.get(i))/ value_information.continuous_standard_dev.get(i));
                continuous += Math.pow(temp_input_value - temp_compare_value, 2);
                continue;
            }
            temp_input_value = new Double(values.get(i).get(inputvalue[i]));
            temp_compare_value = new Double(values.get(i).get(comparevalue[i]));
            discrete += Math.pow(temp_input_value - temp_compare_value,2);

        }
        comparedfinal[0] = Math.sqrt(discrete);
        comparedfinal[1] = Math.sqrt(continuous);
        return comparedfinal;
    }
}
