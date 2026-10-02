import java.util.Scanner;


public class Main
{
    public static void main(String[] args)
    {
        Scanner input = new Scanner(System.in);
        World world = new World();
        Game game = new Game(input, world);


        game.start();


        input.close();
    }
}



