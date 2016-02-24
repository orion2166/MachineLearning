/**
 * Created by orion_000 on 2/15/2016.
 */
public class print_decision_tree_main {
    public static void main(String[] args){
//        MathmaticCalculations tests = new MathmaticCalculations();
//
//        Double vasdfe = tests.Enthropy(new int[]{1,100});


        DecisionTree Basic_decision_tree = new DecisionTree("C:\\Users\\orion_000\\Documents\\GitHub\\MachineLearning\\database_sets\\Tic_Tac_Toe_Attributes","C:\\Users\\orion_000\\Documents\\GitHub\\MachineLearning\\database_sets\\Tic_Tac_Toe_Data",9);
        CrossValidation test_validation = new CrossValidation(Basic_decision_tree);
        test_validation.set_cross_values();
        test_validation.build_test_accuracy_cross_values(20,"positive", "?","");
        System.out.println(test_validation.percent_accuracy());
        test_validation.build_test_accuracy_cross_values(20,"positive", "?","Pessimistic");
        System.out.println(test_validation.percent_accuracy());



        Basic_decision_tree.build_value_set(30);



        Basic_decision_tree.build_first_node_decision_tree("positive", "?");
        Basic_decision_tree.build_decision_tree();
        Basic_decision_tree.print_decision_tree(Basic_decision_tree.decision_tree_root_node);
        Basic_decision_tree.decision_tree_test_builder(Basic_decision_tree.decision_tree_root_node);
        Basic_decision_tree.pessimistic_prune_tree();
        System.out.println("with pruned values");
        Basic_decision_tree.print_decision_tree(Basic_decision_tree.decision_tree_root_node);
//        String testdata_value = Basic_decision_tree.test_tree("positive,b,o,x,b,o,x,b,b,x");
//        testdata_value = Basic_decision_tree.test_tree("negative,o,o,x,x,o,b,x,o,x");

        int hasdfeasdfeasdf = 0;
    }

}
