package me.whiteship.refactoring._01_smell_mysterious_name._01_before;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.kohsuke.github.GHIssue;
import org.kohsuke.github.GHIssueComment;
import org.kohsuke.github.GHRepository;
import org.kohsuke.github.GitHub;

public class StudyDashboard {

    private final Set<String> usernames = new HashSet<>();

    private final Set<String> reviews = new HashSet<>();

    public static void main(String[] args) throws IOException {
        GitHub gitHub = GitHub.connect();
        GHRepository repository = gitHub.getRepository("whiteship/live-study");
        GHIssue issue = repository.getIssue(30);

        StudyDashboard studyDashboard = new StudyDashboard();
        studyDashboard.studyReviews(issue);
        studyDashboard.getUsernames().forEach(System.out::println);
        studyDashboard.getReviews().forEach(System.out::println);
    }

    private void studyReviews(GHIssue issue) throws IOException {
        List<GHIssueComment> comments = issue.getComments();
        for (GHIssueComment comment : comments) {
            usernames.add(comment.getUserName());
            reviews.add(comment.getBody());
        }
    }

    public Set<String> getUsernames() {
        return usernames;
    }

    public Set<String> getReviews() {
        return reviews;
    }
}
