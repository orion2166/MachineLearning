import java.util.Random;
import java.util.Vector;

/**
 * Created by orion_000 on 3/21/2016.
 */
public class NeuralNetworkNodes {
    Vector<Double> weights = new Vector<>();
    Vector<Double> backward_pass_weights = new Vector<>();
    private double outvalue;
    private double netvalue;
    public NeuralNetworkNodes(int number_inputs){
        Random ra = new Random();
        ra.doubles(1);
        for(int i = 0;i<number_inputs;i++)
            weights.add(ra.nextDouble()*.1-.05);
    }
    void insert_out_value(double value){
        outvalue = value;
    }
    double get_output_value(){
        return outvalue;
    }

    public double overall_net_value(Vector<Double> outputvalues,double bias_weight,boolean last) {
        NeuralNetworkMathmatics math_neural = new NeuralNetworkMathmatics();
        netvalue = math_neural.overall_net(outputvalues,weights);
        netvalue += bias_weight;
//        if(last)
//            return netvalue;
        outvalue = math_neural.sigmoid(netvalue);
        return outvalue;
    }

    public void changeweights(Vector<Double> outputvalues, double eout, double onet) {
        Vector<Double> temp = new Vector<>();
        for(int i = 0; i < weights.size();i++){
            double temp_weight = weights.get(i) - (.01*(outputvalues.get(i)*eout*onet));
            temp.add(temp_weight);
        }
        backward_pass_weights = temp;
    }
    void weightswitch(){
        weights = backward_pass_weights;
    }
}
