package src;

import org.testng.annotations.Test;

import org.testng.Assert;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Map;
import java.util.HashMap;

public class RecentSongsStoreTest {
    @Test
    public void testRecentSongsStore() {
        RecentSongsStore store = new RecentSongsStore(3);

        store.addSong("S1", "User1");
        store.addSong("S2", "User1");
        store.addSong("S3", "User1");

        LinkedList<String> recentSongs = store.getRecentSongs("User1");
        Assert.assertEquals(recentSongs, new LinkedList<>(Arrays.asList("S1", "S2", "S3")));

        store.addSong("S4", "User1");

        recentSongs = store.getRecentSongs("User1");
        Assert.assertEquals(recentSongs, new LinkedList<>(Arrays.asList("S2", "S3", "S4")));

        store.addSong("S2", "User1");

        recentSongs = store.getRecentSongs("User1");
        Assert.assertEquals(recentSongs, new LinkedList<>(Arrays.asList("S3", "S4", "S2")));

        store.addSong("S1", "User1");

        recentSongs = store.getRecentSongs("User1");
        Assert.assertEquals(recentSongs, new LinkedList<>(Arrays.asList("S4", "S2", "S1")));
    }

    static class RecentSongsStore {
        private final int capacity;
        private final Map<String, LinkedList<String>> songMap;
        private final Map<String, Integer> countMap;

        public RecentSongsStore(int capacity) {
            this.capacity = capacity;
            this.songMap = new HashMap<>();
            this.countMap = new HashMap<>();
        }

        public void addSong(String song, String user) {
            LinkedList<String> songs = songMap.getOrDefault(user, new LinkedList<>());
            Integer count = countMap.getOrDefault(user, 0);

            songs.remove(song);
            songs.addFirst(song);

            count++;
            countMap.put(user, count);

            if (songs.size() > capacity) {
                String removedSong = songs.removeLast();
                System.out.println("Removed song: " + removedSong);
            }

            songMap.put(user, songs);
        }

        public LinkedList<String> getRecentSongs(String user) {
            return songMap.getOrDefault(user, new LinkedList<>());
        }
    }
}

