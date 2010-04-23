package com.bushytails.eventbird.server;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.jdo.PersistenceManager;

import net.arnx.jsonic.JSON;

import com.bushytails.eventbird.client.FileRackService;
import com.bushytails.eventbird.model.datastore.Event;
import com.bushytails.eventbird.model.datastore.Member;
import com.bushytails.eventbird.model.json.EventJson;
import com.bushytails.eventbird.model.json.MemberJson;
import com.bushytails.eventbird.util.Keeper;
import com.bushytails.eventbird.util.StoreManager;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

@SuppressWarnings("serial")
public class FileRackServiceImpl extends RemoteServiceServlet implements FileRackService {
	public String pickupEventFile(String author) {
	   Keeper keeper = new Keeper();
		PersistenceManager pm = StoreManager.getPMF().getPersistenceManager();
		try {
   		Map<String, Event> map = keeper.getScoreMap(pm, "yigaS4");
   		System.out.println(map.size());
         List<EventJson> eventList = new ArrayList<EventJson>();
         for(Event event : map.values()){
            EventJson ej = new EventJson();
            ej.setEndDate(event.getPlayAt());
            ej.setInformation(event.getContents());
            ej.setLimitMemberCount(event.getNumberOfMembers());
            ej.setNowMemberCount(event.getNowMember());
            List<Member> memberList = keeper.getMemberList(pm, event);
            List<MemberJson> memberJsonList = new ArrayList<MemberJson>();
            for(Member m : memberList){
               MemberJson mj = new MemberJson();
               mj.setId(m.getName());
               mj.setMessage(m.getContent());
               memberJsonList.add(mj);
            }
            ej.setMembers(memberJsonList);
   		   eventList.add(ej);
   		}
         System.out.println(JSON.encode(eventList));
   		return JSON.encode(eventList);
		} finally {
		   pm.close();
		}
	}
	
	 // @TestData
   private List<String> eventList = new ArrayList<String>() {
      {
         add("event name 1 event name 1 event name 1 event name 1 event name 1 event name 1 event name 1"
            + "<br/><strong>2010-04-03 19:00</strong>まで受付, 現在<strong>2</strong>人, 定員<strong>10</strong>人");
         add("event name 2 event name 2 event name 2 event name 1 event name 1 event name 1 event name 1"
            + "<br/><strong>2010-04-03 19:00</strong>まで受付, 現在<strong>3</strong>人, 定員<strong>30</strong>人");
      }
   };
   private Map<String, String> memberMap = new HashMap<String, String>() {
      {
         put("yigaS4", "取り替えずなんかいってみるテストをしてみるテストを考えたほうがいいかなと思いながらのテスト。");
         put("event member", "取り替えずなんかいってみるテストをしてみるテストを考えたほうがいいかなと思いながらのテスト。");
      }
   };
}
