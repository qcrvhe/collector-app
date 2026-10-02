import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


public class World
{
    private String[] biomes;
    private ArrayList<Creature> allMobs;
    private Map<String, ArrayList<String>> biomeMobs;
    private ArrayList<String> discoveredTypes;


    private Map<String, String> evolutionTree;
    private Map<String, String[]> biomeEffects;
    private ArrayList<Quest> quests;


    public World()
    {
        biomes = new String[]
                {
                        "Grasslands", "Desert", "Badlands", "Snowy Plains",
                        "Forest", "Jungle", "Swamp", "Mountains", "Nether", "The End"
                };


        allMobs = new ArrayList<Creature>();
        biomeMobs = new LinkedHashMap<String, ArrayList<String>>();
        discoveredTypes = new ArrayList<String>();
        evolutionTree = new HashMap<String, String>();
        biomeEffects = new HashMap<String, String[]>();
        quests = new ArrayList<Quest>();


        buildMobIndex();
        buildBiomeMap();
        buildEvolutionTree();
        buildBiomeEffects();
        buildQuests();
    }


    private void buildMobIndex()
    {
        // commons
        addMob("Skeleton", 1, 1, 100, 10, 30, false, "Common");
        addMob("Zombie", 1, 1, 90, 10, 23, false, "Common");
        addMob("Spider", 1, 2, 80, 14, 18, false, "Common");
        addMob("Slime", 1, 1, 60, 7, 10, false, "Common");
        addMob("Goat", 1, 2, 100, 9, 25, false, "Common");
        addMob("Zombified Piglin", 1, 2, 100, 9, 25, false, "Common");


        addMob("Pig", 1, 1, 40, 5, 15, true, "Common");
        addMob("Cow", 1, 1, 50, 4, 12, true, "Common");
        addMob("Chicken", 1, 1, 25, 6, 8, true, "Common");
        addMob("Sheep", 1, 1, 45, 5, 10, true, "Common");
        addMob("Rabbit", 1, 1, 25, 12, 5, true, "Common");
        addMob("Camel", 1, 3, 90, 6, 18, true, "Common");
        addMob("Armadillo", 1, 1, 50, 7, 20, true, "Common");
        addMob("Frog", 1, 1, 30, 10, 6, true, "Common");


        // uncommons
        addMob("Creeper", 1, 2, 70, 8, 10, false, "Uncommon");
        addMob("Cave Spider", 1, 2, 60, 15, 12, false, "Uncommon");
        addMob("Silverfish", 1, 1, 30, 12, 8, false, "Uncommon");
        addMob("Husk", 1, 3, 130, 11, 28, false, "Uncommon");
        addMob("Stray", 1, 4, 140, 13, 32, false, "Uncommon");
        addMob("Magma Cube", 1, 4, 120, 9, 14, false, "Uncommon");


        addMob("Wolf", 1, 3, 110, 16, 20, true, "Uncommon");
        addMob("Squid", 1, 2, 55, 6, 6, true, "Uncommon");
        addMob("Cat", 1, 2, 40, 14, 8, true, "Uncommon");
        addMob("Parrot", 1, 2, 30, 16, 5, true, "Uncommon");
        addMob("Ocelot", 1, 2, 45, 15, 8, true, "Uncommon");


        // rares
        addMob("Blaze", 1, 1, 140, 11, 40, false, "Rare");
        addMob("Enderman", 2, 5, 130, 15, 20, false, "Rare");
        addMob("Witch", 2, 6, 120, 9, 18, false, "Rare");
        addMob("Guardian", 2, 4, 150, 8, 35, false, "Rare");
        addMob("Phantom", 2, 4, 110, 20, 12, false, "Rare");
        addMob("Polar Bear", 2, 5, 160, 10, 25, false, "Rare");
        addMob("Ghast", 1, 4, 120, 8, 20, false, "Rare");
        addMob("Shulker", 1, 3, 140, 6, 40, false, "Rare");
        addMob("Piglin", 2, 4, 120, 12, 25, false, "Rare");
        addMob("Drowned", 3, 6, 170, 13, 30, false, "Rare");
        addMob("Charged Creeper", 2, 5, 140, 10, 15, false, "Rare");


        addMob("Fox", 1, 3, 60, 17, 10, true, "Rare");
        addMob("Panda", 1, 4, 90, 6, 14, true, "Rare");
        addMob("Dire Wolf", 2, 5, 200, 18, 25, true, "Rare");
        addMob("Mooshroom", 2, 4, 110, 6, 20, true, "Rare");


        // legendaries
        addMob("Ender Dragon", 3, 10, 500, 20, 60, false, "Legendary");
        addMob("Wither", 3, 8, 450, 18, 55, false, "Legendary");
        addMob("Elder Guardian", 3, 7, 380, 12, 50, false, "Legendary");
        addMob("Warden", 3, 9, 500, 22, 65, false, "Legendary");
        addMob("Wither Skeleton", 2, 5, 180, 14, 30, false, "Legendary");
        addMob("Piglin Brute", 3, 7, 200, 14, 35, false, "Legendary");
    }


