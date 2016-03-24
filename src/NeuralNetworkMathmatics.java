import java.util.Vector;

/**
 * Created by orion_000 on 3/21/2016.
 */
public class NeuralNetworkMathmatics {
    public NeuralNetworkMathmatics(){}

    public double overall_net(Vector<Double> outputvalues, Vector<Double> weights) {
        double net = 0.0;
        for(int i = 0;i <outputvalues.size();i++){
            net += (outputvalues.get(i) * weights.get(i));
        }
        return net;
    }
    double sigmoid(double x) {
        return (1/( 1 + Math.pow(Math.E,(-1*x))));
    }
//    Double totalNetInput(Vector<Double> weights,Vector<Double> inputs){    }
    double t_test(double mean1,double mean2, double std1,double std2,int size){
        double mean_average = mean1-mean2;
        double stdaverage = (Math.pow(std1,2)/size)+ (Math.pow(std2, 2)/ size);
        return Math.abs(mean_average)/Math.sqrt(stdaverage);
    }
}
