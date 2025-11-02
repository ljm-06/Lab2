package Martin;

import java.util.*;

public class MultipleStates {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        Map<String, Map<Character, Set<String>>> nfa = new HashMap<>();

        nfa.put("q0", new HashMap<>());
        nfa.get("q0").put('a', new HashSet<>(Arrays.asList("q0", "q1")));
        nfa.get("q0").put('b', new HashSet<>(Arrays.asList("q0")));

        nfa.put("q1", new HashMap<>());
        nfa.get("q1").put('b', new HashSet<>(Arrays.asList("q2")));

        nfa.put("q2", new HashMap<>());
        nfa.get("q2").put('a', new HashSet<>(Arrays.asList("q2")));
        nfa.get("q2").put('b', new HashSet<>(Arrays.asList("q2")));

        String startState = "q0";
        Set<String> acceptStates = new HashSet<>(Arrays.asList("q2"));

        while (true) {
            System.out.print("Enter a string: ");
            String input = scanner.nextLine();

            if (input.equalsIgnoreCase("exit")) {
                break;
            }

            if (!input.matches("[ab]+")) {
                System.out.println("Invalid input! Please enter only 'a' and 'b'.\n");
                continue;
            }

            boolean accepted = simulateNFA(nfa, startState, acceptStates, input);
            System.out.println(accepted ? "Accepted\n" : "Rejected\n");
        }
    }

    private static boolean simulateNFA(
            Map<String, Map<Character, Set<String>>> nfa,
            String startState,
            Set<String> acceptStates,
            String input) {

        Set<String> currentStates = new HashSet<>();
        currentStates.add(startState);

        for (char symbol : input.toCharArray()) {
            Set<String> nextStates = new HashSet<>();

            for (String state : currentStates) {
                if (nfa.containsKey(state) && nfa.get(state).containsKey(symbol)) {
                    nextStates.addAll(nfa.get(state).get(symbol));
                }
            }

            currentStates = nextStates;
        }

        for (String state : currentStates) {
            if (acceptStates.contains(state)) {
                return true;
            }
        }

        return false;
    }
}
