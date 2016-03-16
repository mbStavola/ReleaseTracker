package xyz.stavola.release_tracker;

import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.Spark;
import spark.template.handlebars.HandlebarsTemplateEngine;

import java.util.List;

public class ReleaseTracker {
    public static void main(String[] args) {
        Spark.get("/", (req, res) -> {
            //This should redirect to /:org/:repo
            return "";
        });

        Spark.get("/:org/:repo", ReleaseTracker::fetchAndRender, new HandlebarsTemplateEngine());

        Spark.post("/releases", ((request, response) -> {
            //This should render the model
            return "";
        }));
    }

    public static ModelAndView fetchAndRender(Request request, Response response) {
        String org = request.params("org");
        String repo = request.params("repo");

        List<ReleaseModel> releases = GithubAPI.get().getReleases(org, repo);


        //This should render the model
        return new ModelAndView(releases, "releases.hbs");
    }
}
