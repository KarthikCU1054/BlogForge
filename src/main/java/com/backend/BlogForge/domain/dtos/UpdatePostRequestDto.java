package com.backend.BlogForge.domain.dtos;


import com.backend.BlogForge.domain.PostStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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
public class UpdatePostRequestDto {

	@NotNull(message = "Post ID is required")
	private UUID id;


	@NotBlank(message = "Title is required!")
	@Size(min = 3, max = 250, message = "Title must be between {min} and {max} characters!")
	private String title;

	@NotBlank(message = "Content cannot be blank!")
	@Size(min = 10, max = 50000, message = "Content must be between {min} and {max} characters!")
	private String content;

	@NotNull(message = "Category is required!")
	private UUID categoryId;

	@Size(max = 10, message = "Maximum of {max} tags allowed!")
	private Set<UUID> tagIds;

	@NotNull(message = "Status is required!")
	private PostStatus status;

}
