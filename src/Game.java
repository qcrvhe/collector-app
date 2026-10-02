import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


// everything the player does outside of battles goes through here
public class Game
{
    private Scanner input;
    private World world;


    private Trainer player;
    private String currentBiome;
    private boolean running;
    private ArrayList<String> visitedBiomes;
    private ArrayList<String> defeatedRivals;


    public Game(Scanner input, World world)
    {
        this.input = input;
        this.world = world;
        this.running = true;
        this.visitedBiomes = new ArrayList<String>();
        this.defeatedRivals = new ArrayList<String>();
    }


    public void start()
    {
        printTitle();


        player = chooseTrainer();
        currentBiome = chooseBiome();
        player.setChosenBiome(currentBiome);
        visitedBiomes.add(currentBiome);


        Creature starter = chooseStarter();
        player.addToParty(starter);
        announceDiscovery(starter.getType());


        System.out.println();
        System.out.println(player.getName() + "'s journey begins with "
                + starter.getType() + " in the " + currentBiome + "!");


        while (running == true)
        {
            gameLoop();
        }


        System.out.println();
        System.out.println("Thanks for playing! Goodbye, " + player.getName() + ".");
    }


    public void printTitle()
    {
        System.out.println("========================================");
        System.out.println("      MOB TRAINER ADVENTURE         ");
        System.out.println("========================================");
    }


    public void gameLoop()
    {
        printMenu();


        int maxPick = 7;
        if (townUnlocked() == true)
        {
            maxPick = 8;
        }


        int pick = readIntInRange("> ", 1, maxPick);


        if (pick == 1) explore();
        else if (pick == 2) showParty();
        else if (pick == 3) switchActive();
        else if (pick == 4)
        {
            Handbook h = new Handbook(input, world, player, currentBiome,
                    visitedBiomes, defeatedRivals);
            h.open();
        }
        else if (pick == 5) travel();
        else if (pick == 6) challengeRival();
        else if (pick == 7)
        {
            if (townUnlocked() == true) tryEnterTown();
            else running = false;
        }
        else if (pick == 8) running = false;
    }


    public void printMenu()
    {
        System.out.println();
        System.out.println("===== MAIN MENU =====");
        System.out.println("1. Explore the wild");
        System.out.println("2. View your party");
        System.out.println("3. Switch active mob");
        System.out.println("4. Open Handbook");
        System.out.println("5. Travel to another biome");
        System.out.println("6. Challenge a rival");


        if (townUnlocked() == true)
        {
            System.out.println("7. Enter Town Square  [unlocked: friendly companion]");
            System.out.println("8. Quit");
        }
        else
        {
            System.out.println("7. Quit");
            System.out.println();
            System.out.println("(Equip a friendly mob as active to unlock the Town Square.)");
        }
    }


    public boolean townUnlocked()
    {
        Creature active = player.getActive();
        if (active == null) return false;
        if (active.isFriendly() == true) return true;
        return false;
    }


    // ========== input helpers ==========
    public int readIntInRange(String prompt, int min, int max)
    {
        while (true)
        {
            System.out.print(prompt);
            String line = input.nextLine().trim();


            int value = -99999;
            boolean ok = true;


            try
            {
                value = Integer.parseInt(line);
            }
            catch (NumberFormatException e)
            {
                ok = false;
            }


            if (ok == false)
            {
                System.out.println("  Invalid input - that's not a number. Try again!");
                continue;
            }
            if (value < min || value > max)
            {
                System.out.println("  Invalid input - enter a number between "
                        + min + " and " + max + ".");
                continue;
            }
            return value;
        }
    }


    public boolean readYesNo(String prompt)
    {
        while (true)
        {
            System.out.print(prompt + " (y/n): ");
            String line = input.nextLine().trim().toLowerCase();


            if (line.equals("y") || line.equals("yes")) return true;
            if (line.equals("n") || line.equals("no")) return false;


            System.out.println("  Please answer 'y' or 'n'.");
        }
    }


    // ========== setup ==========
    public Trainer chooseTrainer()
    {
        System.out.println();
        System.out.println("Choose your trainer:");
        System.out.println("1. Steve");
        System.out.println("2. Alex");


        int pick = readIntInRange("> ", 1, 2);


        String name = "Alex";
        if (pick == 1) name = "Steve";


        System.out.println("You chose " + name + "!");
        Trainer t = new Trainer(name, 1);
        return t;
    }


