package com.backend.BlogForge.controllers;

import com.backend.BlogForge.domain.dtos.CategoryDto;
import com.backend.BlogForge.domain.dtos.CreateCategoryRequest;
import com.backend.BlogForge.domain.entities.Category;
import com.backend.BlogForge.domain.mappers.CategoryMapper;
import com.backend.BlogForge.services.CategoryService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(path = "/api/v1/categories")
@AllArgsConstructor
public class CategoryController {

	private final CategoryService categoryService;
	private final CategoryMapper categoryMapper;

	@GetMapping
	public ResponseEntity<List<CategoryDto>> listCategories(){
		List<CategoryDto> categories = categoryService.listCategories().stream().map(categoryMapper::toDto).toList();

		return ResponseEntity.ok(categories);
	}

	@PostMapping
	public ResponseEntity<CategoryDto> createCategory(@Valid @RequestBody CreateCategoryRequest createCategoryRequest) {
		Category categoryToCreate  = categoryMapper.toEntity(createCategoryRequest);
		Category savedCategory = categoryService.createCategory(categoryToCreate);
		return new ResponseEntity<>(
				categoryMapper.toDto(savedCategory),
				HttpStatus.CREATED
		);
	}

	@DeleteMapping(path = "/{id}")
	public ResponseEntity<Void> deleteCategory(@PathVariable UUID id){
		categoryService.deleteCategory(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

}
