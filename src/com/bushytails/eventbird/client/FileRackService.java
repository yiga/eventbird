package com.bushytails.eventbird.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("filerack")
public interface FileRackService extends RemoteService {
   String pickupEventFile(String author);
}
