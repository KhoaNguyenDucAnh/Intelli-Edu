package com.intelliedu.intelliedu.mapper;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

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

  protected Map<Integer, QuestionDetail> toQuestionDetail(Map<Integer, QuestionDtoDetail> questionDtoDetail) {
    Map<Integer, QuestionDetail> questionDetailList = new HashMap<>();
    questionDtoDetail.forEach((id, question) -> {
      QuestionDetail questionDetail = new QuestionDetail();
      questionDetail.setQuestion(question.getQuestion());
      questionDetail.setAnswer(question);
      questionDetailList.put(id, questionDetail);
    });
    return questionDetailList;
  }

  protected Map<Integer, QuestionDtoDetail> toQuestionDtoDetail(Map<Integer, QuestionDetail> questionDetail) {
    Map<Integer, QuestionDtoDetail> questionDtoDetailList = new HashMap<>();
    questionDetail.forEach((id, question) -> {
      QuestionDtoDetail questionDtoDetail = new QuestionDtoDetail();
      questionDtoDetail.setQuestion(question.getQuestion());
      questionDtoDetail.setAnswer(Arrays.asList(question.getCorrectAnswer(), question.getIncorrectAnswer1(), question.getIncorrectAnswer2(), question.getIncorrectAnswer3()));
      //questionDtoDetail.setAnswer(question);
      questionDtoDetailList.put(id, questionDtoDetail);
    });
    return questionDtoDetailList;
  }
}
