public class Creature
{
    private String type;
    private int level;
    private int xp;
    private int evolution;
    private int age;
    private int hearts;
    private int maxHearts;
    private int speed;
    private int defense;
    private boolean friendly;
    private String rarity;


    private String evolutionTarget;
    private String signatureMove;
    private int regenPerRound;


    private int decayRounds;
    private int halfHealRounds;


    private String passiveBuff;


    private String[] moves;
    private int guardBonus;
    private int flinchRounds;
    private int burnRounds;


    public Creature(String type, int evolution, int age, int hearts,
                    int speed, int defense, boolean friendly, String rarity)
    {
        this(type, evolution, age, hearts, speed, defense, friendly, rarity, 1);
    }


    public Creature(String type, int evolution, int age, int hearts,
                    int speed, int defense, boolean friendly, String rarity,
                    int level)
    {
        if (type == null || type.isEmpty())
        {
            throw new IllegalArgumentException("Creature type required");
        }
        if (rarity == null || rarity.length() == 0)
        {
            throw new IllegalArgumentException("Creature rarity required");
        }
        if (hearts <= 0)
        {
            throw new IllegalArgumentException("Creature hearts must be positive");
        }


        this.type = type;
        this.evolution = evolution;
        this.age = age;
        this.hearts = hearts;
        this.maxHearts = hearts;
        this.speed = speed;
        this.defense = defense;
        this.friendly = friendly;
        this.rarity = rarity;


        if (level < 1)
        {
            this.level = 1;
        }
        else
        {
            this.level = level;
        }


        this.xp = 0;
        this.regenPerRound = 0;
        this.decayRounds = 0;
        this.halfHealRounds = 0;
        this.passiveBuff = "";
        this.guardBonus = 0;
        this.flinchRounds = 0;
        this.burnRounds = 0;


        if (rarity.equalsIgnoreCase("Legendary"))
        {
            this.regenPerRound = 5;
        }


        if (type.equalsIgnoreCase("Ender Dragon"))
        {
            this.signatureMove = "Void Breath";
        }
        else if (type.equalsIgnoreCase("Wither"))
        {
            this.signatureMove = "Decay";
        }
        else if (type.equalsIgnoreCase("Elder Guardian"))
        {
            this.signatureMove = "Curse";
        }
        else if (type.equalsIgnoreCase("Warden"))
        {
            this.signatureMove = "Sonic Boom";
        }
        else if (type.equalsIgnoreCase("Wither Skeleton"))
        {
            this.signatureMove = "Withering";
        }


        assignPassiveBuff();
        assignMoves();
    }


    private void assignPassiveBuff()
    {
        if (friendly == false)
        {
            this.passiveBuff = "";
            return;
        }


        if (type.equalsIgnoreCase("Pig"))
        {
            this.passiveBuff = "+5 HP regen per round to your fighter";
        }
        else if (type.equalsIgnoreCase("Cow"))
        {
            this.passiveBuff = "+10 max HP to your fighter";
        }
        else if (type.equalsIgnoreCase("Wolf"))
        {
            this.passiveBuff = "+2 SPD to your fighter";
        }
        else if (type.equalsIgnoreCase("Cat"))
        {
            this.passiveBuff = "+5% dodge chance for your fighter";
        }
        else if (type.equalsIgnoreCase("Parrot"))
        {
            this.passiveBuff = "Reveals one extra mob per Explore";
        }
        else if (type.equalsIgnoreCase("Fox"))
        {
            this.passiveBuff = "+15% catch rate in this biome";
        }
        else if (type.equalsIgnoreCase("Chicken"))
        {
            this.passiveBuff = "+5% XP gain";
        }
        else if (type.equalsIgnoreCase("Sheep"))
        {
            this.passiveBuff = "+5% XP gain";
        }
        else if (type.equalsIgnoreCase("Squid"))
        {
            this.passiveBuff = "+5% XP gain";
        }
        else if (type.equalsIgnoreCase("Panda"))
        {
            this.passiveBuff = "+8 DEF to your fighter";
        }
        else if (type.equalsIgnoreCase("Mooshroom"))
        {
            this.passiveBuff = "+10 max HP to your fighter";
        }
        else if (type.equalsIgnoreCase("Dire Wolf"))
        {
            this.passiveBuff = "+4 SPD to your fighter";
        }
        else
        {
            this.passiveBuff = "Support companion";
        }
    }