    private void addMob(String type, int evo, int age, int hp,
                        int spd, int def, boolean friendly, String rarity)
    {
        Creature c = new Creature(type, evo, age, hp, spd, def, friendly, rarity);
        allMobs.add(c);
    }


    public boolean removeMobFromIndex(String type)
    {
        if (type == null)
        {
            return false;
        }


        for (int i = 0; i < allMobs.size(); i++)
        {
            Creature c = allMobs.get(i);
            if (c.getType().equalsIgnoreCase(type))
            {
                allMobs.remove(i);
                return true;
            }
        }


        return false;
    }


    private void buildBiomeMap()
    {
        addBiomeMobs("Grasslands",
                "Zombie", "Skeleton", "Spider", "Creeper",
                "Pig", "Cow", "Sheep", "Chicken",
                "Wolf", "Cat", "Parrot");


        addBiomeMobs("Forest",
                "Zombie", "Skeleton", "Spider", "Creeper",
                "Pig", "Cow", "Chicken",
                "Wolf", "Fox", "Enderman", "Cat", "Parrot");


        addBiomeMobs("Snowy Plains",
                "Zombie", "Skeleton", "Spider", "Creeper",
                "Stray", "Polar Bear", "Fox", "Wolf", "Goat", "Sheep");


        addBiomeMobs("Desert",
                "Zombie", "Husk", "Skeleton", "Spider", "Creeper", "Silverfish",
                "Rabbit", "Camel");


        addBiomeMobs("Badlands",
                "Zombie", "Skeleton", "Spider", "Creeper", "Silverfish",
                "Goat", "Armadillo");


        addBiomeMobs("Mountains",
                "Zombie", "Skeleton", "Spider", "Creeper", "Silverfish",
                "Stray", "Goat", "Wolf", "Sheep");


        addBiomeMobs("Jungle",
                "Zombie", "Skeleton", "Spider", "Creeper",
                "Enderman", "Panda", "Parrot", "Ocelot", "Cat");


        addBiomeMobs("Swamp",
                "Zombie", "Drowned", "Skeleton", "Spider", "Creeper", "Slime",
                "Witch", "Squid", "Frog");


        addBiomeMobs("Nether",
                "Zombified Piglin", "Blaze", "Wither Skeleton",
                "Magma Cube", "Ghast", "Piglin",
                "Wither");


        addBiomeMobs("The End",
                "Enderman", "Shulker",
                "Ender Dragon", "Warden");
    }


    private void addBiomeMobs(String biome, String... mobTypes)
    {
        ArrayList<String> list = new ArrayList<String>();


        for (int i = 0; i < mobTypes.length; i++)
        {
            list.add(mobTypes[i]);
        }


        biomeMobs.put(biome, list);
    }


    public boolean removeMobFromBiome(String biome, String mobType)
    {
        if (biome == null || mobType == null)
        {
            return false;
        }


        ArrayList<String> list = biomeMobs.get(biome);


        if (list == null)
        {
            return false;
        }


        return list.remove(mobType);
    }


    private void buildEvolutionTree()
    {
        evolutionTree.put("Pig",      "Piglin");
        evolutionTree.put("Piglin",   "Piglin Brute");
        evolutionTree.put("Zombie",   "Husk");
        evolutionTree.put("Husk",     "Drowned");
        evolutionTree.put("Skeleton", "Stray");
        evolutionTree.put("Stray",    "Wither Skeleton");
        evolutionTree.put("Cow",      "Mooshroom");
        evolutionTree.put("Wolf",     "Dire Wolf");
        evolutionTree.put("Creeper",  "Charged Creeper");
        evolutionTree.put("Spider",   "Cave Spider");
        evolutionTree.put("Slime",    "Magma Cube");
    }