    public String chooseBiome()
    {
        System.out.println();
        System.out.println("Choose a biome to spawn in:");


        String[] biomes = world.getBiomes();
        ArrayList<String> unlocked = new ArrayList<String>();


        for (int i = 0; i < biomes.length; i++)
        {
            if (world.isUnlocked(biomes[i], player.getLevel()) == true)
                unlocked.add(biomes[i]);
        }


        for (int i = 0; i < unlocked.size(); i++)
        {
            System.out.println((i + 1) + ". " + unlocked.get(i));
        }


        int pick = readIntInRange("> ", 1, unlocked.size());
        return unlocked.get(pick - 1);
    }


    public Creature chooseStarter()
    {
        System.out.println();
        System.out.println("Choose your starter:");
        System.out.println("  1. Zombie");
        System.out.println("  2. Skeleton");
        System.out.println("  3. Spider");


        int pick = readIntInRange("> ", 1, 3);


        String type = "Zombie";
        if (pick == 2) type = "Skeleton";
        else if (pick == 3) type = "Spider";


        System.out.println();
        System.out.println(player.getName() + " chose " + type + "!");


        return world.makeStartingCopy(type);
    }


    // ========== quests / discovery ==========
    public void announceDiscovery(String type)
    {
        if (world.discover(type) == true)
        {
            System.out.println("  [Handbook] New entry unlocked: " + type + "!");
            trackQuestProgress(Quest.TYPE_DISCOVER, "", 1);
        }
    }


    public void trackQuestProgress(String type, String target, int amount)
    {
        List<Quest> all = world.getAllQuests();


        for (int i = 0; i < all.size(); i++)
        {
            Quest q = all.get(i);


            if (q.isComplete()) continue;
            if (world.isQuestAvailable(q) == false) continue;


            boolean matches = false;


            if (q.getType().equals(Quest.TYPE_DISCOVER))
            {
                if (type.equals(Quest.TYPE_DISCOVER)) matches = true;
            }
            else if (q.getType().equals(type))
            {
                if (q.getTarget().length() == 0) matches = true;
                else if (q.getTarget().equalsIgnoreCase(target)) matches = true;
            }


            if (matches == true)
            {
                q.increment(amount);
                if (q.isReady() == true) completeQuest(q);
            }
        }
    }


    public void completeQuest(Quest q)
    {
        q.markComplete();
        System.out.println();
        System.out.println("  *** QUEST COMPLETE: " + q.getDescription());
        grantReward(q);
    }


    public void grantReward(Quest q)
    {
        if (q.getRewardType().equals(Quest.REWARD_XP))
        {
            int amount = parseIntSafe(q.getRewardData(), 0);
            Creature active = player.getActive();


            if (active != null)
            {
                String log = active.gainXp(amount);
                System.out.println("    +" + amount + " XP to " + active.getType() + log);
            }
        }
        else if (q.getRewardType().equals(Quest.REWARD_LEVEL))
        {
            int amount = parseIntSafe(q.getRewardData(), 1);
            int oldLevel = player.getLevel();


            for (int i = 0; i < amount; i++) player.levelUp();


            int newLevel = player.getLevel();
            System.out.println("    +" + amount + " Trainer Level (now Lv." + newLevel + ")");
            checkBiomeUnlocks(oldLevel, newLevel);
        }
        else if (q.getRewardType().equals(Quest.REWARD_BALLS))
        {
            int amount = parseIntSafe(q.getRewardData(), 0);
            player.addBalls(amount);
            System.out.println("    +" + amount + " Balls (you now have " + player.getBalls() + ")");
        }
        else if (q.getRewardType().equals(Quest.REWARD_TITLE))
        {
            player.addTitle(q.getRewardData());
            System.out.println("    Title unlocked: \"" + q.getRewardData() + "\"");
        }
        else if (q.getRewardType().equals(Quest.REWARD_UNLOCK))
        {
            System.out.println("    Unlocked: " + q.getRewardData());
        }
    }


    public int parseIntSafe(String s, int fallback)
    {
        try
        {
            return Integer.parseInt(s.trim());
        }
        catch (Exception e)
        {
            return fallback;
        }
    }


