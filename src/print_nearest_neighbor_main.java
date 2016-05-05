import java.util.Vector;

/**
 * Created by orion_000 on 4/12/2016.
 */
public class print_nearest_neighbor_main {
    public static void main(String[] args) {
        NeuralNetworkMathmatics t_tests = new NeuralNetworkMathmatics();
        // THORAC_SURGERY_TESTS -------------------------------------------------------------------------------------------
        Vector<Double> neuralreturnvalues = new Vector<>();
//        neuralreturnvalues.add(t_tests.t_test(.76849, 0.872427766, .023826, 0.057122714, 432));
//        neuralreturnvalues.add(t_tests.t_test(0.788131313, 0.872427766, 0.066211216, 0.057122714, 432));
//        neuralreturnvalues.add(t_tests.t_test(.73332, 0.866156189, .019633, 0.063072047, 958));
//        neuralreturnvalues.add(t_tests.t_test(0.703371101, 0.866156189, 0.045381809, 0.063072047, 958));
//        neuralreturnvalues.add(t_tests.t_test(.73332, 0.882269504, .019633, 0.061615922, 958));
//        neuralreturnvalues.add(t_tests.t_test(0.893617021, 0.882269504, 0.039451893, 0.061615922, 958));
//        neuralreturnvalues.add(t_tests.t_test(.73332, 0.913157654, .019633, 0.039723237, 958));
//        neuralreturnvalues.add(t_tests.t_test(0.92343935, 0.913157654, 0.033258355, 0.039723237, 958));
//        neuralreturnvalues.add(t_tests.t_test(.73332, 0.955209067, .019633, 0.0335495, 958));
//        neuralreturnvalues.add(t_tests.t_test(0.96840498, 0.955209067, 0.025301192, 0.0335495, 958));

//        System.out.println(neuralreturnvalues);



        for(int k = 10; k <= 100;k+=10) {
            t_tests = new NeuralNetworkMathmatics();
            String attributes = "C:\\Users\\orion_000\\Documents\\GitHub\\MachineLearning\\database_sets\\Monks_Problems_Attributes";
            String data = "C:\\Users\\orion_000\\Documents\\GitHub\\MachineLearning\\database_sets\\Monks_Problems_Data";
            NearestNeighborCrossValidation test_validation = new NearestNeighborCrossValidation();
            test_validation.cross_validation(attributes, data, 0, false,"0",k);
            Vector<Double> return_values = test_validation.percent_accuracy_cross_validation();
            return_values.add(t_tests.t_test(.76849, return_values.get(0), .023826, return_values.get(2), 432));
            return_values.add(t_tests.t_test(0.788131313, return_values.get(0), 0.066211216, return_values.get(2), 432));
            return_values.add((double) k);
            System.out.print("Monks_Problems, ");
            System.out.println(return_values);


//        System.out.println("------------------------------------------------------------------------------");

            attributes = "C:\\Users\\orion_000\\Documents\\GitHub\\MachineLearning\\database_sets\\Tic_Tac_Toe_Attributes";
            data = "C:\\Users\\orion_000\\Documents\\GitHub\\MachineLearning\\database_sets\\Tic_Tac_Toe_Data";
            test_validation = new NearestNeighborCrossValidation();
            test_validation.cross_validation(attributes, data, 9, false,"positive",k);
            return_values = test_validation.percent_accuracy_cross_validation();
            return_values.add(t_tests.t_test(.73332, return_values.get(0), .019633, return_values.get(2), 958));
            return_values.add(t_tests.t_test(0.703371101, return_values.get(0), 0.045381809, return_values.get(2), 958));
            return_values.add((double)k);
            System.out.print("Tic_Tac_Toe, ");
            System.out.println(return_values);

//        System.out.println("------------------------------------------------------------------------------");

            attributes = "C:\\Users\\orion_000\\Documents\\GitHub\\MachineLearning\\database_sets\\Thoracic_Surgery_Attribute";
            data = "C:\\Users\\orion_000\\Documents\\GitHub\\MachineLearning\\database_sets\\Thoracic_Surgery_Data";
            test_validation = new NearestNeighborCrossValidation();
            test_validation.cross_validation(attributes, data, 16, false, "T", k);
            return_values = test_validation.percent_accuracy_cross_validation();
            return_values.add(t_tests.t_test(.73332, return_values.get(0), .019633, return_values.get(2), 958));
            return_values.add(t_tests.t_test(0.893617021, return_values.get(0), 0.039451893, return_values.get(2), 958));
            return_values.add((double)k);
            System.out.print("Thoracic_Surgery, ");
            System.out.println(return_values);

//        System.out.println("------------------------------------------------------------------------------");

            attributes = "C:\\Users\\orion_000\\Documents\\GitHub\\MachineLearning\\database_sets\\Chess_King-Rook-Vs-King-Pawn_Attribute";
            data = "C:\\Users\\orion_000\\Documents\\GitHub\\MachineLearning\\database_sets\\Chess_King-Rook-Vs-King-Pawn_Data";
            test_validation = new NearestNeighborCrossValidation();
            test_validation.cross_validation(attributes, data, 36, false, "won", k);
            return_values = test_validation.percent_accuracy_cross_validation();
            return_values.add(t_tests.t_test(.73332, return_values.get(0), .019633, return_values.get(2), 958));
            return_values.add(t_tests.t_test(0.92343935, return_values.get(0), 0.033258355, return_values.get(2), 958));
            return_values.add((double)k);
            System.out.print("Chess_King-Rook-Vs-King-Pawn, ");
            System.out.println(return_values);

//        System.out.println("------------------------------------------------------------------------------");

            attributes = "C:\\Users\\orion_000\\Documents\\GitHub\\MachineLearning\\database_sets\\Congressional_Voting_Records_Attributes_neural";
            data = "C:\\Users\\orion_000\\Documents\\GitHub\\MachineLearning\\database_sets\\Congressional_Voting_Records_Data";
            test_validation = new NearestNeighborCrossValidation();
            test_validation.cross_validation(attributes, data, 0, false, "democrat", k);
            return_values = test_validation.percent_accuracy_cross_validation();
            return_values.add(t_tests.t_test(.73332, return_values.get(0), .019633, return_values.get(2), 958));
            return_values.add(t_tests.t_test(0.96840498, return_values.get(0), 0.025301192, return_values.get(2), 958));
            return_values.add((double)k);
            System.out.print("Congressional_Voting_Records, ");
            System.out.println(return_values);

//        System.out.println("------------------------------------------------------------------------------");
        }

    }

}