    private void buildBiomeEffects()
    {
        biomeEffects.put("Grasslands", new String[]{"SPD", "+2", "FRIENDLY"});
        biomeEffects.put("Desert", new String[]{"HP",  "-1", "ALL"});
        biomeEffects.put("Badlands", new String[]{"DEF", "-1", "ALL"});
        biomeEffects.put("Snowy Plains", new String[]{"SPD", "-2", "ALL"});
        biomeEffects.put("Forest", new String[]{"HP",  "+1", "FRIENDLY"});
        biomeEffects.put("Jungle", new String[]{"DEF", "+2", "Creeper"});
        biomeEffects.put("Swamp", new String[]{"HP",  "-1", "ALL"});
        biomeEffects.put("Mountains", new String[]{"SPD", "+2", "Phantom"});
        biomeEffects.put("Nether", new String[]{"DMG", "+20", "FIRE"});
        biomeEffects.put("The End", new String[]{"HP",  "-1", "ALL"});
    }


    private void buildQuests()
    {
        addQuest("catch_forest_3", "Catch 3 mobs from the Forest",
                Quest.TYPE_CATCH, "Forest", 3, "",
                Quest.REWARD_XP, "300");
        addQuest("catch_nether_5", "Catch 5 mobs from the Nether",
                Quest.TYPE_CATCH, "Nether", 5, "",
                Quest.REWARD_BALLS, "5");
        addQuest("catch_legendary", "Catch any Legendary mob",
                Quest.TYPE_CATCH, "Legendary", 1, "",
                Quest.REWARD_TITLE, "Legend Hunter");


        addQuest("beat_creeper_5", "Defeat 5 Creepers",
                Quest.TYPE_DEFEAT, "Creeper", 5, "",
                Quest.REWARD_XP, "250");
        addQuest("beat_legendary_3", "Defeat 3 Legendary mobs",
                Quest.TYPE_DEFEAT, "Legendary", 3, "beat_creeper_5",
                Quest.REWARD_TITLE, "Legend Slayer");


        addQuest("discover_10", "Discover 10 mob types",
                Quest.TYPE_DISCOVER, "", 10, "",
                Quest.REWARD_BALLS, "3");
        addQuest("discover_20", "Discover 20 mob types",
                Quest.TYPE_DISCOVER, "", 20, "discover_10",
                Quest.REWARD_TITLE, "Beast Scholar");


        addQuest("beat_blake", "Defeat rival Blake",
                Quest.TYPE_RIVAL, "Blake", 1, "",
                Quest.REWARD_LEVEL, "1");
        addQuest("beat_rowan", "Defeat rival Rowan",
                Quest.TYPE_RIVAL, "Rowan", 1, "beat_blake",
                Quest.REWARD_BALLS, "5");
        addQuest("beat_wren", "Defeat rival Wren",
                Quest.TYPE_RIVAL, "Wren", 1, "beat_rowan",
                Quest.REWARD_TITLE, "Champion");


        addQuest("evolve_once", "Evolve any mob to Evo 2",
                Quest.TYPE_EVOLVE, "", 1, "",
                Quest.REWARD_XP, "500");
        addQuest("evolve_three", "Evolve any mob to Evo 3",
                Quest.TYPE_EVOLVE, "", 3, "evolve_once",
                Quest.REWARD_TITLE, "Evolution Master");


        addQuest("travel_5_biomes", "Visit 5 different biomes",
                Quest.TYPE_TRAVEL, "", 5, "",
                Quest.REWARD_BALLS, "5");
        addQuest("travel_all_10",    "Visit all 10 biomes",
                Quest.TYPE_TRAVEL, "", 10, "travel_5_biomes",
                Quest.REWARD_TITLE, "World Wanderer");


        addQuest("level_5", "Reach Trainer Level 5",
                Quest.TYPE_LEVEL, "", 5, "",
                Quest.REWARD_XP, "300");
        addQuest("level_10", "Reach Trainer Level 10",
                Quest.TYPE_LEVEL, "", 10, "level_5",
                Quest.REWARD_TITLE, "Veteran Trainer");
    }


    private void addQuest(String id, String desc, String type, String target,
                          int goal, String prereq, String rewardType, String rewardData)
    {
        Quest q = new Quest(id, desc, type, target, goal, prereq, rewardType, rewardData);
        quests.add(q);
    }


