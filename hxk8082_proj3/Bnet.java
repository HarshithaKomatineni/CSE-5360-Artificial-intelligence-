import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Bnet {
    private final Map<String, Double> burglaryTT = new HashMap<String, Double>();
    private final Map<String, Double> earthquakeTT = new HashMap<String, Double>();
    private final Map<String, Double> alarmTT = new HashMap<String, Double>();
    private final Map<String, Double> johnCallTT = new HashMap<String, Double>();
    private final Map<String, Double> maryCallTT = new HashMap<String, Double>();

    private final String[] args;

    public Bnet(String[] args){
        this.args = args;
    }

    private void initializeValues() {
        burglaryTT.put("B_T", 0.001);
        earthquakeTT.put("E_T", .002);

        alarmTT.put("A_T|B_T,E_T", 0.95);
        alarmTT.put("A_T|B_T,E_F", 0.94);
        alarmTT.put("A_T|B_F,E_T", 0.29);
        alarmTT.put("A_T|B_F,E_F", 0.001);

        johnCallTT.put("J_T|A_T", 0.90);
        johnCallTT.put("J_T|A_F", 0.05);

        maryCallTT.put("M_T|A_T", 0.70);
        maryCallTT.put("M_T|A_F", 0.01);
    }

    public void calculateBnet(){
        //initialize Bayesian network probability values
        initializeValues();
        int counter = 0;
        //Storing the values received for command line -as per the Bayes's rule
        Map<Character, ArrayList<Character>> givenValues = new HashMap<Character, ArrayList<Character>>();
        Map<Character, ArrayList<Character>> conditions = new HashMap<Character, ArrayList<Character>>();
        for (String inline : args) {
            ArrayList<Character> possibleBool = new ArrayList<Character>();
            if (inline.equals("given")) {
                counter = 1;
                continue;
            }
            String temp = inline.toUpperCase();
            possibleBool.add(temp.charAt(1));
            if (counter == 0) {
                conditions.put(temp.charAt(0), possibleBool);
            } else {
                givenValues.put(temp.charAt(0), possibleBool);
            }
        }
        if (conditions.keySet().size() < 1 || conditions.keySet().size() > 5) {
            System.out.println("Invalid condition arguments.");
            System.exit(0);
        }
        if (counter == 1 && givenValues.keySet().size() < 1 || givenValues.keySet().size() > 4) {
            System.out.println("Invalid given statement");
            System.exit(0);
        }
        conditions.putAll(givenValues);
        conditions.putAll(initializeConditionGivenValues(conditions));
        givenValues.putAll(initializeConditionGivenValues(givenValues));
        double calcProb1 = 0.0, denomVal = 0.0;
        for (int b = 0; b < conditions.get('B').size(); b++) {
            for (int e = 0; e < conditions.get('E').size(); e++) {
                for (int a = 0; a < conditions.get('A').size(); a++) {
                    for (int j = 0; j < conditions.get('J').size(); j++) {
                        for (int m = 0; m < conditions.get('M').size(); m++) {
                            calcProb1 += computeProbability(conditions.get('B').get(b), conditions.get('E').get(e), conditions.get('A').get(a), conditions.get('J').get(j), conditions.get('M').get(m)); /**Calling the different possible combination of possible values of the variables B,E,A,J,M*/
                        }
                    }
                }
            }
        }
        if (counter == 1) {
            for (int i = 0; i < givenValues.get('B').size(); i++) {
                for (int j = 0; j < givenValues.get('E').size(); j++) {
                    for (int k = 0; k < givenValues.get('A').size(); k++) {
                        for (int l = 0; l < givenValues.get('J').size(); l++) {
                            for (int m = 0; m < givenValues.get('M').size(); m++) {
                                denomVal += computeProbability(givenValues.get('B').get(i), givenValues.get('E').get(j), givenValues.get('A').get(k), givenValues.get('J').get(l), givenValues.get('M').get(m));/**Calling the different possible combination of possible values of the variables B,E,A,J,M*/
                            }
                        }
                    }
                }
            }
            calcProb1 = calcProb1 / denomVal;
        }
        System.out.printf("Probability = %.7f\n", calcProb1);
    }

    private double computeProbability(char burglary, char earthquake, char alarm, char johnCall, char maryCall) {
        double burglaryVal = burglaryTT.get("B_T");
        double earthquakeVal = earthquakeTT.get("E_T");
        double alarmVal = alarmTT.get("A_T|B_" + burglary + ",E_" + earthquake);
        double johnCallVal = johnCallTT.get("J_T|A_" + alarm);
        double maryCallVal = maryCallTT.get("M_T|A_" + alarm);
        if (burglary == 'F') burglaryVal = 1.00 - burglaryVal;
        if (earthquake == 'F') earthquakeVal = 1.00 - earthquakeVal;
        if (alarm == 'F') alarmVal = 1.00 - alarmVal;
        if (johnCall == 'F') johnCallVal = 1.00 - johnCallVal;
        if (maryCall == 'F') maryCallVal = 1.00 - maryCallVal;
        return burglaryVal * earthquakeVal * alarmVal * johnCallVal * maryCallVal;
    }

    private Map<Character, ArrayList<Character>> initializeConditionGivenValues(Map<Character, ArrayList<Character>> passedMap) {
        Map<Character, ArrayList<Character>> possibleVal = new HashMap<Character, ArrayList<Character>>();
        ArrayList<Character> possibleBool;
        if (!(passedMap.containsKey('B'))) {
            possibleBool = new ArrayList<Character>();
            possibleBool.add('T');
            possibleBool.add('F');
            possibleVal.put('B', possibleBool);
        }
        if (!(passedMap.containsKey('E'))) {
            possibleBool = new ArrayList<Character>();
            possibleBool.add('T');
            possibleBool.add('F');
            possibleVal.put('E', possibleBool);
        }
        if (!(passedMap.containsKey('A'))) {
            possibleBool = new ArrayList<Character>();
            possibleBool.add('T');
            possibleBool.add('F');
            possibleVal.put('A', possibleBool);
        }
        if (!(passedMap.containsKey('J'))) {
            possibleBool = new ArrayList<Character>();
            possibleBool.add('T');
            possibleBool.add('F');
            possibleVal.put('J', possibleBool);
        }
        if (!(passedMap.containsKey('M'))) {
            possibleBool = new ArrayList<Character>();
            possibleBool.add('T');
            possibleBool.add('F');
            possibleVal.put('M', possibleBool);
        }
        return possibleVal;
    }
}
