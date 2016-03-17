package xyz.stavola.release_tracker;

import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.Spark;
import spark.template.handlebars.HandlebarsTemplateEngine;
import xyz.stavola.release_tracker.model.Wrapper;
import xyz.stavola.release_tracker.model.transformed.Release;

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

        Release[] releases = GithubAPI.get().getReleases(org, repo);

        Wrapper wrapper = new Wrapper();
        wrapper.releases = releases;

        return new ModelAndView(wrapper, "releases.hbs");
    }
}
