import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    static ArrayList<String> songs = new ArrayList<>();
    static ArrayList<String> playlist = new ArrayList<>();

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        // Sample Songs
        songs.add("Believer - Imagine Dragons");
        songs.add("Shape of You - Ed Sheeran");
        songs.add("Perfect - Ed Sheeran");

        int choice;

        do {

            System.out.println("\n====== MUSIC PLAYER ======");

            System.out.println("1. View Songs");
            System.out.println("2. Create Playlist");
            System.out.println("3. Add Song to Playlist");
            System.out.println("4. View Playlist");
            System.out.println("5. Remove Song from Playlist");
            System.out.println("6. Play Song");
            System.out.println("7. Pause Song");
            System.out.println("8. Stop Song");
            System.out.println("9. Next Song");
            System.out.println("0. Exit");

            System.out.print("Enter Choice: ");
            choice = sc.nextInt();
            sc.nextLine();

            switch(choice) {

                case 1:
                    System.out.println("\n--- SONG LIST ---");

                    for(int i = 0; i < songs.size(); i++) {
                        System.out.println((i + 1) + ". " + songs.get(i));
                    }
                    break;

                case 2:
                    System.out.print("Enter Playlist Name: ");
                    String playlistName = sc.nextLine();

                    System.out.println("Playlist Created: " + playlistName);
                    break;

                case 3:

                    System.out.println("\nSelect Song Number:");

                    for(int i = 0; i < songs.size(); i++) {
                        System.out.println((i + 1) + ". " + songs.get(i));
                    }

                    int songChoice = sc.nextInt();

                    if(songChoice > 0 && songChoice <= songs.size()) {

                        String selectedSong = songs.get(songChoice - 1);

                        playlist.add(selectedSong);

                        System.out.println("Song Added Successfully");
                    }
                    else {
                        System.out.println("Invalid Choice");
                    }

                    break;

                case 4:

                    System.out.println("\n--- PLAYLIST SONGS ---");

                    if(playlist.isEmpty()) {
                        System.out.println("Playlist Empty");
                    }
                    else {

                        for(int i = 0; i < playlist.size(); i++) {
                            System.out.println((i + 1) + ". " + playlist.get(i));
                        }
                    }

                    break;

                case 5:

                    if(playlist.isEmpty()) {
                        System.out.println("Playlist Empty");
                    }
                    else {

                        System.out.println("\nSelect Song to Remove:");

                        for(int i = 0; i < playlist.size(); i++) {
                            System.out.println((i + 1) + ". " + playlist.get(i));
                        }

                        int removeChoice = sc.nextInt();

                        if(removeChoice > 0 && removeChoice <= playlist.size()) {

                            String removedSong =
                                    playlist.remove(removeChoice - 1);

                            System.out.println(
                                    removedSong + " Removed Successfully"
                            );
                        }
                        else {
                            System.out.println("Invalid Choice");
                        }
                    }

                    break;

                case 6:

                    if(playlist.isEmpty()) {
                        System.out.println("No Songs in Playlist");
                    }
                    else {

                        System.out.println("\nSelect Song to Play:");

                        for(int i = 0; i < playlist.size(); i++) {
                            System.out.println((i + 1) + ". " + playlist.get(i));
                        }

                        int playChoice = sc.nextInt();

                        if(playChoice > 0 && playChoice <= playlist.size()) {

                            System.out.println(
                                    "Now Playing: " +
                                    playlist.get(playChoice - 1)
                            );
                        }
                        else {
                            System.out.println("Invalid Choice");
                        }
                    }

                    break;

                case 7:
                    System.out.println("Song Paused");
                    break;

                case 8:
                    System.out.println("Playback Stopped");
                    break;

                case 9:
                    System.out.println("Playing Next Song");
                    break;

                case 0:
                    System.out.println("Exiting Music Player...");
                    break;

                default:
                    System.out.println("Invalid Choice");
            }

        } while(choice != 0);

        sc.close();
    }
}
