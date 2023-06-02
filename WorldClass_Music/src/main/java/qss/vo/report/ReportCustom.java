package qss.vo.report;

//보고서 데이터
public class ReportCustom {
    private String dayofweek; // 요일
    private String playdate; // 재생일자
    private String time; // 시간
    private String actionName; // 액션 명
    private int count; // 카운트

    public String getDayofweek() {
        return dayofweek;
    }

    public void setDayofweek(String dayofweek) {
        this.dayofweek = dayofweek;
    }

    public String getPlaydate() {
        return playdate;
    }

    public void setPlaydate(String playdate) {
        this.playdate = playdate;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getActionName() {
        return actionName;
    }

    public void setActionName(String actionName) {
        this.actionName = actionName;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}