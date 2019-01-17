package com.amwater.mule.logging.policy.transformers;

import org.mule.api.MuleMessage;
import org.mule.api.transformer.TransformerException;
import org.mule.transformer.AbstractMessageTransformer;

public class AWLogTransformer extends AbstractMessageTransformer{

	@Override
	public Object transformMessage(MuleMessage muleMessage, String arg1) throws TransformerException {

		String correlationId = muleMessage.getInboundProperty("correlationId");		
		if(correlationId == null || correlationId == ""){
			correlationId = muleMessage.getUniqueId();
		}
		
		muleMessage.setOutboundProperty("correlationId", correlationId);
		
		StringBuffer sb = new StringBuffer();
		sb.append("[messageId: " +muleMessage.getUniqueId()+ "]").append(" ");
		sb.append("[correlationId: " +correlationId+"]").append(" ");
		sb.append("[userAgent: " +muleMessage.getInboundProperty("user-agent")+ "]").append(" ");
		
		org.apache.log4j.MDC.put("mule_msg_details", sb.toString());
		
		logger.debug("Inbound mule-message :" + muleMessage.toString());
		
		return muleMessage.getPayload();
	}
}
