package org.telegram.messenger.video;

import android.media.MediaCodec;
import android.media.MediaExtractor;
import java.io.File;
import java.util.ArrayList;
import org.telegram.messenger.FileLog;
import org.telegram.messenger.MediaController;
import org.telegram.messenger.SharedConfig;
import org.telegram.messenger.Utilities;
import org.telegram.messenger.VideoEditedInfo;

/* loaded from: classes3.dex */
public class MediaCodecVideoConvertor {
    private static final int MEDIACODEC_TIMEOUT_DEFAULT = 2500;
    private static final int MEDIACODEC_TIMEOUT_INCREASED = 22000;
    private static final int PROCESSOR_TYPE_INTEL = 2;
    private static final int PROCESSOR_TYPE_MTK = 3;
    private static final int PROCESSOR_TYPE_OTHER = 0;
    private static final int PROCESSOR_TYPE_QCOM = 1;
    private static final int PROCESSOR_TYPE_SEC = 4;
    private static final int PROCESSOR_TYPE_TI = 5;
    private MediaController.VideoConvertorListener callback;
    private long endPresentationTime;
    private MediaExtractor extractor;
    private MP4Builder mediaMuxer;

    public boolean convertVideo(String str, File file, int i, boolean z, int i2, int i3, int i4, int i5, int i6, int i7, int i8, long j, long j2, long j3, boolean z2, long j4, MediaController.SavedFilterState savedFilterState, String str2, ArrayList<VideoEditedInfo.MediaEntity> arrayList, boolean z3, MediaController.CropState cropState, boolean z4, MediaController.VideoConvertorListener videoConvertorListener) {
        this.callback = videoConvertorListener;
        return convertVideoInternal(str, file, i, z, i2, i3, i4, i5, i6, i7, i8, j, j2, j3, j4, z2, false, savedFilterState, str2, arrayList, z3, cropState, z4);
    }

    public long getLastFrameTimestamp() {
        return this.endPresentationTime;
    }

