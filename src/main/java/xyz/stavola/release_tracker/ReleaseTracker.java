package xyz.stavola.release_tracker;

import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.Spark;
import spark.template.handlebars.HandlebarsTemplateEngine;
import xyz.stavola.release_tracker.model.Release;

import java.util.List;

public class ReleaseTracker {
    public static void main(String[] args) {
        int portNumber = Integer.parseInt(System.getenv("PORT"));

        Spark.port(portNumber);

        Spark.get("/", (request, response) -> {
            response.redirect("/mbStavola/ReleaseTracker");
            return null;
        });

        Spark.get("/:org/:repo", ReleaseTracker::fetchAndRender, new HandlebarsTemplateEngine());
    }

    public static ModelAndView fetchAndRender(Request request, Response response) {
        String org = request.params("org");
        String repo = request.params("repo");

        List<Release> releases = GithubAPI.get().getReleases(org, repo);


        //This should render the model
        return new ModelAndView(releases, "releases.hbs");
    }
}
