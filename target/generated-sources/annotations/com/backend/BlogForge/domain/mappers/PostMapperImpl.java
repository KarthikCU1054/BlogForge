package com.backend.BlogForge.domain.mappers;

import com.backend.BlogForge.domain.dtos.AuthorDto;
import com.backend.BlogForge.domain.dtos.CategoryDto;
import com.backend.BlogForge.domain.dtos.CreatePostRequest;
import com.backend.BlogForge.domain.dtos.CreatePostRequestDto;
import com.backend.BlogForge.domain.dtos.PostDto;
import com.backend.BlogForge.domain.dtos.TagDto;
import com.backend.BlogForge.domain.dtos.UpdatePostRequest;
import com.backend.BlogForge.domain.dtos.UpdatePostRequestDto;
import com.backend.BlogForge.domain.entities.Category;
import com.backend.BlogForge.domain.entities.Post;
import com.backend.BlogForge.domain.entities.Tag;
import com.backend.BlogForge.domain.entities.User;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-10-08T12:27:29-0400",
    comments = "version: 1.6.3, compiler: javac, environment: Java 24.0.2 (Oracle Corporation)"
)
@Component
public class PostMapperImpl implements PostMapper {

    @Override
    public PostDto toDto(Post Post) {
        if ( Post == null ) {
            return null;
        }

        PostDto.PostDtoBuilder postDto = PostDto.builder();

        postDto.author( userToAuthorDto( Post.getAuthor() ) );
        postDto.category( categoryToCategoryDto( Post.getCategory() ) );
        postDto.tags( tagSetToTagDtoSet( Post.getTags() ) );
        postDto.id( Post.getId() );
        postDto.title( Post.getTitle() );
        postDto.content( Post.getContent() );
        postDto.readingTime( Post.getReadingTime() );
        postDto.createdAt( Post.getCreatedAt() );
        postDto.updatedAt( Post.getUpdatedAt() );

        return postDto.build();
    }

    @Override
    public CreatePostRequest toCreatePostRequest(CreatePostRequestDto createPostRequestDto) {
        if ( createPostRequestDto == null ) {
            return null;
        }

        CreatePostRequest.CreatePostRequestBuilder createPostRequest = CreatePostRequest.builder();

        createPostRequest.title( createPostRequestDto.getTitle() );
        createPostRequest.content( createPostRequestDto.getContent() );
        createPostRequest.categoryId( createPostRequestDto.getCategoryId() );
        Set<UUID> set = createPostRequestDto.getTagIds();
        if ( set != null ) {
            createPostRequest.tagIds( new LinkedHashSet<UUID>( set ) );
        }
        createPostRequest.status( createPostRequestDto.getStatus() );

        return createPostRequest.build();
    }

    @Override
    public UpdatePostRequest toUpdatePostRequest(UpdatePostRequestDto updatePostRequestDto) {
        if ( updatePostRequestDto == null ) {
            return null;
        }

        UpdatePostRequest.UpdatePostRequestBuilder updatePostRequest = UpdatePostRequest.builder();

        updatePostRequest.id( updatePostRequestDto.getId() );
        updatePostRequest.title( updatePostRequestDto.getTitle() );
        updatePostRequest.content( updatePostRequestDto.getContent() );
        updatePostRequest.categoryId( updatePostRequestDto.getCategoryId() );
        Set<UUID> set = updatePostRequestDto.getTagIds();
        if ( set != null ) {
            updatePostRequest.tagIds( new LinkedHashSet<UUID>( set ) );
        }
        updatePostRequest.status( updatePostRequestDto.getStatus() );

        return updatePostRequest.build();
    }

    protected AuthorDto userToAuthorDto(User user) {
        if ( user == null ) {
            return null;
        }

        AuthorDto.AuthorDtoBuilder authorDto = AuthorDto.builder();

        authorDto.id( user.getId() );
        authorDto.name( user.getName() );

        return authorDto.build();
    }

    protected CategoryDto categoryToCategoryDto(Category category) {
        if ( category == null ) {
            return null;
        }

        CategoryDto.CategoryDtoBuilder categoryDto = CategoryDto.builder();

        categoryDto.id( category.getId() );
        categoryDto.name( category.getName() );

        return categoryDto.build();
    }

    protected TagDto tagToTagDto(Tag tag) {
        if ( tag == null ) {
            return null;
        }

        TagDto.TagDtoBuilder tagDto = TagDto.builder();

        tagDto.id( tag.getId() );
        tagDto.name( tag.getName() );

        return tagDto.build();
    }

    protected Set<TagDto> tagSetToTagDtoSet(Set<Tag> set) {
        if ( set == null ) {
            return null;
        }

        Set<TagDto> set1 = LinkedHashSet.newLinkedHashSet( set.size() );
        for ( Tag tag : set ) {
            set1.add( tagToTagDto( tag ) );
        }

        return set1;
    }
}
