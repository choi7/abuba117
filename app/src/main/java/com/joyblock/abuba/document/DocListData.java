package com.joyblock.abuba.document;



import java.text.Collator;
import java.util.Comparator;

/**
 * Created by POPCON on 2018-01-11.
 */

public class DocListData {
    //아이콘

    //제목
    public String mTitle;
    //부제목

    //알파벳이름으로 정렬

    public static final Comparator<DocListData> ALPHA_COMPARTOR = new Comparator<DocListData>() {

        private final Collator sCollator = Collator.getInstance();

        @Override
        public int compare(DocListData o1, DocListData o2) {
            return sCollator.compare(o1.mTitle,o2.mTitle);
        }
    };
}
