package SpringBootFirstPkg;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
public class ArticleController {
    @Autowired
    private ArticleService articleService;

    @RequestMapping("Articles")
    public List<Article> getAllArticle() {
        return articleService.getArticleList();
    }

    @RequestMapping("Home")
    public String Home() {
        return " Welcome Home !!!";
    }

    @RequestMapping("/Articles/{id}")
    public Article getArticle(@PathVariable String id){
        return articleService.getArticle(id);
    }
    @RequestMapping(method = RequestMethod.POST, value = "/Articles")
    public void addArticle(@RequestBody Article article ) {
        articleService.addArticle(article);
    }
    @RequestMapping(method = RequestMethod.PUT, value = "/Articles/{id}")
    public void updateArticle(@RequestBody Article article,@PathVariable String id){
        articleService.updateArticle(article ,id);

    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/Articles/{id}")
    public void updateArticle(@PathVariable String id){
        articleService.removeArticle(id);

    }
}
