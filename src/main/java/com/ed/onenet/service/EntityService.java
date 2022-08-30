package com.ed.onenet.service;

import com.ed.onenet.dto.FileResponse;
import com.ed.onenet.dto.FormResponse;
import com.ed.onenet.controller.rest_template.OneNetRestTemplate;
import com.ed.onenet.controller.rest_template.SofiaRestTemplate;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;

@Service
public class EntityService {

//    @Value("${orion.consumer.fiware.ip}")
//    private String onenetConsumerfiwareIp;
//
//    @Value("${orion.provider.fiware.ip}")
//    private String onenetProviderFiwareIp;
//
//    @Value("${orion.provider.appData.ip}")
//    private String onenetProviderAppDataIp;


    private final UserService userService;
    private final OneNetRestTemplate oneNetRestTemplate;
    private final SofiaRestTemplate sofiaRestTemplate;

    public EntityService(UserService userService,
                         OneNetRestTemplate oneNetRestTemplate,
                         SofiaRestTemplate sofiaRestTemplate) {
        this.oneNetRestTemplate = oneNetRestTemplate;
        this.userService = userService;
        this.sofiaRestTemplate = sofiaRestTemplate;
    }

//    public Map<String, Object> getObject(String componentName,
//                                         String id) {
//        Map<String, Object> jsonLdParameters = this.sourceRegistrationParameters(componentName, id);
//        return null;
//    }

    public FormResponse postObject(String formId, Map<String, Map<String, Object>> parameters,
                                          Map<String, String> headers) {

        Map<String, Map<String, Object>> subEntity =
                (Map<String, Map<String, Object>>) parameters.get("data_send").get("sub-entities");
        String providerFiwareIp = (String) subEntity.get("provider").get("provider_fiware_url");

        String message = parameters.get("data_send").get("message").toString();
        parameters.get("data_send").put("message", "");
        String id = this.sofiaRestTemplate.post(parameters, formId, headers);
        parameters.get("data_send").put("id", id);
        parameters.get("data_send").put("message", message);

        // UserDTO userDTO = userService.getCurrentUser(headers.get("authorization"));

        List<Map<String, Object>> jsonLdParametersList =  this.parametersToJsonLdList(parameters);

        for(Map<String, Object> jsonLdParameters :  jsonLdParametersList){

            Map<String, String> refId = (Map<String, String>) jsonLdParameters.get("refId");
            id = refId.get("value");

           // String type = (String) jsonLdParameters.get("type");

           // Boolean exists = this.oneNetRestTemplate.checkExistance(providerFiwareIp, type, id);
           // if (exists) {
           //     this.oneNetRestTemplate.update(jsonLdParameters, headers, type, id, providerFiwareIp);
           // } else {
                this.oneNetRestTemplate.post(jsonLdParameters, headers, providerFiwareIp);
           // }

        }

        return new FormResponse(id);
    }


    public FileResponse getObjectData(String id,
                                String encodedEccUrl,
                                String encodedConsumerFiwareUrl,
                                      String encodedConsumerDataAppUrl,
                                Map<String, String> headers) {

        byte[] decodedBytes = Base64.getDecoder().decode(encodedEccUrl);
        String eccUrl = new String(decodedBytes);

        decodedBytes = Base64.getDecoder().decode(encodedConsumerFiwareUrl);
        String consumerFiwareUrl = new String(decodedBytes);

        decodedBytes = Base64.getDecoder().decode(encodedConsumerDataAppUrl);
        String consumerDataAppUrl = new String(decodedBytes);

        List<String> fileParts = new ArrayList<>();


        Map<String, Object> reqJsonParameters = new HashMap<>();
        reqJsonParameters.put("entityId", "urn:ngsi-ld:dataentity:" + id);
        reqJsonParameters.put("eccUrl",eccUrl);
        reqJsonParameters.put("brokerUrl",consumerFiwareUrl);
            this.oneNetRestTemplate.dataentityRequesFromProvider(reqJsonParameters, consumerDataAppUrl);

        Map<String, Object> jsonLdParameters = this.oneNetRestTemplate.retrieveData("dataentity", id, consumerDataAppUrl );

        Map<String, Object> sizeRef = (Map<String, Object>) jsonLdParameters.get("partsSize");
        Integer size = (Integer) sizeRef.get("value");

        for(int i = 0; i < size; i++){

            reqJsonParameters = new HashMap<>();
            reqJsonParameters.put("entityId", "urn:ngsi-ld:datapart:" + id + "-"+ i);
            reqJsonParameters.put("eccUrl",eccUrl);
            reqJsonParameters.put("brokerUrl",consumerFiwareUrl);
            this.oneNetRestTemplate.dataentityRequesFromProvider(reqJsonParameters, consumerDataAppUrl);

            Map<String, Object> jsonLdPartParameters = this.oneNetRestTemplate.retrieveData("datapart", id + "-"+ i, consumerDataAppUrl);
            Map<String, String> partRef = (Map<String, String>) jsonLdPartParameters.get("part");
            String part = partRef.get("value");
            fileParts.add(part);
        }

        return new FileResponse(String.join("", fileParts), true);
    }

