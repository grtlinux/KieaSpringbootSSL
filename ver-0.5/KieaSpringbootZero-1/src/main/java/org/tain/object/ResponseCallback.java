package org.tain.object;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class ResponseCallback {

	private String message;
	private String status;
	
	@Builder
	public ResponseCallback(
			String message,
			String status
			) {
		this.message = message;
		this.status = status;
	}
}
