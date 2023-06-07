import java.util.ArrayList;
public class Comment {
    public int id;
    public int foodId = 0;
    public int userId = 0;
    public int restaurantId = 0;
    public int replyID = 0;
    public int rate;
    public String Comment;
    public int addTime;
    public static Comment currentComment = null;
    public static ArrayList<Comment> currentComments = null;
}