    private void assignMoves()
    {
        if (type.equalsIgnoreCase("Zombie"))
        {
            moves = new String[]{"Tackle", "Bite", "Harden"};
        }
        else if (type.equalsIgnoreCase("Skeleton"))
        {
            moves = new String[]{"Tackle", "Bone Shot", "Guard"};
        }
        else if (type.equalsIgnoreCase("Spider"))
        {
            moves = new String[]{"Bite", "Web", "Sting"};
        }
        else if (type.equalsIgnoreCase("Slime"))
        {
            moves = new String[]{"Tackle", "Bounce", "Harden"};
        }
        else if (type.equalsIgnoreCase("Goat"))
        {
            moves = new String[]{"Headbutt", "Tackle", "Harden"};
        }
        else if (type.equalsIgnoreCase("Zombified Piglin"))
        {
            moves = new String[]{"Tackle", "Bash", "Guard"};
        }
        else if (type.equalsIgnoreCase("Pig"))
        {
            moves = new String[]{"Tackle", "Roll", "Guard"};
        }
        else if (type.equalsIgnoreCase("Cow"))
        {
            moves = new String[]{"Tackle", "Stomp", "Harden"};
        }
        else if (type.equalsIgnoreCase("Chicken"))
        {
            moves = new String[]{"Peck", "Tackle", "Screech"};
        }
        else if (type.equalsIgnoreCase("Sheep"))
        {
            moves = new String[]{"Tackle", "Ram", "Harden"};
        }
        else if (type.equalsIgnoreCase("Rabbit"))
        {
            moves = new String[]{"Peck", "Pounce", "Guard"};
        }
        else if (type.equalsIgnoreCase("Camel"))
        {
            moves = new String[]{"Tackle", "Spit", "Harden"};
        }
        else if (type.equalsIgnoreCase("Armadillo"))
        {
            moves = new String[]{"Tackle", "Roll", "Harden"};
        }
        else if (type.equalsIgnoreCase("Frog"))
        {
            moves = new String[]{"Tackle", "Tongue Lash", "Guard"};
        }
        else if (type.equalsIgnoreCase("Creeper"))
        {
            moves = new String[]{"Tackle", "Boom", "Guard"};
        }
        else if (type.equalsIgnoreCase("Cave Spider"))
        {
            moves = new String[]{"Bite", "Web", "Poison Fang"};
        }
        else if (type.equalsIgnoreCase("Silverfish"))
        {
            moves = new String[]{"Bite", "Swarm", "Harden"};
        }
        else if (type.equalsIgnoreCase("Husk"))
        {
            moves = new String[]{"Tackle", "Bite", "Harden"};
        }
        else if (type.equalsIgnoreCase("Stray"))
        {
            moves = new String[]{"Tackle", "Frost Shot", "Guard"};
        }
        else if (type.equalsIgnoreCase("Magma Cube"))
        {
            moves = new String[]{"Tackle", "Ember", "Harden"};
        }
        else if (type.equalsIgnoreCase("Wolf"))
        {
            moves = new String[]{"Bite", "Howl", "Lunge"};
        }
        else if (type.equalsIgnoreCase("Squid"))
        {
            moves = new String[]{"Tackle", "Ink Spray", "Guard"};
        }
        else if (type.equalsIgnoreCase("Cat"))
        {
            moves = new String[]{"Bite", "Pounce", "Screech"};
        }
        else if (type.equalsIgnoreCase("Parrot"))
        {
            moves = new String[]{"Peck", "Screech", "Guard"};
        }
        else if (type.equalsIgnoreCase("Ocelot"))
        {
            moves = new String[]{"Bite", "Pounce", "Screech"};
        }
        else if (type.equalsIgnoreCase("Blaze"))
        {
            moves = new String[]{"Ember", "Blaze Burst", "Guard"};
        }
        else if (type.equalsIgnoreCase("Enderman"))
        {
            moves = new String[]{"Tackle", "Void Slash", "Screech"};
        }
        else if (type.equalsIgnoreCase("Witch"))
        {
            moves = new String[]{"Tackle", "Hex", "Drain"};
        }
        else if (type.equalsIgnoreCase("Guardian"))
        {
            moves = new String[]{"Tackle", "Beam", "Harden"};
        }
        else if (type.equalsIgnoreCase("Phantom"))
        {
            moves = new String[]{"Bite", "Screech", "Pounce"};
        }
        else if (type.equalsIgnoreCase("Polar Bear"))
        {
            moves = new String[]{"Bash", "Tackle", "Harden"};
        }
        else if (type.equalsIgnoreCase("Ghast"))
        {
            moves = new String[]{"Fireball", "Screech", "Guard"};
        }
        else if (type.equalsIgnoreCase("Shulker"))
        {
            moves = new String[]{"Tackle", "Bullet", "Harden"};
        }
        else if (type.equalsIgnoreCase("Piglin"))
        {
            moves = new String[]{"Tackle", "Slash", "Bash"};
        }
        else if (type.equalsIgnoreCase("Drowned"))
        {
            moves = new String[]{"Tackle", "Bite", "Drain"};
        }
        else if (type.equalsIgnoreCase("Charged Creeper"))
        {
            moves = new String[]{"Tackle", "Boom", "Screech"};
        }
        else if (type.equalsIgnoreCase("Fox"))
        {
            moves = new String[]{"Bite", "Pounce", "Screech"};
        }
        else if (type.equalsIgnoreCase("Panda"))
        {
            moves = new String[]{"Bash", "Roll", "Harden"};
        }
        else if (type.equalsIgnoreCase("Dire Wolf"))
        {
            moves = new String[]{"Bite", "Howl", "Lunge"};
        }
        else if (type.equalsIgnoreCase("Mooshroom"))
        {
            moves = new String[]{"Tackle", "Ram", "Harden"};
        }
        else if (type.equalsIgnoreCase("Ender Dragon"))
        {
            moves = new String[]{"Void Slash", "Sonic Screech", "Evolve Burst"};
        }
        else if (type.equalsIgnoreCase("Wither"))
        {
            moves = new String[]{"Decay", "Bash", "Drain"};
        }
        else if (type.equalsIgnoreCase("Elder Guardian"))
        {
            moves = new String[]{"Beam", "Hex", "Harden"};
        }
        else if (type.equalsIgnoreCase("Warden"))
        {
            moves = new String[]{"Sonic Screech", "Bash", "Harden"};
        }
        else if (type.equalsIgnoreCase("Wither Skeleton"))
        {
            moves = new String[]{"Slash", "Withering", "Bash"};
        }
        else if (type.equalsIgnoreCase("Piglin Brute"))
        {
            moves = new String[]{"Slash", "Bash", "Howl"};
        }
        else
        {
            moves = new String[]{"Tackle", "Slash", "Guard"};
        }
    }