    public FileResponse getLocalObjectData(String id,
                                     String encodedFiwareUrl,
                                     Map<String, String> headers) {

        byte[] decodedBytes = Base64.getDecoder().decode(encodedFiwareUrl);
        String fiwareUrl = new String(decodedBytes);

        List<String> fileParts = new ArrayList<>();

        Boolean exists = this.oneNetRestTemplate.checkExistance(fiwareUrl, "dataentity", id);
        if (!exists) {
           return new FileResponse("", true);
        }

        Map<String, Object> jsonLdParameters = this.oneNetRestTemplate.retrieveData("dataentity", id, fiwareUrl );

        Map<String, Object> sizeRef = (Map<String, Object>) jsonLdParameters.get("partsSize");
        int size = (int) sizeRef.get("value");

        for(int i = 0; i < size; i++){

            exists = this.oneNetRestTemplate.checkExistance(fiwareUrl, "datapart", id + "-"+ i);
            if (!exists) {
              return new FileResponse("", true);
            }

            Map<String, Object> jsonLdPartParameters = this.oneNetRestTemplate.retrieveData("datapart", id + "-"+ i, fiwareUrl);
            Map<String, String> partRef = (Map<String, String>) jsonLdPartParameters.get("part");
            String part = partRef.get("value");
            fileParts.add(part);
        }

        return new FileResponse(String.join("", fileParts), true);
    }

    public Map<String, Object> saveResponce(String id,
                                            Map<String, String> headers) {

//        Map<String, Object> responce = this.oneNetRestTemplate.getFromProvider(id);
//
//        Map<String, Map<String, Object>> parameters = this.jsonLdToParameters(responce);
//
//        return responce;
        return null;
    }

    public List<Map<String, Object>> parametersToJsonLdList(Map<String, Map<String, Object>> parameters) {
        List<Map<String, Object>> jsonLdParametersList = new ArrayList<>();
        Map<String, Object> jsonLdParameters = new HashMap<>();
        jsonLdParametersList.add(jsonLdParameters);

        Map<String, Object> parameterFields = parameters.get("data_send");

        jsonLdParameters.put("id", "urn:ngsi-ld:dataentity:" + parameterFields.get("id"));
        jsonLdParameters.put("type", "dataentity");
        jsonLdParameters.put("@context", Arrays.asList("https://fiware.github.io/data-models/context.jsonld",
                "https://uri.etsi.org/ngsi-ld/v1/ngsi-ld-core-context-v1.3.jsonld"));

        String filedata = parameterFields.get("message").toString();

        int length = filedata.contains("base64,")  ? filedata.split(",")[1].length() : filedata.length();
        double fileSizeInByte = Math.ceil((double) length / 4) * 3;
        System.out.println(fileSizeInByte);

        String[] filedataParts = filedata.split("(?<=\\G.{10000})");

        Map<String, Object> jsonLdField = new HashMap<>();
        jsonLdField.put("type", "Property");
        jsonLdField.put("value", filedataParts.length);
        jsonLdParameters.put("partsSize", jsonLdField);

        jsonLdField = new HashMap<>();
        jsonLdField.put("type", "Property");
        jsonLdField.put("value", parameterFields.get("id"));
        jsonLdParameters.put("refId", jsonLdField);

        int index = 0;
        for(String filedataPart : filedataParts){
            Map<String, Object> fileDataPartJsonLd = this.fileDataPartToJsonLd( filedataPart,
                    parameterFields.get("id") + "-" + index );

            jsonLdParametersList.add(fileDataPartJsonLd);
            index++;
        }

        return jsonLdParametersList;
    }

    public Map<String, Object> createConsumerProviderRequestJsonLd(String id, String type, String onenetProviderAppDataPublicIp) {

        Map<String, Object> jsonLdParameters = new HashMap<>();

        jsonLdParameters.put("type", "ContextSourceRegistration");
        jsonLdParameters.put("@context",
                Arrays.asList("https://fiware.github.io/data-models/context.jsonld",
                "https://uri.etsi.org/ngsi-ld/v1/ngsi-ld-core-context-v1.3.jsonld"));
        jsonLdParameters.put("endpoint", onenetProviderAppDataPublicIp);

        Map<String, Object> jsonLdEntity = new HashMap<>();
        jsonLdEntity.put("type", "dataentity");
        jsonLdEntity.put("id", "urn:ngsi-ld:dataentity:" + id);
        Map<String, Object> jsonLdEntities = new HashMap<>();
        jsonLdEntities.put("entities", Arrays.asList(jsonLdEntity));
        jsonLdParameters.put("information", Arrays.asList(jsonLdEntities));

        return jsonLdParameters;
    }

