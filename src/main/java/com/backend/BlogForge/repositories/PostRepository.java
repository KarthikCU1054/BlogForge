package com.backend.BlogForge.repositories;

import com.backend.BlogForge.domain.PostStatus;
import com.backend.BlogForge.domain.dtos.AuthorDto;
import com.backend.BlogForge.domain.entities.Category;
import com.backend.BlogForge.domain.entities.Post;
import com.backend.BlogForge.domain.entities.Tag;
import com.backend.BlogForge.domain.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface PostRepository extends JpaRepository<Post, UUID> {
	List<Post> findAllByStatusAndCategoryAndTagsContaining(PostStatus status, Category category, Tag tag);
	List<Post> findAllByStatusAndCategory(PostStatus status, Category category);
	List<Post> findAllByStatusAndTags(PostStatus status, Tag tag);
	List<Post> findAllByStatus(PostStatus status);
	List<Post> findAllByAuthorAndStatus(User author, PostStatus status);
}
