import java.util.HashMap;
import java.util.Vector;

/**
 * Created by orion2166 on 2/14/2016.
 */
public class AttributeNode {
    private String node_name;
    private HashMap<String,int[]> attribute_transition_nodes = new HashMap<String,int[]>();
    private HashMap<String,AttributeNode> attribute_children = new HashMap<String,AttributeNode>();
    private HashMap<String,String> next_connection = new HashMap<>();
    private HashMap<String, Vector> sublists = new HashMap<>();
    private boolean node_completed;
    private boolean node_on_tree;
    public AttributeNode(String name) {
        node_name = name;
        node_on_tree = false;
        node_completed = false;
    }
    void initialize_attribute_values(String classification){
        attribute_transition_nodes.put(classification,new int[]{0,0});
        sublists.put(classification,new Vector<String[]>());
    }
    void update_attributes(String classification, int[] updated_value){
        attribute_transition_nodes.replace(classification,updated_value);
    }
    HashMap get_transition_nodes(){return attribute_transition_nodes;}
    Boolean is_on_tree(){
        return node_on_tree;
    }
    Boolean node_is_complete(){
        return node_completed;
    }
    void used(){
        node_on_tree = true;
    }
    void update_all(String end, AttributeNode connection, String key_value){
        attribute_children.put(key_value,connection);
        next_connection.put(key_value,end);
    }
    void reset_key(String key){
        attribute_transition_nodes.replace(key,new int[]{0,0});
    }
    void list_add(String[] list_value,String key){
        sublists.get(key).add(list_value);
    }


}
