import java.util.List;
import java.util.Vector;

/**
 * Created by orion2166 on 2/14/2016.
 */
public class DecisionTree {
    private DatabaseManagment value_information;
    private Vector <AttributeNode> tree_attributes = new Vector<>();
    public DecisionTree(String attribute_file_location, String database_datasets_filelocatio)
    {
        value_information = new DatabaseManagment(attribute_file_location);
        value_information.setDataset(database_datasets_filelocatio);   }

    void set_attributes(){
        int num_attributes = value_information.get_attribute_size();
        String [] key_values = (String[]) value_information.get_attribute_keys();
        for(int i = 0;i<num_attributes;i++){
            tree_attributes.add(new AttributeNode(key_values[i]));
            List<String> classifiers = value_information.get_attribute_classifications(key_values[i]);
            for(int j = 0;j < classifiers.size();j++)
                tree_attributes.lastElement().initialize_attribute_values(classifiers.get(j));
        }

    }

    
}
