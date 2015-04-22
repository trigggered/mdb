package mdb.core.ui.client.communication;

import java.util.List;

import mdb.core.shared.transport.Request;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface IGatewaService {
	
	
	  void Invoke(Request input, AsyncCallback<Request> callback);

	  void batchInvoke(List<Request> input, AsyncCallback<List<Request>> callback);
}
