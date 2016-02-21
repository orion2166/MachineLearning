import java.util.Vector;

/**
 * Created by orion_000 on 2/20/2016.
 */
public class CrossValidation {
    DecisionTree test_node = null;
    private Vector<Vector<String[]>> used_data = new Vector<>();
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


}
