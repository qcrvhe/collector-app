import java.util.List;
import java.util.Scanner;


// one encounter from start to finish
public class Battle
{
    private Scanner input;
    private World world;
    private Trainer player;
    private Game game;
    private String currentBiome;


    public Battle(Scanner input, World world, Trainer player, Game game, String currentBiome)
    {
        this.input = input;
        this.world = world;
        this.player = player;
        this.game = game;
        this.currentBiome = currentBiome;
    }


    public void run(Creature mine, Creature foe)
    {
        System.out.println();
        System.out.println("  === " + mine.getType() + " (Lv." + mine.getLevel()
                + ") vs " + foe.getType() + " (Lv." + foe.getLevel() + ", "
                + foe.getRarity() + ") ===");


        giveBuffs(mine);


        String[] effect = world.getBiomeEffect(currentBiome);
        if (effect != null)
        {
            System.out.println("  [Biome] " + currentBiome + ": "
                    + effect[0] + " " + effect[1] + " to " + effect[2]);
        }


        if (foe.canFlee() == true)
        {
            if (Math.random() < 0.10)
            {
                System.out.println("  The wild " + foe.getType() + " flees! No battle.");
                return;
            }
        }


        boolean encounterOver = false;


        while (encounterOver == false)
        {
            System.out.println();
            System.out.println("  What will " + mine.getType() + " do?");
            System.out.println("    1. Fight");
            System.out.println("    2. Catch  (Balls: " + player.getBalls() + ")");
            System.out.println("    3. Switch mob");
            System.out.println("    4. Run away");


            int pick = game.readIntInRange("  > ", 1, 4);


            if (pick == 1)
            {
                if (turnBattle(mine, foe) == false) encounterOver = true;
            }
            else if (pick == 2)
            {
                if (tryCatch(foe) == true) encounterOver = true;
                else
                {
                    foeTurn(foe, mine);
                    if (mine.isFainted() == true)
                    {
                        System.out.println("  " + mine.getType() + " fainted! You retreat.");
                        player.recordFaint();
                        encounterOver = true;
                    }
                }
            }
            else if (pick == 3)
            {
                if (switchDuringBattle(mine) == true)
                {
                    mine = player.getActive();
                    foeTurn(foe, mine);
                    if (mine.isFainted() == true)
                    {
                        System.out.println("  " + mine.getType() + " fainted! You retreat.");
                        player.recordFaint();
                        encounterOver = true;
                    }
                }
            }
            else if (pick == 4)
            {
                if (tryFlee(mine, foe) == true) encounterOver = true;
                else
                {
                    System.out.println("  Couldn't escape!");
                    foeTurn(foe, mine);
                    if (mine.isFainted() == true)
                    {
                        System.out.println("  " + mine.getType() + " fainted! You retreat.");
                        player.recordFaint();
                        encounterOver = true;
                    }
                }
            }
        }
    }


    public boolean turnBattle(Creature mine, Creature foe)
    {
        System.out.println();
        System.out.println("  Choose a move:");


        int moveCount = mine.getMoveCount();
        for (int i = 0; i < moveCount; i++)
            System.out.println("    " + (i + 1) + ". " + mine.getMoveName(i));
        System.out.println("    0. Back");


        int pick = game.readIntInRange("  > ", 0, moveCount);
        if (pick == 0) return true;


        String move = mine.getMoveName(pick - 1);


        System.out.println();
        System.out.println("  " + mine.useMove(move, foe));
        mine.clearGuardBonus();


        if (foe.isFainted() == true)
        {
            endBattleWin(mine, foe);
            return false;
        }


        foeTurn(foe, mine);


        if (mine.isFainted() == true)
        {
            System.out.println("  " + mine.getType() + " fainted! You retreat to heal.");
            player.recordFaint();
            return false;
        }


        return true;
    }


    public void foeTurn(Creature foe, Creature mine)
    {
        if (foe.isFlinched() == true)
        {
            System.out.println("  " + foe.getType() + " is flinched and can't move!");
            foe.clearFlinch();
            return;
        }


        if (foe.isBurned() == true)
        {
            foe.tickBurn();
            System.out.println("  " + foe.getType() + " suffers burn damage. (HP: "
                    + foe.getHealth() + "/" + foe.getMaxHealth() + ")");
            if (foe.isFainted() == true)
            {
                endBattleWin(mine, foe);
                return;
            }
        }


        int moveCount = foe.getMoveCount();
        int pick = (int)(Math.random() * moveCount);
        String move = foe.getMoveName(pick);


        System.out.println("  " + foe.useMove(move, mine));
        foe.clearGuardBonus();


        if (mine.isBurned() == true)
        {
            mine.tickBurn();
            System.out.println("  " + mine.getType() + " suffers burn damage. (HP: "
                    + mine.getHealth() + "/" + mine.getMaxHealth() + ")");
        }
    }


    public void endBattleWin(Creature mine, Creature foe)
    {
        System.out.println("  You defeated the wild " + foe.getType() + "!");


        int xp = xpFor(foe);
        String log = mine.gainXp(xp);
        System.out.println("  +" + xp + " XP to " + mine.getType() + log);


        int oldLevel = player.getLevel();
        player.levelUp();
        int newLevel = player.getLevel();


        int reward = 5 + foe.getLevel();
        player.addCoins(reward);
        player.recordBattleWon();


        System.out.println("  +" + reward + " coins (you have " + player.getCoins() + ")");
        System.out.println("  " + player.getName() + " is now Lv." + newLevel + "!");


        game.checkBiomeUnlocks(oldLevel, newLevel);


        game.trackQuestProgress(Quest.TYPE_DEFEAT, foe.getType(), 1);
        if (foe.getRarity().equalsIgnoreCase("Legendary"))
            game.trackQuestProgress(Quest.TYPE_DEFEAT, "Legendary", 1);


        checkLevelQuests();
        tryAutoEvolve(mine);
    }


