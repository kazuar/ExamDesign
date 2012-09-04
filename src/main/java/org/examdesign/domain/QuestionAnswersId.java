package org.examdesign.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;

@Embeddable
public class QuestionAnswersId implements Serializable {
	
	private Answer answer;
	private Question question;
	
	@ManyToOne
	public Answer getAnswer() {
		return answer;
	}
	
	@ManyToOne
	public Question getQuestion() {
		return question;
	}
	
	private boolean isCorrect;
    
	@Column(name="is_correct")
    public boolean getIsCorrect() {
		return isCorrect;
	}
    
    public void setIsCorrect(boolean isCorrect) {
		this.isCorrect = isCorrect;
	}
	
	public void setAnswer(Answer answer) {
		this.answer = answer;
	}
	
	public void setQuestion(Question question) {
		this.question = question;
	}
	
	public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        
        QuestionAnswersId that = (QuestionAnswersId) o;

        if (answer != null ? !answer.equals(that.answer) : that.answer != null) return false;
        if (question != null ? !question.equals(that.question) : that.question != null)
            return false;

        return true;
    }

    public int hashCode() {
        int result;
        result = (answer != null ? answer.hashCode() : 0);
        result = 31 * result + (question != null ? question.hashCode() : 0);
        return result;
    }
}
