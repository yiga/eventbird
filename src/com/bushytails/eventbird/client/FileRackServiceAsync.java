package com.bushytails.eventbird.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface FileRackServiceAsync {
	void pickupEventFile(String input, AsyncCallback<String> callback);
}
