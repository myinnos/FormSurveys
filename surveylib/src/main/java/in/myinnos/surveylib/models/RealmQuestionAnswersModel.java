package in.myinnos.surveylib.models;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class RealmQuestionAnswersModel extends RealmObject {

    @PrimaryKey
    public String question;
    public String answer;

    public RealmQuestionAnswersModel(){

    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}
