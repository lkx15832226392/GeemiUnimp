package com.geemi.facelibrary.model;

import java.util.List;

public class DictItemsModel {

    /**
     * success : true
     * message : 操作成功！
     * code : 0
     * result : [{"value":"zx_zxs","text":"咨询-咨询师","title":"咨询-咨询师"},{"value":"sg_jd","text":"施工-机电","title":"施工-机电"},{"value":"sg_mg","text":"施工-木工","title":"施工-木工"},{"value":"sg_lg","text":"施工-篱工","title":"施工-篱工"},{"value":"sg_ng","text":"施工-砌筑工","title":"施工-砌筑工"},{"value":"sg_gjg","text":"施工-钢筋工","title":"施工-钢筋工"},{"value":"kc_kcsjs","text":"勘察-勘察设计师","title":"勘察-勘察设计师"},{"value":"sj_sjs","text":"审计-审计师","title":"审计-审计师"},{"value":"030","text":"架子工","title":"架子工"},{"value":"sj_xmfzr","text":"设计-项目负责人","title":"设计-项目负责人"},{"value":"hntg","text":"混凝土工","title":"混凝土工"},{"value":"sheji_sjs","text":"设计-设计师","title":"设计-设计师"},{"value":"050","text":"模板工","title":"模板工"},{"value":"zaojiashi","text":"造价-造价师","title":"造价-造价师"},{"value":"060","text":"机械设备安装工","title":"机械设备安装工"},{"value":"070","text":"通风工","title":"通风工"},{"value":"sg_xmjl","text":"施工-项目经理","title":"施工-项目经理"},{"value":"080","text":"安装起重工","title":"安装起重工"},{"value":"sg_xmzg","text":"施工-项目总工","title":"施工-项目总工"},{"value":"090","text":"安装钳工","title":"安装钳工"},{"value":"sg_zygz","text":"施工-专业工长","title":"施工-专业工长"},{"value":"100","text":"电气设备安装调试工","title":"电气设备安装调试工"},{"value":"sg_sgy","text":"施工-施工员","title":"施工-施工员"},{"value":"110","text":"管道工","title":"管道工"},{"value":"sg_jsy","text":"施工-技术员","title":"施工-技术员"},{"value":"120","text":"变电安装工","title":"变电安装工"},{"value":"sg_zyzljcy","text":"施工-专业质量检查员","title":"施工-专业质量检查员"},{"value":"130","text":"建筑电工","title":"建筑电工"},{"value":"sg_aqy","text":"施工-安全员","title":"施工-安全员"},{"value":"140","text":"司泵工","title":"司泵工"},{"value":"sg_jxy","text":"施工-机械员","title":"施工-机械员"},{"value":"150","text":"挖掘铲运和桩工机械司机","title":"挖掘铲运和桩工机械司机"},{"value":"sg_ysy","text":"施工-预算员","title":"施工-预算员"},{"value":"160","text":"桩机操作工","title":"桩机操作工"},{"value":"sg_cly","text":"施工-材料员","title":"施工-材料员"},{"value":"170","text":"起重信号工","title":"起重信号工"},{"value":"sg_lwy","text":"施工-劳务员","title":"施工-劳务员"},{"value":"180","text":"建筑起重机械安装拆卸工","title":"建筑起重机械安装拆卸工"},{"value":"sg_zjy","text":"施工-质检员","title":"施工-质检员"},{"value":"190","text":"装饰装修工","title":"装饰装修工"},{"value":"sg_zly","text":"施工-资料员","title":"施工-资料员"},{"value":"200","text":"室内成套设施安装工","title":"室内成套设施安装工"},{"value":"jl_zjlgcs","text":"监理-总监理工程师","title":"监理-总监理工程师"},{"value":"210","text":"建筑门窗幕墙安装工","title":"建筑门窗幕墙安装工"},{"value":"jc_zjy","text":"检测-质检员","title":"检测-质检员"},{"value":"220","text":"幕墙制作工","title":"幕墙制作工"},{"value":"sz_jgy","text":"市政-监管员","title":"市政-监管员"},{"value":"230","text":"防水工","title":"防水工"},{"value":"250","text":"石工","title":"石工"},{"value":"270","text":"电焊工","title":"电焊工"},{"value":"280","text":"爆破工","title":"爆破工"},{"value":"290","text":"除尘工","title":"除尘工"},{"value":"300","text":"测量放线工","title":"测量放线工"},{"value":"310","text":"线路架设工","title":"线路架设工"},{"value":"320","text":"古建筑传统石工","title":"古建筑传统石工"},{"value":"330","text":"古建筑传统瓦工","title":"古建筑传统瓦工"},{"value":"340","text":"古建筑传统彩画工","title":"古建筑传统彩画工"},{"value":"350","text":"古建筑传统木工","title":"古建筑传统木工"},{"value":"360","text":"古建筑传统油工","title":"古建筑传统油工"},{"value":"380","text":"金属工","title":"金属工"},{"value":"390","text":"杂工","title":"杂工"},{"value":"900","text":"管理人员","title":"管理人员"},{"value":"1000","text":"其它","title":"其它"},{"value":"zjlgcs","text":"总监理工程师","title":"总监理工程师"},{"value":"zly1","text":"资料员","title":"资料员"},{"value":"zzjsj","text":"装载机司机","title":"装载机司机"},{"value":"zyzljcy","text":"专业质量检查员","title":"专业质量检查员"},{"value":"zygz","text":"专业工长","title":"专业工长"},{"value":"zlg","text":"筑路工","title":"筑路工"},{"value":"zjy","text":"质检员","title":"质检员"},{"value":"zhg","text":"支护工","title":"支护工"},{"value":"zjs","text":"造价师","title":"造价师"},{"value":"yyl","text":"预应力","title":"预应力"},{"value":"ysy","text":"预算员","title":"预算员"},{"value":"yqg","text":"油漆工","title":"油漆工"},{"value":"ybg","text":"仪表工","title":"仪表工"},{"value":"xwzjs","text":"旋挖钻机手","title":"旋挖钻机手"},{"value":"xlg","text":"修理工","title":"修理工"},{"value":"xmzg","text":"项目总工","title":"项目总工"},{"value":"xmjl","text":"项目经理","title":"项目经理"},{"value":"tbg","text":"涂裱工","title":"涂裱工"},{"value":"tdzh（xhg）","text":"塔吊指挥（信号工）","title":"塔吊指挥（信号工）"},{"value":"sdg1","text":"水电工","title":"水电工"},{"value":"sdg2","text":"石雕工","title":"石雕工"},{"value":"sgy","text":"施工员","title":"施工员"},{"value":"sjs1","text":"审计师","title":"审计师"},{"value":"sjs2","text":"设计师","title":"设计师"},{"value":"rf","text":"人防","title":"人防"},{"value":"qd","text":"强电","title":"强电"},{"value":"qzg","text":"砌筑工","title":"砌筑工"},{"value":"pjg","text":"喷浆工","title":"喷浆工"},{"value":"mhg（ng）","text":"抹灰工（泥工）","title":"抹灰工（泥工）"},{"value":"msg","text":"锚索工","title":"锚索工"},{"value":"lhg","text":"绿化工","title":"绿化工"},{"value":"lg1","text":"篱工","title":"篱工"},{"value":"lwy","text":"劳务员","title":"劳务员"},{"value":"kcsjs","text":"勘察设计师","title":"勘察设计师"},{"value":"kwg","text":"开挖工","title":"开挖工"},{"value":"jgy","text":"监管员","title":"监管员"},{"value":"jsy","text":"技术员","title":"技术员"},{"value":"jxy","text":"机械员","title":"机械员"},{"value":"jxsj","text":"机械司机","title":"机械司机"},{"value":"jd","text":"机电","title":"机电"},{"value":"hpg","text":"护坡工","title":"护坡工"},{"value":"glzy(hszhy)","text":"锅炉作业(含水质化验)","title":"锅炉作业(含水质化验)"},{"value":"gg","text":"管工","title":"管工"},{"value":"gzmc","text":"工种名称","title":"工种名称"},{"value":"gz","text":"工长","title":"工长"},{"value":"gjg1","text":"钢结构","title":"钢结构"},{"value":"eczjg","text":"二次注浆工","title":"二次注浆工"},{"value":"ecjg","text":"二次结构","title":"二次结构"},{"value":"dgjsj","text":"盾构机司机","title":"盾构机司机"},{"value":"dtazg","text":"电梯安装工","title":"电梯安装工"},{"value":"dqsbazg","text":"电器设备安装工","title":"电器设备安装工"},{"value":"dg","text":"电工","title":"电工"},{"value":"clg","text":"衬里工","title":"衬里工"},{"value":"cly1","text":"材料员","title":"材料员"},{"value":"bjzczy","text":"拌浆站操作员","title":"拌浆站操作员"},{"value":"aqy","text":"安全员","title":"安全员"},{"value":"zlzy","text":"制冷作业","title":"制冷作业"},{"value":"zjg","text":"植筋工","title":"植筋工"},{"value":"zxg","text":"砧细工","title":"砧细工"},{"value":"zkg","text":"砧刻工","title":"砧刻工"},{"value":"ygg","text":"仰拱工","title":"仰拱工"},{"value":"ylrqzy","text":"压力容器作业","title":"压力容器作业"},{"value":"ylh","text":"压力焊","title":"压力焊"},{"value":"xgb","text":"修改补","title":"修改补"},{"value":"xhzygcs","text":"信号专业工程师","title":"信号专业工程师"},{"value":"xfazg","text":"消防安装工","title":"消防安装工"},{"value":"xqg","text":"镶嵌工","title":"镶嵌工"},{"value":"wltsjsj","text":"物料提升机司机","title":"物料提升机司机"},{"value":"wsqs","text":"文施清扫","title":"文施清扫"},{"value":"wxwpzy","text":"危险物品作业","title":"危险物品作业"},{"value":"wydtsj","text":"外用电梯司机","title":"外用电梯司机"},{"value":"wjsj","text":"挖机司机","title":"挖机司机"},{"value":"tgqg","text":"推光漆工","title":"推光漆工"},{"value":"tf","text":"土方","title":"土方"},{"value":"txzygcs","text":"通信专业工程师","title":"通信专业工程师"},{"value":"tdsj（qzg）","text":"塔吊司机（起重工）","title":"塔吊司机（起重工）"},{"value":"szy","text":"司钻员","title":"司钻员"},{"value":"ssg","text":"司索工","title":"司索工"},{"value":"sng（gdg）","text":"水暖工（管道工）","title":"水暖工（管道工）"},{"value":"slxsj","text":"双轮铣司机","title":"双轮铣司机"},{"value":"sglczs","text":"双轨梁操作手","title":"双轨梁操作手"},{"value":"rd","text":"弱电","title":"弱电"},{"value":"rclg","text":"热处理工","title":"热处理工"},{"value":"qsg","text":"桥隧工","title":"桥隧工"},{"value":"qzg（wg）","text":"砌筑工（瓦工）","title":"砌筑工（瓦工）"},{"value":"qhjg","text":"砌花街工","title":"砌花街工"},{"value":"qhqgg","text":"气焊气割工","title":"气焊气割工"},{"value":"qt","text":"其他","title":"其他"},{"value":"pg","text":"普工","title":"普工"},{"value":"nsg","text":"泥塑工","title":"泥塑工"},{"value":"mqazg","text":"幕墙安装工","title":"幕墙安装工"},{"value":"mdg","text":"木雕工","title":"木雕工"},{"value":"mcazg","text":"门窗安装工","title":"门窗安装工"},{"value":"mg","text":"铆工","title":"铆工"},{"value":"lddsj","text":"履带吊司机","title":"履带吊司机"},{"value":"lgg","text":"螺杆工","title":"螺杆工"},{"value":"lmdsj","text":"龙门吊司机","title":"龙门吊司机"},{"value":"lsgz","text":"临时工种","title":"临时工种"},{"value":"lg2","text":"力工","title":"力工"},{"value":"jzxg","text":"精装修工","title":"精装修工"},{"value":"jcwg","text":"接触网工","title":"接触网工"},{"value":"js","text":"降水","title":"降水"},{"value":"jgg","text":"加固工","title":"加固工"},{"value":"jkzh","text":"基坑支护","title":"基坑支护"},{"value":"jxg","text":"机修工","title":"机修工"},{"value":"jxazcxg","text":"机械安装拆卸工","title":"机械安装拆卸工"},{"value":"jdcsj","text":"机动车司机","title":"机动车司机"},{"value":"jdcjsy","text":"机动车驾驶员","title":"机动车驾驶员"},{"value":"hg","text":"焊工","title":"焊工"},{"value":"gxg","text":"轨线工","title":"轨线工"},{"value":"gppzg","text":"管片拼装工","title":"管片拼装工"},{"value":"gjyq","text":"古建油漆","title":"古建油漆"},{"value":"gjwg","text":"古建瓦工","title":"古建瓦工"},{"value":"gqbg","text":"隔墙板工","title":"隔墙板工"},{"value":"fbglry","text":"分包管理人员","title":"分包管理人员"},{"value":"fxg","text":"放线工","title":"放线工"},{"value":"fhg","text":"防火工","title":"防火工"},{"value":"ffg","text":"防腐工","title":"防腐工"},{"value":"fy","text":"翻译","title":"翻译"},{"value":"dhz","text":"栋号长","title":"栋号长"},{"value":"dgg","text":"顶管工","title":"顶管工"},{"value":"dcsj","text":"吊车司机","title":"吊车司机"},{"value":"dldqgcs","text":"电力电气工程师","title":"电力电气工程师"},{"value":"djcsj","text":"电机车司机","title":"电机车司机"},{"value":"czg1","text":"瓷砖工","title":"瓷砖工"},{"value":"czg2","text":"出渣工","title":"出渣工"},{"value":"ccjsj","text":"成槽机司机","title":"成槽机司机"},{"value":"ccsj1","text":"铲车司机","title":"铲车司机"},{"value":"ccsj2","text":"叉车司机","title":"叉车司机"},{"value":"chg","text":"彩绘工","title":"彩绘工"},{"value":"blkczs","text":"布鲁克操作手","title":"布鲁克操作手"},{"value":"beg","text":"匾额工","title":"匾额工"},{"value":"bw","text":"保温","title":"保温"},{"value":"azg","text":"安装工","title":"安装工"},{"value":"ecg","text":"二衬工","title":"二衬工"},{"value":"czgg489ygg","text":"出渣工G489仰拱工","title":"出渣工G489仰拱工"},{"value":"sj","text":"书记","title":"书记"},{"value":"zg1","text":"总工","title":"总工"},{"value":"aqzj","text":"安全总监","title":"安全总监"},{"value":"zxjl","text":"执行经理","title":"执行经理"},{"value":"swjl","text":"商务经理","title":"商务经理"},{"value":"scjl","text":"生产经理","title":"生产经理"},{"value":"kgy","text":"库管员","title":"库管员"},{"value":"jly1","text":"计量员","title":"计量员"},{"value":"cly2","text":"测量员","title":"测量员"},{"value":"pzy","text":"旁站员","title":"旁站员"},{"value":"bzy","text":"标准员","title":"标准员"},{"value":"mgy","text":"民管员","title":"民管员"},{"value":"sby1","text":"设备员","title":"设备员"},{"value":"syy","text":"试验员","title":"试验员"},{"value":"ddy","text":"调度员","title":"调度员"},{"value":"xzwy","text":"行政文员","title":"行政文员"},{"value":"cw","text":"财务","title":"财务"},{"value":"hq","text":"后勤","title":"后勤"},{"value":"yz（jsdw）","text":"业主（建设单位）","title":"业主（建设单位）"},{"value":"jldw","text":"监理单位","title":"监理单位"},{"value":"sjdw","text":"设计单位","title":"设计单位"},{"value":"qdz","text":"区段长","title":"区段长"},{"value":"ba","text":"保安","title":"保安"},{"value":"bj","text":"保洁","title":"保洁"},{"value":"cs","text":"厨师","title":"厨师"},{"value":"jdy","text":"监督员","title":"监督员"},{"value":"jlzl","text":"经理助理","title":"经理助理"},{"value":"bimzxjl","text":"BIM中心经理","title":"BIM中心经理"},{"value":"bimgcs","text":"BIM工程师","title":"BIM工程师"},{"value":"lzy","text":"劳资员","title":"劳资员"},{"value":"gjfy","text":"钢筋翻样","title":"钢筋翻样"},{"value":"xby","text":"行保员","title":"行保员"},{"value":"bz","text":"部长","title":"部长"},{"value":"fbz","text":"副部长","title":"副部长"},{"value":"zr","text":"主任","title":"主任"},{"value":"zg2","text":"主管","title":"主管"},{"value":"jzddz","text":"架子队队长","title":"架子队队长"},{"value":"jzdfdz","text":"架子队副队长","title":"架子队副队长"},{"value":"zhz","text":"指挥长","title":"指挥长"},{"value":"cwzhz","text":"常务指挥长","title":"常务指挥长"},{"value":"swzhz","text":"商务指挥长","title":"商务指挥长"},{"value":"xczhz","text":"现场指挥长","title":"现场指挥长"},{"value":"fsj","text":"副书记","title":"副书记"},{"value":"gqz","text":"工区长","title":"工区长"},{"value":"ky","text":"科员","title":"科员"},{"value":"tmgcs","text":"土木工程师","title":"土木工程师"},{"value":"aqgcs","text":"安全工程师","title":"安全工程师"},{"value":"gysbgcs","text":"公用设备工程师","title":"公用设备工程师"},{"value":"jzs","text":"建造师","title":"建造师"},{"value":"jlgcs","text":"监理工程师","title":"监理工程师"},{"value":"zjgcs1","text":"造价工程师","title":"造价工程师"},{"value":"hbgcs","text":"环保工程师","title":"环保工程师"},{"value":"jxgcs","text":"机械工程师","title":"机械工程师"},{"value":"gjggcs","text":"钢结构工程师","title":"钢结构工程师"},{"value":"dqgcs","text":"电气工程师","title":"电气工程师"},{"value":"bmjl","text":"部门经理","title":"部门经理"},{"value":"ghzx","text":"工会主席","title":"工会主席"},{"value":"xmfjl","text":"项目副经理","title":"项目副经理"},{"value":"xcdz","text":"现场队长","title":"现场队长"},{"value":"zjjs","text":"总经济师","title":"总经济师"},{"value":"bimzxzr","text":"BIM中心主任","title":"BIM中心主任"},{"value":"gcs","text":"工程师","title":"工程师"},{"value":"jhy","text":"计划员","title":"计划员"},{"value":"xmfzr","text":"项目负责人","title":"项目负责人"},{"value":"jwy","text":"机务员","title":"机务员"},{"value":"cwfjl","text":"常务副经理","title":"常务副经理"},{"value":"zlzj","text":"质量总监","title":"质量总监"},{"value":"dsfjcry","text":"第三方监测人员","title":"第三方监测人员"},{"value":"gysry","text":"供应商人员","title":"供应商人员"},{"value":"zddz","text":"总调度长","title":"总调度长"},{"value":"fzjjs","text":"副总经济师","title":"副总经济师"},{"value":"swfjl","text":"商务副经理","title":"商务副经理"},{"value":"fzddz","text":"副总调度长","title":"副总调度长"},{"value":"fzg","text":"副总工","title":"副总工"},{"value":"aqjdg","text":"安全监督官","title":"安全监督官"},{"value":"qqjl","text":"前期经理","title":"前期经理"},{"value":"jsfzr","text":"技术负责人","title":"技术负责人"},{"value":"zlfzr","text":"质量负责人","title":"质量负责人"},{"value":"jxs","text":"机修师","title":"机修师"},{"value":"jdjl","text":"机电经理","title":"机电经理"},{"value":"jdgcs","text":"机电工程师","title":"机电工程师"},{"value":"sby2","text":"司磅员","title":"司磅员"},{"value":"qhsezj","text":"QHSE总监","title":"QHSE总监"},{"value":"fjzz","text":"副机组长","title":"副机组长"},{"value":"jzz","text":"机组长","title":"机组长"},{"value":"wky","text":"文控员","title":"文控员"},{"value":"xxhgly","text":"信息化管理员","title":"信息化管理员"},{"value":"fzr","text":"副主任","title":"副主任"},{"value":"zhjs","text":"总会计师","title":"总会计师"},{"value":"dzm105fdz","text":"队长M105副队长","title":"队长M105副队长"},{"value":"fzjlgcs","text":"副总监理工程师","title":"副总监理工程师"},{"value":"aqfzjlgcs","text":"安全副总监理工程师","title":"安全副总监理工程师"},{"value":"zjlgcsdb","text":"总监理工程师代表","title":"总监理工程师代表"},{"value":"htygsjlgcs","text":"合同预概算监理工程师","title":"合同预概算监理工程师"},{"value":"clzyjlgcs","text":"测量专业监理工程师","title":"测量专业监理工程师"},{"value":"syzyjlgcs","text":"试验专业监理工程师","title":"试验专业监理工程师"},{"value":"jhtjjlgcs","text":"计划统计监理工程师","title":"计划统计监理工程师"},{"value":"zljlgcs","text":"资料监理工程师","title":"资料监理工程师"},{"value":"xxgljlgcs","text":"信息管理监理工程师","title":"信息管理监理工程师"},{"value":"jly2","text":"监理员","title":"监理员"},{"value":"kz","text":"科长","title":"科长"},{"value":"cny","text":"出纳员","title":"出纳员"},{"value":"fjl","text":"副经理","title":"副经理"},{"value":"jdfzjlgcs","text":"机电副总监理工程师","title":"机电副总监理工程师"},{"value":"tjzjdb","text":"土建总监代表","title":"土建总监代表"},{"value":"jdzjdb","text":"机电总监代表","title":"机电总监代表"},{"value":"aqzyjlgcs","text":"安全专业监理工程师","title":"安全专业监理工程师"},{"value":"jdzyjlgcs","text":"机电专业监理工程师","title":"机电专业监理工程师"},{"value":"tjzyjlgcs","text":"土建专业监理工程师","title":"土建专业监理工程师"},{"value":"zyjlgcs","text":"专业监理工程师","title":"专业监理工程师"},{"value":"gpzyjlgcs","text":"管片专业监理工程师","title":"管片专业监理工程师"},{"value":"dzzyjlgcs","text":"地质专业监理工程师","title":"地质专业监理工程师"},{"value":"zzwsy","text":"专职卫生员","title":"专职卫生员"},{"value":"mw","text":"门卫","title":"门卫"},{"value":"hj","text":"会计","title":"会计"},{"value":"zjl","text":"总经理","title":"总经理"},{"value":"jsjl","text":"技术经理","title":"技术经理"},{"value":"fzjl","text":"副总经理","title":"副总经理"},{"value":"bmfjl","text":"部门副经理","title":"部门副经理"},{"value":"gwjl","text":"岗位经理","title":"岗位经理"},{"value":"scfjl","text":"生产副经理","title":"生产副经理"},{"value":"xmfzg","text":"项目副总工","title":"项目副总工"},{"value":"xmsj","text":"项目书记","title":"项目书记"},{"value":"clzr","text":"材料主任","title":"材料主任"},{"value":"wzbz","text":"物资部长","title":"物资部长"},{"value":"jsbz","text":"技术部长","title":"技术部长"},{"value":"jsfbz","text":"技术副部长","title":"技术副部长"},{"value":"gcbz","text":"工程部长","title":"工程部长"},{"value":"gcfbz","text":"工程副部长","title":"工程副部长"},{"value":"jzdz","text":"架子队长","title":"架子队长"},{"value":"jzfdz","text":"架子副队长","title":"架子副队长"},{"value":"xmzzh","text":"项目总指挥","title":"项目总指挥"},{"value":"xzglpggcs","text":"行政管理配管工程师","title":"行政管理配管工程师"},{"value":"tjgcs","text":"土建工程师","title":"土建工程师"},{"value":"sbgcs","text":"设备工程师","title":"设备工程师"},{"value":"ybgcs","text":"仪表工程师","title":"仪表工程师"},{"value":"hjgcs","text":"焊接工程师","title":"焊接工程师"},{"value":"zlgcs","text":"质量工程师","title":"质量工程师"},{"value":"cggcs","text":"采购工程师","title":"采购工程师"},{"value":"htgcs","text":"合同工程师","title":"合同工程师"},{"value":"clkzgcs","text":"材料控制工程师","title":"材料控制工程师"},{"value":"wdkzgcs","text":"文档控制工程师","title":"文档控制工程师"},{"value":"zjgcs2","text":"质检工程师","title":"质检工程师"},{"value":"wsjcy","text":"无损检测员","title":"无损检测员"},{"value":"wsjcgcs","text":"无损检测工程师","title":"无损检测工程师"},{"value":"jhgcs","text":"计划工程师","title":"计划工程师"},{"value":"zly2","text":"质量员","title":"质量员"},{"value":"scgly","text":"市场管理员","title":"市场管理员"}]
     * timestamp : 1620452753082
     */

    private Boolean success;
    private String message;
    private Integer code;
    private Long timestamp;
    private List<ResultDTO> result;

    public Boolean isSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public List<ResultDTO> getResult() {
        return result;
    }

    public void setResult(List<ResultDTO> result) {
        this.result = result;
    }

    public static class ResultDTO {
        /**
         * value : zx_zxs
         * text : 咨询-咨询师
         * title : 咨询-咨询师
         */

        private String value;
        private String text;
        private String title;

        @Override
        public String toString() {
            return "ResultDTO{" +
                    "value='" + value + '\'' +
                    ", text='" + text + '\'' +
                    ", title='" + title + '\'' +
                    '}';
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }
}
