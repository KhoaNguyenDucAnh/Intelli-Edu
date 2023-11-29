package com.intelliedu.intelliedu.mapper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;
import org.springframework.beans.factory.annotation.Autowired;

import com.intelliedu.intelliedu.dto.QuestionDto;
import com.intelliedu.intelliedu.dto.QuestionDtoDetail;
import com.intelliedu.intelliedu.entity.Question;
import com.intelliedu.intelliedu.entity.QuestionDetail;

/**
 * QuestionMapper
 */
@Mapper(
	componentModel = "spring",
	unmappedTargetPolicy = ReportingPolicy.IGNORE,
	nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
  uses = PostMapper.class
)
public abstract class QuestionMapper implements ContentMapper<Question, QuestionDto> {

  @Autowired
  private PostMapper postMapper;

  @Override
  public Question toEntity(QuestionDto cDto) {
     if (cDto == null) {
      return null;
    }

    Question.QuestionBuilder<?, ?> questionBuilder = Question.builder();

    questionBuilder.content(toQuestionDetail(cDto.getContent()));

    return questionBuilder.build();
  }

  @Override
  public QuestionDto toDto(Question c) {
    if (c == null) {
      return null;
    }

    QuestionDto.QuestionDtoBuilder<?, ?> questionDtoBuilder = QuestionDto.builder();

    questionDtoBuilder.shared(c.isShared());
    questionDtoBuilder.postDto(postMapper.toPostDto(c.getPost()));
    questionDtoBuilder.content(toQuestionDtoDetail(c.getContent()));

    return questionDtoBuilder.build();
  }

  public QuestionDetail toQuestionDetail(QuestionDtoDetail questionDtoDetail) {
    QuestionDetail questionDetail = new QuestionDetail();
    questionDetail.setId(questionDtoDetail.getId());
    questionDetail.setQuestion(questionDtoDetail.getQuestion());
    questionDetail.setAnswer(questionDtoDetail);
    return questionDetail;
  }


  public List<QuestionDetail> toQuestionDetail(List<QuestionDtoDetail> questionDtoDetail) {
    return questionDtoDetail.stream().map(question -> toQuestionDetail(question)).collect(Collectors.toList());
  }

  public QuestionDtoDetail toQuestionDtoDetail(QuestionDetail questionDetail) {
    QuestionDtoDetail questionDtoDetail = new QuestionDtoDetail();
    questionDtoDetail.setId(questionDetail.getId());
    questionDtoDetail.setQuestion(questionDetail.getQuestion());
    questionDtoDetail.setAnswer(Arrays.asList(questionDetail.getCorrectAnswer(), questionDetail.getIncorrectAnswer1(), questionDetail.getIncorrectAnswer2(), questionDetail.getIncorrectAnswer3()));
    return questionDtoDetail;
  }

  protected List<QuestionDtoDetail> toQuestionDtoDetail(List<QuestionDetail> questionDetail) {
    return questionDetail.stream().map(question -> toQuestionDtoDetail(question)).toList();  
  }
}
