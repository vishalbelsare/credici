import ch.idsia.credici.inference.CredalCausalAproxLP;
import ch.idsia.credici.inference.CredalCausalVE;
import ch.idsia.credici.model.StructuralCausalModel;
import ch.idsia.crema.factor.credal.linear.IntervalFactor;
import ch.idsia.crema.factor.credal.vertex.VertexFactor;
import ch.idsia.crema.model.graphical.specialized.BayesianNetwork;
import ch.idsia.crema.model.io.uai.UAIParser;
import gnu.trove.map.TIntIntMap;
import gnu.trove.map.hash.TIntIntHashMap;

import java.io.IOException;

public class EquationlessFromFile {
    public static void main(String[] args) throws IOException, InterruptedException {

        // Load the empirical model
        String fileName = "./models/simple-bayes.uai";
        BayesianNetwork bnet = (BayesianNetwork) UAIParser.open(fileName);

        // Get the markovian equationless SCM
        StructuralCausalModel causalModel = StructuralCausalModel.of(bnet);

        // Set query
        TIntIntMap intervention = new TIntIntHashMap();
        intervention.put(0,1);
        int target = 1;

        // Approx inference
        CredalCausalAproxLP inf = new CredalCausalAproxLP(causalModel, bnet.getFactors());
        IntervalFactor res = inf.doQuery(target, intervention);
        System.out.println(res);

        //Exact inference
        CredalCausalVE inf2 = new CredalCausalVE(causalModel, bnet.getFactors());
        VertexFactor res2 = inf2.doQuery(target, intervention);
        System.out.println(res2);


    }
}
