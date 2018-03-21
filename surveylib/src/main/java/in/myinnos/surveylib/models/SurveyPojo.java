package in.myinnos.surveylib.models;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SurveyPojo implements Serializable {

    @SerializedName("survey_properties")
    @Expose
    private SurveyProperties surveyProperties;
    @SerializedName("questions")
    @Expose
    private List<Question> questions = new ArrayList<Question>();

    /**
     *
     * @return
     * The surveyProperties
     */
    public SurveyProperties getSurveyProperties() {
        return surveyProperties;
    }

    /**
     *
     * @param surveyProperties
     * The survey_properties
     */
    public void setSurveyProperties(SurveyProperties surveyProperties) {
        this.surveyProperties = surveyProperties;
    }

    /**
     *
     * @return
     * The questions
     */
    public List<Question> getQuestions() {
        return questions;
    }

    /**
     *
     * @param questions
     * The questions
     */
    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

}