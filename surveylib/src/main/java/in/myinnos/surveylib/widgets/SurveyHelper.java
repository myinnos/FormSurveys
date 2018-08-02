package in.myinnos.surveylib.widgets;

import in.myinnos.surveylib.Answers;
import in.myinnos.surveylib.function.RealmObjectFlow;

public class SurveyHelper {


    public static void putAnswer(String question, String answer,
                                 String type, String questionId, String value) {

        RealmObjectFlow.set(question, answer);

        if (type.equals("boolean")) {

            if (value.equals("Yes")) {
                Answers.getInstance().put_answer(questionId, true);
            } else if (value.equals("No")) {
                Answers.getInstance().put_answer(questionId, false);
            }
            //Answers.getInstance().put_answer(questionId, Boolean.parseBoolean(value));

        } else if (type.equals("int")) {

            Answers.getInstance().put_answer(questionId, Integer.parseInt(value));

        } else if (type.equals("long")) {

            if (value != null) {
                Answers.getInstance().put_answer(questionId, Long.parseLong(value));
            } else {
                Answers.getInstance().put_answer(questionId, null);
            }

        } else if (type.equals("string")) {

            Answers.getInstance().put_answer(questionId, String.valueOf(value));

        }

    }

}
