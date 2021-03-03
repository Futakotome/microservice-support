package io.futakotome.MHDatasource.config.mongo;

import io.futakotome.MHDatasource.config.DataSourceDefinition;

public class MongoDynamicClientsDefinition implements DataSourceDefinition {

    private String key;

    private String uri = "mongodb://localhost/test";

    private String authenticationDatabase;

    private String gridFsDatabase;

    private Boolean autoIndexCreation;

    @Override
    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getAuthenticationDatabase() {
        return authenticationDatabase;
    }

    public void setAuthenticationDatabase(String authenticationDatabase) {
        this.authenticationDatabase = authenticationDatabase;
    }

    public String getGridFsDatabase() {
        return gridFsDatabase;
    }

    public void setGridFsDatabase(String gridFsDatabase) {
        this.gridFsDatabase = gridFsDatabase;
    }

    public Boolean getAutoIndexCreation() {
        return autoIndexCreation;
    }

    public void setAutoIndexCreation(Boolean autoIndexCreation) {
        this.autoIndexCreation = autoIndexCreation;
    }

    @Override
    public String toString() {
        return "\t" + "[" + "\n" +
                "\t\t" + "key ----- " + key + "\n" +
                "\t\t" + "uri ----- " + uri + "\n" +
                "\t\t" + "authenticationDatabase ---- " + authenticationDatabase + "\n" +
                "\t\t" + "gridFsDatabase ---- " + gridFsDatabase + "\n" +
                "\t\t" + "autoIndexCreation ---- " + autoIndexCreation + "\n" +
                "\t" + "]";
    }
}
