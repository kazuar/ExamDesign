package org.examdesign.domain;

import javax.persistence.AssociationOverride;
import javax.persistence.AssociationOverrides;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.Transient;

@Entity(name="questionanswers")
@AssociationOverrides({
@AssociationOverride(name = "pk.answer", joinColumns = @JoinColumn(name = "id_answer")),
@AssociationOverride(name = "pk.question", joinColumns = @JoinColumn(name = "id_question"))
        })
public class QuestionAnswers {
	
	private QuestionAnswersId pk = new QuestionAnswersId();
	
	@EmbeddedId
    public QuestionAnswersId getPk() {
        return pk;
    }

    public void setPk(QuestionAnswersId pk) {
        this.pk = pk;
    }

    @Transient
    public Answer getAnswer() {
        return getPk().getAnswer();
    }

    public void setAnswer(Answer answer) {
        getPk().setAnswer(answer);
    }

    @Transient
    public Question getQuestion() {
        return getPk().getQuestion();
    }

    public void setQuestion(Question question) {
        getPk().setQuestion(question);
    }
    
    @Transient
    public boolean getIsCorrect() {
    	return getPk().getIsCorrect();
    }
    
    public void setIsCorrect(boolean isCorrect) {
    	getPk().setIsCorrect(isCorrect);
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        QuestionAnswers that = (QuestionAnswers) o;

        if (getPk() != null ? !getPk().equals(that.getPk()) : that.getPk() != null) return false;

        return true;
    }

    public int hashCode() {
        return (getPk() != null ? getPk().hashCode() : 0);
    }
}
