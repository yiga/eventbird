package com.bushytails.eventbird.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.json.client.JSONValue;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.DecoratedStackPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

public class Roost implements EntryPoint {
   public void onModuleLoad() {
      final TextBox nameField = new TextBox();
      nameField.setText("Your code");
      nameField.setFocus(true);
      nameField.selectAll();
      RootPanel.get("nameField").add(nameField);
      RootPanel.get("mainPanel").add(mainPanel);
      class MyHandler implements KeyUpHandler {
         public void onKeyUp(KeyUpEvent event) {
            if(event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
               if(author.equals(nameField.getText())) {
                  return;
               }

               // if author == help
               
               mainPanel.removeFromParent();
               mainPanel = new VerticalPanel();
               RootPanel.get("mainPanel").add(mainPanel);

               author = nameField.getText();
               
               // TODO get events by author.
               service.pickupEventFile(author, new AsyncCallback<String>(){
                  @Override
                  public void onSuccess(String result) {
                     HTML h3 = new HTML("<h3 class='listTitle'>Events</h3>", true);
                     mainPanel.add(h3);
                     
                     JSONValue value = JSONParser.parse(result);
                     JSONArray ja = value.isArray();

                     GWT.log(ja.toString());
//                     if(result.size() == 0) {
//                        mainPanel.add(new Label("No Events"));
//                     }
//                     sp = new DecoratedStackPanel();
//                     sp.addStyleName("stackPanel");
//                     mainPanel.add(sp);
//                     for(Event eventCode : result) {
//                        VerticalPanel vp = new VerticalPanel();
//                        sp.add(vp, getHeaderString(makeEventCode(eventCode)), true);
//                        for(String member : memberMap.keySet()) {
//                           vp.add(makeMemberPanel(member, memberMap.get(member)));
//                        }
//                     }
                  }
                  @Override
                  public void onFailure(Throwable caught) {
                     caught.printStackTrace();
                  }
               });
            }
         }
      }
      MyHandler handler = new MyHandler();
      nameField.addKeyUpHandler(handler);
   }

   private Panel makeMemberPanel(String id, String message) {
      HorizontalPanel panel = new HorizontalPanel();
      Label name = new Label(id);
      name.addStyleName("nameLabel");
      panel.add(name);
      panel.add(new Label(message));
      panel.addStyleName("memberPanel");
      return panel;
   }

   private String getHeaderString(String text) {
      HorizontalPanel hPanel = new HorizontalPanel();
      hPanel.setSpacing(0);
      hPanel.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
      HTML headerText = new HTML(text);
      headerText.setStyleName("cw-StackPanelHeader");
      hPanel.add(headerText);
      return hPanel.getElement().getString();
   }
   
//   private String makeEventCode(Event event) {
//      StringBuilder sb = new StringBuilder();
//      sb.append(event.getContents());
//      sb.append("<br/>");
//      sb.append("<strong>");
//      sb.append(Interpreter.interpretDateToString(event.getPlayAt(), Locale.JAPANESE));
//      sb.append("</strong>");
//      return sb.toString();
//   }

   private FileRackServiceAsync service = GWT.create(FileRackService.class);
   private String author = "";
   private VerticalPanel mainPanel = new VerticalPanel();
   private DecoratedStackPanel sp;

}
