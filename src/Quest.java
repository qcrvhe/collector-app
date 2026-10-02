

public class Quest
{
    public static final String TYPE_CATCH = "CATCH";
    public static final String TYPE_DEFEAT = "DEFEAT";
    public static final String TYPE_DISCOVER = "DISCOVER";
    public static final String TYPE_RIVAL = "RIVAL";
    public static final String TYPE_EVOLVE = "EVOLVE";
    public static final String TYPE_TRAVEL = "TRAVEL";
    public static final String TYPE_LEVEL = "LEVEL";


    public static final String REWARD_XP = "XP";
    public static final String REWARD_LEVEL = "LEVEL";
    public static final String REWARD_BALLS = "BALLS";
    public static final String REWARD_TITLE = "TITLE";
    public static final String REWARD_UNLOCK = "UNLOCK";


    private String id;
    private String description;
    private String type;
    private String target;
    private int goal;
    private String prerequisite;
    private String rewardType;
    private String rewardData;


    private int progress;
    private boolean complete;


    public Quest(String id, String description, String type, String target,
                 int goal, String prerequisite,
                 String rewardType, String rewardData)
    {
        if (id == null || id.length() == 0)
        {
            throw new IllegalArgumentException("Quest id required");
        }
        if (description == null || description.length() == 0)
        {
            throw new IllegalArgumentException("Quest description required");
        }
        if (type == null || type.length() == 0)
        {
            throw new IllegalArgumentException("Quest type required");
        }
        if (goal <= 0)
        {
            throw new IllegalArgumentException("Quest goal must be positive");
        }


        this.id = id;
        this.description = description;
        this.type = type;


        if (target == null)
        {
            this.target = "";
        }
        else
        {
            this.target = target;
        }


        this.goal = goal;


        if (prerequisite == null)
        {
            this.prerequisite = "";
        }
        else
        {
            this.prerequisite = prerequisite;
        }


        this.rewardType = rewardType;
        this.rewardData = rewardData;
        this.progress = 0;
        this.complete = false;
    }


    public String getId()
    {
        return id;
    }


    public String getDescription()
    {
        return description;
    }


    public String getType()
    {
        return type;
    }


    public String getTarget()
    {
        return target;
    }


    public int getGoal()
    {
        return goal;
    }


    public String getPrerequisite()
    {
        return prerequisite;
    }


    public String getRewardType()
    {
        return rewardType;
    }


    public String getRewardData()
    {
        return rewardData;
    }


    public int getProgress()
    {
        return progress;
    }


    public boolean isComplete()
    {
        return complete;
    }


    public boolean hasPrerequisite()
    {
        return !prerequisite.isEmpty();
    }


    public boolean isReady()
    {
        if (!complete && progress >= goal)
        {
            return true;
        }
        return false;
    }


    public void setProgress(int p)
    {
        if (p < 0)
        {
            p = 0;
        }
        if (p > goal)
        {
            p = goal;
        }
        this.progress = p;
    }


    public void increment(int n)
    {
        setProgress(progress + n);
    }


    public void markComplete()
    {
        this.complete = true;
        this.progress = goal;
    }


    public String progressBar()
    {
        String bar = "[" + progress + "/" + goal + "] ";


        int filled = 0;
        if (goal != 0)
        {
            filled = (int)((progress * 10.0) / goal);
        }


        for (int i = 0; i < 10; i++)
        {
            if (i < filled)
            {
                bar = bar + "#";
            }
            else
            {
                bar = bar + ".";
            }
        }


        return bar;
    }

// adding a comment
    public String toString()
    {
        String status = "";
        if (complete)
        {
            status = "[DONE] ";
        }
        else if (isReady())
        {
            status = "[READY] ";
        }
        return status + description + " " + progressBar();
    }
}

