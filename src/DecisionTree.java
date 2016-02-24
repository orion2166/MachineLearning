import java.util.*;

/**
 * Created by orion2166 on 2/14/2016.
 */
public class DecisionTree {
    public DatabaseManagment value_information;
    private Vector<AttributeNode> tree_attributes = new Vector<>();
    public Vector<String[]> used_data = new Vector<>();
    private int[] target_data_attributes = null;
    private Vector<String> target_attribute_value = new Vector<>();
    public AttributeNode decision_tree_root_node = null;
    private static Queue<AttributeNode> attributeNode_queue = null;
    private String global_target_positive_value, global_missing_value;
    private HashMap<String, Integer> test_tree_location = new HashMap<>();

    public DecisionTree(String attribute_file_location, String database_datasets_filelocatio,int target) {
        value_information = new DatabaseManagment(attribute_file_location,target);
        value_information.setDataset(database_datasets_filelocatio);
        attributeNode_queue = new LinkedList();
        set_attributes();
    }

    boolean tree_finished() {
        for (int i = 1; i < tree_attributes.size(); i++) {
            if (!tree_attributes.get(i).node_is_complete())
                return false;
        }
        return true;
    }

    void set_target_values(String target_pos) {
        if (target_attribute_value.isEmpty() || !target_attribute_value.contains(target_pos)) {
            target_attribute_value.add(target_pos);
            HashMap transition_node_maps = tree_attributes.get(0).get_transition_nodes();
            Iterator<HashMap.Entry<String, int[]>> iterator = transition_node_maps.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<String, int[]> temperary = iterator.next();
                if (!temperary.getKey().equals(target_pos))
                    target_attribute_value.add(temperary.getKey());
            }
            tree_attributes.get(0).completed();
        }
    }


    void set_attributes() {
        int num_attributes = value_information.get_attribute_size();
        Vector<String> key_values = value_information.get_attribute_keys();
        int num_targets = 0;
        for (int i = 0; i < num_attributes; i++) {
            tree_attributes.add(new AttributeNode(key_values.get(i)));
            List<String> classifiers = value_information.get_attribute_classifications(key_values.get(i));
            if(i == 0){
                num_targets = classifiers.size();
                for(int j = 0;j<num_targets;j++)
                {
                    set_target_values(classifiers.get(j));
                }
            }

            for (int j = 0; j < classifiers.size(); j++)
                tree_attributes.lastElement().initialize_attribute_values(classifiers.get(j),num_targets);
        }

    }


    void build_value_set(int percentage_used) {
        value_information.randomize_data();
        int max = value_information.get_size();
        int number_sets = (max * percentage_used) / 100;
        for (int i = 0; i < number_sets; i++) {
            used_data.add(value_information.get_value(i));
        }
    }

    void reset_attributes() {
        target_data_attributes = new int[target_attribute_value.size()];
        for(int i = 0;i<target_data_attributes.length;i++)
        {
            target_data_attributes[i] = 0;
        }
        for (int i = 0; i < tree_attributes.size(); i++) {
            HashMap transition_node_maps = tree_attributes.get(i).get_transition_nodes();
            if (tree_attributes.get(i).is_on_tree())
                continue;
            Iterator<HashMap.Entry<String, int[]>> iterator = transition_node_maps.entrySet().iterator();
            while (iterator.hasNext()) {
                String temperary = iterator.next().getKey();
                tree_attributes.get(i).reset_key(temperary);
            }
        }
    }

    void build_attributes_sets(String target_positive_value, String missing_value, Vector<String[]> usable_data) {
        reset_attributes();

        for (int i = 0; i < usable_data.size(); i++) {

            String[] change_value = usable_data.get(i);
            int target_location = 0;
            for(int k = 0;k<target_attribute_value.size();k++)
            {
                if(change_value[0].equals(target_attribute_value.get(k)))
                    target_location = k;
            }
            for (int l = 0; l < tree_attributes.size(); l++) {
                if (tree_attributes.get(l).is_on_tree())
                    continue;
                HashMap transition_node_maps = tree_attributes.get(l).get_transition_nodes();
                if (change_value[l].equals(missing_value)) {
                    Object[] key_values = transition_node_maps.keySet().toArray();
                    int max_change = 0;
                    int location_change = 0;
                    for (int j = 0; j < key_values.length; j++) {
                        int temp = 0;
                        int[] update_value = (int[]) transition_node_maps.get(key_values[j]);
                        temp = update_value[target_location];
                        if (temp > max_change) {
                            max_change = temp;
                            location_change = j;
                        }
                    }
                    tree_attributes.get(l).list_add(change_value, (String) key_values[location_change]);
                } else
                    tree_attributes.get(l).list_add(change_value, change_value[l]);
            }

            for (int j = 0; j < change_value.length; j++) {
                HashMap transition_node_maps = tree_attributes.get(j).get_transition_nodes();
                if (change_value[j].equals(missing_value)) {
                    Object[] key_values = transition_node_maps.keySet().toArray();
                    int max_change = 0;
                    int location_change = 0;
                    for (int l = 0; l < key_values.length; l++) {
                        int temp = 0;
                        int[] update_value = (int[]) transition_node_maps.get(key_values[l]);
                        temp = update_value[target_location];
                        if (temp > max_change) {
                            max_change = temp;
                            location_change = l;
                        }
                    }
                    int[] update_value = (int[]) transition_node_maps.get(key_values[location_change]);
                    update_value[target_location]++;
                    tree_attributes.get(j).update_attributes((String) key_values[location_change], update_value);

                } else {
                    int[] transition_node_value = (int[]) transition_node_maps.get(change_value[j]);
                    transition_node_value[target_location]++;
                    tree_attributes.get(j).update_attributes(change_value[j], transition_node_value);
                }
            }
        }
        set_target_values(target_positive_value);
        HashMap transition_node_maps = tree_attributes.get(0).get_transition_nodes();
        Iterator<HashMap.Entry<String, int[]>> iterator = transition_node_maps.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, int[]> temperary = iterator.next();
            for(int k = 0;k < target_data_attributes.length;k++)
            {
                target_data_attributes[k] += temperary.getValue()[k];
            }

        }
        tree_attributes.get(0).used();

    }

    void build_first_node_decision_tree(String target_positive_value, String missing_value) {
        global_missing_value = missing_value;
        global_target_positive_value = target_positive_value;

        build_attributes_sets(target_positive_value, missing_value, used_data);
        MathmaticCalculations gain_value = new MathmaticCalculations();
        double max_value = 0;
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
        decision_tree_root_node = tree_attributes.get(location);
        tree_attributes.get(location).used();
        attributeNode_queue.add(tree_attributes.get(location));
    }

    void build_decision_tree() {
        MathmaticCalculations gain_value = new MathmaticCalculations();
        while (!attributeNode_queue.isEmpty()) {

            AttributeNode temp_node_queue = attributeNode_queue.remove();

            // Getting all subsets
            HashMap transition_node_maps = temp_node_queue.get_transition_nodes();
            Iterator<HashMap.Entry<String, int[]>> iterator = transition_node_maps.entrySet().iterator();
            while (iterator.hasNext()) {
                Vector<Double> tmp_gain_values = new Vector<>();
                //CHECK IF IT IS ONE SIDED THEN DEISGNATE VALUE
                double max_value = 0;
                Map.Entry<String, int[]> temperary = iterator.next();
                String temp_key = temperary.getKey();
                int[] temp_value = temperary.getValue();
                double entrop_value = gain_value.Enthropy(temp_value);
                if (Double.isNaN(entrop_value) || tree_finished()) {
                    double max = 0.0;
                    int location = 0;
                    for(int k = 0;k<temp_value.length;k++)
                    {
                        if(temp_value[k] > max)
                        {
                            max = temp_value[k];
                            location = k;
                        }
                    }
                    temp_node_queue.update_all(target_attribute_value.get(location), null, temp_key);
                    continue;
                }

                build_attributes_sets(global_target_positive_value, global_missing_value, temp_node_queue.get_list(temp_key));
                target_data_attributes = temp_value;
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
                if (max_value != 0) {
                    temp_node_queue.update_all(tree_attributes.get(location).node_name, tree_attributes.get(location), temp_key);
                    attributeNode_queue.add(tree_attributes.get(location));
                    tree_attributes.get(location).used();
                } else {
                    attributeNode_queue.add(temp_node_queue);
                }

            }
            temp_node_queue.completed();

        }
    }

    public void print_decision_tree(AttributeNode leafnodes) {
        String connections = leafnodes.node_name + " - ";
        HashMap transition_node_maps = leafnodes.get_transition_nodes();
        Iterator<HashMap.Entry<String, int[]>> iterator = transition_node_maps.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, int[]> temperary = iterator.next();
            String temp_key = temperary.getKey();
            connections += "(" + temp_key + " -> " + leafnodes.key_connection(temp_key) + ")";
            AttributeNode temperary_attribute = leafnodes.get_node_connection(temp_key);
            if (temperary_attribute != null)
                print_decision_tree(temperary_attribute);
        }
        System.out.println(connections);

    }

    void decision_tree_test_builder(AttributeNode leafnodes) {
        String name = leafnodes.node_name;
        for (int i = 0; i < tree_attributes.size(); i++) {
            if (tree_attributes.get(i).node_name.equals(name))
                test_tree_location.put(name, i);
        }
        HashMap transition_node_maps = leafnodes.get_transition_nodes();
        Iterator<HashMap.Entry<String, int[]>> iterator = transition_node_maps.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, int[]> temperary = iterator.next();
            String temp_key = temperary.getKey();
            AttributeNode temperary_attribute = leafnodes.get_node_connection(temp_key);
            if (temperary_attribute != null)
                decision_tree_test_builder(temperary_attribute);
        }
    }

    String test_tree(String[] test_value) {
        String[] seperated_values = test_value;
        AttributeNode temperary = decision_tree_root_node;
        while (true) {
            String returned_value = "";
            if (seperated_values[test_tree_location.get(temperary.node_name)].equals(global_missing_value)) {
                seperated_values = replace_missing_value(seperated_values, temperary);
            }
            returned_value = temperary.key_connection(seperated_values[test_tree_location.get(temperary.node_name)]);
            if (test_tree_location.containsKey(returned_value))
                temperary = temperary.get_node_connection(seperated_values[test_tree_location.get(temperary.node_name)]);
            else
                return returned_value;
        }
    }

    String[] replace_missing_value(String[] actual_set, AttributeNode missingtemp) {
        int max = 0;
        String[] temp_actual = actual_set;
        String replace_missing = "";
        HashMap transition_node_maps = missingtemp.get_transition_nodes();
        Iterator<HashMap.Entry<String, int[]>> iterator = transition_node_maps.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, int[]> maptemp = iterator.next();
            String temp_key = maptemp.getKey();
            String specific = missingtemp.key_connection(temp_key);
            if (!target_attribute_value.contains(specific)) {
                if (temp_actual[test_tree_location.get(specific)].equals(global_missing_value))
                    temp_actual = replace_missing_value(temp_actual, missingtemp.get_node_connection(temp_key));
                else {
                    int[] weighted_value_list = missingtemp.get_node_connection(temp_key).attribute_transition_nodes.get(temp_actual[test_tree_location.get(missingtemp.key_connection(temp_key))]);
                    int weight = 0;
                    for (int j = 0; j < weighted_value_list.length; j++) {
                        weight += weighted_value_list[j];
                    }
                    if (max < weight) {
                        max = weight;
                        replace_missing = temp_key;
                    }

                }
            } else {
                int[] weighted_value_list = missingtemp.attribute_transition_nodes.get(temp_key);
                int weight = 0;
                for (int j = 0; j < weighted_value_list.length; j++) {
                    weight += weighted_value_list[j];
                }
                if (max < weight) {
                    max = weight;
                    replace_missing = temp_key;
                }
            }


        }
        temp_actual[test_tree_location.get(missingtemp.node_name)] = replace_missing;
        return temp_actual;
    }

    void initialize_prune_error_values() {
        Queue<AttributeNode> temperary_tree = new LinkedList<>();
        temperary_tree.add(decision_tree_root_node);
        MathmaticCalculations prune = new MathmaticCalculations();
        while (!temperary_tree.isEmpty()) {
            AttributeNode queue_pull = temperary_tree.poll();
            HashMap transition_node_maps = queue_pull.get_transition_nodes();
            Iterator<HashMap.Entry<String, int[]>> iterator = transition_node_maps.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<String, int[]> maptemp = iterator.next();
                String temp_key = maptemp.getKey();
                Double prune_value = prune.normal_aproximation(maptemp.getValue());
                queue_pull.insert_prune(temp_key, prune_value);
                AttributeNode temp = queue_pull.get_node_connection(temp_key);
                if (temp != null)
                    temperary_tree.add(temp);
            }
        }
    }

    void pessimistic_prune_tree() {
        initialize_prune_error_values();
        Queue<AttributeNode> temperary_tree = new LinkedList<>();
        temperary_tree.add(decision_tree_root_node);
        HashMap<String,Double> need_prune = new HashMap<>();
        while (!temperary_tree.isEmpty()) {
            AttributeNode queue_pull = temperary_tree.poll();
            HashMap transition_node_maps = queue_pull.get_prune();
            Iterator<HashMap.Entry<String, Double>> iterator = transition_node_maps.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<String, Double> maptemp = iterator.next();
                String temp_key = maptemp.getKey();
                AttributeNode temp = queue_pull.get_node_connection(temp_key);
                if(temp != null){
                    if (maptemp.getValue() < temp.get_total_prune() && temp.is_last())
                    {
                        int max = 0;
                        int location = 0;
                        HashMap transition = queue_pull.get_transition_nodes();
                        int[] pruneing = (int[]) transition.get(temp_key);
                        for(int i = 0;i<pruneing.length;i++){
                            if(pruneing[i] > max){
                                max = pruneing[i];
                                location = i;
                            }
                        }
                        queue_pull.update_all(target_attribute_value.get(location),null,temp_key);
                        temperary_tree.clear();
                        pessimistic_prune_tree();
                    }
                    else
                        temperary_tree.add(temp);
                }

            }
        }
    }
}
