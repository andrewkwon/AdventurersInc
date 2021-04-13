import java.util.HashMap;
import java.util.List;
import java.util.Map;

// A vector of skills
public class SkillSet {

	static final Map<String, Integer> NAMES = new HashMap<>();
	static {
		int i = 0;
		NAMES.put("Combat", i++);
		NAMES.put("Magic", i++);
		NAMES.put("Science", i++);
		NAMES.put("Survival", i++);
		NAMES.put("Communication", i++);
		NAMES.put("Stealth", i++);
		NAMES.put("Research", i++);
		NAMES.put("Arts", i++);
		NAMES.put("Strategy", i++);
		NAMES.put("Improvisation", i++);
		NAMES.put("Healing", i++);
		NAMES.put("Perseverance", i++);
	};
	static final int NUM_TYPES = NAMES.size();
	int[] skills;
	
	public SkillSet(int[] skills) {
		this.skills = skills;
		if(skills.length != NUM_TYPES)
			throw new IllegalArgumentException("Tried to construct SkillSet with invalid number of skills");
	}
	
	// Computes how well the skills match
	public double match(SkillSet matcher) {
		// Orthogonal projection of this skillset onto the matcher
		// Divided by the norm of the matcher
		int dot = 0;
		for(int i = 0; i < NUM_TYPES; i++) {
			dot += skills[i] * matcher.skills[i];
		}
		return ((double) dot) / matcher.normSquare();
	}
	
	// Combines all the given skill sets into one
	public static SkillSet add(List<SkillSet> sets) {
		int[] result = new int[NUM_TYPES];
		for(int i = 0; i < NUM_TYPES; i++) {
			result[i] = 0;
			for(SkillSet s : sets) {
				result[i] += s.skills[i];
			}
		}
		return new SkillSet(result);
	}
	
	// Adds a skillset to this skillset
	public void add(SkillSet b) {
		for(int i = 0; i < NUM_TYPES; i++) {
			skills[i] += b.skills[i];
		}
	}
	
	public String description() {
		StringBuilder rep = new StringBuilder();
		for(Map.Entry entry : NAMES.entrySet()) {
			rep.append(String.format("%20s %d%n", entry.getKey(), skills[(int) entry.getValue()]));
		}
		return rep.toString();
	}
	
	// Computes the vector norm squared of the skills
	public double normSquare() {
		double normSquare = 0;
		for(int s : skills)
			normSquare += s * s;
		return normSquare;
	}
}
