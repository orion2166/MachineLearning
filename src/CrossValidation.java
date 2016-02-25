import java.util.Vector;

/**
 * Created by orion_000 on 2/20/2016.
 */
public class CrossValidation {
    DecisionTree test_node = null;
    private Vector<Vector<String[]>> used_data = new Vector<>();
    private int percent = 0;
    private Vector<double[]> result_sets = new Vector<>();
    public CrossValidation(DecisionTree value){
        test_node = value;
        for(int i= 0;i<10;i++){
            used_data.add(new Vector<String[]>());
        }
    }
    Vector cross_validation(String targets,String missing,String prunning_method,String attribute,String data,int location){
        set_cross_values();
        for(int i = 0;i<10;i++)
        {
            test_node = new DecisionTree(attribute,data,location);
            build_cross_validation(used_data.get(i),targets,missing,prunning_method);
            for(int j = 0;j<10;j++)
            {
                double[] cross_results = new double[]{0.0,0.0};
                cross_results[0] = used_data.get(j).size();

                if(i == j) {
                    continue;
                }
                for(int k = 0;k<cross_results[0];k++)
                {
                    String[] test_values = used_data.get(j).get(k);
                    String Actual_value = test_values[0];
                    if(Actual_value.equals(test_node.test_tree(test_values))){
                        cross_results[1]++;
                    }
                }
                result_sets.add(cross_results);
            }
        }
//        test_node.print_decision_tree(test_node.decision_tree_root_node);
        return result_sets;
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
        if(prunning_method.equals("Pessimistic")){
            test_node.initialize_prune_error_values();
            test_node.pessimistic_prune_tree();
        }
    }
    void build_cross_validation(Vector<String[]> data,String targets,String missing,String prunning_method){
//        percent = value_percent;
//        test_node.value_information.randomize_data();
//        test_node.build_value_set(percent);
        test_node.used_data = data;
        test_node.decision_tree_root_node = null;
        test_node.build_first_node_decision_tree(targets, missing);
        test_node.build_decision_tree();
        test_node.decision_tree_test_builder(test_node.decision_tree_root_node);
        if(prunning_method.equals("Pessimistic")){
            test_node.initialize_prune_error_values();
            test_node.pessimistic_prune_tree();
        }
    }
    Double percent_accuracy_total_dataset(){
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

    Vector percent_accuracy_cross_validation(){
        MathmaticCalculations mathvalue = new MathmaticCalculations();
        Vector<Double> mean_average = new Vector<>();
        for(int i = 0;i<result_sets.size();i++)
        {
            mean_average.add((result_sets.get(i)[1]/result_sets.get(i)[0]));
        }
        double mean= 0;
        for(int i = 0;i<mean_average.size();i++)
        {
            mean += mean_average.get(i);
        }
        mean = mean/mean_average.size();
        double variance = 0;
        for(int i = 0;i<mean_average.size();i++)
        {
            variance += Math.pow((mean_average.get(i)-mean),2);
        }
        variance = variance/(mean_average.size()-1);
        double standard_deviation = Math.sqrt(variance);
        Vector<Double> results = new Vector<>();
        results.add(mean);
        results.add(variance);
        results.add(standard_deviation);
        Vector intervalvalue = mathvalue.confidence_interval(1 - mean, mean_average.size());
        results.add((Double)intervalvalue.firstElement());
        results.add((Double)intervalvalue.lastElement());
        return results;
    }

}