    public String getType()
    {
        return type;
    }


    public int getLevel()
    {
        return level;
    }


    public int getXp()
    {
        return xp;
    }


    public int getXpToNext()
    {
        return level * 50;
    }


    public int getHealth()
    {
        return hearts;
    }


    public int getMaxHealth()
    {
        return maxHearts;
    }


    public int getAge()
    {
        return age;
    }


    public int getSpeed()
    {
        return speed;
    }


    public int getDefense()
    {
        return defense;
    }


    public int getEvolution()
    {
        return evolution;
    }


    public boolean isFriendly()
    {
        return friendly;
    }


    public String getRarity()
    {
        return rarity;
    }


    public String getSignatureMove()
    {
        return signatureMove;
    }


    public int getRegenPerRound()
    {
        return regenPerRound;
    }


    public String getEvolutionTarget()
    {
        return evolutionTarget;
    }


    public String getPassiveBuff()
    {
        return passiveBuff;
    }


    /**
     * Returns a COPY of the internal moves array.
     * Callers may not mutate the creature's real moveset.
     */
    public String[] getMoves()
    {
        String[] copy = new String[moves.length];
        for (int i = 0; i < moves.length; i++)
        {
            copy[i] = moves[i];
        }
        return copy;
    }


    public String getMoveName(int index)
    {
        if (index < 0 || index >= moves.length)
        {
            return "";
        }
        return moves[index];
    }


    public int getMoveCount()
    {
        return moves.length;
    }


    public boolean isFainted()
    {
        if (hearts <= 0)
        {
            return true;
        }
        return false;
    }


