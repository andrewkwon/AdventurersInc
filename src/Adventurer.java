import java.util.Arrays;
import java.util.Random;

public class Adventurer {

	String name;
	static final String[] JOB_CLASSES = {"Fighter", "Wizard", "Rogue", "Cleric", "Bard"};
	String jobClass;
	SkillSet skills;
	int level;
	int experience;
	static final int PAY_PER_LEVEL = 10;
	static final int INJURY_COST = 10;
	
	// -1 indicates no current quest
	int currQuestId = -1;
	
	// Marks an adventurer for injury costs or removal
	boolean injured = false;
	boolean dead = false;
	
	// Used for name generation
	static final String[] VOWELS = {"a", "e", "i", "o", "u", "ee", "oo", "ai", "ie"};
	static final String[] CONSONANTS_1 = {"", "b", "c", "d", "f", "g", "h", "j", "k", "l", "m", "n", "p", "q", "r", "s", "t", "v", "w", "z",
			"gh", "ch", "sh", "th"}; 
	static final String[] CONSONANTS_2 = {"", "b", "c", "d", "f", "g", "h", "j", "k", "l", "m", "n", "p", "r", "s", "t", "v", "z",
			"gh", "ch", "sh", "th"};
	
	// Randomly generate an adventurer
	public Adventurer(Random rng) {
		// Name is up to 4 syllables, each syllable is consonant-vowel-consonant
		name = "";
		for(int i = 0; i < rng.nextInt(4) + 1; i++) {
			name +=  CONSONANTS_1[rng.nextInt(CONSONANTS_1.length)];
			name +=  VOWELS[rng.nextInt(VOWELS.length)];
			name +=  CONSONANTS_2[rng.nextInt(CONSONANTS_2.length)];
		}
		name = name.substring(0, 1).toUpperCase() + name.substring(1);
		//  Random skills, 0 to 5 points per skill
		int[] stats = new int[SkillSet.NUM_TYPES];
		for(int i = 0; i < stats.length; i++)
			stats[i] = rng.nextInt(5 + 1);
		// Boost skills depending on job class
		jobClass = JOB_CLASSES[rng.nextInt(JOB_CLASSES.length)];
		switch(jobClass) {
		case "Fighter":
			stats[SkillSet.NAMES.get("Combat")] += 2;
			break;
		case "Wizard":
			stats[SkillSet.NAMES.get("Magic")] += 2;
			break;
		case "Rogue":
			stats[SkillSet.NAMES.get("Stealth")] += 2;
			break;
		case "Cleric":
			stats[SkillSet.NAMES.get("Healing")] += 2;
			break;
		case "Bard":
			stats[SkillSet.NAMES.get("Arts")] += 2;
			break;
		}
		skills = new SkillSet(stats);
		// Start with no experience
		level = 1;
		experience = 0;
	}
	
	public String description() {
		StringBuilder rep = new StringBuilder();
		rep.append(summary());
		rep.append("Pay = Level x " + PAY_PER_LEVEL + " = " + level * PAY_PER_LEVEL + Game.CURRENCY + "\n");
		rep.append("---------Skills--------\n");
		rep.append(skills.description());
		return rep.toString();
	}
	
	public String summary() {
		StringBuilder rep = new StringBuilder();
		rep.append(name + " | ");
		rep.append(jobClass + "\n");
		rep.append("Level: " + level + ", ");
		rep.append("Experience: " + experience + "\n");
		rep.append("Assigned: " + currQuestId + "\n");
		return rep.toString();
	}
	
	public void experienceUp(int exp, Random rng) {
		// Random skill gain
		int[] stats = new int[SkillSet.NUM_TYPES];
		Arrays.fill(stats, 0);
		stats[rng.nextInt(SkillSet.NUM_TYPES)] = 1;
		// Guaranteed class skill gain
		jobClass = JOB_CLASSES[rng.nextInt(JOB_CLASSES.length)];
		switch(jobClass) {
		case "Fighter":
			stats[SkillSet.NAMES.get("Combat")]++;
			break;
		case "Wizard":
			stats[SkillSet.NAMES.get("Magic")]++;
			break;
		case "Rogue":
			stats[SkillSet.NAMES.get("Stealth")]++;
			break;
		case "Cleric":
			stats[SkillSet.NAMES.get("Healing")]++;
			break;
		case "Bard":
			stats[SkillSet.NAMES.get("Arts")]++;
			break;
		}
		skills.add(new SkillSet(stats));
		// Level up every 10 experience
		experience += exp;
		level = ((int) (experience / 10)) + 1;
	}
	
	// Increase the adventurer's skills by the given amounts
	public void learnSkills(SkillSet learned) {
		skills.add(learned);
	}
	
	// Get the adventurer's pay
	public int pay() {
		return level * PAY_PER_LEVEL;
	}
	
	@Override
	public String toString() {
		return name;
	}
}
