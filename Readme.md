# Welcome to Adventurers, Inc.!

Adventurers Inc is a command-line game where you manage a company that sends fantasy adventurers on quests.
To run the executable jar, use
`java -jar .\AdventurersInc.jar`

As a newly-sprung company in the adventurer industry, you have a responsibility to provide your customers with the skilled services they need to keep safe from dragons, profit from ancient dungeons, and investigate strange disappearances!

Hire adventurers and match them with quests to ensure maximal likelihood of success and minimal likelihood of horrible death!

### To Win

Make as much or as little profit as you want. Invest in your adventurers as much or as little as you want. Take on the most dangerous quests, or the most mundane, to your liking.

### To Lose

If you are in debt at the beginning of a given year, you lose.

### How to Play

Every week of in-game time, you can manage your company by entering commands.

`stats` shows how much money you have, how much your adventurers get paid, etc.

`hires` shows potential new hires

`hire ID` hire the adventurer of the given ID

`adventurers` shows the adventurers you have currently employed

`details ID` see details about the adventurer with the given ID

`fire ID` fire the adventurer with the given ID

`quests` lists the available quests

`assign ID1 ID2 ... IDN to QUEST_ID` assigns the adventurers from the list of IDs to the quest with the final ID

`free ID1 ID2 ... IDN` frees the adventurers of the given list of IDs from any quests they were assigned to
`free all` frees all adventurers

`pass` pass to the next week, you will receive a report of quest successes and failures as well as costs and income

`quit` quits the game

### Tips

* The probability that a quest will succeed is determined by how well adventurer skill sets match the skills that might be used for the quest.
* Putting multiple adventurers on the same quest allows them to put their skills together. So, adventurers weak in one skill can be compensated for by their teammates' skills.
* Death rate of a quest applies only if the quest fails. No adventurers will die if the quest succeeds.
* Whether you succeed or fail a quest determines what new quest will replace it. 
* Adventurers get paid every week. Adventurers of a higher level get paid more.