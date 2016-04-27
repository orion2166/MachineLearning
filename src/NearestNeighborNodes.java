import java.util.HashMap;
import java.util.List;
import java.util.Vector;

/**
 * Created by orion_000 on 4/11/2016.
 */
public class NearestNeighborNodes extends NeuralNetworkNodes{
    public NearestNeighborNodes(int number_inputs) {
        super(number_inputs);
    }
//    void build_values(){
//        Vector keys = value_information.get_attribute_keys();
//        for(int i = 0;i < keys.size();i++){
//            HashMap<String,Integer> temps = new HashMap<>();
//            List<String> attribute_values = value_information.get_attribute_classifications((String) keys.get(i));
//            attributes_sections.add(attribute_values.size());
//            for(int j = 0;j < attribute_values.size();j++){
//                temps.put(attribute_values.get(j),j);
//            }
//            values.add(temps);
//        }


}
