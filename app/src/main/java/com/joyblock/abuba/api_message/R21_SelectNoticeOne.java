package com.joyblock.abuba.api_message;

/**
 * Created by hyoshinchoi on 2018. 1. 22..
 */

public class R21_SelectNoticeOne {
    public int resultcode;
    public ItemList[] survey_vote_item_list;
    public Survey survey;

}


class ItemList{
    public String seq_survey_vote_item,vote_flag,vote_item;
}

class Survey{
    public String seq_kindergarden_class, reg_date, month, year, seq_kindergarden,
            is_yn, mod_date, seq_user, seq_survey, title, day, content;
}