    public void checkBiomeUnlocks(int oldLevel, int newLevel)
    {
        String[] biomes = world.getBiomes();


        for (int i = 0; i < biomes.length; i++)
        {
            int need = world.getUnlockLevel(biomes[i]);


            if (oldLevel < need && newLevel >= need)
            {
                System.out.println("  *** New biome unlocked: " + biomes[i]
                        + "! (see Travel menu) ***");
            }
        }
    }


    // ========== exploring ==========
    public void explore()
    {
        System.out.println();
        System.out.println("--- Exploring the " + currentBiome + " ---");


        int encounters = 1 + (int)(Math.random() * 3);


        for (int i = 0; i < encounters; i++)
        {
            Creature wild = world.getRandomMobFromBiome(currentBiome, player.getLevel());


            if (wild == null)
            {
                System.out.println("  You wander but find nothing...");
                continue;
            }


            announceDiscovery(wild.getType());


            System.out.println();
            System.out.println("  A wild " + wild.getType() + " (Lv." + wild.getLevel()
                    + ", " + wild.getRarity() + ") appears!");
            System.out.println("  " + wild);


            Creature active = player.getActive();


            if (active == null || active.isFainted() == true)
            {
                System.out.println("  You have no fighting mob - you back away.");
                continue;
            }


            Battle b = new Battle(input, world, player, this, currentBiome);
            b.run(active, wild);
        }


        Creature active = player.getActive();


        if (active != null)
        {
            if (active.isFainted() == false && active.getHealth() < active.getMaxHealth())
            {
                active.heal(5);
                System.out.println();
                System.out.println("  " + active.getType() + " rests. HP: "
                        + active.getHealth() + "/" + active.getMaxHealth());
            }
        }
    }


    // ========== party ==========
    public void showParty()
    {
        System.out.println();
        System.out.println("--- " + player.getName() + "'s Party ---");


        List<Creature> party = player.getParty();


        if (party.isEmpty())
        {
            System.out.println("  (empty)");
            return;
        }


        Creature active = player.getActive();


        for (int i = 0; i < party.size(); i++)
        {
            Creature c = party.get(i);
            String marker = "";


            if (active != null && active == c) marker = " *ACTIVE*";


            System.out.println("  " + (i + 1) + ". " + c + marker);


            if (c.canEvolve() == true)
            {
                int gate = 20 * (c.getEvolution() + 1);
                System.out.println("      -> evolves to " + c.getEvolutionTarget()
                        + " at Lv." + gate);
            }
        }


        System.out.println("  Fainted: " + player.countFainted() + "/" + party.size());
        System.out.println("  Coins: " + player.getCoins() + " | Balls: " + player.getBalls());


        if (player.getTitles().isEmpty() == false)
        {
            String all = "";
            for (int i = 0; i < player.getTitles().size(); i++)
            {
                if (i > 0) all = all + ", ";
                all = all + player.getTitles().get(i);
            }
            System.out.println("  Titles: " + all);
        }
    }


    public void switchActive()
    {
        if (player.partySize() == 0)
        {
            System.out.println();
            System.out.println("  Your party is empty.");
            return;
        }


        List<Creature> party = player.getParty();


        for (int i = 0; i < party.size(); i++)
        {
            System.out.println("  " + (i + 1) + ". " + party.get(i));
        }


        System.out.println("  0. Back");


        int pick = readIntInRange("> ", 0, party.size());


        if (pick == 0) return;


        player.setActive(party.get(pick - 1));


        Creature active = player.getActive();
        System.out.println("  Active is now " + active.getType() + "!");


        if (active.isFriendly() == true)
            System.out.println("  A friendly companion! The Town Square is now unlocked from the main menu.");
        else
            System.out.println("  (The Town Square needs a friendly active mob to enter.)");
    }


