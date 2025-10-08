package com.backend.BlogForge.services.impl;

import com.backend.BlogForge.domain.dtos.CreatePostRequest;
import com.backend.BlogForge.domain.PostStatus;
import com.backend.BlogForge.domain.dtos.UpdatePostRequest;
import com.backend.BlogForge.domain.entities.Category;
import com.backend.BlogForge.domain.entities.Post;
import com.backend.BlogForge.domain.entities.Tag;
import com.backend.BlogForge.domain.entities.User;
import com.backend.BlogForge.repositories.PostRepository;
import com.backend.BlogForge.services.CategoryService;
import com.backend.BlogForge.services.PostService;
import com.backend.BlogForge.services.TagService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

	private final PostRepository postRepository;
	private final CategoryService categoryService;
	private final TagService tagService;

	private static final int WORDS_PER_MIN = 200;


	@Override
	@Transactional(readOnly = true)
	public List<Post> getAllPosts(UUID categoryId, UUID tagId) {
		if(categoryId != null && tagId != null){
			Category category = categoryService.getCategoryById(categoryId);
			Tag tag = tagService.getTagById(tagId);

			return postRepository.findAllByStatusAndCategoryAndTagsContaining(
					PostStatus.PUBLISHED,
					category,
					tag
			);
		}

		if(categoryId != null){
			Category category = categoryService.getCategoryById(categoryId);
			return postRepository.findAllByStatusAndCategory(PostStatus.PUBLISHED, category);
		}

		if(tagId != null){
			Tag tag = tagService.getTagById(tagId);
			return postRepository.findAllByStatusAndTags(PostStatus.PUBLISHED, tag);
		}

		return postRepository.findAllByStatus(PostStatus.PUBLISHED);
	}

	@Override
	public List<Post> getDraftPosts(User user) {
		return postRepository.findAllByAuthorAndStatus(user, PostStatus.DRAFT);
	}

	@Override
	@Transactional
	public Post createPost(User user, CreatePostRequest createPostRequest) {
		Post newPost = Post.builder()
				.title(createPostRequest.getTitle())
				.content(createPostRequest.getContent())
				.status(createPostRequest.getStatus())
				.author(user)
				.readingTime(calculateReadingTime(createPostRequest.getContent()))
				.build();

		Category category = categoryService.getCategoryById(createPostRequest.getCategoryId());
		newPost.setCategory(category);

		List<Tag> tags = tagService.getTagIds(createPostRequest.getTagIds());

		newPost.setTags(new HashSet<>(tags));

		return postRepository.save(newPost);
	}

	@Override
	@Transactional
	public Post updatePost(UUID id, UpdatePostRequest updatePostRequest) {
		Post existingPost = postRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Post not found with ID:" + id));

		existingPost.setTitle(updatePostRequest.getTitle());
		existingPost.setContent(updatePostRequest.getContent());
		existingPost.setStatus(updatePostRequest.getStatus());
		existingPost.setReadingTime(calculateReadingTime(updatePostRequest.getContent()));

		UUID updatePostRequestCategoryId = updatePostRequest.getCategoryId();

		if(!existingPost.getCategory().getId().equals(updatePostRequestCategoryId)){
			Category category = categoryService.getCategoryById(updatePostRequestCategoryId);
			existingPost.setCategory(category);
		}

		Set<UUID> existingTagIds = existingPost.getTags().stream().map(Tag::getId).collect(Collectors.toSet());

		Set<UUID> updatePostRequestTagIds = updatePostRequest.getTagIds();

		if(!existingTagIds.equals(updatePostRequestTagIds)){
			List<Tag> newTags = tagService.getTagIds(updatePostRequestTagIds);
			existingPost.setTags(new HashSet<>(newTags));
		}

		return postRepository.save(existingPost);

	}

	@Override
	public Post getPost(UUID id) {
		return postRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Cannot find post with id:" + id));
	}

	@Override
	public void deletePost(UUID id) {
		 Post post = getPost(id);
		 postRepository.delete(post);
	}

	private Integer calculateReadingTime(String content){
		if(content == null || content.isEmpty())
			return 0;

		int wordCount = content.trim().split("\\s+").length;

		return (int) Math.ceil((double)wordCount/WORDS_PER_MIN);
	}
}
