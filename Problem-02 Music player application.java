// 2. Problem Statement for the Project: Music player application 

// Develop a music player application that allows users to play, manage, and enjoy their music collection. The music player should provide essential functionalities for organizing and playing music files in various formats. 
// Required Functionalities: 
// Music File Import: Enable users to import music files from their local storage or specified directories into the music player library. The player should support common audio file formats, such as MP3, WAV, and FLAC. 
// Music Playback: Implement the ability to play, pause, resume, and stop music playback. 
// The player should provide controls for adjusting the volume and seeking within the track. 
// Playlist Management: Allow users to create and manage playlists, enabling them to group their favorite songs or create custom collections. Users should be able to add and remove songs from playlists and organize the playlist order. 
// Music Library Organization: Provide features to organize and categorize music files within the player's library. Users should be able to create folders, assign tags or metadata to songs, and search for specific songs or artists. 
// Audio Equalizer: Implement an audio equalizer that allows users to adjust the sound output according to their preferences. The equalizer should provide pre-defined presets and allow users to customize the equalizer settings manually. 
// Shuffle and Repeat: Include options for shuffling the playlist order and repeating individual tracks or the entire playlist. 
// Crossfade: Implement a crossfade feature that smoothly transitions between songs, creating a seamless listening experience. 
// User Interface: Design an intuitive and user-friendly interface that provides easy navigation, displays album art and song information, and includes controls for playback, playlist management, and other functionalities. 
// Metadata Display: Retrieve and display metadata information, such as song title, artist, album, and duration, for each music file in the player's library. 
// File Format Compatibility: Ensure the music player supports a wide range of audio file formats to accommodate different user preferences and file types. 
// Note: This is a high-level overview of the required functionalities for a music player application. Additional features and details can be added based on specific requirements and project scope. 
// 1. Music Playback: Users should be able to play, pause, and stop music playback. 
//   - Output: The music player starts playing the selected song and displays basic playback controls such as play, pause, and stop. 
// 2. Playlist Management: Users should be able to create and manage playlists. 
//   - Output: Users can create a new playlist, add songs to the playlist, and view and modify existing playlists. 
// 3. Music Library Organization: Users should be able to browse and select songs from their music library. 
//   - Output: The music player displays a list of available songs in the library. Users can select a song to play. 
// 4. Basic Navigation: Users should be able to navigate through the music library and playlists. 
//   - Output: Users can browse through their music library, view songs by artist, album, or genre, and switch between different playlists. 
// 5. Audio Control: Users should be able to adjust the volume of the music playback. 
//   - Output: Users can increase or decrease the volume of the music player, and the output audio volume changes accordingly. 
// The expected output for these minimum features includes a functional music player interface with basic playback controls, the ability to create and manage playlists, the ability to browse and select 
// songs, basic navigation features, and audio control capabilities. The player should provide a seamless experience for users to play and manage their music collection with ease.

import javax.sound.sampled.*;
import java.io.File;
import java.util.*;

public class Java_Project_2 {

    static class Playlist {
        String name;
        List<String> songs = new ArrayList<>();

        Playlist(String name) {
            this.name = name;
        }
    }

    private static Map<String, Playlist> playlists = new HashMap<>();
    private static Playlist currentPlaylist = null;

    private static Clip clip;
    private static FloatControl volumeControl;
    private static int currentIndex = 0;
    private static boolean repeat = false;
    private static boolean shuffle = false;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("\n==== MUSIC PLAYER ====");
            System.out.println("1. Create Playlist");
            System.out.println("2. Select Playlist");
            System.out.println("3. Add Song (WAV)");
            System.out.println("4. View Songs");
            System.out.println("5. Play Song");
            System.out.println("6. Pause");
            System.out.println("7. Resume");
            System.out.println("8. Stop");
            System.out.println("9. Next");
            System.out.println("10. Previous");
            System.out.println("11. Volume");
            System.out.println("12. Toggle Shuffle");
            System.out.println("13. Toggle Repeat");
            System.out.println("0. Exit");

