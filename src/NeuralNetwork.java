import java.util.HashMap;
import java.util.List;
import java.util.Vector;

/**
 * Created by orion_000 on 3/21/2016.
 */
public class NeuralNetwork {
    protected Vector<NerualNetworkLayer> totalLayers = new Vector<>();
    protected NerualNetworkLayer inputlayer;
    public Vector<String[]> used_data = new Vector<>();
    public DatabaseManagment value_information;
    protected Vector<HashMap<String,Integer>> values = new Vector<>();
    protected NerualNetworkLayer outputlayer;
    protected Vector<Integer> attributes_sections = new Vector<>();
    protected boolean convert_binary_final = false;
    protected Vector<Double> threshold = new Vector<>();

    public NeuralNetwork(int nodesNumber, int hiddenLayerNumbers,String attribute_file_location, String database_datasets_filelocatio,int target,boolean convert_binary){
        value_information = new DatabaseManagment(attribute_file_location,target,!convert_binary);
        Vector keys = value_information.get_attribute_keys();
        build_values();
        value_information.setDataset(database_datasets_filelocatio,values);
        inputlayer = new NerualNetworkLayer(0,true,false,value_information.get_attribute_size()-1);
        if(hiddenLayerNumbers == 0){
            if(convert_binary){
                int max = 0;
                for(int i = 1;i < attributes_sections.size();i++)
                    max += attributes_sections.get(i);
                inputlayer = new NerualNetworkLayer(0,true, false, max);
                outputlayer = new NerualNetworkLayer(max,false, true, value_information.get_attribute_classifications((String) keys.get(0)).size()-1);
                convert_binary_final = true;
            }
            else {
                outputlayer = new NerualNetworkLayer(value_information.get_attribute_size()-1,false,true,value_information.get_attribute_classifications((String) keys.get(0)).size()-1);
            }
            threshold.add(.5);
            return;
        }
        if(convert_binary){
            int max = 0;
            for(int i = 1;i < attributes_sections.size();i++)
                max += attributes_sections.get(i);
            inputlayer = new NerualNetworkLayer(0,true, false, max);
            totalLayers.add(new NerualNetworkLayer(max, false, false, nodesNumber));
            convert_binary_final = true;
        }
        else
            totalLayers.add(new NerualNetworkLayer(value_information.get_attribute_size()-1, false, false, nodesNumber));
        for(int i = 1;i < hiddenLayerNumbers;i++){
            totalLayers.add(new NerualNetworkLayer(nodesNumber, false, false, nodesNumber));
        }
        outputlayer = new NerualNetworkLayer(nodesNumber,false, true, value_information.get_attribute_classifications((String) keys.get(0)).size()-1);
        for(int i = 0;i < value_information.get_attribute_classifications((String) keys.get(0)).size();i++)
            threshold.add(.5);
//        build_value_set(10);
    }

    public NeuralNetwork() {
    }


