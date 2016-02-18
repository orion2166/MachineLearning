import java.util.*;

/**
 * Created by orion2166 on 2/14/2016.
 */
public class DecisionTree {
    private DatabaseManagment value_information;
    private Vector <AttributeNode> tree_attributes = new Vector<>();
    private Vector<String[]> used_data = new Vector<>();
    private int[] target_data_attributes = new int[]{0,0};
    private Vector <String> target_attribute_value = new Vector<>();
    private AttributeNode decision_tree_root_node = null;
    private Queue<AttributeNode> attributeNode_queue = new LinkedList<AttributeNode>();;

    public DecisionTree(String attribute_file_location, String database_datasets_filelocatio)
    {
        value_information = new DatabaseManagment(attribute_file_location);
        value_information.setDataset(database_datasets_filelocatio);   }
    boolean tree_finished(){
        for(int i = 0;i<tree_attributes.size();i++){
            if(!tree_attributes.get(i).node_is_complete())
                return false;
        }
        return true;
    }
    void set_attributes(){
        int num_attributes = value_information.get_attribute_size();
        Vector<String> key_values = value_information.get_attribute_keys();
        for(int i = 0;i<num_attributes;i++){
            tree_attributes.add(new AttributeNode(key_values.get(i)));
            List<String> classifiers = value_information.get_attribute_classifications(key_values.get(i));
            for(int j = 0;j < classifiers.size();j++)
                tree_attributes.lastElement().initialize_attribute_values(classifiers.get(j));
        }

    }


    void build_value_set(int percentage_used){
        value_information.randomize_data();
        int max = value_information.get_size();
        int number_sets = (max * percentage_used)/100;
        for(int i = 0;i<number_sets;i++)
        {
            used_data.add(value_information.get_value(i));
        }
    }
    void reset_attributes(){
        target_data_attributes = new int[]{0,0};
        for(int i = 0;i <tree_attributes.size();i++){
            HashMap transition_node_maps = tree_attributes.get(i).get_transition_nodes();
            Iterator<HashMap.Entry<String,int[]>> iterator = transition_node_maps.entrySet().iterator();
            while(iterator.hasNext()){
                String temperary = iterator.next().getKey();
                tree_attributes.get(i).reset_key(temperary);
            }
        }
    }
    void build_attributes_sets(String target_positive_value,String missing_value,Vector<String[]> usable_data){
        reset_attributes();
        for(int i = 0;i < usable_data.size();i++)
        {
            String [] change_value = usable_data.get(i);
            for(int l = 0;l<tree_attributes.size();l++){
                tree_attributes.get(l).list_add(change_value, change_value[l]);
            }
            boolean target_is_positive = change_value[0].equals(target_positive_value);
            for(int j = 0;j<change_value.length;j++)
            {
                HashMap transition_node_maps = tree_attributes.get(j).get_transition_nodes();
                if(change_value[j].equals(missing_value)){
                    Object[] key_values = transition_node_maps.keySet().toArray();
                    int max_change = 0;
                    int location_change = 0;
                    for(int l = 0;l <key_values.length;l++)
                    {
                        int temp = 0;
                        int[] update_value = (int[]) transition_node_maps.get(key_values[l]);
                        if(target_is_positive)
                        {
                            temp = update_value[0];
                        }
                        else
                        {
                            temp = update_value[1];
                        }
                        if(temp > max_change)
                        {
                            max_change = temp;
                            location_change = l;
                        }
                    }
                    if(target_is_positive){
                        int[] update_value = (int[]) transition_node_maps.get(key_values[location_change]);
                        update_value[0]++;
                        tree_attributes.get(j).update_attributes((String) key_values[location_change],update_value);
                    }
                    else{
                        int[] update_value = (int[]) transition_node_maps.get(key_values[location_change]);
                        update_value[1]++;
                        tree_attributes.get(j).update_attributes((String) key_values[location_change],update_value);
                    }
                }
                else {
                    if (target_is_positive) {
                        int[] transition_node_value = (int[]) transition_node_maps.get(change_value[j]);
                        transition_node_value[0]++;
                        tree_attributes.get(j).update_attributes(change_value[j], transition_node_value);
                    } else {
                        int[] transition_node_value = (int[]) transition_node_maps.get(change_value[j]);
                        transition_node_value[1]++;
                        tree_attributes.get(j).update_attributes(change_value[j], transition_node_value);
                    }
                }
            }
        }
        target_attribute_value.add(target_positive_value);
        HashMap transition_node_maps = tree_attributes.get(0).get_transition_nodes();
        Iterator<HashMap.Entry<String,int[]>> iterator = transition_node_maps.entrySet().iterator();
        while(iterator.hasNext()){
            Map.Entry<String,int[]> temperary = iterator.next();
            target_data_attributes[0] += temperary.getValue()[0];
            target_data_attributes[1] += temperary.getValue()[1];
            target_attribute_value.add(temperary.getKey());
        }
        tree_attributes.get(0).used();

    }

