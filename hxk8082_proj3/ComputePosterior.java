import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.Arrays;
import java.util.List;

public class ComputePosterior {
    /**
     * Initializing a list for all the provided values of probabilities
     * h1 (prior: 10%): This type of bag contains 100% cherry candies.
     * h2 (prior: 20%): This type of bag contains 75% cherry candies and 25% lime candies.
     * h3 (prior: 40%): This type of bag contains 50% cherry candies and 50% lime candies.
     * h4 (prior: 20%): This type of bag contains 25% cherry candies and 75% lime candies.
     * h5 (prior: 10%): This type of bag contains 100% lime candies.
     */

    private String commands;
    private String result;
    private int q_len;
    private final int bag_num = 5;

    public ComputePosterior(String commands){
        this.commands = commands.trim();
    }

    public void calculate() {
        q_len = commands.length();
        result = "Observation sequence Q: " + commands;
        result += System.lineSeparator() + "Length of Q: " + q_len;

        double temp_cherry_prob_sum = 0.0, temp_lime_prob_sum = 0.0;

        //initialize probability values
        List<Double> probOfBag =  Arrays.asList(0.1, 0.2, 0.4, 0.2, 0.1);
        List<Double> cherryProbPerBag = Arrays.asList(1.0, 0.75, 0.50, 0.25, 0.0);
        List<Double> limeProbPerBag = Arrays.asList(0.0, 0.25, 0.50, 0.75, 1.0);

        for (int j = 0; j < bag_num; j++) {
            temp_cherry_prob_sum += (probOfBag.get(j)) * cherryProbPerBag.get(j);
            temp_lime_prob_sum += (probOfBag.get(j)) * limeProbPerBag.get(j);
        }

        for (int i = 0; i < q_len; i++) {
            result += System.lineSeparator() + System.lineSeparator() + "After Observation " + i + " = " + commands.charAt(i);
            if (commands.charAt(i) == 'C') {
                for (int j = 0; j < bag_num; j++) {
                    //h(j)|Q when Q=C
                    Double temp = ((probOfBag.get(j)) * cherryProbPerBag.get(j)) / temp_cherry_prob_sum;
                    probOfBag.set(j, temp);
                    result += System.lineSeparator() + "P(h" + (j + 1) + " | Q) = " + String.format("%.10f", temp);
                }
            } else {
                for (int j = 0; j < bag_num; j++) {
                    //h(j)|Q when Q=L
                    Double temp = ((probOfBag.get(j)) * limeProbPerBag.get(j)) / temp_lime_prob_sum;
                    probOfBag.set(j, temp);
                    result += System.lineSeparator() + "P(h" + (j + 1) + " | Q) = " + String.format("%.10f", temp);
                }
            }
            temp_cherry_prob_sum = 0.0;
            temp_lime_prob_sum = 0.0;
            //Pt(Qi+1=C or L)
            for (int j = 0; j < bag_num; j++) {
                temp_cherry_prob_sum += (probOfBag.get(j)) * cherryProbPerBag.get(j);
                temp_lime_prob_sum += (probOfBag.get(j)) * limeProbPerBag.get(j);
            }

            result += System.lineSeparator() + "Probability that the next candy we pick will be C, given Q: " + String.format("%.5f", temp_cherry_prob_sum);
            result += System.lineSeparator() + "Probability that the next candy we pick will be L, given Q: " + String.format("%.5f", temp_lime_prob_sum);
        }
        saveResultToFile(result);
    }

    private void saveResultToFile(String result_str) {
        System.out.println(result_str);
        try {
            FileWriter out_stream = new FileWriter("result.txt");
            BufferedWriter out = new BufferedWriter(out_stream);
            out.write(result_str);
            out.close();
            out_stream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
