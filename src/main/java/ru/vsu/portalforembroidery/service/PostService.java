package ru.vsu.portalforembroidery.service;

import org.springframework.data.domain.Pageable;
import ru.vsu.portalforembroidery.model.dto.LikeDto;
import ru.vsu.portalforembroidery.model.dto.PostDto;
import ru.vsu.portalforembroidery.model.dto.view.PostViewDto;
import ru.vsu.portalforembroidery.model.dto.view.ViewListPage;

import java.util.List;

public interface PostService {

    int createPost(PostDto postDto);

    PostViewDto getPostViewById(int id);

    void updatePostById(int id, PostDto postDto);

    void deletePostById(int id);

    void likePostById(int id, LikeDto likeDto);

    int countLikes(int id);

    ViewListPage<PostViewDto> getViewListPage(String page, String size);

    List<PostViewDto> listPosts(Pageable pageable);

    int numberOfPosts();

}
