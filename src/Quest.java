import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Quest {

	String name;
	int reward;
	SkillSet skills;
	ProbabilisticBool success;
	double injuryRate;
	double deathRate;
	
	// Determines what quest replaces this on success or failure
	int[] successIndeces;
	int[] failIndeces;
	
	public Quest(String name, int reward, SkillSet skills, double injuryRate, double deathRate, int[] successIndeces, int[] failIndeces) {
		this.name = name;
		this.reward = reward;
		this.skills = skills;
		this.injuryRate = injuryRate;
		this.deathRate = deathRate;
		this.successIndeces = successIndeces;
		this.failIndeces = failIndeces;
	}
	
	// Called when a week passes
	// Returns true if the quest succeeded
	public QuestResult update(List<Adventurer> assigned) {
		boolean defaulted;	
		boolean succeeded;
		int nextQuest;
		List<Adventurer> deaths = new ArrayList<>();;
		// If no adventurers assigned, fails by default
		if(assigned == null || assigned.isEmpty()) {
			defaulted = true;
			succeeded = false;
		} 
		// If adventurers were assigned, compute success or failure
		else {
			defaulted = false;
			SkillSet adventurerSkills = SkillSet.add(
					assigned.stream().map(a -> a.skills).collect(Collectors.toList()));
			success = new ProbabilisticBool(adventurerSkills.match(skills));
			succeeded = success.eval();
			
			for(Adventurer assign : assigned) {
				// Give adventurers experience
				assign.experienceUp(1, ProbabilisticBool.rng);
				// Determine injuries
				assign.injured = new ProbabilisticBool(injuryRate).eval();
				// If failed, determine deaths
				if(!succeeded) {
					assign.dead = new ProbabilisticBool(deathRate).eval();
					if(assign.dead)
						deaths.add(assign);
				}
			}
		}
		// Determine what quest is next
		if(succeeded)
			nextQuest = successIndeces[ProbabilisticBool.rng.nextInt(successIndeces.length)];
		else 
			nextQuest = failIndeces[ProbabilisticBool.rng.nextInt(failIndeces.length)];
		
		return new QuestResult(defaulted, succeeded, nextQuest, deaths);
	}
	
	public String summary() {
		StringBuilder rep = new StringBuilder();
		rep.append(name + "\n");
		rep.append("Reward: " + reward + Game.CURRENCY + ", ");
		rep.append(String.format("Injury Rate: %%%d, ", (int) (injuryRate * 100)));
		rep.append(String.format("Death Rate: %%%d%n", (int) (deathRate * 100)));
		return rep.toString();
	}
	
	@Override
	public String toString() {
		return name;
	}
	
	// Returned by update
	class QuestResult {
		boolean defaulted;
		boolean succeeded;
		int nextQuestId;
		List<Adventurer> deaths;
		
		public QuestResult(boolean defaulted, boolean succeeded, int nextQuestId, List<Adventurer> deaths) {
			this.defaulted = defaulted;
			this.succeeded = succeeded;
			this.nextQuestId = nextQuestId;
			this.deaths = deaths;
		}
	}
}
