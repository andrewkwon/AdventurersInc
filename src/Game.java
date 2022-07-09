import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Game {
	
	// Game state
	boolean running = true;
	int week = 0;
	static final String CURRENCY = " G"; 
	long money = 1000;
	
	Map<Integer, Adventurer> idToEmployees;
	int totalHired = 0;
	Map<Integer, Adventurer> idToPotentialHires;
	Quest[] currQuests;
	static final int NUM_AVAILABLE_QUESTS = 5;
	Map<Integer, List<Adventurer>> questIdToAssignments;
	
	// All possible quests
	static final Quest[] POSSIBLE_QUESTS = {
			// Quest(name, reward, skills, injuryRate, deathRate, successIndeces, failIndeces)
			// SkillSet([Combat, Magic, Science, Survival, Communication, Stealth, Research, Arts, Strategy, Improv, Healing, Perseverance])
			/*00*/ new Quest("Pizza delivery", 15, 
					new SkillSet(new int[] {0, 1, 1, 0, 1, 0, 1, 0, 1, 0, 0, 0}), 0.00, 0.00,
					new int[] {0, 2, 3}, new int[] {0, 1, 13}),
			/*01*/ new Quest("Retrieve stolen pizza from sewers", 60, 
					new SkillSet(new int[] {20, 1, 4, 5, 1, 10, 5, 0, 5, 5, 5, 0}), 0.90, 0.00,
					new int[] {2, 5, 9}, new int[] {1, 0, 15}),
			/*02*/ new Quest("Making pizzas", 20, 
					new SkillSet(new int[] {0, 1, 5, 0, 3, 0, 1, 5, 1, 1, 0, 0}), 0.01, 0.00,
					new int[] {2, 3, 8}, new int[] {0, 8, 10}),
			/*03*/ new Quest("Investigate strange illnesses", 100, 
					new SkillSet(new int[] {5, 5, 5, 1, 0, 5, 10, 1, 5, 5, 0, 10}), 0.50, 0.00,
					new int[] {4, 9, 18}, new int[] {5, 8, 10}),
			/*04*/ new Quest("Confront corrupt lord about embezzlement", 60, 
					new SkillSet(new int[] {10, 1, 1, 0, 20, 5, 10, 10, 15, 15, 1, 1}), 0.00, 0.00,
					new int[] {8, 5, 2}, new int[] {6, 15, 17}),
			/*05*/ new Quest("Investigate underground cult", 40, 
					new SkillSet(new int[] {0, 10, 1, 10, 0, 15, 10, 0, 15, 15, 1, 10}), 0.00, 0.50,
					new int[] {9, 12, 13}, new int[] {6}),
			/*06*/ new Quest("Prevent opening of demonic gate", 500, 
					new SkillSet(new int[] {25, 50, 1, 50, 50, 25, 50, 50, 25, 25, 50, 50}), 0.20, 0.50,
					new int[] {5}, new int[] {6, 7}),
			/*07*/ new Quest("Close demonic gate", 1000, 
					new SkillSet(new int[] {100, 100, 1, 100, 50, 50, 50, 100, 100, 50, 100, 100}), 0.90, 1.00,
					new int[] {5}, new int[] {7, 19}),
			/*08*/ new Quest("Enter knights' tournament", 100, 
					new SkillSet(new int[] {10, 1, 1, 1, 5, 5, 5, 5, 10, 5, 5, 5}), 0.50, 0.02,
					new int[] {8, 14, 16}, new int[] {0, 12, 13}),
			/*09*/ new Quest("Conduct magical research of strange artifact", 100, 
					new SkillSet(new int[] {0, 10, 10, 0, 10, 0, 10, 5, 5, 1, 0, 10}), 0.01, 0.01,
					new int[] {9, 10, 18}, new int[] {9, 6, 17}),
			/*10*/ new Quest("Search for mystical oasis in desert", 80, 
					new SkillSet(new int[] {0, 10, 10, 20, 0, 0, 20, 0, 20, 1, 15, 20}), 0.30, 0.10,
					new int[] {9, 13, 14}, new int[] {0, 10, 13}),
			/*11*/ new Quest("Slay dragon", 10000, 
					new SkillSet(new int[] {500, 500, 500, 500, 500, 500, 500, 500, 500, 500, 500, 500}), 1.00, 1.00,
					new int[] {16}, new int[] {11, 19}),
			/*12*/ new Quest("Repossess pirate ship", 100, 
					new SkillSet(new int[] {50, 1, 1, 50, 50, 50, 25, 0, 40, 10, 30, 1}), 0.60, 0.50,
					new int[] {14}, new int[] {13}),
			/*13*/ new Quest("Walk neighbor's dog", 15, 
					new SkillSet(new int[] {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1}), 0.00, 1.00,
					new int[] {15}, new int[] {11}),
			/*14*/ new Quest("Search for legendary buried treasure", 500, 
					new SkillSet(new int[] {1, 1, 1, 1, 1, 1, 100, 1, 1, 1, 1, 1}), 0.00, 0.00,
					new int[] {2, 9, 16}, new int[] {14, 0, 13}),
			/*15*/ new Quest("Defeat the mysterious Dog-Man", 30, 
					new SkillSet(new int[] {10, 10, 1, 1, 1, 1, 1, 1, 1, 1, 10, 1}), 0.20, 0.00,
					new int[] {12}, new int[] {13}),
			/*16*/ new Quest("Perform in night-long rock concert", 30, 
					new SkillSet(new int[] {0, 1, 1, 0, 50, 1, 1, 50, 1, 30, 10, 50}), 0.00, 0.00,
					new int[] {16}, new int[] {0, 18}),
			/*17*/ new Quest("Defend town from evil ducks", 100, 
					new SkillSet(new int[] {30, 20, 20, 1, 1, 1, 1, 1, 100, 1, 20, 50}), 0.20, 0.50,
					new int[] {8, 18}, new int[] {17, 11}),
			/*18*/ new Quest("Collect bugs", 50, 
					new SkillSet(new int[] {0, 20, 20, 30, 20, 20, 30, 20, 10, 1, 1, 30}), 0.00, 0.00,
					new int[] {18, 0, 3}, new int[] {15}),
			/*19*/ new Quest("Rebuild kingdom", 0, 
					new SkillSet(new int[] {1000, 1000, 1000, 1000, 1000, 1000, 1000, 1000, 1000, 1000, 1000, 1000}), 0.00, 0.00,
					new int[] {0, 3, 12}, new int[] {19}),
	};
	
	public Game() {
		idToEmployees = new HashMap<>();
		idToPotentialHires = new HashMap<>();
		// Initialize the available quests
		currQuests = new Quest[NUM_AVAILABLE_QUESTS];
		for(int i = 0; i < NUM_AVAILABLE_QUESTS; i++) {
			currQuests[i] = POSSIBLE_QUESTS[ProbabilisticBool.rng.nextInt(POSSIBLE_QUESTS.length)];
		}
		questIdToAssignments = new HashMap<>();
		for(int i = 0; i < NUM_AVAILABLE_QUESTS; i++) {
			questIdToAssignments.put(i, new ArrayList<>());
		}
	}
	
	// Main game loop, gets user input
	public void gameLoop() {
		System.out.println("---------------Welcome to Adventurer's Inc.!---------------");
		@SuppressWarnings("resource")
		Scanner input = new Scanner(System.in);
		String[] command;
		int currWeek = week;
		while(running) {
			System.out.println("---------------Week " + week + "---------------");
			// Refresh the potential hires
			idToPotentialHires.clear();
			idToPotentialHires.put(0, new Adventurer(ProbabilisticBool.rng));
			idToPotentialHires.put(1, new Adventurer(ProbabilisticBool.rng));
			idToPotentialHires.put(2, new Adventurer(ProbabilisticBool.rng));
			
			// Commands for the week
			while(currWeek == week) {
				// Get input and split into words
				command = input.nextLine().split("\\s+");
				// Parse the command
				parseCommand(command);
				// Check for a quit command
				if(!running)
					return;
			}
			
			// Between week events
			betweenWeeks();
			
			// Update currWeek to match the new week
			currWeek++;
		}
	}
	
	// Executes a single user command
	public void parseCommand(String[] command) {
		// NOP for empty command
		if(command.length == 0) return;
		switch(command[0]) {
		// Company stats
		case "stats": {
			System.out.println("Week: " + week);
			System.out.println("Funds: " + money + CURRENCY);
			long totalPay = 0;
			for(Adventurer a : idToEmployees.values())
				totalPay += a.pay();
			System.out.println("Weekly wages to adventurers: " + totalPay + CURRENCY);
			System.out.println("Total adventurers employed: " + idToEmployees.size());
			break;
		}
		// Show all potential hires
		case "hires":
			if(idToPotentialHires.isEmpty()) {
				System.out.println("No available candidates");
				return;
			}
			for(Map.Entry<Integer, Adventurer> entry : idToPotentialHires.entrySet()) {
				System.out.println("# Potential Hire " + entry.getKey() + "\n"
						+ ((Adventurer) entry.getValue()).description());
			}
			break;
		// Hire a new adventurer
		case "hire": {
			int id;
			Adventurer newHire;
			try {
				id = Integer.parseInt(command[1]);
				newHire = idToPotentialHires.get(id);
				if(newHire == null) throw new IllegalArgumentException();
			} catch(Exception ex) {
				System.out.println("Invalid hire ID");
				return;
			}
			idToEmployees.put(totalHired, newHire);
			idToPotentialHires.remove(id);
			totalHired++;
			break;
		}
		// Show employee summaries
		case "adventurers":
			if(idToEmployees.isEmpty()) {
				System.out.println("No adventurers employed");
				return;
			}
			for(Map.Entry<Integer, Adventurer> entry : idToEmployees.entrySet()) {
				System.out.println("# Adventurer " + entry.getKey() + "\n"
						+ ((Adventurer) entry.getValue()).summary());
			}
			break;
		// Show detailed description of an adventurer
		case "details": {
			int id;
			Adventurer selected;
			try {
				id = Integer.parseInt(command[1]);
				selected = idToEmployees.get(id);
				if(selected == null) throw new IllegalArgumentException();
			} catch(Exception ex) {
				System.out.println("Invalid adventurer ID");
				return;
			}
			System.out.println(selected.description());
			break;
		}
		// Fire an adventurer
		case "fire": {
			int id;
			Adventurer selected;
			try {
				id = Integer.parseInt(command[1]);
				selected = idToEmployees.get(id);
				if(selected == null) throw new IllegalArgumentException();
			} catch(Exception ex) {
				System.out.println("Invalid adventurer ID");
				return;
			}
			idToEmployees.remove(id);
			System.out.println(selected.name + " was fired");
			break;
		}
		// Show current quests
		case "quests": {
			for(int i = 0; i < NUM_AVAILABLE_QUESTS; i++) {
				System.out.print("# Quest " + i + "\n"
						+ currQuests[i].summary());
				System.out.println("Assigned: " + questIdToAssignments.get(i) + "\n");
			}
			break;
		}
		// Assign adventurers to quests
		case "assign": {
			// Check valid command syntax
			if(command.length < 4 || !command[command.length - 2].equals("to")) {
				System.out.println("Invalid assignment syntax");
				return;
			}
			// Check adventurer Ids are valid
			List<Adventurer> assigned = new ArrayList<>();
			try {
				for(int i = 1; i < command.length - 2; i++) {
					if(!idToEmployees.containsKey(Integer.parseInt(command[i])))
						throw new IllegalArgumentException();
					assigned.add(idToEmployees.get(Integer.parseInt(command[i])));
				}
			} catch(Exception ex) {
				System.out.println("Invalid adventurer ID");
				return;
			}
			// Check quest Id is valid
			int questId = -1;
			try {
				questId = Integer.parseInt(command[command.length - 1]);
				if(questId < 0 || questId >= NUM_AVAILABLE_QUESTS) 
					throw new IllegalArgumentException();
			} catch(Exception ex) {
				System.out.println("Invalid quest ID");
				return;
			}
			// Assign the adventurers if everything is valid
			for(Adventurer assign : assigned) {
				// If assign is already assigned to a quest, reassign them
				if(assign.currQuestId != -1) {
					questIdToAssignments.get(assign.currQuestId).remove(assign);
				}
				assign.currQuestId = questId;
			}
			questIdToAssignments.get(questId).addAll(assigned);
			break;
		}
		// Unassign adventurers from their quests
		case "free": {
			List<Adventurer> freed = new ArrayList<>();
			// Free all adventurers
			if(command.length == 2 && command[1].equals("all")) {
				clearAssignments();
				break;
			}
			// Free particular adventurers
			// Check adventurer Ids are valid
			try {
				for(int i = 1; i < command.length; i++) {
					if(!idToEmployees.containsKey(Integer.parseInt(command[i])))
						throw new IllegalArgumentException();
					freed.add(idToEmployees.get(Integer.parseInt(command[i])));
				}
			} catch(Exception ex) {
				System.out.println("Invalid adventurer ID");
				return;
			}
			for(Adventurer free : freed) {
				// If assign is already assigned to a quest, reassign them
				if(free.currQuestId != -1) {
					questIdToAssignments.get(free.currQuestId).remove(free);
				}
				free.currQuestId = -1;
			}
			break;
		}
		// Pass the week
		case "pass":
			week++;
			break;
		// End game
		case "quit":
			running = false;
			break;
		default:
			System.out.println("Unknown command");
			break;
		}
	}
	
	// Process events between weeks
	public void betweenWeeks() {		
		StringBuilder report = new StringBuilder();
		int moneyDiff = 0;
		
		// Update the quests
		for(int i = 0; i < NUM_AVAILABLE_QUESTS; i++) {
			Quest.QuestResult result = currQuests[i].update(questIdToAssignments.get(i));
			// Include quest in report if adventurers were assigned
			if(result.succeeded) {
				report.append("Quest (" + currQuests[i] + ") succeeded!\n");
				// Get quest reward
				int reward = currQuests[i].reward;
				moneyDiff += reward;
 				report.append("Reward received: " + reward + CURRENCY + "\n");
			}
			else if(!result.defaulted) {
				report.append("Quest (" + currQuests[i] + ") failed.\n");
				// Include in the report any deaths
				report.append("Deaths: " + result.deaths + "\n");
			}
			
			// Set the quest to the next quest
			currQuests[i] = POSSIBLE_QUESTS[result.nextQuestId];
		}
		
		// Clear assignments
		clearAssignments();
		
		// Remove any dead adventurers
		Iterator<Map.Entry<Integer, Adventurer>> cleaner = idToEmployees.entrySet().iterator();
		while(cleaner.hasNext()) {
			Map.Entry<Integer, Adventurer> entry = cleaner.next();
			if(entry.getValue().dead)
				cleaner.remove();
		}
		
		// Pay for injuries
		int numInjured = (int) idToEmployees.values().stream()
				.filter(a -> a.injured).count();
		moneyDiff -= numInjured * Adventurer.INJURY_COST;
		report.append("Total number of injuries: " + numInjured + "\n");
		report.append("Injury costs = Injuries x " + Adventurer.INJURY_COST + 
				" = " + (numInjured * Adventurer.INJURY_COST) + CURRENCY + "\n");
		
		// Pay adventurers
		int wages = (int) idToEmployees.values().stream()
				.mapToInt(Adventurer::pay)
				.reduce(0, (a, b) -> a + b);
		moneyDiff -= wages;
		report.append("Adventurer wages: " + wages + CURRENCY + "\n");
		
		// Report events and profit to player
		money += moneyDiff;
		report.append("This week's profits: " + moneyDiff + CURRENCY + "\n");
		report.append("Total funds: " + money + CURRENCY + "\n");
		System.out.println(report.toString());
		
		// Check for debt at beginning of new year
		if(week % 52 == 0 && money < 0) {
			System.out.println("You're in debt at the beginning of the year, you have lost the game.");
			@SuppressWarnings("resource")
			Scanner waitForInput = new Scanner(System.in);
			waitForInput.nextLine();
			running = false;
		}
	}
	
	// Clear the quests of all assigned adventurers
	public void clearAssignments() {
		for(Adventurer free : idToEmployees.values()) {
			free.currQuestId = -1;
		}
		for(List<Adventurer> assigned : questIdToAssignments.values()) {
			assigned.clear();
		}
	}
}
