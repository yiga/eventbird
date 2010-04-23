package com.bushytails.eventbird.model.json;

import java.util.Date;
import java.util.List;

public class EventJson {
   public String getInformation() {
      return information;
   }
   public void setInformation(String information) {
      this.information = information;
   }
   public List<MemberJson> getMembers() {
      return members;
   }
   public void setMembers(List<MemberJson> members) {
      this.members = members;
   }
   public Date getEndDate() {
      return endDate;
   }
   public void setEndDate(Date endDate) {
      this.endDate = endDate;
   }
   public int getLimitMemberCount() {
      return limitMemberCount;
   }
   public void setLimitMemberCount(int limitMemberCount) {
      this.limitMemberCount = limitMemberCount;
   }
   public int getNowMemberCount() {
      return nowMemberCount;
   }
   public void setNowMemberCount(int nowMemberCount) {
      this.nowMemberCount = nowMemberCount;
   }
   private String information;
   private List<MemberJson> members;
   private Date endDate;
   private int limitMemberCount;
   private int nowMemberCount;
}