    public boolean tryCatch(Creature foe)
    {
        System.out.println();
        System.out.println("  You throw a Ball at " + foe.getType() + "...");


        if (player.spendBall() == false)
        {
            System.out.println("  ...but you're out of Balls! Buy more in the Town Square.");
            return false;
        }


        double chance = 0.9;
        String r = foe.getRarity().toLowerCase();


        if (r.equals("uncommon")) chance = 0.7;
        else if (r.equals("rare")) chance = 0.4;
        else if (r.equals("legendary")) chance = 0.15;


        double hpRatio = (double)foe.getHealth() / foe.getMaxHealth();


        if (hpRatio < 0.25) chance = chance + 0.3;
        else if (hpRatio < 0.5) chance = chance + 0.15;


        if (chance > 0.95) chance = 0.95;


        if (Math.random() < chance)
        {
            Creature toAdd = world.makeStartingCopy(foe.getType());
            if (toAdd != null)
            {
                if (player.addToParty(toAdd) == true)
                {
                    game.announceDiscovery(foe.getType());
                    System.out.println("  Gotcha! " + foe.getType() + " was caught!");
                    player.recordMobCaught();
                    game.trackQuestProgress(Quest.TYPE_CATCH, currentBiome, 1);
                    if (foe.getRarity().equalsIgnoreCase("Legendary"))
                        game.trackQuestProgress(Quest.TYPE_CATCH, "Legendary", 1);
                }
                else
                {
                    System.out.println("  You already own that mob. It broke free and fled.");
                }
            }
            return true;
        }


        System.out.println("  Argh! " + foe.getType() + " broke free!");
        return false;
    }


    public boolean tryFlee(Creature mine, Creature foe)
    {
        double chance = 0.5 + (mine.getSpeed() - foe.getSpeed()) * 0.05;
        if (chance < 0.2) chance = 0.2;
        if (chance > 0.9) chance = 0.9;


        if (Math.random() < chance)
        {
            System.out.println("  You got away safely!");
            return true;
        }
        return false;
    }


    public boolean switchDuringBattle(Creature current)
    {
        List<Creature> party = player.getParty();


        if (party.size() <= 1)
        {
            System.out.println("  You have no other mobs to switch to.");
            return false;
        }


        System.out.println();
        System.out.println("  Switch to which mob?");


        for (int i = 0; i < party.size(); i++)
        {
            Creature c = party.get(i);
            String marker = "";
            if (c == current) marker = " *CURRENT*";
            else if (c.isFainted() == true) marker = " (fainted)";


            System.out.println("    " + (i + 1) + ". " + c.getType() + marker);
        }
        System.out.println("    0. Back");


        int pick = game.readIntInRange("  > ", 0, party.size());
        if (pick == 0) return false;


        Creature chosen = party.get(pick - 1);


        if (chosen == current)
        {
            System.out.println("  That mob is already active.");
            return false;
        }
        if (chosen.isFainted() == true)
        {
            System.out.println("  That mob has fainted and can't fight.");
            return false;
        }


        player.setActive(chosen);
        System.out.println("  Go, " + chosen.getType() + "!");
        return true;
    }


    public int xpFor(Creature foe)
    {
        int base = 15;
        String r = foe.getRarity().toLowerCase();


        if (r.equals("uncommon")) base = 30;
        else if (r.equals("rare")) base = 60;
        else if (r.equals("legendary")) base = 150;


        return base * foe.getLevel();
    }


    public void tryAutoEvolve(Creature c)
    {
        int gate = 20 * (c.getEvolution() + 1);


        if (c.canEvolve() == true && c.getLevel() >= gate)
        {
            System.out.println("  " + c.evolve());
            game.trackQuestProgress(Quest.TYPE_EVOLVE, "", c.getEvolution());
        }
    }


    public void checkLevelQuests()
    {
        List<Quest> all = world.getAllQuests();


        for (int i = 0; i < all.size(); i++)
        {
            Quest q = all.get(i);


            if (q.isComplete()) continue;
            if (world.isQuestAvailable(q) == false) continue;
            if (q.getType().equals(Quest.TYPE_LEVEL) == false) continue;


            if (player.getLevel() >= q.getGoal())
            {
                q.setProgress(q.getGoal());
                game.completeQuest(q);
            }
        }
    }


    // friendly mobs in your party give the active fighter little bonuses
    public void giveBuffs(Creature fighter)
    {
        List<Creature> party = player.getParty();


        for (int i = 0; i < party.size(); i++)
        {
            Creature c = party.get(i);


            if (c == fighter) continue;
            if (c.isFriendly() == false) continue;
            if (c.isFainted() == true) continue;


            String type = c.getType();


            if (type.equalsIgnoreCase("Pig")) fighter.boostMaxHealth(5);
            else if (type.equalsIgnoreCase("Cow")) fighter.boostMaxHealth(10);
            else if (type.equalsIgnoreCase("Mooshroom")) fighter.boostMaxHealth(10);
            else if (type.equalsIgnoreCase("Wolf")) fighter.boostSpeed(2);
            else if (type.equalsIgnoreCase("Dire Wolf")) fighter.boostSpeed(4);
            else if (type.equalsIgnoreCase("Panda")) fighter.boostDefense(8);
        }
    }
}

