package in.myinnos.surveylib.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class Question implements Serializable {

    @SerializedName("question_type")
    @Expose
    private String questionType;
    @SerializedName("question_title")
    @Expose
    private String questionTitle;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("required")
    @Expose
    private Boolean required;
    @SerializedName("random_choices")
    @Expose
    private Boolean randomChoices;
    @SerializedName("choices")
    @Expose
    private List<ChoicesListModel> choicesListModelList;
    @SerializedName("min")
    @Expose
    private Integer min;
    @SerializedName("max")
    @Expose
    private Integer max;
    @SerializedName("number_of_lines")
    @Expose
    private Integer numberOfLines;

    @SerializedName("question_id")
    @Expose
    private String questionId;
    @SerializedName("question_v_type")
    @Expose
    private String question_v_type;
    @SerializedName("min_char_length")
    @Expose
    private String min_char_length;
    @SerializedName("max_char_length")
    @Expose
    private String max_char_length;

    @SerializedName("date_condition")
    @Expose
    private int date_condition;

    @SerializedName("is_phone_number")
    @Expose
    private Boolean is_phone_number;

    @SerializedName("is_photo_required")
    @Expose
    private Boolean is_photo_required;

    @SerializedName("pdf_file_link")
    @Expose
    private String pdf_file_link;


    public String getQuestionId() {
        return questionId;
    }

    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }

    public String getQuestion_v_type() {
        return question_v_type;
    }

    public void setQuestion_v_type(String question_v_type) {
        this.question_v_type = question_v_type;
    }

    public String getMin_char_length() {
        return min_char_length;
    }

    public void setMin_char_length(String min_char_length) {
        this.min_char_length = min_char_length;
    }

    public String getMax_char_length() {
        return max_char_length;
    }

    public void setMax_char_length(String max_char_length) {
        this.max_char_length = max_char_length;
    }

    public int getDate_condition() {
        return date_condition;
    }

    public void setDate_condition(int date_condition) {
        this.date_condition = date_condition;
    }

    public Boolean getIs_phone_number() {
        return is_phone_number;
    }

    public void setIs_phone_number(Boolean is_phone_number) {
        this.is_phone_number = is_phone_number;
    }

    public Boolean getIs_photo_required() {
        return is_photo_required;
    }

    public void setIs_photo_required(Boolean is_photo_required) {
        this.is_photo_required = is_photo_required;
    }

    public String getPdf_file_link() {
        return pdf_file_link;
    }

    public void setPdf_file_link(String pdf_file_link) {
        this.pdf_file_link = pdf_file_link;
    }

    /**
     * @return The questionType
     */
    public String getQuestionType() {
        return questionType;
    }

    /**
     * @param questionType The question_type
     */
    public void setQuestionType(String questionType) {
        this.questionType = questionType;
    }

    /**
     * @return The questionTitle
     */
    public String getQuestionTitle() {
        return questionTitle;
    }

    /**
     * @param questionTitle The question_title
     */
    public void setQuestionTitle(String questionTitle) {
        this.questionTitle = questionTitle;
    }

    /**
     * @return The description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description The description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return The required
     */
    public Boolean getRequired() {
        return required;
    }

    /**
     * @param required The required
     */
    public void setRequired(Boolean required) {
        this.required = required;
    }

    /**
     * @return The randomChoices
     */
    public Boolean getRandomChoices() {
        return randomChoices;
    }

    /**
     * @param randomChoices The random_choices
     */
    public void setRandomChoices(Boolean randomChoices) {
        this.randomChoices = randomChoices;
    }


    public List<ChoicesListModel> getChoicesListModelList() {
        return choicesListModelList;
    }

    public void setChoicesListModelList(List<ChoicesListModel> choicesListModelList) {
        this.choicesListModelList = choicesListModelList;
    }

    /**
     * @return The min
     */
    public Integer getMin() {
        return min;
    }

    /**
     * @param min The min
     */
    public void setMin(Integer min) {
        this.min = min;
    }

    /**
     * @return The max
     */
    public Integer getMax() {
        return max;
    }

    /**
     * @param max The max
     */
    public void setMax(Integer max) {
        this.max = max;
    }

    /**
     * @return The numberOfLines
     */
    public Integer getNumberOfLines() {
        return numberOfLines;
    }

    /**
     * @param numberOfLines The number_of_lines
     */
    public void setNumberOfLines(Integer numberOfLines) {
        this.numberOfLines = numberOfLines;
    }

}