package com.togo.home.data.remote.response;

import android.text.TextUtils;

import java.io.Serializable;
import java.util.List;

public class PatientFirstPageModel implements Serializable {
    private static final long serialVersionUID = -5504365222891340145L;

    public static final int NOTICE_DEFAULT = -0x100; // 默认状态,用于兼容老版本
    public static final int NOTICE_NONE = 0; // 不弹框
    public static final int NOTICE_ONCE = 1; // 弹一次
    public static final int NOTICE_ALWAYS = 2; // 每次都弹

    private String hospital_guid;
    private String hospital_image;
    private String hospital_name;
    private String customer_service;
    private String hospital_telephone;
    private String customer_service_xbkp;
    private List<String> wish_words;
    private String aboutQrCode;
    private String aboutHospitalName;
    private String checkoutName;
    private String invalidQrCode;
    private String firstPageScanNote;
    private String braceletScanNote;
    private IssuedCard call_consultant_entry; //专家在线
    private IssuedCard prescription_entry; //底价购药
    private IssuedCard patient_club_entry; //病友圈
    private String inPatientBillGuide;
    private List<Entrys> entrys;
    private AssistantInfo shopping_drug_assistant_info; //购药助手信息
    private String copyright;
    private String emergency_telephone;
    private Notices infos;
    private List<NetHosButtonItem> buttons;
    private NetHosDiseases diseases;
    private News news;
    private Feed feed;
    private List<BannerInfo> marquee;
    private int is_always_show_yuyue = NOTICE_DEFAULT; // 0:不弹框，1:弹框一次，2:每次都弹
    private int is_always_show_dangri = NOTICE_DEFAULT; // 0:不弹框，1:弹框一次，2:每次都弹


    /**
     * 控制分享按钮的显示
     * 1：显示   0：不显示
     */
    private String showShareApp;
    public static final String SHOW_SHARE_ICON = "1";
    //综合服务电话
    private String comprehensive_service_telephone;

    //-------------------------云信账号信息-----------------//
    private NimAccount video_account;

    public void setShopping_drug_assistant_info(AssistantInfo shoppingDrugAssistantInfo) {
        this.shopping_drug_assistant_info = shoppingDrugAssistantInfo;
    }

    public AssistantInfo getShopping_drug_assistant_info() {
        return shopping_drug_assistant_info;
    }

    public String getAboutQrCode() {
        return aboutQrCode;
    }

    public void setAboutQrCode(String aboutQrCode) {
        this.aboutQrCode = aboutQrCode;
    }

    public String getAboutHospitalName() {
        return aboutHospitalName;
    }

    public void setAboutHospitalName(String aboutHospitalName) {
        this.aboutHospitalName = aboutHospitalName;
    }

    public String getCheckoutName() {
        return checkoutName;
    }

    public void setCheckoutName(String checkoutName) {
        this.checkoutName = checkoutName;
    }

    public String getInvalidQrCode() {
        return invalidQrCode;
    }

    public void setInvalidQrCode(String invalidQrCode) {
        this.invalidQrCode = invalidQrCode;
    }

    public String getFirstPageScanNote() {
        return firstPageScanNote;
    }

    public void setFirstPageScanNote(String firstPageScanNote) {
        this.firstPageScanNote = firstPageScanNote;
    }

    public String getBraceletScanNote() {
        return braceletScanNote;
    }

    public void setBraceletScanNote(String braceletScanNote) {
        this.braceletScanNote = braceletScanNote;
    }

    public List<String> getWish_words() {
        return wish_words;
    }

    public void setWish_words(List<String> wishWords) {
        this.wish_words = wishWords;
    }

    public String getHospital_image() {
        return hospital_image;
    }

    public void setHospital_image(String hospitalImage) {
        this.hospital_image = hospitalImage;
    }

    public String getHospital_name() {
        return hospital_name;
    }

    public void setHospital_name(String hospitalName) {
        this.hospital_name = hospitalName;
    }

    public String getCustomer_service() {
        return customer_service;
    }

    public void setCustomer_service(String customerService) {
        this.customer_service = customerService;
    }

