import java.util.List;
import java.util.Scanner;

public class Handbook {
    private Scanner input;
    private World world;
    private Trainer player;
    private String currentBiome;
    private List<String> visitedBiomes;
    private List<String> defeatedRivals;

    public Handbook(Scanner input, World world, Trainer player, String currentBiome,
                    List<String> visitedBiomes, List<String> defeatedRivals) {
        this.input = input;
        this.world = world;
        this.player = player;
        this.currentBiome = currentBiome;
        this.visitedBiomes = visitedBiomes;
        this.defeatedRivals = defeatedRivals;
    }


    public void open() {
        boolean inHandbook = true;


        while (inHandbook == true) {
            System.out.println();
            System.out.println("===== HANDBOOK =====");
            System.out.println("1. Biome Index");
            System.out.println("2. My Progress");
            System.out.println("3. Battle Stats");
            System.out.println("4. Rival Tracker");
            System.out.println("5. Mob List");
            System.out.println("0. Back");


            int pick = readIntInRange("> ", 0, 5);


            if (pick == 0) inHandbook = false;
            else if (pick == 1) biomeIndex();
            else if (pick == 2) myProgress();
            else if (pick == 3) battleStats();
            else if (pick == 4) rivalTracker();
            else if (pick == 5) mobList();
        }
    }


    public void biomeIndex() {
        System.out.println();
        System.out.println("--- BIOME INDEX ---");


        String[] biomes = world.getBiomes();


        for (int i = 0; i < biomes.length; i++) {
            String biome = biomes[i];
            System.out.println();


            int unlockLevel = world.getUnlockLevel(biome);


            if (world.isUnlocked(biome, player.getLevel()) == true)
                System.out.println("  " + biome + "  [unlocked]");
            else
                System.out.println("  " + biome + "  [LOCKED - Trainer Lv." + unlockLevel + "]");


            List<String> mobs = world.getMobsInBiome(biome);


            for (int j = 0; j < mobs.size(); j++) {
                String mobType = mobs.get(j);
                String tag = "[???]       ";


                if (world.isDiscovered(mobType) == true) tag = "[discovered]";


                System.out.println("    " + tag + " " + mobType);
            }
        }


        System.out.println();
        System.out.println("Discovered " + world.getDiscoveredCount()
                + " of " + world.getTotalMobCount() + " mobs total.");
    }


    public void myProgress() {
        System.out.println();
        System.out.println("--- MY PROGRESS ---");
        System.out.println("  Trainer:         " + player.getName());
        System.out.println("  Trainer Level:   " + player.getLevel());
        System.out.println("  Chosen Biome:    " + currentBiome);
        System.out.println("  Party Size:      " + player.partySize());
        System.out.println("  Active Mob:      "
                + (player.getActive() == null ? "none" : player.getActive().getType()));
        System.out.println("  Coins:           " + player.getCoins());
        System.out.println("  Balls:           " + player.getBalls());


        System.out.println();
        System.out.println("  Titles:");


        if (player.getTitles().isEmpty() == true) {
            System.out.println("    (none yet)");
        } else {
            for (int i = 0; i < player.getTitles().size(); i++) {
                System.out.println("    - " + player.getTitles().get(i));
            }
        }


        System.out.println();
        System.out.println("  Biomes Visited:  " + visitedBiomes.size()
                + " of " + world.getBiomes().length);


        for (int i = 0; i < visitedBiomes.size(); i++) {
            System.out.println("    - " + visitedBiomes.get(i));
        }
    }


    public void battleStats() {
        System.out.println();
        System.out.println("--- BATTLE STATS ---");
        System.out.println("  Battles Won:      " + player.getBattlesWon());
        System.out.println("  Mobs Caught:      " + player.getMobsCaught());
        System.out.println("  Faints Suffered:  " + player.getFaintsSuffered());
        System.out.println("  Mobs Discovered:  " + world.getDiscoveredCount()
                + " / " + world.getTotalMobCount());


        System.out.println();
        System.out.println("  Party overview:");


        List<Creature> party = player.getParty();


        for (int i = 0; i < party.size(); i++) {
            Creature c = party.get(i);
            System.out.println("    " + c.getType() + " (Lv." + c.getLevel()
                    + ")  HP " + c.getHealth() + "/" + c.getMaxHealth()
                    + "  XP " + c.getXp() + "/" + c.getXpToNext());
        }
    }


    public void rivalTracker() {
        System.out.println();
        System.out.println("--- RIVAL TRACKER ---");


        String[] names = {"Blake", "Rowan", "Wren"};
        String[] biomes = {"Grasslands", "Nether", "The End"};


        for (int i = 0; i < names.length; i++) {
            int unlockLevel = world.getRivalUnlockLevel(names[i]);
            String status;


            if (defeatedRivals.contains(names[i]) == true) status = "DEFEATED";
            else if (player.getLevel() < unlockLevel) status = "LOCKED - Trainer Lv." + unlockLevel;
            else status = "AVAILABLE";


            System.out.println("  " + names[i] + " (found in " + biomes[i] + ")");
            System.out.println("      Status: " + status);
        }


        System.out.println();
        System.out.println("  Rivals defeated: " + defeatedRivals.size() + " / 3");
    }


    public void mobList() {
        System.out.println();
        System.out.println("--- MOB LIST ---");
        System.out.println("Only discovered mobs are shown.\n");


        String[] rarities = {"Common", "Uncommon", "Rare", "Legendary"};
        List<Creature> all = world.getAllMobs();
        int totalShown = 0;


        for (int r = 0; r < rarities.length; r++) {
            String rarity = rarities[r];
            System.out.println("  " + rarity + ":");


            boolean anyShown = false;


            for (int i = 0; i < all.size(); i++) {
                Creature c = all.get(i);


                if (c.getRarity().equalsIgnoreCase(rarity) == false) continue;
                if (world.isDiscovered(c.getType()) == false) continue;


                System.out.println("    [discovered] " + c.getType());
                anyShown = true;
                totalShown = totalShown + 1;
            }


            if (anyShown == false) System.out.println("    (none discovered yet)");
        }


        System.out.println();
        System.out.println("  Total discovered: " + totalShown
                + " / " + world.getTotalMobCount());
    }


    private int readIntInRange(String prompt, int min, int max) {
        while (true) {
            System.out.print(prompt);
            String line = input.nextLine().trim();


            int value = -99999;
            boolean ok = true;


            try {
                value = Integer.parseInt(line);
            } catch (NumberFormatException e) {
                ok = false;
            }


            if (ok == false) {
                System.out.println("  Invalid input - that's not a number. Try again!");
                continue;
            }
            if (value < min || value > max) {
                System.out.println("  Invalid input - enter a number between "
                        + min + " and " + max + ".");
                continue;
            }
            return value;
        }
    }
}

