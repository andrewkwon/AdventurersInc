import java.util.Random;

// Encapsulates a lot of the non-deterministic functionality
// Represents a boolean which may evaluate to true or false with a given probability
public class ProbabilisticBool {
	
	// Probability of true given as a double in the range 0 to 1
	double pTrue;
	
	static Random rng = new Random();
	
	// Construct a ProbabilisticBool of the given probability for true
	public ProbabilisticBool(double pTrue) {
		this.pTrue = pTrue;
	}
	
	// Evaluates the boolean to true or false probabilistically
	public boolean eval() {
		return rng.nextDouble() < pTrue;
	}
}
