package ru.icerebro.telega.attendanceapp.entities;

import java.sql.Time;

public class Attendance {
    private long id;
    private Integer eId;
    private Time time;
    private Integer month;
    private Integer aYear;
    private Integer day;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Integer geteId() {
        return eId;
    }

    public void seteId(Integer eId) {
        this.eId = eId;
    }

    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
        this.time = time;
    }

    public Integer getMonth() {
        return month;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

    public Integer getaYear() {
        return aYear;
    }

    public void setaYear(Integer aYear) {
        this.aYear = aYear;
    }

    public Integer getDay() {
        return day;
    }

    public void setDay(Integer day) {
        this.day = day;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Attendance that = (Attendance) o;

        if (id != that.id) return false;
        if (eId != null ? !eId.equals(that.eId) : that.eId != null) return false;
        if (time != null ? !time.equals(that.time) : that.time != null) return false;
        if (month != null ? !month.equals(that.month) : that.month != null) return false;
        if (aYear != null ? !aYear.equals(that.aYear) : that.aYear != null) return false;
        if (day != null ? !day.equals(that.day) : that.day != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (eId != null ? eId.hashCode() : 0);
        result = 31 * result + (time != null ? time.hashCode() : 0);
        result = 31 * result + (month != null ? month.hashCode() : 0);
        result = 31 * result + (aYear != null ? aYear.hashCode() : 0);
        result = 31 * result + (day != null ? day.hashCode() : 0);
        return result;
    }
}
