package macbeth;

import com.google.gson.annotations.SerializedName;

/**
 * Represents a Tweet User.
 * Implements User JSON Object Structure
 * https://developer.twitter.com/en/docs/tweets/data-dictionary/overview/user-object
 *
 * @author Chad Macbeth (CS246 Prove-03)
 */
public class User {
    private String name;
    @SerializedName("followers_count")
    private int followers; // Use the SerializedName above for JSON mapping
    private String location;

    /**
     * Get User name
     *
     * @return - name
     */
    public String getName() {
        return name;
    }

    /**
     * Set the user name
     *
     * @param name - user name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Get follower count.
     *
     * @return - follower count
     */
    public int getFollowers() {
        return followers;
    }

    /**
     * Set the follower count
     *
     * @param followers - follower count
     */
    public void setFollowers(int followers) {
        this.followers = followers;
    }

    public String getLocation() {
        return location;
    }
}
