package com.xingye.pojo;

import java.util.Date;
import java.util.Objects;

public class UserLog {
    private Integer id;
    private Integer userId;
    private String username; // 新增
    private Date time;
    private String ip;
    private String country;
    private String area;
    private String city;
    private String org; // operator 改为 org
    private String coord;

    public UserLog() {}

    public UserLog(Integer id, Integer userId, String username, Date time, String ip,
                   String country, String area, String city, String org, String coord) {
        this.id = id;
        this.userId = userId;
        this.username = username;
        this.time = time;
        this.ip = ip;
        this.country = country;
        this.area = area;
        this.city = city;
        this.org = org;
        this.coord = coord;
    }

    // Getters & Setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public Integer getUserId() { return userId; }
    public void setUserId(Integer userId) { this.userId = userId; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public Date getTime() { return time; }
    public void setTime(Date time) { this.time = time; }
    public String getIp() { return ip; }
    public void setIp(String ip) { this.ip = ip; }
    public String getCountry() { return country; }
    public void setCountry(String country) { this.country = country; }
    public String getArea() { return area; }
    public void setArea(String area) { this.area = area; }
    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }
    public String getOrg() { return org; }
    public void setOrg(String org) { this.org = org; }
    public String getCoord() { return coord; }
    public void setCoord(String coord) { this.coord = coord; }

    @Override
    public String toString() {
        return "UserLog{" +
                "id=" + id +
                ", userId=" + userId +
                ", username='" + username + '\'' +
                ", time=" + time +
                ", ip='" + ip + '\'' +
                ", country='" + country + '\'' +
                ", area='" + area + '\'' +
                ", city='" + city + '\'' +
                ", org='" + org + '\'' +
                ", coord='" + coord + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        UserLog userLog = (UserLog) o;
        return Objects.equals(id, userLog.id) &&
                Objects.equals(userId, userLog.userId) &&
                Objects.equals(username, userLog.username) &&
                Objects.equals(time, userLog.time) &&
                Objects.equals(ip, userLog.ip) &&
                Objects.equals(country, userLog.country) &&
                Objects.equals(area, userLog.area) &&
                Objects.equals(city, userLog.city) &&
                Objects.equals(org, userLog.org) &&
                Objects.equals(coord, userLog.coord);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userId, username, time, ip, country, area, city, org, coord);
    }
}
