import java.util.Vector;

/**
 * Created by orion_000 on 3/21/2016.
 */
public class print_neura_network_main {
    public static void main(String[] args) {
        double learning_rate = 0.01;
        boolean binary_continuous = false;
        double binary = 0.0;
        for(int k = 0; k <=1 ;k++) {
            for (int i = 0; i <= 2; i++)
                for (int j = 2; j < 12; j += 2) {
                    NeuralNetworkMathmatics t_tests = new NeuralNetworkMathmatics();
                    String attributes = "C:\\Users\\orion_000\\Documents\\GitHub\\MachineLearning\\database_sets\\Monks_Problems_Attributes";
                    String data = "C:\\Users\\orion_000\\Documents\\GitHub\\MachineLearning\\database_sets\\Monks_Problems_Data";
                    NeuralNetworkCrossValidation test_validation = new NeuralNetworkCrossValidation();
                    test_validation.cross_validation(j, i, attributes, data, 0, binary_continuous);
                    Vector<Double> return_values = test_validation.percent_accuracy_cross_validation();
                    return_values.add(t_tests.t_test(.76849, return_values.get(0), .023826, return_values.get(2), 432));
                    return_values.add(learning_rate);
                    return_values.add(0.0);
                    return_values.add((double) j);
                    return_values.add((double) i);
                    return_values.add(binary);
                    System.out.print("Monks_Problems, ");
                    System.out.println(return_values);

//        System.out.println("-----------------------------------------MultiOutput--------------------------");

                    NeuralNetworkCrossValidationMouput test_validation_multiple = new NeuralNetworkCrossValidationMouput();
                    test_validation_multiple.cross_validation(j, i, attributes, data, 0, binary_continuous);
                    return_values = test_validation_multiple.percent_accuracy_cross_validation();
                    return_values.add(t_tests.t_test(.76849, return_values.get(0), .023826, return_values.get(2), 432));
                    return_values.add(learning_rate);
                    return_values.add(1.0);
                    return_values.add((double) j);
                    return_values.add((double) i);
                    return_values.add(binary);
                    System.out.print("Monks_Problems, ");
                    System.out.println(return_values);
//        System.out.println(test_validation_multiple.percent_accuracy_cross_validation());

//        System.out.println("------------------------------------------------------------------------------");

//        attributes = "C:\\Users\\orion_000\\Documents\\GitHub\\MachineLearning\\database_sets\\Thoracic_Surgery_Attribute";
//        data = "C:\\Users\\orion_000\\Documents\\GitHub\\MachineLearning\\database_sets\\Thoracic_Surgery_Data";
//        test_validation = new NeuralNetworkCrossValidation();
//        test_validation.cross_validation(10, 20, attributes, data, 16,true);
//        System.out.println(test_validation.percent_accuracy_cross_validation());

                    attributes = "C:\\Users\\orion_000\\Documents\\GitHub\\MachineLearning\\database_sets\\Tic_Tac_Toe_Attributes";
                    data = "C:\\Users\\orion_000\\Documents\\GitHub\\MachineLearning\\database_sets\\Tic_Tac_Toe_Data";
                    test_validation = new NeuralNetworkCrossValidation();
                    test_validation.cross_validation(j, i, attributes, data, 9, binary_continuous);
                    return_values = test_validation.percent_accuracy_cross_validation();
                    return_values.add(t_tests.t_test(.73332, return_values.get(0), .019633, return_values.get(2), 958));
                    return_values.add(learning_rate);
                    return_values.add(0.0);
                    return_values.add((double) j);
                    return_values.add((double) i);
                    return_values.add(binary);
                    System.out.print("Tic_Tac_Toe, ");
                    System.out.println(return_values);
//        System.out.println(test_validation.percent_accuracy_cross_validation());

//        System.out.println("-----------------------------------------MultiOutput--------------------------");

                    test_validation_multiple = new NeuralNetworkCrossValidationMouput();
                    test_validation_multiple.cross_validation(j, i, attributes, data, 9, binary_continuous);
                    return_values = test_validation_multiple.percent_accuracy_cross_validation();
                    return_values.add(t_tests.t_test(.73332, return_values.get(0), .019633, return_values.get(2), 958));
                    return_values.add(learning_rate);
                    return_values.add(1.0);
                    return_values.add((double) j);
                    return_values.add((double) i);
                    return_values.add(binary);
                    System.out.print("Tic_Tac_Toe, ");
                    System.out.println(return_values);
//        System.out.println(test_validation_multiple.percent_accuracy_cross_validation());

//        System.out.println("------------------------------------------------------------------------------");

                    attributes = "C:\\Users\\orion_000\\Documents\\GitHub\\MachineLearning\\database_sets\\Thoracic_Surgery_Attribute";
                    data = "C:\\Users\\orion_000\\Documents\\GitHub\\MachineLearning\\database_sets\\Thoracic_Surgery_Data";
                    test_validation = new NeuralNetworkCrossValidation();
                    test_validation.cross_validation(j, i, attributes, data, 16, binary_continuous);
                    return_values = test_validation.percent_accuracy_cross_validation();
                    return_values.add(t_tests.t_test(.83392, return_values.get(0), .041207, return_values.get(2), 470));
                    return_values.add(learning_rate);
                    return_values.add(0.0);
                    return_values.add((double) j);
                    return_values.add((double) i);
                    return_values.add(binary);
                    System.out.print("Thoracic_Surgery, ");
                    System.out.println(return_values);
//        System.out.println(test_validation.percent_accuracy_cross_validation());

//        System.out.println("-----------------------------------------MultiOutput--------------------------");

                    test_validation_multiple = new NeuralNetworkCrossValidationMouput();
                    test_validation_multiple.cross_validation(j, i, attributes, data, 16, binary_continuous);
                    return_values = test_validation_multiple.percent_accuracy_cross_validation();
                    return_values.add(t_tests.t_test(.83392, return_values.get(0), .041207, return_values.get(2), 470));
                    return_values.add(learning_rate);
                    return_values.add(1.0);
                    return_values.add((double) j);
                    return_values.add((double) i);
                    return_values.add(binary);
                    System.out.print("Thoracic_Surgery, ");
                    System.out.println(return_values);
//        System.out.println(test_validation_multiple.percent_accuracy_cross_validation());

//        System.out.println("------------------------------------------------------------------------------");

                    attributes = "C:\\Users\\orion_000\\Documents\\GitHub\\MachineLearning\\database_sets\\Chess_King-Rook-Vs-King-Pawn_Attribute";
                    data = "C:\\Users\\orion_000\\Documents\\GitHub\\MachineLearning\\database_sets\\Chess_King-Rook-Vs-King-Pawn_Data";
                    test_validation = new NeuralNetworkCrossValidation();
                    test_validation.cross_validation(j, i, attributes, data, 36, binary_continuous);
                    return_values = test_validation.percent_accuracy_cross_validation();
                    return_values.add(t_tests.t_test(.81805, return_values.get(0), .03447, return_values.get(2), 3196));
                    return_values.add(learning_rate);
                    return_values.add(0.0);
                    return_values.add((double) j);
                    return_values.add((double) i);
                    return_values.add(binary);
                    System.out.print("Chess_King-Rook-Vs-King-Pawn, ");
                    System.out.println(return_values);
//        System.out.println(test_validation.percent_accuracy_cross_validation());

//        System.out.println("-----------------------------------------MultiOutput--------------------------");

                    test_validation_multiple = new NeuralNetworkCrossValidationMouput();
                    test_validation_multiple.cross_validation(j, i, attributes, data, 36, binary_continuous);
                    return_values = test_validation_multiple.percent_accuracy_cross_validation();
                    return_values.add(t_tests.t_test(.81805, return_values.get(0), .03447, return_values.get(2), 3196));
                    return_values.add(learning_rate);
                    return_values.add(1.0);
                    return_values.add((double) j);
                    return_values.add((double) i);
                    return_values.add(binary);
                    System.out.print("Chess_King-Rook-Vs-King-Pawn, ");
                    System.out.println(return_values);
//        System.out.println(test_validation_multiple.percent_accuracy_cross_validation());

//        System.out.println("------------------------------------------------------------------------------");

                    attributes = "C:\\Users\\orion_000\\Documents\\GitHub\\MachineLearning\\database_sets\\Congressional_Voting_Records_Attributes_neural";
                    data = "C:\\Users\\orion_000\\Documents\\GitHub\\MachineLearning\\database_sets\\Congressional_Voting_Records_Data";
                    test_validation = new NeuralNetworkCrossValidation();
                    test_validation.cross_validation(j, i, attributes, data, 0, binary_continuous);
                    return_values = test_validation.percent_accuracy_cross_validation();
                    return_values.add(t_tests.t_test(.9018, return_values.get(0), .075304, return_values.get(2), 435));
                    return_values.add(learning_rate);
                    return_values.add(0.0);
                    return_values.add((double) j);
                    return_values.add((double) i);
                    return_values.add(binary);
                    System.out.print("Congressional_Voting_Records, ");
                    System.out.println(return_values);
//        System.out.println(test_validation.percent_accuracy_cross_validation());

//        System.out.println("-----------------------------------------MultiOutput--------------------------");

                    test_validation_multiple = new NeuralNetworkCrossValidationMouput();
                    test_validation_multiple.cross_validation(j, i, attributes, data, 0, binary_continuous);
                    return_values = test_validation_multiple.percent_accuracy_cross_validation();
                    return_values.add(t_tests.t_test(.9018, return_values.get(0), .075304, return_values.get(2), 435));
                    return_values.add(learning_rate);
                    return_values.add(1.0);
                    return_values.add((double) j);
                    return_values.add((double) i);
                    return_values.add(binary);
                    System.out.print("Congressional_Voting_Records, ");
                    System.out.println(return_values);
//        System.out.println(test_validation_multiple.percent_accuracy_cross_validation());
//
//        System.out.println("------------------------------------------------------------------------------");

                }
            binary_continuous = true;
            binary = 1.0;
        }

    }
}
