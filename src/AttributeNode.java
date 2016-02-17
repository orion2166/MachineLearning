import java.util.HashMap;
import java.util.Vector;

/**
 * Created by orion2166 on 2/14/2016.
 */
public class AttributeNode {
    private String node_name;
    private HashMap<String,int[]> attribute_transition_nodes = new HashMap<String,int[]>();
    private HashMap<String,AttributeNode> attribute_children = new HashMap<String,AttributeNode>();
    private boolean node_on_tree;
    public AttributeNode(String name) {
        node_name = name;
        node_on_tree = false;
    }
    void initialize_attribute_values(String classification){
        attribute_transition_nodes.put(classification,new int[]{0,0});
    }
    void update_attributes(String classification, int[] updated_value){
        attribute_transition_nodes.replace(classification,updated_value);
    }
    HashMap get_transition_nodes(){return attribute_transition_nodes;}
    Boolean is_on_tree(){
        return node_on_tree;
    }
    void used(){
        node_on_tree = true;
    }

}