    public boolean canAttack()
    {
        if (friendly == false)
        {
            return true;
        }
        return false;
    }


    public boolean isFullHealth()
    {
        if (hearts == maxHearts)
        {
            return true;
        }
        return false;
    }


    public boolean hasSignature()
    {
        if (signatureMove != null)
        {
            return true;
        }
        return false;
    }


    public boolean regenerates()
    {
        if (regenPerRound > 0)
        {
            return true;
        }
        return false;
    }


    public boolean canFlee()
    {
        if (rarity.equalsIgnoreCase("Common"))
        {
            return true;
        }
        return false;
    }


    public boolean canEvolve()
    {
        if (evolutionTarget != null && evolutionTarget.length() > 0)
        {
            return true;
        }
        return false;
    }


    public void setHealth(int h)
    {
        if (h < 0)
        {
            h = 0;
        }
        if (h > maxHearts)
        {
            h = maxHearts;
        }
        this.hearts = h;
    }


    public void setType(String t)
    {
        if (t != null && t.length() > 0)
        {
            this.type = t;
        }
    }


    public void setEvolutionTarget(String t)
    {
        this.evolutionTarget = t;
    }


    public void boostMaxHealth(int amount)
    {
        if (amount > 0)
        {
            maxHearts = maxHearts + amount;
            hearts = hearts + amount;
        }
    }


    public void boostSpeed(int amount)
    {
        if (amount > 0)
        {
            speed = speed + amount;
        }
    }


    public void boostDefense(int amount)
    {
        if (amount > 0)
        {
            defense = defense + amount;
        }
    }


    public void reduceSpeed(int amount)
    {
        if (amount > 0)
        {
            speed = speed - amount;
            if (speed < 1)
            {
                speed = 1;
            }
        }
    }


    public void decayRounds(int n)
    {
        if (n > decayRounds)
        {
            decayRounds = n;
        }
    }


    public void halveHealing(int n)
    {
        if (n > halfHealRounds)
        {
            halfHealRounds = n;
        }
    }


    public void halveSpeed()
    {
        speed = speed / 2;
        if (speed < 1)
        {
            speed = 1;
        }
    }


    public void setGuardBonus(int n)
    {
        if (n > guardBonus)
        {
            guardBonus = n;
        }
    }


    public int getGuardBonus()
    {
        return guardBonus;
    }


    public void clearGuardBonus()
    {
        guardBonus = 0;
    }


    public void applyFlinch()
    {
        flinchRounds = 1;
    }


    public boolean isFlinched()
    {
        if (flinchRounds > 0)
        {
            return true;
        }
        return false;
    }


    public void clearFlinch()
    {
        if (flinchRounds > 0)
        {
            flinchRounds = flinchRounds - 1;
        }
    }


    public void applyBurn()
    {
        burnRounds = 3;
    }


    public boolean isBurned()
    {
        if (burnRounds > 0)
        {
            return true;
        }
        return false;
    }


    public void tickBurn()
    {
        if (burnRounds > 0)
        {
            takeDamage(3);
            burnRounds = burnRounds - 1;
        }
    }


    public int getBurnRounds()
    {
        return burnRounds;
    }


    public String gainXp(int amount)
    {
        if (amount <= 0)
        {
            return "";
        }


        xp = xp + amount;
        String log = "";


        while (xp >= getXpToNext())
        {
            xp = xp - getXpToNext();
            level = level + 1;


            maxHearts = maxHearts + 8;
            hearts = hearts + 8;
            if (hearts > maxHearts)
            {
                hearts = maxHearts;
            }
            speed = speed + 1;
            defense = defense + 2;


            log = log + "\n    " + type + " reached Lv." + level + "!";
        }


        return log;
    }


    public String birthday()
    {
        age = age + 1;
        return type + " is now " + age + " years old.";
    }


    public String evolve()
    {
        if (canEvolve() == false)
        {
            return type + " cannot evolve any further.";
        }


        int requiredLevel = 20 * (evolution + 1);


        if (level < requiredLevel)
        {
            return type + " needs to reach Lv." + requiredLevel + " to evolve.";
        }


        evolution = evolution + 1;
        maxHearts = maxHearts + 30;
        hearts = maxHearts;
        speed = speed + 3;
        defense = defense + 5;


        String oldType = type;
        type = evolutionTarget;


        assignPassiveBuff();
        assignMoves();


        return oldType + " evolved into " + type + "! (Evo " + evolution + ", HP " + hearts + ")";
    }


