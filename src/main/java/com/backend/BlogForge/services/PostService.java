package com.backend.BlogForge.services;

import com.backend.BlogForge.domain.dtos.CreatePostRequest;
import com.backend.BlogForge.domain.dtos.UpdatePostRequest;
import com.backend.BlogForge.domain.entities.Post;
import com.backend.BlogForge.domain.entities.User;

import java.util.List;
import java.util.UUID;

public interface PostService {
	List<Post> getAllPosts(UUID categoryId, UUID tagId);
	List<Post> getDraftPosts(User user);
	Post createPost(User user, CreatePostRequest createPostRequest);
	Post updatePost(UUID id, UpdatePostRequest updatePostRequest);
	Post getPost(UUID id);
	void deletePost(UUID id);
}