    public Map<String, Object> fileDataPartToJsonLd(String filedataPart, String id) {

        Map<String, Object> jsonLdParameters = new HashMap<>();
        jsonLdParameters.put("id", "urn:ngsi-ld:datapart:" + id);
        jsonLdParameters.put("type", "datapart");
        jsonLdParameters.put("@context", Arrays.asList("https://fiware.github.io/data-models/context.jsonld",
                "https://uri.etsi.org/ngsi-ld/v1/ngsi-ld-core-context-v1.3.jsonld"));


        Map<String, Object> jsonLdField = new HashMap<>();
        jsonLdField.put("type", "Property");
        jsonLdField.put("value", filedataPart);
        jsonLdParameters.put("part", jsonLdField);

        jsonLdField = new HashMap<>();
        jsonLdField.put("type", "Property");
        jsonLdField.put("value", id);
        jsonLdParameters.put("refId", jsonLdField);

        return jsonLdParameters;
    }

    public Map<String, Object> parametersToJsonLd(Map<String, Map<String, Object>> parameters) throws IOException {

        Map<String, Object> parameterFields = parameters.get("data_send");

        Map<String, Object> jsonLdBody = new HashMap<>();
        jsonLdBody.put("id", "urn:ngsi-ld:dataentity:" + parameterFields.get("id"));
        jsonLdBody.put("type", "dataentity");
        jsonLdBody.put("@context", Arrays.asList("https://fiware.github.io/data-models/context.jsonld",
                "https://uri.etsi.org/ngsi-ld/v1/ngsi-ld-core-context-v1.3.jsonld"));

        String filedata = parameterFields.get("message").toString();
        System.out.println(filedata.length());

        String[] filedataParts = filedata.split("(?<=\\G.{100000})");

        int index = 0;
        for(String filedataPart : filedataParts){
            Map<String, Object> jsonLdField = new HashMap<>();
            jsonLdField.put("type", "Property");
            jsonLdField.put("value", filedataPart);
            jsonLdBody.put("filedata"+index, jsonLdField);
            index++;
        }

        Map<String, Object> jsonLdField = new HashMap<>();
        jsonLdField.put("type", "Property");
        jsonLdField.put("value", parameterFields.get("message").toString());
        jsonLdBody.put("filedataPartSize", index);


//        BufferedWriter writer = new BufferedWriter(new FileWriter("test.txt"));
//        writer.write(parameterFields.get("message").toString());
//        writer.close();

//        Map<String, Object> jsonLdField2 = new HashMap<>();
//        jsonLdField2.put("type", "Property");
//        jsonLdField2.put("value", "Hello");
//        jsonLdBody.put("hello", jsonLdField2);

        System.out.println(parameterFields.get("message").toString());
        ObjectMapper mapper = new ObjectMapper();
        //Converting the Object to JSONString
        String jsonString = mapper.writeValueAsString(jsonLdBody);
        System.out.println(jsonString);

        return jsonLdBody;
    }


    public Map<String, Map<String, Object>> jsonLdToParameters(Map<String, Object> response) {
        Map<String, Map<String, Object>> parameters = new HashMap<>();
        Map<String, Object> ids_resources = new HashMap<>();

        response
                .entrySet()
                .stream()
                .filter(e -> e.getKey() != "onenet_resourse_creator")
                .filter(e -> e.getKey() != "id")
                .filter(e -> e.getKey() != "@context")
                .filter(e -> e.getKey() != "type")
                .forEach(e -> {
                    Map<String, Object> responseFieldValue = (Map<String, Object>) e.getValue();
                    ids_resources.put(
                            (e.getKey() == "https://uri.fiware.org/ns/data-models#language" ? "language" : e.getKey()), responseFieldValue.get("value"));
                });

        String urnId = (String) response.get("id");
        String[] idParts = urnId.replace("urn:ngsi-ld:", "").split(":");
        ids_resources.put("onenet_type", "received");
        // ids_resources.put("id", idParts[1]);

        Map<String, Object> resourceCreator = (Map<String, Object>) response.get("onenet_resourse_creator");
        Map<String, String> resourceCreatorFields = (Map<String, String>) resourceCreator.get("value");
        ids_resources.put("onenet_user_id", resourceCreatorFields.get("id"));
        ids_resources.put("onenet_user_username", resourceCreatorFields.get("username"));
        ids_resources.put("onenet_user_email", resourceCreatorFields.get("email"));

        parameters.put(idParts[0], ids_resources);

        return parameters;
    }

    public Map<String, Object> sourceRegistrationParameters(String componentName, String id) {

        Map<String, Object> response = new HashMap<>();
        response.put("@context", Arrays.asList("https://fiware.github.io/data-models/context.jsonld",
                "https://uri.etsi.org/ngsi-ld/v1/ngsi-ld-core-context-v1.3.jsonld"));
        response.put("type", "ContextSourceRegistration");

        Map<String, Object> entity = new HashMap<>();
        entity.put("type", componentName);
        entity.put("id", "urn:ngsi-ld:" + id);

        Map<String, Object> entities = new HashMap<>();
        entities.put("entities", Arrays.asList(entity));

        response.put("information", Arrays.asList(entity));

        return response;
    }

}
