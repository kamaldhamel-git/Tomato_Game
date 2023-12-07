package Login;

public class UserProfile {

    private String username;
    private int score;

    public UserProfile(String username) {
        this.username = username;
        this.score = 0; // Initialize score to 0
    }

    public String getUsername() {
        return username;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
