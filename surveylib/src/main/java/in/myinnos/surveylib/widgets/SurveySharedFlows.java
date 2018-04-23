package in.myinnos.surveylib.widgets;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

public class SurveySharedFlows {

    public static void saveIDPreferences(Activity activity,
                                         String questionId, String questionVariableType) {

        SharedPreferences.Editor editor =
                activity.getSharedPreferences("MY_PREFS_NAME", Context.MODE_PRIVATE).edit();
        editor.putString("questionId", questionId);
        editor.putString("questionVariableType", questionVariableType);
        editor.apply();
    }

    public static String getQuestionID(Activity activity){
        SharedPreferences preferences =
                activity.getSharedPreferences("MY_PREFS_NAME", Context.MODE_PRIVATE);

        String question = preferences.getString("questionId", "sample");
        return question;
    }

    public static String getQuestionType(Activity activity){
        SharedPreferences preferences =
                activity.getSharedPreferences("MY_PREFS_NAME", Context.MODE_PRIVATE);

        String question = preferences.getString("questionVariableType", "string");
        return question;
    }

}
