import java.util.*;

/**
 * Created by orion2166 on 2/14/2016.
 */
public class AttributeNode {
    public String node_name;
    public HashMap<String,int[]> attribute_transition_nodes = new HashMap<String,int[]>();
    private HashMap<String,AttributeNode> attribute_children = new HashMap<String,AttributeNode>();
    private HashMap<String,String> next_connection = new HashMap<>();
    private HashMap<String, Vector> sublists = new HashMap<>();
    private HashMap<String, Double> prune = new HashMap<>();
    private boolean node_completed;
    private boolean node_on_tree;
    public double global_middle = 0.0;
    public boolean continuous = false;
    public AttributeNode(String name) {
        node_name = name;
        node_on_tree = false;
        node_completed = false;
    }
    void initialize_attribute_values(String classification,int size){
        int[] targets = new int[size];
        for(int i = 0;i<size;i++)
        {
            targets[i] = 0;
        }
        attribute_transition_nodes.put(classification,targets);
        sublists.put(classification,new Vector<String[]>());
    }
    void update_attributes(String classification, int[] updated_value){
        attribute_transition_nodes.replace(classification,updated_value);
    }
    HashMap get_transition_nodes(){return attribute_transition_nodes;}
    HashMap get_prune(){return prune;}
    Boolean is_on_tree(){
        return node_on_tree;
    }
    Boolean node_is_complete(){
        return node_completed;
    }
    void insert_prune(String key, Double prune_value){prune.put(key,prune_value);}
    void used(){
        node_on_tree = true;
    }
    void update_all(String end, AttributeNode connection, String key_value){
        attribute_children.put(key_value,connection);
        next_connection.put(key_value,end);
    }
    void reset_key(String key,int size){
        if(continuous) {
            attribute_transition_nodes.clear();
            sublists.clear();
            key = "continuous";
        }
        int[] newsets = new int[size];
        for(int k = 0; k < size; k++){
            newsets[k] = 0;
        }
        attribute_transition_nodes.replace(key,newsets);
        sublists.replace(key,new Vector<String[]>());
    }
    void list_add(String[] list_value,String key){
        if(!(sublists.get(key) == null))
            sublists.get(key).add(list_value);
    }
    Vector get_list(String key){
        return sublists.get(key);
    }
    void completed(){node_completed = true;}
    String key_connection(String key){
        return next_connection.get(key);
    }
    Double get_total_prune(){
        Double total = 0.0;
        Iterator<HashMap.Entry<String,Double>> iterator = prune.entrySet().iterator();
        while(iterator.hasNext()) {
            Map.Entry<String, Double> maptemp = iterator.next();
            total += maptemp.getValue();
        }
        return total;
    }

    AttributeNode get_node_connection(String key){
        return attribute_children.get(key);
    }
    Boolean is_last(){
        Iterator<HashMap.Entry<String,AttributeNode>> iterator = attribute_children.entrySet().iterator();
        while(iterator.hasNext()) {
            Map.Entry<String, AttributeNode> maptemp = iterator.next();
            if(maptemp.getValue() != null)
            {
                return false;
            }
        }
        return true;
    }
    void set_continuous_attribute_values(Vector<String[]> usable_data,int location,Vector<String> build_attribute){
        HashMap<String, Vector<Double>> bounds_determine = new HashMap<>();
        Vector<Double> overall_sets = new Vector<>();
        for(int i = 0;i<usable_data.size();i++){
            if(!bounds_determine.containsKey(usable_data.get(i)[0])){
                bounds_determine.put(usable_data.get(i)[0],new Vector<Double>());
            }
            overall_sets.add(Double.parseDouble(usable_data.get(i)[location]));
        }
        Collections.sort(overall_sets);
        double middle = (overall_sets.lastElement() - overall_sets.firstElement())/2.0;
        middle += overall_sets.firstElement();
        global_middle = middle;
        int[] enthropy_values = new int[build_attribute.size()];
        for(int k = 0;k<enthropy_values.length;k++)
            enthropy_values[k] = 0;
        attribute_transition_nodes.put("constant_upper",enthropy_values);
        attribute_transition_nodes.put("constant_lower",enthropy_values);
        sublists.put("constant_upper",new Vector<String[]>());
        sublists.put("constant_lower",new Vector<String[]>());

        for(int i = 0;i<usable_data.size();i++){
            if (Double.parseDouble(usable_data.get(i)[location]) > middle){
                attribute_transition_nodes.get("constant_upper")[build_attribute.lastIndexOf(usable_data.get(i)[0])]++;
                sublists.get("constant_upper").add(usable_data.get(i));

            }
            else
            {
                attribute_transition_nodes.get("constant_lower")[build_attribute.lastIndexOf(usable_data.get(i)[0])]++;
                sublists.get("constant_lower").add(usable_data.get(i));
            }

        }

    }


}
