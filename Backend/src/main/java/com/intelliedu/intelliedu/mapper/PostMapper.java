package com.intelliedu.intelliedu.mapper;

import java.util.List;

import org.mapstruct.BeforeMapping;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

import com.intelliedu.intelliedu.config.VoteStatus;
import com.intelliedu.intelliedu.dto.PostDto;
import com.intelliedu.intelliedu.entity.Post;
import com.intelliedu.intelliedu.entity.Vote;

/**
 * PostMapper
 */
@Mapper(
	componentModel = "spring",
	unmappedTargetPolicy = ReportingPolicy.IGNORE,
	nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
  builder = @Builder(disableBuilder = true)
)
public interface PostMapper {

  @BeforeMapping
  default void convertVote(Post post, @MappingTarget PostDto postDto) {
    List<Vote> vote = post.getVote();
    postDto.setUpvote(vote.stream().filter(v -> v.getVoteStatus() == VoteStatus.UPVOTE).toList().size());
    postDto.setDownvote(vote.stream().filter(v -> v.getVoteStatus() == VoteStatus.DOWNVOTE).toList().size());
  }

  PostDto toPostDto(Post post);
}
