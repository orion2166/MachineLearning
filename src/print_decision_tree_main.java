/**
 * Created by orion_000 on 2/15/2016.
 */
public class print_decision_tree_main {
    public static void main(String[] args){
        DecisionTree Basic_decision_tree = new DecisionTree("C:\\Users\\orion_000\\Documents\\GitHub\\MachineLearning\\database_sets\\Congressional_Voting_Records_Attributes","C:\\Users\\orion_000\\Documents\\GitHub\\MachineLearning\\database_sets\\Congressional_Voting_Records_Data");
        Basic_decision_tree.set_attributes();
        Basic_decision_tree.build_value_set(10);
        Basic_decision_tree.build_first_node_decision_tree("democrat","?");
//        Double valuesasdf = Basic_decision_tree.gaintest();
        int hlepers = 0;
    }

}