    void build_first_node_decision_tree(String target_positive_value,String missing_value) {
        build_attributes_sets(target_positive_value, missing_value, used_data);
        MathmaticCalculations gain_value = new MathmaticCalculations();
        double max_value = 0;
        Queue<AttributeNode> attributeNode_queue = new LinkedList<AttributeNode>();
        Vector<Double> tmp_gain_values = new Vector<>();
        for (int j = 0; j < tree_attributes.size(); j++) {
            if (tree_attributes.get(j).is_on_tree()) {
                tmp_gain_values.add((double) 0);
                continue;
            }
            HashMap temp = tree_attributes.get(j).get_transition_nodes();
            tmp_gain_values.add(gain_value.gain(target_data_attributes, temp));
        }
        int location = 0;
        for (int j = 0; j < tmp_gain_values.size(); j++) {
            if (max_value < tmp_gain_values.get(j)) {
                max_value = tmp_gain_values.get(j);
                location = j;
            }
        }
        if (decision_tree_root_node == null) {
            decision_tree_root_node = tree_attributes.get(location);
            tree_attributes.get(location).used();
            attributeNode_queue.add(decision_tree_root_node);
        }
    }
    void build_decision_tree(){
        while(!tree_finished()){
            tmp_gain_values = new Vector<>();
            int initial_queue_size = attributeNode_queue.size();
            for(int l = 0;l<initial_queue_size;l++)
            {
                AttributeNode temp_node_queue = attributeNode_queue.poll();
                HashMap transition_node_maps = temp_node_queue.get_transition_nodes();
                Iterator<HashMap.Entry<String,int[]>> iterator = transition_node_maps.entrySet().iterator();
                while(iterator.hasNext()){
                    Map.Entry<String,int[]> temperary = iterator.next();
                    String temp_key = temperary.getKey();
                    int [] temp_value = temperary.getValue();
                    double entrop_value = gain_value.Enthropy(temp_value);
                    if(entrop_value < 0.4 || i == 2){
                        if(temp_value[0] > temp_value[1])
                            temp_node_queue.update_all(target_attribute_value.get(0),null,temp_key);
                        else
                            temp_node_queue.update_all(target_attribute_value.get(1),null,temp_key);
                        continue;
                    }
                    target_data_attributes = temp_value;
                    for(int j = 0;j < tree_attributes.size();j++){
                        if(tree_attributes.get(j).is_on_tree()){
                            tmp_gain_values.add((double) 0);
                            continue;
                        }
                        HashMap temp = tree_attributes.get(j).get_transition_nodes();
                        tmp_gain_values.add(gain_value.gain(target_data_attributes,temp));
                    }

                    location = 0;
                    for(int j = 0;j < tmp_gain_values.size();j++) {
                        if (max_value < tmp_gain_values.get(j)) {
                            max_value = tmp_gain_values.get(j);
                            location = j;
                        }
                    }
                    if(max_value == 0)
                    {
                        if(temp_value[0] > temp_value[1])
                            temp_node_queue.update_all(target_attribute_value.get(0),null,temp_key);
                        else
                            temp_node_queue.update_all(target_attribute_value.get(1),null,temp_key);
                        continue;
                    }
                    else
                    {
                        temp_node_queue.update_all(null,tree_attributes.get(location),temp_key);
                        attributeNode_queue.add(tree_attributes.get(location));
                        tree_attributes.get(location).used();
                    }

                }
            }
        }
    }
}