    /* JADX WARN: Can't wrap try/catch for region: R(14:(44:(3:1373|1374|1375)(2:459|(62:1357|1358|1359|1360|1361|1362|465|(4:467|468|(1:1343)(1:472)|473)(1:1348)|(54:1338|1339|(2:1333|1334)|477|478|479|(2:1327|1328)|481|(1:485)|550|551|552|553|554|555|556|557|(9:1301|1302|(1:1304)(1:1314)|1305|(1:1307)|1308|(1:1310)|1311|(1:1313))(1:559)|560|(3:562|563|(38:566|(1:568)|569|570|571|572|573|574|575|577|578|579|580|581|582|583|584|(8:586|587|588|589|590|591|592|(3:594|595|596)(1:1251))(1:1262)|597|598|599|600|601|602|(2:1233|1234)(1:604)|605|(8:607|608|609|(3:1224|1225|(4:1227|612|(1:614)|(12:1161|(8:1163|1164|1165|(1:1167)|1168|1169|(2:1171|1172)(1:1174)|1173)(12:1188|1189|1190|1191|(2:1193|1194)(2:1212|1213)|1195|1196|1197|1198|1199|1200|1201)|(1:619)(1:1160)|620|621|622|(2:(8:629|630|(1:1145)(4:633|634|635|636)|(5:1032|1033|1034|(5:1036|1037|1038|(4:1040|(1:1042)(1:1062)|1043|(1:1045)(1:1061))(1:1063)|1046)(3:1070|(2:1132|(1:1134))(14:1075|1076|1077|(2:1079|(1:1081))(1:1125)|1082|1083|1084|(1:1086)|1087|(2:1089|1090)(2:1123|1124)|1091|(1:1122)(6:1098|1099|1100|1101|1102|(1:1114)(6:1106|1107|(1:1109)|1110|1111|1112))|1115|1112)|1113)|(6:1048|1049|(4:1051|1052|641|(1:(10:646|647|(1:649)(1:1026)|650|651|652|653|(1:655)(4:847|(4:1012|1013|(1:1015)|1016)(2:849|(5:966|967|968|(1:1008)(9:971|972|973|974|975|976|(3:978|979|(5:981|982|983|984|985))(1:996)|995|985)|986)(2:851|(5:853|854|855|(1:857)(1:955)|(8:859|860|(4:870|871|872|(4:(1:877)|(1:916)(3:881|(3:883|884|(2:885|(1:914)(2:887|(1:909)(2:897|898))))(1:915)|899)|900|(3:904|(1:906)|907))(2:917|(11:919|(3:923|(2:929|(2:931|932)(1:939))|940)|945|933|(1:936)|937|938|864|(1:866)(1:869)|867|868)))(1:862)|863|864|(0)(0)|867|868)(3:952|953|954))(3:963|964|965)))|987|868)|(3:844|845|846)(6:657|(7:664|665|666|(1:668)(2:670|(2:672|(3:828|829|(1:831))(1:(26:675|676|(1:678)(1:824)|679|680|681|(3:685|686|687)|693|(4:695|696|697|(6:699|700|701|702|703|(21:705|(3:707|708|709)(4:799|800|801|802)|710|711|712|713|714|(3:716|717|(12:721|722|(1:724)(1:787)|725|(1:786)(2:729|(3:731|(1:733)(1:781)|734)(3:782|(1:784)|785))|(1:736)(4:774|(1:778)|779|780)|(9:745|746|747|(2:766|767)|749|750|751|(4:753|754|755|756)(1:761)|757)(1:738)|739|(3:741|(1:743)|744)|660|661|662))(1:789)|788|722|(0)(0)|725|(1:727)|786|(0)(0)|(0)(0)|739|(0)|660|661|662)(17:806|807|714|(0)(0)|788|722|(0)(0)|725|(0)|786|(0)(0)|(0)(0)|739|(0)|660|661|662)))(1:820)|814|807|714|(0)(0)|788|722|(0)(0)|725|(0)|786|(0)(0)|(0)(0)|739|(0)|660|661|662)(3:825|826|827))))|669|661|662)|659|660|661|662)|663)))|640|641|(11:(0)|646|647|(0)(0)|650|651|652|653|(0)(0)|(0)(0)|663)))(1:638)|639|640|641|(11:(0)|646|647|(0)(0)|650|651|652|653|(0)(0)|(0)(0)|663))|645)|627|509|510|(1:512)|514)(1:616)))|611|612|(0)|(0)(0))(1:1232)|617|(0)(0)|620|621|622|(11:(0)|629|630|(0)|1145|(0)(0)|639|640|641|(11:(0)|646|647|(0)(0)|650|651|652|653|(0)(0)|(0)(0)|663)|645)|627|509|510|(0)|514))|1300|572|573|574|575|577|578|579|580|581|582|583|584|(0)(0)|597|598|599|600|601|602|(0)(0)|605|(0)(0)|617|(0)(0)|620|621|622|(11:(0)|629|630|(0)|1145|(0)(0)|639|640|641|(11:(0)|646|647|(0)(0)|650|651|652|653|(0)(0)|(0)(0)|663)|645)|627|509|510|(0)|514)|475|(0)|477|478|479|(0)|481|(2:483|485)|550|551|552|553|554|555|556|557|(0)(0)|560|(0)|1300|572|573|574|575|577|578|579|580|581|582|583|584|(0)(0)|597|598|599|600|601|602|(0)(0)|605|(0)(0)|617|(0)(0)|620|621|622|(11:(0)|629|630|(0)|1145|(0)(0)|639|640|641|(11:(0)|646|647|(0)(0)|650|651|652|653|(0)(0)|(0)(0)|663)|645)|627|509|510|(0)|514)(3:461|462|463))|552|553|554|555|556|557|(0)(0)|560|(0)|1300|572|573|574|575|577|578|579|580|581|582|583|584|(0)(0)|597|598|599|600|601|602|(0)(0)|605|(0)(0)|617|(0)(0)|620|621|622|(11:(0)|629|630|(0)|1145|(0)(0)|639|640|641|(11:(0)|646|647|(0)(0)|650|651|652|653|(0)(0)|(0)(0)|663)|645)|627|509|510|(0)|514)|465|(0)(0)|(0)|475|(0)|477|478|479|(0)|481|(0)|550|551) */
    /* JADX WARN: Can't wrap try/catch for region: R(21:(24:(3:1373|1374|1375)(2:459|(62:1357|1358|1359|1360|1361|1362|465|(4:467|468|(1:1343)(1:472)|473)(1:1348)|(54:1338|1339|(2:1333|1334)|477|478|479|(2:1327|1328)|481|(1:485)|550|551|552|553|554|555|556|557|(9:1301|1302|(1:1304)(1:1314)|1305|(1:1307)|1308|(1:1310)|1311|(1:1313))(1:559)|560|(3:562|563|(38:566|(1:568)|569|570|571|572|573|574|575|577|578|579|580|581|582|583|584|(8:586|587|588|589|590|591|592|(3:594|595|596)(1:1251))(1:1262)|597|598|599|600|601|602|(2:1233|1234)(1:604)|605|(8:607|608|609|(3:1224|1225|(4:1227|612|(1:614)|(12:1161|(8:1163|1164|1165|(1:1167)|1168|1169|(2:1171|1172)(1:1174)|1173)(12:1188|1189|1190|1191|(2:1193|1194)(2:1212|1213)|1195|1196|1197|1198|1199|1200|1201)|(1:619)(1:1160)|620|621|622|(2:(8:629|630|(1:1145)(4:633|634|635|636)|(5:1032|1033|1034|(5:1036|1037|1038|(4:1040|(1:1042)(1:1062)|1043|(1:1045)(1:1061))(1:1063)|1046)(3:1070|(2:1132|(1:1134))(14:1075|1076|1077|(2:1079|(1:1081))(1:1125)|1082|1083|1084|(1:1086)|1087|(2:1089|1090)(2:1123|1124)|1091|(1:1122)(6:1098|1099|1100|1101|1102|(1:1114)(6:1106|1107|(1:1109)|1110|1111|1112))|1115|1112)|1113)|(6:1048|1049|(4:1051|1052|641|(1:(10:646|647|(1:649)(1:1026)|650|651|652|653|(1:655)(4:847|(4:1012|1013|(1:1015)|1016)(2:849|(5:966|967|968|(1:1008)(9:971|972|973|974|975|976|(3:978|979|(5:981|982|983|984|985))(1:996)|995|985)|986)(2:851|(5:853|854|855|(1:857)(1:955)|(8:859|860|(4:870|871|872|(4:(1:877)|(1:916)(3:881|(3:883|884|(2:885|(1:914)(2:887|(1:909)(2:897|898))))(1:915)|899)|900|(3:904|(1:906)|907))(2:917|(11:919|(3:923|(2:929|(2:931|932)(1:939))|940)|945|933|(1:936)|937|938|864|(1:866)(1:869)|867|868)))(1:862)|863|864|(0)(0)|867|868)(3:952|953|954))(3:963|964|965)))|987|868)|(3:844|845|846)(6:657|(7:664|665|666|(1:668)(2:670|(2:672|(3:828|829|(1:831))(1:(26:675|676|(1:678)(1:824)|679|680|681|(3:685|686|687)|693|(4:695|696|697|(6:699|700|701|702|703|(21:705|(3:707|708|709)(4:799|800|801|802)|710|711|712|713|714|(3:716|717|(12:721|722|(1:724)(1:787)|725|(1:786)(2:729|(3:731|(1:733)(1:781)|734)(3:782|(1:784)|785))|(1:736)(4:774|(1:778)|779|780)|(9:745|746|747|(2:766|767)|749|750|751|(4:753|754|755|756)(1:761)|757)(1:738)|739|(3:741|(1:743)|744)|660|661|662))(1:789)|788|722|(0)(0)|725|(1:727)|786|(0)(0)|(0)(0)|739|(0)|660|661|662)(17:806|807|714|(0)(0)|788|722|(0)(0)|725|(0)|786|(0)(0)|(0)(0)|739|(0)|660|661|662)))(1:820)|814|807|714|(0)(0)|788|722|(0)(0)|725|(0)|786|(0)(0)|(0)(0)|739|(0)|660|661|662)(3:825|826|827))))|669|661|662)|659|660|661|662)|663)))|640|641|(11:(0)|646|647|(0)(0)|650|651|652|653|(0)(0)|(0)(0)|663)))(1:638)|639|640|641|(11:(0)|646|647|(0)(0)|650|651|652|653|(0)(0)|(0)(0)|663))|645)|627|509|510|(1:512)|514)(1:616)))|611|612|(0)|(0)(0))(1:1232)|617|(0)(0)|620|621|622|(11:(0)|629|630|(0)|1145|(0)(0)|639|640|641|(11:(0)|646|647|(0)(0)|650|651|652|653|(0)(0)|(0)(0)|663)|645)|627|509|510|(0)|514))|1300|572|573|574|575|577|578|579|580|581|582|583|584|(0)(0)|597|598|599|600|601|602|(0)(0)|605|(0)(0)|617|(0)(0)|620|621|622|(11:(0)|629|630|(0)|1145|(0)(0)|639|640|641|(11:(0)|646|647|(0)(0)|650|651|652|653|(0)(0)|(0)(0)|663)|645)|627|509|510|(0)|514)|475|(0)|477|478|479|(0)|481|(2:483|485)|550|551|552|553|554|555|556|557|(0)(0)|560|(0)|1300|572|573|574|575|577|578|579|580|581|582|583|584|(0)(0)|597|598|599|600|601|602|(0)(0)|605|(0)(0)|617|(0)(0)|620|621|622|(11:(0)|629|630|(0)|1145|(0)(0)|639|640|641|(11:(0)|646|647|(0)(0)|650|651|652|653|(0)(0)|(0)(0)|663)|645)|627|509|510|(0)|514)(3:461|462|463))|583|584|(0)(0)|597|598|599|600|601|602|(0)(0)|605|(0)(0)|617|(0)(0)|620|621|622|(11:(0)|629|630|(0)|1145|(0)(0)|639|640|641|(11:(0)|646|647|(0)(0)|650|651|652|653|(0)(0)|(0)(0)|663)|645)|627|509|510|(0)|514)|552|553|554|555|556|557|(0)(0)|560|(0)|1300|572|573|574|575|577|578|579|580|581|582) */
    /* JADX WARN: Can't wrap try/catch for region: R(24:(3:1373|1374|1375)(2:459|(62:1357|1358|1359|1360|1361|1362|465|(4:467|468|(1:1343)(1:472)|473)(1:1348)|(54:1338|1339|(2:1333|1334)|477|478|479|(2:1327|1328)|481|(1:485)|550|551|552|553|554|555|556|557|(9:1301|1302|(1:1304)(1:1314)|1305|(1:1307)|1308|(1:1310)|1311|(1:1313))(1:559)|560|(3:562|563|(38:566|(1:568)|569|570|571|572|573|574|575|577|578|579|580|581|582|583|584|(8:586|587|588|589|590|591|592|(3:594|595|596)(1:1251))(1:1262)|597|598|599|600|601|602|(2:1233|1234)(1:604)|605|(8:607|608|609|(3:1224|1225|(4:1227|612|(1:614)|(12:1161|(8:1163|1164|1165|(1:1167)|1168|1169|(2:1171|1172)(1:1174)|1173)(12:1188|1189|1190|1191|(2:1193|1194)(2:1212|1213)|1195|1196|1197|1198|1199|1200|1201)|(1:619)(1:1160)|620|621|622|(2:(8:629|630|(1:1145)(4:633|634|635|636)|(5:1032|1033|1034|(5:1036|1037|1038|(4:1040|(1:1042)(1:1062)|1043|(1:1045)(1:1061))(1:1063)|1046)(3:1070|(2:1132|(1:1134))(14:1075|1076|1077|(2:1079|(1:1081))(1:1125)|1082|1083|1084|(1:1086)|1087|(2:1089|1090)(2:1123|1124)|1091|(1:1122)(6:1098|1099|1100|1101|1102|(1:1114)(6:1106|1107|(1:1109)|1110|1111|1112))|1115|1112)|1113)|(6:1048|1049|(4:1051|1052|641|(1:(10:646|647|(1:649)(1:1026)|650|651|652|653|(1:655)(4:847|(4:1012|1013|(1:1015)|1016)(2:849|(5:966|967|968|(1:1008)(9:971|972|973|974|975|976|(3:978|979|(5:981|982|983|984|985))(1:996)|995|985)|986)(2:851|(5:853|854|855|(1:857)(1:955)|(8:859|860|(4:870|871|872|(4:(1:877)|(1:916)(3:881|(3:883|884|(2:885|(1:914)(2:887|(1:909)(2:897|898))))(1:915)|899)|900|(3:904|(1:906)|907))(2:917|(11:919|(3:923|(2:929|(2:931|932)(1:939))|940)|945|933|(1:936)|937|938|864|(1:866)(1:869)|867|868)))(1:862)|863|864|(0)(0)|867|868)(3:952|953|954))(3:963|964|965)))|987|868)|(3:844|845|846)(6:657|(7:664|665|666|(1:668)(2:670|(2:672|(3:828|829|(1:831))(1:(26:675|676|(1:678)(1:824)|679|680|681|(3:685|686|687)|693|(4:695|696|697|(6:699|700|701|702|703|(21:705|(3:707|708|709)(4:799|800|801|802)|710|711|712|713|714|(3:716|717|(12:721|722|(1:724)(1:787)|725|(1:786)(2:729|(3:731|(1:733)(1:781)|734)(3:782|(1:784)|785))|(1:736)(4:774|(1:778)|779|780)|(9:745|746|747|(2:766|767)|749|750|751|(4:753|754|755|756)(1:761)|757)(1:738)|739|(3:741|(1:743)|744)|660|661|662))(1:789)|788|722|(0)(0)|725|(1:727)|786|(0)(0)|(0)(0)|739|(0)|660|661|662)(17:806|807|714|(0)(0)|788|722|(0)(0)|725|(0)|786|(0)(0)|(0)(0)|739|(0)|660|661|662)))(1:820)|814|807|714|(0)(0)|788|722|(0)(0)|725|(0)|786|(0)(0)|(0)(0)|739|(0)|660|661|662)(3:825|826|827))))|669|661|662)|659|660|661|662)|663)))|640|641|(11:(0)|646|647|(0)(0)|650|651|652|653|(0)(0)|(0)(0)|663)))(1:638)|639|640|641|(11:(0)|646|647|(0)(0)|650|651|652|653|(0)(0)|(0)(0)|663))|645)|627|509|510|(1:512)|514)(1:616)))|611|612|(0)|(0)(0))(1:1232)|617|(0)(0)|620|621|622|(11:(0)|629|630|(0)|1145|(0)(0)|639|640|641|(11:(0)|646|647|(0)(0)|650|651|652|653|(0)(0)|(0)(0)|663)|645)|627|509|510|(0)|514))|1300|572|573|574|575|577|578|579|580|581|582|583|584|(0)(0)|597|598|599|600|601|602|(0)(0)|605|(0)(0)|617|(0)(0)|620|621|622|(11:(0)|629|630|(0)|1145|(0)(0)|639|640|641|(11:(0)|646|647|(0)(0)|650|651|652|653|(0)(0)|(0)(0)|663)|645)|627|509|510|(0)|514)|475|(0)|477|478|479|(0)|481|(2:483|485)|550|551|552|553|554|555|556|557|(0)(0)|560|(0)|1300|572|573|574|575|577|578|579|580|581|582|583|584|(0)(0)|597|598|599|600|601|602|(0)(0)|605|(0)(0)|617|(0)(0)|620|621|622|(11:(0)|629|630|(0)|1145|(0)(0)|639|640|641|(11:(0)|646|647|(0)(0)|650|651|652|653|(0)(0)|(0)(0)|663)|645)|627|509|510|(0)|514)(3:461|462|463))|583|584|(0)(0)|597|598|599|600|601|602|(0)(0)|605|(0)(0)|617|(0)(0)|620|621|622|(11:(0)|629|630|(0)|1145|(0)(0)|639|640|641|(11:(0)|646|647|(0)(0)|650|651|652|653|(0)(0)|(0)(0)|663)|645)|627|509|510|(0)|514) */
    /* JADX WARN: Can't wrap try/catch for region: R(44:(3:1373|1374|1375)(2:459|(62:1357|1358|1359|1360|1361|1362|465|(4:467|468|(1:1343)(1:472)|473)(1:1348)|(54:1338|1339|(2:1333|1334)|477|478|479|(2:1327|1328)|481|(1:485)|550|551|552|553|554|555|556|557|(9:1301|1302|(1:1304)(1:1314)|1305|(1:1307)|1308|(1:1310)|1311|(1:1313))(1:559)|560|(3:562|563|(38:566|(1:568)|569|570|571|572|573|574|575|577|578|579|580|581|582|583|584|(8:586|587|588|589|590|591|592|(3:594|595|596)(1:1251))(1:1262)|597|598|599|600|601|602|(2:1233|1234)(1:604)|605|(8:607|608|609|(3:1224|1225|(4:1227|612|(1:614)|(12:1161|(8:1163|1164|1165|(1:1167)|1168|1169|(2:1171|1172)(1:1174)|1173)(12:1188|1189|1190|1191|(2:1193|1194)(2:1212|1213)|1195|1196|1197|1198|1199|1200|1201)|(1:619)(1:1160)|620|621|622|(2:(8:629|630|(1:1145)(4:633|634|635|636)|(5:1032|1033|1034|(5:1036|1037|1038|(4:1040|(1:1042)(1:1062)|1043|(1:1045)(1:1061))(1:1063)|1046)(3:1070|(2:1132|(1:1134))(14:1075|1076|1077|(2:1079|(1:1081))(1:1125)|1082|1083|1084|(1:1086)|1087|(2:1089|1090)(2:1123|1124)|1091|(1:1122)(6:1098|1099|1100|1101|1102|(1:1114)(6:1106|1107|(1:1109)|1110|1111|1112))|1115|1112)|1113)|(6:1048|1049|(4:1051|1052|641|(1:(10:646|647|(1:649)(1:1026)|650|651|652|653|(1:655)(4:847|(4:1012|1013|(1:1015)|1016)(2:849|(5:966|967|968|(1:1008)(9:971|972|973|974|975|976|(3:978|979|(5:981|982|983|984|985))(1:996)|995|985)|986)(2:851|(5:853|854|855|(1:857)(1:955)|(8:859|860|(4:870|871|872|(4:(1:877)|(1:916)(3:881|(3:883|884|(2:885|(1:914)(2:887|(1:909)(2:897|898))))(1:915)|899)|900|(3:904|(1:906)|907))(2:917|(11:919|(3:923|(2:929|(2:931|932)(1:939))|940)|945|933|(1:936)|937|938|864|(1:866)(1:869)|867|868)))(1:862)|863|864|(0)(0)|867|868)(3:952|953|954))(3:963|964|965)))|987|868)|(3:844|845|846)(6:657|(7:664|665|666|(1:668)(2:670|(2:672|(3:828|829|(1:831))(1:(26:675|676|(1:678)(1:824)|679|680|681|(3:685|686|687)|693|(4:695|696|697|(6:699|700|701|702|703|(21:705|(3:707|708|709)(4:799|800|801|802)|710|711|712|713|714|(3:716|717|(12:721|722|(1:724)(1:787)|725|(1:786)(2:729|(3:731|(1:733)(1:781)|734)(3:782|(1:784)|785))|(1:736)(4:774|(1:778)|779|780)|(9:745|746|747|(2:766|767)|749|750|751|(4:753|754|755|756)(1:761)|757)(1:738)|739|(3:741|(1:743)|744)|660|661|662))(1:789)|788|722|(0)(0)|725|(1:727)|786|(0)(0)|(0)(0)|739|(0)|660|661|662)(17:806|807|714|(0)(0)|788|722|(0)(0)|725|(0)|786|(0)(0)|(0)(0)|739|(0)|660|661|662)))(1:820)|814|807|714|(0)(0)|788|722|(0)(0)|725|(0)|786|(0)(0)|(0)(0)|739|(0)|660|661|662)(3:825|826|827))))|669|661|662)|659|660|661|662)|663)))|640|641|(11:(0)|646|647|(0)(0)|650|651|652|653|(0)(0)|(0)(0)|663)))(1:638)|639|640|641|(11:(0)|646|647|(0)(0)|650|651|652|653|(0)(0)|(0)(0)|663))|645)|627|509|510|(1:512)|514)(1:616)))|611|612|(0)|(0)(0))(1:1232)|617|(0)(0)|620|621|622|(11:(0)|629|630|(0)|1145|(0)(0)|639|640|641|(11:(0)|646|647|(0)(0)|650|651|652|653|(0)(0)|(0)(0)|663)|645)|627|509|510|(0)|514))|1300|572|573|574|575|577|578|579|580|581|582|583|584|(0)(0)|597|598|599|600|601|602|(0)(0)|605|(0)(0)|617|(0)(0)|620|621|622|(11:(0)|629|630|(0)|1145|(0)(0)|639|640|641|(11:(0)|646|647|(0)(0)|650|651|652|653|(0)(0)|(0)(0)|663)|645)|627|509|510|(0)|514)|475|(0)|477|478|479|(0)|481|(2:483|485)|550|551|552|553|554|555|556|557|(0)(0)|560|(0)|1300|572|573|574|575|577|578|579|580|581|582|583|584|(0)(0)|597|598|599|600|601|602|(0)(0)|605|(0)(0)|617|(0)(0)|620|621|622|(11:(0)|629|630|(0)|1145|(0)(0)|639|640|641|(11:(0)|646|647|(0)(0)|650|651|652|653|(0)(0)|(0)(0)|663)|645)|627|509|510|(0)|514)(3:461|462|463))|552|553|554|555|556|557|(0)(0)|560|(0)|1300|572|573|574|575|577|578|579|580|581|582|583|584|(0)(0)|597|598|599|600|601|602|(0)(0)|605|(0)(0)|617|(0)(0)|620|621|622|(11:(0)|629|630|(0)|1145|(0)(0)|639|640|641|(11:(0)|646|647|(0)(0)|650|651|652|653|(0)(0)|(0)(0)|663)|645)|627|509|510|(0)|514) */
    /* JADX WARN: Code restructure failed: missing block: B:1152:0x153b, code lost:
    
        r0 = e;
     */
    /* JADX WARN: Code restructure failed: missing block: B:1153:0x153c, code lost:
    
        r14 = r84;
        r4 = r91;
        r8 = r15;
        r66 = r65;
        r47 = r11;
        r64 = r9;
        r56 = r12;
        r3 = r18;
        r7 = r21;
        r6 = -5;
     */
    /* JADX WARN: Code restructure failed: missing block: B:1155:0x1535, code lost:
    
        r0 = th;
     */
    /* JADX WARN: Code restructure failed: missing block: B:1156:0x1536, code lost:
    
        r14 = r84;
     */
    /* JADX WARN: Code restructure failed: missing block: B:1157:0x15e8, code lost:
    
        r8 = r15;
     */
    /* JADX WARN: Code restructure failed: missing block: B:1239:0x1558, code lost:
    
        r0 = e;
     */
    /* JADX WARN: Code restructure failed: missing block: B:1240:0x1559, code lost:
    
        r8 = r75;
        r14 = r84;
        r12 = r89;
        r4 = r91;
        r66 = r65;
        r47 = r13;
     */
    /* JADX WARN: Code restructure failed: missing block: B:1241:0x1583, code lost:
    
        r56 = r12;
        r3 = r18;
        r7 = r21;
        r6 = -5;
     */
    /* JADX WARN: Code restructure failed: missing block: B:1244:0x156d, code lost:
    
        r0 = e;
     */
    /* JADX WARN: Code restructure failed: missing block: B:1245:0x156e, code lost:
    
        r8 = r75;
        r14 = r84;
        r12 = r89;
        r4 = r91;
        r66 = r65;
        r47 = r13;
        r5 = r13;
     */
    /* JADX WARN: Code restructure failed: missing block: B:1266:0x1597, code lost:
    
        r0 = e;
     */
    /* JADX WARN: Code restructure failed: missing block: B:1267:0x1598, code lost:
    
        r8 = r75;
        r14 = r84;
        r3 = r13;
        r12 = r89;
        r4 = r91;
        r66 = r65;
     */
    /* JADX WARN: Code restructure failed: missing block: B:1268:0x15bb, code lost:
    
        r5 = r3;
        r56 = r12;
        r3 = r18;
        r7 = r21;
     */
    /* JADX WARN: Code restructure failed: missing block: B:1269:0x15da, code lost:
    
        r6 = -5;
        r47 = null;
     */
    /* JADX WARN: Code restructure failed: missing block: B:1271:0x15a9, code lost:
    
        r0 = e;
     */
    /* JADX WARN: Code restructure failed: missing block: B:1272:0x15aa, code lost:
    
        r3 = r13;
        r12 = r89;
        r30 = r91;
        r4 = r5;
        r21 = r6;
        r8 = r15;
        r66 = r4;
        r62 = r2;
     */
    /* JADX WARN: Code restructure failed: missing block: B:1274:0x15c3, code lost:
    
        r0 = e;
     */
    /* JADX WARN: Code restructure failed: missing block: B:1275:0x15c4, code lost:
    
        r30 = r91;
        r4 = r5;
        r8 = r15;
        r66 = r4;
        r62 = r2;
        r56 = r89;
        r3 = r18;
        r7 = r6;
        r5 = null;
     */
    /* JADX WARN: Code restructure failed: missing block: B:1276:0x15fa, code lost:
    
        r0 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:1277:0x15fb, code lost:
    
        r30 = r91;
        r4 = r5;
        r8 = r15;
        r66 = r4;
        r1 = r0;
        r56 = r89;
        r3 = r18;
        r7 = r6;
        r5 = null;
        r6 = -5;
     */
    /* JADX WARN: Code restructure failed: missing block: B:1278:0x15df, code lost:
    
        r0 = th;
     */
    /* JADX WARN: Code restructure failed: missing block: B:1279:0x15e0, code lost:
    
        r12 = r89;
        r30 = r91;
        r21 = r6;
     */
    /* JADX WARN: Code restructure failed: missing block: B:1281:0x1625, code lost:
    
        r0 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:1282:0x1626, code lost:
    
        r30 = r91;
        r4 = r5;
        r8 = r15;
        r66 = r4;
        r1 = r0;
        r56 = r89;
        r3 = r18;
     */
    /* JADX WARN: Code restructure failed: missing block: B:1283:0x1612, code lost:
    
        r0 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:1284:0x1613, code lost:
    
        r30 = r91;
        r8 = r15;
        r2 = r82;
        r4 = r83;
        r5 = r0;
        r56 = r89;
        r3 = r18;
     */
    /* JADX WARN: Code restructure failed: missing block: B:1316:0x1637, code lost:
    
        r0 = e;
     */
    /* JADX WARN: Code restructure failed: missing block: B:1317:0x1638, code lost:
    
        r12 = r89;
        r30 = r91;
        r4 = r5;
        r8 = r15;
        r66 = r4;
     */
    /* JADX WARN: Code restructure failed: missing block: B:1318:0x165f, code lost:
    
        r1 = r0;
        r56 = r12;
     */
    /* JADX WARN: Code restructure failed: missing block: B:1324:0x1645, code lost:
    
        r0 = e;
     */
    /* JADX WARN: Code restructure failed: missing block: B:1325:0x1655, code lost:
    
        r12 = r89;
        r30 = r91;
        r66 = r4;
        r4 = r5;
        r8 = r15;
     */
    /* JADX WARN: Code restructure failed: missing block: B:1329:0x1652, code lost:
    
        r0 = e;
     */
    /* JADX WARN: Code restructure failed: missing block: B:1330:0x1653, code lost:
    
        r14 = r84;
     */
    /* JADX WARN: Code restructure failed: missing block: B:1331:0x1647, code lost:
    
        r0 = th;
     */
    /* JADX WARN: Code restructure failed: missing block: B:1332:0x1648, code lost:
    
        r14 = r84;
     */
    /* JADX WARN: Code restructure failed: missing block: B:161:0x025b, code lost:
    
        r1 = r2;
        r2 = r3;
        r3 = r4;
        r4 = r6;
        r5 = r7;
        r6 = r8;
        r7 = r9;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:1026:0x0f9f  */
    /* JADX WARN: Removed duplicated region for block: B:1032:0x0d95 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:113:0x069d A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:1160:0x0d19  */
    /* JADX WARN: Removed duplicated region for block: B:1161:0x0c04 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:1232:0x0d05  */
    /* JADX WARN: Removed duplicated region for block: B:1233:0x0b8d A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:1262:0x0b70  */
    /* JADX WARN: Removed duplicated region for block: B:1301:0x09e2 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:1327:0x0974 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:1333:0x0951 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:1338:0x0949 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:1348:0x0940  */
    /* JADX WARN: Removed duplicated region for block: B:1409:0x1737  */
    /* JADX WARN: Removed duplicated region for block: B:236:0x0480  */
    /* JADX WARN: Removed duplicated region for block: B:238:0x0482  */
    /* JADX WARN: Removed duplicated region for block: B:39:0x065c A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:435:0x07b3  */
    /* JADX WARN: Removed duplicated region for block: B:467:0x091b  */
    /* JADX WARN: Removed duplicated region for block: B:483:0x09bc A[Catch: all -> 0x0932, Exception -> 0x0999, TRY_ENTER, TryCatch #8 {all -> 0x0932, blocks: (B:472:0x0927, B:1339:0x0949, B:1334:0x0951, B:1328:0x0974, B:483:0x09bc, B:485:0x09c2, B:1343:0x092c), top: B:465:0x0919 }] */
    /* JADX WARN: Removed duplicated region for block: B:495:0x16af A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:512:0x16f9 A[Catch: all -> 0x170a, TRY_LEAVE, TryCatch #134 {all -> 0x170a, blocks: (B:510:0x16f0, B:512:0x16f9), top: B:509:0x16f0 }] */
    /* JADX WARN: Removed duplicated region for block: B:517:0x1766 A[Catch: all -> 0x1758, TryCatch #36 {all -> 0x1758, blocks: (B:529:0x1754, B:517:0x1766, B:519:0x176b, B:521:0x1773, B:522:0x1776), top: B:528:0x1754 }] */
    /* JADX WARN: Removed duplicated region for block: B:519:0x176b A[Catch: all -> 0x1758, TryCatch #36 {all -> 0x1758, blocks: (B:529:0x1754, B:517:0x1766, B:519:0x176b, B:521:0x1773, B:522:0x1776), top: B:528:0x1754 }] */
    /* JADX WARN: Removed duplicated region for block: B:521:0x1773 A[Catch: all -> 0x1758, TryCatch #36 {all -> 0x1758, blocks: (B:529:0x1754, B:517:0x1766, B:519:0x176b, B:521:0x1773, B:522:0x1776), top: B:528:0x1754 }] */
    /* JADX WARN: Removed duplicated region for block: B:528:0x1754 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:559:0x0a49  */
    /* JADX WARN: Removed duplicated region for block: B:55:0x06ab A[Catch: all -> 0x06a1, TryCatch #70 {all -> 0x06a1, blocks: (B:114:0x069d, B:55:0x06ab, B:57:0x06b0, B:58:0x06b6), top: B:113:0x069d }] */
    /* JADX WARN: Removed duplicated region for block: B:562:0x0a4f A[Catch: all -> 0x0a34, Exception -> 0x0a3b, TRY_LEAVE, TryCatch #149 {Exception -> 0x0a3b, all -> 0x0a34, blocks: (B:1302:0x09e2, B:1304:0x09ea, B:1305:0x09fa, B:1307:0x0a02, B:1308:0x0a0d, B:1310:0x0a15, B:1311:0x0a20, B:1313:0x0a28, B:562:0x0a4f), top: B:1301:0x09e2 }] */
    /* JADX WARN: Removed duplicated region for block: B:57:0x06b0 A[Catch: all -> 0x06a1, TryCatch #70 {all -> 0x06a1, blocks: (B:114:0x069d, B:55:0x06ab, B:57:0x06b0, B:58:0x06b6), top: B:113:0x069d }] */
    /* JADX WARN: Removed duplicated region for block: B:586:0x0b0c  */
    /* JADX WARN: Removed duplicated region for block: B:604:0x0baf  */
    /* JADX WARN: Removed duplicated region for block: B:607:0x0bb5  */
    /* JADX WARN: Removed duplicated region for block: B:614:0x0c01  */
    /* JADX WARN: Removed duplicated region for block: B:616:0x0cdd  */
    /* JADX WARN: Removed duplicated region for block: B:619:0x0d16  */
    /* JADX WARN: Removed duplicated region for block: B:624:0x0d3d A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:62:0x1786  */
    /* JADX WARN: Removed duplicated region for block: B:632:0x0d63 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:638:0x0f51  */
    /* JADX WARN: Removed duplicated region for block: B:643:0x0f75 A[ADDED_TO_REGION, EDGE_INSN: B:643:0x0f75->B:644:0x0f78 BREAK  A[LOOP:5: B:642:0x0f73->B:663:0x0f73]] */
    /* JADX WARN: Removed duplicated region for block: B:649:0x0f96  */
    /* JADX WARN: Removed duplicated region for block: B:655:0x0fae  */
    /* JADX WARN: Removed duplicated region for block: B:657:0x120f A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:67:0x1821  */
    /* JADX WARN: Removed duplicated region for block: B:70:0x1853  */
    /* JADX WARN: Removed duplicated region for block: B:716:0x133d  */
    /* JADX WARN: Removed duplicated region for block: B:724:0x135d  */
    /* JADX WARN: Removed duplicated region for block: B:727:0x1365  */
    /* JADX WARN: Removed duplicated region for block: B:736:0x13aa  */
    /* JADX WARN: Removed duplicated region for block: B:738:0x13ff  */
    /* JADX WARN: Removed duplicated region for block: B:741:0x1409 A[Catch: all -> 0x14c0, Exception -> 0x14c2, TryCatch #68 {Exception -> 0x14c2, blocks: (B:756:0x13ef, B:739:0x1403, B:741:0x1409, B:743:0x140d, B:744:0x1412, B:826:0x1429, B:827:0x1445, B:953:0x1479, B:954:0x149b, B:964:0x14a1, B:965:0x14bf), top: B:755:0x13ef }] */
    /* JADX WARN: Removed duplicated region for block: B:745:0x13c8 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:74:0x178d A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:774:0x13b1  */
    /* JADX WARN: Removed duplicated region for block: B:787:0x1360  */
    /* JADX WARN: Removed duplicated region for block: B:789:0x1353  */
    /* JADX WARN: Removed duplicated region for block: B:844:0x11f4 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:847:0x0fc6  */
    /* JADX WARN: Removed duplicated region for block: B:866:0x11e7  */
    /* JADX WARN: Removed duplicated region for block: B:869:0x11e9  */
    /* JADX WARN: Removed duplicated region for block: B:89:0x1801  */
    /* JADX WARN: Removed duplicated region for block: B:93:0x1808 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Type inference failed for: r6v207 */
    /* JADX WARN: Type inference failed for: r6v208 */
    /* JADX WARN: Type inference failed for: r6v39 */
    /* JADX WARN: Type inference failed for: r6v41 */
    /* JADX WARN: Unreachable blocks removed: 2, instructions: 11 */
    /* JADX WARN: Unreachable blocks removed: 2, instructions: 12 */
    @android.annotation.TargetApi(18)
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private boolean convertVideoInternal(java.lang.String r76, java.io.File r77, int r78, boolean r79, int r80, int r81, int r82, int r83, int r84, int r85, int r86, long r87, long r89, long r91, long r93, boolean r95, boolean r96, org.telegram.messenger.MediaController.SavedFilterState r97, java.lang.String r98, java.util.ArrayList<org.telegram.messenger.VideoEditedInfo.MediaEntity> r99, boolean r100, org.telegram.messenger.MediaController.CropState r101, boolean r102) {
        /*
            Method dump skipped, instructions count: 6349
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.messenger.video.MediaCodecVideoConvertor.convertVideoInternal(java.lang.String, java.io.File, int, boolean, int, int, int, int, int, int, int, long, long, long, long, boolean, boolean, org.telegram.messenger.MediaController$SavedFilterState, java.lang.String, java.util.ArrayList, boolean, org.telegram.messenger.MediaController$CropState, boolean):boolean");
    }

    private boolean isMediatekAvcEncoder(MediaCodec mediaCodec) {
        return mediaCodec.getName().equals("c2.mtk.avc.encoder");
    }

    /* JADX WARN: Code restructure failed: missing block: B:52:0x0123, code lost:
    
        if (r9[r6 + 3] != 1) goto L76;
     */
    /* JADX WARN: Removed duplicated region for block: B:106:0x01d5  */
    /* JADX WARN: Removed duplicated region for block: B:36:0x00df  */
    /* JADX WARN: Removed duplicated region for block: B:89:0x01d0  */
    /* JADX WARN: Removed duplicated region for block: B:92:0x01ec  */
    /* JADX WARN: Removed duplicated region for block: B:95:0x01ee  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private long readAndWriteTracks(android.media.MediaExtractor r29, org.telegram.messenger.video.MP4Builder r30, android.media.MediaCodec.BufferInfo r31, long r32, long r34, long r36, java.io.File r38, boolean r39) throws java.lang.Exception {
        /*
            Method dump skipped, instructions count: 524
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.messenger.video.MediaCodecVideoConvertor.readAndWriteTracks(android.media.MediaExtractor, org.telegram.messenger.video.MP4Builder, android.media.MediaCodec$BufferInfo, long, long, long, java.io.File, boolean):long");
    }

    private void checkConversionCanceled() {
        MediaController.VideoConvertorListener videoConvertorListener = this.callback;
        if (videoConvertorListener != null && videoConvertorListener.checkConversionCanceled()) {
            throw new ConversionCanceledException();
        }
    }

    private static String createFragmentShader(int i, int i2, int i3, int i4, boolean z) {
        int clamp = (int) Utilities.clamp((Math.max(i, i2) / Math.max(i4, i3)) * 0.8f, 2.0f, 1.0f);
        if (clamp > 1 && SharedConfig.deviceIsAverage()) {
            clamp = 1;
        }
        FileLog.d("source size " + i + "x" + i2 + "    dest size " + i3 + i4 + "   kernelRadius " + clamp);
        if (z) {
            return "#extension GL_OES_EGL_image_external : require\nprecision mediump float;\nvarying vec2 vTextureCoord;\nconst float kernel = " + clamp + ".0;\nconst float pixelSizeX = 1.0 / " + i + ".0;\nconst float pixelSizeY = 1.0 / " + i2 + ".0;\nuniform samplerExternalOES sTexture;\nvoid main() {\nvec3 accumulation = vec3(0);\nvec3 weightsum = vec3(0);\nfor (float x = -kernel; x <= kernel; x++){\n   for (float y = -kernel; y <= kernel; y++){\n       accumulation += texture2D(sTexture, vTextureCoord + vec2(x * pixelSizeX, y * pixelSizeY)).xyz;\n       weightsum += 1.0;\n   }\n}\ngl_FragColor = vec4(accumulation / weightsum, 1.0);\n}\n";
        }
        return "precision mediump float;\nvarying vec2 vTextureCoord;\nconst float kernel = " + clamp + ".0;\nconst float pixelSizeX = 1.0 / " + i2 + ".0;\nconst float pixelSizeY = 1.0 / " + i + ".0;\nuniform sampler2D sTexture;\nvoid main() {\nvec3 accumulation = vec3(0);\nvec3 weightsum = vec3(0);\nfor (float x = -kernel; x <= kernel; x++){\n   for (float y = -kernel; y <= kernel; y++){\n       accumulation += texture2D(sTexture, vTextureCoord + vec2(x * pixelSizeX, y * pixelSizeY)).xyz;\n       weightsum += 1.0;\n   }\n}\ngl_FragColor = vec4(accumulation / weightsum, 1.0);\n}\n";
    }

    public class ConversionCanceledException extends RuntimeException {
        public ConversionCanceledException() {
            super("canceled conversion");
        }
    }
}
