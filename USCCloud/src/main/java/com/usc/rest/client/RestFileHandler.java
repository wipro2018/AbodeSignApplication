/*
 * Rest File Handler
 * Author: Ashok Ayyandhurai
 * Version: 1.0
 * 
 */

package com.usc.rest.client;

import java.io.File;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.client.filter.HTTPBasicAuthFilter;
import com.sun.jersey.multipart.FormDataBodyPart;
import com.sun.jersey.multipart.FormDataMultiPart;
import com.sun.jersey.multipart.file.FileDataBodyPart;
import com.sun.jersey.multipart.impl.MultiPartWriter;

@Path("/fileHandler")
public class RestFileHandler {

	// Create a new folder in Oracle Content Cloud
	@POST
	@Path("/createFolder")
	public String createFolder(@QueryParam("folderName") String folderName) {
		String statusMessage = "";
		try {
			ClientConfig config = new DefaultClientConfig();
			Client client = Client.create(config);
			String folderId = "F1DB3E9ED87E51C9FF7ABD25943F4BAC1AA63FA213FC";
			String inputData = "{\"name\":\"" + folderName + "\",\"description\":\"Document Folder\"}";
			WebResource webResource = client
					.resource("https://contentcloud-restcloud.documents.us2.oraclecloud.com/documents/api/1.2/folders/"
							+ folderId);
			HTTPBasicAuthFilter basicAuth = new HTTPBasicAuthFilter("ashok.ayyandhurai@wipro.com", "Rest@7100");
			client.addFilter(basicAuth);
			ClientResponse response = webResource.accept("application/json").post(ClientResponse.class, inputData);
			if (response.getStatus() == 201) {
				statusMessage = "Success: Folder Created Successfully - " + response.getEntity(String.class);
			} else {
				statusMessage = "Error: Folder Creation Failed, Reason: " + response.getStatus();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return statusMessage;
	}

	// Upload a file into specific folder directory in Oracle Content Cloud
	// Query Param - File Path
	@POST
	@Path("/fileUpload")
	public String uploadContentFile(@QueryParam("filePath") String filePath) {
		String statusMessage = "";
		try {
			ClientConfig config = new DefaultClientConfig();
			config.getClasses().add(MultiPartWriter.class);
			Client client = Client.create(config);
			WebResource webResource = client.resource(
					"https://contentcloud-restcloud.documents.us2.oraclecloud.com/documents/api/1.2/files/data");
			HTTPBasicAuthFilter basicAuth = new HTTPBasicAuthFilter("ashok.ayyandhurai@wipro.com", "Rest@7100");
			client.addFilter(basicAuth);
			String input = "{'parentID' : 'F1DB3E9ED87E51C9FF7ABD25943F4BAC1AA63FA213FC'}";
			FormDataMultiPart multiform = new FormDataMultiPart();
			FormDataBodyPart formPart = new FormDataBodyPart("parameters", input, MediaType.APPLICATION_JSON_TYPE);
			FileDataBodyPart filePart = new FileDataBodyPart("primaryFile", new File(filePath),
					MediaType.APPLICATION_OCTET_STREAM_TYPE);
			multiform.bodyPart(formPart);
			multiform.bodyPart(filePart);
			ClientResponse response = webResource.type(MediaType.MULTIPART_FORM_DATA_TYPE).post(ClientResponse.class,
					multiform);
			if (response.getStatus() == 201) {
				statusMessage = "Success: File Successfully uploaded in to Oracle Content Cloud. Status: "
						+ response.getEntity(String.class);
			} else {
				statusMessage = "Error: File Upload Failed, Reason: " + response.getStatus();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return statusMessage;
	}

	// Get folder details from Oracle Content Cloud
	// params: folderId

	@GET
	@Path("/getFolder")
	public String getFolder(@QueryParam("folderId") String folderId) {
		String statusMessage = "";
		try {
			ClientConfig config = new DefaultClientConfig();
			Client client = Client.create(config);
			WebResource webResource = client
					.resource("https://contentcloud-restcloud.documents.us2.oraclecloud.com/documents/api/1.2/folders/"
							+ folderId);
			HTTPBasicAuthFilter basicAuth = new HTTPBasicAuthFilter("ashok.ayyandhurai@wipro.com", "Rest@7100");
			client.addFilter(basicAuth);
			ClientResponse response = webResource.accept("application/json").get(ClientResponse.class);
			if (response.getStatus() == 200) {
				statusMessage = "Success: Folder Details - " + response.getEntity(String.class);
			} else {
				statusMessage = "Error: Folder Details Service Failed, Reason: " + response.getStatus();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return statusMessage;
	}

	// Create a Metadata Collection to a File
	// params: collectionName

	@POST
	@Path("/createMetadataCollection")
	public String createMetaDataCollection(@QueryParam("collectionName") String collectionName) {
		String statusMessage = "";
		try {
			ClientConfig config = new DefaultClientConfig();
			Client client = Client.create(config);
			WebResource webResource = client
					.resource("https://contentcloud-restcloud.documents.us2.oraclecloud.com/documents/api/1.2/metadata/"
							+ collectionName);
			HTTPBasicAuthFilter basicAuth = new HTTPBasicAuthFilter("ashok.ayyandhurai@wipro.com", "Rest@7100");
			client.addFilter(basicAuth);
			ClientResponse response = webResource.accept("application/json").post(ClientResponse.class);
			/*
			 * String responseBody = response.getEntity(String.class);
			 * System.out.println(responseBody);
			 */
			if (response.getStatus() == 201) {
				statusMessage = "Success: Meta Collection Created Successfully. Details - "
						+ response.getEntity(String.class);
			} else {
				statusMessage = "Error: Create Meta Collection Failed";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return statusMessage;
	}

	// Assign a Metadata Collection to a File
	// params: collectionName, fileId

	@POST
	@Path("/assignMetadataCollection")
	public String assignMetaDataCollection(@QueryParam("collectionName") String collectionName,
			@QueryParam("fileId") String fileId) {
		String statusMessage = "";
		try {
			ClientConfig config = new DefaultClientConfig();
			Client client = Client.create(config);
			WebResource webResource = client
					.resource("https://contentcloud-restcloud.documents.us2.oraclecloud.com/documents/api/1.2/files/"
							+ fileId + "/metadata/" + collectionName);
			HTTPBasicAuthFilter basicAuth = new HTTPBasicAuthFilter("ashok.ayyandhurai@wipro.com", "Rest@7100");
			client.addFilter(basicAuth);
			ClientResponse response = webResource.accept("application/json").post(ClientResponse.class);
			/*
			 * String responseBody = response.getEntity(String.class);
			 * System.out.println(responseBody);
			 */
			if (response.getStatus() == 200) {
				statusMessage = "Success: Assign a Metadata Collection to a File. Details - "
						+ response.getEntity(String.class);
			} else {
				statusMessage = "Error: Assign a Metadata Collection to a File";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return statusMessage;
	}

	// Assign Values to a File Metadata Collection
	// params: collectionName, fields

	@POST
	@Path("/assignFieldsToFileMetadata")
	public String assignFieldsToFileMetaData(@QueryParam("collectionName") String collectionName,
			@QueryParam("fieldOne") String fieldOne, @QueryParam("fieldTwo") String fieldTwo, @QueryParam("fileId") String fileId) {
		String statusMessage = "";
		try {
			ClientConfig config = new DefaultClientConfig();
			Client client = Client.create(config);
			WebResource webResource = client.resource(
					"https://contentcloud-restcloud.documents.us2.oraclecloud.com/documents/api/1.2/files/"+fileId+"/metadata");
			// List<String> elephantList = Arrays.asList(str.split(","));
			String inputData = "{\"collection\":\"" + collectionName + "\",\"FieldA1\":\"" + fieldOne + "\",\"FieldA2\":\"" + fieldTwo + "\"}";
			HTTPBasicAuthFilter basicAuth = new HTTPBasicAuthFilter("ashok.ayyandhurai@wipro.com", "Rest@7100");
			client.addFilter(basicAuth);
			ClientResponse response = webResource.accept("application/json").post(ClientResponse.class, inputData);
			/*String responseBody = response.getEntity(String.class);
			System.out.println(responseBody);*/
			if (response.getStatus() == 200) {
				statusMessage = "Success: Set Searchable Metadata Fields. Details - "
						+ response.getEntity(String.class);
			} else {
				statusMessage = "Error: Set Searchable Metadata Fields";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return statusMessage;
	}

	// Set Searchable Metadata Fields
	// params: collectionName, fields

	@POST
	@Path("/setMetadataFields")
	public String setMetaDataFields(@QueryParam("collectionName") String collectionName,
			@QueryParam("fieldValues") String fieldValues) {
		String statusMessage = "";
		try {
			ClientConfig config = new DefaultClientConfig();
			Client client = Client.create(config);
			WebResource webResource = client.resource(
					"https://contentcloud-restcloud.documents.us2.oraclecloud.com/documents/api/1.2/metadata/searchFields");
			String inputData = "{\"collection\":\"" + collectionName + "\",\"fields\":\"" + fieldValues + "\"}";
			HTTPBasicAuthFilter basicAuth = new HTTPBasicAuthFilter("ashok.ayyandhurai@wipro.com", "Rest@7100");
			client.addFilter(basicAuth);
			ClientResponse response = webResource.accept("application/json").post(ClientResponse.class, inputData);
			/*String responseBody = response.getEntity(String.class);
			System.out.println(responseBody);*/
			if (response.getStatus() == 200) {
				statusMessage = "Success: Set Searchable Metadata Fields. Details - "
						+ response.getEntity(String.class);
			} else {
				statusMessage = "Error: Set Searchable Metadata Fields";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return statusMessage;
	}
}