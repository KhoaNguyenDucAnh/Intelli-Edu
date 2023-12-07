package com.intelliedu.intelliedu.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.mapstruct.BeforeMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

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

  /*@Override
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
  }*/

  public QuestionDto toDto(Question question, Boolean shuffle) {
    QuestionDto questionDto = toDto(question);
    if (shuffle) questionDto.shuffle();
    return questionDto;
  }

  @BeforeMapping
  public void convertAnswer(QuestionDtoDetail questionDtoDetail, @MappingTarget QuestionDetail questionDetail) {
    List<String> answer = questionDtoDetail.getAnswers();
    questionDetail.setCorrectAnswer(answer.get(0));
    questionDetail.setIncorrectAnswer(answer.subList(1, answer.size()));
  }

  @BeforeMapping
  public void convertAnswer(QuestionDetail questionDetail, @MappingTarget QuestionDtoDetail questionDtoDetail) {
    questionDtoDetail.setAnswers(questionDetail.getCorrectAnswer(), questionDetail.getIncorrectAnswer());
  }

  public abstract QuestionDetail toQuestionDetail(QuestionDtoDetail questionDtoDetail);

  /*public QuestionDetail toQuestionDetail(QuestionDtoDetail questionDtoDetail) {
    QuestionDetail questionDetail = new QuestionDetail();
    questionDetail.setId(questionDtoDetail.getId());
    questionDetail.setQuestionDetail(questionDtoDetail.getQuestionDetail());
    questionDetail.setAnswer(questionDtoDetail);
    return questionDetail;
  }*/

  public abstract QuestionDetail toQuestionDetail(QuestionDtoDetail questionDtoDetail, @MappingTarget QuestionDetail questionDetail);

  /*public QuestionDtoDetail toQuestionDtoDetail(QuestionDetail questionDetail) {
    QuestionDtoDetail questionDtoDetail = new QuestionDtoDetail();
    questionDtoDetail.setId(questionDetail.getId());
    questionDtoDetail.setQuestionDetail(questionDetail.getQuestionDetail());
    questionDtoDetail.setAnswers(Arrays.asList(questionDetail.getCorrectAnswer(), questionDetail.getIncorrectAnswer1(), questionDetail.getIncorrectAnswer2(), questionDetail.getIncorrectAnswer3()));
    return questionDtoDetail;
  }*/

  public abstract QuestionDtoDetail toQuestionDtoDetail(QuestionDetail questionDetail);

  public List<QuestionDetail> toQuestionDetail(List<QuestionDtoDetail> questionDtoDetail) {
    System.out.println(questionDtoDetail);
    return questionDtoDetail.stream().map(question -> toQuestionDetail(question)).collect(Collectors.toList());
  }

  protected List<QuestionDtoDetail> toQuestionDtoDetail(List<QuestionDetail> questionDetail) {
    return questionDetail.stream().map(question -> toQuestionDtoDetail(question)).toList();  
  }
}
