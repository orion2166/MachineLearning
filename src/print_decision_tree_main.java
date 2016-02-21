/**
 * Created by orion_000 on 2/15/2016.
 */
public class print_decision_tree_main {
    public static void main(String[] args){
//        MathmaticCalculations tests = new MathmaticCalculations();
//
//        Double vasdfe = tests.Enthropy(new int[]{1,100});


        DecisionTree Basic_decision_tree = new DecisionTree("C:\\Users\\orion_000\\Documents\\GitHub\\MachineLearning\\database_sets\\Congressional_Voting_Records_Attributes","C:\\Users\\orion_000\\Documents\\GitHub\\MachineLearning\\database_sets\\Congressional_Voting_Records_Data");
        Basic_decision_tree.build_value_set(10);
        Basic_decision_tree.build_first_node_decision_tree("republican","?");
        Basic_decision_tree.build_decision_tree();
        Basic_decision_tree.print_decision_tree(Basic_decision_tree.decision_tree_root_node);
        Basic_decision_tree.decision_tree_test_builder(Basic_decision_tree.decision_tree_root_node);
        String testdata_value = Basic_decision_tree.test_tree("republican,y,y,?,?,y,y,?,?,n,y,?,?,?,y,n,y");
        testdata_value = Basic_decision_tree.test_tree("republican,n,n,n,y,y,y,n,n,n,y,n,y,y,y,n,y");

        int hasdfeasdfeasdf = 0;
    }

}