    public boolean removeQuest(String id)
    {
        if (id == null)
        {
            return false;
        }


        for (int i = 0; i < quests.size(); i++)
        {
            Quest q = quests.get(i);
            if (q.getId().equals(id))
            {
                quests.remove(i);
                return true;
            }
        }


        return false;
    }


    public List<Quest> getAllQuests()
    {
        return Collections.unmodifiableList(quests);
    }




    public Quest getQuest(String id)
    {
        if (id == null)
        {
            return null;
        }


        for (int i = 0; i < quests.size(); i++)
        {
            Quest q = quests.get(i);
            if (q.getId().equals(id))
            {
                return q;
            }
        }
        return null;
    }


    public boolean isQuestAvailable(Quest q)
    {
        if (q == null || q.isComplete())
        {
            return false;
        }


        if (!q.hasPrerequisite())
        {
            return true;
        }


        Quest pre = getQuest(q.getPrerequisite());


        if (pre != null && pre.isComplete())
        {
            return true;
        }


        return false;
    }


    public List<Quest> getAvailableQuests()
    {
        ArrayList<Quest> out = new ArrayList<Quest>();


        for (int i = 0; i < quests.size(); i++)
        {
            Quest q = quests.get(i);
            if (isQuestAvailable(q))
            {
                out.add(q);
            }
        }


        return Collections.unmodifiableList(out);
    }


    public List<Creature> getAllMobs()
    {
        return Collections.unmodifiableList(allMobs);
    }




    public Creature getMobByType(String type)
    {
        if (type == null)
        {
            return null;
        }


        for (int i = 0; i < allMobs.size(); i++)
        {
            Creature c = allMobs.get(i);
            if (c.getType().equalsIgnoreCase(type))
            {
                return c;
            }
        }


        return null;
    }


    // Returns a fresh copy of the matching mob, or null if none.
    public Creature getMobCopyByType(String type)
    {
        Creature base = getMobByType(type);


        if (base == null)
        {
            return null;
        }


        return base.copy();
    }


    // Returns a fresh copy with its evolution target set, or null if none.
    public Creature makeStartingCopy(String type)
    {
        Creature base = getMobCopyByType(type);


        if (base == null)
        {
            return null;
        }


        String next = evolutionTree.get(base.getType());
        base.setEvolutionTarget(next);


        return base;
    }


    public String getEvolutionTarget(String type)
    {
        return evolutionTree.get(type);
    }


    public String[] getBiomeEffect(String biome)
    {
        return biomeEffects.get(biome);
    }




    public String[] getBiomes()
    {
        String[] copy = new String[biomes.length];
        for (int i = 0; i < biomes.length; i++)
        {
            copy[i] = biomes[i];
        }
        return copy;
    }


    public boolean isValidBiome(String biome)
    {
        if (biome == null)
        {
            return false;
        }


        for (int i = 0; i < biomes.length; i++)
        {
            if (biomes[i].equalsIgnoreCase(biome))
            {
                return true;
            }
        }


        return false;
    }




    public List<String> getMobsInBiome(String biome)
    {
        ArrayList<String> list = biomeMobs.get(biome);


        if (list == null)
        {
            return new ArrayList<String>();
        }


        return new ArrayList<String>(list);
    }


    public boolean discover(String type)
    {
        if (type == null)
        {
            return false;
        }


        String key = type.toLowerCase();


        if (discoveredTypes.contains(key))
        {
            return false;
        }


        discoveredTypes.add(key);
        return true;
    }


    public boolean undiscover(String type)
    {
        if (type == null)
        {
            return false;
        }


        String key = type.toLowerCase();


        return discoveredTypes.remove(key);
    }


    public boolean isDiscovered(String type)
    {
        if (type == null)
        {
            return false;
        }


        return discoveredTypes.contains(type.toLowerCase());
    }


    public int getDiscoveredCount()
    {
        return discoveredTypes.size();
    }


    public int getTotalMobCount()
    {
        return allMobs.size();
    }


    public String[] getShopItems()
    {
        String[] items = new String[]
                {
                        "Ball (5 coins)",
                        "Ball x5 (20 coins)",
                        "Ball x20 (60 coins)"
                };
        return items;
    }


