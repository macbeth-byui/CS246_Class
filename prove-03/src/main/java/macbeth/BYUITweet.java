package macbeth;

/**
 * Represents a Tweet.
 * Implements Tweet JSON Object Structure
 * https://developer.twitter.com/en/docs/tweets/data-dictionary/overview/tweet-object
 *
 * @author Chad Macbeth (CS246 Prove-03)
 */
public class BYUITweet {
    private User user;
    private String text;

    /**
     * Get User object
     *
     * @return - user
     */
    public User getUser() {
        return user;
    }

    /**
     * Set User object
     *
     * @param user - new user
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * Get Tweet Text
     *
     * @return - text
     */
    public String getText() {
        return text;
    }

    /**
     * Set Tweet Text
     *
     * @param text - new text
     */
    public void setText(String text) {
        this.text = text;
    }
}
