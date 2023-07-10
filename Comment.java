import com.sun.source.tree.CaseTree;

import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.CancellationException;

public class Comment {
    public int id;
    public Food food;
    public User user;
    public Restaurant restaurant;
    public Comment replyComment;
    public int rate;
    public String Comment;
    public Date addingTime;
    public static Comment currentComment = null;
    public Comment(int id, int userId, int foodId, int restaurantId, int replyId, int rate, String comment, long addingTime) {
        this.id = id;
        this.user = User.getUserById(userId);
        this.food = (foodId > 0) ? Food.getFood(foodId) : null;
        this.restaurant = (restaurantId > 0) ? Restaurant.getRestaurant(restaurantId) : null;
        this.rate = rate;
        this.Comment = comment;
        this.addingTime = new Date(addingTime);
    }
    public static Comment getComment(int id) {
        return Main.sql.getComment(id, "id", false).get(0);
    }
    public static void addComment(int userId, int foodId, int restaurantId, int replyId, int rate, String comment, long addingTime) {
        Main.sql.insertToComment(restaurantId, foodId, userId, comment, rate, replyId, addingTime);
    }
    public static boolean deleteComment(int id) {
        if (getComment(id) != null)
            Main.sql.deleteFromComment(id, "id");
        return false;
    }
    public static boolean setCurrentComment(int id) {
        Comment comment = getComment(id);
        if (comment != null) {
            currentComment = new Comment(id, comment.user.id, (comment.food != null) ? comment.food.id : 0, (comment.restaurant != null) ? comment.restaurant.id : 0, (comment.replyComment != null) ? comment.replyComment.id : 0, comment.rate, comment.Comment, comment.addingTime.getTime());
            return true;
        }
        return false;
    }
    public static boolean editComment(int id, int data1, String data2) {
        Comment comment = getComment(id);
        if (comment != null && comment.user.id == User.currentUser.id) {
            Main.sql.editComment(id, data2, data1, new Date().getTime());
            return true;
        }
        return false;
    }
    public static void printComment(ArrayList<Comment> ratings, ArrayList<Comment> comments, String topic, boolean isForFood) {
        String leftAlignFormat1 = "| %-4d | %-6d | %-17s | %-4d |%n";
        String leftAlignHeaderFormat1 = "| %-4s | %-6s | %-17s | %-4s |%n";
        String leftAlignFormat2 = "| %-4d | %-6d | %-17s | %-127s | %-14d |%n";
        String leftAlignHeaderFormat2 = "| %-4s | %-6s | %-17s | %-127s | %-14s |%n";
        String dashedLine1 = "--------------------------------------------";
        String dashedLine2 = "----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------";
        System.out.println("topic:");
        System.out.println(dashedLine1);
        System.out.format(leftAlignHeaderFormat1," Id","UserId" ,"   addingTime","rate");
        System.out.println (dashedLine1);
        int sum = 0;
        for (Comment rating : ratings) {
            System.out.format(leftAlignFormat1, rating.id, rating.user.id, Functions.simpleDateFormat.format(rating.addingTime), rating.rate);
            sum += rating.rate;
        }
        System.out.println(dashedLine1);
        System.out.println("the average of ratings is: " + (double) ((int) ((double) sum / ratings.size() * 100)) / 100);
        System.out.println(dashedLine2);
        System.out.format(leftAlignHeaderFormat2," Id","UserId" ,"   addingTime","                                                         comment", "replyToComment");
        System.out.println (dashedLine2);
        for (Comment comment : comments)
            System.out.format(leftAlignFormat2, comment.id, comment.user.id, Functions.simpleDateFormat.format(comment.addingTime), comment.Comment, (comment.replyComment == null) ? "" : comment.replyComment.id);
        System.out.println(dashedLine2);
    }
    public static void printComment(int id, String whichId) {
        ArrayList<Comment>[] comments = sortedComment(Main.sql.getComment(id, whichId, false));
        if (whichId.equals("foodId"))
            printComment(comments[0], comments[1], "this is the feedback from people who have eaten this food", true);
        else if (whichId.equals("restaurantId")) {
            printComment(comments[0], comments[1], "his is the feedback from people who have received food from this restaurant", false);
        }
    }
    public static ArrayList<Comment>[] sortedComment(ArrayList<Comment> comments) {
        ArrayList<Comment>[] newComments = new ArrayList[2];
        for (int i = 0; i < 2; i++)
            newComments[i] = new ArrayList<>();
        for (Comment comment : comments) {
            if (comment.rate > 0)
                newComments[0].add(comment);
            else if (comment.replyComment == null)
                newComments[1].add(comment);
        }
        for (Comment comment : newComments[1])
            for (Comment reply: comments)
                if (reply.replyComment != null && reply.replyComment.id == comment.id)
                    newComments[1].add(newComments[1].indexOf(comment) + 1, reply);
        return newComments;
    }
    public static boolean isUnique(int userId, int idKey, String whichId) {
        ArrayList<Comment> ratings = Main.sql.getComment(userId, "userId", false);
        switch (whichId) {
            case "foodId" -> {
                for (Comment rate : ratings)
                    if (rate.food.id == idKey)
                        return false;
            }
            case "restaurantId" -> {
                for (Comment rate : ratings)
                    if (rate.restaurant.id == idKey)
                        return false;
            }
        }
        return true;
    }
}
