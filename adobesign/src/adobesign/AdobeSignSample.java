package adobesign;

import java.io.File;

import javax.ws.rs.core.MultivaluedMap;

import com.adobe.sign.api.TransientDocumentsApi;
import com.adobe.sign.model.agreements.DocumentCreationInfo;
import com.adobe.sign.model.transientDocuments.TransientDocumentResponse;
import com.adobe.sign.utils.ApiException;
import com.sun.jersey.core.util.MultivaluedMapImpl;

public class AdobeSignSample {

	public static void main(String[] args) throws ApiException {
		//Create a File object      
		File inputFile = new File ("MyDocument.pdf");
		
		TransientDocumentsApi transientDocumentsApi=new TransientDocumentsApi();

		//Populate the headers
		//Note that MultivaluedMap is part of javax.ws.rs.core package
		//Note that MultivaluedMapImpl is part of com.sun.jersey.core.util package
		MultivaluedMap headers = new MultivaluedMapImpl();    
		//Add headers    
		String ACCESS_TOKEN_KEY= "Access-Token";  
		String X_API_USER_KEY = "x-api-user";
		headers.put("Content-Type", "multipart/form-data");
		headers.put(ACCESS_TOKEN_KEY, "3AAABLblqZhCnhNjkAn4IDMKUiEX9hzY95Wg02Q5ZKUlbTy3cNQTNMVaqtPdNVg0_UdoM6_Gqqrh1kVkKjKnpv9tNpQc6QXKB");    
		headers.put(X_API_USER_KEY, "aditya.srivastava8@wipro.com");

		//Create a transient document.      
		TransientDocumentResponse  transientDocumentResponse = transientDocumentsApi.createTransientDocument(headers, "MyDocument.pdf", inputFile, "application/pdf");

		//Get the transient document ID
		String documentID = transientDocumentResponse.getTransientDocumentId();

		//Print the transient document ID
		System.out.println("Transient Document ID: "+documentID);
		
		DocumentCreationInfo documentCreationInfo = new DocumentCreationInfo();    
		documentCreationInfo.setName(agreementName);    
		documentCreationInfo.setFileInfos(fileInfos);    
		documentCreationInfo.setRecipientSetInfos(recipientSetInfos);    
		documentCreationInfo.setSignatureType(DocumentCreationInfo.SignatureTypeEnum.ESIGN);    
		documentCreationInfo.setSignatureFlow(DocumentCreationInfo.SignatureFlowEnum.SENDER_SIGNATURE_NOT_REQUIRED);

	}

}
