package com.backend.BlogForge.services.impl;

import com.backend.BlogForge.domain.entities.Tag;
import com.backend.BlogForge.repositories.TagRepository;
import com.backend.BlogForge.services.TagService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TagServiceImpl implements TagService {

	private final TagRepository tagRepository;

	@Override
	public List<Tag> getTags() {
		return tagRepository.findAllWIthPostCount();
	}

	@Override
	@Transactional
	public List<Tag> createTags(Set<String> tagNames) {
		List<Tag> existingTags = tagRepository.findByNameIn(tagNames);

		Set<String> existingTagNames = existingTags.stream().map(tag -> tag.getName()).collect(Collectors.toSet());

		List<Tag> newTags = tagNames.stream()
				.filter( name -> !existingTagNames.contains(name))
				.map(name -> Tag.builder()
						.name(name)
						.posts(new HashSet<>())
						.build())
						.toList();
		List<Tag> savedTags = new ArrayList<>();

		if(!newTags.isEmpty()){
			savedTags = tagRepository.saveAll(newTags);
		}

		savedTags.addAll(existingTags);

		return savedTags;
	}

	@Override
	@Transactional
	public void deleteTag(UUID id) {
		tagRepository.findById(id).ifPresent(tag -> {
			if(!tag.getPosts().isEmpty()) {
				throw new IllegalStateException("Cannot delete tag with posts!");
			}
			tagRepository.deleteById(id);
		});
	}

	@Override
	public Tag getTagById(UUID id) {
		return tagRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Cannot find Tag with ID:" + id));
	}

	@Override
	public List<Tag> getTagIds(Set<UUID> tagIds) {
		List<Tag> foundTags = tagRepository.findAllById(tagIds);

		if(foundTags.size() != tagIds.size()){
			throw new EntityNotFoundException("Not all specified tag IDs exist!");
		}

		return foundTags;
	}
}
