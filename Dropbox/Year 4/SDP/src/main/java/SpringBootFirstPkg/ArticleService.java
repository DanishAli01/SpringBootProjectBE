package SpringBootFirstPkg;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class ArticleService {

    List<Article>  articleList = new ArrayList<Article>(Arrays.asList(
            new Article("1","SpringBoot Article X","Whats New"),
            new Article("2","SpringBoot Article U","Whats Old")
    ));

    public List<Article> getArticleList() {
        return articleList;
    }

    public Article getArticle(String id){
        return articleList.stream().filter(t -> t.getId().equals(id)).findFirst().get();
    }

    public void addArticle(Article article){
        articleList.add(article);
    }

    public void updateArticle(Article art,String id) {
        for (int i = 0; i <articleList.size() ; i++) {
            Article article = articleList.get(i);

            if(article.getId().equals(id)){
                articleList.set(i,art);
            }
        }
    }

    public void removeArticle(String id) {
        articleList.removeIf(s->s.getId().equals(id));
    }
}