    public void takeDamage(int amount)
    {
        if (amount < 0)
        {
            amount = 0;
        }
        setHealth(hearts - amount);
    }


    public void heal(int amount)
    {
        if (amount < 0)
        {
            amount = 0;
        }
        setHealth(hearts + amount);
    }


    public void healChecked(int amount)
    {
        if (halfHealRounds > 0)
        {
            amount = amount / 2;
        }
        heal(amount);
    }


    public void applyRoundRegen()
    {
        if (regenerates() == true && isFainted() == false)
        {
            heal(regenPerRound);
        }
    }


    public String attack(Creature target)
    {
        if (target == null)
        {
            return type + " has no target.";
        }


        if (canAttack() == false)
        {
            return type + " is friendly and can't attack back!";
        }


        int damage = age * 2 + evolution * 3 + level / 4;
        if (damage < 1)
        {
            damage = 1;
        }


        target.takeDamage(damage);


        return type + " hits " + target.getType() + " for " + damage
                + " (target HP: " + target.getHealth() + "/" + target.getMaxHealth() + ")";
    }


    public String useMove(String moveName, Creature target)
    {
        if (target == null)
        {
            return type + " has no target.";
        }
        if (canAttack() == false)
        {
            return type + " is friendly and can't fight back!";
        }


        int baseDamage = age * 2 + evolution * 3 + level / 4;
        if (baseDamage < 1)
        {
            baseDamage = 1;
        }


        int damage = baseDamage;
        String extra = "";


        if (moveName.equals("Tackle"))
        {
            damage = baseDamage;
        }
        else if (moveName.equals("Slash"))
        {
            double acc = Math.random();
            if (acc > 0.85)
            {
                return type + " uses Slash... but it misses!";
            }
            damage = (int)(baseDamage * 1.5);
        }
        else if (moveName.equals("Bite"))
        {
            damage = (int)(baseDamage * 1.2);
            if (Math.random() < 0.10)
            {
                target.applyFlinch();
                extra = " (Flinched!)";
            }
        }
        else if (moveName.equals("Ember"))
        {
            damage = (int)(baseDamage * 1.4);
            target.applyBurn();
            extra = " (Burned!)";
        }
        else if (moveName.equals("Guard"))
        {
            int guard = baseDamage;
            if (guard < 5)
            {
                guard = 5;
            }
            setGuardBonus(guard);
            return type + " raises its guard! (+" + guard + " DEF this round)";
        }
        else if (moveName.equals("Harden"))
        {
            int guard = baseDamage + baseDamage / 2;
            if (guard < 8)
            {
                guard = 8;
            }
            setGuardBonus(guard);
            return type + " hardens! (+" + guard + " DEF this round)";
        }
        else if (moveName.equals("Lunge"))
        {
            damage = (int)(baseDamage * 1.6);
            if (Math.random() < 0.20)
            {
                int recoil = damage / 6;
                if (recoil < 1)
                {
                    recoil = 1;
                }
                takeDamage(recoil);
                extra = " (Recoil: -" + recoil + " HP)";
            }
        }
        else if (moveName.equals("Boom"))
        {
            if (Math.random() < 0.25)
            {
                return type + " uses Boom... but it misses!";
            }
            damage = (int)(baseDamage * 1.8);
        }
        else if (moveName.equals("Headbutt"))
        {
            damage = (int)(baseDamage * 1.4);
            if (Math.random() < 0.30)
            {
                int recoil = damage / 5;
                if (recoil < 1)
                {
                    recoil = 1;
                }
                takeDamage(recoil);
                extra = " (Recoil: -" + recoil + " HP)";
            }
        }
        else if (moveName.equals("Sting"))
        {
            damage = (int)(baseDamage * 1.0);
            target.applyBurn();
            extra = " (Poisoned!)";
        }
        else if (moveName.equals("Poison Fang"))
        {
            damage = (int)(baseDamage * 1.1);
            target.applyBurn();
            extra = " (Poisoned!)";
        }
        else if (moveName.equals("Web"))
        {
            damage = (int)(baseDamage * 0.5);
            target.halveSpeed();
            extra = " (Target's SPD halved!)";
        }
        else if (moveName.equals("Screech"))
        {
            int before = target.getSpeed();
            target.reduceSpeed(2);
            int after = target.getSpeed();
            return type + " screeches! (" + target.getType() + " SPD "
                    + before + " -> " + after + ")";
        }
        else if (moveName.equals("Bash"))
        {
            damage = (int)(baseDamage * 1.2);
            if (Math.random() < 0.15)
            {
                target.applyFlinch();
                extra = " (Flinched!)";
            }
        }
        else if (moveName.equals("Withering"))
        {
            damage = (int)(baseDamage * 1.0);
            target.halveHealing(99);
            extra = " (Healing halved!)";
        }
        else if (moveName.equals("Drain"))
        {
            damage = (int)(baseDamage * 0.9);
            int healAmount = damage / 2;
            if (healAmount < 1)
            {
                healAmount = 1;
            }
            heal(healAmount);
            extra = " (Drained " + healAmount + " HP)";
        }
        else if (moveName.equals("Howl"))
        {
            boostSpeed(3);
            return type + " howls! (+3 SPD)";
        }
        else if (moveName.equals("Roll"))
        {
            damage = (int)(baseDamage * 0.9);
            boostSpeed(1);
            extra = " (+1 SPD)";
        }
        else if (moveName.equals("Bounce"))
        {
            damage = (int)(baseDamage * 1.0);
            boostSpeed(2);
            extra = " (+2 SPD)";
        }
        else if (moveName.equals("Swarm"))
        {
            damage = (int)(baseDamage * 1.0);
            boostSpeed(1);
            extra = " (+1 SPD)";
        }
        else if (moveName.equals("Bone Shot"))
        {
            damage = (int)(baseDamage * 1.2);
        }
        else if (moveName.equals("Peck"))
        {
            damage = (int)(baseDamage * 0.8);
            boostSpeed(1);
            extra = " (+1 SPD)";
        }
        else if (moveName.equals("Pounce"))
        {
            damage = (int)(baseDamage * 1.3);
            boostSpeed(2);
            extra = " (+2 SPD)";
        }
        else if (moveName.equals("Ram"))
        {
            damage = (int)(baseDamage * 1.1);
        }
        else if (moveName.equals("Stomp"))
        {
            damage = (int)(baseDamage * 1.1);
        }
        else if (moveName.equals("Spit"))
        {
            damage = (int)(baseDamage * 0.9);
            target.reduceSpeed(1);
            extra = " (Target -1 SPD)";
        }
        else if (moveName.equals("Tongue Lash"))
        {
            damage = (int)(baseDamage * 1.0);
            if (Math.random() < 0.15)
            {
                target.applyFlinch();
                extra = " (Flinched!)";
            }
        }
        else if (moveName.equals("Frost Shot"))
        {
            damage = (int)(baseDamage * 1.1);
            target.reduceSpeed(2);
            extra = " (Target -2 SPD)";
        }
        else if (moveName.equals("Ink Spray"))
        {
            damage = (int)(baseDamage * 0.6);
            target.halveSpeed();
            extra = " (Target's SPD halved!)";
        }
        else if (moveName.equals("Blaze Burst"))
        {
            damage = (int)(baseDamage * 1.7);
            target.applyBurn();
            extra = " (Burned!)";
        }
        else if (moveName.equals("Void Slash"))
        {
            damage = (int)(baseDamage * 1.5);
            heal(5);
            extra = " (+5 HP)";
        }
        else if (moveName.equals("Hex"))
        {
            damage = (int)(baseDamage * 1.0);
            target.applyBurn();
            extra = " (Cursed!)";
        }
        else if (moveName.equals("Beam"))
        {
            damage = (int)(baseDamage * 1.1);
        }
        else if (moveName.equals("Fireball"))
        {
            damage = (int)(baseDamage * 1.3);
            target.applyBurn();
            extra = " (Burned!)";
        }
        else if (moveName.equals("Bullet"))
        {
            damage = (int)(baseDamage * 1.1);
        }
        else if (moveName.equals("Decay"))
        {
            damage = (int)(baseDamage * 1.0);
            target.decayRounds(99);
            extra = " (Decay!)";
        }
        else if (moveName.equals("Sonic Screech"))
        {
            damage = (int)(baseDamage * 1.0);
            extra = " (ignores DEF)";
        }
        else if (moveName.equals("Evolve Burst"))
        {
            if (hearts > maxHearts * 30 / 100)
            {
                return type + " can only use Evolve Burst when below 30% HP!";
            }
            damage = (int)(baseDamage * 2.2);
            heal(10);
            extra = " (+10 HP)";
        }
        else
        {
            damage = baseDamage;
        }


        int effectiveDamage = damage - target.getGuardBonus();
        if (effectiveDamage < 1)
        {
            effectiveDamage = 1;
        }


        target.takeDamage(effectiveDamage);


        return type + " uses " + moveName + "! " + effectiveDamage + " dmg. (Target HP: "
                + target.getHealth() + "/" + target.getMaxHealth() + ")" + extra;
    }


