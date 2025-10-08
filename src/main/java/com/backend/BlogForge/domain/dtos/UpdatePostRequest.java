package com.backend.BlogForge.domain.dtos;

import com.backend.BlogForge.domain.PostStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdatePostRequest {

	private UUID id;

	private String title;

	private String content;

	private UUID categoryId;

	private Set<UUID> tagIds;

	private PostStatus status;
}
