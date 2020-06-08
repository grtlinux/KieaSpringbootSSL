package org.tain.object;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class RequestCallback {

	private String counterpartyTransactionId;
	private String reason;
	private String sourceOperatorCode;
	private String status;
	private String transactionId;
	
	@Builder
	public RequestCallback(
			String counterpartyTransactionId,
			String reason,
			String sourceOperatorCode,
			String status,
			String transactionId
			) {
		this.counterpartyTransactionId = counterpartyTransactionId;
		this.reason = reason;
		this.sourceOperatorCode = sourceOperatorCode;
		this.status = status;
		this.transactionId = transactionId;
	}
}
