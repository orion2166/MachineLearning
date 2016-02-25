/**
 * Created by orion_000 on 2/15/2016.
 */
public class print_decision_tree_main {
    public static void main(String[] args){

        // THORAC_SURGERY_TESTS -------------------------------------------------------------------------------------------
        String attributes = "C:\\Users\\orion_000\\Documents\\GitHub\\MachineLearning\\database_sets\\Thoracic_Surgery_Attribute";
        String data = "C:\\Users\\orion_000\\Documents\\GitHub\\MachineLearning\\database_sets\\Thoracic_Surgery_Data";
        DecisionTree Basic_decision_tree = new DecisionTree(attributes,data,16);
        CrossValidation test_validation = new CrossValidation(Basic_decision_tree);
        test_validation.set_cross_values();
        test_validation.cross_validation("DGN3", "?", "");
        System.out.println(test_validation.percent_accuracy_cross_validation());
        Basic_decision_tree.print_decision_tree(Basic_decision_tree.decision_tree_root_node);

        System.out.println("with pruning");

        Basic_decision_tree = new DecisionTree(attributes,data,16);
        test_validation = new CrossValidation(Basic_decision_tree);
        test_validation.set_cross_values();
        test_validation.cross_validation("DGN3", "?", "Pessimistic");
        System.out.println(test_validation.percent_accuracy_cross_validation());

        // CHESS_KING_PAWN-------------------------------------------------------------------------------------------
        attributes = "C:\\Users\\orion_000\\Documents\\GitHub\\MachineLearning\\database_sets\\Chess_King-Rook-Vs-King-Pawn_Attribute";
        data = "C:\\Users\\orion_000\\Documents\\GitHub\\MachineLearning\\database_sets\\Chess_King-Rook-Vs-King-Pawn_Data";
        Basic_decision_tree = new DecisionTree(attributes,data,36);
        test_validation = new CrossValidation(Basic_decision_tree);
        test_validation.set_cross_values();
        test_validation.cross_validation("won", "?", "");
        System.out.println(test_validation.percent_accuracy_cross_validation());

        System.out.println("with pruning");

        Basic_decision_tree = new DecisionTree(attributes,data,36);
        test_validation = new CrossValidation(Basic_decision_tree);
        test_validation.set_cross_values();
        test_validation.cross_validation("won", "?", "Pessimistic");
        System.out.println(test_validation.percent_accuracy_cross_validation());
        Basic_decision_tree.print_decision_tree(Basic_decision_tree.decision_tree_root_node);

        // Tic_Tac_Toe -------------------------------------------------------------------------------------------
        attributes = "C:\\Users\\orion_000\\Documents\\GitHub\\MachineLearning\\database_sets\\Tic_Tac_Toe_Attributes";
        data = "C:\\Users\\orion_000\\Documents\\GitHub\\MachineLearning\\database_sets\\Tic_Tac_Toe_Data";
        Basic_decision_tree = new DecisionTree(attributes,data,9);
        test_validation = new CrossValidation(Basic_decision_tree);
        test_validation.set_cross_values();
        test_validation.cross_validation("positive", "?", "");
        System.out.println(test_validation.percent_accuracy_cross_validation());

        System.out.println("with pruning");

        Basic_decision_tree = new DecisionTree(attributes,data,9);
        test_validation = new CrossValidation(Basic_decision_tree);
        test_validation.set_cross_values();
        test_validation.cross_validation("positive", "?", "Pessimistic");
        System.out.println(test_validation.percent_accuracy_cross_validation());
        Basic_decision_tree.print_decision_tree(Basic_decision_tree.decision_tree_root_node);

        // Congressional_Voting -------------------------------------------------------------------------------------------
        attributes = "C:\\Users\\orion_000\\Documents\\GitHub\\MachineLearning\\database_sets\\Congressional_Voting_Records_Attributes";
        data = "C:\\Users\\orion_000\\Documents\\GitHub\\MachineLearning\\database_sets\\Congressional_Voting_Records_Data";
        Basic_decision_tree = new DecisionTree(attributes,data,0);
        test_validation = new CrossValidation(Basic_decision_tree);
        test_validation.set_cross_values();
        test_validation.cross_validation("democrat", "?", "");
        System.out.println(test_validation.percent_accuracy_cross_validation());

        System.out.println("with pruning");

        Basic_decision_tree = new DecisionTree(attributes,data,0);
        test_validation = new CrossValidation(Basic_decision_tree);
        test_validation.set_cross_values();
        test_validation.cross_validation("democrat", "?", "Pessimistic");
        System.out.println(test_validation.percent_accuracy_cross_validation());
        Basic_decision_tree.print_decision_tree(Basic_decision_tree.decision_tree_root_node);

        // Monks_Problems -------------------------------------------------------------------------------------------
        attributes = "C:\\Users\\orion_000\\Documents\\GitHub\\MachineLearning\\database_sets\\Monks_Problems_Attributes";
        data = "C:\\Users\\orion_000\\Documents\\GitHub\\MachineLearning\\database_sets\\Monks_Problems_Data";
        Basic_decision_tree = new DecisionTree(attributes,data,0);
        test_validation = new CrossValidation(Basic_decision_tree);
        test_validation.set_cross_values();
        test_validation.cross_validation("1", "?", "");
        System.out.println(test_validation.percent_accuracy_cross_validation());

        System.out.println("with pruning");

        Basic_decision_tree = new DecisionTree(attributes,data,0);
        test_validation = new CrossValidation(Basic_decision_tree);
        test_validation.set_cross_values();
        test_validation.cross_validation("1", "?", "Pessimistic");
        System.out.println(test_validation.percent_accuracy_cross_validation());
        Basic_decision_tree.print_decision_tree(Basic_decision_tree.decision_tree_root_node);

    }

}
