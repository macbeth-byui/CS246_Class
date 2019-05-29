package macbeth;

import java.util.*;

/**
 * Test the TweetLoader Class
 *
 * @author Chad Macbeth (CS246 Prove-03)
 */
public class Main {

    public static void main(String[] args) {
	    TweetLoader tweetLoader = new TweetLoader();

	    // Search for #byui tweets and display all of them to the screen.
	    Map<String,BYUITweet> tweets = tweetLoader.retrieveTweetsWithHashTag("@MarsCuriosity");

        if (tweets != null) {
            List<BYUITweet> sortedTweets = new ArrayList<BYUITweet>(tweets.values());
            Collections.sort(sortedTweets, new Comparator<BYUITweet>() {
                public int compare(BYUITweet o1, BYUITweet o2) {
                    return 0;
                }
            });
            Collections.sort(sortedTweets, new Comparator<BYUITweet>(){
                public int compare(BYUITweet o1, BYUITweet o2) {
                    return o2.getUser().getFollowers() - o1.getUser().getFollowers();
                }
            });
            // The tweets map contains all the tweets sorted by key (username)
            for (BYUITweet tweet : sortedTweets) {
                System.out.format("%s (%d Followers, %s) - %s%n",
                        tweet.getUser().getName(),
                        tweet.getUser().getFollowers(),
                        tweet.getUser().getLocation(),
                        tweet.getText());
            }
        }
    }
}