            System.out.print("Choose: ");
            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1: createPlaylist(sc); break;
                case 2: selectPlaylist(sc); break;
                case 3: addSong(sc); break;
                case 4: viewSongs(); break;
                case 5: playSong(sc); break;
                case 6: pauseSong(); break;
                case 7: resumeSong(); break;
                case 8: stopSong(); break;
                case 9: nextSong(); break;
                case 10: prevSong(); break;
                case 11: setVolume(sc); break;
                case 12: toggleShuffle(); break;
                case 13: toggleRepeat(); break;
                case 0:
                    System.out.println("Exiting...");
                    System.exit(0);
                default:
                    System.out.println("Invalid choice!");
            }
        }
    }

    // CREATE PLAYLIST
    private static void createPlaylist(Scanner sc) {
        System.out.print("Enter playlist name: ");
        String name = sc.nextLine();
        playlists.put(name, new Playlist(name));
        System.out.println("Playlist created!");
    }

    // SELECT PLAYLIST
    private static void selectPlaylist(Scanner sc) {
        System.out.print("Enter playlist name: ");
        String name = sc.nextLine();

        if (playlists.containsKey(name)) {
            currentPlaylist = playlists.get(name);
            System.out.println("Playlist selected!");
        } else {
            System.out.println("Playlist not found!");
        }
    }

    // ADD SONG
    private static void addSong(Scanner sc) {
        if (currentPlaylist == null) {
            System.out.println("Select playlist first!");
            return;
        }

        System.out.print("Enter WAV file path: ");
        String path = sc.nextLine();

        File file = new File(path);
        if (file.exists()) {
            currentPlaylist.songs.add(path);
            System.out.println("Song added!");
        } else {
            System.out.println("File not found!");
        }
    }

    // VIEW SONGS
    private static void viewSongs() {
        if (currentPlaylist == null) {
            System.out.println("Select playlist first!");
            return;
        }

        if (currentPlaylist.songs.isEmpty()) {
            System.out.println("No songs in playlist!");
            return;
        }

        System.out.println("\n--- Songs ---");
        for (int i = 0; i < currentPlaylist.songs.size(); i++) {
            String name = new File(currentPlaylist.songs.get(i)).getName();
            System.out.println(i + " -> " + name);
        }
    }

    // PLAY SONG
    private static void playSong(Scanner sc) {
        try {
            if (currentPlaylist == null || currentPlaylist.songs.isEmpty()) {
                System.out.println("No playlist/songs available!");
                return;
            }

            System.out.print("Enter song index: ");
            int index = sc.nextInt();

            if (index < 0 || index >= currentPlaylist.songs.size()) {
                System.out.println("Invalid index!");
                return;
            }

            currentIndex = index;
            playCurrent();

        } catch (Exception e) {
            System.out.println("Error playing song!");
        }
    }

    // PLAY CURRENT SONG
    private static void playCurrent() {
        try {
            stopSong();

            String path = currentPlaylist.songs.get(currentIndex);
            File file = new File(path);

            AudioInputStream audio = AudioSystem.getAudioInputStream(file);
            clip = AudioSystem.getClip();
            clip.open(audio);
            clip.start();

            volumeControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);

            System.out.println("Playing: " + file.getName());

            if (repeat) {
                clip.loop(Clip.LOOP_CONTINUOUSLY);
            }

        } catch (Exception e) {
            System.out.println("Playback error!");
        }
    }

    // PAUSE
    private static void pauseSong() {
        if (clip != null && clip.isRunning()) {
            clip.stop();
            System.out.println("Paused");
        }
    }

    // RESUME
    private static void resumeSong() {
        if (clip != null) {
            clip.start();
            System.out.println("Resumed");
        }
    }

    // STOP
    private static void stopSong() {
        if (clip != null) {
            clip.stop();
            clip.close();
        }
    }

    // NEXT SONG
    private static void nextSong() {
        if (currentPlaylist == null || currentPlaylist.songs.isEmpty()) return;

        if (shuffle) {
            currentIndex = new Random().nextInt(currentPlaylist.songs.size());
        } else {
            currentIndex = (currentIndex + 1) % currentPlaylist.songs.size();
        }

        playCurrent();
    }

    // PREVIOUS SONG
    private static void prevSong() {
        if (currentPlaylist == null || currentPlaylist.songs.isEmpty()) return;

        currentIndex = (currentIndex - 1 + currentPlaylist.songs.size()) % currentPlaylist.songs.size();
        playCurrent();
    }

    // VOLUME CONTROL
    private static void setVolume(Scanner sc) {
        if (volumeControl == null) {
            System.out.println("Play a song first!");
            return;
        }

        System.out.print("Enter volume (-80 to 6): ");
        float v = sc.nextFloat();

        try {
            volumeControl.setValue(v);
            System.out.println("Volume updated!");
        } catch (Exception e) {
            System.out.println("Invalid volume range!");
        }
    }

    // TOGGLE SHUFFLE
    private static void toggleShuffle() {
        shuffle = !shuffle;
        System.out.println("Shuffle: " + shuffle);
    }

    // TOGGLE REPEAT
    private static void toggleRepeat() {
        repeat = !repeat;
        System.out.println("Repeat: " + repeat);
    }
}

