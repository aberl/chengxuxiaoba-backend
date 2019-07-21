package com.chengxuxiaoba.video.constant;

import java.util.ArrayList;
import java.util.List;

public class FilePurpose {
    public final static String COURSE_BACKGROUND="COURSE_BACKGROUND";
    public final static String COURSE_MODULE_BACKGROUND="COURSE_MODULE_BACKGROUND";
    public final static String COURSE_MODULE_DETAILS="COURSE_MODULE_DETAILS";
    public final static String COURSE_VIDEO="COURSE_VIDEO";
    public final static String COURSE_VIDEO_DETAILS="COURSE_VIDEO_DETAILS";

    public final static String VIDEO_DOCUMENTS="VIDEO_DOCUMENTS";
    public final static String VIDEO_CODES="VIDEO_CODES";
    public final static String VIDEO_ATTACHMENTS="VIDEO_ATTACHMENTS";

    public final static String PAYMENT_QR_IMAGES="PAYMENT_IMAGES";
    public final static String PAYMENT_QR_SCANWAY_IMAGES="PAYMENT_QR_SCANWAY_IMAGES";

    public final static String MATERIAL_DOWNLOAD="MATERIAL_DOWNLOAD";

    public static List<String> getAllPurposeList()
    {
        List<String> list=new ArrayList<>();
        list.add(COURSE_BACKGROUND);
        list.add(COURSE_MODULE_BACKGROUND);
        list.add(COURSE_MODULE_DETAILS);
        list.add(COURSE_VIDEO);
        list.add(COURSE_VIDEO_DETAILS);
        list.add(VIDEO_DOCUMENTS);
        list.add(VIDEO_CODES);
        list.add(VIDEO_ATTACHMENTS);
        list.add(PAYMENT_QR_IMAGES);
        list.add(PAYMENT_QR_SCANWAY_IMAGES);
        list.add(MATERIAL_DOWNLOAD);

        return list;
    }
}
