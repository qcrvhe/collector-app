import java.util.ArrayList;
import java.util.Collections;
import java.util.List;




public class Trainer
{
    private String name;
    private String chosenBiome;
    private int level;
    private int coins;
    private int balls;
    private int battlesWon;
    private int mobsCaught;
    private int faintsSuffered;
    private Creature activeCreature;
    private ArrayList<Creature> party;
    private ArrayList<String> titles;


    public Trainer(String name, int level)
    {
        if (name == null || name.length() == 0)
        {
            throw new IllegalArgumentException("Trainer name required");
        }


        this.name = name;
        this.level = level;
        this.chosenBiome = "";
        this.coins = 100;
        this.balls = 5;
        this.battlesWon = 0;
        this.mobsCaught = 0;
        this.faintsSuffered = 0;
        this.party = new ArrayList<Creature>();
        this.titles = new ArrayList<String>();
        this.activeCreature = null;
    }


    public String getName()
    {
        return name;
    }


    public String getChosenBiome()
    {
        return chosenBiome;
    }


    public int getLevel()
    {
        return level;
    }


    public int getCoins()
    {
        return coins;
    }


    public int getBalls()
    {
        return balls;
    }


    public int getBattlesWon()
    {
        return battlesWon;
    }


    public int getMobsCaught()
    {
        return mobsCaught;
    }


    public int getFaintsSuffered()
    {
        return faintsSuffered;
    }


    public Creature getActive()
    {
        return activeCreature;
    }


    public List<Creature> getParty()
    {
        return Collections.unmodifiableList(party);
    }


    public int partySize()
    {
        return party.size();
    }


    public List<String> getTitles()
    {
        return Collections.unmodifiableList(titles);
    }


    public void setChosenBiome(String b)
    {
        if (b != null && !b.isEmpty())
        {
            chosenBiome = b;
        }
    }


    public void setActive(Creature c)
    {
        if (c == null)
        {
            return;
        }
        if (party.contains(c))
        {
            activeCreature = c;
        }
    }


    public void levelUp()
    {
        level = level + 1;
    }


    public void recordBattleWon()
    {
        battlesWon = battlesWon + 1;
    }


    public void recordMobCaught()
    {
        mobsCaught = mobsCaught + 1;
    }


    public void recordFaint()
    {
        faintsSuffered = faintsSuffered + 1;
    }


    public void addCoins(int amount)
    {
        if (amount > 0)
        {
            coins = coins + amount;
        }
    }


    public boolean spendCoins(int amount)
    {
        if (amount <= 0)
        {
            return false;
        }
        if (coins < amount)
        {
            return false;
        }
        coins = coins - amount;
        return true;
    }


    public void addBalls(int amount)
    {
        if (amount > 0)
        {
            balls = balls + amount;
        }
    }


    public boolean spendBall()
    {
        if (balls <= 0)
        {
            return false;
        }
        balls = balls - 1;
        return true;
    }


    public void addTitle(String t)
    {
        if (t != null && t.length() > 0 && titles.contains(t) == false)
        {
            titles.add(t);
        }
    }


    public boolean hasTitle(String t)
    {
        if (titles.contains(t))
        {
            return true;
        }
        return false;
    }


    public boolean addToParty(Creature c)
    {
        if (c == null)
        {
            return false;
        }
        if (party.contains(c))
        {
            return false;
        }


        party.add(c);


        if (activeCreature == null)
        {
            activeCreature = c;
        }


        return true;
    }


    public boolean removeFromParty(Creature c)
    {
        if (c == null)
        {
            return false;
        }
        if (party.remove(c) == false)
        {
            return false;
        }


        if (c == activeCreature)
        {
            if (party.isEmpty())
            {
                activeCreature = null;
            }
            else
            {
                activeCreature = party.get(0);
            }
        }


        return true;
    }




    public Creature findByType(String type)
    {
        if (type == null)
        {
            return null;
        }


        for (int i = 0; i < party.size(); i++)
        {
            Creature c = party.get(i);
            if (c.getType().equalsIgnoreCase(type))
            {
                return c;
            }
        }


        return null;
    }


    public int countFainted()
    {
        int n = 0;


        for (int i = 0; i < party.size(); i++)
        {
            Creature c = party.get(i);
            if (c.isFainted())
            {
                n = n + 1;
            }
        }


        return n;
    }


    public void healAll()
    {
        for (int i = 0; i < party.size(); i++)
        {
            Creature c = party.get(i);
            c.heal(c.getMaxHealth());
        }
    }


    public String toString()
    {
        String biomeText = "not chosen yet";
        if (chosenBiome.length() > 0)
        {
            biomeText = chosenBiome;
        }


        String activeText = "none";
        if (activeCreature != null)
        {
            activeText = activeCreature.getType();
        }


        return name + " (Lvl " + level + ") - Biome: " + biomeText
                + " | Active: " + activeText
                + " | Party: " + party.size()
                + " | Coins: " + coins
                + " | Balls: " + balls;
    }
}