    public String getHospital_guid() {
        return hospital_guid;
    }

    public void setHospital_guid(String hospitalGuid) {
        this.hospital_guid = hospitalGuid;
    }

    /**
     * @return the hospital_telephone
     */
    public String getHospital_telephone() {
        return hospital_telephone;
    }

    /**
     * @param hospitalTelephone the hospital_telephone to set
     */
    public void setHospital_telephone(String hospitalTelephone) {
        this.hospital_telephone = hospitalTelephone;
    }

    public String getCustomer_service_xbkp() {
        return customer_service_xbkp;
    }

    public void setCustomer_service_xbkp(String customerServiceXbkp) {
        this.customer_service_xbkp = customerServiceXbkp;
    }

    public IssuedCard getCall_consultant_entry() {
        return call_consultant_entry;
    }

    public void setCall_consultant_entry(IssuedCard callConsultantEntry) {
        this.call_consultant_entry = callConsultantEntry;
    }

    public IssuedCard getPrescription_entry() {
        return prescription_entry;
    }

    public void setPrescription_entry(IssuedCard prescriptionEntry) {
        this.prescription_entry = prescriptionEntry;
    }

    public IssuedCard getPatient_club_entry() {
        return patient_club_entry;
    }

    public void setPatient_club_entry(IssuedCard patientClubEntry) {
        this.patient_club_entry = patientClubEntry;
    }

    public List<Entrys> getEntrys() {
        return entrys;
    }

    public void setEntrys(List<Entrys> entrys) {
        this.entrys = entrys;
    }

    public String getInPatientBillGuide() {
        return inPatientBillGuide;
    }

    public void setInPatientBillGuide(String inPatientBillGuide) {
        this.inPatientBillGuide = inPatientBillGuide;
    }

    public void setCopyright(String copyright) {
        this.copyright = copyright;
    }

    public String getCopyright() {
        return copyright;
    }

    public String getEmergency_telephone() {
        return emergency_telephone;
    }

    public void setEmergency_telephone(String emergencyTelephone) {
        this.emergency_telephone = emergencyTelephone;
    }

    public News getNews() {
        return news;
    }

    public Feed getFeed() {
        return feed;
    }

    public void setFeed(Feed feed) {
        this.feed = feed;
    }

    public void setNews(News news) {
        this.news = news;
    }

    public String getShowShareApp() {
        return showShareApp;
    }

    public void setShowShareApp(String showShareApp) {
        this.showShareApp = showShareApp;
    }

    public boolean isShowShareIcon() {
        return TextUtils.equals(SHOW_SHARE_ICON, showShareApp);
    }

    public String getComprehensive_service_telephone() {
        return comprehensive_service_telephone;
    }

    public void setComprehensive_service_telephone(String comprehensiveServiceTelephone) {
        this.comprehensive_service_telephone = comprehensiveServiceTelephone;
    }

    public List<BannerInfo> getMarquee() {
        return marquee;
    }

    public void setMarquee(List<BannerInfo> marquee) {
        this.marquee = marquee;
    }

    public int getIs_always_show_yuyue() {
        return is_always_show_yuyue;
    }

    public void setIs_always_show_yuyue(int isAlwaysShowYuyue) {
        this.is_always_show_yuyue = isAlwaysShowYuyue;
    }

    public int getIs_always_show_dangri() {
        return is_always_show_dangri;
    }

    public void setIs_always_show_dangri(int isAlwaysShowDangri) {
        this.is_always_show_dangri = isAlwaysShowDangri;
    }

    public NimAccount getVideo_account() {
        return video_account;
    }

    public void setVideo_account(NimAccount video_account) {
        this.video_account = video_account;
    }

    public Notices getInfos() {
        return infos;
    }

    public void setInfos(Notices notices) {
        this.infos = notices;
    }

    public List<NetHosButtonItem> getButtons() {
        return buttons;
    }

    public void setButtons(List<NetHosButtonItem> list) {
        this.buttons = list;
    }

    public NetHosDiseases getDiseases() {
        return diseases;
    }

    public void setDiseases(NetHosDiseases diseases) {
        this.diseases = diseases;
    }
}