    public String useSignature(Creature target, int round)
    {
        if (hasSignature() == false || target == null)
        {
            return null;
        }


        if (signatureMove.equals("Void Breath"))
        {
            if (round % 3 != 0)
            {
                return null;
            }
            target.takeDamage(25);
            heal(10);
            return type + " unleashes Void Breath! 25 dmg, heals 10. Target HP: "
                    + target.getHealth() + "/" + target.getMaxHealth();
        }


        if (signatureMove.equals("Decay"))
        {
            if (round != 1)
            {
                return null;
            }
            target.decayRounds(99);
            return type + " inflicts Decay! (Target loses 3 HP/round.)";
        }


        if (signatureMove.equals("Curse"))
        {
            if (round != 1)
            {
                return null;
            }
            target.halveSpeed();
            return type + " curses " + target.getType() + "! (SPD halved.)";
        }


        if (signatureMove.equals("Sonic Boom"))
        {
            if (round % 4 != 0)
            {
                return null;
            }
            int hp = target.getHealth() - 40;
            target.setHealth(hp);
            return type + " fires Sonic Boom! 40 dmg (ignores DEF). Target HP: "
                    + target.getHealth() + "/" + target.getMaxHealth();
        }


        if (signatureMove.equals("Withering"))
        {
            if (round != 1)
            {
                return null;
            }
            target.halveHealing(99);
            return type + " withers " + target.getType() + "! (Healing halved.)";
        }


        return null;
    }


