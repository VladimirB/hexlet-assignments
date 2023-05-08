package exercise.controller;

import exercise.dto.ArticleDto;
import exercise.dto.CategoryDto;
import exercise.model.Article;
import exercise.model.Category;
import exercise.repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/articles")
public class ArticlesController {

    @Autowired
    private ArticleRepository articleRepository;

    @GetMapping
    public Iterable<Article> getArticles() {
        return this.articleRepository.findAll();
    }

    @DeleteMapping(path = "/{id}")
    public void deleteArticle(@PathVariable long id) {
        this.articleRepository.deleteById(id);
    }

    @PostMapping
    public String createArticle(@RequestBody ArticleDto articleDto) {
        var category = new Category();
        category.setId(articleDto.category().id());

        var article = new Article();
        article.setName(articleDto.name());
        article.setCategory(category);
        article.setBody(articleDto.body());

        articleRepository.save(article);
        return "OK";
    }

    @GetMapping("/{id}")
    public ArticleDto getArticleById(@PathVariable("id") long id) {
        var article = articleRepository.findById(id);

        var categoryDto = new CategoryDto(article.getCategory().getId(), article.getCategory().getName());
        return new ArticleDto(article.getName(), article.getBody(), categoryDto);
    }

    @PatchMapping("/{id}")
    public String updateArticle(@PathVariable("id") long articleId, @RequestBody ArticleDto articleDto) {
        var article = articleRepository.findById(articleId);

        article.setName(articleDto.name());
        article.setBody(articleDto.body());

        articleRepository.save(article);
        return "OK";
    }
}
