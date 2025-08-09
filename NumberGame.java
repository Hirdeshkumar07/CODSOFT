
import java.util.Random;
import java.util.Scanner;

public class NumberGame {
    @SuppressWarnings("ConvertToTryWithResources")
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Random random = new Random();

        int roundsPlayed = 0;
        int roundsWon = 0;
        int totalAttempts = 0;

        String playAgain;
        System.out.println("\n\n****** Number Guess Game ******");

        do {
            int orignal = random.nextInt(100) + 1;
            int maxAttempts = 8;
            int attempts = 0;
            boolean guessed = false;

            System.out.println("\n Computer have selected a number between 1 and 100.");
            System.out.println("You have " + maxAttempts + " attempts to guess it.");

            while (attempts < maxAttempts) {
                System.out.print("Attempt " + (attempts + 1) + ": Enter your guess: ");
                int guess = sc.nextInt();
                attempts++;

                if (guess < orignal) {
                    if(guess<orignal-5){
                        if(guess<orignal-10){
                            System.out.println("Too low!");
                        }
                        else{
                            System.out.println("Low");
                        }
                    }
                    else{
                        System.out.println("Close low");
                    }
                } else if (guess > orignal) {
                    if(guess>orignal+5){
                        if(guess>orignal+10){
                            System.out.println("Too high!");
                        }
                        else{
                            System.out.println("High");
                        }
                    }
                    else{
                        System.out.println("Close High");
                    }
                } else {
                    System.out.println(" Correct! You guessed the number in " + attempts + " attempts.");
                    guessed = true;
                    roundsWon++;
                    break;
                }
            }

            if (!guessed) {
                System.out.println("!! You ran out of attempts. The number was: " + orignal);
            }

            roundsPlayed++;
            totalAttempts += attempts;
            //asking to play agian
            System.out.print("\nDo you want to play another round? (yes/no): ");
            sc.nextLine();
            playAgain = sc.nextLine().trim().toLowerCase();

        } while (playAgain.equals("yes") || playAgain.equals("y"));

        System.out.println("\n****** Game Summary ******");
        System.out.println("Rounds played: " + roundsPlayed);
        System.out.println("Rounds won: " + roundsWon);
        System.out.println("Total attempts: " + totalAttempts);
        System.out.println("Thanks for playing!");
        sc.close();
        System.exit(0);
    }
}
