import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Vector;

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
    private boolean continuous = false;
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
    void reset_key(String key){
        attribute_transition_nodes.replace(key,new int[]{0,0});
        sublists.replace(key,new Vector<String[]>());
    }
    void list_add(String[] list_value,String key){
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


}
