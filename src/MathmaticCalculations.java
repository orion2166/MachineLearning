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
        double combination = 0;
        double[] value_set = new double[]{0.0,0.0};
        for(int k = 0;k<plus_minus_nodes.length;k++){
            if(k == 0)
                value_set[0] += Double.valueOf(plus_minus_nodes[k]);
            combination += Double.valueOf(plus_minus_nodes[k]);
            value_set[1] += Double.valueOf(plus_minus_nodes[k]);
        }
        for(int k = 0;k<value_set.length;k++)
            result -= (plus_minus_nodes[k]/combination) * (Math.log(plus_minus_nodes[k]/combination) / Math.log(2));
        return result;
    }

    Double gain(int []S, HashMap map){
        Vector<int []> overallnodevalues = new Vector<>();
        Vector<Double> sum_of_enthropies = new Vector<>();
        Iterator<HashMap.Entry<String,int[]>> iterator = map.entrySet().iterator();
        while(iterator.hasNext()){
            overallnodevalues.add(iterator.next().getValue());
        }
        int maxtemp = 0;
        for(int k = 0;k<S.length;k++)
            maxtemp += S[k];
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

    Double normal_aproximation(int[] testdatas){
        double z = 1.96;
        double min = 1000000000;
        double size = 0;
        for(int i = 0;i<testdatas.length;i++)
        {
            if(testdatas[i] < min)
            {
                min = testdatas[i];
            }
            size += testdatas[i];
        }
        double p = (min+1)/(size+2);
        double standard_mean = Math.sqrt((p*(1-p))/(size+2));
        return size*(p + (z*standard_mean));

    }

    Vector confidence_interval(double wrong,int size){
        double z = 1.96;
        Vector<Double> confidence = new Vector<>();
        double p = wrong;
        double standard_mean = Math.sqrt((p*(1-p))/(size+2));
        confidence.add(p + (z*standard_mean));
        confidence.add(p - (z*standard_mean));
        return confidence;
    }
}