    void build_value_set(int percentage_used) {
        value_information.randomize_data();
        int max = value_information.get_size();
        int number_sets = (max * percentage_used) / 100;
        for (int i = 0; i < number_sets; i++) {
            used_data.add(value_information.get_value(i));
        }
        Vector keys = value_information.get_attribute_keys();
        for(int i = 0;i < keys.size();i++){
            HashMap<String,Integer> temps = new HashMap<>();
            List<String> attribute_values = value_information.get_attribute_classifications((String) keys.get(i));
            for(int j = 0;j < attribute_values.size();j++){
                temps.put(attribute_values.get(j),j);
            }
            values.add(temps);
        }
    }
    void build_values(){
        Vector keys = value_information.get_attribute_keys();
        for(int i = 0;i < keys.size();i++){
            HashMap<String,Integer> temps = new HashMap<>();
            List<String> attribute_values = value_information.get_attribute_classifications((String) keys.get(i));
            attributes_sections.add(attribute_values.size());
            for(int j = 0;j < attribute_values.size();j++){
                temps.put(attribute_values.get(j),j);
            }
            values.add(temps);
        }
    }
    void input_into_neural(String[] inputvalue){
        Vector<Double> converted_value = new Vector<>();
        Vector<Double> targets = new Vector<>();

        if(convert_binary_final){
            for(int i = 1;i < inputvalue.length;i++){
                if(value_information.continuous_marker.get(i)){
                    double temp_values = Double.parseDouble(inputvalue[i]);
                    temp_values = (temp_values - value_information.continuous_mean.get(i))/ value_information.continuous_standard_dev.get(i);
                    converted_value.add(temp_values);
                    continue;
                }
                for(int j = 0;j<attributes_sections.get(i);j++)
                {

                    if(j == values.get(i).get(inputvalue[i]))
                        converted_value.add(1.0);
                    else
                        converted_value.add(0.0);

                }
            }
        }
        else{
            for(int i = 1;i < inputvalue.length;i++){
                if(value_information.continuous_marker.get(i)){
                    double temp_values = 0;
                    if(!values.get(i).containsKey("continuous"))
                        temp_values = new Double(values.get(i).get(inputvalue[i]));
                    if(values.get(i).containsKey("continuous"))
                        temp_values = Double.parseDouble(inputvalue[i]);
                    temp_values = (temp_values - value_information.continuous_mean.get(i))/ value_information.continuous_standard_dev.get(i);
                    converted_value.add(temp_values);
                }
            }
        }
        inputlayer.insert_output_values(converted_value);
        if(totalLayers.size() != 0)
            totalLayers.get(0).input_nodes(inputlayer);
        for(int i = 1;i < totalLayers.size();i++){
            totalLayers.get(i).input_nodes(totalLayers.get(i-1));
        }
        if(totalLayers.size() != 0)
            outputlayer.input_nodes(totalLayers.get(totalLayers.size()-1));
        else
            outputlayer.input_nodes(inputlayer);

        for(int i = 0;i < outputlayer.get_node_size();i++){
            if(values.get(0).get(inputvalue[0]) == i) {
                targets.add(1.0);
            }
            else {
                targets.add(0.0);
            }
        }
        double[] error = new double[targets.size()];
        for(int i = 0;i < targets.size();i++) {
            error[i] = -(targets.get(i) - outputlayer.get_outputvalues().get(i));
        }
//        for(int i = 0;i < targets.size();i++) {
//            if(outputlayer.get_outputvalues().get(i) > threshold.get(i))
//                error[i] = -(targets.get(i) - 1.0);
//            else
//                error[i] = -(targets.get(i));
//        }
//        for(int i = 0;i < targets.size();i++) {
//            if(error[i] > 0)
//                threshold.set(i,threshold.get(i) - .01);
//            else if(error[i]<0)
//                threshold.set(i,threshold.get(i) + .01);
//        }



//        for(int i = 0;i < targets.size();i++)
//        {
//            double tmp_error = -(targets.get(i) - outputlayer.get_outputvalues().get(i));
//            error[i] = tmp_error;
//        }
        outputlayer.insert_Eout(error);
        outputlayer.build_out_net();
        outputlayer.build_Enet();
        if(totalLayers.size() != 0){
            outputlayer.change_weights(totalLayers.get(totalLayers.size() - 1));
            totalLayers.get(totalLayers.size()-1).build_Eout(outputlayer);
            totalLayers.get(totalLayers.size()-1).build_out_net();
            totalLayers.get(totalLayers.size()-1).build_Enet();
            if(totalLayers.size() > 1)
                totalLayers.get(totalLayers.size()-1).change_weights(totalLayers.get(totalLayers.size() - 2));
            else
                totalLayers.get(0).change_weights(inputlayer);

            for(int i = totalLayers.size()-2;i > 0;i--){
                totalLayers.get(i).build_Eout(totalLayers.get(i+1));
                totalLayers.get(i).build_out_net();
                totalLayers.get(i).build_Enet();
                totalLayers.get(i).change_weights(totalLayers.get(i - 1));
            }
            if(totalLayers.size() > 1){
                totalLayers.get(0).build_Eout(totalLayers.get(1));
                totalLayers.get(0).build_out_net();
                totalLayers.get(0).build_Enet();
                totalLayers.get(0).change_weights(inputlayer);
            }
        }
        else
        {
            outputlayer.change_weights(inputlayer);
        }
        outputlayer.switchweights();
        for(int i = 0;i<totalLayers.size();i++){
            totalLayers.get(i).switchweights();
        }
    }
    void build_neural_network(){
        for(int i = 0; i < used_data.size();i++)
        {
            input_into_neural(used_data.get(0));
        }
    }

    boolean test_neural_network(String[] inputvalue){
        Vector<Double> converted_value = new Vector<>();
        Vector<Double> targets = new Vector<>();
        Vector<Double> tests = new Vector<>();

        if(convert_binary_final){
            for(int i = 1;i < inputvalue.length;i++){
                if(value_information.continuous_marker.get(i)){
                    double temp_values = Double.parseDouble(inputvalue[i]);
                    temp_values = (temp_values - value_information.continuous_mean.get(i))/ value_information.continuous_standard_dev.get(i);
                    converted_value.add(temp_values);
                    continue;
                }
                for(int j = 0;j<attributes_sections.get(i);j++)
                {

                    if(j == values.get(i).get(inputvalue[i]))
                        converted_value.add(1.0);
                    else
                        converted_value.add(0.0);

                }
            }
        }
        else{
            for(int i = 1;i < inputvalue.length;i++){
                if(value_information.continuous_marker.get(i)){
                    double temp_values = 0;
                    if(!values.get(i).containsKey("continuous"))
                        temp_values = new Double(values.get(i).get(inputvalue[i]));
                    if(values.get(i).containsKey("continuous"))
                        temp_values = Double.parseDouble(inputvalue[i]);
                    temp_values = (temp_values - value_information.continuous_mean.get(i))/ value_information.continuous_standard_dev.get(i);
                    converted_value.add(temp_values);
                }
            }
        }

        inputlayer.insert_output_values(converted_value);
        if(totalLayers.size() != 0)
            totalLayers.get(0).input_nodes(inputlayer);
        for(int i = 1;i < totalLayers.size();i++){
            totalLayers.get(i).input_nodes(totalLayers.get(i-1));
        }
        if(totalLayers.size() != 0)
            outputlayer.input_nodes(totalLayers.get(totalLayers.size()-1));
        else
            outputlayer.input_nodes(inputlayer);

        for(int i = 0;i < outputlayer.get_node_size();i++){
            if(values.get(0).get(inputvalue[0]) == i)
                targets.add(1.0);
            else
                targets.add(0.0);
        }

        for(int i = 0;i < targets.size();i++) {
            if(outputlayer.get_outputvalues().get(i) > .5)
                tests.add(1.0);
            else
                tests.add(0.0);
        }
        for(int i =0;i < targets.size();i++)
            if(!targets.get(i).equals(tests.get(i)))
                return false;
        return true;
    }


}
