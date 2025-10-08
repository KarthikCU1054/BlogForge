package com.backend.BlogForge.services.impl;

import com.backend.BlogForge.domain.entities.Category;
import com.backend.BlogForge.repositories.CategoryRepository;
import com.backend.BlogForge.services.CategoryService;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class CategoryServiceImpl implements CategoryService {

	private final CategoryRepository categoryRepository;

	@Override
	public List<Category> listCategories() {
		return categoryRepository.findAllWithPostCount();
	}

	@Override
	public Category createCategory(Category category) {
		if(categoryRepository.existsByNameIgnoreCase(category.getName())){
			throw new IllegalArgumentException(("category already exists with name: " + category.getName()));
		}

		return categoryRepository.save(category);
	}

	@Override
	public void deleteCategory(UUID id) {
		Optional<Category> category =  categoryRepository.findById(id);

		if(category.isPresent()){
			if(category.get().getPosts().size() > 0){
				throw new IllegalStateException("Category has posts associated with it");
			}
			categoryRepository.deleteById(id);
		}
	}

	@Override
	public Category getCategoryById(UUID id) {
		return categoryRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Category not found with id" + id));
	}


}
