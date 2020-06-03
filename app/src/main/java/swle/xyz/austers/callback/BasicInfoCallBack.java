package swle.xyz.austers.callback;

/**
 * Created by TSOMH on 2020/5/31$
 * Description:拿到验证身份时的基本数据
 *
 * basic_info大小为26
 * 关键字如下：
 * 0.学号 $1.姓名 2.英文名 $3.性别 4.年级 5.学制 6.项目 7.学历层次 8.学生类别 9.$院系 10.$专业 11.方向 12.入校时间
 * 13.毕业时间 14.行政管理院系 15.学习形式 16.是否在籍 17.是否在校 18.所属校区 19.所属班级 20.学籍生效日期 21.是否有学籍
 * 22.学籍状态 23.身份证号 24.备注 25.民族
 *
 *
 */

public interface BasicInfoCallBack {
    void failure(int status_code);
    void getBasicInfo(String[] basic_info);
}
