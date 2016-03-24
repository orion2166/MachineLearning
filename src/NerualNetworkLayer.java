import java.util.Random;
import java.util.Vector;

/**
 * Created by orion_000 on 3/21/2016.
 */
public class NerualNetworkLayer {
    Vector<NeuralNetworkNodes> layernodes = new Vector<>();
    boolean initial_input;
    boolean output;
    Vector<Double> node_out_values = new Vector<>();
    double[] Eout;
    double[] out_net;
    double[] Enet;
    double bias_weight;
    public NerualNetworkLayer(int num_nodes_inputs, boolean inputnode, boolean b, int nodenumbers){
        initial_input = inputnode;
        output = b;
        for(int i = 0;i<nodenumbers;i++)
        {
            if(inputnode){
                layernodes.add(new NeuralNetworkNodes(0));
            }
            else{
                layernodes.add(new NeuralNetworkNodes(num_nodes_inputs));
            }
        }
        Random ra = new Random();
        ra.doubles(1);
        bias_weight = ra.nextDouble()*.1-.05;
    }
    Vector<Double> get_outputvalues(){
        return node_out_values;
    }

    void insert_output_values(Vector<Double> inputvalue) {
        node_out_values = inputvalue;
//        for(int i = 0;i < layernodes.size();i++){
//            layernodes.get(i).insert_out_value(inputvalue.get(i));
//            node_out_values.add(layernodes.get(i).get_output_value());
//        }
    }

    void input_nodes(NerualNetworkLayer previous_layer){
        Vector<Double> temps = new Vector<>();
        for(int i = 0;i<layernodes.size();i++){
            double temp_out = layernodes.get(i).overall_net_value(previous_layer.get_outputvalues(),bias_weight,output);
            temps.add(temp_out);
        }
        node_out_values = temps;
    }
    int get_node_size() {
        return layernodes.size();
    }

    void build_Eout(NerualNetworkLayer nextlayer){
        double[] temp = new double[node_out_values.size()];
        double total = 0.0;
        for(int i = 0;i < temp.length;i++){
            for(int j = 0;j < nextlayer.get_node_size();j++)
            {
                total += nextlayer.getweight(j,i) * nextlayer.get_Enet(j);
            }
            temp[i] = total;
        }
        Eout = temp;
    }

    private double get_Enet(int netoutindex) {
        return Enet[netoutindex];
    }

    private double getweight(int node_index, int weight_index) {
        return layernodes.get(node_index).weights.get(weight_index);
    }

    void insert_Eout(double[] error) {
        Eout = error;
    }
    void build_out_net(){
        double[] temp = new double[node_out_values.size()];
        for(int i = 0;i<temp.length;i++){
            temp[i] = node_out_values.get(i)*(1-node_out_values.get(i));
        }
        out_net = temp;
    }
    void build_Enet(){
        double[] temp = new double[node_out_values.size()];
        for(int i = 0;i<Eout.length;i++){
            temp[i] = out_net[i] * Eout[i];
        }
        Enet = temp;
    }
    void change_weights(NerualNetworkLayer previous_layer){
        double biases_change = 0.0;
        for(int i = 0;i < layernodes.size();i++){
            layernodes.get(i).changeweights(previous_layer.get_outputvalues(),out_net[i],Eout[i]);
            biases_change += (Eout[i]*out_net[i]);
        }
//        switchweights();
        bias_weight = bias_weight - (.1*biases_change);
    }

    double[] get_Onet() {
        return out_net;
    }

    double[] get_Eout() {
        return Eout;
    }

    public void switchweights() {
        for(int i = 0;i < layernodes.size();i++){
            layernodes.get(i).weightswitch();
        }
    }
}
