package com.backend.BlogForge.controllers;

import com.backend.BlogForge.domain.dtos.*;
import com.backend.BlogForge.domain.entities.Post;
import com.backend.BlogForge.domain.entities.User;
import com.backend.BlogForge.domain.mappers.PostMapper;
import com.backend.BlogForge.services.PostService;
import com.backend.BlogForge.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(path = "/api/v1/posts")
@RequiredArgsConstructor
public class PostController {

	private final PostService postService;
	private final PostMapper postMapper;
	private final UserService userService;

	@GetMapping
	public ResponseEntity<List<PostDto>> getAllPosts(
			@RequestParam(required = false) UUID categoryId,
			@RequestParam(required = false) UUID tagId
	){
		List<Post> posts = postService.getAllPosts(categoryId, tagId);
		List<PostDto> postDtos = posts.stream().map(postMapper::toDto).toList();

		return ResponseEntity.ok(postDtos);
	}

	@GetMapping(path = "/drafts")
	public ResponseEntity<List<PostDto>> getDraft(@RequestAttribute UUID userId){
		User loggedInUser = userService.getUserById(userId);
		List<Post> draftPosts = postService.getDraftPosts(loggedInUser);
		List<PostDto> postDtos = draftPosts.stream().map(postMapper::toDto).toList();

		return ResponseEntity.ok(postDtos);
	}

	@PostMapping
	public ResponseEntity<PostDto> createPost(
			@Valid @RequestBody CreatePostRequestDto createPostRequestDto,
			@RequestAttribute UUID userId
		){
		User loggedInUser = userService.getUserById(userId);
		CreatePostRequest createPostRequest = postMapper.toCreatePostRequest(createPostRequestDto);
		Post createdPost = postService.createPost(loggedInUser, createPostRequest);

		return new ResponseEntity<>(postMapper.toDto(createdPost), HttpStatus.CREATED);
	}

	@PutMapping(path = "/{id}")
	public ResponseEntity<PostDto> updatePost(
			@PathVariable UUID id,
			@Valid @RequestBody UpdatePostRequestDto updatePostRequestDto
			){
		UpdatePostRequest updatePostRequest = postMapper.toUpdatePostRequest(updatePostRequestDto);
		Post updatedPost = postService.updatePost(id, updatePostRequest);
		PostDto dto = postMapper.toDto(updatedPost);

		return ResponseEntity.ok(dto);
	}

	@GetMapping(path = "/{id}")
	public ResponseEntity<PostDto> getPostById(
			@PathVariable UUID id
	){
		Post post = postService.getPost(id);
		PostDto postDto = postMapper.toDto(post);

		return ResponseEntity.ok(postDto);
	}

	@DeleteMapping(path = "/{id}")
	public ResponseEntity<Void> deletPost(
			@PathVariable UUID id
	){
		postService.deletePost(id);
		return ResponseEntity.noContent().build();
	}
}
