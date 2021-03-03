package io.futakotome.MHDatasource.annotation;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum DatabaseType {

    MYSQL,
    REDIS,
    MONGODB,

}
