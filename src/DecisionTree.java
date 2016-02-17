import java.util.*;

/**
 * Created by orion2166 on 2/14/2016.
 */
public class DecisionTree {
    private DatabaseManagment value_information;
    private Vector <AttributeNode> tree_attributes = new Vector<>();
    private Vector<String[]> used_data = new Vector<>();
    private int[] target_data_attributes = new int[]{0,0};
    private AttributeNode decision_tree_root_node = null;

    public DecisionTree(String attribute_file_location, String database_datasets_filelocatio)
    {
        value_information = new DatabaseManagment(attribute_file_location);
        value_information.setDataset(database_datasets_filelocatio);   }

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
        Random rand = new Random();

        int max = value_information.get_size();
        List<Boolean> list = new ArrayList<Boolean>(Collections.nCopies(max, false));
        int number_sets = (max * percentage_used)/100;
        for(int i = 0;i<number_sets;i++)
        {
            int random_value = rand.nextInt(max);
            if(list.get(random_value)){
                //it has already been used
                i--;
            }
            else {
                used_data.add(value_information.get_value(random_value));
                list.set(random_value,true);
            }
        }
    }
    void build_attributes_sets(String target_positive_value,String missing_value){
        for(int i = 0;i < used_data.size();i++)
        {
            String [] change_value = used_data.get(i);
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
        HashMap transition_node_maps = tree_attributes.get(0).get_transition_nodes();
        Iterator<HashMap.Entry<String,int[]>> iterator = transition_node_maps.entrySet().iterator();
        while(iterator.hasNext()){
            int[] temp = iterator.next().getValue();
            target_data_attributes[0] += temp[0];
            target_data_attributes[1] += temp[1];
        }

    }

    void build_decision_tree(){
        MathmaticCalculations gain_value = new MathmaticCalculations();
        double max_value = 0;
        for(int i = 0;i < 3;i++){
            if(decision_tree_root_node){

            }
            Vector <Double> tmp_gain_values = new Vector<>();
            for(int j = 0;j < tree_attributes.size();j++){
                if(tree_attributes.get(j).is_on_tree()){
                    tmp_gain_values.add((double) 0);
                    continue;
                }
                HashMap temp = tree_attributes.get(j).get_transition_nodes();
                tmp_gain_values.add(gain_value.gain(target_data_attributes,temp));
            }
            int location = 0;
            for(int j = 0;j < tmp_gain_values.size();j++) {
                if (max_value < tmp_gain_values.get(j)) {
                    max_value = tmp_gain_values.get(j);
                    location = j;
                }
            }
            if(decision_tree_root_node == null)
            {
                decision_tree_root_node = tree_attributes.get(location);
                tree_attributes.get(location).used();
            }

        }
    }
}
