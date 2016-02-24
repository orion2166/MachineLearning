import java.util.Vector;

/**
 * Created by orion_000 on 2/20/2016.
 */
public class CrossValidation {
    DecisionTree test_node = null;
    private Vector<Vector<String[]>> used_data = new Vector<>();
    private int percent = 0;
    public CrossValidation(DecisionTree value){
        test_node = value;
        for(int i= 0;i<10;i++){
            used_data.add(new Vector<String[]>());
        }
    }
    void set_cross_values(){
        test_node.value_information.randomize_data();
        int max = test_node.value_information.get_size();
//        int number_sets = (max * 10)/100;
        for(int i = 0;i<max;i++)
        {
            used_data.get(i % 10).add(test_node.value_information.get_value(i));
        }
    }
    void build_test_accuracy_cross_values(int value_percent,String targets,String missing,String prunning_method){
        percent = value_percent;
        test_node.value_information.randomize_data();
        test_node.build_value_set(percent);
        test_node.build_first_node_decision_tree(targets,missing);
        test_node.build_decision_tree();
        test_node.decision_tree_test_builder(test_node.decision_tree_root_node);
        test_node.print_decision_tree(test_node.decision_tree_root_node);
        if(prunning_method.equals("Pessimistic")){
            test_node.initialize_prune_error_values();
            test_node.pessimistic_prune_tree();
        }
    }
    Double percent_accuracy(){
        double right = 0;
        int max = test_node.value_information.get_size();
        int number_sets = (max * percent) / 100;
        for(int i = number_sets;i<max;i++)
        {
            String[] test_values = test_node.value_information.get_value(i);
            String Actual_value = test_values[0];
            if(Actual_value.equals(test_node.test_tree(test_values))){
                right ++;
            }
        }
        return (right/max)*100;
    }


}