    public String tickStatus()
    {
        String msg = "";


        if (decayRounds > 0)
        {
            takeDamage(3);
            decayRounds = decayRounds - 1;
            msg = msg + "\n    " + type + " suffers Decay (-3 HP).";
        }


        if (halfHealRounds > 0)
        {
            halfHealRounds = halfHealRounds - 1;
        }


        return msg;
    }


    public Creature copy()
    {
        Creature c = new Creature(type, evolution, age, hearts, speed, defense,
                friendly, rarity, level);
        c.xp = this.xp;
        c.maxHearts = this.maxHearts;
        c.evolutionTarget = this.evolutionTarget;
        return c;
    }


    public String toString()
    {
        String friendlyText = "Hostile";
        if (friendly == true)
        {
            friendlyText = "Friendly";
        }


        String line = type + " (Lv." + level + ") - " + rarity
                + " | Evo " + evolution
                + " | HP " + hearts + "/" + maxHearts
                + " | SPD " + speed
                + " | DEF " + defense
                + " | XP " + xp + "/" + getXpToNext()
                + " | " + friendlyText;


        if (passiveBuff.length() > 0)
        {
            line = line + "\n        Buff: " + passiveBuff;
        }


        return line;
    }


    public boolean equals(Object o)
    {
        if (this == o)
        {
            return true;
        }
        if (!(o instanceof Creature))
        {
            return false;
        }


        Creature c = (Creature) o;


        if (type.equalsIgnoreCase(c.type) == false)
        {
            return false;
        }
        if (rarity.equalsIgnoreCase(c.rarity) == false)
        {
            return false;
        }
        if (evolution != c.evolution)
        {
            return false;
        }


        return true;
    }


    public int hashCode()
    {
        return type.toLowerCase().hashCode() + rarity.toLowerCase().hashCode() + evolution;
    }
}



