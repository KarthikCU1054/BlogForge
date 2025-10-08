package com.backend.BlogForge.domain.mappers;

import com.backend.BlogForge.domain.dtos.*;
import com.backend.BlogForge.domain.entities.Post;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PostMapper {

	@Mapping(target = "author", source = "author")
	@Mapping(target = "category", source = "category")
	@Mapping(target = "tags", source = "tags")
	PostDto toDto(Post Post);

	CreatePostRequest toCreatePostRequest(CreatePostRequestDto createPostRequestDto);

	UpdatePostRequest toUpdatePostRequest(UpdatePostRequestDto updatePostRequestDto);
}