    public int getShopPrice(int index)
    {
        if (index == 0)
        {
            return 5;
        }
        if (index == 1)
        {
            return 20;
        }
        if (index == 2)
        {
            return 60;
        }
        return -1;
    }


    public int getShopBallAmount(int index)
    {
        if (index == 0)
        {
            return 1;
        }
        if (index == 1)
        {
            return 5;
        }
        if (index == 2)
        {
            return 20;
        }
        return 0;
    }


    public int getUnlockLevel(String biome)
    {
        if (biome == null)
        {
            return 99;
        }


        if (biome.equalsIgnoreCase("Grasslands"))
        {
            return 1;
        }
        if (biome.equalsIgnoreCase("Forest"))
        {
            return 1;
        }
        if (biome.equalsIgnoreCase("Snowy Plains"))
        {
            return 1;
        }
        if (biome.equalsIgnoreCase("Desert"))
        {
            return 3;
        }
        if (biome.equalsIgnoreCase("Mountains"))
        {
            return 4;
        }
        if (biome.equalsIgnoreCase("Badlands"))
        {
            return 5;
        }
        if (biome.equalsIgnoreCase("Jungle"))
        {
            return 6;
        }
        if (biome.equalsIgnoreCase("Swamp"))
        {
            return 7;
        }
        if (biome.equalsIgnoreCase("Nether"))
        {
            return 8;
        }
        if (biome.equalsIgnoreCase("The End"))
        {
            return 10;
        }


        return 99;
    }


    public boolean isUnlocked(String biome, int playerLevel)
    {
        if (playerLevel >= getUnlockLevel(biome))
        {
            return true;
        }
        return false;
    }


    public int getRivalUnlockLevel(String name)
    {
        if (name == null)
        {
            return 99;
        }


        if (name.equalsIgnoreCase("Blake"))
        {
            return 3;
        }
        if (name.equalsIgnoreCase("Rowan"))
        {
            return 6;
        }
        if (name.equalsIgnoreCase("Wren"))
        {
            return 10;
        }


        return 99;
    }


    public Creature getRandomMobFromBiome(String biome, int playerLevel)
    {
        ArrayList<String> pool = biomeMobs.get(biome);


        if (pool == null || pool.isEmpty())
        {
            return null;
        }


        ArrayList<String> commons = new ArrayList<String>();
        ArrayList<String> uncommons = new ArrayList<String>();
        ArrayList<String> rares = new ArrayList<String>();
        ArrayList<String> legendaries = new ArrayList<String>();


        for (int i = 0; i < pool.size(); i++)
        {
            Creature template = getMobByType(pool.get(i));


            if (template == null)
            {
                continue;
            }


            String r = template.getRarity().toLowerCase();


            if (r.equals("common"))
            {
                commons.add(pool.get(i));
            }
            else if (r.equals("uncommon"))
            {
                uncommons.add(pool.get(i));
            }
            else if (r.equals("rare"))
            {
                rares.add(pool.get(i));
            }
            else if (r.equals("legendary"))
            {
                legendaries.add(pool.get(i));
            }
        }


        boolean legendaryAllowed = playerLevel >= 8;


        double roll = Math.random();
        String chosen = null;


        if (legendaryAllowed && roll < 0.01 && !legendaries.isEmpty())
        {
            chosen = legendaries.get((int)(Math.random() * legendaries.size()));
        }
        else if (roll < 0.08 && !rares.isEmpty())
        {
            chosen = rares.get((int)(Math.random() * rares.size()));
        }
        else if (roll < 0.30 && !uncommons.isEmpty())
        {
            chosen = uncommons.get((int) (Math.random() * uncommons.size()));
        }
        else if (!commons.isEmpty())
        {
            chosen = commons.get((int)(Math.random() * commons.size()));
        }
        else if (!uncommons.isEmpty())
        {
            chosen = uncommons.get((int)(Math.random() * uncommons.size()));
        }
        else if (!rares.isEmpty())
        {
            chosen = rares.get((int)(Math.random() * rares.size()));
        }
        else if (!legendaries.isEmpty())
        {
            chosen = legendaries.get((int)(Math.random() * legendaries.size()));
        }


        if (chosen == null)
        {
            return null;
        }


        Creature c = getMobCopyByType(chosen);


        if (c != null)
        {
            c.setEvolutionTarget(getEvolutionTarget(chosen));
        }


        return c;
    }
}

