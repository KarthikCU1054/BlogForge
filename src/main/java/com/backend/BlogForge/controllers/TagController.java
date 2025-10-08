package com.backend.BlogForge.controllers;

import com.backend.BlogForge.domain.dtos.CreateTagsRequest;
import com.backend.BlogForge.domain.dtos.TagDto;
import com.backend.BlogForge.domain.entities.Tag;
import com.backend.BlogForge.domain.mappers.TagMapper;
import com.backend.BlogForge.services.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/v1/tags")
public class TagController {

	private final TagService tagService;
	private final TagMapper tagMapper;

	@GetMapping
	public ResponseEntity<List<TagDto>> getAllTags(){
		List<Tag> tags = tagService.getTags();

		List<TagDto> tagResponses = tags.stream().map(tagMapper::toTagResponse).toList();

		return ResponseEntity.ok(tagResponses);
	}

	@PostMapping
	public ResponseEntity<List<TagDto>> createTags(@RequestBody CreateTagsRequest createTagsRequest){
		List<Tag> savedTags = tagService.createTags(createTagsRequest.getNames());
		List<TagDto> createdTagResponses = savedTags.stream().map(tag -> tagMapper.toTagResponse(tag))
				.toList();

		return new ResponseEntity<>(
				createdTagResponses,
				HttpStatus.CREATED
		);
	}

	@DeleteMapping(path = "/{id}")
	public ResponseEntity<Void> deleteTag(@PathVariable UUID id){
		tagService.deleteTag(id);
		return ResponseEntity.noContent().build();
	}
}
