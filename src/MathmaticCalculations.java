import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Vector;

/**
 * Created by orion2166 on 2/14/2016.
 */
public class MathmaticCalculations {
    public MathmaticCalculations(){}
    Double Enthropy(int [] plus_minus_nodes){
        double posivite = -plus_minus_nodes[0]*Math.log(plus_minus_nodes[0]);
        double negative = -plus_minus_nodes[0]*Math.log(plus_minus_nodes[0]);
        return negative + posivite;
    }

    Double gain(int []S, HashMap map){
        Vector<int []> overallnodevalues = new Vector<>();
        Vector<Double> sum_of_enthropies = new Vector<>();
        Iterator<HashMap.Entry<String,int[]>> iterator = map.entrySet().iterator();
        while(iterator.hasNext()){
            overallnodevalues.add(iterator.next().getValue());
        }
        int maxtemp = S[0] + S[1];
        double max_enthropy_node = Enthropy(S);
        for(int i = 0;i<overallnodevalues.size();i++){
            int nodetemp = (overallnodevalues.get(i)[1] + overallnodevalues.get(i)[0])/maxtemp;
            double temp = nodetemp * Enthropy(overallnodevalues.get(i));
            sum_of_enthropies.add(temp);
        }
        double gain = 0;
        for(int i = 0;i<sum_of_enthropies.size();i++){
            gain += sum_of_enthropies.get(i);
        }
        gain = max_enthropy_node - gain;
        return gain;
    }

}
