package ca.gov.bc.open.jrccaccess.autoconfigure.plugins.http;

import java.io.IOException;
import java.util.UUID;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import ca.bc.gov.open.api.DocumentApi;
import ca.bc.gov.open.api.model.DocumentReceivedResponse;
import ca.bc.gov.open.api.model.Error;
import ca.gov.bc.open.jrccaccess.autoconfigure.services.DocumentReadyHandler;
import ca.gov.bc.open.jrccaccess.libs.services.ServiceUnavailableException;

/**
 * The document controller provides an endpoint to submit a document.
 * @author alexjoybc
 * @since 0.2.0
 *
 */
@RestController
@ConditionalOnProperty(
	value="bcgov.access.input",
	havingValue = "http"
)
public class DocumentController implements DocumentApi {
	
	private DocumentReadyHandler documentReadyHandler;
	
	/**
	 * Creates a new document controller
	 * @param documentReadyHandler the service that will handle the document
	 */
	public DocumentController(DocumentReadyHandler documentReadyHandler) {
		
		this.documentReadyHandler = documentReadyHandler;
		
	}
	
	/**
	 * POST /document?sender={sender}
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public ResponseEntity<DocumentReceivedResponse> postDocument(@NotNull @Valid String sender, UUID xRequestID,
			UUID xB3TraceId, UUID xB3ParentSpanId, UUID xB3SpanId, String xB3Sampled, @Valid Resource body) {
		
		DocumentReceivedResponse response = new DocumentReceivedResponse();
		response.setAcknowledge(true);
		
		try {
			documentReadyHandler.Handle(body.getInputStream(), sender);
		} catch (IOException e) {
			// TODO Auto-generated catch block
		
			Error error = new Error();
			error.setCode(Integer.toString(HttpStatus.INTERNAL_SERVER_ERROR.value()));
			error.setMessage(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
			return new ResponseEntity(error, HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (ServiceUnavailableException e) {
			
			Error error = new Error();
			error.setCode(Integer.toString(HttpStatus.SERVICE_UNAVAILABLE.value()));
			error.setMessage(e.getMessage());
			return new ResponseEntity(error, HttpStatus.SERVICE_UNAVAILABLE);
		}
		
		return ResponseEntity.ok(response);
		
	}

}