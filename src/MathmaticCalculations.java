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
        double result = 0;
        Double combination = Double.valueOf(plus_minus_nodes[0] + plus_minus_nodes[1]);
        result -= (plus_minus_nodes[0]/combination) * (Math.log(plus_minus_nodes[0]/combination) / Math.log(2));
        result -= (plus_minus_nodes[1]/combination) * (Math.log(plus_minus_nodes[1]/combination) / Math.log(2));
        return result;
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
            Double combination = Double.valueOf((overallnodevalues.get(i)[1] + overallnodevalues.get(i)[0]));
            Double nodetemp = combination / maxtemp;
            double temp = nodetemp * Enthropy(overallnodevalues.get(i));
            if(Double.isNaN(temp))
                temp = 0;
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
