package macbeth;

import twitter4j.Query;
import twitter4j.Twitter;
import com.google.gson.Gson;
import twitter4j.conf.ConfigurationBuilder;
import twitter4j.TwitterFactory;
import twitter4j.TwitterObjectFactory;
import java.util.Map;
import java.util.TreeMap;
import twitter4j.TwitterException;
import twitter4j.QueryResult;
import twitter4j.Status;

/**
 * TweetLoader will connect to Twitter and allow the user to load tweets.
 *
 * @author Chad Macbeth (CS246 Prove-03)
 */
public class TweetLoader {
    private Twitter twitter;

    /**
     * Create TweetLoader Object.
     */
    public TweetLoader() {
        configureKeys();
    }

    /**
     * Create Twitter Object based on secret keys.
     */
    private void configureKeys() {
        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setOAuthConsumerKey("7muZzPTxWy6ifDnzGJt89gAOe")
                .setOAuthConsumerSecret("4YQVbDcmChjPw0uqcCOYCTNMoRfehfPwp0vVSBqXPTeT1Br4xN")
                .setOAuthAccessToken("814198345682079744-ohZPBAc9S2KUAcQXJSnbT5rd8SGDI7e")
                .setOAuthAccessTokenSecret("eT0UIO2xlQ9twZcgBWoz0Ur33yDFQFSprgRJjI4pPu9ln")
                .setJSONStoreEnabled(true);
        TwitterFactory tf = new TwitterFactory(cb.build());
        this.twitter = tf.getInstance();
    }

    public Map<String,BYUITweet> retrieveTweetsWithHashTag(String hashTag) {
        // TreeMap will keep data in order by the key.
        Map<String,BYUITweet> tweets = new TreeMap<String,BYUITweet>();

        // Create a Twitter Search Query to look for hashtag
        // This will only return recent tweets with the hashtag.
        Query query = new Query(hashTag);
        Gson gson = new Gson();
        try {
            QueryResult results = twitter.search(query);

            // Iterate through all twitter results
            for (Status tweet : results.getTweets()) {
                // Convert Status object (ie, the tweet) to JSON format
                String tweetJSON = TwitterObjectFactory.getRawJSON(tweet);
                System.out.println(tweetJSON);
                // Convert JSON format to BYUITweet object
                BYUITweet byuiTweet = gson.fromJson(tweetJSON, BYUITweet.class);
                // Add BYUITweet to map based on twitter user name
                tweets.put(byuiTweet.getUser().getName(),byuiTweet);
            }
            return tweets;
        }
        catch (TwitterException te)
        {
            System.out.println("Error searching tweets.");
            return null;
        }
    }
}