    // ========== travel ==========
    public void travel()
    {
        System.out.println();
        System.out.println("Travel to which biome?");


        String[] biomes = world.getBiomes();


        for (int i = 0; i < biomes.length; i++)
        {
            String biome = biomes[i];


            if (world.isUnlocked(biome, player.getLevel()) == true)
            {
                System.out.println("  " + (i + 1) + ". " + biome);
            }
            else
            {
                int need = world.getUnlockLevel(biome);
                System.out.println("  " + (i + 1) + ". " + biome + "  [LOCKED - Trainer Lv."
                        + need + "]");
            }
        }


        System.out.println("  0. Back");


        int pick = readIntInRange("> ", 0, biomes.length);
        if (pick == 0) return;


        String chosen = biomes[pick - 1];


        if (world.isUnlocked(chosen, player.getLevel()) == false)
        {
            int need = world.getUnlockLevel(chosen);
            System.out.println("  The " + chosen + " is locked. You need Trainer Lv."
                    + need + " to enter.");
            return;
        }


        currentBiome = chosen;
        player.setChosenBiome(currentBiome);


        if (visitedBiomes.contains(currentBiome) == false)
            visitedBiomes.add(currentBiome);


        System.out.println("  You arrive in the " + currentBiome + ".");


        List<Quest> all = world.getAllQuests();


        for (int i = 0; i < all.size(); i++)
        {
            Quest q = all.get(i);


            if (q.isComplete()) continue;
            if (q.getType().equals(Quest.TYPE_TRAVEL) == false) continue;


            q.setProgress(visitedBiomes.size());


            if (q.isReady() == true) completeQuest(q);
        }
    }


    // ========== rivals ==========
    public void challengeRival()
    {
        System.out.println();
        System.out.println("--- CHALLENGE A RIVAL ---");


        // hardcoded for now - would be nicer in a list of Trainer objects
        String[][] rivals = new String[3][4];
        rivals[0][0] = "Blake";   rivals[0][1] = "Grasslands"; rivals[0][2] = "Wolf";    rivals[0][3] = "Dire Wolf";
        rivals[1][0] = "Rowan";   rivals[1][1] = "Nether";     rivals[1][2] = "Blaze";   rivals[1][3] = "Piglin";
        rivals[2][0] = "Wren";    rivals[2][1] = "The End";    rivals[2][2] = "Enderman";rivals[2][3] = "Wither Skeleton";


        for (int i = 0; i < rivals.length; i++)
        {
            String status = "";


            if (defeatedRivals.contains(rivals[i][0]) == true)
                status = " [DEFEATED]";
            else if (player.getLevel() < world.getRivalUnlockLevel(rivals[i][0]))
            {
                int need = world.getRivalUnlockLevel(rivals[i][0]);
                status = "  [LOCKED - Trainer Lv." + need + "]";
            }


            System.out.println("  " + (i + 1) + ". " + rivals[i][0]
                    + " (found in " + rivals[i][1] + ")" + status);
        }


        System.out.println("  0. Back");


        int pick = readIntInRange("> ", 0, rivals.length);
        if (pick == 0) return;


        String[] rival = rivals[pick - 1];


        if (defeatedRivals.contains(rival[0]) == true)
        {
            System.out.println("  You already defeated " + rival[0] + "!");
            return;
        }


        if (player.getLevel() < world.getRivalUnlockLevel(rival[0]))
        {
            int need = world.getRivalUnlockLevel(rival[0]);
            System.out.println("  " + rival[0] + " isn't interested in a rookie. Come back at Trainer Lv." + need + ".");
            return;
        }


        System.out.println();
        System.out.println("  " + rival[0] + " challenges you!");


        for (int i = 2; i < rival.length; i++)
        {
            Creature foe = world.makeStartingCopy(rival[i]);
            if (foe == null) continue;


            for (int lvl = 1; lvl < player.getLevel() + 2; lvl++)
                foe.gainXp(foe.getXpToNext());


            Creature mine = player.getActive();
            if (mine == null)
            {
                System.out.println("  You have no active mob.");
                return;
            }


            Battle b = new Battle(input, world, player, this, currentBiome);
            b.run(mine, foe);


            if (mine.isFainted() == true)
            {
                System.out.println("  You lost to " + rival[0] + ".");
                return;
            }
        }


        defeatedRivals.add(rival[0]);
        System.out.println("  You defeated " + rival[0] + "!");
        trackQuestProgress(Quest.TYPE_RIVAL, rival[0], 1);
    }


    // ========== town ==========
    public void tryEnterTown()
    {
        Creature active = player.getActive();


        if (active == null) return;
        if (active.isFriendly() == false)
        {
            System.out.println();
            System.out.println("  You need a friendly companion to enter the Town Square.");
            return;
        }


        System.out.println();
        System.out.println("*** You arrive at the Town Square ***");
        System.out.println("Your companion " + active.getType()
                + " trots alongside you. The townsfolk welcome you.");


        boolean inTown = true;


        while (inTown == true)
        {
            System.out.println();
            System.out.println("--- TOWN SQUARE ---");
            System.out.println("1. Rest at the Inn (heal party, free)");
            System.out.println("2. Visit the Shop");
            System.out.println("3. Talk to townsfolk");
            System.out.println("4. Open the Quest Board");
            System.out.println("5. Leave town");


            int pick = readIntInRange("> ", 1, 5);


            if (pick == 1)
            {
                player.healAll();
                System.out.println("  Your whole party is fully healed!");
            }
            else if (pick == 2) visitShop();
            else if (pick == 3) talkToTownsfolk();
            else if (pick == 4) questBoard();
            else if (pick == 5)
            {
                inTown = false;
                System.out.println("  You head back out into the " + currentBiome + ".");
            }
        }
    }


