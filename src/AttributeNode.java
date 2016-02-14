import java.util.HashMap;
import java.util.Vector;

/**
 * Created by orion2166 on 2/14/2016.
 */
public class AttributeNode {
    private String node_name;
    private HashMap<String,int[]> attribute_sidenodes = new HashMap<String,int[]>();
    private HashMap<String,AttributeNode> attribute_children = new HashMap<String,AttributeNode>();
    public AttributeNode(String name)
    {node_name = name;}

    

}