    public void visitShop()
    {
        System.out.println();
        System.out.println("--- SHOP ---");
        System.out.println("You have " + player.getCoins() + " coins and "
                + player.getBalls() + " balls.");


        String[] items = world.getShopItems();


        for (int i = 0; i < items.length; i++)
        {
            System.out.println("  " + (i + 1) + ". " + items[i]);
        }
        System.out.println("  0. Back");


        int pick = readIntInRange("> ", 0, items.length);
        if (pick == 0) return;


        int price = world.getShopPrice(pick - 1);
        int ballAmount = world.getShopBallAmount(pick - 1);


        if (price < 0)
        {
            System.out.println("  That item isn't available.");
            return;
        }


        if (player.spendCoins(price) == true)
        {
            player.addBalls(ballAmount);
            System.out.println("  Bought " + ballAmount + " Ball(s)! You now have "
                    + player.getBalls() + " balls and " + player.getCoins() + " coins.");
        }
        else
        {
            System.out.println("  Not enough coins.");
        }
    }


    public void talkToTownsfolk()
    {
        System.out.println();
        System.out.println("--- TOWNSFOLK ---");


        String[] lines = new String[]
                {
                        "Old Farmer: \"Watch out in the Nether, friend. Those Blazes don't play nice.\"",
                        "Blacksmith: \"Friendly mobs aren't useless, you know. A Pig on your side opens doors.\"",
                        "Villager Child: \"I saw a Warden once. My ears are still ringing.\"",
                        "Town Guard: \"Legendaries regen 5 HP every round. Bring a heavy hitter.\"",
                        "Scholar: \"Evolution is tied to level, not age. Level 20, 40, 60 - remember that.\"",
                        "Miner: \"Badlands give everything -1 DEF. Hard on the fighters, easy on the rangers.\"",
                        "Hunter: \"Legendaries don't show up in the wild until you're Trainer Lv.8. Stay sharp.\"",
                        "Trader: \"Bring a friendly mob into town and the innkeeper heals your whole team for free.\"",
                        "Rival Watcher: \"Blake shows up at Lv.3. Rowan at Lv.6. Wren won't bother with you until Lv.10.\""
                };


        int pick = (int)(Math.random() * lines.length);
        System.out.println("  " + lines[pick]);
    }


    public void questBoard()
    {
        System.out.println();
        System.out.println("--- QUEST BOARD ---");
        System.out.println("All quests are tracked silently as you play.");
        System.out.println();


        List<Quest> all = world.getAllQuests();


        for (int i = 0; i < all.size(); i++)
        {
            Quest q = all.get(i);
            boolean available = world.isQuestAvailable(q);


            if (available == false && q.isComplete() == false)
            {
                String prereqDesc = q.getPrerequisite();
                Quest pre = world.getQuest(q.getPrerequisite());


                if (pre != null) prereqDesc = pre.getDescription();


                System.out.println("  [LOCKED] " + q.getDescription()
                        + "  (requires: " + prereqDesc + ")");
                continue;
            }


            System.out.println("  " + q);


            if (q.isComplete() == false && q.hasPrerequisite() == true)
            {
                Quest pre = world.getQuest(q.getPrerequisite());
                if (pre != null) System.out.println("      Chain: " + pre.getDescription());
            }
        }


        System.out.println();
        System.out.println("  (Quests complete automatically the moment their goal is met.)");
    }


    // getters used by Battle / Handbook
    public Trainer getPlayer()
    { return player;
    }
    public String getCurrentBiome() { return currentBiome; }
    public List<String> getVisitedBiomes() { return visitedBiomes; }
    public List<String> getDefeatedRivals() { return defeatedRivals; }
    public World getWorld() { return world; }
    public Scanner getInput() { return input; }
}

